<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>게시판 종류 등록</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<!-- 부트스트랩 -->
	<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<style>
		.col-xs-offset-5, .col-xs-2, .col-cs-offset-5, .col-lg-offset-5, .col-lg-2, .col-lg-offset-5 {
  			border: 0px solid red;
		}
    </style>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		// ajax 작업시 캐쉬를 사용하지 않도록 한다
		$.ajaxSetup({ 
			cache: false
		});
		$("#btnRegist").click(function(){
			// $("#regfrm").submit();
			
			var boardTypeName = $("#boardTypeName").val();
			var url = $("#url").val();
			
			$.ajax({
	        	url : "/boardType/boardTypeInsert.do",
	        	type : "POST",
	        	data : JSON.stringify({"boardTypeName" : boardTypeName, "url" : url}),
	        	// data : formSerialize,
	        	contentType: "application/json",	// contentType으로 지정하면 Request Body로 전달되기 때문에 Spring에서 받을때 다르게 접근해야 한다
	        	// processData: false,
	        	dataType : "json",
	        	beforeSend : function(xhr){
	        		var validationResult = true;
	        		if($.trim($("#boardTypeName").val()) == ""){
						alert("<spring:message code='NotBlank.boardType.boardTypeName' />");
						$("#boardTypeName").focus();
						validationResult = false;
	        		}else if(($("#boardTypeName").val().length < 3) || ($("#boardTypeName").val().length > 10)){
						var alertMsg = "<spring:message code='Size.boardType.boardTypeName' />";
						alertMsg = alertMsg.replace("\{2\}", "3");
						alertMsg = alertMsg.replace("\{1\}", "10");
						alert(alertMsg);
						$("#boardTypeName").focus();
						validationResult = false;
					}else if($.trim($("#url").val()) == ""){
						alert("<spring:message code='NotBlank.boardType.url' />");
						$("#url").focus();
						validationResult = false;
					}else if(($("#url").val().length < 10) || ($("#url").val().length > 100)){
						var alertMsg = "<spring:message code='Size.boardType.url' />";
						alertMsg = alertMsg.replace("\{2\}", "10");
						alertMsg = alertMsg.replace("\{1\}", "100");
						alert(alertMsg);
						$("#url").focus();
						validationResult = false;
					}
	        		return validationResult;
					
	        	},
	        	success:function(data, textStatus, jqXHR){
	        		alert("<spring:message code='registOK' />");
	        		location.href="/boardType/boardTypeList.do";
	        	},
	        	error:function(jqXHR, textStatus, errorThrown){
	        		// console.log("error");
	        		
	        		// console.log(jqXHR);
	        		// console.log(textStatus);
	        		// console.log(errorThrown);
	        		
	        		// 에러메시지를 표현하는 span 태그를 모두 초기화를 시켜준다
	        		// 에러메시지를 표현하는 span 태그는 id가 ErrorMsg로 끝나는 것들이기 때문에 이것들만 찾아서 빈 문자열로 초기화 시켜주면된다
	        		/*
	        		$("span[id$='ErrorMsg']").html("");
	        		
	        		if(jqXHR.status == 400){
	        			var responseJSON = jqXHR.responseJSON;
		        		
		        		if(responseJSON.hasOwnProperty("result")){		// result property가 있다는 것은 Spring에서 return한 결과 Object이므로 이에 대한 작업을 진행한다
		        			var errorMessageMap = responseJSON.errorMessageMap;
		        			$.each(errorMessageMap, function(k, v){
		        				var spanId = k + "ErrorMsg";
		        				var msg = v.replace(/\n/g, "<br/>");
		        				$("#" + spanId).html(msg);
		        			});
		        		}else{											// result property가 없다는 것은 Spring과는 무관하게 발생한 것을 의미한다
		        			alert("<spring:message code='errorFail' />");
		        		}
	        		}else{
	        			alert("<spring:message code='errorFail' />");
	        		}
	        		*/
	        		if(jqXHR.status == 400){
	        			var responseJSON = jqXHR.responseJSON;
	        			if(responseJSON.hasOwnProperty("result")){		// result property가 있다는 것은 Spring에서 return한 결과 Object이므로 이에 대한 작업을 진행한다
	        				var alertMsg = "";
		        			if(responseJSON.hasOwnProperty("result")){
		        				var errorMessageMap = responseJSON.errorMessageMap;
		        				$.each(errorMessageMap, function(k, v){
		        					alertMsg += v + "\n";
			        			});
		        			}
		        			var pattern = /\n$/;
		        			alertMsg = alertMsg.replace(pattern, "");		// 에러 문자열을 결합하면 마지막 행 끝에 개행문자(\n)가 붙기 때문에 이를 제거하기 위해 정규표현식을 이용해서 마지막에 붙은 개행문자를 제거한다
		        			console.log(alertMsg);
		        			alert(alertMsg);
	        			}else{
	        				alert("<spring:message code='errorFail' />");
	        			}	
	        		}else{
	        			alert("<spring:message code='errorFail' />");
	        		}
	        	}
	        	
	        });
		});
		
		$("#btnReset").click(function(){
			$("#regfrm").reset();
		});
		
		$("#btnList").click(function(){
			// location.href="/notmember/insertUpdateNotMemberBoard.do?idx=" + $("#idx").val()+"&replyYN=Y";
		});
		
		$("#regfrm").submit(function(event){
			event.preventDefault();
		});
	});
	</script>
</head>
<body>
	<div class="container">
		<div class="page-header">
	 		<h1>게시판 종류 등록</h1>
		</div>
		<form:form id="regfrm" method="post" cssClass="form-horizontal" role="form">
		<div class="form-group">
			<label for="name" class="col-xs-2 col-lg-2 control-label">게시판 이름</label>
			<div class="col-xs-10 col-lg-10">
				<input id="boardTypeName" name="boardTypeName" type="text" class="form-control" placeholder="이름">
			</div>
		</div>
		<div class="form-group">
			<label for="url" class="col-xs-2 col-lg-2 control-label">URL</label>
			<div class="col-xs-10 col-lg-10">
				<input id="url" name="url" type="text" class="form-control" placeholder="URL">
			</div>
		</div>
		<div class="form-group text-center">
			<div class="col-xs-offset-4 col-xs-4 col-cs-offset-4 col-lg-offset-4 col-lg-4 col-lg-offset-4">
				<button id="btnRegist" type="button" class="btn btn-default">등록</button>
				<button id="btnReset" type="button" class="btn btn-default">재작성</button>
				<button id="btnList" type="button" class="btn btn-default">목록</button>
			</div>
		</div>
		</form:form>
	</div>
</body>
</html>