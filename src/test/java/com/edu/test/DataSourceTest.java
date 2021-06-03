package com.edu.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
