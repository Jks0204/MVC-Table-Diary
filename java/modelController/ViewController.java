package modelController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		dao.updateVisitcount(no);
		DiaryDTO dto=dao.selectview(no);
		dao.close();
		
		dto.setContent(dto.getContent().replaceAll("/r/n", "<br>"));
		
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/View.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
