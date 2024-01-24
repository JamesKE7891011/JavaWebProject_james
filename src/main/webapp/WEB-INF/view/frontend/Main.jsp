<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/view/header.jsp" %> 

<div class="vh-100" style="min-height: 100vh">
	<div class="d-flex">		
		<div class="m-3" style="width: 40%">
			<!-- 專案下拉選單 -->
			<h4 class="fs-3 fw-bold">請選趣專案</h4>
			<div class="d-flex justify-content-start">
				<select class="mt-2 form-select w-75" id="projectId" name="projectId" aria-label="Default select example" 
						onchange="selectProject(event.target.value)" required>
					<option selected disabled value="">Please choose project...</option>
					<c:forEach items="${ projects }" var="project">
						<option value="${ project.projectId }" ${project.projectId == defaultProjectId ? 'selected':''} >${ project.projectId } ${ project.projectName }</option>
					</c:forEach>
				</select>
			</div>		
			<!-- 專案顯示資訊 -->
			<div class="mt-3">
				<label class="fs-4 fw-bold ">趣專案_基本資訊</label>
				<!-- 專案顯示資訊 -->
				<table class="table mt-2 w-100">
					<c:forEach var="project" items="${ projects }" varStatus="loop">
						<tbody class="${loop.index >0 ? 'd-none': 'd-block'}" id="${ project.projectId }">
							<tr>
								<th scope="row">專案_ID:</th>
								<td class="" id="formProjectId">${ project.projectId }</td>
							</tr>
							<tr>
								<th scope="row">專案_名稱:</th>
								<td class="" id="formProjectName">${ project.projectName }</td>
							</tr>
							<tr>
								<th scope="row">專案_內容:</th>
								<td class="" id="formProjectContent">${ project.projectContent }</td>
							</tr>
							<tr>
								<th scope="row">專案_PM:</th>
								<td class=" d-flex justift-content-start" id="formProjectOwner">${project.projectOwner.employeeName}</td>
							</tr>
							<tr>
								<th scope="row">專案_成員:</th>								
							    <td class="" id="formProjectMember">
							        <c:if test="${not empty project.projectMembers}">
							            <c:forEach items="${project.projectMembers}" var="member" varStatus="loop">
							                <c:if test="${not loop.first}">,</c:if> <!-- 在非第一個成員之前顯示逗號 -->
							                ${member.employeeName}
							            </c:forEach>
							        </c:if>
								</td>
							</tr>
							<tr>
								<th scope="row">專案_開始日期:</th>
								<td class="" id="formProjectStartDate">${ project.projectStartDate }</td>
							</tr>
							<tr>
								<th scope="row">專案_結束日期:</th>
								<td class="" id="formProjectEndDate">${ project.projectEndDate }</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
		</div>
		<!-- 甘特圖 -->
		<div class="mx-3 w-50"  style="margin-top: 90px">
			<label class="fs-4 fw-bold mt-2">專案進度表</label>
			<div id="chart_div"></div>
		</div>
	</div>
	<!-- Issue顯示狀態列 -->
	<div class="ms-3">
		<label class="fs-4 fw-bold">趣議題狀態列</label>
		<table class="table table-hover text-center table-responsive" style="width: 90%" id="issue_table">
			<thead>
 				<tr>
   					<th scope="col">ID</th>
   					<th scope="col">名稱</th>
   					<th scope="col">類</th>
   					<th scope="col">內容</th>
   					<th scope="col">相關檔案</th>
   					<th scope="col">上傳時間</th>
   					<th scope="col">狀態</th>
 				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</div>



<script>
	
    selectProject('${defaultProjectId}');

	//----------------selectProject----------------------//
	function selectProject(projectId) {
		
	    console.log('projectId:', projectId);
	    
	    $('#issue_table tbody tr').remove();
	    
	    $('#task_table tbody tr').remove();
	
	    fetch('/JavaWebProject_james/mvc/main/findproject/' + projectId, {method: "GET",headers: {"Content-Type": "application/json",}})
	    .then(response => response.json())
	    .then(project => {
	    	
	        // 將變數宣告在 fetch 之外，以便後續使用
            let projectName = project.projectName;
            let projectContent = project.projectContent;
            let projectOwner = project.projectOwner.employeeName;
            let projectMembers = project.projectMembers;
            let projectStartDate = project.projectStartDate;
            let projectEndDate = project.projectEndDate;

            // 在這裡可以使用這些變數進行後續操作，例如顯示在控制台上
            //console.log('projectName:', projectName);
            //console.log('projectContent:', projectContent);
            //console.log('projectOwner:', projectOwner);
            //console.log('projectMembers:', projectMembers);
            //console.log('projectStartDate:', projectStartDate);
            //console.log('projectEndDate:', projectEndDate);
            
            document.getElementById("formProjectId").innerText = projectId;
            document.getElementById("formProjectName").innerText = projectName;
            document.getElementById("formProjectContent").innerText = projectContent;
            document.getElementById("formProjectOwner").innerText = projectOwner;
            document.getElementById("formProjectMember").innerText = projectMembers.map(member => member.employeeName).join(', ');
            document.getElementById("formProjectStartDate").innerText = projectStartDate;
            document.getElementById("formProjectEndDate").innerText = projectEndDate;
                       
	    })
	    .catch(error => console.error('Error fetching project:', error));
	    
	  //----------------selectIssue----------------------//
	    fetch('/JavaWebProject_james/mvc/main/findissue/' +projectId+ '/1', {
	        method: "GET",
	        headers: {
	            "Content-Type": "application/json",
	        },
	    })
	        .then(response => response.json())
	        .then(data => {
	            // 在這裡可以進一步處理 issue 的資料，例如顯示在控制台上
	            console.log('Issue Object:', data);   
	            
	            // 若是目前沒有
	            if(data.length == 0) {
	            	$('#issue_table tbody').append(
		                	`
		                	<tr>
		                		<td colspan='7' class='fw-bold'>目前沒有待處理Issue !</td>
		                	</tr>
		                	`
		                );
	            }
	            	            
	            // 進行迴圈處理每個 issue
	            data.forEach(issue => {
	                // 使用解構賦值檢查屬性是否存在
	                let {issueId,issueName,issueClassId,issueContent,issueFiles,issueDateTime,issueStatus} = issue;

	                // 初始化用來存放按鈕的陣列
	                let issueFileButtons = [];

	                // 進一步處理 issueFiles
	                if (Array.isArray(issueFiles)) {
	                    issueFileButtons = issueFiles.map(function (file) {
	                        return '<button class="btn btn-primary ms-2 mt-2 text-start" onclick="downloadFile(' + file.issueFileId + ')">' + file.issueFilePath + '</button>';
	                    });
	                }
	                
	                $('#issue_table tbody').append(
		                	`
		                	<tr>
		                		<td>\${issueId}</td>
		                		<td>\${issueName}</td>
		                		<td>\${issueClassId}</td>
		                		<td>\${issueContent}</td>
		                		<td class = "text-start">\${issueFileButtons.join('')}</td>
		                		<td>\${issueDateTime}</td>
		                		<td>\${issueStatus === 1 ? 'Open' : 'Close'}</td>
		                	</tr>
		                	`
		             );

	            });
	        })
	        .catch(error => console.error('Error fetching issues:', error));
		
	  //----------------selectSchedule----------------------//
	    fetch('/JavaWebProject_james/mvc/schedule/findschedule/'+projectId, {method: "GET",headers: {"Coneent-Type": "application/json",}})
		.then(response => response.json())
		.then(data => {
			
			if(data.length == 0) {
            	$('#task_table tbody').append(
	                	`
	                	<tr>
	                		<td colspan='8' class='fw-bold'>請通知專人建立進度表 !</td>
	                	</tr>
	                	`
	                );
            }
			
			let { projectId, scheduleId, tasks } = data;
			
			$('#scheduleId').val(scheduleId);
			
			console.log('projectId:', projectId);
			console.log('scheduleId:', scheduleId);
			console.log('tasks:', tasks);
			
			rows = [];
			
			tasks.forEach(task => {
				
				let taskId = task.taskId;
				let taskName = task.taskName;
				let taskResource = task.taskResource;
				let taskStartDate = task.taskStartDate;
				let taskEndDate = task.taskEndDate;
				let taskDuration = task.taskDuration;
				let taskPercentComplete = task.taskPercentComplete;
				let taskDependency = task.taskDependency;
				
				$('#task_table tbody').append(
					`
					<tr>
						<td>\${taskId}</td>
	      				<td>\${taskName}</td>
	      				<td>\${taskResource}</td>
	      				<td>\${taskStartDate}</td>
	      				<td>\${taskEndDate}</td>
	      				<td>\${taskDuration}</td>
	      				<td>\${taskPercentComplete}</td>
	      				<td>\${taskDependency == null ? "-": taskDependency}</td>
					</tr>
					`	
				);
				
				rows.push(
					[
						taskId.toString(),
						taskName,
						taskResource,
						new Date(taskStartDate),
						new Date(taskEndDate),
						taskDuration,
						taskPercentComplete,
						taskDependency == null ? '':taskDependency.toString()
					]		
				);
				
			});
			drawChart(rows);
		}).catch(err => {
			drawChart([]);
        });
	}
	
	//下載issue檔案
	function downloadFile(issueFileId) {
        // 构建下载链接
        var downloadLink = '/JavaWebProject_james/mvc/issue/download/' + issueFileId;

        // 创建一个隐藏的<a>元素
        var link = document.createElement('a');
        link.href = downloadLink;
        link.download = '';  // 如果你希望浏览器提示保存文件对话框，可以设置一个文件名

        // 将<a>元素附加到文档中
        document.body.appendChild(link);

        // 模拟点击事件
        link.click();

        // 从文档中移除<a>元素
        document.body.removeChild(link);
    }
		
</script>

<script src="https://www.gstatic.com/charts/loader.js"></script>
<script>
	google.charts.load("current", { packages : [ "gantt" ] });
	google.charts.setOnLoadCallback(drawChart);

	function toMilliseconds(minutes) {
		return minutes * 60 * 1000;
	}

	function drawChart() {
		var chart = new google.visualization.Gantt(document.getElementById("chart_div"));
		if(rows.length > 0) {
	   		$('#chart_div').show();
		   	var otherData = new google.visualization.DataTable();
			otherData.addColumn("string", "Task ID");
			otherData.addColumn("string", "Task Name");
			otherData.addColumn("string", "Resource");
			otherData.addColumn("date", "Start");
			otherData.addColumn("date", "End");
			otherData.addColumn("number", "Duration");
			otherData.addColumn("number", "Percent Complete");
			otherData.addColumn("string", "Dependencies");
	   		otherData.addRows(rows);
	   		var options = {height : 275,};	
	   		chart.draw(otherData, options);
	   	} else {
	   		$('#chart_div').hide();
	   	}
	}
</script>
