package com.kh.jdbc.day02.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day02.student.model.vo.Student;

public class StudentDAO {	// 데이터를 객체화하는 게 DAO 의 역할
		// 필드에 멤버변수로 선언 (전역변수처럼 사용)
		// 변하지 않기때문에 상수로 선언하고
		// 그렇기 때문에 대문자로 상수명 설정
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";	// XE는 소문자 대문자 상관없음
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";
	
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 및 5. 결과 받기
		 * 6. 자원해제(close())
		 */
		
//		String driverName = "oracle.jdbc.driver.OracleDriver";
//		String url = "jdbc:oracle:thin:@localhost:1521:XE";	// XE는 소문자 대문자 상관없음
//		String user = "STUDENT";
//		String password = "STUDENT";
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null;
		Student student = null;
		
		try {
				// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);	// 드라이버 명 // try/catch
				// 2. DB 연결 생성(DriverManager)
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);	// add catch clause
				// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
				// 4. 쿼리문 실행 및 5. 결과 받기
			ResultSet rset = stmt.executeQuery(query);	// SELECT만 ResultSet 씀 - 시험문제
			sList = new ArrayList<Student>();
				// 후처리 필요 - DB에서 가져온 데이터를 사용하기 위함
			while(rset.next()) {	// 더 있나 없나 확인해서 더 있으면 while 진행
//				student = new Student();	// 정보를 객체를 생성
//				student.setStudentId(rset.getString("STUDENT_ID"));		// setter 메소드를 통해 student 객체에 저장
//				student.setStudentPwd(rset.getString("STUDENT_PWD"));
//				student.setStudentName(rset.getString("STUDENT_NAME"));
//				student.setAge(rset.getInt("AGE"));
//				student.setEmail(rset.getString("EMAIL"));
//				student.setPhone(rset.getString("PHONE"));
//				student.setGender(rset.getString("GENDER").charAt(0));	// 문자열에서 문자로 잘라서 사용, charAt() 메소드 사용 // GENDER가 char 형식이기 때문에
//				student.setAddress(rset.getString("ADDRESS"));
//				student.setHobby(rset.getString("HOBBY"));
//				student.setEnrollDate(rset.getDate("ENROLL_DATE"));
				student = rsetToStudent(rset);
				sList.add(student);	// sList에 student 객체들을 모두 저장 (다른 객체도 많기 때문에 저장)
			}
				// 6. 자원해제(close())
			rset.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}

	public List<Student> selectAllByName(String studentName) {
					//  SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '삼용자';
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '" + studentName + "'";	// 홑따옴표 주의!
		ResultSet rset = null;
		Student student = null;
		List<Student> sList = new ArrayList<Student>();
		
		try {
		Class.forName(DRIVER_NAME);
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		Statement stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		
			while(rset.next()) {	// 여러개일때는 while로 진행
				student = rsetToStudent(rset);
				sList.add(student);
			}
			rset.close();
			stmt.close();
			conn.close();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}

	public Student selectOneById(String studentId) {
					//  SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01';
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";	// 홑따옴표 주의!
		ResultSet rset = null;
		Student student = null;
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {	// 하나 일때는 if문으로 진행
				student = rsetToStudent(rset);
			}
			rset.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	public int insertStudent(Student student) {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 및 5. 결과 받기
		 * 6. 자원해제
		 */
			// INSERT INTO STUDENT_TBL VALUES('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01082829222', '서울시 중구 남대문로 120', '독서, 수영', SYSDATE);
//		String query = "INSERT INTO STUDENT_TBL VALUES('"+student.getStudentId()+"', '"+student.getStudentPwd()+"', '"+student.getStudentName()+"', '"+student.getGender()+"', "+student.getAge()+", '"+student.getEmail()+"', '"+student.getPhone()+"', '"+student.getAddress()+"', '"+student.getHobby()+"', SYSDATE)";	// 홑따옴표 쓰는 이유는 오라클에서 문자열은 홑따옴표로 쓰기 때문에, 숫자는 홑따옴표 없음 - 시험문제
		String query = "INSERT INTO STUDENT_TBL VALUES("
				+ "'"+student.getStudentId()+"', "
						+ "'"+student.getStudentPwd()+"', "
								+ "'"+student.getStudentName()+"', "
										+ "'"+student.getGender()+"', "
													+student.getAge()+", "
														+ "'"+student.getEmail()+"', "
																+ "'"+student.getPhone()+"', "
																		+ "'"+student.getAddress()+"', "
																				+ "'"+student.getHobby()+"', "
																						+ "SYSDATE)";	// 이것처럼 변신 가능
		int result = -1;	// 동작안하면 -1이 되게 -1로 선언
		try {
				// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
				// 2. DB 연결 생성
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
				// 4. 쿼리문 실행 및 5. 결과 받기
//			ResultSet rset = stmt.executeQuery(query);	// SELECT만 ResultSet 씀 - 시험문제
			result = stmt.executeUpdate(query);	// DML(INSERT, UPDATE, DELETE) 에 사용
				// 6. 자원해제(close())
//			rset.close();	// 안썼으니 안씀
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateStudent(Student student) {
					 	 // UPDATE STUDENT_TBL SET STUDENT_PWD = 'pass11', EMAIL = 'khuser@iei.or.kr', PHONE = '01092920303', ADDRESS = '서울시 강남구', HOBBY = '코딩,수영' WHERE STUDENT_ID = 'khuser01';
	//		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = '"+student.getStudentPwd()+"', EMAIL = '"+student.getEmail()+"', PHONE = '"+student.getPhone()+"', ADDRESS = '"+student.getAddress()+"', HOBBY = '"+student.getHobby()+"' WHERE STUDENT_ID = '" + student.getStudentId() + "'";
			String query = "UPDATE STUDENT_TBL SET "
					+ "STUDENT_PWD = '"+student.getStudentPwd()+"', "
							+ "EMAIL = '"+student.getEmail()+"', "
									+ "PHONE = '"+student.getPhone()+"', "
											+ "ADDRESS = '"+student.getAddress()+"', "
													+ "HOBBY = '"+student.getHobby()+"' "
															+ "WHERE STUDENT_ID = '" + student.getStudentId() + "'";
			int result = -1;
			
			try {
				Class.forName(DRIVER_NAME);
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				result = stmt.executeUpdate(query);
				
				stmt.close();
				conn.close();
			
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

	public int deleteStudent(String studentId) {
					 // DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01';
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";
		int result = -1;
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();	// 정보를 객체를 생성
		student.setStudentId(rset.getString("STUDENT_ID"));	// setter 메소드를 통해 student 객체에 저장 // try/catch 위에 있는 걸로
		student.setStudentPwd(rset.getString("STUDENT_PWD"));
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

}
