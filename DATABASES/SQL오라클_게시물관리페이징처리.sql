--SQL쿼리로 페이징을 구현해서 변수로 삼을것을 정의 - 아래 쿼리에서 상수를 변수로
--PageVO의 멤버변수로 사용예정 -> 게시물 때문에 board_type 멤버변수로 추가
SELECT TableB.* FROM
(
    SELECT ROWNUM AS RNUM, TableA. * FROM
    (
    SELECT * FROM tbl_board
    where board_type = 'gallery' -- 게시물관리 다중게시판 검색때문에 추가한 코드
    and (title LIKE '%%'
    OR content LIKE '%%')
    ORDER BY reg_date DESC
    ) TableA WHERE ROWNUM <= (0*5)+5
) TableB WHERE TableB.RNUM > 0*5
--페이징쿼리에서 필요한 변수는 2개
--현재페이지에 보여주는 변수 page * queryPerPageNum == queryStartNo
--1페이지당 보여주는 변수의 갯수 queryPerPageNum
--PageVO에서 필요한 추가변수: page
--UI하단의 페이지 선택 번호 출력할때 사용하는 변수(아래)
--perPageNum 변수로 받아서 StartPage, endPage 를 구해서 하단의 페이지 선택 번호를 출력
