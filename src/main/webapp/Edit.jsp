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
<title>수정하기</title>
<script type="text/javascript">
	function validateForm(form){
		if(form.title.value==""){
			alert("이름을 입력하세요");
			form.name.focus();
			return false;
		}
		if(form.content.value==""){
			alert("내용을 입력하세요");
			form.content.focus();
			return false;
		}
	 	var inputFile = document.getElementById("file");
        var files = inputFile.files;       
        if(files[0].size>10*1024*1024 ){
        	alert("파일크기는 10Mbyte를 초과할 수 없습니다.");
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
			</script>
		</div>
	</nav>
	<div class="container">
		<h2 class="mt-5">내용수정</h2>
		<form name="writeFrm" method="post" action="/Diary/edit.do" enctype="multipart/form-data" onsubmit="return validateForm(this);">
			<input type="hidden" name="no" value="${dto.no }"/>
			<input type="hidden" name="prevOfile" value="${dto.ofile }"/>
			<input type="hidden" name="prevSfile" value="${dto.sfile }"/>
			
			<table class="table table-bordered mt-3" width="90%">
				<tr>
					<td align="center">제목</td>
					<td>
						<input type="text" name="title" style="width:99%" value="${dto.title }"/>
					</td>
				</tr>
				<tr>
					<td align="center">날씨</td>
					<td>
						<select name="weather" style="width:200px">
							<c:forTokens var="opt" items="화창,맑음,흐림,비,소나기" delims=",">
								<c:if test="${dto.weather==opt }">
									<option value="${opt}" selected>${opt}</option>
								</c:if>
								<c:if test="${dto.weather!=opt }">
									<option value="${opt}">${opt}</option>
								</c:if>
							</c:forTokens>
						</select>
					</td>
				</tr>
				<tr>
					<td align="center">내용</td>
					<td>
						<textarea name="content" style="width:99%;height:300px;border:3px solid black;">${dto.content }</textarea>
					</td>
				</tr>
				<tr>
					<td align="center">첨부파일</td>
					<td>
						<input type="file" name="ofile" id="file"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit" class="btn btn-outline-primary">작성완료</button>
						<button type="reset" class="btn btn-outline-warning">다시작성</button>
						<button type="button" class="btn btn-outline-info"  onclick="location.href='/Diary/list.do';">목록으로</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>