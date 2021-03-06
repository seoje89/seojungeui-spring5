package com.edu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.edu.vo.PageVO;
import com.edu.vo.ReplyVO;

/**
 * 이 클래스는 sqlSession 템플릿을 이용해서 쿼리를 실행하는 클래스
 * @author 서정의
 *
 */

@Repository
public class ReplyDAOImpl implements IF_ReplyDAO {
	@Inject //자바8부터 지원됨, 이전@Autowired, @Resource로 스프링빈을 주입
	private SqlSession sqlSession;
	
	@Override
	public void deleteReplyAll(Integer bno) throws Exception {
		// 여러개 레코드 한번에 지우기
		sqlSession.delete("replyMapper.deleteReplyAll", bno);
	}

	@Override
	public void deleteReply(ReplyVO replyVO) throws Exception {
		// 1개 레코드 지우기
		sqlSession.delete("replyMapper.deleteReply", replyVO);
	}

	@Override
	public void updateReply(ReplyVO replyVO) throws Exception {
		// 
		sqlSession.update("replyMapper.updateReply", replyVO);
	}

	@Override
	public void replyCountUpdate(Integer bno, int count) throws Exception {
		// 매개변수가 2개일때 1개의 오브젝트인 Map형태로 담아서 처리
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("bno", bno);
		paramMap.put("count", count);
		sqlSession.update("replyMapper.replyCountUpdate", paramMap);
	}

	@Override
	public void insertReply(ReplyVO replyVO) throws Exception {
		// 아래와 동일
		sqlSession.insert("replyMapper.insertReply", replyVO);
	}

	@Override
	public int countReply(Integer bno) throws Exception {
		// 아래와 동일
		return sqlSession.selectOne("replyMapper.countReply", bno);
	}

	@Override
	public List<ReplyVO> selectReply(Integer bno, PageVO pageVO) throws Exception {
		// sqlSession 템플릿사용("매퍼쿼리명",매개변수명")
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//paramMap.put("pageVO", pageVO);
		paramMap.put("queryStartNo", pageVO.getQueryStartNo());
		paramMap.put("queryPerPageNum", pageVO.getQueryPerPageNum());
		paramMap.put("bno", bno);
		return sqlSession.selectList("replyMapper.selectReply", paramMap);
	}
	
}
