package com.edu.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//외부 라이브러리(모듈) 사용 = import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.service.IF_MemberService;
import com.edu.vo.MemberVO;

/**
 * 이 클래스는 MVC웹프로젝트를 최초로 생성하면 자동으로 생성되는 클래스
 * 웹 브라우저의 요청사항을 view단(jsp)에 연결시켜주는 역할 @controller.
 * 스프링에서 관리하는 클래스를 스프링빈(bean) = 스프링빈을 명시 @controller 애노테이션
 * Beans(콩) 그래프로 이 프로젝트의 규모를 확인가능
 * 스프링이 관리하는 클래스는 파일 아이콘에 s가 붙는다.
 */

@Controller
public class HomeController {
	//스프링빈(클래스)에서는 로거로 디버그를 한다. = 로거객체를 만든다.
	// 로그중 slf4j(spring Log For Java)
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * 사용자요청(웹브라우저)을 받아서=@RequestMapping 인터페이스를 사용해서 메서드 명을 스프링이 구현한다.
	 * , router(루트rootX)
	 * return 값으로 view(jsp)를 선택해서 작업한 결과를 변수로 담아서 화면에 전송 후 결과를 표시(렌더링) 한다.
	 * 폼(자료) 전송시 post(데이터가 숨겨져서 전송), get(데이터가 노출되서 전송-쿼리스트링[?]자료전송)
	 */
	//이제부터 일반적인 개발방식 VO -> 쿼리 -> DAO -> Service(관리자단에서 여기까지 완료)
	//관리자단에서 작성한 Service까지 사용자단에서 그대로 이용, 컨트롤러부터 분리해서 작업 -> jsp
	@Inject
	private IF_MemberService memberService;
	
	//마이페이지 회원정보수정 POST방식, 처리 후 msg를 히든값으로 jsp로 전송
	@RequestMapping(value="/member/mypage", method = RequestMethod.POST)
	public String mypage(MemberVO memberVO, RedirectAttributes rdat) throws Exception {
		//암호를 인코딩 처리. 조건, 암호를 변경하는 값이 있을때
		if(!memberVO.getUser_pw().isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String rawPassword = memberVO.getUser_pw();
			memberVO.setUser_pw(passwordEncoder.encode(rawPassword));
		}
		memberService.updateMember(memberVO);
		rdat.addFlashAttribute("msg", "회원 정보 수정");//회원정보수정 가(이) 성공했습니다. 출력용
		return "redirect:/member/mypage_form";
	}
	//마이페이지 폼 호출 GET방식, 회원수정폼이기 때문에 model담아서 변수값 전송이 필요
	@RequestMapping(value="/member/mypage_form", method = RequestMethod.GET)
	public String mypage_form(HttpServletRequest request, Model model) throws Exception {
		//로그인한 사용자 세션 session_userid 을 이용해서 memberService의 readMember 호출
		//jsp에서 발생된 세션을 가져오려고 하기 때문에 HttpServletRequest객체가 사용됨
		HttpSession session = request.getSession();//싱글톤 객체
		String user_id = (String) session.getAttribute("session_userid");
		//memberService에서 1개의 레코드를 가져옴. model에 담아서 jsp로 보냄
		model.addAttribute("memberVO", memberService.readMember(user_id));
		return "home/member/mypage";
	}
	//사용자단 로그인 URL 폼호출 GET, 로그인 POST처리는 컨트롤러로 하지 않고 스프링시큐리티로 처리
	@RequestMapping(value = "/login_form", method = RequestMethod.GET)
	public String login() throws Exception {
		
		return "home/login"; //.jsp생략
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) { //콜백메소드,자동실행됨
		String jspVar = "@서비스(DB)에서 처리한 결과";
		model.addAttribute("jspObject", jspVar); 
		logger.info("디버그 스프링로고사용: " + jspVar);//System.out 대신에 logger 객체로 디버그함
		// home.jsp파일로 자료를 전송하는 기능 = model 인터페이스 객체(스프링이 처리)에 내용만 채우면 된다.
		return "home/index"; //확장자가 생략 .jsp가 생략되어 있음.
	}
	
}
