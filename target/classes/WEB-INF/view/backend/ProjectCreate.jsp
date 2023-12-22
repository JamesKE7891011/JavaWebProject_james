<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	
<%@ include file="/WEB-INF/view/backendheader.jsp"%>

<div class="vh-100">
	<div class="row ">		
		<div class="col-5 m-3">
			<!-- 專案下拉選單 -->
			<h4 class="fs-4 fw-bolld">Project Name</h4>
			<div class="d-flex justify-content-start">
				<select class=" form-select  w-75" aria-label="Default select example">
  					<option selected>Choose project</option>
  					<option value="One">AC23020</option>
				</select>
				<button class="ms-2  btn btn-secondary btn-md" href="/JavaWebProject_james/mvc/project/viewprojects,/JavaWebProject_james/mvc/project/viewprojectmembers"  >
				Search
				</button>
			</div>
					
			<!-- 專案顯示資訊 -->
			<table class="table mt-2">
  				<tbody>
	  				<c:forEach var="project" items="${ projects }">
	    				<tr>
	      					<th scope="row">project_id:</th>
	      						<td>${ project.projectId }</td>
	    				</tr>
	    				<tr>
	      					<th scope="row">project_name:</th>
	      						<td>${ project.projectName }</td>
	    				</tr>
	    				<tr>
	      					<th scope="row">project_content:</th>
	      						<td>${ project.content }</td>
	    				</tr>
	    				<tr>
	      					<th scope="row">project_owner:</th>
	      						<td>${ project.owner }</td>
	    				</tr>
	    				<tr>
	      					<th scope="row">project_member:</th>
	      						<td>${ project.members }</td>
	    				</tr>
	    				<tr>
	      					<th scope="row">project_start:</th>
	      						<td>${ project.startDate }</td>
	    				</tr>
	    				<tr>
	      					<th scope="row">project_end:</th>
	      						<td>${ project.endDate }</td>
	    				</tr>
	    				<tr>
	      					<th scope="row"></th>
	      						<td class="row justify-content-end">
	      						<a class="mx-4 col-3 btn btn-danger"
	      							href="javascript:void(0);"
	      							onclick="/JavaWebProject_james/mvc/project/cancelproject/+ document.getElementById('projectId')" 
	      							role="button">Delete</a>
	      						<a class="col-3 btn btn-secondary href="#" role="button"">Revise</a>
	      						</td>
	    				</tr>
	  				</c:forEach>
  				</tbody>
  			
			</table>
		</div>
		<!-- 新增專案表格 -->
		<form class="ms-4 my-0 row g-3  col-6" method="post" action="/JavaWebProject_james/mvc/project/addproject" >
			<label class="fs-4 fw-bolld">Project Create</label>
			
			<!-- Project ID -->
			<div class="col-md-6">
				<label for="validationDefault01" class="form-label">project_id</label> 
				<input type="text" class="form-control" id="project_id" name="project_id" required>
			</div>
			
			<!-- Project Name -->
			<div class="col-md-6">
				<label for="validationDefault01" class="form-label">project_name</label> <input
					type="text" class="form-control" id="project_name" name="project_name" required>
			</div>
			
			<!-- Project Content -->
			<div class="mb-3">
  				<label for="exampleFormControlTextarea1" class="form-label">project_content</label>
  				<textarea class="form-control" id="project_content" name="project_content" rows="3" required></textarea>
			</div>
			
			<!-- Project Owner -->
			<div class="col-md-6 justify-content-start"> 
				<label for="validationDefault01" class="form-label">project_owner</label> 
				<div class="d-flex justift-content-start">
					<input type="text" class="form-control" id="project_owner" name="project_owner" disabled>
					<button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#projectModal">+</button>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="projectModal" tabindex="-1" aria-labelledby="exampleOwnerLabel" aria-hidden="true">
  					<div class="modal-dialog modal-lg">
    					<div class="modal-content">
      						<div class="modal-header">
        						<h5 class="modal-title" id="exampleOwnerLabel">Project Owner</h5>
        						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      						</div>
      						<div class="modal-body">
        						<div class=" d-flex justify-content-center mt-2">
        							<div style="height: 500px; width: 500px;" class="shadow" id="left">
            							<h3 class="text-center">Employee</h3>
            							<ul class="list-group">
                							<button type="button" class="listItem list-group-item list-group-item-action mb-1">A</button>
                							<button type="button" class="listItem list-group-item list-group-item-action mb-1">B</button>
                							<button type="button" class="listItem list-group-item list-group-item-action mb-1">C</button>
            							</ul>
        							</div>
        							<div style="width: 50px; height: 500px;" class="d-flex flex-column align-items-center justify-content-center mx-1">
            							<button id="toRight" class="btn btn-outline-primary"> >> </button>
            							<button id="toLeft" class="btn btn-outline-primary"> << </button>
        							</div>
        							<div style="height: 500px; width: 500px;" class="shadow" id="right">
            							<h3 class="text-center">Projrct Owner</h3>
            							<ul class="list-group"></ul>
        							</div>
    							</div>
      						</div>
      						<div class="modal-footer">
        						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        						<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="saveProjectOwner()">Save changes</button>
      						</div>
    					</div>
  					</div>
				</div>
			</div>
			
			<!-- Project Member -->
			<div class="col-md-6">
				<label for="validationDefault01" class="form-label">project_member</label> 
				<div class="d-flex justift-content-start">
					<input type="text" class="form-control" id="project_member" name="project_member" disabled>
					<button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#exampleMember">+</button>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="exampleMember" tabindex="-1" aria-labelledby="exampleMemberLabel" aria-hidden="true">
  					<div class="modal-dialog modal-lg">
    					<div class="modal-content">
      						<div class="modal-header">
        						<h5 class="modal-title" id="exampleModalLabel">Project Member</h5>
        						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      						</div>
      						<div class="modal-body">
        						<div class=" d-flex justify-content-center mt-2">
        							<div style="height: 500px; width: 500px;" class="shadow" id="left2">
            							<h3 class="text-center">Employee</h3>
            							<ul class="list-group">
                							<button type="button" class="listItem list-group-item list-group-item-action mb-1">A</button>
                							<button type="button" class="listItem list-group-item list-group-item-action mb-1">B</button>
                							<button type="button" class="listItem list-group-item list-group-item-action mb-1">C</button>
            							</ul>
        							</div>
        							<div style="width: 50px; height: 500px;" class="d-flex flex-column align-items-center justify-content-center mx-1">
            							<button id="toRight2" class="btn btn-outline-primary toRight"> >> </button>
            							<button id="toLeft2" class="btn btn-outline-primary toLeft"> << </button>
        							</div>
        							<div style="height: 500px; width: 500px;" class="shadow" id="right2">
            							<h3 class="text-center">Projrct Member</h3>
            							<ul class="list-group"></ul>
        							</div>
    							</div>
      						</div>
      						<div class="modal-footer">
        						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        						<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="saveProjectMember()">Save changes</button>
      						</div>
    					</div>
  					</div>
				</div>
			</div>
			
			<!-- Project Start -->
			<div class="col-md-6">
				<label for="validationDefault01" class="form-label">project_start</label> <input
					type="date" class="form-control" id="project_start" name="project_start" required>
			</div>
			
			<!-- Project End -->
			<div class="col-md-6 mb-2">
				<label for="validationDefault01" class="form-label">project_end</label> <input
					type="date" class="form-control" id="project_end" name="project_end" required>
			</div>
			<div class="col-12 d-flex justify-content-center ">
				<button class="btn btn-secondary col-12" type="submit">Submit Form</button>
			</div>
		</form>
		
	</div>

</div>
<%@ include file="/WEB-INF/view/backendfooter.jsp"%>

<!-- 彈跳視窗 -->
<script>
var myModal = document.getElementById('exampleOwner');
var myModal = document.getElementById('exampleMember');
var myInput = document.getElementById('myInput');

myModal.addEventListener('shown.bs.modal', function () {
  myInput.focus();
});
</script>
<!-- 成員新增 -->
<script>

	let projectOwner = [];

    $('.listItem').on('click',function() {
       $(this).toggleClass('active');
    });

    $('#toRight').on('click',function() {
        $('#left .active').each(function() {
            $('#right').append($(this)[0]);
            //加入至右邊欄位
            projectOwner.push($(this)[0].innerText);
        });
    });

    $('#toLeft').on('click',function() {
        $('#right .active').each(function() {
            $('#left').append($(this)[0]);
            //移除還原至左邊欄位
            let ownerstr = $(this)[0].innerText;
            projectOwner = projectOwner.filter(owner => owner !== ownerstr);
        });
    });
    
    //儲存至框格內
    function saveProjectOwner() {
    	$('#project_owner').val(projectOwner);
    }

    
    
    let projectMember = [];

    $('#toRight2').on('click',function() {
        $('#left2 .active').each(function() {
            $('#right2').append($(this)[0]);
            //加入至右邊欄位
            projectMember.push($(this)[0].innerText);
        });
    });

    $('#toLeft2').on('click',function() {
        $('#right2 .active').each(function() {
            $('#left2').append($(this)[0]);
            //移除還原至左邊欄位
            let ownerstr = $(this)[0].innerText;
            projectMember = projectMember.filter(owner => owner !== ownerstr);
        });
    });
    
    function saveProjectMember() {
    	$('#project_member').val(projectMember);
    }
    
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

