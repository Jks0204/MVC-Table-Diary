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

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;

/**
 * Servlet implementation class WriteController
 */
@WebServlet("/write.do")
public class WriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/Write.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//파일 업로드 처리
		String saveDirectory=request.getServletContext().getRealPath("/upload");
		
		int maxPostSize=10*1024*1024;
		MultipartRequest mr = FileUtil.uploadFile(request, saveDirectory, maxPostSize);
				
		DiaryDTO dto=new DiaryDTO();
		dto.setTitle(mr.getParameter("title"));
		dto.setWeather(mr.getParameter("weather"));
		dto.setContent(mr.getParameter("content"));
		dto.setPass(mr.getParameter("pass"));
		
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
		}
		
		
		DiaryDAO dao=new DiaryDAO();
		int result=dao.insertWrite(dto);
		dao.close();
		
		if(result==1) {
			response.sendRedirect("/Diary/list.do");
		} else {
			response.sendRedirect("/Diary/write.do");
		}
			
	}

}
