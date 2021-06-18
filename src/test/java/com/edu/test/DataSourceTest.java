package com.edu.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.edu.service.IF_BoardService;
import com.edu.service.IF_MemberService;
import com.edu.vo.BoardVO;
import com.edu.vo.MemberVO;
import com.edu.vo.PageVO;

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
	private DataSource dataSource; //인젝트로 객체를 만들면 객체의 메모리 관리를 스프링이 대신해준다.
	//Inject는 자바8부터 지원, 자바7이하에서는 @Autowired로 객체를 만듬.
	@Inject //MemberService 서비스를 주입받아서 객체를 사용(아래)
	private IF_MemberService memberService;
	@Inject
	private IF_BoardService boardService;
	
	@Test
	public void insertBoard() throws Exception {
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("인서트 후 반환값테스트");
		boardVO.setContent("JUnit 입력테스트");
		boardVO.setWriter("admin");
		boardVO.setBoard_type("gallery");
		boardService.insertBoard(boardVO);
	}
	
	@Test
	public void updateMember() throws Exception {
		//이 메서드는 회원정보수정(1개 레코드) jsp에 사용예정
		MemberVO memberVO = new MemberVO();
		memberVO.setEmail("admin@test.com");
		memberVO.setEnabled(true);
		memberVO.setLevels("ROLE_ADMIN");
		memberVO.setPoint(100);
		memberVO.setUser_name("최고관리자");
		memberVO.setUser_pw(""); //암호를 입력하지 않으면 업데이트에서 제외된다
		//메서드 내 적용된 객체변수 생성
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//스프링5시큐리티 적용 로직(아래)
		if((memberVO.getUser_pw()).length() > 0) {
		String userPwEncoder = passwordEncoder.encode(memberVO.getUser_pw());
		memberVO.setUser_pw(userPwEncoder); // 암호화된 헤시데이터가 memberVO객체에 임시저장됨
		}
		memberVO.setUser_id("admin"); //수정조회조건에 사용(실제 수정하는건 아님)
		memberService.updateMember(memberVO);
		//========== 여기까지는 jsp에서 1명의 회원만 수정할때 사용하는 로직
		//========== 이후부터는 모든회원중에 시큐리티 암호화가 아직인 사용자의 암호만 업데이트하는 로직
		//아래 수정 호출을 회원수만큼 반복해야함(아래)
		PageVO pageVO = new PageVO();
		pageVO.setPage(1); 
		pageVO.setPerPageNum(10); 
		pageVO.setQueryPerPageNum(1000);
		//MemberVO 타입을 가진 리스트형 객체 List<MemberVO>
		List<MemberVO> listMember = memberService.selectMember(pageVO);
		//향상된 for반복문(memberOne:listMember) {구현내용}
		for(MemberVO memberOne:listMember) { //listMember 객체가 비워질 때까지 반복
			//혹시 여러번 실행시켜서 중복암호화 시킬수 있기 때문에 제외조건을 추가(아래)
			String rawPassword = memberOne.getUser_pw();
			if(rawPassword.length() < 50) { //초기암호데이터 길이가 10보다 작을때만 암호화로직 진입
			//memberOne 객체(1개레코드)의 암호를 뽑아서 시큐리티로 암호화 시킨 후 onePwEncoder 변수에 담는다.
			String onePwEncoder = passwordEncoder.encode(rawPassword);
			memberOne.setUser_pw(onePwEncoder);
			memberService.updateMember(memberOne);} //1명(admin)만 수정 -> 모든회원 업데이트
		}
		
	}
	@Test
	public void readMember() throws Exception {
		//이 메서드는 회원상세보기(1개 레코드) jsp에 사용될 예정
		MemberVO memberVO = new MemberVO();
		//100명중 한명을 출력하려면 고유키(기본키,주키,PK)가 필요 = 지금은 user_id
		//String user_id = "admin"; 이후 readmember 안에 user_id 입력
		memberVO.setUser_id("admin");
		memberVO = memberService.readMember(memberVO.getUser_id());
	}
	@Test
	public void deleteMember() throws Exception {
		memberService.deleteMember("user_del");
		selectMember();
	}
	@Test
	public void insertMember() throws Exception {
		MemberVO memberVO = new MemberVO();
		//인서트 쿼리에 저장할 객체 생성
		memberVO.setUser_id("user_del");
		memberVO.setUser_pw("1234");//스프링시큐리티 중 512비트 암호화로 처리예정
		memberVO.setEmail("user@test.com");
		memberVO.setPoint(10);
		memberVO.setEnabled(true);
		memberVO.setLevels("ROLE_USER");
		memberVO.setUser_name("삭제할 사용자");
		memberService.insertMember(memberVO);
		selectMember();
	}
	
	//스프링 코딩 시작 순서(Readme로 이동)

	@Test
	public void selectMember() throws Exception {
		//회원관리 테이블에서 더미로 입력한 100개의 레코드를 출력하는 메서드 테스트 -> 회원관리 목록이 출력
		//현재 100명 검색기능, 페이징기능 여기서 구현. 1페이지에 10명씩 나오게 변경
		//현재 몇페이지, 검색어 임시저장 공간 -> DB에 페이징 조건문, 검색 조건문
		//변수를 2~3이상은 바로 String 변수로 처리하지 않고, VO 만들어서 사용
		//PageVO.java클래스를 만들어서 페이징처리변수와 검색어변수 선언, Get/Set생성
		//PageVO 만들기전 SQL쿼리로 가상으로 페이지를 한번 구현해보면서, 필요한 변수 만들어야 함
		//PageVO 객체를 만들어서 가상으로 초기값을 입력한다.(아래)
		PageVO pageVO = new PageVO();
		pageVO.setPage(1); //기본값으로 1페이지를 입력
		pageVO.setPerPageNum(10); // UI하단사용 페이지 개수
		pageVO.setQueryPerPageNum(1000); // 쿼리사용 페이지당 개수
		//pageVO.setTotalCount(memberService.countMember()); //테스트하려고, 100명을 강제입력
		/* 모든 사용자를 출력하지 않고, 일부 사용자만 출력할때 필요하다
		pageVO.setSearch_type("user_id"); //검색타입 all, user_id, user_name
		pageVO.setSearch_keyword("user_del"); */
		//위 위치가 다른 설정보다 상단이면 에러발생 : 이유는 calcPage()가 실행시 위 3가지 변수값이 저장되어 있어야 계산메서드가 정상작동되기 때문
		//위 토탈카운트 변수값은 startPage, endPage 계산에 필수이다.
		//매퍼쿼리_DAO클래스_Service클래스_JUnit(나중엔 컨트롤러에서 작업) 이제 역순으로 작업진행
		//더 진행하기 전에 현재 pageVO 객체에는 어떤값이 들어있는지 확인 후 사용(아래)
		logger.info("pageVO 저장된 값 확인: "+pageVO.toString());
		
		List<MemberVO> listMember = memberService.selectMember(pageVO);
		listMember.toString();
	}
	
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
		ResultSet rs = stmt.executeQuery("Select * from dept order by deptno");//옛날방식
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
