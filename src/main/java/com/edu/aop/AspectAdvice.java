package com.edu.aop;

import java.util.List;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.edu.service.IF_BoardTypeService;
import com.edu.vo.BoardTypeVO;
import com.edu.vo.BoardVO;
import com.edu.vo.PageVO;

/**
 * 이 클래스는 AOP기능중 @Aspect 와 @ControllerAdvice 로 구현
 * @author 서정의
 *
 */
@Aspect
@ControllerAdvice
public class AspectAdvice {
	private Logger logger = LoggerFactory.getLogger(AspectAdvice.class);
	@Inject
	private IF_BoardTypeService boardTypeService;
	
	//나중에 게시물관리 기능 만들때 @Aspect로 AOP기능 추가. 목적: board_type값을 항상 가져가도록 처리(세션)
	//세션 : 서버-클라이언트 구조상에서 클라이언트가 서버에 접속할때 서버에 발생되는 정보를 뜻함(세션은 서버에 저장됨)
	//쿠키 : 서버-클라이언트 구조상에서 클라이언트가 서버에 접속할때 서버에 발생되는 정보를 뜻함(쿠키는 PC에 저장됨)
	//옛날에는 쿠키로 로그인 체크를 했음 -> 보안상 PC에 로그인정보가 저장되기 때문에 위험(인터넷 광고에만 사용) -> 현재는 세션으로 사용
	//Aspect로 AOP를 구현할때는 포인트컷(Advice가 실행될 위치)이 필요
	//@Around는 @Before + @After 와 동일 : @Around(포인트컷 전+후.*(...)모든메서드)
	//@Around는 콜백함수 매개변수로 조인포인트객체(포인트컷에서 실행되는 메서드)를 필수로 받음
	@Around("execution(* com.edu.controller.*Controller.*(..))")
	public Object sessionManager(ProceedingJoinPoint pjp) throws Throwable {
		// board_type 변수값을 세션에 저장하려고 함. 클라이언트별 세션이 발생됨
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		//일반적인 컨트롤러 클래스에서는 매개변수 HttpServletRequest를 사용가능함. 위처럼 복잡하게 구하지 않음.
		//컨트롤러 클래스에서 매개변수로 받을값 (board_type) < pageVO
		PageVO pageVO = null;
		String board_type = null; //jsp에서 전송되는 값을 임시로 저장, 목적은 세션변수 발생조건으로 사용
		//조인포인트리스트의 객체들의 메서드의 Arguments(매개변수)를 뽑아냄
		for(Object object:pjp.getArgs()) {
			if(object instanceof PageVO) {
				pageVO = (PageVO) object;
				board_type = pageVO.getBoard_type();
			}
		}
		if(request != null) { // jsp에서 Get,Post 있을때
			//세션값을 pageVO.board_type 값으로 저장로직(아래)
			HttpSession session = request.getSession(); //PC가 스프링프로젝트 접근시 세션객체
			if(board_type !=null) {//최초로 세션변수가 발생
				session.setAttribute("session_board_type", board_type);
			}
			if(session.getAttribute("session_board_type") !=null) {
				board_type = (String) session.getAttribute("session_board_type");
				pageVO.setBoard_type(board_type); //검색목표달성 : 여기서 항상 값을 가져가도록 구현
			}
			logger.info("디버그19: " +(String) session.getAttribute("session_board_type"));
		}
		//Aspect > 포인트컷(Around) > 조인포인트(메서드) > 매개변수로 구현한 결과를 리턴
		Object result = pjp.proceed(); //여기서 조인포인트가 실행됨
		return result;
	}
	
	//이 메서드는 컨트롤러의 메서드가 실행되기 전에 값을 생성해서 model 객체에 담아서 jsp로 자료를 보냄
	//위 @컨트롤러어드바이스를 이용해서 컨트롤러의 모든 메서드가 실행되기 전에 호출만되면 아래 메서드가 자동실행(콜백함수)
	@ModelAttribute("listBoardTypeVO")
	public List<BoardTypeVO> listBoardTypeVO() throws Exception {
		return boardTypeService.selectBoardType();
	}
}
