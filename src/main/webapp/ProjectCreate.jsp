<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<%@ include file="/WEB-INF/view/backendheader.jsp"%>

<style>
.disable {
	pointer-events: none;
}
</style>

<div class="vh-100">
	<div class="row ">		
		<!-- 專案下拉選單 -->
		<div class="col-5 m-3">
			<h4 class="fs-4 fw-bolld">Project Name </h4>
			<div class="d-flex justify-content-start">
				<select class=" form-select  w-75"
					aria-label="Default select example" onchange="selectProject(event)">
					
					<!-- 從後端取得專案列表集合， var代表遍歷集合中取出單一值的名稱(能透$字符取SQL中的值是因為透過Controller內的getPage方法，用model將值放到items) -->
					<c:forEach items="${ projects }" var="project">
						
						<!-- value 代表屬性表示選項的值 -->
						<option value="${ project.projectId }">${ project.projectId }   ${ project.projectName }</option>
					</c:forEach>
				</select>
			</div>

			<!-- 專案資訊更新及顯示 -->			
			<!-- c:forEach 標籤就會在每次迭代時創建一個名為 "loop" 的變數，如果loop.index被第一次尋訪就顯示 -->
			<table class="table mt-2">
				<c:forEach var="project" items="${ projects }" varStatus="loop"> 
					<tbody class="${loop.index >0 ? 'd-none': 'd-block'}" id="${ project.projectId }">
						<tr>
							<th scope="row">projectId:</th>
							<td class="w-100">${ project.projectId }</td>
						</tr>
						<tr>
							<th scope="row">projectName:</th>
							<td class="w-100">
								<input type="text" value="${ project.projectName }" class="w-75" id="upadte_projectName_${ project.projectId }">
							</td>
						</tr>
						<tr>
							<th scope="row">projectContent:</th>
							<td class="w-100">
								<input type="text" value="${ project.projectContent }" class="w-75" id="upadte_projectContent_${ project.projectId }">
							</td>
						</tr>
						<tr>
							<th scope="row">projectOwner:</th>
							<td class="w-100"> 
								
								<!-- Project Owner Update -->
								<div class="d-flex justift-content-start">
									<select class="form-select" value="${project.projectOwner.employeeId}" id="upadte_projectOwner_${ project.projectId }">
									  <c:forEach items="${ employees }" var="employee">
									     <option value="${ employee.employeeId }" 
									        <c:if test="${ employee.employeeId == project.projectOwner.employeeId }">selected</c:if>
									     >
									        ${ employee.employeeName}
									     </option>
									  </c:forEach>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">projectMember:</th>
							<td class="w-100">
								<c:set var="membersString" value="" />
								<c:set var="membersIdString" value="" />
								<c:forEach items="${ project.projectMembers }" var="member"  varStatus="loop">
									<c:if test="${ loop.index < project.projectMembers.size() -1 }">
									
									<!-- 如果循環索引不是最後一個成員，則加上逗號 -->
										<c:set var="membersString" value="${membersString}${member}," />
										<c:set var="membersIdString" value="${membersIdString}${member.employeeId}," />
									</c:if>
									<c:if test="${ loop.index == project.projectMembers.size() - 1 }">
									
									<!-- 如果循環索引是最後一個成員，則不加逗號 -->
										<c:set var="membersString" value="${membersString}${member}" />
										<c:set var="membersIdString" value="${membersIdString}${member.employeeId}" />
									</c:if>
								</c:forEach>
								
								<!-- Project Member Update -->
								<div class="d-flex justift-content-start">
									<input type="text" class="form-control disable" id="update_projectMember_${ project.projectId }" name="projectMember" value="${membersIdString}" hidden> 
									<input type="text" class="form-control disable " id="update_projectMember2_${ project.projectId }" name="projectMember2" value="${membersString}" >
									<button type="button" class="btn btn-secondary" data-bs-toggle="modal" onclick="openModalMember('${ project.projectId }')" >+</button>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">projectStartDate:</th>
							<td class="w-100">
								<input type="date" value="<fmt:formatDate value="${ project.projectStartDate }" pattern="yyyy-MM-dd" />" class="w-75" id="upadte_projectStartDate_${ project.projectId }">
							</td>
						</tr>
						<tr>
							<th scope="row">projectEndDate:</th>
							<td class="w-100">
								<input type="date" value="<fmt:formatDate value="${ project.projectEndDate }" pattern="yyyy-MM-dd" />" class="w-75" id="upadte_projectEndDate_${ project.projectId }">
							</td>
						</tr>
						<tr>
							<th scope="row"></th>
								<td class="row justify-content-end">
								
								<!-- 刪除專案按鈕 -->
								<a class="mx-4 col-3 btn btn-danger" href="javascript:void(0);" onclick="deleteProject('${project.projectId}')" role="button">Delete</a>
								
								<!-- 更新專案按鈕 -->
								<a class="col-3 btn btn-secondary" href="javascript:void(0);" onclick="updateProject('${project.projectId}')" role="button">Update</a>
							
							</td>
						</tr>
					</tbody>
				</c:forEach>
			</table>
		</div>
		
		<!-- Update Project Member Modal -->
		<div class="modal fade" id="update_exampleMember" tabindex="-1" aria-labelledby="exampleMemberLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabelMember">Project Member</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div class=" d-flex justify-content-center mt-2">
							<div style="height: 500px; width: 500px;"
								class="shadow overflow-auto" id="left4">
								<h3 class="text-center">Employee</h3>
								<ul class="list-group overflow-auto">
									<c:forEach items="${ employees }" var="employee">
										<button type="button" class="listItem list-group-item list-group-item-action mb-1" data-employee-id="${ employee.employeeId }">${ employee.employeeName}</button>
									</c:forEach>
								</ul>
							</div>
							<div style="width: 50px; height: 500px;"
								class="d-flex flex-column align-items-center justify-content-center mx-1">
								<button id="toRight4" class="btn btn-outline-primary toRight">>></button>
								<button id="toLeft4" class="btn btn-outline-primary toLeft"><<</button>
							</div>
							<div style="height: 500px; width: 500px;" class="shadow" id="right4">
								<h3 class="text-center">Projrct Member</h3>
								<ul class="list-group"></ul>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"	data-bs-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="updateProjectMember()">Savechanges</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 新增專案表格 -->
		<form class="ms-4 my-0 row g-3  col-6" method="post"
			action="/JavaWebProject_james/mvc/project/addproject">
			<label class="fs-4 fw-bolld">Project Create  </label>
			<div> ${ errorMessage } </div>
			<!-- Project ID -->
			<div class="col-md-6">
				<label for="validationDefault01" class="form-label">projectId</label>
				<input type="text" class="form-control" id="projectId"
					name="projectId" required>
			</div>

			<!-- Project Name -->
			<div class="col-md-6">
				<label for="validationDefault01" class="form-label">projectName</label>
				<input type="text" class="form-control" id="projectName"
					name="projectName" required>
			</div>

			<!-- Project Content -->
			<div class="mb-3">
				<label for="exampleFormControlTextarea1" class="form-label">projectContent</label>
				<textarea class="form-control" id="projectContent"
					name="projectContent" rows="3" required></textarea>
			</div>

			<!-- Project Owner -->
			<div class="col-md-6 justify-content-start">
				<label for="validationDefault01" class="form-label">projectOwner</label>
				<div class="d-flex justift-content-start">
					<input type="text" class="form-control disable" id="projectOwner"
						name="projectOwner" hidden> <input type="text"
						class="form-control disable" id="projectOwner2"
						name="projectOwner2">
					<button type="button" class="btn btn-secondary"
						data-bs-toggle="modal" data-bs-target="#projectModal">+</button>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="projectModal" tabindex="-1"
					aria-labelledby="exampleOwnerLabel" aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleOwnerLabel">Project
									Owner</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<div class=" d-flex justify-content-center mt-2">
									<div style="height: 500px; width: 500px;"
										class="shadow overflow-auto" id="left">
										<h3 class="text-center">Employee</h3>
										<ul class="list-group overflow-auto">
											<c:forEach items="${ employees }" var="employee">
												<button type="button"
													class="listItem list-group-item list-group-item-action mb-1"
													data-employee-id="${ employee.employeeId }">
													${employee.employeeName}</button>
											</c:forEach>
										</ul>
									</div>
									<div style="width: 50px; height: 500px;"
										class="d-flex flex-column align-items-center justify-content-center mx-1">
										<button id="toRight" class="btn btn-outline-primary">
											>></button>
										<button id="toLeft" class="btn btn-outline-primary">
											<<</button>
									</div>
									<div style="height: 500px; width: 500px;" class="shadow"
										id="right">
										<h3 class="text-center">Project Owner</h3>
										<ul class="list-group"></ul>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-bs-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary"
									data-bs-dismiss="modal" onclick="saveProjectOwner()">Save
									changes</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- Project Member -->
			<div class="col-md-6">
				<label for="validationDefault01" class="form-label">projectMember</label>
				<div class="d-flex justift-content-start">
					<input type="text" class="form-control disable" id="projectMember"
						name="projectMember" hidden> <input type="text"
						class="form-control disable" id="projectMember2"
						name="projectMember2">
					<button type="button" class="btn btn-secondary"
						data-bs-toggle="modal" data-bs-target="#exampleMember">+</button>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="exampleMember" tabindex="-1"
					aria-labelledby="exampleMemberLabel" aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">Project
									Member</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<div class=" d-flex justify-content-center mt-2">
									<div style="height: 500px; width: 500px;"
										class="shadow overflow-auto" id="left2">
										<h3 class="text-center">Employee</h3>
										<ul class="list-group overflow-auto">
											<c:forEach items="${ employees }" var="employee">
												<button type="button"
													class="listItem list-group-item list-group-item-action mb-1"
													data-employee-id="${ employee.employeeId }">${ employee.employeeName}
												</button>
											</c:forEach>
										</ul>
									</div>
									<div style="width: 50px; height: 500px;"
										class="d-flex flex-column align-items-center justify-content-center mx-1">
										<button id="toRight2" class="btn btn-outline-primary toRight">
											>></button>
										<button id="toLeft2" class="btn btn-outline-primary toLeft">
											<<</button>
									</div>
									<div style="height: 500px; width: 500px;" class="shadow"
										id="right2">
										<h3 class="text-center">Projrct Member</h3>
										<ul class="list-group"></ul>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-bs-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary"
									data-bs-dismiss="modal" onclick="saveProjectMember()">Save
									changes</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- Project Start -->
			<div class="col-md-6">
				<label for="validationDefault01" class="form-label">projectStartDate</label>
				<input type="date" class="form-control" id="projectStartDate"
					name="projectStartDate" required>
			</div>

			<!-- Project End -->
			<div class="col-md-6 mb-2">
				<label for="validationDefault01" class="form-label">projectEndDate</label>
				<input type="date" class="form-control" id="projectEndDate"
					name="projectEndDate" required>
			</div>
			<div class="col-12 d-flex justify-content-center ">
				<button class="btn btn-secondary col-12" type="button" onclick="addproject()" id="submitBtn">Submit Form</button>
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

    //----------------projectOwner----------------------//
    
	let projectOwner = [];  // 儲存 Member 的 ID
	let projectOwner2 = []; // 儲存 Member 的 Name

    $('.listItem').on('click',function() {
       $(this).toggleClass('active');
    });

    $('#toRight').on('click',function() {
        $('#left .active').each(function() {
            $('#right').append($(this)[0]);
            //加入至右邊欄位
            projectOwner.push($(this).eq(0).attr("data-employee-id"));
            projectOwner2.push($(this)[0].innerText);
        });
    });

    $('#toLeft').on('click',function() {
        $('#right .active').each(function() {
            $('#left').append($(this)[0]);
            //移除還原至左邊欄位
            let ownerstr = $(this).eq(0).attr("data-employee-id");
            let ownerstr2 = $(this)[0].innerText;
            projectOwner = projectOwner.filter(owner => owner !== ownerstr);
            projectOwner2 = projectOwner2.filter(owner => owner !== ownerstr2);
        });
    });
    
    //儲存至框格內
    function saveProjectOwner() {
    	$('#projectOwner').val(projectOwner);
    	$('#projectOwner2').val(projectOwner2);
    }

    //----------------projectMember----------------------//
    
    let projectMember = [];  // 儲存 Member 的 ID
    let projectMember2 = []; // 儲存 Member 的 Name

    $('#toRight2').on('click',function() {
        $('#left2 .active').each(function() {
            $('#right2').append($(this)[0]);
            //加入至右邊欄位
            projectMember.push($(this).eq(0).attr("data-employee-id"));
            projectMember2.push($(this)[0].innerText);
        });
    });

    $('#toLeft2').on('click',function() {
        $('#right2 .active').each(function() {
            $('#left2').append($(this)[0]);
            //移除還原至左邊欄位
            let memberstr = $(this).eq(0).attr("data-employee-id");
            let memberstr2 = $(this)[0].innerText;
            projectMember = projectMember.filter(memeber => memeber !== memberstr);
            projectMember2 = projectMember2.filter(memeber => memeber !== memberstr2);
        });
    });
    
    function saveProjectMember() {
    	$('#projectMember').val(projectMember);
    	$('#projectMember2').val(projectMember2);
    }
    
  //----------------selectProject----------------------//
    function selectProject(event) {
    	let elementId = event.target.value;
    	$('tbody').addClass('d-none');
    	$('#'+elementId).removeClass('d-none');
    	$('#'+elementId).addClass('d-block');
    }
    
  //----------------addProject----------------------//
  	function addproject() {
    // Get form data
    let formData = {
        "projectId": $('#projectId').val(),
        "projectName": $('#projectName').val(),
        "projectContent": $('#projectContent').val(),
        "projectOwner": $('#projectOwner').val(),
        "projectMember": $('#projectMember').val(),
        "projectStartDate": $('#projectStartDate').val(),
        "projectEndDate": $('#projectEndDate').val(),
    };

    // Using AJAX to submit the form data
    $.ajax({
        type: 'POST',
        url: '/JavaWebProject_james/mvc/project/addproject',
        data: formData,
        success: function (response) {
        	location.reload("redirect:/mvc/project");
            alert('專案提交成功');
        },
        error: function (error) {
            alert('專案提交失敗');
        }
    });
}
    
  //----------------deleteProject----------------------//
    function deleteProject(projectId) {
        if (confirm("確定要刪除專案嗎？")) {
            window.location.href = '/JavaWebProject_james/mvc/project/cancelproject/' + projectId;
        }
    }
   
  //----------------updateProject----------------------//
    function updateProject(projectId) {
	  
	  	let field = {
	  		"projectId": projectId,
	  		"projectName": $('#upadte_projectName_' + projectId).val(),
	  		"projectContent": $('#upadte_projectContent_' + projectId).val(),
	  		"projectOwner": $('#upadte_projectOwner_' + projectId).val(),
	  		"projectMember": $('#update_projectMember_' + projectId).val(),
	  		"projectStartDate": $('#upadte_projectStartDate_' + projectId).val(),
	  		"projectEndDate": $('#upadte_projectEndDate_' + projectId).val(),
	  	};
	  	
	  	console.log(projectId);
	  	
	    // 使用 AJAX 向後端傳遞資料
	    $.ajax({
	        type: 'POST', 
	        url: '/JavaWebProject_james/mvc/project/' + projectId + '/updateproject',
	        data: field,
	        success: function (response) {
	            // 根據後端的回應執行適當的操作
	            alert('專案更新成功');
	            // 刷新頁面或執行其他操作
	            location.reload();
	        },
	        error: function (error) {
	            alert('專案更新失敗');
	        }
	    });
	}
  
  	//----------------update projectMember----------------------//
	var update_project = '';
	var update_projectMember = [];  // 儲存 Member 的 ID
	var update_projectMember2 = []; // 儲存 Member 的 Name
	
	function openModalMember(projectId) {
		update_project = projectId;
		
		// 找到指定 ID 的元素，並透過val()獲取其值用逗號隔開
    	update_projectMember = $('#update_projectMember_'+update_project).val().split(',');
    	update_projectMember2 = $('#update_projectMember2_'+update_project).val().split(',');

    	// 加入員工至右邊欄位
    	$('#left4 .listItem').each(function() {
    		
    		// 獲取選中的按鈕"data-employee-id" 屬性的值
    		let employeeId = $(this).eq(0).attr("data-employee-id");
    		
    		// 將當前元素移動到 id 為 "right4" 的元素的內部，並放置在已有內容的末尾
    		if(update_projectMember.includes(employeeId)) {
    	        $('#right4').append($(this)[0]);
    		}
	    });
    	
        // 移除員工至左邊欄位
    	$('#right4 .listItem').each(function() {
    		let employeeId = $(this).eq(0).attr("data-employee-id");
    		
    		// 如果 update_projectMember 陣列中不包含 employeeId，移回left4。
    		if(!update_projectMember.includes(employeeId)) {
    			$('#left4').append($(this)[0]);
    		}
	    });
        
    	var myModal = new bootstrap.Modal(document.getElementById('update_exampleMember'));
    	myModal.show();
  	}
    
	
	// 將選中的元素從左邊的欄位移動到右邊的欄位，同時更新相關的資料結構
	$('#toRight4').on('click',function() {
	    $('#left4 .active').each(function() {
	        $('#right4').append($(this)[0]);
	        
	        // 添加屬性至右邊欄位
	        update_projectMember.push($(this).eq(0).attr("data-employee-id"));
	        update_projectMember2.push($(this)[0].innerText);
	    });
	});
	
	// 將選中的元素從左邊的欄位移動到左邊的欄位，同時更新相關的資料結構
	$('#toLeft4').on('click',function() {
	    $('#right4 .active').each(function() {
	        $('#left4').append($(this)[0]);
	        
	        //還原屬性至左邊欄位
	        let memberstr = $(this).eq(0).attr("data-employee-id");
	        let memberstr2 = $(this)[0].innerText;
	        update_projectMember = update_projectMember.filter(memeber => memeber !== memberstr);
	        update_projectMember2 = update_projectMember2.filter(memeber => memeber !== memberstr2);
	    });
	});

	function updateProjectMember() {
		$('#update_projectMember_'+update_project).val(update_projectMember);
		$('#update_projectMember2_'+update_project).val(update_projectMember2);
		console.log(update_projectMember);
		console.log(update_projectMember2);
	}
  
  	//表單驗證(Bootstrap)
	// Example starter JavaScript for disabling form submissions if there are invalid fields
	(function () {
	  'use strict'

	  // Fetch all the forms we want to apply custom Bootstrap validation styles to
	  var forms = document.querySelectorAll('.needs-validation')

	  // Loop over them and prevent submission
	  Array.prototype.slice.call(forms)
	    .forEach(function (form) {
	      form.addEventListener('submit', function (event) {
	        if (!form.checkValidity()) {
	          event.preventDefault()
	          event.stopPropagation()
	        }

	        form.classList.add('was-validated')
	      }, false)
	    })
	})()
    
</script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

