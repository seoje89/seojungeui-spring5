package com.edu.vo;
/**
 * 이 클래스는 공통(회원관리,게시물관리)으로 사용하는 페이징처리+검색기능이 포함된 클래스
 * @author 서정의
 * 이 클래스는 오라클이든, MySql(마리아DB) 어디서든 공통으로 사용하는 Get/Set.
 * queryStartNo, queryPerPageNum, page, perPageNum, startPage, endPage
 * 검색에 필요한 변수(쿼리변수만): 검색어(search_keyword), 검색조건(search_type)
 *
 */
public class PageVO {
	private String board_type; // 게시판 종류표시 전용 변수추가
	private int queryStartNo; //쿼리전용 변수, 페이징쿼리에서 시작 페이지 인덱스번호
	private int queryPerPageNum; //쿼리전용
	private Integer page; // jsp에서 발생 자바전용. int인데 Null값을 허용
	private int perPageNum; // UI 하단에 보여줄 페이징 개수 계산
	private int totalCount; // 계산식의 기초값으로 이 전체개수가 구해진 이후 계산식이 진행됨.
	private int startPage; // 위 perPageNum으로 구하는 UI하단페이지 시작번호(반복문시작)
	private int endPage; // 위 perPageNum으로 구하는 UI하단페이지 끝번호(반복문끝)
	private boolean prev; // UI하단 이전 페이지로 이동이 가능한지 판별
	private boolean next; // UI하단 다음 페이지로 이동이 가능한지 판별
	private String search_keyword; // jsp에서 받은 검색어 쿼리전용 변수
	private String Search_type; // 검색조건에 해당되는 쿼리전용 변수
	
	@Override
	public String toString() {
		return "PageVO [queryStartNo=" + queryStartNo + ", queryPerPageNum=" + queryPerPageNum + ", page=" + page
				+ ", perPageNum=" + perPageNum + ", totalCount=" + totalCount + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", prev=" + prev + ", next=" + next + ", search_keyword=" + search_keyword
				+ ", Search_type=" + Search_type + "]";
	}
	//Get,Set용 메서드 추가
	public String getBoard_type() {
		return board_type;
	}
	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}
	
	public int getQueryStartNo() {
		//this.page-1 하는 이유는 jsp에서는 1부터 시작하지만 쿼리에서는 0부터 사용되기 때문에 page*페이지당 보여줄 개수
		queryStartNo = (this.page-1)*queryPerPageNum;
		//queryStartNo = this.page-1;//오라클은 이대로 가능, mysql에서는 쿼리를 수정한다
		return queryStartNo;
	}
	public void setQueryStartNo(int queryStartNo) {
		this.queryStartNo = queryStartNo;
	}
	public int getQueryPerPageNum() {
		return queryPerPageNum;
	}
	public void setQueryPerPageNum(int queryPerPageNum) {
		this.queryPerPageNum = queryPerPageNum;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		//전체개수 값을 지정한 이후 계산식 실행
		calcPage();
	}
	private void calcPage() {
		// 이 메서드는 totalCount 변수값을 기반으로 prev,next,startPage,endPage 등등을 구현하게 됨.
		//UI하단의 페이지번호 상상 <(비활성) 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 >(활성 - 링크값11)
		//위 상상대로 진행하면 전체개수는 101개 이상이 된다.
		//2 1.9 1.8 ... 1.2 1.1 => 2 (이것이 ceil=소수점 이하 올림)
		//ceil(11/10)*10 => 20페이지 == tempEnd 1~10페이지에서 11페이지값이 존재하면, 끝페이지에 임시로 20이라는 숫자를 준다.
		int tempEnd = (int) Math.ceil(page/(double)this.perPageNum)*this.perPageNum;
		//jsp에서 클릭한 페이지번호 예로 1부터 10까지는 클릭하면, 임시 끝페이지가 10.
		//만약 11페이지를 클릭하면, 임시 끝페이지가 20. 확인 위 tempEnd변수값으로 아래내용에 이용
		this.startPage = (tempEnd - this.perPageNum) + 1; //UI페이지 하단에 페이징 번호가 출력되도록 하는 반복문의 시작 변수.
		//(20-10)+1 = 현재페이지의 UI 하단의  시작페이지번호 11(시작페이지)
		//예, 1-10까지는 page를 jsp에 클릭했을때 시작페이지가 1페이지, 하지만 11페이지부터는 위 계산식을 이용하면 시작페이지가 11페이지로 변동됨(11-20으로 변동)
		//위 startPage 변수 jsp에서 반복문의 시작값으로 사용. 지금 토탈 개수는 101개 이상(게시물100, 회원100)
		if(tempEnd*this.queryPerPageNum > this.totalCount) {
			this.endPage = (int)Math.ceil((this.totalCount/(double)this.queryPerPageNum));
			//위 계산식을 예로들면, (101/10)= ceil 10.1 = 11(엔드페이지)
		} else {
			this.endPage = tempEnd; //20(엔드페이지)
		}
		// 여기까지가 startPage, endPage를 구하는 계산식
		// 이후는 prev, next 구하는 계산식
		// UI하단의 페이지번호 상상 <(비활성) 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 >(활성-링크값 10+1)
		//<(활성링크startPage-1)11,12,13,14,15,16,17,18,19,20>(활성-링크값 endPage+1)
		this.prev = (this.startPage > 1);//startPage가 1페이지 아닐때만 prev 비활성화 = false
		//이전버튼<(활성화시 링크값은 startPage-1), >다음버튼(활성화시 링크값은 endPage+1)
		this.next = (this.endPage*this.queryPerPageNum) < this.totalCount; // 10*10 = 100 < 101이상이라서 next 활성화 = true
		
		
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public String getSearch_keyword() {
		return search_keyword;
	}
	public void setSearch_keyword(String search_keyword) {
		this.search_keyword = search_keyword;
	}
	public String getSearch_type() {
		return Search_type;
	}
	public void setSearch_type(String search_type) {
		Search_type = search_type;
	}
	
}
