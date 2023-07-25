package com.kh.jdbc.day04.student.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {
	
	/*
	디자인 패턴 : 각기 다른 소프트웨어 모듈이나 기능을 가진 응용 SW를
	개발할 때 공통되는 설계 문제를 해결하기 위하여 사용되는 패턴.
	=> 효율적인 방식을 위함!
	
	패턴의 종류 : 생성패턴, 구조패턴, 행위패턴, ...
	1. 생성패턴 : 싱글톤 패턴, 추상팩토리, 팩토리 메서드, ...
	2. 구조패턴 : 컴포지트. 데코레이트, ...
	3. 행위패턴 : 옵저버, 스테이트, 전략, 템플릿 메서드, ...
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
	
	/*
	싱글톤 패턴의 적용 (재사용 목적)
	딱 한번만 생성되고 없을 때에만 생성한다.
	이미 존재하면 존재하는 객체를 사용함
	 */
	
	private Properties prop;
	
	private static JDBCTemplate instance;		// 객체를 생성하지않고 사용하는 방법 : static
	private static Connection conn;
	
	private JDBCTemplate() {}

	public static JDBCTemplate getInstance() {	// 객체를 생성하지않고 사용하는 방법 : static
		if(instance == null) {
			instance = new JDBCTemplate();		// 이미 만들어져 있는지 체크하고
		}
		return instance;						// 만들어져 있으면 그거 사용하라
	}
	
//	DBCP (DataBase Connection Pool) : 생성해놓고 불러만 오겠다. (계속 생성하기엔 자원과 시간이 많이 들기 때문)
	public Connection createConnection() {
		
		try {
			prop = new Properties();
			Reader reader = new FileReader("resources/dev.properties");	// 만들어놓은 properties 파일에서 stream 으로 읽어들임
			prop.load(reader);
			String driverName = prop.getProperty("DRIVER_NAME");
			String url = prop.getProperty("URL");
			String user = prop.getProperty("USER");
			String password = prop.getProperty("PASSWORD");
			
			if(conn == null|| conn.isClosed()) {
				Class.forName(driverName);
				conn = DriverManager.getConnection(url, user, password);
				conn.setAutoCommit(false);	// 오토 커밋 해제
			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();	// 틀린 것 출력하는 메소드
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
		} catch (Exception e) {		// 부모는 upcasting 가능하기 때문에 실행 메소드가 같다면 최상위 부모로 대체할 수 있다.
			e.printStackTrace();
		} 
		return conn;
	}
	
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
