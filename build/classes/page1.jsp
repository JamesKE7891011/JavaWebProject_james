<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/header.jsp" %>

<div class="mx-5 mt-4 vh-100">
	<h4 class="fw-bold fs-3 text-center ">假單申請</h4>
	<form class="w-80 needs-validation" method="post" action="./page1" novalidate >
  		<div class="mb-3">
    		<label for="employeename" class="form-label">員工姓名</label>
    		<input type="text" class="form-control" id="employeename" name="employeename" required>
  	    	<div class="invalid-feedback">請輸入員工姓名!</div>
  	    </div>
  		<div class="mb-3">
    		<label for="email" class="form-label">Email</label>
    		<input type="text" class="form-control" id="email" name="email" required>
  	    	<div class="invalid-feedback">請輸入Email!</div>
  	    </div>
  		<div class="mb-3">
  			<label for="selectleave" class="form-label" id="selectleave" name="selectleave">請選擇假別...</label>
   	 		<select class="form-select" required aria-label="select example">         
     			<option value="">請選擇</option>
      			<option value="事假">事假</option> 
      			<option value="病假">病假</option>
     			<option value="特休">特休</option>
     			<option value="補休">補休</option>
     			<option value="其他(事由請於備註欄中填寫)">其他(事由請於備註欄中填寫)</option>
    		</select>
    		<div class="invalid-feedback ">請選擇假別!</div>
  		</div>
		<div class="mb-3 mt-1">
  			<label for="formFile" class="form-label">備註檔案上傳</label>
  			<input class="form-control" type="file" id="formFile">
		</div>
		<div class="mb-3 mt-2">
  			<label for="formnote" class="form-label">備註欄(請簡單敘述原因:)</label>
    		<textarea class="form-control" id="id="formnote" name="formnote" " placeholder="無特殊原因請填寫與假別內容相同即可" required></textarea>
			<div class="invalid-feedback ">請備註!</div>
		</div>
		<div class="d-flex justify-content-center mb-1 mt-3">
		<button type="reset" class="mx-4 col-3 btn btn-danger ">清除</button>
		<button type="submit" class="col-3 btn btn-secondary ">提交</button>
		</div>
	</form>
</div>


<%@ include file="/WEB-INF/view/footer.jsp" %>


