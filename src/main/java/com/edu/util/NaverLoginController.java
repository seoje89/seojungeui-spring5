package com.edu.util;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
/**
 * 이 클래스는 네이버 REST_API서버 URL을 생성하는 기능
 * @author 서정의
 *
 */
//아래 sns.~ 만드는 목적: 로컬과 헤로쿠의 인증ID와 Secret를 소스에서 변경하는것보다 전역변수로 만드는 것이 편하기 때문
@PropertySource("classpath:properties/sns.properties")
@Controller
public class NaverLoginController {
	@Value("${SnsClientID}") //스프링빈의 전역변수를 가져올때 사용하는 @Resource와 비교하면 도움이 됨
	private String CLIENT_ID; //properties에 있는 전역변수를 가져와 사용
	@Value("${SnsClientSecret}")
	private String CLIENT_SECRET;
	@Value("${SnsCallbackUrl}")
	private String REDIRECT_URL;//위 변수를 이용해서 만든 RestAPI URL이후 인증성공후 이동할 URL
	//네이버에서 지정한 상수값을 사용해야함(아래)
	//대문자로 타이핑하는 변수는 파이널이거나, 스태틱일때 사용 == 이 클래스에서만 사용하고(final), 끝내는 상수(static)
	private final static String SESSION_STATE = "oauth_state";
	//네이버 ID + 이메일 정보를 가져올수 있는 RestAPI Url설정
	private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me\r\n";
	
	//네이버에서 제공하는 인증URL 구하는 메서드(사용자 로그인폼에 $url로 제공하게됨)
	public String getAuthorizationUrl(HttpSession session) {
		// 세션에 유효성 검증을 위하여 난수를 생성(아래)
		String state = generateRandomString();
		// 생성한 난수 state값을 session변수에 저장
		setSession(session,state);
		// Scribe가입을 담당하는 외부모듈(OAuth2.0서비스)에서 제공하는 인증URL생성 - 외부모듈 pom.xml에 추가
		OAuth20Service oauthService = new ServiceBuilder()
			.apiKey(CLIENT_ID)
			.apiSecret(CLIENT_SECRET)
			.callback(REDIRECT_URL)
			.state(state)
			.build(NaverLoginApi.instance());
			
		return oauthService.getAuthorizationUrl();
	}

	private void setSession(HttpSession session, String state) {
		// HttpSession 클래스에 데이터를 저장
		session.setAttribute("SESSION_STATE", state);
	}

	private String generateRandomString() {
		// 세션 유효성 검증을 위한 난수생성기
		return UUID.randomUUID().toString();
	}
}
