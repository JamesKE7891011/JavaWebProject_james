<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/view/header.jsp" %> 

<div class="vh-100">
	<div class="d-flex">		
		<div class="m-3" style="width: 40%">
			<!-- 專案下拉選單 -->
			<h4 class="fs-3 fw-bold">Project Name</h4>
			<div class="d-flex justify-content-start">
				<select class="mt-2 form-select w-75" id="projectId" name="projectId" aria-label="Default select example" 
						onchange="selectProject(event)" required>
					<option selected disabled value="">Please choose project...</option>
					<c:forEach items="${ projects }" var="project">
						<option value="${ project.projectId }">${ project.projectId } ${ project.projectName }</option>
					</c:forEach>
				</select>
			</div>		
			<!-- 專案顯示資訊 -->
			<div class="mt-3">
				<label class="fs-4 fw-bold ">Project Information</label>
				<!-- 專案顯示資訊 -->
				<table class="table mt-2">
					<c:forEach var="project" items="${ projects }" varStatus="loop">
						<tbody class="${loop.index >0 ? 'd-none': 'd-block'}" id="${ project.projectId }">
							<tr>
								<th scope="row">projectId:</th>
								<td class="w-100" id="formProjectId">${ project.projectId }</td>
							</tr>
							<tr>
								<th scope="row">projectName:</th>
								<td class="w-100" id="formProjectName">${ project.projectName }</td>
							</tr>
							<tr>
								<th scope="row">projectContent:</th>
								<td class="w-100" id="formProjectContent">${ project.projectContent }</td>
							</tr>
							<tr>
								<th scope="row">projectOwner:</th>
								<td class="w-100 d-flex justift-content-start" id="formProjectOwner">${project.projectOwner.employeeName}</td>
							</tr>
							<tr>
								<th scope="row">projectMember:</th>								
							    <td class="w-100" id="formProjectMember">
							        <c:if test="${not empty project.projectMembers}">
							            <c:forEach items="${project.projectMembers}" var="member" varStatus="loop">
							                <c:if test="${not loop.first}">,</c:if> <!-- 在非第一個成員之前顯示逗號 -->
							                ${member.employeeName}
							            </c:forEach>
							        </c:if>
								</td>
							</tr>
							<tr>
								<th scope="row">projectStartDate:</th>
								<td class="w-100" id="formProjectStartDate">${ project.projectStartDate }</td>
							</tr>
							<tr>
								<th scope="row">projectEndDate:</th>
								<td class="w-100" id="formProjectEndDate">${ project.projectEndDate }</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
		</div>
		<!-- 甘特圖 -->
		<div class="mx-3 w-50"  style="margin-top: 90px">
			<label class="fs-4 fw-bold mt-3">Schedule</label>
			<div id="chart_div"></div>
		</div>
	</div>
	<!-- Issue顯示狀態列 -->
	<div class="ms-3">
		<label class="fs-4 fw-bold">Issue</label>
		<table class="table table-hover text-center" style="width: 80%">
			<thead>
 				<tr>
   					<th scope="col">IssueID</th>
   					<th scope="col">IssueName</th>
   					<th scope="col">IssueClass</th>
   					<th scope="col">IssueContent</th>
   					<th scope="col">IssuePath</th>
   					<th scope="col">IssueDatetime</th>
   					<th scope="col">IssueStatus</th>
 				</tr>
			</thead>
			<tbody >
				
 					<tr>
   						<td id="formIssueId"></td>
   						<td id="formIssueName"></td>
   						<td id="formIssueClass"></td>
   						<td id="formIssueContent"></td>
   						<td id="formIssueFiles"></td>
   						<td id="formIssueDateTime"></td>   						
   						<td id="formIssueStatus">   						
   						</td>    										
 					</tr>    			
				
			</tbody>
		</table>
	</div>
</div>


<%@ include file="/WEB-INF/view/footer.jsp" %>

<script>
	

	//----------------selectProject----------------------//
	function selectProject(event) {
	    var projectId = event.target.value;
	    console.log('projectId:', projectId);
	
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
	    
	    fetch('/JavaWebProject_james/mvc/main/findissue/' + projectId, {method: "GET",headers: {"Content-Type": "application/json",}})
	    .then(response => response.json())
	    .then(data => {
	        $.each(data, function(index, issue) {
	            // 在這裡可以進一步處理 issue 的資料，例如顯示在控制台上
	            console.log('Issue Object:', issue);

	            // 使用解構賦值檢查屬性是否存在
	            let {
	                issueId,
	                issueName,
	                issueClassId,
	                issueContent,
	                issueFiles,
	                issueDateTime,
	                issueStatus
	            } = issue;
	            

	            // 進一步處理 issueFile
	            if (Array.isArray(issueFiles)) {
	                let issueFileButton = issueFiles.map(function(file) {
	                    return '<button class="btn btn-primary ms-2 mt-2 text-start" onclick="downloadFile(' + file.issueFileId + ')">' + file.issueFilePath + '</button>';
	                });
	                console.log('Issue File Buttons:', issueFileButton);
	            }
	            
	            document.getElementById("formIssueId").innerText = issueId;
	            document.getElementById("formIssueName").innerText = issueName;
	            document.getElementById("formIssueClass").innerText = issueClassId;
	            document.getElementById("formIssueContent").innerText = issueContent;
	            document.getElementById("formIssueFiles").innerText = issueFileButtons.join('');
	            document.getElementById("formIssueDateTime").innerText = issueDateTime;
	            document.getElementById("formIssueStatus").innerText = issueStatus;
	            
	        });
	    })
	    .catch(error => console.error('Error fetching issues:', error));

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

<!-- 甘特圖修改 -->
<script>
	//Example starter JavaScript for disabling form submissions if there are invalid fields
	(function() {
		'use strict'

		// Fetch all the forms we want to apply custom Bootstrap validation styles to
		var forms = document.querySelectorAll('.needs-validation')

		// Loop over them and prevent submission
		Array.prototype.slice.call(forms).forEach(function(form) {
			form.addEventListener('submit', function(event) {
				if (!form.checkValidity()) {
					event.preventDefault()
					event.stopPropagation()
				}

				form.classList.add('was-validated')
			}, false)
		})
	})
</script>


<script src="https://www.gstatic.com/charts/loader.js"></script>
<script>
	google.charts.load("current", {
		packages : [ "gantt" ]
	});
	google.charts.setOnLoadCallback(drawChart);

	function toMilliseconds(minutes) {
		return minutes * 60 * 1000;
	}

	function drawChart() {
		var otherData = new google.visualization.DataTable();
		otherData.addColumn("string", "Task ID");
		otherData.addColumn("string", "Task Name");
		otherData.addColumn("string", "Resource");
		otherData.addColumn("date", "Start");
		otherData.addColumn("date", "End");
		otherData.addColumn("number", "Duration");
		otherData.addColumn("number", "Percent Complete");
		otherData.addColumn("string", "Dependencies");

		otherData.addRows([
     				[ "taskmain", "Project", "blue", new Date(2023, 11, 1), new Date(2024, 5, 1), null, 100, null ],
     				[ "taskPurchase", "Purchase", "red", new Date(2023, 11, 1), new Date(2024, 0, 1), null, 100, null ],
     				[ "taskExecution", "Execution", "orange", new Date(2024, 0, 2), new Date(2024, 2, 1), null, 100, "taskPurchase" ],
     				[ "taskCheckAndAccept", "CheckAndAccept", "green", new Date(2024, 2, 2), new Date(2024, 4, 1), null, 75, "taskExecution" ],
     				[ "task4", "Payment", "purple", new Date(2024, 4, 2), new Date(2024, 5, 1), null, 0, "taskCheckAndAccept" ],
					]);

		var options = {
			height : 275,
			gantt : {
				defaultStartDate : new Date(2023, 11, 1),
			},
		};

		var chart = new google.visualization.Gantt(document.getElementById("chart_div"));
			chart.draw(otherData, options);
		}
</script>
