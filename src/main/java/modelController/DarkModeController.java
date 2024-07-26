package modelController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DarkModeController
 */
@WebServlet("/darkmode.do")
public class DarkModeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DarkModeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String chkVal=request.getParameter("dSwitch");
		Cookie cookie= new Cookie("dSwitch","off");
		if(chkVal!=null&&chkVal.equals("yes")) {
			cookie.setValue("on");
		}
		cookie.setPath("/");
		cookie.setMaxAge(60*30);
		response.addCookie(cookie);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
