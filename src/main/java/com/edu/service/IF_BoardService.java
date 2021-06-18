package com.edu.service;

import java.util.List;

import com.edu.vo.AttachVO;
import com.edu.vo.BoardVO;
import com.edu.vo.PageVO;

/**
 * 이 인터페이스는 DAO를 호출하는 역할
 * DAO 클래스의 12개 메서드를 서비스에서 7개로 감소. 나머지 5개는 구현 클래스에서 사용
 * @author 서정의
 *
 */
public interface IF_BoardService {
	//DAO 클래스에 있는 첨부파일CRUD 메서드가 제외 -> 대신 구현클래스에서 사용만 한다.
	public List<AttachVO> readAttach(Integer bno) throws Exception;
	//게시물 상세보기시 조횟수 올리는 메서드 제외 -> 구현클래스에서 사용만 함
	//페이징 없는 검색된(board_type 포함된) 게시물 개수
	public int countBoard(PageVO pageVO) throws Exception;
	//기본 CRUD(아래)
	public void deleteBoard(int bno) throws Exception;
	public void updateBoard(BoardVO boardVO) throws Exception;
	public BoardVO readBoard(int bno) throws Exception;
	public void insertBoard(BoardVO boardVO) throws Exception;
	public List<BoardVO> selectBoard(PageVO pageVO) throws Exception;
}
