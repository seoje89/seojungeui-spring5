package com.edu.util;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class NaverLoginApi extends DefaultApi20 {

	@Override
	public String getAccessTokenEndpoint() {
		// Endpoint는 네이버의 RestAPI URL을 명시함
		// 예, 중복ID체크 RestApi URL /id_check?~
		return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		// 인증체크용 RestApi URL
		return "https://nid.naver.com/oauth2.0/authorize";
	}

	//싱클톤 인스턴스 객체를 생성하기 위해서 인스턴스 홀더 클래스 상수(static,final)변수를 생성
	private static class InstanceHolder {
		private static final NaverLoginApi INSTANCE = new NaverLoginApi();
	}
	public static NaverLoginApi instance() {
		// 싱클톤으로 인스턴스 객체를 생성하는 방식:클래스 실행을 한번만 하기 위해서(메모리에 올라가는 객체를 1회만 생성하기 위해서)
		return InstanceHolder.INSTANCE;
	}


}
