package com.kh.jdbc.day02.student.controller;

import java.util.List;

import com.kh.jdbc.day02.student.model.dao.StudentDAO;
import com.kh.jdbc.day02.student.model.vo.Student;

public class StudentController {
	
	private StudentDAO studentDao;
	
	public StudentController(){
		studentDao = new StudentDAO();
	}

	public List<Student> printStudentList() {
		List<Student> sList = studentDao.selectAll();
		return sList;
	}

	public List<Student> printStudentsByName(String studentName) {
		List<Student> sList = studentDao.selectAllByName(studentName);
		return sList;
	}

	public Student printStudentById(String studentId) {
		Student student = studentDao.selectOneById(studentId);
		return student;
	}

	public int insertStudent(Student student) {
		int result = studentDao.insertStudent(student);	// sql: 1행이 추가되었습니다. 이것 때문에 int 로 받아준다
		return result;
	}

	public int modifyStudent(Student student) {
		int result = studentDao.updateStudent(student);
		return result;
	}

	public int deleteStudent(String studentId) {
		int result = studentDao.deleteStudent(studentId);
		return result;
	}
	
}
