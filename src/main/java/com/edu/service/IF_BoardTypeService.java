package com.edu.service;

import java.util.List;

import com.edu.vo.BoardTypeVO;

/**
 * 이 인터페이스는 DAO클래스에 접근하는 서비스
 * DAO와 동일하나 차후 DAO가 늘어나도 서비스는 추가하지 않고 처리가 가능할수 있다
 * 스프링부트는 위 과정이 하나로 합쳐져 있어서 상대적으로 간단한 프로젝트에 사용한다
 * @author 서정의
 *
 */
public interface IF_BoardTypeService {
	//CRUD 메서드 명세만 생성(아래)
	public void deleteBoardType(String board_type) throws Exception;
	public void updateBoardType(BoardTypeVO boardTypeVO) throws Exception;
	public BoardTypeVO readBoardType(String board_type) throws Exception;
	public void insertBoardType(BoardTypeVO boardTypeVO) throws Exception;
	//BoardTypeVO 1개의 레코드를 저장한 클래스를 List<제네릭타입>형 다중레코드로 묶어서 받음
	public List<BoardTypeVO> selectBoardType() throws Exception;
}
