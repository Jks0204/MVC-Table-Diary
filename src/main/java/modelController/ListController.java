package modelController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.BoardPage;

/**
 * Servlet implementation class ListController
 */
@WebServlet("/list.do")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiaryDAO dao = new DiaryDAO();
		
		Map<String,Object> map=new HashMap<>();
		// 검색
		String searchField=request.getParameter("searchField");
		String searchWord=request.getParameter("searchWord");
		if(searchWord!=null && searchWord!="") {
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		
		int totalCount=dao.selectCount(map);
		int pageSize=15;
		int blockPage=10;
		
		int pageNum=1;	
		String pageTemp=request.getParameter("pageNum");
		if(pageTemp!=null && !pageTemp.equals("")) {
			pageNum=Integer.parseInt(pageTemp);
		}
		
		int start=(pageNum-1)*pageSize+1;
		int end=pageNum*pageSize;
		map.put("start", start);
		map.put("end", end);
		
		List<DiaryDTO> boardList= dao.selectListPage(map);
		dao.close();
		
		String pagingHtml=BoardPage.PagingStr(totalCount, pageSize, blockPage, pageNum, "/Diary/list.do",searchField,searchWord);
		map.put("pagingHtml", pagingHtml);
		
		request.setAttribute("boardList",boardList);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/List.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
