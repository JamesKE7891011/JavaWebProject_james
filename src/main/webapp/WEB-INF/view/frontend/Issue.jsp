<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="/WEB-INF/view/header.jsp"%>

<div class="mx-5 mt-4 row " style="min-height: 100vh">
	<!-- 議題新增 -->
	<div class=" ms-3 w-75">
		<h4 class="fw-bold fs-2 text-center ">ISSUE</h4>
		<form class="w-80 needs-validation" id="issueForm" name="issueForm" enctype="multipart/form-data" novalidate>
			<div class="mb-1">
				<h5 class="mb-2 form-label fs-4 fw-bolld">Project Select</h5> 
				<select class="mt-2 form-select w-75" id="projectId" name="projectId" aria-label="Default select example" 
						onchange="selectProject(event.target.value)" required>
					<option selected disabled value="">Please choose project...</option>
					<c:forEach items="${ projects }" var="project">
						<option value="${ project.projectId }">${ project.projectId } ${ project.projectName }</option>
					</c:forEach>
				</select>
			</div>
			<div class="mb-1 mt-2">
				<label for="issueName" class="form-label">議題名稱</label> <input
					class="form-control" list="datalistOptions" id="issueName"
					name="issueName" required>
				<div class="invalid-feedback w-75">請輸入議題名稱!</div>

			</div>
			<div class="mb-1">
				<label for="issueClass" class="form-label mt-2">議題類別</label> 
				<select class="form-select" aria-label="Default select example" onchange="selectIssueClass(event)" 
				  		id="issueClassId" name="issueClassId" required>
					<option selected disabled value="">Please choose issue class...</option>
					<c:forEach var="issueClass" items="${ issueClasses }">
						<option value="${ issueClass.issueClassId }">${ issueClass.issueClassName }</option>
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
				<textarea class="form-control" id="issueContent" name="issueContent" required></textarea>
				<div class="invalid-feedback ">請敘述原因!</div>
			</div>
			<div class="col-12 d-flex justify-content-center my-3">
				<button class="btn btn-secondary col-12" type="button"
					onclick="submitForm()">Submit Form</button>
			</div>
		</form>
	</div>

	<!-- Issue顯示狀態列 -->
	<div class="mx-2">
		<h5 class="mt-2 form-label fs-4 fw-bolld">Issue Details</h5> 
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


<%@ include file="/WEB-INF/view/footer.jsp"%>

<script>
	
	//檔案上傳顯示
	function getStringCommaSeparated(jsonData, propertyName) {
	    var propertyArray = jsonData.map(function(item) {
	        return item[propertyName];
	    });

	    return propertyArray.join('<br>');
	}
	
	//日期設定
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
	
	/* 重新選擇專案，並重新取得該專案下的所有Issue */
	function selectProject(projectId) {
		
		$('#issue_table  tr').remove();
		
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
				 //let issueFilePath = getStringCommaSeparated(issue.issueFiles,"issueFilePath");
				 let issueDateTime = formatDateTime(issue.issueDateTime);
				 let issueId = issue.issueId;
				 let issueStatus = issue.issueStatus;
				
				 
				// 生成每個檔案路徑的按鈕陣列
		         let fileButtons = issue.issueFiles.map(function(file) {
		         	return '<button class="btn btn-primary ms-2 mt-2 text-start" onclick="downloadFile(' + file.issueFileId + ')">' + file.issueFilePath + '</button>';
		         });
				 
				 let tr = `
				 	<tr>
				 		<td>\${issue.issueId}</td>
				 		<td>\${issue.issueName}</td>
				 		<td>\${issue.issueClassId}</td>
				 		<td>\${issue.issueContent}</td>
				 		<td class="text-start">
				 		    <!-- \${issue.issueFiles.length > 0 ? '<button class="btn btn-primary" onclick="downloadFile('+issueId+')">'+issueFilePath+'</button>' : ''} -->
				 		    \${fileButtons.join('')}
				 		</td>
				 		<td>\${issue.issueDateTime}</td>
				 		<td>
				 			\${issue.issueStatus == 1 ? 'Open': 'Close'}
				 		</td>
				 		<td>
				 		    \${issue.issueStatus == 1? 
				 		    		'<button title="Close" class="btn btn-danger" onclick="closeIssue('+issueId+','+issueStatus+')" id="revise_"'+issueId+'>I/O</button>':
				 		    		'<button title="Open" class="btn btn-success" onclick="closeIssue('+issueId+','+issueStatus+')" id="revise_"'+issueId+'>I/O</button>'
				 		     } 
				 			
				 		</td>
				 		<td><button class="btn btn-danger" onclick="deleteIssue(\${issue.issueId},'\${issue.projectId}')">刪除</button></td>
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
	function downloadFile(issueFileId) {
        // 构建下载链接
        var downloadLink = '/JavaWebProject_james/mvc/issue/download/' + issueFileId;

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
                alert('專案提交成功');
                location.reload("redirect:/mvc/issue");
            },
            error: function (error) {
                console.error('提交失敗：', error);
                alert('提交失敗！');
            }
        });

	}
	
	//刪除專案內issue
	function deleteIssue(issueId,projectId) {
	    if (confirm("確定要刪除這個議題嗎？")) {
	        $.ajax({
	            type: 'GET',
	            url: '/JavaWebProject_james/mvc/issue/cancelissue/' + issueId,
	            success: function (data) {
	                alert('issue刪除成功!');
	                selectProject(projectId);
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

	    let newIssueStatus = (issueStatus == 1) ? 0 : 1;
	    console.log('New Issue Status = ', newIssueStatus);
	    
	 	// 向後端發送更新請求
        $.ajax({
            type: 'PUT',
            url: '/JavaWebProject_james/mvc/issue/' + issueId + '/updateissuestatus',
            contentType: 'application/json',  // 設置請求的內容類型
            data: JSON.stringify({ newIssueStatus }),
            success: function (response) {
                // 更新成功後的處理
                console.log('Issue Status 更新成功:', response);
                alert('Issue Status 更新成功!');
                selectProject( $('#projectId').val());
            },
            error: function (error) {
                console.error('Issue Status 更新失敗:', error);
                alert('Issue Status 更新失敗！');
            }
        });
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


