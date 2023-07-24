package com.kh.jdbc.day03.student.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day03.student.controller.StudentController;
import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentView {

	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}

	public void studentProgram() {
		List<Student> sList = null;
		Student student = null;
		int result = 0;
		String studentId = "";
		
		theEnd : 
		while(true) {
			int input = printMenu();
			switch(input) {
				case 9 :	// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01' AND STUDENT_PWD = '1234';
					// 'or'1'='1'--;
					// 'or'1'='1'--
					// Statement는 입력값 인증을 거치지 않아서
					// SQL injection 공격을 받아버림 - 다 통과됨
					// PreparedStatement 로 방어
					student = inputLoginInfo();
					student = controller.studentLogin(student);
					if(student != null) {
						displaySucces("로그인 완료");
					} else {
						displayError("로그인 실패");
					}
					break;
				case 1 : 
					sList = controller.selectAllStudent();
					if(!sList.isEmpty()) {
						printAllStudent(sList);
					} else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 2 : 
					studentId = inputStudentId();
					student = controller.printStudentById(studentId);
					if(!sList.isEmpty()) {
						printStudent(student);
					} else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 3 : 
					String studentName = inputStudentName();
					sList = controller.printStudentByName(studentName);
					if(!sList.isEmpty()) {
						printAllStudent(sList);
					} else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 4 : 
					student = inputStudent();
					result = controller.insertStudent(student);
					if(result > 0) {
						displaySucces("학생 정보 등록 완료");
					} else {
						displayError("학생 정보 등록 실패");
					}
					break;
				case 5 : 
					studentId = inputStudentId();
					student = controller.printStudentById(studentId);
					if(student != null) {
						student = modifyStudent();
						student.setStudentId(studentId);
						result = controller.modifyStudent(student);
						
						if(result > 0) {
							displaySucces("학생 정보 수정 완료");
						} else {
							displayError("학생 정보 수정 실패");
						}
					} else {
						// 없을때
					}
					break;
				case 6 : 
					studentId = inputStudentId();
					result = controller.deleteStudent(studentId);
					if(result > 0) {
						displaySucces("학생 정보 삭제 완료");
					} else {
						displayError("학생 정보 삭제 실패");
					}
					break;
				case 0 : 
					break theEnd;
			}
		}
	}

	private Student inputLoginInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 로그인 =====");
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		Student student = new Student(studentId, studentPw);
		return student;
	}

	private int printMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 관리 프로그램 =====");
		System.out.println("===== 9. 학생 로그인 =====");
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

	private String inputStudentId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("아이디를 입력해주세요.");
		String studentId =  sc.next();
		return studentId;
	}

	private String inputStudentName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("이름을 입력해주세요.");
		String studentName =  sc.next();
		return studentName;
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

	private void displaySucces(String message) {
		System.out.println("[시스템 성공] : " + message);
	}

	private void displayError(String message) {
		System.out.println("[시스템 실패] : " + message);
	}
}
