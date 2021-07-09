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
						<!-- 위처럼 c:url로 쿼리스트링을 처리하면 한글이 인코딩되어서 전송됨(한글 안깨짐) -->
						<a href="${url}">다운로드
						${boardVO.real_file_names[idx]}<br>
						</a>
						<!-- 만약 첨부파일이 jpg,jpeg,gif,png,bmp라면 img태그를 사용해서 미리보기기능 추가 -->
						<c:set var="fileNameArray" value="${fn:split(boardVO.save_file_names[idx],'.')}" />
						<c:set var="extName" value="${fileNameArray[fn:length(fileNameArray)-1]}" />
						<c:choose>
							<c:when test="${fn:containsIgnoreCase(checkImgArray,extName)}">
								<img alt="다운로드이미지" style="max-width:100%;display:block;" src="/image_preview?save_file_name=${boardVO.save_file_names[idx]}">
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
			var form = $("#hide_form");
			$("#btn_delete").click(function(){
				if(confirm("정말로 삭제하시겠습니까?")){
					form.attr("action","/home/board/board_delete");
					form.submit();
				}
			});
			$("#btn_update").click(function(){
				form.attr("action","/home/board/board_update_form");
				form.attr("method","get");
				form.submit();
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
					  <label for="replyer">작성자</label>
					  <input readonly value="${session_userid}" type="text" class="form-control" id="replyer" placeholder="작성자를 입력해주세요">
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
		  <span class="bg-red" data-toggle="collapse" href="#collapseReply" role="button" id="btn_reply_list">댓글리스트 [<span id="reply_count">${empty boardVO.reply_count?'0':boardVO.reply_count}</span>]</span>
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
<!-- 댓글 하단의 페이징 처리용 변수값지정 -->
<input id="reply_page" value="1" type="hidden">
<!-- //메인콘텐츠영역 -->
<!-- 모달창(초기엔 숨김상태 - 수정버튼 클릭하면 나오는 창) -->
<div class="modal fade" id="modal-reply">
	<div class="modal-dialog">
	<div class="modal-content">
	<div class="modal-header">
		<h4 class="modal-title">작성자명</h4>
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span>
		</button>
	</div>
	<div class="modal-body">
		<input class="form-control" type="text" name="modal_reply_text" id="modal_reply_text" value="댓글내용출력">
	</div>
	<div class="modal-footer"> <!-- justify-content-between:양쪽배분정렬 -->
		<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
		<button id="btn_reply_update" type="button" class="btn btn-primary">수정</button>
		<button id="btn_reply_delete" type="button" class="btn btn-danger">삭제</button>
		<input type="hidden" id="rno" name="rno">
	</div>
	</div>
	<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
 </div>
<!-- //모달창 -->
<%@ include file="../include/footer.jsp" %>
<script>
//댓글 리스트 출력 함수
  var printReplyList = function(data, templateData, target) {
    // result json 데이터를 templateDate에 파싱(아래)
    var template = Handlebars.compile(templateData.html()); // 템플릿을 태그로 변환
    var html = template(data); // 파싱처리
    $('.div_template').remove(); // 기존 댓글 데이터 누적을 방지, target 안쪽의 자식만 지움
    target.prepend(html);
  };
//댓글 하단 페이지를 출력하는 함수
  var printPagingList = function(pageVO, target) {
    // 스프링 RestAPI 서버에서 받은 pageVO 오브젝트를 target에 파싱(아래)
    $(target).html(''); // target의 내용만 지우고, target은 남아있음
	// pageVO = 스프링에서 받은 json데이터, 변수 3개 pageVO.prev(이전 데이터가 있다면 true), pageVO.next(다음 데이터가 있다면 true), pageVO=5페이지 가정
	var pagination = ''; // 문자열 누적변수
	// Previous 출력(아래)
	var prevlink, nextlink;
	if(pageVO.prev) { prevlink = ''; } else { prevlink = 'disabled'; }
	pagination += '<li class="paginate_button page-item previous '+prevlink+'" id="example2_previous">';
	pagination += '<a href="'+(pageVO.startPage-1)+'" aria-controls="example2" data-dt-idx="0" tabindex="0" class="page-link">Previous</a>';
	pagination += '</li>'; //pagination = pagination + 과 pagination += 동일(단축)
	// previous 출력 끝

	var active = '';
	for(var i=pageVO.startPage; i<=pageVO.endPage; i++) {
		if(i==0) { active = 'active'; } else { active = ''; }
		pagination += '<li class="paginate_button page-item '+active+'">';
		pagination += '<a href="'+i+'" aria-controls="example2" data-dt-idx="6" tabindex="0" class="page-link">'+(i)+'</a>';
		pagination += '</li>';
	}
	// Next 출력(아래)
	if(pageVO.next) { nextlink = ''; } else { nextlink = 'disabled'; }
	pagination += '<li class="paginate_button page-item next '+nextlink+'" id="example2_next">';
	pagination += '<a href="'+(pageVO.endPage+1)+'" aria-controls="example2" data-dt-idx="7" tabindex="0" class="page-link">Next</a>';
	pagination += '</li>';
	$(target).append(pagination);
  };
//함수형 변수로서 댓글 리스트를 RestAPI에서 받아서 출력하는 변수
var replyList = function(){
	var page = $("#reply_page").val();
	$.ajax({
		type:"post",
		url:"/reply/reply_list/${boardVO.bno}/"+page,
		dateType:"json",//전송받는 데이터형태 json
		success:function(result){
			if(typeof result=="undefined" || result=="" || result==null) {
				$("#collapseReply").empty();//div태그 안의 내용만 삭제하기
				$("#collapseReply").html('<div class="pagination justify-content-center"><ul class="pagination pageVO">조회된 값이 없습니다.</ul></div>');//div태그 안의 내용을 추가하기
				
			}else{
				//json데이터를 화면에 파싱(구버전:xml 복잡한 태그 데이터를 파싱)
				//템플릿 빵틀에 result 데이터를 바인딩해서 출력
				//JSON.parse(문자열) -> 일반문자열을 json으로 변경하는 함수
				//JSON.stringify(json데이터) -> json데이터를 일반문자열로 변경하는 함수
				console.log("여기까지" + JSON.stringify(result.replyList));//크롬 콘솔에서 확인
				printReplyList(result.replyList, $("#template"), $("#collapseReply"));
				printPagingList(result.pageVO,".pagination");
			}
		},
		error:function(result){
			//console.log(result); //크롬에서만 확인가능, 이클립스는 X
			//전체 JSon데이터를 출력할때 stringify함수로 형변환해서 출력
			//만약 JSon데이터에서 키이름을 알게되면 stringify 함수가 필요없이 result.responseText로 바로 출력 가능
			alert(result.responseText); //이클립스에서 확인O
			//관리자단에서만 디버그하고 사용자단에서는 아래 항목만 유지
			alert("RestAPI서버가 작동하지 않습니다. 다음에 이용해주세요.0");
		}
	})
};
</script>
<script>
//댓글 CRUD처리
$(document).ready(function() {
	//댓글 모달창 삭제버튼 액션처리(아래)
	$("#btn_reply_delete").click(function(){
		//댓글을 삭제할때 필요한 변수 2개 rno(삭제쿼리),bno(댓글카운트업데이트) 확인
		var rno = $("#rno").val(); //모달창의 input태그의 값을 가져오기
		var bno = "${boardVO.bno}"; //자바변수값. @Controller의 model에 담긴 값을 사용
		$.ajax({
			type:"delete", //전송타입, 컨트롤러의 RequestMethod의 값과 동일
			url:"/reply/reply_delete/"+bno+"/"+rno, //endpoint = @RestController의 @RequestMapping(value="")
			dataType:"text", //결과값을 받는 데이터형식 text-String, json-Map<String,Object>
			/* data:"", //처리할 값을 보내는 데이터형식  <- json을 사용하지 않고 패스베리어블로 보내기 때문에 필요없음 */
			/* headers:"", //크롬의 개발자도구 > 네트워크항목의 오른쪽 창에서 확인가능,전송방식때문에 필요하지만 패스베리어블로 보내서 필요없음 */
			success:function(result){
				if(result=="success"){
					alert("삭제되었습니다.");
					//삭제 후 모달창 숨기고, 댓글카운트UI -1처리, 댓글리스트 리프레시(랜더링)
					$("#modal-reply").modal("hide");
					var reply_count = $("#reply_count").text();//Get
					$("#reply_count").text(parseInt(reply_count)-1);//Set
					$("#reply_page").val("1"); //삭제한 후 1페이지로 이동, 삭제후 남은 댓글이 5개 단위로 떨어질때 값을 잃어서 댓글페이지가 제대로 안뜨는 현상 처리 
					replyList();
				}
			},
			error:function(){
				alert("RestAPI서버가 작동하지 않습니다. 다음에 시도해주세요.1");
			}
		});
	});
	//댓글 모달창 수정버튼 액션처리(아래)
	$("#btn_reply_update").click(function(){
		//댓글 수정할때 필요한 변수 확인
		var reply_text = $("#modal_reply_text").val(); // modal내 태그로 변경
		var rno = $("#rno").val(); // modal 내 input 태그로 추가
		if(reply_text == '' || rno == '') { // && 은 and(전부 만족해야 실행됨), || 은 or(하나라도 만족하면 실행됨)
			alert("댓글내용은 필수입력값입니다");
			return false; //더이상 실행없이 콜백함수를 빠져나감 
		}
		$.ajax({
			type:'patch', //컨트롤러의 method값과 같아야함
			url:'/reply/reply_update',
			dataType:'text', // RestAPI 컨트롤러에서 받는 데이터형식
			data:JSON.stringify({
				rno:rno,
				reply_text:reply_text
			}), //보내는 데이터 자체는 텍스트 형식으로 변경(형식은 text지만 타입은 json)
			headers:{ //보내는 데이터 형식
				"Content-Type":"application/json",
				"X-HTTP-Method-Override":"PATCH"
			},//json데이터 형식으로 브라우저에 내장된 헤더값을 지정
			success:function(result){ // 댓글 입력이 성공했을때
				if(result=="success"){
					alert("수정에 성공했습니다.");
					//모달창 숨기기(아래)
					$("#modal-reply").modal("hide");
					//댓글 수정 후 화면에 댓글 목록 출력하는 함수실행
					replyList();//화면의 일부분만 리프레시(재생)
				}
				
			},
			error:function() {
				alert("RestAPI 서버가 작동하지 않습니다. 잠시후 이용해 주세요.2")
			}
		});
	});
	//하단 페이징 링크의 링크속성 처리(아래)
	$(".pagination").on("click","li a",function(event){
		event.preventDefault();//a태그의 링크속성을 사용하지 않겠다
		$("#reply_page").val($(this).attr("href"));
		replyList();
	});
	//댓글 리스트 버튼(아래)
	$("#btn_reply_list").click(function(){
		replyList();//댓글 리스트 출력 Ajax 호출
	});
	//댓글 등록 버튼(아래)
	$("#btn_reply_write").click(function(){
		//RestAPI 엔드포인트로 보낼값 지정
		var bno = "${boardVO.bno}"; // 자바변수값 : 게시물번호
		var reply_text = $("#reply_text").val();
		var replyer = $("#replyer").val();
		if(reply_text == '' || replyer == '') { // && 은 and(전부 만족해야 실행됨), || 은 or(하나라도 만족하면 실행됨)
			alert("작성자ID와 댓글내용은 필수입력값입니다");
			return false; //더이상 실행없이 콜백함수를 빠져나감 
		}
		$.ajax({
			type:'post', //컨트롤러의 method값과 같아야함
			url:'/reply/reply_insert',
			dataType:'text', // RestAPI 컨트롤러에서 받는 데이터형식
			data:JSON.stringify({
				bno:bno,
				reply_text:reply_text,
				replyer:replyer
			}), //보내는 데이터 자체는 텍스트 형식으로 변경(형식은 text지만 타입은 json)
			headers:{ //보내는 데이터 형식
				"Content-Type":"application/json",
				"X-HTTP-Method-Override":"POST"
			},//json데이터 형식으로 브라우저에 내장된 헤더값을 지정
			success:function(result){ // 댓글 입력이 성공했을때
				var reply_count = $("#reply_count").text(); //EL에서 초기값 0
				$("#reply_count").text(parseInt(reply_count)+1);
				//댓글을 신규등록 후 댓글 페이징의 1페이지로 이동
				$("#reply_page").val("1"); // val()로 값을 입력, input태그
				//댓글 입력 후 화면에 댓글 목록 출력하는 함수실행
				replyList();
				//alert("test출력2");
			},
			error:function() {
				alert("RestAPI 서버가 작동하지 않습니다. 잠시후 이용해 주세요.3")
			},
			beforeSend:function() {
				//alert("전송전에 실행할 내용1");
			},
			complete:function() {
				//success 완료된 이후 실행해야할 내용이 있을때 사용
				//success에서 함수가 여러개 실행될때 순서가 중요한경우 분리해서 사용
				alert("등록이 완료되었습니다.");
				$("#reply_text").val("");//댓글 등록후 기존 입력된 댓글내용 자동으로 지워줌
			},
			async:true //Async+Javascript And Xml:비동기 데이터 통신
			//Ajax이지만, 비동기 false로 하는경우(아이작스로 대용량첨부파일 업로드할때)
			//success:업로딩중...표시가 됨 -> complete 실행됨
		});
	});
});
</script>

<script>
  // 댓글리스트에서 수정 버튼클릭시 현재 선택한 값을 모달창에 보여주는 것을 구현(아래)
  $(document).ready(function(){
    $('.timeline').on("click", '.div_template', function(){
      $('#rno').val($(this).attr('data-rno'));
      $('#modal_reply_text').val($(this).find('.timeline-body').text());
      $('.modal-title').html($(this).find('.timeline-header').text());
    });
  });
  </script>