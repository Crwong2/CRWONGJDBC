package com.kh.jdbc.day02.stmt.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day02.stmt.member.model.vo.Member;

public class MemberDAO {
	// JDBC 코딩절차
	// JDBC을 통해 DB의 데이터를 가져옴
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver"; // 23.
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 24.
	private final String USERNAME = "CRWONGJDBC"; // 25.
	private final String PASSWORD = "CRWONGJDBC"; // 26.

//	public void insertMember() { // 14.
	public void insertMember(Member member) { // 46.

		/*
		 * 1. 드라이버 등록 2. 연결 생성 3. Statement 생성 (워크시트 생성) 4. SQL문 전송 5. 결과받기 6. 자원해제
		 */
		Connection conn = null; // 18.
		Statement stmt = null; // 19.
		try { // 16.
//			Class.forName("oracle.jdbc.driver.OracleDriver"); // 15. 드라이버 등록
			Class.forName(DRIVER_NAME); // 28.
			conn = // 18.
//			DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CRWONGJDBC", "CRWONGJDBC"); // 17. 연결 생성
					DriverManager.getConnection(URL, USERNAME, PASSWORD); // 27.
			stmt = conn.createStatement(); // 19. 워크시트 열기

			// 쿼리문 작성, ; 오타조심!, '(홑따옴표) 조심!
//			String query = "INSERT MEMBER_TBL VALUES()"; // 20.
			String query = "INSERT INTO MEMBER_TBL VALUES('" + member.getMemberId() + "','" + member.getMemberPw()
					+ "','" + member.getMemberName() + "','" + member.getGender() + "','" + member.getAge() + "','"
					+ member.getEmail() + "','" + member.getPhone() + "','" + member.getAddress() + "','"
					+ member.getHobby() + "', DEFAULT)"; // 47.

			// DML의 경우 성공한 행의 갯수가 리턴, 메소드는 executeUpdate() 사용
			int result = stmt.executeUpdate(query); // 21.
//			// 다 쓴 자원해제 해줌
//			stmt.close();
//			conn.close(); // 22.
			if (result > 0) {
				// commit
			} else {
				// rollback
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) { // 18.
			e.printStackTrace();
		} finally {
			try { // 29
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// MemberController:23
	public List<Member> selectList() { // 50
		/*
		 * 1. 드라이버 등록 2. 연결 생성 3. Statement 생성 4. SQL문 전송
		 * 
		 */
		Connection conn = null; // 51
		Statement stmt = null;
		ResultSet rset = null;
		// 디비에서 가져온 값 넘겨줘야하니까
		List<Member> mList = null;

		try {
			Class.forName(DRIVER_NAME); // 드라이버 등록 // 52
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 연결 생성 // 53
			stmt = conn.createStatement(); // 워크시트 열기 // 54
			String query = "SELECT * FROM MEMBER_TBL"; // 쿼리문 작성 // 55
			// 실행 CTRL+ENTER
			rset = stmt.executeQuery(query); // SELECT는 executeQuery(query) // 56
			// 후처리, 여러개니까 while,(한개면 if쓰면됨) 전부 가져올때까지 돈다
			// null 이면 안되니까 new ArrayList<>()
			mList = new ArrayList<Member>(); // 57
			while (rset.next()) { // 58
				// rset은 바로 못쓰니까 Member
//				Member member = new Member(); // 59
				Member member = this.rsetToMember(rset); // 76
				// 비어 있으면 안되니까 setter
				// resultset에서 값을 가져와야되니까 rset.getString("컬렴명")
//				member.setMemberId(rset.getString("MEMBER_ID")); // 59
//				member.setMemberPw(rset.getString("MEMBER_PW"));
//				member.setMemberName(rset.getString("MEMBER_NAME"));
//				member.setGender(rset.getString("GENDER"));
//				member.setAge(rset.getInt("AGE"));
//				member.setEmail(rset.getString("EMAIL"));
//				member.setPhone(rset.getString("PHONE"));
//				member.setAddress(rset.getString("ADDRESS"));
//				member.setHobby(rset.getString("HOBBY"));
//				member.setRegDate(rset.getDate("REG_DATE")); // 60.
				// member에 다 담고 List에 담아야되니까
				mList.add(member); // 61
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {  // 63
//			자원 해제해야 되니까 close
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		// 호출한 곳에서 써야되니까 return mList, MemberConroller:23
		return mList; //62
	}

	public Member selectOne(String memberId) { // 73
		// rset은 바로 못쓰니까 Member
		Connection conn = null; // 78
		Statement stmt = null;
		// select 니까 ResultSet
		ResultSet rset = null;
		Member member = null; // 85
		try {
			Class.forName(DRIVER_NAME); // 77
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 79
			stmt = conn.createStatement(); // 80
			String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'"; // 81
			rset = stmt.executeQuery(query); // 82
			if(rset.next()) {
				member = rsetToMember(rset); // 82
			}
//			Member member = rsetToMember(rset); // 83
			// member = rsetToMember(rset); // 84
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// try 안에 쓴 변수는 return 안되니까 try 밖에서 Member member = null;
		// 호출한 곳에서 써야되니까 return member, MemberController:32
		return member; // 86
	}
	
	// return 있으니까 void 대신 Member
	public Member rsetToMember(ResultSet rset) throws SQLException { // 74
		Member member = new Member();
		// 비어 있으면 안되니까 setter
		// resultset에서 값을 가져와야되니까 rset.getString("컬렴명")
		member.setMemberId(rset.getString("MEMBER_ID"));
		member.setMemberPw(rset.getString("MEMBER_PW"));
		member.setMemberName(rset.getString("MEMBER_NAME"));
		member.setGender(rset.getString("GENDER"));
		member.setAge(rset.getInt("AGE"));
		member.setEmail(rset.getString("EMAIL"));
		member.setPhone(rset.getString("PHONE"));
		member.setAddress(rset.getString("ADDRESS"));
		member.setHobby(rset.getString("HOBBY"));
		member.setRegDate(rset.getDate("REG_DATE")); // 60.
		// member 에 다 담았고 호출한 곳에서 써야되니까 return member;
		return member; // 75
	}

	public int deleteMember(String memberId) { // 91
		// finally 에서 close() 하니까
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		try {
			// 1. 드라이버 등록
			// checked Exception이니까 try~catch
			Class.forName(DRIVER_NAME);
			// 2. 연결 생성
			// checked Exception이니까 catch절 추가
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// 밑에서 커밋/롤백할거니까 자동커밋 해제, setAutoCommit(false)
			conn.setAutoCommit(false);
			// 3. Statement 생성
			stmt = conn.createStatement();
			// 문자열은 '(홑따옴표)로 감싸야되니까 "'"
			String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
			// 4. 쿼리문 전송 및 5. 결과 받기
			// DML의 결과값은 성공한 행의 갯수니까 int result
			// 쿼리 실행 메소드는 DML이니까 executeUpdate(query);
			result = stmt.executeUpdate(query);
			// 쿼리 성공하면 커밋, 실패하면 롤백해야되니까 if(result > 0)
			if(result > 0) {
				// 커밋
			}else 
				// 롤백
				conn.rollback();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// try 안에서 쓴 변수는 return 안되니까 try 밖에서 int result = 0;
		// 호출한 곳에서 써야되니까 return member, MemberController:25
		return result;
	}

	public int updateMember(Member modifyInfo) { // 100.
		// finally 에서 close() 하니까
		Connection conn = null;
		Statement stmt = null;
		// try 안에 쓴 변수는 return 안되니까 try 밖에서 int result = 0;
		int result = 0;
		try {
			// 1. 드라이버 등록
			// checked Exception 이니까 try ~ catch
			Class.forName(DRIVER_NAME);
			// 2. 연결 생성
			// checked Exception 이니까 catch절 추가
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// 밑에서 커밋/롤백할거니까 자동커밋 해제, setAutoCommit(false)
			conn.setAutoCommit(false);
			// 3. Statement 생성
			stmt = conn.createStatement();
			// 문자열은 '(홑따옴표)로 감싸야되니까 "'"
			String query = "UPDATE MEMBER_TBL SET MEMBER_PW = '" +modifyInfo.getMemberPw()
												  +"', EMAIL = '"+modifyInfo.getEmail()
												  +"', PHONE = '"+modifyInfo.getPhone()
												  +"', ADDRESS = '"+modifyInfo.getAddress()
												  +"', HOBBY = '"+modifyInfo.getHobby()
												  +"' WHERE MEMBER_ID = '" + modifyInfo.getMemberId()+"'";
			// 4. 쿼리문 전송 및 5. 결과 받기
			// DML의 결과값은 성공한 행의 갯수니까 int result
			// 쿼리 실행 메소드는 DML이니까 executeUpdate(query);
			result = stmt.executeUpdate(query);
			// 쿼리 성공하면 커밋, 실패하면 롤백해야되니까 if(result > 0)
			if (result > 0) {
				conn.commit(); // 커밋, 영구저장
			}else {
				conn.rollback(); // 롤백, 최근 커밋시점으로 이동(원복)
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
