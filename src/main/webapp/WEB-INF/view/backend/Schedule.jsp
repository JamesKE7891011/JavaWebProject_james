<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/backendheader.jsp"%>
<div>
	<div class="m-3">
		<!-- 專案下拉選單 -->
		<h4 class="fs-4 fw-bold">Project Name</h4>
		<select class=" form-select" aria-label="Default select example">
  			<option selected>Choose project</option>
  			<option value="One">AC23020</option>
		</select>
 	</div>	
	<!-- 新增進度條 -->
	<div class="mx-3">
	<label class="fs-4 fw-bold mt-3">Schedule <button class=" ms-3 btn btn-outline-danger btn-sm  fw-bold">deleteSchedule</button></label>
		<form class="row g-3 mt-2" action="/JavaWebProject_james/mvc/schedule/addTask" id="addTaskForm" method="post">
		  ${ message }
		  <input type="hidden" value="" id="scheduleId" name="scheduleId">
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Task Name</label>
		    <input type="text" class="form-control is-valid" id="taskName"  name="taskName" value="執行" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Resourse</label>
		    <input type="text" class="form-control is-valid" id="taskResource" name="taskResource" value="採購部" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Task Start Date</label>
		    <input type="date" class="form-control is-valid" id="taskStartDate" name="taskStartDate" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Task End Date</label>
		    <input type="date" class="form-control is-valid" id="taskEndDate" name="taskEndDate" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Dependencies</label>
		    <input type="text" class="form-control is-valid" id="taskDependency" name="taskDependencred>
		  </div>
		  <div class="col-2 d-flex align-items-end">
		  	<label></label>
		    <button class="btn btn-primary ms-2"  type="button" onclick="addTask()">Add Rows</button>
		  </div>
		</form>
	</div>	
	<!-- 任務顯示表格 -->
	<div>
		<div class=" table-responsive ms-3 me-3 mt-4 mb-0 " id = "task_table">
	  		<table class="table table-bordered">
	    		<thead>
	    			<tr>
	      				<th scope="col">Task ID</th>
	      				<th scope="col">Task Name</th>
	      				<th scope="col">Resource</t
	    			<tr>
	      				<th scope="row">2023-12-01</th>
	      				<td>Project</td>
	      				<td>採購</td>
	      				<td>2024-03-01</td>
	      				<td>2024-03-02</td>
	      				<td>2024-05-01</td>
	      				<td>2024-05-02</td>
	      				<td>2024-06-01</td>
	      				<td><button class=" ms-3 btn btn-outline-danger btn-sm  fw-bold">delete</button></td>
	   				</tr>
	  			h>
	      				<th scope="col">Start</th>
	      				<th scope="col">End</th>
	      				<th scope="col">Duration</th>
	      				<th scope="col">Percent Complete</th>
	      				<th scope="col">Dependencies</th>
	      				<th scope="col">Delete</th>
	      				
	    			</tr>
	  			</thead>
	  			<tbody></tbody>
	  		</table>
		</div>		
		
		<div class="ms-3 me-4 mb-0" id="chart_div"></div>
	</div>
</div>



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
		
		/*
		rows = [
	        [ "1", "專案", "專案部", new Date(2023, 12, 01), new Date(2024, 06, 01), 183, 28.42, '' ],
	        [ "2", "採購", "採購部", new Date(2023, 12, 01), new Date(2024, 01, 01), 31, 100, '' ],
	        [ "3", "執行", "工程部", new Date(2024, 01, 02), new Date(2024, 03, 01), 59, 33.9, '2' ],
	   	];*/
	   	
	   	var chart = new google.visualization.Gantt(document.getElementById("chart_div"));
	   	if(rows.length > 0) {
	   		$('#chart_div').show();
		   	var otherData = new google.visualization.DataTable();
			otherData.addColumn("string", "Task ID");
			otherData.addColumn("string", "Task Name");
			otherData.addColumn("string", "Resource");
			otherDa = task.taskDdency;
				
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
	      				<td><button class=" ms-3 btn btn-outline-danger btn-sm  fw-bold">delete</button></td>
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
		}).catch(err => {

        });
	}

</script>


