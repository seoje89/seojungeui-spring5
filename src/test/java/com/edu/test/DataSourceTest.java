package com.edu.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 이 클래스는 오라클과 연동해서 CRUD를 테스트하는 클래스
 * 과장(이사,팀장) JUnit CRUD까지 만들어서 공개
 * @author 서정의
 *
 */
//RunWith 인터페이스 : 현재클래스가 JUnit 실행클래스라고 명시
@RunWith(SpringJUnit4ClassRunner.class)
//경로에서 ** : 모든폴더를 명시, * : 모든 파일명을 명시
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@WebAppConfiguration
public class DataSourceTest {
	//디버그용 로그 객체변수생성
	private Logger logger = Logger.getLogger(DataSourceTest.class);
	//dataSource 객체는 데이터베이스 객체를 pool로 저장해서 사용할때 DataSource 클래스를 사용(아래)
	@Inject //인젝트는 스프링에서 객체를 만드는 방법, 이전 자바에서는 new 키워드로 객체를 만듬.
	DataSource dataSource; //인젝트로 객체를 만들면 객체의 메모리 관리를 스프링이 대신해준다.
	//Inject는 자바8부터 지원, 자바7이하에서는 @Autowired로 객체를 만듬.
	
	@Test
	public void oldQueryTest() throws Exception {
		//스프링을 사용하지 않을때 예전 방식: 코딩테스트에서는 스프링설정을 안쓰고, 직접 		DB 아이디/암호 입력
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE","XE","apmsetup");
		logger.debug("데이터베이스 직접 접속이 성공했습니다. DB종류는"+ connection.getMetaData().getDatabaseProductName());
		//직접 쿼리를 날린다. 날리기전 쿼리문자 statement
		Statement stmt = connection.createStatement();
		//위 쿼리문장객체를 만드는 이유? 보안(SQL인젝션공격)
		//stmt객체가 없으면, 개발자가 SQL인젝션 방지코딩을 넣어야함
		//Insert쿼리 문장을 만듬(아래)
		//예전 방식으로 더미데이터(샘플)를 100개 입력
		
		/*
		 * for(int cnt=0;cnt<100;cnt++) { //error deptno 자리수가 2로 고정되어서 100은 입력시 에러
		 * stmt.executeQuery("insert into dept02 values("+cnt+",'디자인부','경기도')"); }
		 */
		
		//인서트, 업데이트, 삭제시 sql디벨로퍼에서는 커밋이 필수지만, 외부 java클래스는 자동커밋이 된다.
		//테이블에 입력되어있는 레코드를 select 쿼리 stmt 문장으로 가져옴(아래)
		ResultSet rs = stmt.executeQuery("Select * from dept02 order by deptno");//옛날방식
		//위에서 저장된 rs객체를 반복문으로 출력(아래)
		while(rs.next()) {
			// rs라는 객체의 레코드가 없을때까지 무한반복
			logger.debug(rs.getString("deptno")+" "+rs.getString("dname")+" "+rs.getString("loc"));
		}
		stmt = null; // 메모리 반환
		rs = null; // 메모리 반환
		connection = null; //메모리 초기화
	}
	@Test
	public void dbConnectionTest() {
		//데이터베이스 커넥션 테스트: 설정은 root-context의 빈(스프링클래스)을 이용
		try {
			Connection connection = dataSource.getConnection();
			logger.debug("데이터베이스 접속이 성공했습니다. DB종류는"+ connection.getMetaData().getDatabaseProductName());
		} catch (SQLException e) {
			logger.debug("데이터베이스 접속이 실패했습니다.");
			//e.printStackTrace();
		}
		
	}
	@Test
	public void junitTest() {
		//logger의 장점: 조건에 따라서 출력을 조정할수 있음.
		logger.debug("JUnit테스트 시작입니다.");
	}
}
