package modelController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;
import utils.JSFunction;

/**
 * Servlet implementation class EditController
 */
@WebServlet("/edit.do")
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public EditController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no=request.getParameter("no");
		DiaryDAO dao=new DiaryDAO();
		DiaryDTO dto=dao.selectview(no);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/Edit.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String saveDirectory=request.getServletContext().getRealPath("/upload");
		
		int maxPostSize=10*1024*1024;
		MultipartRequest mr=FileUtil.uploadFile(request, saveDirectory, maxPostSize);
		
		int no=Integer.parseInt(mr.getParameter("no"));
		String prevOfile=mr.getParameter("prevOfile");
		String prevSfile=mr.getParameter("prevsfile");
		
		HttpSession session=request.getSession();
		String pass=(String)session.getAttribute("pass");
		
		DiaryDTO dto=new DiaryDTO();
		dto.setNo(no);
		dto.setTitle(mr.getParameter("title"));
		dto.setContent(mr.getParameter("content"));
		dto.setWeather(mr.getParameter("weather"));
		dto.setPass(pass);
		
		String fileName=mr.getFilesystemName("ofile");
		if(fileName!=null) {
			String now= new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String ext=fileName.substring(fileName.lastIndexOf("."));
			String newFileName=now+ext;
			
			File oldFile=new File(saveDirectory+File.separator+fileName);
			File newFile=new File(saveDirectory+File.separator+newFileName);
			oldFile.renameTo(newFile);
			
			dto.setOfile(fileName);
			dto.setSfile(newFileName);
			
			FileUtil.deleteFile(request, "/upload", prevSfile);
		} else {
			dto.setOfile(prevOfile);
			dto.setSfile(prevSfile);
		}
		
		DiaryDAO dao= new DiaryDAO();
		int result=dao.updatePost(dto);
		dao.close();
		
		if(result==1) {
			session.removeAttribute("pass");
			response.sendRedirect("/Diary/view.do?no="+no);
		} else {
			JSFunction.alertLocation(response, "수정 실패", "/Diary/view.do?no="+no);
		}
	}

}
