<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/view/backendheader.jsp"%>
<div>
	<div class="m-3">
		<!-- 專案下拉選單 -->
		<h4 class="fs-4 fw-bold">請選趣專案</h4>
		<select class="mt-2 form-select w-75" id="projectId" name="projectId" aria-label="Default select example" 
						onchange="selectProject(event.target.value)" required>
			<option selected disabled value="">請選擇專案...</option>
			<c:forEach items="${ projects }" var="project">
				<option value="${ project.projectId }">${ project.projectId } ${ project.projectName }</option>
			</c:forEach>
		</select>
 	</div>	
	<!-- 新增進度條 -->
	<div class="mx-3">
	<label class="fs-4 fw-bold mt-3">進度表 
	    <button class=" ms-3 btn btn-outline-danger btn-sm fw-bold" onclick="deleteSchedule()">刪除進度表</button></label>
		<form class="row g-3 mt-2" action="/JavaWebProject_james/mvc/schedule/addTask" id="addTaskForm" method="post">
		  ${ message }
		  <input type="hidden" value="" id="scheduleId" name="scheduleId">
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">任務名稱:</label>
		    <input type="text" class="form-control is-valid" id="taskName"  name="taskName" value="執行" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">執行單位:</label>
		    <input type="text" class="form-control is-valid" id="taskResource" name="taskResource" value="採購部" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">任務開始:</label>
		    <input type="date" class="form-control is-valid" id="taskStartDate" name="taskStartDate" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">任務結束:</label>
		    <input type="date" class="form-control is-valid" id="taskEndDate" name="taskEndDate" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">關聯任務ID:</label>
		    <input type="text" class="form-control is-valid" id="taskDependency" name="taskDependency" value="1" >
		  </div>
		  <div class="col-2 d-flex align-items-end">
		  	<label></label>
		    <button class="btn btn-primary ms-2"  type="button" onclick="addTask()">新增</button>
		  </div>
		</form>
	</div>	
	<!-- 任務顯示表格 -->
	<div>
		<div class=" table-responsive ms-3 me-3 mt-4 mb-0 " id = "task_table">
	  		<table class="table table-bordered">
	    		<thead>
	    			<tr>
	      				<th scope="col">任務 ID</th>
	      				<th scope="col">任務名稱</th>
	      				<th scope="col">執行單位e</th>
	      				<th scope="col">任務開始</th>
	      				<th scope="col">任務結束</th>
	      				<th scope="col">天數</th>
	      				<th scope="col">進度%</th>
	      				<th scope="col">關聯任務ID:</th>
	      				<th scope="col">刪除</th>
	    			</tr>
	  			</thead>
	  			<tbody></tbody>
	  		</table>
		</div>		
		<div class="ms-3 me-4 pb-3" id="chart_div"></div>
	</div>
</div>

<style>

#chart_div> div {

/*height: 1000px !important;*/

}

</style>

<script src="https://www.gstatic.com/charts/loader.js"></script>
<script>

	document.getElementById("taskStartDate").valueAsDate = new Date();
	document.getElementById("taskEndDate").valueAsDate = new Date();

	google.charts.load("current", {packages : [ "gantt" ]});
	google.charts.setOnLoadCallback(drawChart);

	function toMilliseconds(minutes) {
		return minutes * 60 * 1000;
	}

	function drawChart(rows) {
		
		/* 正確
		rows = [
	        [ "1", "專案", "專案部", new Date(2023, 12, 01), new Date(2024, 06, 01), 183, 28.42, '' ],
	        [ "2", "採購", "採購部", new Date(2023, 12, 01), new Date(2024, 01, 01), 31, 100, '' ],
	        [ "3", "執行", "工程部", new Date(2024, 01, 02), new Date(2024, 03, 01), 59, 33.9, '2' ],
	   	];*/

	   	var chart = new google.visualization.Gantt(document.getElementById("chart_div"));
	   	if(rows!=undefined && rows.length > 0) {
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
	   		var height = rows.length * 41 + 30; // 動態計算高度
	   		var options = {height : height,};	
	   		chart.draw(otherData,options);
	   	} else {
	   		$('#chart_div').hide();
	   	}
	}
	
	function selectProject(projectId) {
		
		$('#task_table tbody tr').remove();
		
		fetch('/JavaWebProject_james/mvc/schedule/findschedule/'+projectId, {method: "GET",headers: {"Coneent-Type": "application/json",}})
		.then(response => response.json())
		.then(data => {
			console.log('data:', data);
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
				
				let tr = `
					<tr>
						<td>\${taskId}</td>
	      				<td>\${taskName}</td>
	      				<td>\${taskResource}</td>
	      				<td>\${taskStartDate}</td>
	      				<td>\${taskEndDate}</td>
	      				<td>\${taskDuration}</td>
	      				<td>\${taskPercentComplete}</td>
	      				<td>\${taskDependency == null ? "-": taskDependency}</td>
	      				<td><button class="ms-3 btn btn-outline-danger btn-sm fw-bold" onclick="deleteTask(\${ task.taskId })">刪除</button></td>
					</tr>
								
				`;
				
				$('#task_table tbody').append(tr);
				
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
	
	function addTask(){
		
		let formData = {
			"projectId" : $('#projectId').val(),
			"scheduleId" : $('#scheduleId').val(),
			"taskName" : $('#taskName').val(),
			"taskResource" : $('#taskResource').val(),
			"taskStartDate" : $('#taskStartDate').val(),
			"taskEndDate" : $('#taskEndDate').val(),
			"taskDependency" : $('#taskDependency').val()
		};
		let action = $('#addTaskForm').attr('action');

		console.log(action,formData);
		
		fetch(
			action, 
			{   method: 'POST', 
				body: JSON.stringify(formData), 
				headers: new Headers({
			        'Content-Type': 'application/json'
			    }),
			}
		)
		.then(response => response.json())
		.then(data => {
			selectProject($('#projectId').val());
			alert(data['message']);
		}).catch(err => {

        });
	}
	
	
	function deleteTask(taskId) {
		const url = '${pageContext.request.contextPath}/mvc/schedule/deletetask/' + taskId;
		if(confirm('是否要刪除 ?')) {
			fetch(url, {method: 'GET'})
			.then(response => {
				console.log(response);
				//console.log(response.redirected);
				if(response.ok || response.redirected) {
					console.log(response);
					// 刪除成功, 更新網頁
					//location.href = '${pageContext.request.contextPath}/mvc/user/';
					//location.href = response.url;
					selectProject($('#projectId').val());
				} else {			
					console.log('delete fail');
				}
			})
			.catch(error => {
				console.log('delete error: ', error);
			});
		}
	}
	
	function deleteSchedule() {
		
		let scheduleId = $('#scheduleId').val();
		
		const url = '${pageContext.request.contextPath}/mvc/schedule/deleteschedule/' + scheduleId;
		if(confirm('是否要刪除 ?')) {
			fetch(url, {method: 'GET'})
			.then(response => {
				console.log(response);
				//console.log(response.redirected);
				if(response.ok || response.redirected) {
					console.log(response);
					// 刪除成功, 更新網頁
					//location.href = '${pageContext.request.contextPath}/mvc/user/';
					location.href = response.url;
				} else {			
					console.log('delete fail');
				}
			})
			.catch(error => {
				console.log('delete error: ', error);
			});
		}
	}
	
</script>

