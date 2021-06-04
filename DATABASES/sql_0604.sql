-- 19장 사용자 추가(CreateWorkSpace)시 오라클데스크탑(옛날방식) 사용x
-- 대신 웹프로그램 http://127.0.0.1:9000/apex/f?p=4950 사용
-- SQL플러스x

-- 15장 PK생성시 자동으로 발생되는 제약조건(constraint) :NOT NULL(빈값방지), UNIQUE(NO중복)
-- INDEX(테이블)도 자동생성(검색
-- ERD로 게시판테이블-[댓글|첨부파일] Foreign KEY(외래키) : 부자 관계에 생성

-- 14장 트랜잭션 DB단에서 사용하지 않고, 스프링단에서 트랜잭션 사용 @Transactional 인터페이스를 사용
-- commit과 rollback;(DML문:insert,update,delete)
-- rollback 은 제일 마지막 커밋된 상태로 되돌린다.

-- 12장 테이블 구조 생성(create;),변경(alter;),삭제(drop;)
-- ERD 관계형 다이어그램으로 물리적 테이블 생성(포워드엔지니어링)
DROP TABLE TBL_MEMBER_DEL;
CREATE TABLE TBL_MEMBER_DEL
(
USER_ID VARCHAR(50) PRIMARY KEY,
USER_PW VARCHAR(255),
USER_NAME VARCHAR(255),
EMAIL VARCHAR(255),
POINT NUMBER(11),
ENABLED NUMBER(1),
LEVELS VARCHAR(255),
LEG_DATE TIMESTAMP,
UPDATE_DATE TIMESTAMP
);
-- ALTER TABLE로 필드명 변경(아래)
ALTER TABLE tbl_member_del RENAME COLUMN email TO user_email;
-- DEPT테이블의 deptno 숫자 2자리때문에 에러 -> 4자리로 크기를 변경
DESC dept; -- 작은 자릿수에서 큰 자릿수로 변경하는건 문제 없음. 반대의 경우 에러 혹은 데이터가 깨짐(이미 큰자릿수가 존재할 확률이 있기 때문)
ALTER TABLE dept MODIFY (deptno NUMBER(4));
-- 11장 서브쿼리
-- 단일행 서브쿼리 필드값을 비교할때, 비교하는 값이 단일한(필드값)
-- 다중행 서브쿼리 테이블값을 select쿼리로 생성(레코드값)

--10장 테이블 조인 2개 이상의 테이블을 연결해서 결과를 구하는 예약어
--댓글 개수 구할때, 
--카티시안프러덕트 조인(합집합-게시물10개,댓글100=110개~1000개)
--(인너)조인을 제일 많이 사용(교집합)
--오라클방식(아래)
SELECT dept.dname, emp.* FROM emp, dept 
WHERE emp.deptno = dept.deptno
AND emp.ename = 'SCOTT';
--표준(ANSI)쿼리방식(아래) inner 키워드 디폴트값
SELECT d.dname, e.* FROM emp e JOIN dept d 
ON e.deptno = d.deptno
WHERE e.ename = 'SCOTT';
-- 조인과 그룹을 이용해서 댓글카운터까지 출력하는 게시판 리스트 만들기
SELECT bod.bno, title||'['||count(*)||']'
,writer, bod.reg_date, view_count
FROM tbl_board BOD
INNER JOIN tbl_reply REP ON bod.bno=rep.bno
GROUP BY bod.bno,title,writer,bod.reg_date,view_count
ORDER BY bod.bno;
--8장 함수(count, upper, lower, to_char, round..) 그룹함수번호
--having은 group by의 조건문
--부서별 평균 급여가 2천 이상인 부서의 번호와 부서별평균급여
SELECT deptno, round(AVG(sal)) FROM emp
--WHERE AVG(sal) >= 2000 --검색조건
GROUP BY deptno 
HAVING AVG(sal) >= 2000; -- 그룹조건
--부서별 연봉의 합계를 구해서 제일 급여가 많이 지출되는 부서 찾기(아래)
--자바코딩에서는 소문자로 통일
--DB세팅에서 대소문자 구분해서 사용할지, 구분할지 않을지 세팅
SELECT * FROM (
SELECT deptno, SUM(sal) AS dept_sal 
FROM emp GROUP BY deptno
) R ORDER BY dept_sal DESC; -- R의 역할을 Alias 별명
SELECT deptno, SUM(sal) FROM emp 
GROUP BY deptno 
ORDER BY SUM(sal) DESC;
--라운드함수(반올림) 소수점기준. round(10.05,2) 소수점 2번째 자리서 반올림
SELECT ename, round(sal,-3) FROM emp;
SELECT SUM(sal) FROM emp; --한개의 레코드만, 그룹함수라고 말한다.
SELECT round(AVG(sal)) FROM emp; --평균 한개의 레코드로 출력
SELECT COUNT(*) FROM emp WHERE sal < (SELECT round(AVG(sal)) FROM emp);
--위 쿼리는 사원중에 평균연봉보다 많이 받는 사람의 숫자
--(error)위 avg함수를 where조건에 사용 못할때 서브쿼리를 이용한다.
SELECT MAX(sal), MIN(sal)
, MAX(sal) - MIN(sal) as "대표와 사원의 연봉차"
FROM emp;
--DDL문(create; alter; drop;), DCL문(commit; rollback;)
--DML문(Data Manufacture Language) insert, update, delete
--insert문: 테이블에 새로운 레코드(row)를 추가
CREATE TABLE dept02 AS SELECT * FROM dept WHERE 1=0;
--위 쿼리는 테이블을 복제하는 명령
--위처럼 쿼리를 실행하면 dept테이블과 구조,내용이 똑같은 테이블이 생성됨
--where 조건이 붙으면 구조는 같으나 내용은 비어있는 테이블이 생성됨
INSERT INTO dept02 (
--필드명
deptno, dname, loc
) VALUES(
10, '개발부서', '천안'
--바인딩값
);
insert into dept02 values(20,'디자인부','경기도');
--DCL명령어는 인 커밋이 필수이다.
COMMIT; -- 데이터베이스쿼리 직접입력한 결과는 반드시 커밋을 해줘야지만 실제 저장이 된다. 
--커밋을 하지 않으면, 여기만 보이고, 다른곳은 보이지 않음.
SELECT * FROM dept02 order by deptno;
--delete는 레코드 1줄을 지우는 명령
DELETE FROM dept02; -- 이렇게 사용하면 모든 레코드가 삭제됨. 사용주의필요
DELETE FROM dept02 WHERE deptno >= 0; --모두 삭제 where 반드시 포함

-- Drop table 테이블명은 테이블 자체를 물리적으로 없애는 명령
DROP TABLE dept02; -- 드롭테이블은 커밋없이 바로 적용됨.
CREATE TABLE emp01 AS SELECT * FROM emp; -- 테이블 복제명령
SELECT * FROM emp01;
--Update 테이블명 set 필드명 = '바꿀값' where empno='특정값'
UPDATE emp01 SET ename = '홍길동' where empno = 7839;
ROLLBACK; --롤백은 마지막 커밋 바로 전까지 되돌린다
UPDATE emp01 SET sal = sal*1.1; -- 모든 직원의 연봉을 10%인상
UPDATE emp01 SET hiredate = sysdate;
--머지 merge는 표준쿼리(ANSI쿼리)가 아니기 때문에 생략