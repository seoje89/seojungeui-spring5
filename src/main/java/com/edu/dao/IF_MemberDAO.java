package com.edu.dao;

import java.util.List;

import com.edu.vo.MemberVO;

/**
 * 이 인터페이스는 회원관리에 관련된 CRUD 메서드 명세가 포함된 파일.
 * 이 인터페이스는 메서드명만 있고, {구현내용}이 없는 목록파일.
 * @author 서정의
 *
 */
public interface IF_MemberDAO {
	//List<제네릭타입> : MemberVO 1개 레코드를 여러개 출력하기 위해 List 클래스 형태로 감싸준다. 이렇게 처리하면 다수의 레코드를 저장할수 있는 형태가 된다.
	public List<MemberVO> selectMember() throws Exception;
}
