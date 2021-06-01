package com.edu.controller;

//외부 라이브러리(모듈) 사용 = import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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
//	private Logger logger = Logger
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * 사용자요청(웹브라우저)을 받아서=@RequestMapping 인터페이스를 사용해서 메서드 명을 스프링이 구현한다.
	 * , router(루트rootX)
	 * return 값으로 view(jsp)를 선택해서 작업한 결과를 변수로 담아서 화면에 전송 후 결과를 표시(렌더링) 한다.
	 * 폼(자료) 전송시 post(데이터가 숨겨져서 전송), get(데이터가 노출되서 전송-쿼리스트링[?]자료전송)
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) { //콜백메소드,자동실행됨
		String jspVar = "@서비스(DB)에서 처리한 결과";
		model.addAttribute("jspObject", jspVar); 
		// home.jsp파일로 자료를 전송하는 기능 = model 인터페이스 객체(스프링이 처리)에 내용만 채우면 된다.
		return "home"; //확장자가 생략 .jsp가 생략되어 있음.
	}
	
}
