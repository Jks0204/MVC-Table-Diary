package modelController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.DBConnPool;

public class DiaryDAO extends DBConnPool {
	public DiaryDAO() {};
		
	
	// 전체 글의 수
	public int selectCount(Map<String,Object> map) {
		int totalCount=0;
		String query="select count(*) from diary ";
		
		if(map.get("searchWord")!=null && map.get("searchWord")!="") {
			query+=" where "+map.get("searchField")+" like '%"+map.get("searchWord")+"%' ";
		}
		
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery(query);
			rs.next();
			totalCount=rs.getInt(1);
			
		} catch(Exception e) {
			e.getStackTrace();
		}
		
		return totalCount;
	}
	
	// 목록보기, 페이징 처리
	public List<DiaryDTO> selectListPage(Map<String,Object> map){
		List<DiaryDTO> board = new ArrayList<>();
		String query="select * from (select T.*, rownum rnum from (select * from diary ";
			
			if(map.get("searchWord")!=null && map.get("searchWord")!="") {
				query+="where "+map.get("searchField")+" like '%"+map.get("searchWord")+"%' ";
			}
		
			query+=" order by no desc) T) where rnum between ? and ?";
		
		try {
			psmt=con.prepareStatement(query);
			psmt.setString(1,map.get("start").toString());
			psmt.setString(2,map.get("end").toString());
			rs=psmt.executeQuery();
			
			while(rs.next()) {
				DiaryDTO dto = new DiaryDTO();
				dto.setNo(rs.getInt("no"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWeather(rs.getString("weather"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setDowncount(rs.getInt("downcount"));
				dto.setViewcount(rs.getInt("viewcount"));
				dto.setPass(rs.getString("pass"));
				
				board.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return board;
	}
	
	//등록
	public int insertWrite (DiaryDTO dto) {
		int result=0;
		String query="insert into diary (no,title,content,weather,ofile,sfile,pass) values(diary_seq_num.nextval,?,?,?,?,?,?)";
		try {
			psmt=con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getWeather());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());
			
			result=psmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("게시판 입력 에러");
			e.printStackTrace();
		}
		return result;
				
	}
	
	// downcount
	public void downCountPlus(String no) {
		String sql="Update diary set downcount=downcount+1 where no=?";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, no);
			psmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		}

	}

	//visitcount
	public void updateVisitcount(String no) {
		String sql="Update diary set viewcount=viewcount+1 where no=?";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, no);
			psmt.executeUpdate();
		} catch(Exception e){
			e.getStackTrace();
		}
	}
	
	// no에 따른 게시물 상세보기
	public DiaryDTO selectview(String no) {
		DiaryDTO dto=new DiaryDTO();
		String query="select * from diary where no=? ";
		try {
			psmt=con.prepareStatement(query);
			psmt.setString(1, no);
			rs=psmt.executeQuery();
			if(rs.next()) {
				dto.setNo(rs.getInt(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString(3));
				dto.setWeather(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setViewcount(rs.getInt(9));
				dto.setPass(rs.getString(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	//비번 확인
	public boolean confirmPassword(String pass, String no) {
		boolean isCorr=true;
		try {
			String query="select count(*) from diary where pass=? and no=? ";
			psmt=con.prepareStatement(query);
			psmt.setString(1, pass);
			psmt.setInt(2, Integer.parseInt(no));
			rs=psmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) {
				isCorr=false;
			}
		}catch(Exception e) {
			e.getStackTrace();
		}
		return isCorr;			
	}
	
	// update
	public int updatePost(DiaryDTO dto) {
		int result=0;
		try {
			String query="update diary set title=?, weather=?, content=?, ofile=?, sfile=? where no=? and pass=?";
			psmt=con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getWeather());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setInt(6, dto.getNo());
			psmt.setString(7, dto.getPass());
			
			result=psmt.executeUpdate();
		} catch(Exception e) {
			e.getStackTrace();
		}
		
		return result;
	}
	
	// delete
	public int deletePost(String no) {
		int result=0;
		try {
			String query="delete from diary where no=? ";
			psmt=con.prepareStatement(query);
			psmt.setInt(1, Integer.parseInt(no));
			result=psmt.executeUpdate();
			
		} catch(Exception e) {
			e.getStackTrace();
		}
		
		return result;
	}
}
