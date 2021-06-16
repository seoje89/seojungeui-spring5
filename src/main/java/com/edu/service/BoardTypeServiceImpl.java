package com.edu.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.edu.dao.IF_BoardTypeDAO;
import com.edu.vo.BoardTypeVO;

/**
 * 이 클래스는 DAO클래스에 접근하기 위한 서비스 클래스
 * @author 서정의
 *
 */
@Service // 애노테이션을 붙여야지만 스프링빈으로 등록이 됨
public class BoardTypeServiceImpl implements IF_BoardTypeService {
	@Inject
	private IF_BoardTypeDAO boardTypeDAO;
	
	@Override //부모 super 인터페이스의 메서드를 상속받아서 재정의하는것 = Override
	public void deleteBoardType(String board_type) throws Exception {
		// DAO클래스 객체를 이용해서 메서드를 호출(실행)
		boardTypeDAO.deleteBoardType(board_type);
	}

	@Override
	public void updateBoardType(BoardTypeVO boardTypeVO) throws Exception {
		// 아래와 동일
		boardTypeDAO.updateBoardType(boardTypeVO);
	}

	@Override
	public BoardTypeVO readBoardType(String board_type) throws Exception {
		// 실행결과반환 readBoardType(아래 실행에 필요한 변수=매개변수,파라미터,인자,아규먼트)
		return boardTypeDAO.readBoardType(board_type);
	}

	@Override
	public void insertBoardType(BoardTypeVO boardTypeVO) throws Exception {
		// 아래와 동일
		boardTypeDAO.insertBoardType(boardTypeVO);
	}

	@Override
	public List<BoardTypeVO> selectBoardType() throws Exception {
		// DAO클래스 객체를 이용해서 메서드를 호출(실행)
		return boardTypeDAO.selectBoardType();
	}

}
