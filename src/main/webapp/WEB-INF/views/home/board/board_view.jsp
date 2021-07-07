<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- 메인콘텐츠영역 -->
	<div id="container">
		<!-- 메인상단위치표시영역 -->
		<%@ include file="./board_header.jsp" %>
		<!-- //메인상단위치표시영역 -->

		<!-- 메인본문영역 -->
		<div class="bodytext_area box_inner">			
			<ul class="bbsview_list">
				<li class="bbs_title">${boardVO.title}</li>
				<li class="bbs_date">작성일 : <span><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${boardVO.reg_date}"/></span></li>
				<li class="bbs_hit">조회수 : <span>${boardVO.view_count}</span></li>
				<li class="bbs_content">
					<div class="editer_content">
					   ${boardVO.content}
                    </div>
				</li>
				<li class="bbs_title" style="height:inherit">첨부파일<br>
					<c:forEach begin="0" end="1" var="idx">
						<c:if test="${boardVO.real_file_names[idx] != null}">
							<c:url var="url" value="/download">
								<c:param name="save_file_name" value="${boardVO.save_file_names[idx]}" />
								<c:param name="real_file_name" value="${boardVO.real_file_names[idx]}" />
							</c:url>
							<a href="${url}">다운로드
							${boardVO.real_file_names[idx]}<br>
							</a>
							<!-- 만약 첨부파일이 jpg,jpeg,gif,png,bmp라면 img태그를 사용해서 미리보기기능 추가 -->
							<c:set var="fileNameArray" value="${fn:split(boardVO.save_file_names[idx],'.')}" />
							<c:set var="extName" value="${fileNameArray[fn:length(fileNameArray)-1]}" />
							<c:choose>
								<c:when test="${fn:containsIgnoreCase(checkImgArray,extName)}">
									<img alt="다운로드이미지" style="width:100%;display:block;" src="/image_preview?save_file_name=${boardVO.save_file_names[idx]}">
								</c:when>
							</c:choose>							
						</c:if> 
					</c:forEach>
				</li>
			</ul>
			<p class="btn_line txt_right">
				<a href="/home/board/board_list?page=${pageVO.page}&search_type=${pageVO.search_type}" class="btn btn-default btn-flat">목록</a>
				<button type="button" id="btn_delete" class="btn btn-danger btn-flat">삭제</button>
				<button type="button" id="btn_update" class="btn btn-warning btn-flat">수정</button>
			</p>
			<form name="hide_form" id="hide_form" method="post" action="">
				<input type="hidden" name="bno" value="${boardVO.bno}">
				<input type="hidden" name="page" value="${pageVO.page}">
			</form>
			<script>
			$(document).ready(function(){
				var form = ${"#hide_form"};
				$("#btn_delete").click(function(){
					if(confirm("정말로 삭제하시겠습니까?")){
						form.attr("action","/home/board/board_delete");
						form.submit();
					}
				});
				$("#btn_update").click(function(){
					alert("수정 준비중입니다.");
				});
			});
			</script>
		</div>
		<!-- //메인본문영역 -->
	

	<!-- 댓글영역 -->
	<div class="row">
		<div class="col-md-12">
			<!-- 댓글 입력폼 -->
			<div class="card-default">
				<div class="card-header">
				  <h3 class="card-title">댓글 쓰기</h3>
				</div>
				<div class="card-body p-0">
				  <div class="bs-stepper linear">
					<div class="bs-stepper-header" role="tablist">
					  <div class="line"></div>
					</div>
					<div class="bs-stepper-content">
					  <!-- your steps content here -->
					  <div id="logins-part" class="content active dstepper-block" role="tabpanel" aria-labelledby="logins-part-trigger">
						<div class="form-group">
						  <label for="writer">작성자</label>
						  <input type="text" class="form-control" id="writer" placeholder="작성자를 입력해주세요">
						</div>
						<div class="form-group">
						  <label for="reply_text">댓글내용</label>
						  <input type="text" class="form-control" id="reply_text" placeholder="댓글 내용을 입력하세요">
						</div>
					  </div>
					  <div id="information-part" class="content" role="tabpanel" aria-labelledby="information-part-trigger">
						<button type="button" class="btn btn-warning" id="btn_reply_write">댓글등록</button>
					  </div>
					</div>
				  </div>
				</div>
				<!-- /.card-body -->
				<div class="card-footer">
					아래 댓글리스트 버튼을 클릭하시면 댓글 목록이 출력이 됩니다.
				</div>
			  </div>
			<!-- //댓글 입력폼 -->
		</div>
		<div class="col-md-12">
		  <!-- The time line -->
		  <div class="timeline">
			<!-- timeline time label -->
			<div class="time-label">
			  <span class="bg-red" data-toggle="collapse" href="#collapseReply" role="button" id="btn_reply_list">댓글리스트 [<span>1</span>]</span>
			</div>
		<!-- 콜렙스 시작 -->
		<div class="collapse timeline" id="collapseReply">
			<!-- time_label 이루 after요소로 템플릿결과가 여기에 출력됨 -->
			<!-- /.timeline-label -->
			<!-- timeline item -->
			<!-- 댓글리스트를 자바스크립트의 빵틀(template)을 만든다. -->
			<!-- 고전 append 함수를 사용하지 않고, handlebars라는 확장프로그램 -->
			<!-- 장점은 기존 퍼블리셔가 만든 태그를 그대로 사용 가능 -->
			<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
			<script id="template" type="text/x-handlebars-template">
			{{#each .}}
			<div class="div_template" data-rno="{{rno}}">
			  <i class="fas fa-envelope bg-blue"></i>
			  <div class="timeline-item">
				<h3 class="timeline-header">{{replyer}}</h3>
				<div class="timeline-body"> {{reply_text}}</div>
				<div class="timeline-footer">
				  <a class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modal-reply">수정</a>
				</div>
			  </div>
			</div>
			{{/each}}
			</script>
			<!-- 페이징 처리 -->
			<div class="row">
				<ul class="pagination" style="margin: 0px auto;">
					<!-- <li class="paginate_button page-item previous disabled" id="example2_previous">
						<a href="#" aria-controls="example2" data-dt-idx="0" tabindex="0" class="page-link">Previous</a>
					</li>
					<li class="paginate_button page-item active">
						<a href="#" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">1</a>
					</li>
					<li class="paginate_button page-item ">
						<a href="#" aria-controls="example2" data-dt-idx="2" tabindex="0" class="page-link">2</a>
					</li>
					<li class="paginate_button page-item ">
						<a href="#" aria-controls="example2" data-dt-idx="3" tabindex="0" class="page-link">3</a>
					</li>
					<li class="paginate_button page-item ">
						<a href="#" aria-controls="example2" data-dt-idx="4" tabindex="0" class="page-link">4</a>
					</li>
					<li class="paginate_button page-item ">
						<a href="#" aria-controls="example2" data-dt-idx="5" tabindex="0" class="page-link">5</a>
					</li>
					<li class="paginate_button page-item ">
						<a href="#" aria-controls="example2" data-dt-idx="6" tabindex="0" class="page-link">6</a>
					</li>
					<li class="paginate_button page-item next" id="example2_next">
						<a href="#" aria-controls="example2" data-dt-idx="7" tabindex="0" class="page-link">Next</a>
					</li> -->
				</ul>
			</div>
			<!-- //페이징 처리 -->
		</div>
		<!-- //콜렙스 끝 -->
		</div>
		<!-- END timeline item -->
		</div>
		<!-- /.col -->
	</div>
	<!-- //댓글영역 -->
	</div>
    <!-- //메인콘텐츠영역 -->

<%@ include file="../include/footer.jsp" %>