package com.kh.jdbc.day04.student.model.dao;

import java.sql.*;	// sql 전체 import
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day04.student.model.vo.Student;

// jar 라이브러리 필수로 필요함

/*
 * JDBC 코딩 절차
 * 1. 드라이버 등록
 * 2. DBMS 연결 생성
 * 3. Statement 객체 생성(쿼리문 실행 준비)
 * 	- new Statement(); 가 아니라 연결을 통해 객체 생성함.
 * 4. SQL 전송 (쿼리문 실행)
 * 5. 결과 받기 (ResultSet으로 바로 받아버림)
 * 6. 자원해제(close())
 */

/* 
 * 1. Statement 
 * - createStatement() 메소드를 통해서 객체 생성 
 * - execute*()를 실행할 때 쿼리문이 필요함 
 * - 쿼리문을 별도로 컴파일 하지 않아서 단순 실행일 경우 빠름 
 * - ex) 전체정보조회
 * 
 * 2. PreparedStatement 
 * - Statement를 상속받아서 만들어진 인터페이스 
 * - prepareStatement() 메소들를 통해서 객체 생성하는데 이때 쿼리문 필요 
 * - 쿼리문을 미리 컴파일하여 캐싱한 후 재사용하는 구조 
 * - 쿼리문을 컴파일 할때 위치홀더(?)를 이용하여 값이 들어가는 부분을 표시한 후 쿼리문 실행전에 값을 셋팅해주어야함. 
 * - 컴파일 하는 과정이 있어 느릴 수 있지만 쿼리문을 반복해서 실행할 때는 속도가 빠름 
 * - 전달값이 있는 쿼리문에 대해서 SqlInjection을 방어할 수 있는 보안기능이 추가됨 
 * - ex) 아이디로 정보조회, 이름으로 정보조회 
 */

public class StudentDAOtest {	// 데이터를 객체화하는 게 DAO 의 역할
		// 필드에 멤버변수로 선언 (전역변수처럼 사용)
		// 변하지 않기때문에 상수로 선언하고
		// 그렇기 때문에 대문자로 상수명 설정
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";	// 드라이버 명
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";	// XE는 소문자 대문자 상관없음
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";
	
	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();	// 시험문제	// 정보를 객체에 저장
		student.setStudentId(rset.getString(1));	// 컬럼의 순번으로도 가져올 수 있다.
		student.setStudentPwd(rset.getString("STUDENT_PWD"));	// setter 메소드를 통해 student 객체에 저장
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		student.setGender(rset.getString("GENDER").charAt(0));	// 문자열에서 문자로 잘라서 사용, charAt() 메소드 사용 // GENDER가 char 형식이기 때문에
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));
		return student;
	}

	public List<Student> selectAll() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;											// SELECT만 ResultSet 씀 - 시험문제
		List<Student> sList = null;
		String query = "SELECT * FROM STUDENT_TBL";
		
		try {
			Class.forName(DRIVER_NAME);									// 1. 드라이버 등록
			conn = DriverManager.getConnection(URL, USER, PASSWORD);	// 2.  DBMS 연결 생성
			stmt = conn.createStatement();								// 3. 쿼리문 실행준비(Statement 객체 생성)
			rset = stmt.executeQuery(query);							// 4. 쿼리문 실행 (SELECT면 ResultSet), 5. 결과값 받기(ResultSet은 테이블 형태)
			sList = new ArrayList<Student>();
			
			while(rset.next()) {  // 더 있나 없나 확인해서 더 있으면 while 진행 // 후처리 필요 - DB에서 가져온 데이터를 사용하기 위함
			Student student = rsetToStudent(rset);						// 스튜던트 객체에 넣어주는 과정
				sList.add(student);										// sList에 student 객체를 모두 저장 (다른 객체도 많기 때문에 저장)
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();											// 6. 자원해제
				stmt.close();											// finally에 넣고 try / catch // 시험문제
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return sList;
	}

	public Student selectOneById(String studentId) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		Student student = null;
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";	// 홑따옴표 주의!
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {	// 하나 일때는 if문으로 진행
				student = rsetToStudent(rset);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return student;
	}

	public List<Student> selectAllByName(String studentName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Student> sList = null;
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = ?";	// 물음표가 아닌 '위치홀더'라고 한다.
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);	// PreparedStatement는 이미 쿼리를 써버림		// query문을 미리 컴파일 후 저장.
			pstmt.setString(1, studentName);		// 인증 과정 // 들어가는 값에 따라 달라짐 ex)String // 시작은 1로하고 마지막 수는 물음표의 갯수와 같다.
			rset = pstmt.executeQuery();			// 실행만 함
			sList = new ArrayList<Student>();
			
			while(rset.next()) {	// 여러개일때는 while로 진행
				Student student = rsetToStudent(rset);
				sList.add(student);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return sList;
	}

	public int insertNewStudent(Student student) {
		Connection conn = null;
		PreparedStatement pstmt = null;
//		홑따옴표 쓰는 이유는 오라클에서 문자열은 홑따옴표로 쓰기 때문에, 숫자는 홑따옴표 없음 - 시험문제
//						INSERT INTO STUDENT_TBL VALUES('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01082829222', '서울시 중구 남대문로 120', '독서, 수영', SYSDATE);
//		String query = "INSERT INTO STUDENT_TBL VALUES('"+student.getStudentId()+"', '"+student.getStudentPwd()+"', '"+student.getStudentName()+"', '"+student.getGender()+"', "+student.getAge()+", '"+student.getEmail()+"', '"+student.getPhone()+"', '"+student.getAddress()+"', '"+student.getHobby()+"', SYSDATE)";
		String query = "INSERT INTO STUDENT_TBL VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";	// 시험문제
		
		int result = -1;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			pstmt.setString(3, student.getStudentName());
			pstmt.setString(4, student.getGender()+"");					// String으로 바꿔줘야함
//			pstmt.setString(4, String.valueOf(student.getGender()));	// String으로 바꿔줘야함
			pstmt.setInt(5, student.getAge());
			pstmt.setString(6, student.getEmail());
			pstmt.setString(7, student.getPhone());
			pstmt.setString(8, student.getAddress());
			pstmt.setString(9, student.getHobby());
			result = pstmt.executeUpdate();		// DML(INSERT, UPDATE, DELETE) 에 사용
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				rset.close();	// 안썼으니 안씀
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

	public int modifyStudent(Student student) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE STUDENT_ID = ?";
		
		int result = -1;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentPwd());
			pstmt.setString(2, student.getEmail());
			pstmt.setString(3, student.getPhone());
			pstmt.setString(4, student.getAddress());
			pstmt.setString(5, student.getHobby());
			pstmt.setString(6, student.getStudentId());
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

	public int deleteStudent(String studentId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		
		int result = -1;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

}
