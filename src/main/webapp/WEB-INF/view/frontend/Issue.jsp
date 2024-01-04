<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/header.jsp" %>

<div class="mx-5 mt-4 row" style="height: auto">	
	<!-- 議題新增 -->
	<div class=" ms-3 w-75">
		<h4 class="fw-bold fs-3 text-center ">ISSUE CREATE</h4>
		<form class="w-80 needs-validation" method="post" action="./frontend/page1.jsp" novalidate >
			<div class="mb-1">
				<label for="issuename" class="form-label" >專案名稱</label>
				<select class=" form-select mb-1" aria-label="Default select example">
  					<option selected>Choose project</option>
  					<option value="One">AC23020</option>
				</select>
			</div>
		 	<div class="mb-1 mt-1">
				<label for="issuename" class="form-label" >議題名稱</label>
				<input class="form-control" list="datalistOptions" id="issuename" name="issuename" placeholder="請輸入議題名稱...">
  	    		<div class="invalid-feedback w-75">請輸入議題名稱!</div>
  	    		
  	    	</div>
  			<div class="mb-1">
  				<label for="issueclass" class="form-label mt-2" id="issueclass" name="issueclass">檔案類別</label>
   	 			<select class="form-select" required aria-label="select example">         
     				<option value="">請選擇議題類別...</option>
      				<option value="A">A類別</option> 
      				<option value="B">B類別</option>
     				<option value="C">C類別</option>
     				<option value="D">D類別</option>
    			</select>
    			<div class="invalid-feedback ">請選擇議題!</div>
  	    	</div>
			<div class="mt-3 mb-3">
  				<label for="issuefile" class="form-label">備註檔案上傳</label>
  				<input class="form-control" type="file" id="issuefile" name="issuefile">
			</div>
			<div class="mb-1">
  				<label for="issuecontent" class="form-label">議題內容(請敘述原因:)</label>
    			<textarea class="form-control" id="issuecontent" name="issuecontent" " placeholder="" required></textarea>
				<div class="invalid-feedback ">請備註!</div>
			</div>
			<div class="col-12 d-flex justify-content-center mt-2">
				<button class="btn btn-secondary col-12" type="submit">Submit
					Form</button>
			</div>
		</form>
	</div>
	
	<!-- 專案下拉選單 -->
	<div>
		<h4 class="fs-4 fw-bolld mt-4">Project Name</h4>
		<select class=" form-select mt-2" aria-label="Default select example">
  			<option selected>Choose project</option>
  			<option value="One">AC23020</option>
		</select>
	</div>
	
	<!-- Issue顯示狀態列 -->
	<div>
		<table class="table table-hover text-center">
  			<thead>
    			<tr>
      				<th scope="col">IssueID</th>
      				<th scope="col">IssueName</th>
      				<th scope="col">IssueClass</th>
      				<th scope="col">IssueContent</th>
      				<th scope="col">IssuePath</th>
      				<th scope="col">IssueDatetime</th>
      				<th scope="col">IssueStatus</th>
      				<th scope="col">Revise</th>
      				<th scope="col">Delete</th>
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
      					<td>
      					<button type="button" class=" btn btn-secondary ">修改</button>
      					</td>
      					<td>
      					<button type="button" class="mx-4  btn btn-danger ">刪除</button>
      					</td>
    			</tr>    			
  			</tbody>
		</table>
	</div>
	
</div>


<%@ include file="/WEB-INF/view/footer.jsp" %>

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


