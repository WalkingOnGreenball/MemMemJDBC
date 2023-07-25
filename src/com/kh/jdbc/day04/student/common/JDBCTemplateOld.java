package com.kh.jdbc.day04.student.common;

import java.sql.*;

public class JDBCTemplateOld {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";
	
//	디자인 패턴 : 각기 다른 소프트웨어 모듈이나 기능을 가진 응용 SW를
//	개발할 때 공통되는 설계 문제를 해결하기 위하여 사용되는 패턴.
//	=> 효율적인 방식을 위함!
//	
//	패턴의 종류 : 생성패턴, 구조패턴, 행위패턴, ...
//	1. 생성패턴 : 싱글톤 패턴, 추상팩토리, 팩토리 메서드, ...
//	2. 구조패턴 : 컴포지트. 데코레이트, ...
//	3. 행위패턴 : 옵저버, 스테이트, 전략, 템플릿 메서드, ...
	
	
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

	/*
	public class Singletone{
		
		private static Singletone instance;			// 필드
		
		private Singletone() {}						// 생성자
		
		public static Singletone getInstance() {	// 메소드
			if(instance == null) {
				instance = new Singletone();
			}
			return instance;
		}
	}
	 */
	
	
//	싱글톤 패턴의 적용 (재사용 목적)
//	딱 한번만 생성되고 없을 때에만 생성한다.
//	이미 존재하면 존재하는 객체를 사용함
	
	private static JDBCTemplateOld instance;		// 객체를 생성하지않고 사용하는 방법 : static
	private static Connection conn;
	
	private JDBCTemplateOld() {}

	public static JDBCTemplateOld getInstance() {	// 객체를 생성하지않고 사용하는 방법 : static
		if(instance == null) {
			instance = new JDBCTemplateOld();		// 이미 만들어져 있는지 체크하고
		}
		return instance;						// 만들어져 있으면 그거 사용하라
	}
	
//	DBCP (DataBase Connection Pool) : 생성해놓고 불러만 오겠다. (계속 생성하기엔 자원과 시간이 많이 들기 때문)
	public Connection createConnection() {
		
		try {
			if(conn == null|| conn.isClosed()) {
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void close() {
		
		if(conn != null) {
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
