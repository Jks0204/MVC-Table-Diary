<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>오늘의 일기</title>
<style>
	td:first-child {text-align:center }
	
	a{text-decoration: none;}
	a:hover {font-weight:900;}

</style>
<script>
	function validateForm(form){
		if (form.title.value==""){
			alert("제목을 입력하세요");
			form.title.focus();
			return false;
		}
		if (form.content.value==""){
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
		<h2 class="mt-5"><a href="/Diary/list.do">오늘의 일기</a></h2>
		<form name="writeD" method="post" action="/Diary/write.do" enctype="multipart/form-data" onsubmit="return validateForm(this);" >
			<table class="table table-bordered mt-3" width="95%">
				<tr>
					<td>제목</td>
					<td>
						<input type="text" name="title" style="width:200px"/> 
					</td>
				</tr>
				<tr>
					<td>날씨</td>
					<td>
						<select name="weather" style="width:200px">
							<option value="화창">화창</option>
							<option value="맑음">맑음</option>
							<option value="흐림">흐림</option>
							<option value="비">비</option>
							<option value="소나기">소나기</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>내용 작성</td>
					<td>
						<textarea type="text" name="content" style="width:99%; height:300px; border:3px solid black;"></textarea>
					</td>
				</tr>
				<tr>
					<td>업로드</td>
					<td><input type="file" name="ofile" id="file"/></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td>
						<input type="password" name="pass" style="width:200px"/> 
					</td>
				</tr>
				<tr>
				<tr>
					<td colspan="2">
						<button type="submit" class="btn btn-outline-primary">작성완료</button>
						<button type="reset" class="btn btn-outline-warning">다시작성</button>
						<button type="button" class="btn btn-outline-info"onclick="location.href='/Diary/list.do'">목록으로</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>