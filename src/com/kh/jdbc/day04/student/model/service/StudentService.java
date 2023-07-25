package com.kh.jdbc.day04.student.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kh.jdbc.day04.student.common.JDBCTemplate;
import com.kh.jdbc.day04.student.model.dao.StudentDAO;
import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentService {

	private StudentDAO sDao;
	private JDBCTemplate jdbcTemplate;
	
	public StudentService() {
		sDao = new StudentDAO();
//		jdbcTemplate = new JDBCTemplate();			// 생성자가 private이기 때문에 사용 못함!
		jdbcTemplate = JDBCTemplate.getInstance();	// 싱글톤 패턴의 적용 (재사용 목적)
	}

	public List<Student> selectAll() {
		Connection conn = jdbcTemplate.createConnection();
		List<Student> sList = null;
		
		try {
			sList = sDao.selectAll(conn);
			jdbcTemplate.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}

	public Student selectOneById(String studentId) {
		Connection conn = jdbcTemplate.createConnection();
		Student student = sDao.selectOneById(conn, studentId);
		jdbcTemplate.close();
		return student;
	}

	public List<Student> selectAllByName(String studentName) {
		Connection conn = jdbcTemplate.createConnection();
		List<Student> sList = sDao.selectAllByName(conn, studentName);
		jdbcTemplate.close();
		return sList;
	}

	public int insertNewStudent(Student student) {
		Connection conn = jdbcTemplate.createConnection();
		int rusult = sDao.insertNewStudent(conn, student);
		if(rusult > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		jdbcTemplate.close();
		return rusult;
	}

	public int modifyStudent(Student student) {
		Connection conn = jdbcTemplate.createConnection();
		int rusult = sDao.modifyStudent(conn, student);
		if(rusult > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		jdbcTemplate.close();
		return rusult;
	}

	public int deleteStudent(String studentId) {
		Connection conn = jdbcTemplate.createConnection();
		int rusult = sDao.deleteStudent(conn, studentId);
		if(rusult > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		jdbcTemplate.close();
		return rusult;
	}
}
