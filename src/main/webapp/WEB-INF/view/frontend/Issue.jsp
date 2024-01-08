<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/view/header.jsp" %>

<div class="mx-5 mt-4 row "  style="min-height: 100vh">	
	<!-- 議題新增 -->
	<div class=" ms-3 w-75">
		<h4 class="fw-bold fs-3 text-center ">ISSUE CREATE</h4>
		<form class="w-80 needs-validation" method="post" action="/JavaWebProject_james/mvc/issue/addissue" novalidate >
			<div class="mb-1">
				<label for="issuename" class="form-label" >隸屬專案名稱</label>
				<select class=" form-select  w-75"
					aria-label="Default select example" onchange="selectProject(event)">
					<c:forEach items="${ projects }" var="project">
						<option value="${ project.projectId }">${ project.projectId } ${ project.projectName }</option>
					</c:forEach>
				</select>
			</div>
		 	<div class="mb-1 mt-1">
				<label for="issueName" class="form-label" >議題名稱</label>
				<input class="form-control" list="datalistOptions" id="issueName" name="issueName" placeholder="請輸入議題名稱...">
  	    		<div class="invalid-feedback w-75">請輸入議題名稱!</div>
  	    		
  	    	</div>
  			<div class="mb-1">
  				<label for="issueClass" class="form-label mt-2" id="issueClass" name="issueClass">議題類別</label>
   	 			<select class="form-select" required aria-label="Default select example" onchange="selectIssueClass(event)">
   	 				<c:forEach var="issueClass" items="${ issueClasses }" >       
	      				<option value="${ issueClass.issueClassId }"> ${ issueClass.issueClassName }</option> 
      				</c:forEach>  
    			</select>
    			<div class="invalid-feedback ">請選擇議題!</div>
  	    	</div>
			<div class="mt-3 mb-3">
  				<label for="issueFile" class="form-label">備註檔案上傳</label>
  				<input class="form-control" type="file" id="issueFile" name="issueFile">
			</div>
			<div class="mb-1">
  				<label for="issuecontent" class="form-label">議題內容(請敘述原因:)</label>
    			<textarea class="form-control" id="issueContent" name="issueContent" " placeholder="" required></textarea>
				<div class="invalid-feedback ">請備註!</div>
			</div>
			<div class="col-12 d-flex justify-content-center mt-2">
				<button class="btn btn-secondary col-12" type="submit">Submit
					Form</button>
			</div>
		</form>
	</div>
	
	<!-- 專案下拉選單 -->
	<div class="col-5 m-3">
		<h4 class="fs-4 fw-bolld">Find Issue</h4>
		<div class="d-flex justify-content-start">
				<select class=" form-select  w-75"
					aria-label="Default select example" onchange="selectProject(event)">
					<c:forEach items="${ projects }" var="project">
						<option value="${ project.projectId }">${ project.projectId } ${ project.projectName }</option>
					</c:forEach>
				</select>
		</div>
	</div>
	
	<!-- Issue顯示狀態列 -->
	<div>
		<table class="table table-hover text-center">
			<c:forEach items="${ issues }" var="issue" varStatus="loop">
				<div class="${loop.index >0 ? 'd-none': 'd-block'}" id="${ issue.projectId }">
	  				<thead>
	    				<tr>
	      					<th scope="col">IssueID:</th>
		      				<th scope="col">IssueName:</th>
		      				<th scope="col">IssueClass:</th>
		      				<th scope="col">IssueContent:</th>
		      				<th scope="col">IssuePath:</th>
		      				<th scope="col">IssueDatetime:</th>
		      				<th scope="col">IssueStatus:</th>
		      				<th scope="col">Revise:</th>
		      				<th scope="col">Delete:</th>
	    				</tr>
		  			</thead>
		  			<tbody>
		    			<tr>
		      					<td>${ issue.issueId }</td>
		      					<td>${ issue.issueName }</td>
		      					<td>${ issue.issueClassId }</td>
		      					<td>${ issue.issueContent }</td>
		      					<c:forEach items="${ issuefiles }" var="issuefile">
		      						<c:if test="${not empty issuefile}">
    									<td><c:out value="${issuefile.issueFileName}" /></td>
									</c:if>
		      					</c:forEach>	
		      					<td>${ issue.issueStatus }</td>
		      					<td>${ issue.issueDateTime }</td>
		      					<td>
		      						<button type="button" class=" btn btn-secondary ">修改</button>
		      					</td>
		      					<td>
		      						<button type="button" class="mx-4  btn btn-danger ">刪除</button>
		      					</td>
		    			</tr>    			
		  			</tbody>
	  			</div>
  			</c:forEach>
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


