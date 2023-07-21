package com.kh.jdbc.day02.view;

import java.util.*;

import com.kh.jdbc.day01.student.controller.StudentController;
import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentView {
	
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}

	public void startProgram() {
		
		Student student = null;
		List<Student> sList = null;
		String studentId = "";
		int result = 0;
		
		finish:
		while(true) {
			int choice = printMenu();
			switch(choice) {
				case 1 : // SELECT * FROM STUDENT_TBL
					sList = controller.printStudentList();
					if(!sList.isEmpty()) {	// isEmpty() : 값이 비어있을때 
						showAllStudents(sList);
					} else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 2 : // SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01';
					studentId = inputStudentId();
					student = controller.printStudentById(studentId);	// printStudentById() 메소드가 학생 정보를 조회, dao 메소드는 selectOneById() 로 명명
					if(student != null) {
						showStudent(student); // showStudent() 메소드로 학생 정보를 출력
					} else {
						displayError("학생 정보가 존재하지 않습니다.");
					}
					break;
				case 3 : // SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '삼용자';
					String studentName = inputStudentName();
					sList = controller.printStudentsByName(studentName);
					if(!sList.isEmpty()) {
						showAllStudents(sList);
					} else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 4 : // INSERT INTO STUDENT_TBL VALUES('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01082829222', '서울시 중구 남대문로 120', '독서, 수영', SYSDATE);
					student = inputStudent();
					result = controller.insertStudent(student);		// INSERT는 int로 반환함 - 후처리가 따로 필요없음
					if(result > 0) {	// 1행이 추가 , 2행이 추가 등 0보다 큰 숫자가 나오기 때문에
						displaySucces("학생 정보 등록 성공");
					} else {
						displayError("학생 정보 등록 실패");
					}
					break;
				case 5 : // UPDATE STUDENT_TBL SET STUDENT_PWD = 'pass11', EMAIL = 'khuser@iei.or.kr', PHONE = '01092920303', ADDRESS = '서울시 강남구', HOBBY = '코딩,수영' WHERE STUDENT_ID = 'khuser01';
					student = modifyStudent();
					result = controller.modifyStudent(student);
					if(result > 0) {
						displaySucces("학생 정보 수정 성공");
					} else {
						displayError("수정이 완료되지 않았습니다.");
					}
					break;
				case 6 : // DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '';
					studentId = inputStudentId();
					result = controller.deleteStudent(studentId);
					if(result > 0) {
						displaySucces("학생 정보 삭제 성공");
					} else {
						displayError("삭제가 완료되지 않았습니다.");
					}
					break;
				case 0 : 
					break finish;
			}
		}
	}

	public int printMenu() {
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

	private String inputStudentId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 아이디로 조회 =====");
		System.out.print("학생 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}

	private String inputStudentName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 이름으로 조회 =====");
		System.out.print("학생 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
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
			sc.nextLine(); // 공백 제거, 엔터 제거 - 씹히는 것 방지
			String address = sc.nextLine();
			System.out.print("취미(,로 구분) : ");
			String hobby = sc.next();
	//		Student student = new Student();
	//		student.setStudentId(studentId);
			Student student = new Student(studentId, studentPw, studentName, gender, age, email, phone, address, hobby);
			return student;	// 절대 null로 두면 안됨
		}

	private Student modifyStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 수정 =====");
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine(); // 공백 제거, 엔터 제거 - 씹히는 것 방지
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.next();
		Student student = new Student(studentId, studentPw, email, phone, address, hobby);
		return student;
	}

	private void displaySucces(String message) {
		System.out.println("[서비스 성공] : " + message);
	}

	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message);
	}

	private void showAllStudents(List<Student> sList) {
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

	private void showStudent(Student student) {
		System.out.println("===== 학생 정보 아이디로 조회 =====");
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
