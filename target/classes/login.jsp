<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!--接錯誤訊息  -->
<%
	String error = (String)request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="./image/weblogo.png">
<link rel="stylesheet" href="./styles/style.css">
<style>
      body {
             font-family: Arial, sans-serif;
             background-color: #f4f4f4;
             margin: 0;
             padding: 0;
         }
      form {
       background-color: #fff;
             padding: 20px;
             padding-top: 40px;
             padding-bottom: 10px;
             border-radius: 8px;
             box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
             width: 500px;
             height: 350px;
      }
     </style>
</head>
<body>
	<div class="d-flex justify-content-center align-items-center vh-100">
	<form class="needs-validation" novalidate method="post" action="./login">
	<h4 class="text-center">Login</h4>
	<%
		if(error!=null){
			out.println("<p class='text-center text-danger font-bold'>"+error+"</p>");
		}
	%>
		<div class="">
			<label for="username" class="form-label">User name</label>
			<input type="text" class="form-control"
				id="username" name="username" value="" required>
			<div class="invalid-feedback">請輸入帳號!</div>
		</div>
		<div class="">
			<label for="password" class="form-label">Password</label>
			<input type="password" class="form-control"
				id="password" name="password" value="" required>
			<div class="invalid-feedback">請輸入密碼!</div>
		</div>
		<div class="d-flex justify-content-center mt-3">
			<button class="btn btn-primary" type="submit">登入</button>
		</div>
	</form>
	</div>
</body>
</html>

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


