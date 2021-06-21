package com.edu.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.dao.IF_BoardDAO;
import com.edu.vo.AttachVO;
import com.edu.vo.BoardVO;
import com.edu.vo.PageVO;

/**
 * 이 클래스는 DAO 메서드를 호출하는 기능을 함
 * @author 서정의
 *
 */

@Service //@애노테이션을 붙이면 스프링빈으로 등록이 됨
public class BoardServiceImpl implements IF_BoardService {
	@Inject
	private IF_BoardDAO boardDAO;
	
	@Override
	public List<AttachVO> readAttach(Integer bno) throws Exception {
		// 첨부파일 List형으로 조회 DAO호출
		return boardDAO.readAttach(bno);
	}

	@Override
	public int countBoard(PageVO pageVO) throws Exception {
		// 페이징처리시 PageVO의 totalCount 변수에 사용될 값을 리턴값으로 받음
		return boardDAO.countBoard(pageVO);
	}
	
	@Transactional // All or Not
	@Override
	public void deleteBoard(int bno) throws Exception {
		// 게시물 삭제시 3개의 메서드가 실행(댓글,첨부파일 -> 게시물이 삭제됨)
		// 트랜잭션이 필요, 작업순서 1. 첨부파일삭제 2. 게시물 삭제시 에러로 삭제가 안됨
		// 위와같은 상황을 방지하는 목적으로 @Transantional 애노테이션을 사용
		//특이사항 : 첨부파일은 DB만 삭제해서는 해결이 안됨 + 실제 업로드된 파일도 삭제가 필요함
		boardDAO.deleteAttachAll(bno);
		// 댓글삭제는 댓글폼 만든 이후에 처리
		boardDAO.deleteBoard(bno);
	}
	
	@Transactional
	@Override
	public void updateBoard(BoardVO boardVO) throws Exception {
		// 첨부파일이 있으면 updateAttach -> 게시물 업데이트 updateBoard 순서로 실행
		boardDAO.updateBoard(boardVO);
		//첨부파일 DB처리(아래)
		int bno = boardVO.getBno();
		String[] save_file_names = boardVO.getSave_file_names();
		String[] real_file_names = boardVO.getReal_file_names();
		if(save_file_names == null) { return; } // 조건이 맞지 않으면 빠져나감. 이후 실행안함
		AttachVO attachVO = new AttachVO();
		int index = 0;
		String real_file_name = "";
		for(String save_file_name: save_file_names) {
			real_file_name = real_file_names[index];
			attachVO.setBno(bno);
			attachVO.setSave_file_name(save_file_name);
			attachVO.setReal_file_name(real_file_name);
			boardDAO.updateAttach(attachVO);
			index = index +1; //index++; 과 동일
		}
				
	}
	
	@Transactional
	@Override
	public BoardVO readBoard(int bno) throws Exception {
		// 게시물 상세보기시 readBoard -> updateViewCount 순서로 실행. 2개의 메서드 필요
		BoardVO boardVO = boardDAO.readBoard(bno);
		boardDAO.updateViewCount(bno);
		return boardVO;
	}
	
	@Transactional
	@Override
	public void insertBoard(BoardVO boardVO) throws Exception {
		// 첨부파일 있으면 게시물 insertBoard 실행 -> 첨부파일 insertAttach 실행
		// 게시물 등록 + 반환값으로 bno 추가
		int bno = boardDAO.insertBoard(boardVO);
		// 첨부파일 등록
		String[] save_file_names=boardVO.getSave_file_names(); // 폴더에 저장될 배열 파일명들
		String[] real_file_names=boardVO.getReal_file_names(); // DB에 저장될 배열 파일명들
		if(save_file_names == null) {return;} //리턴이 발생되면, 이후 실행 안됨
		//첨부파일이 null이 아닐때 아래가 진행됨
		int index=0;
		String real_file_name = ""; // UI용 1개 파일명
		AttachVO attachVO = new AttachVO();
		for(String save_file_name:save_file_names) { //첨부파일 갯수만큼 반복진행
			real_file_name = real_file_names[index];
			attachVO.setBno(bno);
			attachVO.setReal_file_name(real_file_name);
			attachVO.setSave_file_name(save_file_name);
			boardDAO.insertAttach(attachVO);
			index++; // index = index+1; 과 동일
		}
	}

	@Override
	public List<BoardVO> selectBoard(PageVO pageVO) throws Exception {
		// DAO 한개만 호출
		return boardDAO.selectBoard(pageVO);
	}

}
