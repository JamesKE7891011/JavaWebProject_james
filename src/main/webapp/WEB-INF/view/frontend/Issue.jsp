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
		<form class="w-80 needs-validation" id ="issueForm" name="issueForm" enctype="multipart/form-data" novalidate >
			<div class="mb-1">
				<label for="issuename" class="form-label" >選擇專案</label>
				<select class=" form-select  w-75" id="projectId" name="projectId"
					aria-label="Default select example" onchange="selectProject(event)" required>
					<option selected disabled value=""> Please choose project...</option>
					<c:forEach items="${ projects }" var="project">
						<option  value="${ project.projectId }">${ project.projectId } ${ project.projectName }</option>
					</c:forEach>
				</select>
			</div>
		 	<div class="mb-1 mt-1">
				<label for="issueName" class="form-label" >議題名稱</label>
				<input class="form-control" list="datalistOptions" id="issueName" name="issueName" required>
  	    		<div class="invalid-feedback w-75">請輸入議題名稱!</div>
  	    		
  	    	</div>
  			<div class="mb-1">
  				<label for="issueClass" class="form-label mt-2" >議題類別</label>
   	 			<select class="form-select" aria-label="Default select example" onchange="selectIssueClass(event)"
   	 			id="issueClassId" name="issueClassId" required>
   	 				<option selected disabled value="">Please choose issue class...</option>
   	 				<c:forEach var="issueClass" items="${ issueClasses }" >       
	      				<option value="${ issueClass.issueClassId }"> ${ issueClass.issueClassName }</option> 
      				</c:forEach>  
    			</select>
    			<div class="invalid-feedback ">請選擇議題!</div>
  	    	</div>
			<div class="mt-3 mb-3">
  				<label for="issueFile" class="form-label">備註檔案上傳</label>
  				<input class="form-control" type="file" id="issueFilePath" name="issueFilePath" multiple="multiple">
			</div>
			<div class="mb-1">
  				<label for="issuecontent" class="form-label">議題內容(請敘述原因:)</label>
    			<textarea class="form-control" id="issueContent" name="issueContent" " placeholder="" required></textarea>
				<div class="invalid-feedback ">請備註!</div>
			</div>
			<div class="col-12 d-flex justify-content-center mt-2">
				<button class="btn btn-secondary col-12" type="submit" onclick="submitForm()">Submit Form</button>
			</div>
		</form>
	</div>
	
	<!-- 專案下拉選單 -->
	<div class="col-5 m-3">
		<h4 class="fs-4 fw-bolld">Find Issue</h4>
		<div class="d-flex justify-content-start">
				<select class=" form-select  w-75"
					aria-label="Default select example" onchange="selectProject(event)">
					<option selected disabled value=""> Please choose project...</option>
					<c:forEach items="${ projects }" var="project">
						<option value="${ project.projectId }">${ project.projectId } ${ project.projectName }</option>
					</c:forEach>
				</select>
		</div>
	</div>
	
	<!-- Issue顯示狀態列 -->
	<div>
		<table class="table table-hover text-center">
			<thead>
   				<tr>
     				<th scope="col">IssueID:</th>
      				<th scope="col">IssueName:</th>
      				<th scope="col">IssueClass:</th>
      				<th scope="col">IssueContent:</th>
      				<th scope="col">IssuePath:</th>
      				<th scope="col">IssueDatetime:</th>
      				<th scope="col">IssueStatus:</th>
      				<th scope="col">-> Change</th>
      				<th scope="col">Delete:</th>
   				</tr>
		  	</thead>
  			<tbody id="issue_table">
    			<tr></tr>    			
  			</tbody>
		</table>
	</div>
	
</div>


<%@ include file="/WEB-INF/view/footer.jsp" %>

<script>

	function getStringCommaSeparated(jsonData, propertyName) {
	    var propertyArray = jsonData.map(function(item) {
	        return item[propertyName];
	    });

	    return propertyArray.join('<br>');
	}
	
	function formatDate(date) {
		  return (
		    [
		      date.getFullYear(),
		      padTo2Digits(date.getMonth() + 1),
		      padTo2Digits(date.getDate()),
		    ].join('-') +
		    ' ' +
		    [
		      padTo2Digits(date.getHours()),
		      padTo2Digits(date.getMinutes()),
		      padTo2Digits(date.getSeconds()),
		    ].join(':')
		  );
	}
	
	function padTo2Digits(num) {
		  return num.toString().padStart(2, '0');
	}
	
	function formatDateTime(timestamp) {
		let date = new Date(timestamp);
		return formatDate(date);
	}
	
	function selectProject(event) {
		
		$('#issue_table  tr').remove();
		
		let projectId = event.target.value;
		
		fetch('/JavaWebProject_james/mvc/issue/'+projectId, {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
			}
		})
		.then(response => response.json())
		.then(data => {
			 $.each(data, function( index, issue ) {
				 //console.log(issue);
				 let issueFilePath = getStringCommaSeparated(issue.issueFiles,"issueFilePath");
				 let issueDateTime = formatDateTime(issue.issueDateTime);
				 
				 let tr = `
				 	<tr>
				 		<td>\${issue.issueId}</td>
				 		<td>\${issue.issueName}</td>
				 		<td>\${issue.issueClassId}</td>
				 		<td>\${issue.issueContent}</td>
				 		<td>
				 		<!--<a class="btn btn-danger" href="/JavaWebProject_james/mvc/issue/download/${issue.issueId}">
		                \${issueFilePath}
		            	</a>-->
				 			<button class="btn btn-danger" onclick="downloadFile(${issue.issueId})">\${issueFilePath}</button>
				 		</td>
				 		<td>\${issue.issueDateTime}</td>
				 		<td >\${issue.issueStatus}</td>
				 		<td><button class="btn btn-primary" onclick="closeIssue(\${issue.issueId},\${issue.issueStatus})" id="revise_"+\${issue.issueId}>I/O</button></td>
				 		<td><button class="btn btn-danger" onclick="deleteIssue(\${issue.issueId})">刪除</button></td>
				 	</tr>
				 `;
				 console.log(issueFilePath);
				 //console.log(tr);
				 $('#issue_table').append(tr);
			});
		});
	}
	
	//使用下拉選單查詢專案
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
	
	//下載issue檔案
	function downloadFile(issueId) {
        // 构建下载链接
        var downloadLink = '/JavaWebProject_james/mvc/issue/download/' + issueId;

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
	
	//為專案新增issue
	function submitForm() {
	    // 獲取其他需要檢查的表單元素
	    var projectId = document.getElementById('projectId');
	    var issueName = document.getElementById('issueName');
	    var issueClassId = document.getElementById('issueClassId');
	    var issueContent = document.getElementById('issueContent');

	    // 獲取檔案上傳元素
	    var issueFilePath = document.getElementById('issueFilePath');

	    // 檢查其他表單元素是否為空
	    if (projectId.value.trim() === '' || issueName.value.trim() === '' || issueClassId.value.trim() === '' || issueContent.value.trim() === '') {
	        return;
	    }

	        var form = $('#issueForm')[0];
	        var formData = new FormData(form);

	        $.ajax({
	            type: 'POST',
	            url: '/JavaWebProject_james/mvc/issue/addissue',
	            data: formData,
	            processData: false,
	            contentType: false,
	            success: function (response) {
	                // Check if the submission was successful
	                location.reload("redirect:/mvc/issue");
	                alert('專案提交成功');
	            },
	            error: function (error) {
	                console.error('提交失敗：', error);
	                alert('提交失敗！');
	            }
	        });	     
	}
	
	//刪除專案內issue
	function deleteIssue(issueId) {
	    if (confirm("確定要刪除這個議題嗎？")) {
	        $.ajax({
	            type: 'GET',
	            url: '/JavaWebProject_james/mvc/issue/cancelissue/' + issueId,
	            success: function (data) {
	                alert('issue刪除成功!');
	                reload(); // 成功刪除後重新載入議題列表
	            },
	            error: function (error) {
	                console.error('刪除失敗：', error);
	                alert('刪除失敗！');
	            }
	        });
	    }
	}
	
	
	function closeIssue(issueId, issueStatus) {
	    console.log('Issue Id = ', issueId);
	    console.log('Issue Status = ', issueStatus);

	    // 判斷 issueStatus 是否為 1
	    if (issueStatus === 1) {
	        // 向後端發送更新請求
	        $.ajax({
	            type: 'PUT',
	            url: '/JavaWebProject_james/mvc/issue/' + issueId + '/updateissuestatus',
	            contentType: 'application/json',  // 設置請求的內容類型
	            data: JSON.stringify({ issueStatus: 0 }),  // 將數據轉換為 JSON 格式
	            success: function (response) {
	                // 更新成功後的處理
	                console.log('Issue Status 更新成功:', response);
	                alert('Issue Status 更新成功!');
	                // 如果需要刷新頁面或進行其他處理，可以在這裡添加代碼
	                // 例如，重新載入議題列表
	                selectProject({ target: { value: $('#projectId').val() } });
	            },
	            error: function (error) {
	                console.error('Issue Status 更新失敗:', error);
	                alert('Issue Status 更新失敗！');
	            }
	        });
	    } else {
	        // 如果 issueStatus 不為 1，將其設置為 1
	        // 向後端發送更新請求
	        $.ajax({
	            type: 'PUT',
	            url: '/JavaWebProject_james/mvc/issue/' + issueId + '/updateissuestatus',
	            contentType: 'application/json',  // 設置請求的內容類型
	            data: JSON.stringify({ issueStatus: 1 }),  // 將數據轉換為 JSON 格式
	            success: function (response) {
	                // 更新成功後的處理
	                console.log('Issue Status 更新成功:', response);
	                alert('Issue Status 更新成功!');
	                // 如果需要刷新頁面或進行其他處理，可以在這裡添加代碼
	                // 例如，重新載入議題列表
	                selectProject({ target: { value: $('#projectId').val() } });
	            },
	            error: function (error) {
	                console.error('Issue Status 更新失敗:', error);
	                alert('Issue Status 更新失敗！');
	            }
	        });
	    }
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


