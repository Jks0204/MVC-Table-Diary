<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>일기장</title>
<style>
	a{text-decoration: none;}
	a:hover {font-weight:900;}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-secondary fixed-top px-5">
		<div class="form-check form-switch d-flex justify-content-end w-100">
			<label class="form-ckeck-laber me-5 text-white" for="dSwitch"> Dark Mode </label>
			<input class="form-check-input me-5" type="checkbox" id="dSwitch" name="darkmode" value="yes">
			<script>
				$('#dSwitch').click(function(){
					var chkVal=$('#dSwitch:checked').val();
					$.ajax({
						url:"/Diary/darkmode.do",
						type:"get",
						data:{dSwitch:chkVal},
						success:funcD
					})
				});
				
				function funcD (){
					$('html').attr("data-bs-theme","dark");
					if (${cookie.dSwitch.value=="off"}){					
						$('html').removeAttr("data-bs-theme");
					} 
					location.reload();
				};
				
				if(${cookie.dSwitch.value=="on"}){
					$('html').attr("data-bs-theme","dark");
					$('#dSwitch').attr("checked","checked");
				}
				
				// view count 제한 ajax
				$(document).ready(function(){
					var noCount="done";
					$.ajax({
						url:"/Diary/view.do",
						type:"get",
						data:{
							count:noCount,
							ckno:${dto.no}
						}
					});
					
				});
				
				// downcount
				function dCount(){
					$.ajax({
						url:"/Diary/download.do",
						type:"get",
						success:function(){
							$('#dCount').load(location.href+" #dDiv");
						}
						
					});
				}

			</script>
		</div>
	</nav>
	<div class="container">
		<h2 class="mt-5"><a href="/Diary/list.do">그날의 일기</a></h2>
		<table class="table table-striped table-bordered mt-3" width="95%">
			<colgroup>
				<col width="20%"/><col width="30%"/>
				<col width="20%"/><col width="*"/>
			</colgroup>
			
			<tr>
				<td align="center">번호</td><td class="ps-3">${dto.no }</td>
				<td align="center">날씨</td><td class="ps-3">${dto.weather }</td>
			</tr>
			<tr>
				<td align="center">작성일</td><td class="ps-3">${dto.postdate }</td>
				<td align="center">조회수</td><td class="ps-3">${dto.viewcount }</td>
			</tr>
			<tr>
				<td align="center">제목</td>
				<td colspan="3" class="ps-3">${dto.title }</td>
			</tr>
			<tr>
				<td align="center">내용</td>
				<td colspan="3" class="ps-3" height="200">${dto.content }</td>
			</tr>
			<tr>
				<td align="center">첨부파일</td>
				<td class="ps-3">
		       		<c:if test="${ not empty dto.ofile }">
			            ${ dto.ofile }
			            <a href="/Diary/download.do?ofile=${ dto.ofile }&sfile=${ dto.sfile }&no=${ dto.no }" onclick="dCount()">[다운로드]</a>
		            </c:if>   
				</td>
					<td align="center">다운로드 횟수</td>
					<td class="ps-3" id="dCount"><div id=dDiv>${dto.downcount }</div></td>
			</tr>
			
			<tr>
				<td colspan="4" align="center">
					<button type="button" class="btn btn-outline-primary" onclick="location.href='/Diary/pass.do?mode=edit&no=${param.no}'">수정하기</button>
					<button type="button" class="btn btn-outline-danger" onclick="location.href='/Diary/pass.do?mode=delete&no=${param.no}'">삭제하기</button>
					<button type="button" class="btn btn-outline-info" onclick="location.href='/Diary/list.do'">목록으로</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>