#### 20210601(화) 작업예정
- 자바실습 4장 패키지와 예외 처리 실습.
- 스프링프로젝트 ERD 제작 후 게시판 UI디자인 적용.

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