package com.edu.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.edu.vo.AttachVO;
import com.edu.vo.BoardVO;
import com.edu.vo.PageVO;

/**
 * 이 클래스는 게시물 CRUD를 구현하는 DAO클래스
 *  
 * @author 서정의
 *
 */
@Repository
public class BoardDAOImpl implements IF_BoardDAO {
	@Inject
	private SqlSession sqlSession; //sqlSession 템플릿에는 insert,update()...

	@Override
	public void deleteAttachAll(Integer bno) throws Exception {
		// sql세션템플릿을 사용한 매퍼쿼리 호출
		sqlSession.delete("boardMapper.deleteAttachAll", bno);
	}

	@Override
	public void deleteAttach(String save_file_name) throws Exception {
		// 아래와 동일
		sqlSession.delete("boardMapper.deleteAttach", save_file_name);
	}

	@Override
	public void updateAttach(AttachVO attachVO) throws Exception {
		// 아래와 동일, 메서드명은 update지만 실제쿼리는 insert
		sqlSession.insert("boardMapper.updateAttach", attachVO);
	}

	@Override
	public void insertAttach(AttachVO attachVO) throws Exception {
		// 아래와 동일
		sqlSession.insert("boardMapper.insertAttach", attachVO);
	}

	@Override
	public List<AttachVO> readAttach(Integer bno) throws Exception {
		// 아래와 동일 - 첨부파일
		return sqlSession.selectList("boardMapper.readAttach", bno);
	}

	@Override
	public void updateViewCount(Integer bno) throws Exception {
		// 아래와 동일
		sqlSession.update("boardMapper.updateViewCount", bno);
	}

	@Override
	public int countBoard(PageVO pageVO) throws Exception {
		// 아래와 동일
		return sqlSession.selectOne("boardMapper.countBoard", pageVO);
	}

	@Override
	public void deleteBoard(int bno) throws Exception {
		// 아래와 동일
		sqlSession.delete("boardMapper.deleteBoard", bno);
	}

	@Override
	public void updateBoard(BoardVO boardVO) throws Exception {
		// 아래와 동일
		sqlSession.update("boardMapper.updateBoard", boardVO);
	}

	@Override
	public BoardVO readBoard(int bno) throws Exception {
		// 아래와 동일
		return sqlSession.selectOne("boardMapper.readBoard", bno);
	}

	@Override
	public void insertBoard(BoardVO boardVO) throws Exception {
		// 아래와 동일
		sqlSession.insert("boardMapper.insertBoard", boardVO);
	}

	@Override
	public List<BoardVO> selectBoard(PageVO pageVO) throws Exception {
		// sqlSession 템플릿을 사용해서 매퍼쿼리 실행
		return sqlSession.selectList("boardMapper.selectBoard", pageVO);
	}

}
