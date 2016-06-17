<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>에러화면</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<sec:csrfMetaTags /><!-- Spring Security Meta Tag 추가 -->
	<!-- 부트스트랩 -->
	<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		
	});
	</script>
</head>
<body>
	<div class="container">
		<!-- Page Header Start -->
		<div class="page-header">
	 		<h1>에러 화면</h1>
		</div>
		<div>
			세션 문제가 발생했습니다(${sessionError}). <a href="/main.do">메인 화면</a>으로 이동해주시기 바랍니다<br/>
		</div>
	</div>
</body>
</html>