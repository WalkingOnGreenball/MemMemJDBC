package com.kh.jdbc.day04.student.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day04.student.controller.StudentController;
import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentView {
	
	private StudentController controller;

	public StudentView() {
		controller = new StudentController();
	}

	public void startProgram() {
		List<Student> sList = null;
		Student student = null;
		String studentId = null;
		int result = 0;
		
		finish : 
		while(true) {
			int choice = printMenu();
			switch(choice) {
			case 1 :	// SELECT * FROM STUDENT_TBL
				sList = controller.selectAll();
				if(!sList.isEmpty()) {
					printAllStudent(sList);
				} else {
					displayError("");
				}
				break;
			case 2 : 
				studentId = inputStudentId("검색");
				student = controller.selectOneById(studentId);
				if(student != null) {
					printStudent(student);
				} else {
					displayError("");
				}
				break;
			case 3 : 
				String studentName = inputStudentName("검색");
				sList = controller.selectAllByName(studentName);
				if(!sList.isEmpty()) {
					printAllStudent(sList);
				} else {
					displayError("");
				}
				break;
			case 4 : 
				student = inputStudent();
				result = controller.insertNewStudent(student);
				break;
			case 5 : 
				studentId = inputStudentId("수정");
				student = controller.selectOneById(studentId);
				if(student != null) {
					student = modifyStudent();
					student.setStudentId(studentId);
					result = controller.modifyStudent(student);
				}
				
				break;
			case 6 : 
				studentId = inputStudentId("삭제");
				result = controller.deleteStudent(studentId);
				if(result > 0) {
					displaySucces("");
				} else {
					displayError("");
				}
				break;
			case 0 : 
				break finish;
			}
		}
	}

	private Student modifyStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 수정 =====");
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.next();
		Student student = new Student(studentPw, email, phone, address, hobby);
		return student;
	}

	private Student inputStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이름 : ");
		String studentName = sc.next();
		System.out.print("성별 : ");
		char gender = sc.next().charAt(0);
		System.out.print("나이 : ");
		int age = sc.nextInt();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.next();
		Student student = new Student(studentId, studentPw, studentName, gender, age, email, phone, address, hobby);
		System.out.println(student.toString());
		return student;
	}

	private String inputStudentName(String category) {
		Scanner sc = new Scanner(System.in);
		System.out.println(category+ "할 이름을 입력해주세요.");
		String studentName =  sc.next();
		return studentName;
	}

	private void printStudent(Student student) {
		System.out.println("===== 학생 정보 출력 =====");
		System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, 전화번호 : %s, 주소 %s, 취미 : %s, 가입날짜 : %s\n"
				, student.getStudentName()
				, student.getAge()
				, student.getStudentId()
				, student.getGender()
				, student.getEmail()
				, student.getPhone()
				, student.getAddress()
				, student.getHobby()
				, student.getEnrollDate());
	}

	private String inputStudentId(String category) {
		Scanner sc = new Scanner(System.in);
		System.out.println(category+ "할 id를 입력해주세요.");
		String studentId =  sc.next();
		return studentId;
	}

	private int printMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 관리 프로그램 =====");
		System.out.println("===== 1. 학생 전체 조회 =====");
		System.out.println("===== 2. 학생 아이디로 조회 =====");
		System.out.println("===== 3. 학생 이름으로 조회 =====");
		System.out.println("===== 4. 학생 정보 등록 =====");
		System.out.println("===== 5. 학생 정보 수정 =====");
		System.out.println("===== 6. 학생 정보 삭제 =====");
		System.out.println("===== 0. 프로그램 종료 =====");
		System.out.println("메뉴 선택 : ");
		int input = sc.nextInt();
		return input;
	}

	private void displaySucces(String message) {
		System.out.println("[시스템 성공] : " + message);
	}

	private void displayError(String message) {
		System.out.println("[시스템 실패] : " + message);
	}

	private void printAllStudent(List<Student> sList) {
		System.out.println("===== 학생 전체 정보 출력 =====");
		for(Student student : sList) {
			System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, 전화번호 : %s, 주소 %s, 취미 : %s, 가입날짜 : %s\n"
					, student.getStudentName()
					, student.getAge()
					, student.getStudentId()
					, student.getGender()
					, student.getEmail()
					, student.getPhone()
					, student.getAddress()
					, student.getHobby()
					, student.getEnrollDate());
		}
	}
}
