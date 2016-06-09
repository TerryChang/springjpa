<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>메인화면</title>
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
	$(document).ready(function(){
		
	});
	</script>
</head>
<body>
	<div class="container">
		<!-- Page Header Start -->
		<div class="page-header">
	 		<h1>메인 화면</h1>
		</div>
		<div>
			<sec:authorize access="isAnonymous()">
			<a href="/login.do">로그인</a>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
			로그인 id : ${member.username}<br/>
			email : ${member.email}<br/>
			권한 : ${member.authorities}<br/>
			<a href="/logout.do">로그아웃</a>
			</sec:authorize>
		</div>
		<div>
			<div>메뉴</div>
			<div>
				<sec:authorize url="/unitedBoard/unitedBoardList.do?boardTypeIdx=1">
				공지사항
				</sec:authorize>
				<sec:authorize access="hasRole('MEMBER')">
				회원게시판
				</sec:authorize>
				<sec:authorize url="/unitedBoard/unitedBoardList.do?boardTypeIdx=3">
				까페게시판
				</sec:authorize>
				<sec:authorize url="/unitedBoard/unitedBoardList.do?boardTypeIdx=4">
				질문게시판
				</sec:authorize>
			</div>
		</div>
	</div>
</body>
</html>