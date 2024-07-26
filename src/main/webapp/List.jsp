<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>한줄 일기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
	a{text-decoration: none;}
	b{color:red;}
	tr{text-align: center;}
	a:hover {color:red; font-weight:900;}
	#list a:hover{color:violet;}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-secondary fixed-top px-5">
		<div class="form-check form-switch d-flex justify-content-end w-100">
			<label class="form-ckeck-laber me-5 text-white" for="dSwitch"> Dark Mode </label>
			<input class="form-check-input me-5" type="checkbox" id="dSwitch" name="darkmode" value="yes">
			<script>
				// 버튼 클릭시 ajax로 값 주고 받기
				$('#dSwitch').click(function(){
					var chkVal=$('#dSwitch:checked').val();
					$.ajax({
						url:"/Diary/darkmode.do",
						type:"get",
						data:{dSwitch:chkVal},
						success:funcD
					})
				});
				
				// checked에 대해 변경
				function funcD (){
					$('html').attr("data-bs-theme","dark");
					if (${cookie.dSwitch.value=="off"}){					
						$('html').removeAttr("data-bs-theme");
					} 
					location.reload();
				};
				
				// 페이지 이동시 초기 변경
				if(${cookie.dSwitch.value=="on"}){
					$('html').attr("data-bs-theme","dark");
					$('#dSwitch').attr("checked","checked");
				};

			</script>
		</div>
	</nav>
	<div class="container">
		<h2 class="mt-5">한줄 일기</h2>
		
		<form method="get">
			<table width="100%">
			<tr>
				<td align="center">
					<div class="input-group mt-2 mb-3" style="width:70%;">
						<select name="searchField" class="form-select" style="width:20%;text-transform:capitalize">
							<c:forTokens var="opt" items="title,weather,content" delims=",">
								<c:if test="${map.searchField==opt }">
									<option value="${opt}" selected>${opt}</option>						
								</c:if>
								<c:if test="${map.searchField!=opt }">
									<option value="${opt}">${opt}</option>				
								</c:if>
							</c:forTokens>
						</select>
						<input type="text" name="searchWord" value="${map.searchWord }" class="form-control" style="width:65%;">
						<input type="submit" value="검색" class="btn btn-info btn-sm" style="width:15%;">
					</div>
				</td>	
			</tr>	
			</table>
		</form>
		
		<table  class="table table-hover" border="1" width="90%">
			<thead>
				<tr>
					<th width="7%">번호</th>
					<th width="12%">날씨</th>
					<th width="18%">제목</th>
					<th width="*%">내용</th>
					<th width="10%">다운로드</th>
					<th width="13%">게시일</th>		
				</tr>
			</thead>
		<c:choose>
		<c:when test="${empty boardList}">
			<tr>
				<td colspan="6">비었습니다.</td>
			</tr>
		</c:when>
		<c:otherwise>
		<c:forEach items="${boardList}" var="row" varStatus="loop">
			<tr id="list">
				<td>${row.no}</td>
				<td>${row.weather}</td>
				<td align="left">
					 <a href="/Diary/view.do?no=${row.no}" id="detail">${row.title} </a>
				</td>
				<td align="left">${row.content}</td>
				<td>
					<c:if test="${not empty row.ofile}">
						<a href="/Diary/download.do?ofile=${row.ofile}&sfile=${row.sfile}&no=${row.no}">[down]</a>
					</c:if>
				</td>
				<td>${row.postdate}</td>
			</tr>
		</c:forEach>
		</c:otherwise>
		</c:choose>
		</table>
		<table class="table mt-3 table-borderless">
			<tr>
				<td class="ps-5 pe-0 pt-3" style="word-spacing:10px; width:80%" >${map.pagingHtml }</td>
				<td>
					<a href="/Diary/write.do"><button class="btn btn-outline-primary w-75">글쓰기</button></a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>