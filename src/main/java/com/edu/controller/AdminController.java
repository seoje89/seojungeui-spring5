package com.edu.controller;


import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.service.IF_MemberService;
import com.edu.vo.MemberVO;
import com.edu.vo.PageVO;

/**
 * 이 클래스는 Admin 관리자단을 접근하는 클래스
 * 변수(Object)를 만들어서 jsp로 전송 + jsp에서 값을 받아서 Object로 처리
 * @author 서정의
 *
 */
@Controller
public class AdminController {
	//컨트롤러 수정하면 자동로딩(오토컴파일)
	//디버그용 로그객체 생성(아래)
	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	//이 메서드는 회원목록을 출력하는 jsp와 매핑된다.
	@Inject
	private IF_MemberService memberService;
	
	//아래 경로는 수정처리를 호출 = DB를 변경처리
	@RequestMapping(value="/admin/member/member_update", method=RequestMethod.POST)
	public String updateMember(MemberVO memberVO, PageVO pageVO) throws Exception {
		//update 서비스만 처리하면 끝
		//업데이트 쿼리서비스 호출하기 전 스프링시큐리티 암호화 적용
		String rawPassword = memberVO.getUser_pw();
			if(rawPassword.isEmpty()) { //수정폼에서 암호 입력값이 비어있지 않을때만 아래 로직 실행
			BCryptPasswordEncoder passwordEncoder = new 		BCryptPasswordEncoder();
			String encPassword = passwordEncoder.encode(rawPassword);
			memberVO.setUser_pw(encPassword);
		}
		memberService.updateMember(memberVO); //반환값이 없음
		//redirect로 페이지를 이동하면, model로 담아서 보낼수 없음. 쿼리스트링(URL?)으로 보낸다.
		String queryString = "user_id="+memberVO.getUser_id()+"&page="+pageVO.getPage()+"&search_type="+pageVO.getSearch_type()+"&search_keyword="+pageVO.getSearch_keyword();
		return "redirect:/admin/member/member_update_form?"+queryString;
	}
	//아래 경로는 수정폼을 호출 = 화면에 출력만 = 렌더링만
	@RequestMapping(value="/admin/member/member_update_form", method=RequestMethod.GET)
	public String updateMemberForm(MemberVO memberVO, Model model,@ModelAttribute("pageVO") PageVO pageVO) throws Exception {
		//이 메서드는 수정폼에 pageVO, memberVO 2개의 데이터객체를 jsp로 보냄
		//사용자 1명의 레코드를 가져오는 멤버서비스를 실행(아래)
		MemberVO memberView = memberService.readMember(memberVO.getUser_id());
		//사용자 1명의 레코드를 model에 담아서 + @ModelAttribute에 담아서  member_update.jsp로 보냄
		model.addAttribute("memberVO",memberView);
		return "admin/member/member_update";//상대경로
	}
	
	@RequestMapping(value="/admin/member/member_delete",method=RequestMethod.POST)
	public String deleteMember(MemberVO memberVO) throws Exception {
		logger.info("디버그: " + memberVO.toString());
		//MemberVO memberVO(클래스형 변수) 이렇게 사용하는 의미는 String user_id(스트링형 변수) 같은방식
		String user_id = memberVO.getUser_id();
		//이 메서드는 회원상세보기 페이지에서 삭제버튼을 클릭시 전송받은 memberVO 값을 이용해서 삭제를 구현(아래)
		memberService.deleteMember(user_id); //삭제쿼리가 실행됨
		//return "admin/member/member_list"; //삭제후 이동할 jsp 경로지정
		//위 방식대로하면 새로고침하면 /admin/member/member_delete 계속 실행됨
		//게시판 테러상황을 방지하기 위해서, 쿼리를 작업후 이동할때는 redirect(다시접속)라는 명령을 사용
		return "redirect:/admin/member/member_list";
		//주의점 : redirect는 절대경로를 사용
	}
	
	@RequestMapping(value="/admin/member/member_view",method=RequestMethod.GET)
	public String viewMemberForm(Model model, @RequestParam("user_id") String user_id, @ModelAttribute("pageVO")PageVO pageVO) throws Exception {
	//페이지 진입시 받은 클래스 변수갑 PageVO pageVO
		/*
		 * 이 메서드는 리스트페이지에서 상세보기로 이동할때 보여주는 1개 레코드값을 보여주는 구현을 한다.
		 * JUnit에서 테스트했던 readMember 방식을 이용
		 * 다른점은 JUnit에서는 식별자 ID를 강제로 지정했지만, 이 메서드에서는 @RequestParam 인터페이스를 이용해서 식별자값을 받음.
		 */
		
		//위 출력값 memberVO 1개의 레코드를 model을 이용해서 member_view.jsp로 보낸다.(아래)
		model.addAttribute("memberVO", memberService.readMember(user_id));
		//아래 페이지 반환시(렌더링) @ModelAttribute("pageVO")에 의해서 pageVO.page 변수값으로 jsp로 보낸다.
		return "admin/member/member_view";//상대경로 폴더파일위치
	}
	@RequestMapping(value="/admin/member/member_list", method=RequestMethod.GET)
	public String selectMember(@ModelAttribute("pageVO")PageVO pageVO, Model model) throws Exception {
		//jsp의 검색버튼 클릭시 search_type, search_keyword 내용이 PageVO클래스에 Set된다.
		/*
		 * 이 메서드는 2개 객체를 생성하는 로직이 필요. 결과를 JSP로 보내는 기능을 수행
		 * 1객체 : memberList 객체를 생성해서 model을 통해서 jsp로 전송
		 * 2객체 : pageVO(prev,next,startPage,endPage) 객체를 생성해서 model을 통해서 jsp로 전송
		 * 2객체부터 로직이 필요 -> memberList 구하는 쿼리변수가 만들어지기 때문에 이것부터 구현
		 */
		//위에서 검색어를 받아서 역방향 검색한 결과를 jsp로 보내줌(아래)
		if(pageVO.getPage() == null) { //jsp에서 전송값이 없을때만 초기값 입력
			pageVO.setPage(1); //초기값 1페이지 입력
		}
		//학습포인트: calcPage()로직 < 변수(객체)값의 이동확인
		//pageVO의 calcPage 메서드를 실행하려면 필수 변수값입력(아래)
		pageVO.setQueryPerPageNum(5); // memberList 객체+endPage구할때 필요
		pageVO.setPerPageNum(5); // 하단 UI에 보여줄 페이지 번호 갯수, startPage 구할때
		//totalCount를 구하는 메서드는 위 변수 2개값이 필수 -> prev,next값을 구할수 있게된다
		//위 2개의 변수값을 이용해서 아래 setTotalCount 메서드에서 calcPage()호출됨 -> calcPage 실행되면 , prev, next변수값이 입력됨
		pageVO.setTotalCount(memberService.countMember(pageVO));//검색된 결과의 전체카운트값(단 페이징과 관련없는 개수)
		List<MemberVO> listMember = memberService.selectMember(pageVO); // 위 setPerPageNum이 20이면 next가 false(비활성화), 5이면 next가 true(활성화)
		logger.info("디버그" + pageVO.toString()); // 지금까지 jsp -> 컨트롤러 일방향으로 자료 이동, 컨트롤러에서 jsp로 역방향으로 보내는 자료는 Model에 담아서 보내게 됨.
		model.addAttribute("listMember", listMember);
		//model.addAttribute("pageVO", pageVO); // 나중에 @ModelAttribute로 대체 가능
		return "admin/member/member_list";//jsp 파일의 상대경로( / 로 시작하면 절대경로)
	}
	//URL요청 경로는 @RequestMapping 반드시 절대 경로로 표시
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin (Model model) throws Exception {//에러발생시 Exception클래스의 정보를 스프링으로 보내게 된다.
		
		//아래 상대경로에서 /WEB-INF/views/ 폴더가 루트(생략 prefix 접두어)이다.
		//아래 상대경로 home.jsp에서 .jsp(생략 suffix 접미어)이다.
		return "admin/home"; // 리턴 경로(=접근경로)는 반드시 상대경로로 표시
	}
}
