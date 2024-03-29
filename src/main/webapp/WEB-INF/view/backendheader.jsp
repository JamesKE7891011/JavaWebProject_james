<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Layout</title>
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
	    <link rel="shortcut icon" href="./images/icon.png" type="image/x-icon"/>
	    <link rel="stylesheet" href="./styles/style.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #3F6458!important">
  <div class="container-fluid">
    <a class="navbar-brand text-light" href="#">趣~追蹤專案系統</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active text-light" aria-current="page" href="${ pageContext.request.contextPath }/mvc/project">趣專案</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-light" href="${ pageContext.request.contextPath }/mvc/schedule">趣進度表</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-light" href="${ pageContext.request.contextPath }/mvc/main">趣前台</a>
        </li>
      </ul>
    </div>
    <i class="bi bi-person-circle fs-4 me-4 text-light d-none d-lg-block">${sessionScope.username}</i>
    <i class="bi bi-box-arrow-right fs-5 text-light ml-3" role="button" onclick="window.location.href='${ pageContext.request.contextPath }/logout'">登出</i>
  </div>
</nav>
</body>
</html>