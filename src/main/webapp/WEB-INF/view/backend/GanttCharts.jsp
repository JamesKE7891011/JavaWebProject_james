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
	<label class="fs-4 fw-bold mt-3">Schedule </label>
		<form class="row g-3">
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Task Name</label>
		    <input type="text" class="form-control is-valid" id="validationServer01" value="Mark" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Resourse</label>
		    <input type="text" class="form-control is-valid" id="validationServer01" value="Mark" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Task Start Date</label>
		    <input type="date" class="form-control is-valid" id="validationServer01" value="Mark" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Task End Date</label>
		    <input type="date" class="form-control is-valid" id="validationServer01" value="Mark" required>
		  </div>
		  <div class="col-md-2">
		    <label for="validationServer01" class="form-label">Dependencies</label>
		    <input type="text" class="form-control is-valid" id="validationServer01" value="Mark" required>
		  </div>
		  <div class="col-2 d-flex align-items-end">
		  	<label></label>
		    <button class="btn btn-primary ms-2"  type="submit">Add Rows</button>
		  </div>
		</form>
	</div>	
	<!-- 任務顯示表格 -->
	<div>
		<div class=" table-responsive ms-3 me-3 mt-4 mb-0 ">
	  		<table class="table table-bordered">
	    		<thead>
	    			<tr>
	      				<th scope="col">Task ID</th>
	      				<th scope="col">Task Name</th>
	      				<th scope="col">Resource</th>
	      				<th scope="col">Start</th>
	      				<th scope="col">End</th>
	      				<th scope="col">Duration</th>
	      				<th scope="col">Percent Complete</th>
	      				<th scope="col">Dependencies</th>
	      				<th scope="col">Delete</th>
	      				
	    			</tr>
	  			</thead>
	  			<tbody>
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
	  			</tbody>
	  		</table>
	  		
		</div>		
		
		<div class="ms-3 me-4 mb-0" id="chart_div"></div>
	</div>
</div>



<script src="https://www.gstatic.com/charts/loader.js"></script>
<script>
	google.charts.load("current", {packages : [ "gantt" ]});
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
	        [ "1", "Project", "blue", new Date(2023, 11, 1), new Date(2024, 5, 1), null, 100, null ],
	        [ "2", "Purchase", "red", new Date(2023, 11, 1), new Date(2024, 0, 1), null, 100, null ],
	        [ "3", "Execution", "orange", new Date(2024, 0, 2), new Date(2024, 2, 1), null, 100, "2" ],
	        [ "4", "CheckAndAccept", "green", new Date(2024, 2, 2), new Date(2024, 4, 1), null, 75, "3" ],
	        [ "5", "Payment", "purple", new Date(2024, 4, 2), new Date(2024, 5, 1), null, 0, "4" ],
	   	]);

		var options = {
			height : 275,
		};

		var chart = new google.visualization.Gantt(document.getElementById("chart_div"));
		chart.draw(otherData, options);
		}
</script>
