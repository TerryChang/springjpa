<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>게시판 종류 목록</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<!-- 부트스트랩 -->
	<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<!-- Page Header Start -->
	<div class="page-header">
 		<h1>게시판 종류 목록</h1>
	</div>
	<form:form id="listfrm" method="get" cssClass="form-horizontal" role="form">
	<!-- Page Header End -->
	<table class="table table-hover table-bordered">
		<thead>
			<tr>
				<th class="col-md-1 text-center">번호</th>
				<th class="col-md-10 text-center">제목</th>
				<th class="col-md-1 text-center">작성일</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="text-center">1</td>
				<td>제목</td>
				<td>2016-01-01</td>
			</tr>
			
		</tbody>
	</table>
	<!-- Pager Start --> 
	<div class="pull-right">
		<button id="btnRegist" type="button" class="btn btn-default">등록</button>
	</div>
	</form:form>
</body>
</html>