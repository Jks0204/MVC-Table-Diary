package modelController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fileupload.FileUtil;
import utils.JSFunction;

@WebServlet("/pass.do")
public class PassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public PassController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("mode", request.getParameter("mode"));
		request.getRequestDispatcher("/Pass.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no=request.getParameter("no");
		String mode=request.getParameter("mode");
		String pass=request.getParameter("pass");
		
		DiaryDAO dao=new DiaryDAO();
		boolean confirmed=dao.confirmPassword(pass, no);
		dao.close();
		
		if(confirmed) {
			if(mode.equals("edit")) {
				HttpSession session=request.getSession();
				session.setAttribute("pass", pass);
				response.sendRedirect("/Diary/edit.do?no="+no);
				
			} else if(mode.equals("delete")) {
				dao=new DiaryDAO();
				DiaryDTO dto=dao.selectview(no);
				int result=dao.deletePost(no);
				dao.close();
				if(result==1) {
					String saveFileName=dto.getSfile();
					FileUtil.deleteFile(request, "/upload", saveFileName);
				}
				JSFunction.alertLocation(response, "삭제됨", "/Diary/list.do");
			}
		} else {
			JSFunction.alertBack(response, "비밀번호 틀림");
		}
	}

}
