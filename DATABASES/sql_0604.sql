--8장 함수(count, upper, lower, to_char, round..) 그룹함수
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