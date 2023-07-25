package com.kh.jdbc.day04.student.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentDAO {
	
	/*
	 * 1. Checked Exception 과 Unchecked Exception
	 * 2. 예외의 종류 Throwable - Exception(checked exception 한정)
	 * 3. 예외처리 처리 방법 : throws, try ~ catch.
	 */
	
	String selectAll = "";
	String selectOneById = "";
	String selectAllByName = "";
	String insertNewStudent = "";
	String modifyStudent = "";
	String deleteStudent = "";

	private Properties prop;
	
	public StudentDAO() {
		prop = new Properties();
		Reader reader;
		try {
			reader = new FileReader("resources/query.properties");
			prop.load(reader);
			
			selectAll = prop.getProperty("selectAll");
			selectOneById = prop.getProperty("selectOneById");
			selectAllByName = prop.getProperty("selectAllByName");
			insertNewStudent = prop.getProperty("insertNewStudent");
			modifyStudent = prop.getProperty("modifyStudent");
			deleteStudent = prop.getProperty("deleteStudent");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		student.setStudentId(rset.getString(1));
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

	public List<Student> selectAll(Connection conn) throws SQLException {	// selectAll 을 호출하는 상위 클래스인 StudentService 에서 try~catch를 해야함
		Statement stmt = null;
		ResultSet rset = null;
		List<Student> sList = null;
		String query = prop.getProperty("selectAll");
		
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		sList = new ArrayList<Student>();
		
		while(rset.next()) {
		Student student = rsetToStudent(rset);
			sList.add(student);
		}
		
		rset.close();
		stmt.close();
		
//		try {
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			
//		}
		return sList;
	}

	public Student selectOneById(Connection conn,String studentId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Student student = null;
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(selectOneById);
			pstmt.setString(1, studentId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return student;
	}

	public List<Student> selectAllByName(Connection conn,String studentName) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Student> sList = null;
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = ?";
		
		try {
			pstmt = conn.prepareStatement(selectAllByName);
			pstmt.setString(1, studentName);
			rset = pstmt.executeQuery();
			sList = new ArrayList<Student>();
			
			while(rset.next()) {
				Student student = rsetToStudent(rset);
				sList.add(student);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return sList;
	}

	public int insertNewStudent(Connection conn,Student student) {
		PreparedStatement pstmt = null;
//		String query = "INSERT INTO STUDENT_TBL VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		int result = -1;
		
		try {
			pstmt = conn.prepareStatement(insertNewStudent);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			pstmt.setString(3, student.getStudentName());
			pstmt.setString(4, student.getGender()+"");
			pstmt.setInt(5, student.getAge());
			pstmt.setString(6, student.getEmail());
			pstmt.setString(7, student.getPhone());
			pstmt.setString(8, student.getAddress());
			pstmt.setString(9, student.getHobby());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

	public int modifyStudent(Connection conn,Student student) {
		PreparedStatement pstmt = null;
//		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE STUDENT_ID = ?";
		
		int result = -1;
		
		try {
			pstmt = conn.prepareStatement(modifyStudent);
			pstmt.setString(1, student.getStudentPwd());
			pstmt.setString(2, student.getEmail());
			pstmt.setString(3, student.getPhone());
			pstmt.setString(4, student.getAddress());
			pstmt.setString(5, student.getHobby());
			pstmt.setString(6, student.getStudentId());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

	public int deleteStudent(Connection conn,String studentId) {
		PreparedStatement pstmt = null;
//		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		
		int result = -1;
		
		try {
			pstmt = conn.prepareStatement(deleteStudent);
			pstmt.setString(1, studentId);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

}
