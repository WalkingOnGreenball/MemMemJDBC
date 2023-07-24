package com.kh.jdbc.day03.student.controller;

import java.util.List;

import com.kh.jdbc.day03.student.model.dao.StudentDAO;
import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentController {
	private StudentDAO sDao;
	
	public StudentController() {
		sDao = new StudentDAO();
	}

	public Student studentLogin(Student student) {
		Student result = sDao.selectLoginInfo(student);
		return result;
	}

	public List<Student> selectAllStudent() {
		List<Student> sList = sDao.selectAll();
		return sList;
	}

	public Student printStudentById(String studentId) {
		Student student = sDao.selectOneById(studentId);
		return student;
	}

	public List<Student> printStudentByName(String studentName) {
		List<Student> sList = sDao.selectAllByName(studentName);
		return sList;
	}

	public int insertStudent(Student student) {
		int result = sDao.insertStudent(student);
		return result;
	}

	public int modifyStudent(Student student) {
		int result = sDao.modifyStudent(student);
		return result;
	}

	public int deleteStudent(String studentId) {
		int result = sDao.deleteStudent(studentId);
		return result;
	}

}
