package com.edu.dao;

import java.util.List;

import com.edu.vo.BoardTypeVO;

/**
 * 이 인터페이스는 게시판 생성관리 쿼리를 접근하기위한 명세서(영수증) 파일
 * @author 서정의
 *
 */
public interface IF_BoardTypeDAO {
	//CRUD 메서드 명세만 생성(아래)
	public void deleteBoardType(String board_type) throws Exception;
	public void updateBoardType(BoardTypeVO boardTypeVO) throws Exception;
	public BoardTypeVO readBoardType(String board_type) throws Exception;
	public void insertBoardType(BoardTypeVO boardTypeVO) throws Exception;
	//BoardTypeVO 1개의 레코드를 저장한 클래스를 List<제네릭타입>형 다중레코드로 묶어서 받음
	public List<BoardTypeVO> selectBoardType() throws Exception;
}
