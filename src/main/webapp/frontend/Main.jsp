<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/header.jsp" %> 

<div class="vh-100">
	<div class="d-flex">		
		<div class="m-3" style="width: 40%">
			<!-- 專案下拉選單 -->
			<h4 class="fs-3 fw-bold">Project Name</h4>
			<select class=" form-select mt-2" aria-label="Default select example">
  				<option selected>Choose project</option>
  				<option value="One">AC23020</option>
			</select>		
			<!-- 專案顯示資訊 -->
			<div class="mt-3">
				<label class="fs-4 fw-bold ">Project Information</label>
				<table class="table">
  					<tbody>
    					<tr>
      						<th scope="row">project_id:</th>
      						<td>ex:AC23021</td>
    					</tr>
    					<tr>
      						<th scope="row">project_name:</th>
      							<td>Holystone 轉輪更換</td>
    					</tr>
    					<tr>
      						<th scope="row">project_content:</th>
      						<td>轉輪一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套一套</td>
    					</tr>
    					<tr>
      						<th scope="row">project_owner:</th>
      						<td>James</td>
    					</tr>
    					<tr>
      						<th scope="row">project_member:</th>
      						<td>Leo、Tim、Dan、Adam</td>
    					</tr>
    					<tr>
      						<th scope="row">project_start:</th>
      						<td>2023/12/09</td>
    					</tr>
    					<tr>
      						<th scope="row">project_end:</th>
      						<td>2024/12/09</td>
    					</tr>
  					</tbody>
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
			<tbody>
 				<tr>
   					<th scope="row">1</th>
   						<td>馬桶壞掉</td>
   						<td>D</td>
   						<td>因投入不當物品，造成堵塞</td>
   						<td>馬桶.jpg</td>
   						<td>2023-12-07 00:00:00</td>
   						<td>Open</td>
 				</tr>    			
			</tbody>
		</table>
	</div>
</div>


<%@ include file="/WEB-INF/view/footer.jsp" %>

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
	})()
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
