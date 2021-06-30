package com.edu.controller;

import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.service.IF_MemberService;
import com.edu.vo.MemberVO;

/**
 * 이 클래스는 스프링 시큐리티의 /login 처리한 결과를 받아서 /login_success를 처리하는 클래스
 * @author 서정의
 *
 */
@Controller
public class LoginController {
	@Inject
	private IF_MemberService memberService;
	
	@RequestMapping(value="/login_success", method=RequestMethod.GET)
	public String login_success(HttpServletRequest request, RedirectAttributes rdat) throws Exception {
		//request 만든 목적 : 세션객체를 만들기 위해서(로그인 정보를 화면이 이동하더라도 유지하기 위해서)
		//rdat 만든 목적 : model객체로 값을 전송할 수 없는 상황일때, 값을 jsp로 전송하기 위해서
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//싱글톤 객체를 만들거나, 사용하는 목적 : 메모리관리를 위해 1개만 객체로 만들어서 사용하기위해서(new로 객체를 생성하지 못함)
		String userid = "";//사용자ID
		String levels = "";//권한이 들어갈 변수
		Boolean enabled = false;//로그인체크가 들어갈 변수(true:아이디/암호 비교 성공)
		//principal객체는 UserDetails 객체가 포함되어 있고, enabled라는 인증결과가 생성됨
		Object principal = authentication.getPrincipal();// ID,비밀번호 비교쿼리가 실행됨
		if(principal instanceof UserDetails) {//위 결과 principal 객체에 UserDetails가 있으면
			enabled = ((UserDetails) principal).isEnabled();
			//위 인증결과로 로그인 체크함
		}
		//로그인 인증이 true라면 내용 실행(세션값-로그인아이디, 권한, 회원이름)
		if(enabled) {
			HttpSession session = request.getSession();
			//자바8이상에서 지원되는 람다식 사용(아래)
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			//authorities에는 회원의 levels 값 "ROLE_ANONYMOUS","ROLE_USER","ROLE_ADMIN",..
			//우리 DB에서는 Levels가 1개 필드라서 여러개 권한이 있을수 없음
			//규모있는 DB에서는 tbl_member <- tbl_levels 테이블을 만들어서 여러개의 권한을 줌
			//tbl_levels가 있으면, ["admin":{ROLE_ANONYMOUS","ROLE_USER","ROLE_ADMIN"}]
			if(authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ANONYMOUS")).findAny().isPresent()) {
				levels = "ROLE_ANONYMOUS"; // 권한 : 비로그인
			}
			if(authorities.stream().filter(o -> o.getAuthority().equals("ROLE_USER")).findAny().isPresent()) {
				levels = "ROLE_USER"; // 권한 : 일반사용자
			}
			if(authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent()) {
				levels = "ROLE_ADMIN"; // 권한 : 관리자
			}
			//람다식은 외국코드 분석할때 필요
			userid = ((UserDetails) principal).getUsername();
			//위에서 구한 변수 3개 enabled, levels, userid를 세션변수로 저장(아래)
			session.setAttribute("session_enabled", enabled);//로그인여부확인
			session.setAttribute("session_levels", levels);//로그인한 회원의 권한
			session.setAttribute("session_userid", userid);//로그인한 아이디를 출력
			MemberVO memberVO = memberService.readMember(userid);
			session.setAttribute("session_username", memberVO.getUser_name());
		}
		rdat.addFlashAttribute("mag", "로그인");//로그인 성공여부를 jsp페이지로 보내주는 변수생성.
		return "redirect:/";
	}
}
