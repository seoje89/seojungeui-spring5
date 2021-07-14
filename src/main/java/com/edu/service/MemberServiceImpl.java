package com.edu.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.edu.dao.IF_MemberDAO;
import com.edu.vo.MemberVO;
import com.edu.vo.PageVO;

/**
 * 이 클래스는 회원관리 서비스 인터페이스를 구현하는 클래스
 * 상속 extends , 구현 implements 키워드 사용
 * 스프링빈으로 사용하려면(등록하려면), @Service 애너테이션을 명시
 * @author 서정의
 *
 */
@Service
public class MemberServiceImpl implements IF_MemberService {
	@Inject // IF_MemberDAO를 주입해서 객체로 사용(아래)
	private IF_MemberDAO memberDAO;
	Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	//헤로쿠 클라우드에 30분 휴먼상태를 깨우는 기능 추가 -> root-context에서 지정한 스케줄러에서 호출됨(20분간격)
	public void herokuJobMethod() throws Exception {
		//한국시간 월-금 8시부터 23시까지(미국시간 0시부터 14시까지) 헤로쿠 앱에 20분 간격으로 접근하는 역할
		//헤로쿠 컨테이너가 러닝할수 있는 무료시간은 1달에 700시간정도. 시간이 넘으면 먹통이 됨(남은 1달내내)
		String urlStr = "https://seojungeui-spring5.herokuapp.com/";
		URL url = new URL(urlStr);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); //접속 객체만들기
		urlConnection.setUseCaches(false);//접속시 캐시사용없이 무조건 새로고침하겠다고 명시, 컨테이너가 계속 올라가있음(자료유지)
		urlConnection.setReadTimeout(60000);//접속대기시간을 60초로 제한
		//20분마다 접속이 되는지 개발자가 확인하는 코드
		if(urlConnection !=null && urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			logger.info("헤로쿠 앱이 활성화 상태입니다.");
		} else {
			logger.info("헤로쿠 앱이 비활성화 상태입니다.");
		}
	}
	
	@Override
	public List<MemberVO> selectMember(PageVO pageVO) throws Exception {
		// 인터페이스에서 상속받은 메서드를 구현(아래)
		return memberDAO.selectMember(pageVO);
	}

	@Override
	public int countMember(PageVO pageVO) throws Exception {
		// 인젝션으로 주입받은 DAO 객체를 사용(아래)
		return memberDAO.countMember(pageVO);
	}

	@Override
	public void insertMember(MemberVO memberVO) throws Exception {
		// 클래스 상단에서 인젝션으로 주입받은 DAO 객체를 사용(아래)
		memberDAO.insertMember(memberVO);		
	}

	@Override
	public void deleteMember(String user_id) throws Exception {
		// 클래스 상단에서 인젝션으로 주입받은 DAO 객체를 사용(아래)
		memberDAO.deleteMember(user_id);
		
	}

	@Override
	public MemberVO readMember(String user_id) throws Exception {
		// DAO를 호출
		return memberDAO.readMember(user_id);
	}

	@Override
	public void updateMember(MemberVO memberOne) throws Exception {
		// DAO호출
		memberDAO.updateMember(memberOne);		
	}

}
