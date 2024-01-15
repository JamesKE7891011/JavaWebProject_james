<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/backendheader.jsp"%>

<div class="m-3">

	<!-- 專案下拉選單 -->
	<h4 class="fs-4 fw-bold">Project Name</h4>
	<select class=" form-select" aria-label="Default select example">
  		<option selected>Choose project</option>
  		<option value="One">AC23020</option>
	</select>

	<!-- 日期修改表格 -->
	<label class="fs-4 fw-bold mt-3">Schedule </label>
	<div class="mt-2">
		<button class=" ms-2 btn btn-outline-success btn-md  fw-bold">add+</button></label>
		<button class=" ms-2 btn btn-outline-danger btn-  fw-bold">delete</button>
	</div>
	<div class=" table-responsive">
  		<table class="table">
    		<thead>
    			<tr>
      				<th scope="col">purchase_start</th>
      				<th scope="col">purchase_end</th>
      				<th scope="col">execution_start</th>
      				<th scope="col">execution_end</th>
      				<th scope="col">checkandaccept_start</th>
      				<th scope="col">checkandaccept_end</th>
      				<th scope="col">payment_start</th>
      				<th scope="col">payment_end</th>
    			</tr>
  			</thead>
  			<tbody>
    			<tr>
      				<th scope="row">2023-12-01</th>
      				<td>2024-01-01</td>
      				<td>2024-01-02</td>
      				<td>2024-03-01</td>
      				<td>2024-03-02</td>
      				<td>2024-05-01</td>
      				<td>2024-05-02</td>
      				<td>2024-06-01</td>
   				</tr>
  			</tbody>
  		</table>
	</div>			
	<!-- 甘特圖 -->	
	<div id="chart_div"></div>
</div>

<%@ include file="/WEB-INF/view/backendfooter.jsp"%>

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
	        [ "taskmain", "Project", "blue", new Date(2023, 11, 1), new Date(2024, 5, 1), null, 100, null ],
	        [ "taskPurchase", "Purchase", "red", new Date(2023, 11, 1), new Date(2024, 0, 1), null, 100, null ],
	        [ "taskExecution", "Execution", "orange", new Date(2024, 0, 2), new Date(2024, 2, 1), null, 100, "taskPurchase" ],
	        [ "taskCheckAndAccept", "CheckAndAccept", "green", new Date(2024, 2, 2), new Date(2024, 4, 1), null, 75, "taskExecution" ],
	        [ "task4", "Payment", "purple", new Date(2024, 4, 2), new Date(2024, 5, 1), null, 0, "taskCheckAndAccept" ],
	   	]);

		var options = {
			height : 275,
		};

		var chart = new google.visualization.Gantt(document.getElementById("chart_div"));
		chart.draw(otherData, options);
		}
</script>