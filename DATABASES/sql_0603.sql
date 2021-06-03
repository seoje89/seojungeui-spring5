--DESC : Description 테이블 구조를 설명
DESC dept;
--select : 테이블 내용 조회 명령, where : 조회조건, as(Alias) 별칭으로 표시가능(필드명이 길때)
--concat 오라클내장함수는 레포트 작성시
SELECT
concat(deptno,' 번') as "부서번호"
, dname as "부서명"
, concat(loc,' 시') as "위치"
FROM dept
WHERE loc = 'NEW YORK';
-- DUAL 가상테이블이름. 테이블이 없는 내용을 select할때
SELECT 3+5 as "3더하기8은" from dual;
-- 레코드(row) : 컬럼(필드field)들로 구성
SELECT concat(count(*),'명') as "연봉이 2000인 직원" FROM emp WHERE sal > 2000;
-- 큰따옴표(필드명), 작은따옴표(문자처리=비교,결합)
SELECT * FROM emp WHERE ename != 'KING';
SELECT * FROM emp WHERE hiredate >= '1982/01/01';
SELECT * FROM emp
WHERE deptno = 10 AND job = 'MANAGER';
SELECT * FROM emp WHERE sal
BETWEEN 2000 AND 3000;
SELECT * FROM emp WHERE hiredate
BETWEEN '1980/01/01' AND '1980.12.31';
SELECT * FROM emp WHERE comm IN (300, 500, 1400);

--LIKE조회, 와일드카드=% 조회
--(중요)키워드에 괄호가 있으면 함수 upper(), concat(), count() 등
SELECT * FROM emp WHERE ename LIKE upper('K%'); -- 기본 : 대소문자 구분함, 안하는방법 존재
SELECT * FROM emp WHERE ename LIKE '%N';
-- null 널이 중요한 이유: null 값이 있으면 검색이 안된다.
-- 그러면, null 값이 필드에 있을때, 검색은?
SELECT * FROM emp WHERE comm IS NULL;
-- NVL(Null VaLue) 널값을 대치하는 함수
-- 사원중에 커미션을 0원 받은 사람은? null인 사람도 구하려면
-- 아래 E는 emp테이블의 알리아스 별칭 으로 E.* 은 emp.* 와 동일한 내용
SELECT nvl2(comm,0,100), E.* FROM emp E WHERE NVL(comm ,0) = 0;
-- NVL2(필드명, 널이 아닐때 100, 널일때 0), NVL(필드명, 널일때 0)
-- 오라클은 표준쿼리x, ANSI쿼리 표준이다.
SELECT DECODE(comm,null,0,100),NVL2(comm,100,0), E.* FROM emp E WHERE NVL(comm ,0) = 0;
-- 연봉을 기준으로 정렬 sort = 순서 order by 필드명 오름차순[초기값]|내림차순
-- (중요)서브쿼리?(select 쿼리가 중복되어있는...)
SELECT ROWNUM, E.* FROM ( -- 테이블명 대신 묶어줌
SELECT * FROM emp ORDER BY emp.sal DESC -- ASC : 오름차순[초기값], DESC : 내림차순
) E WHERE ROWNUM = 1;
-- 위 서브쿼리문장을 해석할때는 안쪽부터 해석
-- 위 정렬에서 1등만 구할때 limit를 사용x. limit는 mysql(마리아DB)의 명령어. 오라클에는 존재하지 않는다.
-- AI(AutoIncrement) 자동증가값 명령도 오라클에는 없다. mysql(마리아DB)에는 존재.
-- 중복레코드(row)를 제거하는 명령어 distinct
SELECT deptno AS "부서번호" FROM emp; --사원수대로 부서번호가 출력됨.
SELECT DISTINCT deptno AS "부서번호" FROM emp;
-- (중요)문자열을 연결할때 concat함수외에 ||파이프라인 2개를 겹쳐서 구현
SELECT ename ||' is a '|| job AS "문자열 연결" FROM emp;

-- select 마무리
-- 이후 CRUD중 Insert, Update, Delete 3개의 쿼리