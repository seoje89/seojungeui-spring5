package com.edu.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.dao.IF_ReplyDAO;
import com.edu.vo.PageVO;
import com.edu.vo.ReplyVO;

/**
 * 이 클래스는 댓글 DAO의 CRUD를 실행하는 서비스
 * @author 서정의
 *
 */
@Service
public class ReplyServiceImpl implements IF_ReplyService {
	@Inject
	private IF_ReplyDAO replyDAO;
	
	@Transactional
	@Override
	public void deleteReply(ReplyVO replyVO) throws Exception {
		// 2개의 DAO를 호출
		replyDAO.deleteReply(replyVO);
		// 현재 게시물 고유번호 bno가 필요
		replyDAO.replyCountUpdate(replyVO.getBno(), -1);
	}

	@Override
	public void updateReply(ReplyVO replyVO) throws Exception {
		// 
		replyDAO.updateReply(replyVO);
	}
	
	@Transactional // All or NotAll
	@Override
	public void insertReply(ReplyVO replyVO) throws Exception {
		// 2개의 DAO를 호출(실행)
		replyDAO.insertReply(replyVO);
		replyDAO.replyCountUpdate(replyVO.getBno(), 1);
	}

	@Override
	public int countReply(Integer bno) throws Exception {
		// 1개 레코드 출력, 해당게시물의 댓글의 수
		return replyDAO.countReply(bno);
	}

	@Override
	public List<ReplyVO> selectReply(Integer bno, PageVO pageVO) throws Exception {
		// DAO 객체사용
		return replyDAO.selectReply(bno, pageVO);
	}

}
