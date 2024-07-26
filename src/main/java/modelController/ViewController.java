package modelController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/view.do")
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ViewController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiaryDAO dao=new DiaryDAO(); 
		String no=request.getParameter("no");
		
		//조회수 +1, 24시간 동안 오름x
		if(no==null) {
			no=request.getParameter("ckno");
		}
		String count=request.getParameter("count");
		Cookie[] cookies=request.getCookies();
		boolean notfound=true;
		
		if(cookies!=null) {
			for(Cookie c:cookies) {
				String name=c.getName();
				if(name.equals(no)) {
					notfound=false;
					break;
				} 
			}
		} 
		if(notfound) {
			Cookie ckCount = new Cookie(no, count);
			ckCount.setPath("/");
			ckCount.setMaxAge(60*60*24);
			response.addCookie(ckCount);
			dao.updateVisitcount(no);
		}
		DiaryDTO dto=dao.selectview(no);	
		dao.close();
		
		dto.setContent(dto.getContent().replaceAll("/r/n", "<br>"));
		
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/View.jsp").forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
