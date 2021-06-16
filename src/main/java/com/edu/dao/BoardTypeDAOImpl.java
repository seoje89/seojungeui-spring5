package com.edu.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.edu.vo.BoardTypeVO;

/**
 * 이 클래스는 게시판생성관리 쿼리의 인터페이스를 구현하는 메서드가 있음.
 * @author 서정의
 *
 */
@Repository
public class BoardTypeDAOImpl implements IF_BoardTypeDAO {
	//sqlSession 템플릿 의존성주입
	@Inject // 자바8부터 새로 나온 애노테이션
	private SqlSession sqlSession;

	@Override
	public void deleteBoardType(String board_type) throws Exception {
		// sqlSession 템플릿을 이용해서 매퍼쿼리 실행
		sqlSession.delete("boardTypeMapper.deleteBoardType",board_type);
	}

	@Override
	public void updateBoardType(BoardTypeVO boardTypeVO) throws Exception {
		// 아래와 동일
		sqlSession.update("boardTypeMapper.updateBoardType", boardTypeVO);
	}

	@Override
	public BoardTypeVO readBoardType(String board_type) throws Exception {
		// 아래와 동일
		return sqlSession.selectOne("boardTypeMapper.readBoardType", board_type);
	}

	@Override
	public void insertBoardType(BoardTypeVO boardTypeVO) throws Exception {
		// 아래와 동일
		sqlSession.insert("boardTypeMapper.insertBoardType", boardTypeVO);
	}

	@Override
	public List<BoardTypeVO> selectBoardType() throws Exception {
		// sqlSession 템플릿을 이용해서 매퍼쿼리를 실행
		return sqlSession.selectList("boardTypeMapper.selectBoardType");
	}

}
