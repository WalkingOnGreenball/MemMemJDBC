package com.kh.jdbc.day03.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentDAOStatement {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";

	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		student.setStudentId(rset.getString(1));	// 컬럼의 순번으로도 가져올 수 있다.
		student.setStudentPwd(rset.getString("STUDENT_PWD"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		student.setGender(rset.getString("GENDER").charAt(0));
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));
		return student;
	}

	public List<Student> selectAll() {
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = new ArrayList<Student>();
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				Student student = rsetToStudent(rset);
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
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";
		Student student = null;
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			
			if(rset.next()) {
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

	public List<Student> selectAllByName(String studentName) {
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '"+ studentName + "'";
		List<Student> sList = new ArrayList<Student>();
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				Student student = rsetToStudent(rset);
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

	public int insertStudent(Student student) {
		String query = "INSERT INTO STUDENT_TBL VALUES("
				+ "'"+student.getStudentId()+"'"
					+ ", '"+student.getStudentPwd()+"'"
							+ ", '"+student.getStudentName()+"'"
									+ ", '"+student.getGender()+"'"
											+ ", '"+student.getAge()+"'"
													+ ", '"+student.getEmail()+"'"
															+ ", '"+student.getPhone()+"'"
																	+ ", '"+student.getAddress()+"'"
																			+ ", '"+student.getHobby()+"'"
																		+ ", SYSDATE)";
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

	public int modifyStudent(Student student) {
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
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId +"'";
		
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

	public Student selectLoginInfo(Student student) {
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" + student.getStudentId() + "' AND STUDENT_PWD = '"+student.getStudentPwd()+"'";
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ? AND STUDENT_PWD = ?";	// 물음표가 아닌 '위치홀더'라고 한다.
		Student result = null;
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
//			ResultSet rset = stmt.executeQuery(query);	// 실행하면서 쿼리를 씀
			PreparedStatement pstmt = conn.prepareStatement(query);	// 이미 쿼리를 써버림
			pstmt.setString(1, student.getStudentId());		// 인증 과정 // 들어가는 값에 따라 달라짐 ex)String // 시작은 1로하고 마지막 수는 물음표의 갯수와 같다.
			pstmt.setString(2, student.getStudentPwd());	// 인증 과정
			ResultSet rset = pstmt.executeQuery();	// 실행만 함
			
			if(rset.next()) {
				result = rsetToStudent(rset);
			}
			
			rset.close();
			pstmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
