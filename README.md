#### 작업예정
- 정방향개발(앞으로): 매퍼쿼리>DAO>Service>[JUnit-Ioc,DI기능구현]컨트롤러클래스>JSP
- 역방향개발(클래스간이동이 빠름): 정방향으로 개발한것 검증용으로만 사용
- 5주 후 금요일이 수업 마무리
=========== 3주간 작업내역 시작(관리자단 - 손이 많이감) =============
- 관리자단 회원목록 처리 마무리(1.페이징및 검색기능구현).
- model을 이용해서 결과를 JSP로 구현.(2.JSP화면은 표준언어인 JSTL로 구현)
- [공지]06-17 목요일(4교시) UI 디자인 시험 있습니다.(화면기획서XLS제작, 화면설계서PPT제출)
- 그래서, 다음주 월요일 점심시간 피곤할떄 현재 프로젝트를 정리하는 문서작업 시간을 갖겠습니다.
- 나머지 관리자 회원관리 CRUD 화면 JSP처리.
- 관리자단 게시판 생성관리 CRUD 처리.(3.AOP기능구현).
- 관리자단 게시물관리 CRUD 처리(4.파일업로드구현,5.트랜잭션구현).
- 관리자단 댓글 CRUD 처리(6.RestAPI기능구현-개발트렌드).
- 관리자단 왼쪽메뉴 UI 메뉴 고정시키기(7.jQuery로 구현).
- 사용자단 로그인 화면 JSP로 만들기.
- 로그인처리 및 관리자 권한체크 기능 추가(8.스프링시큐리티구현). 
============ 3주간 작업내역 끝(07.02 금) ==============
============ 2주간 작업내역 시작(사용자단은 관리자단 로직 사용) =============
- 사용자단 회원가입, 수정, 탈퇴 JSP기능 추가.
- 사용자단 게시판 CRUD 처리.
- 사용자단 댓글 CRUD 처리.
- 헤로쿠 클라우드에 배포(9.클라우드 배포 CI/CD 구현).깃(최신소스)-연동-헤로쿠(배포)
- 문서작업(제출용)
- [알고리즘 다이어그램 기반으로 자바 코딩 테스트]
- [사용자단 네이버아이디로그인 처리(10.외부RestAPI구현).]
============ 2주간 작업내역 끝(07.16 금) ==============
- 헤로쿠 클라우드에 배포할때, 매퍼 폴더의 mysql폴더 내의 쿼리에 now()를 date_add(now(3), interval 9 HOUR)로 변경예정(이유는 DB서버의 타임존이 한국이 아니라 출력되는 시간이 다름)

#### 20210701(목) 작업예정
- 수업 시작전 깃허브 암호정책 변경으로 토큰사용하는 방법 공유
- 람다식사용예 : https://github.com/miniplugin/SQLite-kimilguk/blob/master/app/src/main/java/com/human/sqlite_kimilguk/MainActivity.java
- 어제 시큐리티적용 부분 확인(web.xml에서 누락된 부분 모두 추가)

```
<!-- 스프링 시큐리티때문에 필터(걸러주는)추가 -->
<filter>
	<filter-name>springSecurityFilterChain</filter-name>
	<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>
<filter-mapping>
	<filter-name>springSecurityFilterChain</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
```

- 어제 시큐리티 context 누락된 부분 추가(security-context.xml)

```
<security:authentication-provider>
	<security:password-encoder ref="passwordEncoder" />
</security:authentication-provider>
<!-- 위 쿼리에서 사용할 패스워드 암호화 id passwordEncoder 빈 클래스를 생성(아래) -->
<bean id="passwordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder" />
```
- 스프링시큐리티 로그인및 권한체크 설정 후 사용자단 로그인 구현 예정.(관리자단 끝 이면서, 사용자단 시작): 사용자단 로그인/로그아웃 기능 처리
- 사용자단 회원가입, 수정, 탈퇴 JSP기능 추가예정.

#### 20210630(수) 작업
- 댓글 Delete 구현 후 마무리

#### 20210629(화) 작업
- json데이터(1개 레코드=K:V 다양한 형태)가 자바의 List데이터(1개 레코드=K:V 제한이 존재)와 대부분 같음. 차이점 : key:value 형태는 같으나 밸류값의 형태차이 존재
- 게시물 상세보기 페이지에는
- 게시물 관련내용 : 컨트롤러에서 보낸 model 객체에 담긴 변수값을 jsp사용
- 댓글 관련내용: Rest컨트롤러에서 보낸 ResponseEntity 객체에 담긴 변수값을 jsp사용
- RestAPI가 주로 사용되는 곳은: 1페이지로 서비스가 이루어지는 곳에서 사용됨
- 데이터를 시각화하는 페이지에 주로 사용 : 구글맵, 네이버맵기반의 데이터를 시각화해서 수익창출 서비스
- RestAPI가 스프링과 연동,노드js + 구글맵 연동 하면 실시간으로 결과물을 공유할수 있게 만들어줌
- Rest컨트롤러에서 CRUD중 Delete 마무리
- jsp에서 $.ajax를 이용해서 RestAPI서버 사용 - 댓글 마무리 -
- $.ajax로 CRUD를 구현할때는 전송값은 json데이터로 보내고(submit으로 보내지 않기 때문에 form태그가 필요없음), 받을때는 List(json),CUD(문자열)
- submit은 form태그가 있을때만 작동하는 브라우저 내장 명령
- 댓글 RUD작업 시작

#### 20210628(월) 작업예정
- 댓글VO제작 -> 매퍼쿼리제작 -> DAO클래스제작 -> Service클래스제작까지 완료 // @RestController클래스제작 -> 크롬부메랑테스트(JUnit테스트 대신) -> JSP제작(1페이지로 CRUD처리 Ajax이용)
- 위 작업전, 크롬 확장 프로그램 중 부메랑 실습. id_check 메서드 이용
- 네트워크로 자료를 전송하는 방식: SOAP(구버전 프로토콜), REST(신버전, HTTP방식으로 헤더에 자료를 담아서 보내는 방식)
- Endpoint : 마이크로서비스는 RestAPI로 구현되고, RestAPI에 요청하는 URL을 엔드포인트라고 함
- 엔드포인트(URL주소)에는 데이터를 전송할때, 쿼리스트링으로 보내지 않는 방식
- 예) 구방식 /reply/reply_list?bno=59&page=1 -> @RequestParam 애노테이션으로 받음
- 예) 신방식 /reply/reply_list/{변수값(예시 :bno변수값)}/{변수값(예시: page변수값)} -> @PathVariable 애노테이션으로 받음
- 결과, /reply/reply_list/59/1 (검색에 쉽게 노출시키는것이 목적)
- 트렌드: 마이크로서비스, 기존 컨트롤러(게이트웨이)를 모두 RestController로 변경이 필요

#### 20210625(금) 작업
- Pull 안되는 원인 : 로컬 이클립스에서 commit 할것이 남아있으면 pull이 안됨
- 해결책은 이클립스에서 커밋 후 다시 pull로 해결
- 게시물관리 Create 작업 마무리.
- 고전 CRUD와 RestFull(API)방식 차이점: 고전(화면이 이동하면서 CRUD처리), Rest방식(1개화면에서 CRUD처리)
- 관리자단 댓글관리 CRUD 처리(6.RestAPI서버구현,JUnit대신에 크롬부메랑으로 테스트)
- 댓글VO제작 -> 매퍼쿼리제작 -> DAO클래스제작 -> Service클래스제작 -> @RestController클래스제작 -> 크롬부메랑테스트(JUnit테스트 대신) -> JSP제작(1페이지로 CRUD처리 Ajax이용)
- 스프링시큐리티 로그인및 권한체크 설정 후 사용자단 로그인 구현 예정.(관리자단 끝이면서 사용자단 시작)
- 사용자단 회원가입, 수정, 탈퇴 JSP기능 추가예정.
  
#### 20210624(목) 작업
```
[복습]오늘 작업한 첨부파일 처리도 데이터 변수의 이동상태나 변수값이 제일 중요합니다. 핵심은 아래와 같습니다. Attach테이블에서 select쿼리 결과 테이터 구조는 아래와 같습니다. List delFiles = [ {"save_file_name":"abc1.jpg","real_file_name":"한글이미지1.jpg","bno":"bno10"}, {"save_file_name":"abc2.jpg","real_file_name":"한글이미지2.jpg","bno":"bno10"} ] 데이터베이스에서 가져올때, 위와 같이 구조가 발생됩니다. 구조를 정리하면 아래와 같습니다. 대괄호 List[VO배열] 안에 중괄호 VO{1개레코드 } 안에 콜론으로 "키":"값" 구분 후 콥마, 로 멤버변수들을 구분합니다.
```
- file.getBytes() 설명 포함 board_update메서드 리뷰 후 수업진행.
- 작업순서: CRUD -> UC 작업.
- update: updateBoard(서비스)참조 -> board_update(컨트롤러)작업+jsp작업
- 업데이트 이후엔 파일업로드 구현 후 /download 컨트롤러 실습예정.
- 관리자단 댓글관리 CRUD 처리(6.RestAPI서버구현,JUnit대신에 크롬부메랑으로 테스트)

#### 20210623(수) 작업
- 시큐어코딩 방지메서드: <(S|s)cript -> &lt;script 로 변경(코딩태그를 특수문자로 변경하여 실행되지 않는 코드가 생성됨)
- jsp에서 -> 컨트롤러로 단방향 보낼때 : @RequestParam("")
- 컨트롤러에서 -> jsp로 단방향 보낼때 : Model model
- jsp <-> 컨트롤러 양방향 전송 : @ModelAttribute("")
- redirect에서는 Model로 담아서 보낼수가 없음
- redirect에서는 Model 대신에 RedirectAttributes 클래스를 이용해서 jsp 값을 보냄(사용자단에서 입력/수정/삭제 성공시 redirect 사용하여 데이터를 보낼때 사용)
- 세션 사용법: 겟(Get), 셋(Set), 삭제(Remove) 하는방법
- 세션 생성법: session.setAttribute("세션변수명","값"); //로그인시 세션변수 생성
- 세션 값불러오기: session.getAttribute("세션변수명");
- 세션 삭제하기: session.removeAttribute("세션변수명"); // 변수삭제
- 전체세션 삭제하기: session.invalidate(); // 전체 세션변수명을 삭제 = 세션초기화 = 로그아웃시 사용
- 수업전 작업예정: ie11이하 계열에서 한글 검색후 페이지 선택시 400에러발생(크롬계열은 문제없음) - AOP로 처리
- 작업순서 : CRUD -> UC 작업예정
- 업데이트 이후 파일 업로드 구현 후 /download 컨트롤러 실습예정
- update : updateBoard(서비스)참조 -> board_view(컨트롤러)작업 + jsp작업
- 관리자단 댓글관리 CRUD 처리(RestAPI 서버구현, JUnit 대신에 크롬부메랑으로 테스트)

```
내일 수업전 실숩 순서는 아래와 같습니다.
아래 순서대로 하시고, 개선된 기능은 수업시 알려 드리겠습니다.^^
ie에서 한글검색과 페이징처리 함께사용시 에러상황 처리
AOP로 처리 되었습니다.
-#1 AOP에서 아래내용 추가
String search_keyword = null;
search_keyword = pageVO.getSearch_keyword();
if(search_keyword != null) {//최초로 세션변수가 발생
   session.setAttribute("session_search_keyword", search_keyword);
}
if(session.getAttribute("session_search_keyword") != null) {
   search_keyword = (String) session.getAttribute("session_search_keyword");
   if(pageVO != null) {//Set은 pageVO가 null아닐 경우만 실행되도록
      pageVO.setSearch_keyword(search_keyword);//검색목표달성:여기서 항상 값을 가져가도록 구현됩니다.
   }
}
-#2 member와 board 뷰jsp파일에서 아래 내용을 일괄 삭제
&search_keyword=${pageVO.search_keyword}
-#3 AdminController에서 아래 내용 일괄 삭제
+"&search_keyword="+pageVO.getSearch_keyword()
-#4. 기능개선 추가
AspectAdvice클래스 PageVO가 메서드매개변수 인스턴트인 조건시 추가
if(pageVO.getPage() == null) {
 session.removeAttribute(“session_search_keyworb”);
}
또는
검색창에 ${session_search_keyword}추가
그리고, include폴더 header.jsp 에 링크값에 ?search_type= 추가
```

#### 20210622(화) 작업
- 수업시작전 아래 내용 확인

```
pageVO 객체가 발생하지 않는 곳에는 에러가 발생됩니다. 에러발생시 수정하실 부분은 아래와 같습니다.
[수정전-아래]
- pageVO.setBoard_type(board_type);//검색목표달성:...
[수정후-아래]
if(pageVO != null) {
   pageVO.setBoard_type(board_type);//검색목표달성:...
}
```

- 스프링시큐리티: 로그인정보가 발생=세션, 즉 로그인정보(세션)이 없으면, 홈페이지로 가도록 작업예정
- 정방향 개발 시작 : VO생성 -> 매퍼쿼리생성 -> DAO클래스 생성 -> Service클래스 생성 -> Controller+jsp
- 위 내용중 게시물 관리에서 CRUD 컨트롤러 + jsp 처리(4.파일업로드구현)


#### 20210621(월) 작업
- 관리자단 게시물관리 CRUD 처리(4.파일업로드구현,5.트랜잭션구현).
- @Service 클래스 마무리
- 트랜잭션? 여러개의 메서드를 한개처럼 처리하도록 구현하는 애노테이션. All or Not(모두 실행되거나 아니면 모두 실행이 안되거나) 목적은 데이터 무결성을 유지하기 위함(쓰레기데이터 방지)
- root-context와 servlet-context 설정파일에 트랜잭션과 파일업로드설정 처리
- @Controller 클래스 추가(파일업로드/다운로드 구현) > jsp 화면처리
- @Service 트랜잭션 기능 추가
- @ASpect 기능 마무리
- AOP기능중 Aspect기능의 설정은 servlet-context.xml에 위치필수

#### 20210618(금) 작업
- 검색처리는 멤버쿼리에서 작성한 내용 붙여넣고, 다중게시판용 필드조회조건 추가예정
- 관리자단 게시물관리 CRUD 처리(4.파일업로드구현,5.트랜잭션구현).
- 게시물관리 시작: 다중게시판? 1개 페이지로 board_type 변수를 이용해서 공지사항,겔러리,QnA... 등등 구별해서 사용.(쿼리스트링이 길어져서 @Aspect로 사용)
- 정방향 개발 시작 : VO생성 -> 매퍼쿼리생성 -> DAO클래스 생성 -> Service클래스 생성
- 두사람 이상이 동시에 글을 쓰고 모두 첨부파일을 추가하는 상황
- 실행순서 
- 사람1 : insertBoard -> bno(101) -> 첨부파일 insertAttach -> bno 필요
- 사람2 : insertBoard -> bno(102) -> 거의 동시에 발생해서 101번이 102번을 불러오게되면?
- 해결책1 : @Transantional 으로 insertBoard 메서드를 감싸주면 해결
- 해결책2 : insertBoard 쿼리에 return 값을 bno 받아서 insertAttach를 실행하게 처리
- @Service 까지는 DB테이블을 CRUD 처리
- 첨부파일은 @Controller에서 업로드/다운로드 로직 처리. 가장 복잡함

#### 20210617(목) 작업
- [복습]: 스프링의 기능 : IoC(제어의역전:객체의 메모리 관리를 스프링이 대신해줌) , DI(의존성 주입, @Inject)
- 관리자 게시판 생성관리 CRUD 중 RU 처리 마무리예정
- 관리자 게시판 생성관리 CRUD 중 CD 처리 예정(AOP기능 구현)
- 관리자단 왼쪽메뉴에 게시판 종류가 실시간으로 출력되야하는데, 현재 게시판 생성관리 목록 페이지에서만 작동중(문제 발생)
- 위 문제를 해결하기 위해 AOP기능 사용
- 스프링 AspectOrientedProgram구현은 3가지방식: @Aspect, @ControllerAdvice, intercept(가로채기) 세가지 태그를 사용해서 관점지향프로그래밍을 구현.
- AOP용어: 관점지향 - 프로그램 전체에 영향을 주는 공통의 기능을 적용하는 패턴기법
- AOP용어: Advice(충고 또는 간섭) - 프로세스 작업 중간에 필요한 기능을 끼워넣는 방식
- Advice: 포인트컷(간섭/필요한 기능을 끼워넣는 시점, @Before,@After,@Around)
- 게시판종류 출력: @ControllerAdvice 로 구현(게시판관리 CRUD작업시 실습)
- @ControllerAdvice 실행조건: 컨트롤러 클래스의 메서드에만 Advice가 적용됨
- 검색시 pageVO 처럼 board_type을 계속 값을 유지하는 기능: @Aspect 로 구현(게시물관리 CRUD작업시 실습)
- @Aspect 장점: 특정클래스의 특정메서드 실행시(포인트컷) 자동실행되는 메서드를 지정이 가능
- @Aspect 실행조건: 컨트롤러에 더해서 서비스, DAO클래스의 메서드에도 Advice 가능
- 로그인체크, 권한체크시: intercept(스프링시큐리티) 태그를 사용해서 구현(로그인,권한체크기능 구현시 실습)
- intercept 태그는 스프링시큐리티에서 관리
- [공지]06-17 목요일(4교시) UI 디자인 시험 있습니다.(화면기획서XLS제작, 화면설계서PPT제출용)
- 관리자 게시물관리 CRUD 처리예정(파일업로드구현, 트랜잭션구현)


#### 20210616(수) 작업
- 관리자 게시판 생성관리 CRUD 처리(AOP기능 구현)
- 게시판 생성관리 VO파일 :https://github.com/seoje89/seojungeui-spring5/blob/master/src/main/java/com/edu/vo/BoardTypeVO.java
- 게시판 생성관리 Mapper파일:https://github.com/seoje89/seojungeui-spring5/blob/master/src/main/resources/mappers/oracle/boardTypeMapper.xml
- 게시판 생성관리 DAO파일(인터페이스별도):https://github.com/seoje89/seojungeui-spring5/blob/master/src/main/java/com/edu/dao/BoardTypeDAOImpl.java
- 게시판 생성관리 Service파일(인터페이스별도):https://github.com/seoje89/seojungeui-spring5/blob/master/src/main/java/com/edu/service/BoardTypeServiceImpl.java
- 10년, 20년, 지금 변하지 않는것은 변수값의 흐름은 변함이 없음. 정방향 개발시작
- 시작. VO -> 매퍼쿼리 xml -> DAO클래스 생성 -> Service 클래스 생성 -> 컨트롤러 -> jsp화면처리
- 관리자 게시물관리 CRUD 처리(파일업로드구현, 트랜잭션구현)

#### 20210615(화) 작업
- 관리자단 회원관리 수정 페이지에서 암호 수정 잘되는지 확인 예정
- 회원관리 CRUD 화면 JSP구현 중 insert 예정
- [공지]06-17 목요일(4교시) UI 디자인 시험 있습니다.(화면기획서XLS제작, 화면설계서PPT제출용)

#### 20210614(월) 작업
- 수업 전 관리자 회원관리 view화면구현 마무리
- multipart(첨부파일기능) 라는 폼태그 전송방식을 추가 -> commons.fileupload 외부모듈 필수(pom.xml에서 의존성 추가) 
- 위 외부모듈을 스프링 빈으로 등록(servlet-context.xml 하단)
- CRUD에서 multipart를 사용한다면, 리졸브(resolve-해석기) 스프링빈이 필요
- 데이터변수를 전송할때 GET(기본값,URL쿼리스트링 방식)으로 전송받으면, 타 도메인에서도 GET으로 접근이 가능 -> GET을 사용하면 다른 도메인(서버)에서 검색이 가능, 입력/수정/삭제 불가능
- 데이터변수를 POST(숨김방식)으로 전송받으면, 타 도메인에서는 접근이 불가능함 -> 같은 도메인에서만 입력/수정/삭제 가능
- 웹프로그램은 보안때문에 외부도메인에서 컨트롤러에서 지정한 GET 방식의 URL로 접근해서 데이터 출력이 가능
- 단, 입력수정삭제 기능은 GET방식으로 못하고, POST방식으로 지정 -> 다른 도메인(서버)에서 해당 기능에 접근하지 못하게 서버단에서 처리
- GET : Insert(외부사이트 입력폼에서도 입력가능)- 쿼리스트링으로 데이터전송 url?key=value&key2=value2
- POST : Insert(외부사이트에서 입력불가능, 같은사이트의 입력폼에서만 입력가능) form의 입력태그(히든스트링)로 데이터전송
- 나머지 관리자 회원관리 CRUD 화면 JSP처리. update, delete, insert

#### 20210611(금) 작업
- 수업전 관리자단 회원관리 페이징처리에서 컨트롤러와 calcPage()메서드의 관계 간단하게 확인하겠습니다.
- JSTL : Java Standard Tag Library 자바 표준 태그  모듈로서 JSP에서 자바를 사용하는 표준.
- taglib uri(유니폼 리소스 ID-의미있는 고유값=식별값,더 많은정보를 포함) > url(링크경로만 있음)
- prefix(접두어), 태그 별칭 사용 예, <c 로 시작
- 관리자단 회원목록 처리 마무리(1.페이징및 검색기능구현).
- model을 이용해서 결과를 JSP로 구현.(2.JSP화면은 표준언어인 JSTL로 구현)

#### 20210610(목) 작업
- 컨트롤러를 이용해서 관리자단 회원관리화면 JSP 만들기 진행
- JUnit 마치고, 관리자단 회원관리(CRUD) jsp 작업
- 쿼리실습에서 .equals함수 사용에 대해서 설명할때,아래 isEmpty메서드와 착각해서 이야기 한 내용이 있어서 정정
- 자바에서 객체가 공백 또는 비었는지 비교할때, 예를 들면, 우리프로젝트에서 첨부파일이 있는지 비교할때 아래 처럼 사용하지 않고
- if(save_file_name != null && "".equals(save_file_name))
- 다음처럼 짧게(널과공백체크를 한번에) 사용합니다.(아래)
- if(!save_file_name.isEmpty()) //게시판 첨부파일 체크시 사용예정
=========================================
- GTM시간(그루니치 천문대 기준시-표준시) : KST한국시간과 9시간 차이
- DB서버에 타임존설정(Asia/Seoul 되어있으면 문제X 그냥사용)
- 만약에 타임존설정이 없거나, 수정할수 없으면 GMT+9시간해서 Insert,Update 한국시간으로 사용
- 오라클일때 확인 : SELECT TO_CHAR(systimestamp + numtodsinterval( 9, 'HOUR' ), 'YYYY-MM-DD HH24:MI.SS.FF4')  from dual;
- Mysql(마리아dB)확인 : SELECT DATE_ADD(NOW(3), INTERVAL 9 HOUR);
-------------------------------------------------------
- JUnit에서 회원관리 나머지 Read,Update 테스트 진행
- 업데이트 실습은 회원암호를 스프링시큐리티5 암호화(1234->해시데이터로 변경)로 일괄변경 실습
- 정방향 암호화 가능, 역방향 복호화는 불가능(JAVA용 스프링 시큐리티 암호화, 데이터베이스용 MD5 등)

#### 20210609(수) 작업
- PageVO.java 클래스 생성 마무리
- JUnit에서 위 작업한 내용을 기준으로 selectMember() 테스트 진행(검색,페이징)
- <![CDATA[ 쿼리]]> : 태그 안쪽에 부등호를 사용하기 위해서 문자열 변환 태그를 사용
- 쿼리에서 변수와 문자열과 연결할때는 +(자바) X, ,(X), ||(o)
- JUnit에서 회원관리 나머지 CRUD 테스트 진행. 암호화도 실습예정.
- 컨트롤러를 이용해서 관리자단 회원관리화면 JSP 만들기 진행예정

#### 20210608(화) 작업
- 페이징에 사용되는 변수(쿼리변수+VO변수)
- queryStartNo, queryPerPageNum, page, perPageNum, startPage, endPage
- 검색에 필요한 변수(쿼리변수만): 검색어(search_keyword), 검색조건(search_type)

```
--SQL쿼리로 페이징을 구현해서 변수로 삼을것을 정의
--PageVO의 멤버변수로 사용예정
SELECT TableB.* FROM
(
    SELECT ROWNUM AS RNUM, TableA. * FROM
    (
    SELECT * FROM tbl_member
    WHERE user_id LIKE '%admin%'
    OR user_name LIKE '%사용자8%'
    ORDER BY reg_date DESC
    ) TableA WHERE ROWNUM <= (page*b)+b
) TableB WHERE TableB.RNUM > page*b
--페이징쿼리에서 필요한 변수는 2개
--현재페이지에 보여주는 변수 page*b == queryStartNo
--1페이지당 보여주는 변수의 갯수 b == queryPerPageNum
--PageVO에서 필요한 추가변수: page
--UI하단의 페이지 선택 번호 출력할때 사용하는 변수(아래)
--perPageNum 변수로 받아서 startPage, endPage 를 구해서 하단의 페이지 선택 번호를 출력
```

- 스프링코딩 작업순서 1부터 6까지(아래)
- 1. ERD를 기준으로 VO클래스 생성
- M-V-C 사이에 데이터를 입출력하는 임시저장 공간(VO클래스-멤버변수+GET/SET메서드)생성
- 보통 ValueObject클래스는 DB테이블과 1:1로 매칭이 된다.
- 2. 매퍼쿼리(마이바티스 사용)를 만든다.(VO사용해서 쿼리생성)
- 3. DAO(데이터 엑세스 오브젝트)클래스를 생성(SqlSession 사용쿼리 실행) *오늘 Sql 세션은 root-context에 빈으로 만듬.
- IF(인터페이스)를 만드는 목적: 복잡한 구현클래스를 간단하게 구조화 시켜서 개발자가 편하게 관리하게 도와주는 역할 -> 기사시험: 캡슐화 구현과 관련(안에 어떤 구현 내용이 존재하는지 몰라도, 이름만 보고 사용하게 만들어주는것)
- 스프링 부트(간단한 프로젝트)에서는 4번 Service클래스 없이 바로 컨트롤러로 이동한다.
- 4. Service(서비스)클래스 생성(서비스에 쿼리결과를 담아 놓는다.)
- 게시물을 등록하는 서비스1개(tbl_board DAO1 + tbl_attach DAO2)
- JUnit에서 위 작업한 내용을 CRUD 테스트
- 5. Controller(컨트롤러)클래스생성(서비스 결과를 JSP로 보냄)
- 6. JSP(View파일) 생성(컨트롤러의 Model 객체를 JSTL을 이용해 화면에 뿌려준다)

#### 20210607(월) 작업
- 마이바티스 추가 순서
- 1. pom.xml에 의존성 추가
- 2. 마이바티스 설정파일 추가(쿼리를 저장할 위치 지정, 파일명 지정)
- JUnit으로 CRUD 실습(아래)
- JUnit의 oldQueryTest 메서드처럼 직접쿼리를 사용하지 않고, 쿼리를 관리하는 프로그램으로 제어한다.
- 위 쿼리를 관리하는 프로그램이 마이바티스. 마이바티스 모듈을 추가한 후 JUnit 실습 진행
- 스프링: 관리자단, 회원관리부터 스프링 작업예정
- 책 스프링 웹프로젝트는 개발 STS(스프링 툴 슈트) 툴 -> 기반은 이클립스 기반을 확장
- 우리가 하는 책 스프링 웹프로젝트는 개발 전자정부표준프레임워크 개발 툴 -> 기반은 이클립스 기반을 확장 -> 전자정부표준프레임워크를 커스터마이징(우리 교육과정에서는 제외)
-----------------------------------------------
- 마이바티스를 이용해서 쿼리를 관리
- 이전에는 자바코드(jsp코드) 안에 쿼리를 만들어서 프로그램을 제작하였음. -> 문제점발견(스파게티 코딩)
- MVC로 분리: Model부분의 SQL쿼리를 분리하도록 기능을 추가한 것이 마이바티스
- 마이바티스(mybatis): 형은 아이바티스(ibatis) -> 마이바티스
- 스프링(마이바티스) 나오기 전에는 쿼리를 모아서 작업을 프로시저로 만들어서 작업
- 지금은 스프링으로 옮기지 못한 프로그램 제외, 대부분 마이바티스로 쿼리를 관리함
- 마이바티스 세팅 시작 
- 오라클의 DB관리 로그인정보(Application Express웹프로그램이름): admin/apmsetup -> 암호변경요청: Apmsetup1234%

```
-- SQL디벨로퍼에서 system으로 로그인 후 아래 쿼리로 XE2라는 사용자를 완벽하게 지우기

SELECT * FROM all_users;
-- all_users는 테이블x, 시노님(동의어) : 테이블명이 사용하기 힘들정도로 길거나 여러 이유로 바로 접근이 안될때 사용
DELETE FROM all_users WHERE username='XE2';
-- 오라클은 스프링과 같은 방식 패키지명 안에 함수, 프로시저(오라클프로그램)를 만들수 있다.
-- 패키지 안에 함수가 있으면 패키지명이 너무 길어서 사용시 개발자에게 부담이 된다.
-- 위 상황을 해결하기 위해 만든것이 시노님(동의어)
DROP USER XE2 CASCADE; -- XE2 사용자를 지울때
-- XE사용자가 생성한 테이블까지 모두 지우는 명령
-- CSS(Cascade[계층형] Style Sheet)
```

- 현업에서는 오픈소스인 mysql(마리아DB)를 사용할 기회가 더 많다. -개발자가 많은편
- 오라클은 납품시 SW비용이 산정되어 사용할 기회가 적다. - 개발자가 상대적으로 적음
- 보통은 클라이언트에게 부탁을 해서 XE사용자를 추가해달라고 요청 후 발급받은 계정으로 시작
- 오라클 기초이론 마무리 : 시퀸스(스프링에서 사용-AutoIncreament 자동증가기능)
- 만약 시퀸스(AI)기능이 없다면, 게시물 작성시 첫번째, 두번째 게시물 등등을 구별하는 숫자를 계속 추가해줘야한다.
- 숫자를 계속 추가하려면, 현재까지 저장된 게시물의 최고번호값(Max)을 구해서 +1을 해야한다.(개발자가 Insert시)
- DB에서 기본으로 위 Max값을 구해서 +1 하는 로직을 만들게 됨. 이 기능이 시퀸스(AI)
- 우리 스프링 프로젝트에서는 2개 시퀸스 생성(게시물 시퀸스 SEQ_BNO, 댓글 시퀸스 SEQ_RNO)
- 시노님(긴 객체를 개발자가 접근하기 힘들어서 만든 단축 이름) 예) sys.dual -> dual 단축이름으로 사용 가능
- 오라클: 더미데이터 일괄등록 예정. 회원관리(100명), 게시판관리(공지사항50개,갤러리50개)
- 위 더미데이터는 프로지서(함수)라는 DB프로그램방식으로 추가
- 오라클: 댓글은 수동등록 후 마무리


#### 20210604(금) 작업
- 오라클일때: localhost:1521/XE 접속URL끝의 XE는 서비스(서버)ID명 1개 -> XE, XE2 스키마2개(DB2개) 존재
- 오라클은 사용자명(XE)이 스키마명(DB명)이 된다. 사용자(XE2) 워크스페이스를 추가하면 스키마(DB)가 새로 만들어진다.(구버전은 데스크탑프로그램 -> 지금은 웹프로그램에서 사용자 추가/DB(테이블스페이스) 추가)
- Mysql(마리아DB): localhost:3306/XE 접속URL끝의 XE는 스키마명(DB명) 
- junit테스트시 SQL에러를 디버그하는 방법은 jdbc드라이버 -> log4dbc드라이버 바꾸면, SQL 에러가 나오게 됨
- junit에서는 SQL에러가 보이지만 콘솔창에서는 보이지 않음. 콘솔창에도 SQL로그상황이 나오게 하는 드라이버를 추가 pom.xml에 추가
- 오라클 7장 마무리 후 13장으로 바로 실습예정
- 스프링 : Junit테스트 - oldQueryTest메서드 실습
- 오라클 : 8~12장, 14장 실습
- 스프링: JUnit테스트: 회원관리부분 CRUD 테스트 진행예정.
- 오라클: 더미데이터 일괄등록 예정. 회원관리(100명), 게시판관리(공지사항50개,겔러리50개)
- 스프링: 관리자단, 회원관리부터 스프링 작업예정.

#### 20210603(목) 작업
- 작업시간(비중) = 관리자단 7:3 사용자단
- 스프링에서 오라클연동 순서
- 1. jdbc(Java DataBase Connection) 확장모듈 pom에 추가
- 2. 오라클 접속 드라이버 확장 모듈을 pom에 추가X 직접 jar 파일을 추가
- root-context.xml 파일에 오라클 커넥션 빈(스프링클래스)을 추가
- 스프링이 관리하는 클래스를 추가하는 방법 2가지 
- 1. @Controller,@Repository,@Service,@Component
- 2. -context.xml에서 빈(bean)을 추가하면, 스프링 클래스가 됨.
- JUnit테스트: 오라클 연동한 후 회원관리 부분 CRUD테스트 진행예정
- 오라클 05장부터 CRUD 실습예정
- admin 회원관리(jsp디자인)부터 프로그램 작업 시작예정.

#### 20210602(수) 작업
- 예외처리하는 목적: 에러상황에서도 프로그램이 중단되지 않도록 하는것이 주목적(에러상황을 세련되게 넘기게 처리)
- 스프링에서는 개발자가 예외처리 하는 경우는 거의 없음.(파일 업/다운로드 직접처리) 대부분 throws Exception으로 스프링으로 넘김. -> 예외의 전파
- 쓰레드:thread, 실행되는 프로그램을 명시, 1개 프로그램에는 보통 1개의 쓰레드가 실행, 특이한 경우(http 웹 네트워크 프로그램인 경우- ex] 게시물 다운로드버튼 클릭시 다운로드 쓰레드 실행, 동시에 리스트버튼 클릭시 목록보기 쓰레드가 동시실행 :안드로이드 앱에서 필요) 여러개 쓰레드 실행이 필요할 수 있음
- 예외처리: Throwable 오브젝트가 실행시 에러가 발생하면, 예외(내용)를 보낼때 사용하는 클래스 Throwable
- 스프링앱에서는 예외(에러)정보를 스프링이 처리함.
- 자바앱에서는 예외(에러)정보를 개발자가 처리함. 
- 예외처리: 에러발생시 프로그램이 중지되는 것을 방지하고, 계속 프로그램을 사용할수 있도록 처리하는 방법 = Exception
- 패키지는 관련기능을 한곳에 모아서 개발자가 관리하기 편하기 위해서 구분한 이름(폴더명)

- Controller(클래스) + home/index.jsp(화면) 한쌍. 그래서 컨트롤러 클래스에서 만든 변수를 index.jsp에서 사용 가능하게 된다.
- 안드로이드 앱 = 액티비티(java) + 레이아웃.xml(화면) 한쌍
- C#닷넷 = .cs(프로그램) + test.aspx(화면) 한쌍
- 일반홈페이지(cafe24)- URL 직접접근 가능(보안 낮음)	
- MVC웹프로그램 차이점 - URL 직접접근 불가(보안 높음) - 관공서, 대학, 은행
- MVC프로젝트에도 직접접근이 가능한 resources 폴더 존재 - static 콘텐츠(html,css,js)를 모아놓은 폴더. views폴더 jsp는 직접접근이 불가능
- views 폴더처럼 직접접근이 불가능한 컨텐츠는 Controller(라우터)로 접근하게 됨.
- views/home/index.jsp 엑박처리 후 분해(header.jsp, footer.jsp, index.jsp)
- Junit(Java Unit Test): 자바 단위 테스트(서비스 프로그램 만들기 전 프로토 타입 시제품 제작) - Junit CRUD 작업 후 본격작업시작.
- Logger의 레벨 : Debug > Info > Warn > Error > Fatal
- 개발할때 : Debug 로거레벨을 설정
- 납품할때 : Warn 로거레벨로 변경
- 개발순서 : ERD제작 -> html제작 -> jsp제작(현재:관리자단 작업 후 사용자단)
- 자바실습 4장 패키지와 예외 처리 실습.

#### 20210601(화) 작업
- 프로젝트의 버전을 올린다 -> 외부라이브러리 부분의 버전을 올림 - 메이븐 컴파일러 -> pom.xml 버전을 관리, 메이븐은 수정 및 추가하게되면 maven 메뉴에서 maven update 메뉴 사용
- Controller클래스에서 생성한 변수 사용(자바)model객체전송(스프링) return home -> home.jsp출력
- 출력하는 방식1: 25년전부터 있던방식 EL(Express Language)방식 출력 - ${변수} , <%=${변수} %>
- 많이 사용하는 출력방식: JSTL방식(*표준) 사용. 출력,반복,조건 등등 다양한 방식
- 언어를 배우는 순서 : 주석 > 디버그하는방법 > 변수 > 메서드(함수) > 클래스 > MVC모델
- 디버그하는 방법: 자바(System.out 이용해서 콘솔창에 출력)
- 스프링에서는 logger 를 사용해서 디버그 내용을 출력.
- 스프링이 관리하는 클래스(빈)의 종류 3가지(사용법은 개발자가 만드는 클래스명 상단에 입력)
- 1. @Controller 클래스빈 : 라우터 역할 메서드 제작(라우터 역할의 클래스 기능)
- 2. @Service 클래스빈 : 비즈니스 로직 관련 메서드 제작(개발자위주의 외부클래스 기능)
- 3. @Repository 클래스빈 : Model 처리 메서드 제작(DB핸들링 외부클래스 사용)
- 위 3가지의 @를 사용하는 클래스는 DI(객체생성-의존성주입), AOP, IoC(제어의역전-메모리 자동관리) 사용가능
- 프런트 개발자가 작업한 결과를 백엔드 개발자가 확인.
- 일괄바꾸기 1. "/home -> "/resources/home
- 일괄바꾸기 2. '/home -> '/resources/home
- VS code에서 작업한 html을 이클립스에 배치(프런트개발자가 작업한 결과를 백엔드개발자가 받아서 배치한다.)
- resources 폴더에 static컨텐츠(html,css,js,font) 배치
- ERD기준으로 게시판UI 마무리
- 오늘부터 vs code -> 이클립스에서 작업
- 관리자단 AdminLTE적용 - 스프링 시간 선택 후 아래 작업진행예정
- 회원관리 CRUD-jsp, 게시판 생성관리CRUD-jsp
- html을 분해(1개의 페이지를 2개로 분해, 1.header.jsp(메뉴를 공통), 2.footer.jsp(공통))
- 이클립스로 작업한 html 내용을 -> resources 폴더(admin,home,root파일까지)로 배치
- 스프링작업 시작

#### 20210531(월) 작업
- 토드(sql 디벨러퍼와 비슷한 상용)에서는 버튼으로 포워드 엔지니어링 처리.
- 무료 sql 디벨러퍼에서는 버튼이 없다. DDL문을 실행해서 포워드 엔지니어링 처리.
- 참고) mysql용 워크벤치는 무료이지만, 버튼으로 포워드 엔지니어링이 가능.
- sql쿼리 3가지 분류
- 1. DDL문: Data Definition Language. 데이터 정의 언어 create table문
- 2. DCL문: Date Control Language. 데이터 제어 언어       commit,rollback
- 3. DNL문: Date Manufacture Language. 데이터 조작 언어 CRUD 쿼리.
- ERD 그림 만든것을 물리테이블로 만드는것: Forward Engineering
- 데이터 딕셔너리를 모델과 동기화: 자료사전(데이터의 데이터)을 DB테이블과 동기화
- 데이터 딕셔너리는 메타 데이터와 동일 : 컨텐츠x, 구조/구성 정보만 존재
- 물리테이블을 ERD 그림으로 만드는것:Reverse Engineering(기존DB분석시 사용)
- 스프링시큐리티는 2단계 구현예정
- 1단계.로그인인증(ENABLED):AUTHENTICATION (로그인 안하면 글쓰기기능x, 관리자x)
- 2단계.권한체크(LEVELS):AUTHORITY (관리자-admin URL 접근가능, 일반회원은 사용자홈페이지에서 공지사항x, 갤러리만 사용가능.)
- 일대다 관계(1:N관계):게시물 1개에 댓글은 N개 달릴수 있음.
- E-R 다이어그램은 그림일뿐, 실제 물리 DB(테이블)은 ERD를 기준으로 생성.
- PK를 AI(Auto Increment 자동증가)로 만들기: 오라클(시퀸스 객체로 기능구현), Mysql(AI라는 필드속성으로 처리)
- ERD에서 Relation 생성: 게시판타입 테이블(부)과 게시물관리 테이블(자)의 관계를 생성
- 부자의 관계는 부모의 PK를 기준, 자식에게는 FK(Foreign Key)로 관계를 맺음.
- 게시판 타입: notice, gallery 2가지가 존재한다면,
- Relation 관계가 필요한 이유: 공지사항이나 갤러리 게시판이 아니면, 개발자가 예외처리를 하지 않아도 다른게시판으로 등록하는 상황이 발생되지 않게됨.
- 데이터의 무결성을 유지하는 역할.(외래키 관계)
- 필드 데이터형3: Timestamp(시간도장) 연월일시분초밀리초, Date(연월일까지)
- 필드 데이터형2: CLOB(CHAR LOGN BYTE) 글내용이 많을때 사용하는 데이터형태
- 필드 데이터형: VARCHAR2(2바이트를 1글자-한글), VARCHAR(1바이트 1글자-영문전용)
- 테이블구성: 필드(컬럼,열)=테이블의 멤버변수(자바VO클래스의 멤버변수)
- 필드구성시 PK: PrimaryKey(주키, 기본키, 고유키)= 테이블영역에서 고유한 ID를 말함(중복X)
- PK예(개인을 식별할수 있는 값): 로그인ID, 개인이메일주소, 주민번호 등
- RDBMS : Relation DataBase Management System(관계형 데이터베이스 관리 시스템)
- 오라클 : 테이블 스페이스(TableSpace) = 스키마(Scheme) = 데이터베이스(DB-MS-SQL)
- 지난주 금요일, 오라클 웹용 관리프로그램에서  XE라는 테이블스페이스를 XE사용자로 추가했음.
- EntityRelationDiagram(ERD-객체관계그림): Entity는 테이블을 명시
- 데이터 모델: Model - Object를 형상화 시킨것. 데이터베이스를 말함.
- MVC 스프링 프로젝트에서 M이 Model이고, 스프링프로젝트 구성중에 데이터베이스를 가리킨다.
- V는 View고 jsp를 가리킴. C는 controller이고 클래스를 뜻함.
- 오라클 설치시 암호는 apmsetup 으로 통일


#### 20210528(금) 작업
- 추상클래스 실습 끝, 인터페이스 실습은 스프링에서
- extends관계에서 this(현재클래스), super(부모클래스)
- 다형성? 메서드 @오버라이드(상속), 메서드 오버로드(동일함수명의 파라미터의 개수,종류가 틀린 메서드)를 통해 구현됨.
- 오버라이드: 상속받아서 재정의 메서드 @오버라이드 = 다형성구현했다.
- 오라클11g ExpressEdition 설치예정. OracleXE112_Win64.zip
- SQL디벨러퍼를 다운받아서 압축풀기 - ERD 제작예정
- StructuredQueryLanguage: 구조화된 질의 언어(오라클서버) -> 답변
- 쿼리스트링(QueryString): URL에서 전송하는 값(서버에 질의문)을 문장으로 저장한내용 ?로 시작.
- CommendLineInterface : SQL*Plus 터미널화면으로 SQL쿼리 실행 X
- GraphicUserInterface : SQL디벨러퍼 윈도우화면 에디터에서 SQL쿼리 실행 O
- SQL Developer 프로그램으로 ERD 다이어그램 그림으로 프로젝트 구조 제작예정
- EntityRelationDiagram: 테이블관계도형(아래)
- 프로젝트 진행: 발주(클라이언트) -> 수주(개발사) -> 디자인 UI(클-개 협의) -> ERD(이사,팀장급 제작) -> 코딩시작(ERD 참고하며 UI소스 프로그램입히기)
- ERD에서 SQL쿼리가 생성 -> 물리테이블을 생성.
- 첨부파일(자식) -> 게시판테이블(부모) <- 댓글테이블(자식)
- 자바앱에서는 객체를 생성후 사용이 끝나면, 메모리에서 삭제하는 명령이 필수.
- 객체를 메모리에서 삭제하기 Object = null; Object.close();
- 스프링에서는 가비지컬렉터가 자동실행되서 사용하지 않는 객체를 자동으로 지워준다.
- 위와같이 개발자가 하던 메모리관리를 스프링이 대신해주는것을 IoC(Inversion Of Control) = 제어의 역전이라고 함.
- 스프링 특징 3가지(IoC, AOP, DI)
- 수업시작전, static메서드와 객체의 멤버메서드 비교설명
- 수업시작전, Step2 클래스에서 MemberService 객체를 직접접근할때 static 메서드(컴파일시 실행가능=메모리로딩)로 변경되어야 하기 때문에 static으로 변경해야함. 원리 설명.
- memberService 객체를 이용해서 메서드에 접근할때는 (런타임(호출)시 실행이 가능=메모리에 로딩됨)
- 클래스와 상속에 대해서 이론 및 실습

#### 20210527(목) 작업
- 3장 객체와 클래스부터 시작
- 스프링에서는 클래스 extends 상속보다는 인터페이스 implements를 더 많이 사용한다.
- abstract 클래스(추상클래스): 구현내용없이, 메서드명만 있는 클래스
- 메서드명만 있기 때문에(구현내용이 없기 때문에), 객체로 만들수 없는 클래스
- 부모로서 자식에게 상속만해서 메서드 이름만 재사용 가능하게 된다.
- 객체로 만들수 없다? 실행가능한 클래스로 사용못하지만, 프로그램을 구조화 시킬때 필요.
- Step1 aaa = new Step();//이렇게 생성자로 객체를 만들지 못함.
- final 클래스 : 부모가 될수 없는 클래스(상속해서 재사용이 불가능한 클래스)
- 접근제어자 : public(패키지 위치에 상관없이 접근- 제일많이 사용)
- public 클래스 안에서 멤버 변수는 private을 제일 많이 사용.(보안때문에)
- public 클래스 안에서 멤버 메서드는 public을 많이 사용.(외부 다른 클래스의 메서드를 실행 가능) 대신, 변수가 private이기 때문에, 직접수정안되고 실행만 가능.
- 인스턴스(클라우드 주용어)=오브젝트(자바)=객체=실행중인 클래스
- 클래스 자료형(int, long, String 처럼)은 멤버변수, 멤버메서드, 서브클래스 등을 포함할수 있다. 	= C언어 구조체 자료형
- 이클립스에서 커밋/푸시가 안될때 커밋 팝업창 하단에 Force 항목을 체크하고 진행하면 된다.

#### 20210526(수) 작업
- 붕어빵틀(클래스) -> 붕어빵(오브젝트:객체)
- OOP : 자바를 객체지향(클래스를 실행 가능하게 하는) 프로그래밍
- 객체 = 오브젝트 = 인스턴스 = 실행가능한 클래스
- 객체(Object) 와 클래스: 클래스형 자료를 실행 가능하게 만든 것을 객체라고 함
- 추상화(Abstract): 오프라인 업무 -> 대표 업무만 뽑아낸 클래스 = 추상클래스
- 클래스는 멤버변수 + 멤버메서드(실행) + 서브클래스
- 변하지 않는 변수 = 상수 = 변수명을 대문자(원주율) PI = 3.141569...
- 보통 상수변수는 클래스의 제일 상단에 위치한다.
- 기본형(정수자료형): byte < short < int < long 10L(롱타임변수)
- 기본형(실수자료형-소수점존재): float < double , 12.34f(실수형숫자)
- 기본형(문자형-''단따옴표 값을 입력해야 에러가 나지 않음): char, 1개 문자만 입력
- 문자형에서 유니코드 \u숫자 입니다.
- 기본형(불린형-참,거짓): boolean, (0|1)
- 참조형(대문자로 시작): 참조형 변수의 대표적인 변수클래스이다.
- 클래스 변수(저장된상태) -> 실행가능하게 되었을때, 인스턴스 변수(메모리로드된 상태)
- 인스턴스라는 말보다는 오브젝트라는 말을 더 많이 사용한다.
- String(문자열 클래스변수): 대문자로 시작.
- 상수변수를 명시적으로 만들때: final int MAX = 100;
- for-each 반복문 전까지 실습

#### 20210525(화) 작업
- 스프링MVC프로젝트: ModelViewController 약자 MVC구조(웹프로그램구조)
- src/test/java폴더 만들었습니다: 테스트 작업은 이 폴더에서 하라는 약속
- src/main/java폴더가 진짜 프로그램작업을 하는 폴더.
- javac HelloWorld.java -encoding UTF-8 (한글 내용도 컴파일됨)
- 위 java컴파일러로 실행한 결과 -> HelloWorld.class 실행파일이 생성됨.
- 주의점)클래스파일은 실행을 패키지의 루트(최상위)에서 실행해야 한다.
- kr.or.test패키지 root폴더 src/test/java폴더에 실행을 해야함.
- java kr.or.test.HelloWorld 클래스를 실행하게 됨.
- 이클립스 나오기 전, 25년전 javac 컴파일에서 class 프로그램을 만들었음.
- 지금은 터미널에서 javac 사용하지 않고, 이클립스에서 바로 실행.
- javac : 자바앱 컴파일러 -> 클래스 실행파일을 만듬.class(바이트코드) (자바환경JVM실행)
- 메이븐(Maven) : 웹프로그램 컴파일러 -> 웹프로그램(앱) 실행파일을 만듬.war(톰캣에서실행)
- 메이븐이 컴파일할때, 자바소스만 컴파일하는것이 아니고, 외부 라이브러리도 가져와서 바인딩(묶어줌)하게됨 = 패키징 이라고 함 = .war(와르) 파일로 패키징됨.
- 메이븐이 관리하는 외부 라이브러리 파일(lib) 목록을 저장하는 파일이 pom.xml 이다.
- pom.xml에서 자바버전을 1.6 -> 1.8로 변경 후 메이븐을 업데이트 한다.
- 외부 라이브러리 파일을 참조하는 방식을 영어로 dependency 라고 함
- jar파일 : JavaARchive = jar 자바클래스를 패키징한 파일.

#### 20210524(월) 작업
- 자바 기초 실습
- 자바 .java라는 클래스 파일을  컴파일해서 생성된 .class 파일을 실행하는 구조
- 파이썬/자바스크립트는 인터프리터 방식으로 실행
- 자바스크립트는 함수구조의 프로그래밍 = Function(함수)
- 자바는 객체지향 프로그래밍(Object Oriented Program)
- 오브젝트(객체) = 실행 가능한 Class
- 아스키, 유니코드(UnicodeTypeFormat - 8)
- 아스키코드 IoT에서 데이터 전송시 필수로 확인해야함. 0,1을 전송 -> 48,49 값을 받게됨.
- IoT프로그램시 하드에웨어 값을 주고받을때  if(var1 == 48) {구현내용}
- 공유기가 하위 가질수 있는 사설IP는 공유기 본인 IP + 255개(여유분) = 256개 인터넷이 가능.
- 영어 인코딩은 아스키코드로 다 표현 가능
- 한글 인코딩, 중국어/일본어 인코딩 등은 아스키코드로 다 표현못함. 그래서, 유니코드 등장.UTF-8
- Hex(16진수), Dec(10진수), Char(문자) = 127개 = 아스키문자의 크기(컴문자-사람문자 1:1매칭)
- Oct(8진수), Bin(2진수)
- 아스키코드- 7비트코드(127글자) -> ANSI코드- 8비트(256글자) -> Unicode(65536글자)- UTF8
- UTF8mb4(이모지까지 포함 : 이모티콘+이미지 약자)
- 자바언어를 한다는 것은 컴파일로 실행 프로그램을 만들게 된다. -> 실습예정
- 자바스크립트(파이썬)는 그냥 실행해서 프로그램을 만들게 된다. -> 실습예정