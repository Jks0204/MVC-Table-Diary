<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<title>비밀번호 확인</title>
<script type="text/javascript">
	function validateForm(form){
		if(form.pass.value==""){
			alert("비밀번호를 입력하세요");
			form.pass.focus();
			return false;
		}
	}
</script>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-secondary fixed-top px-5">
		<div class="form-check form-switch d-flex justify-content-end w-100">
			<label class="form-ckeck-laber me-5 text-white" for="dSwitch"> Dark Mode </label>
			<input class="form-check-input me-5" type="checkbox" id="dSwitch" name="darkmode" value="yes">
			<script>
				const darkSwitch=document.querySelector("#dSwitch");
				let checked = true;
				darkSwitch.onclick=function(){
					if(checked){
						document.querySelector("html").setAttribute("data-bs-theme", "dark");
						checked=false;
					} else {					
						document.querySelector("html").setAttribute("data-bs-theme", "");
						checked=true;
					}
				}
			</script>
		</div>
	</nav>
	<div class="container">
		<h4 class="my-5" align="center">계속하려면 비밀번호를 입력하세요</h4>
		<form name="writeFrm" method="post" action="/Diary/pass.do" onsubmit="return validateForm(this)">
			<input type="hidden" name="no" value="${param.no }"/>
			<input type="hidden" name="mode" value="${param.mode }"/>
			<table class="table table-bordered mt-5" width="90%">
				<tr>
					<td align="center">비밀번호 입력</td>
					<td>
						<input type="password" name="pass" style="width:95%;">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button class="btn btn-outline-primary">입력완료</button>
						<button type="reset" class="btn btn-outline-warning">다시작성</button>
						<button type="button" class="btn btn-outline-info" onclick="location.href='/Diary/list.do'">목록으로</button>
					</td>
				</tr>
			
			</table>
		</form>
	</div>
</body>
</html>