package utils;

public class BoardPage {
	public static String PagingStr(int totalCount, int pageSize, int blockPage, int pageNum, String reqUrl, String searchField, String searchWord) {
		int totalPages=(int)(Math.ceil((double)totalCount/pageSize));
		String pagingStr="";
		int pageTemp=((pageNum-1)/blockPage)*blockPage+1;
		if(searchWord!=null||searchWord=="") {
			//이전
			if(pageTemp!=1) {
				pagingStr+="<a href='"+reqUrl+"?pageNum=1&searchField="+searchField+"&searchWord="+searchWord+"'> [First] </a> ";
				pagingStr+="<a href='"+reqUrl+"?pageNum="+(pageTemp-1)+"&searchField="+searchField+"&searchWord="+searchWord+"'> [prev] </a> ";
			}
			
			//페이지 번호 출력
			int blockCount=1;
			while(blockCount<=blockPage && pageTemp<=totalPages) {
				if(pageTemp==pageNum) {
					pagingStr+="<b>"+pageTemp+"</b>";
				} else {
					pagingStr+=" <a href='"+reqUrl+"?pageNum="+pageTemp+"&searchField="+searchField+"&searchWord="+searchWord+"'>"+pageTemp+"</a> ";
				}
				blockCount++;
				pageTemp++;
			}
			
			//다음
			if(pageTemp<=totalPages) {
				pagingStr+="<a href='"+reqUrl+"?pageNum="+pageTemp+"&searchField="+searchField+"&searchWord="+searchWord+"'> [next] </a>";
				pagingStr+="<a href='"+reqUrl+"?pageNum="+totalPages+"&searchField="+searchField+"&searchWord="+searchWord+"'> [Last] </a>";
			}
		} else {
			//이전
			if(pageTemp!=1) {
				pagingStr+="<a href='"+reqUrl+"?pageNum=1'> [First] </a> ";
				pagingStr+="<a href='"+reqUrl+"?pageNum="+(pageTemp-1)+"'> [prev] </a> ";
			}
			
			//페이지 번호 출력
			int blockCount=1;
			while(blockCount<=blockPage && pageTemp<=totalPages) {
				if(pageTemp==pageNum) {
					pagingStr+="<b>"+pageTemp+"</b>";
				} else {
					pagingStr+=" <a href='"+reqUrl+"?pageNum="+pageTemp+"'>"+pageTemp+"</a> ";
				}
				blockCount++;
				pageTemp++;
			}
			
			//다음
			if(pageTemp<=totalPages) {
				pagingStr+="<a href='"+reqUrl+"?pageNum="+pageTemp+"'> [next] </a>";
				pagingStr+="<a href='"+reqUrl+"?pageNum="+totalPages+"'> [Last] </a>";
			}
		}
		
		
		return pagingStr;
	}
}
