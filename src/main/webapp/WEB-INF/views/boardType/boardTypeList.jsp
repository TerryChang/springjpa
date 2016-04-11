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
	<div class="container">
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
					<th class="col-md-3 text-center">제목</th>
					<th class="col-md-6 text-center">URL</th>
					<th class="col-md-2 text-center">등록일시</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty result}">			
						<tr>
							<td colspan="4" class="text-center">검색된 결과가 없습니다</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:set var="boardStartNo" value="${fn:length(result)}" />
						<c:forEach var="item" items="${result}" varStatus="status">
							<tr>
								<td class="text-center">${boardStartNo-status.index}</td>
								<td><c:out value="${item.boardTypeName}" escapeXml="false"/></td>
								<td><c:out value="${item.url}" escapeXml="false"/></td>
								<td><spring:eval expression="item.insertUpdateDT.insertDateTime" /></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<!-- Pager Start --> 
		<div class="pull-right">
			<button id="btnRegist" type="button" class="btn btn-default">등록</button>
		</div>
	</form:form>
	</div>
</body>
</html>