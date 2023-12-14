<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/backendheader.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/backendheader.jsp"%>

<div class="vh-100">
	<div>
		<form class="m-2 row g-3 ">
			<div class="col-md-3">
				<label for="validationDefault01" class="form-label">姓名</label> <input
					type="text" class="form-control" id="validationDefault01" required>
			</div>
			<div class="col-md-2">
				<label for="validationDefault04" class="form-label">性別</label>
				<div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio"
							name="inlineRadioOptions" id="inlineRadio1" value="option1">
						<label class="form-check-label" for="inlineRadio1">男</label>
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio"
							name="inlineRadioOptions" id="inlineRadio2" value="option2">
						<label class="form-check-label" for="inlineRadio2">女</label>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<label for="validationDefault01" class="form-label">身分證字號</label> <input
					type="text" class="form-control" id="validationDefault01" required>
			</div>
			<div class="col-md-2">
				<label for="validationDefault01" class="form-label">血型</label> <input
					type="text" class="form-control" id="validationDefault01" required>
			</div>
			<div class="col-md-5">
				<label for="validationDefault01" class="form-label">電話</label> <input
					type="text" class="form-control" id="validationDefault01" required>
			</div>
			<div class="col-md-5">
				<label for="validationDefault01" class="form-label">E-mail</label> <input
					type="text" class="form-control" id="validationDefault01" required>
			</div>
			<div class="col-md-6">
				<label for="validationDefault03" class="form-label">現居地</label> <input
					type="text" class="form-control" id="validationDefault03" required>
			</div>
			<div class="col-md-6">
				<label for="validationDefault03" class="form-label">戶籍地</label> <input
					type="text" class="form-control" id="validationDefault03" required>
			</div>
			<div class="col-md-3">
				<label for="validationDefault01" class="form-label">緊急聯絡人</label> <input
					type="text" class="form-control" id="validationDefault01" required>
			</div>
			<div class="col-md-3">
				<label for="validationDefault01" class="form-label">關係</label> <input
					type="text" class="form-control" id="validationDefault01" required>
			</div>
			<div class="col-md-3">
				<label for="validationDefault01" class="form-label">緊急聯絡人電話</label>
				<input type="text" class="form-control" id="validationDefault01"
					required>
			</div>
			<div class="col-12">
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value=""
						id="invalidCheck2" required> <label
						class="form-check-label" for="invalidCheck2"> Agree to
						terms and conditions </label>
				</div>
			</div>
			<div class="col-12">
				<button class="btn btn-primary" type="submit">Submit form</button>
			</div>
		</form>
	</div>

	<div class="mx-2 my-2">
		<h4 class="fw-bold fs-4 text-start ">員工查詢</h4>
		<div class="w-50 d-flex">
			<input class="form-control  me-2" type="search" placeholder="Search"
				aria-label="Search">
			<button class="btn btn-outline-success" type="submit">Search</button>
		</div>
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th scope="col">姓名</th>
						<th scope="col">性別</th>
						<th scope="col">身份證字號</th>
						<th scope="col">血型</th>
						<th scope="col">電話</th>
						<th scope="col">E-MAIL</th>
						<th scope="col">現居地</th>
						<th scope="col">戶籍地</th>
						<th scope="col">緊急聯絡人</th>
						<th scope="col">關係</th>
						<th scope="col">緊急聯絡人電話</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row">王XX</th>
						<td>男</td>
						<td>A123456789</td>
						<td>A</td>
						<td>09XX-XXX-XXX</td>
						<td>XXXXX@gmail.com</td>
						<td>台北市中山區XX路XX巷XX號XX樓</td>
						<td>台北市中山區XX路XX巷XX號XX樓</td>
						<td>王XX</td>
						<td>父子</td>
						<td>09XX-XXX-XXX</td>
					</tr>
			</table>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/view/backendfooter.jsp"%>

<%@ include file="/WEB-INF/view/backendfooter.jsp" %>