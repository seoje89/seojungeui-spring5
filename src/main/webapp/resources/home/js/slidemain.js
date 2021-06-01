var slideNum = 0; //슬라이드 인덱스 초기값 선언 number
var slideAuto = null; //값이 null 일땐 없는것, '' 일땐 공백이라는 값이 존재
// on | off 이런 역할을 하는 값을 스위치 | 플래그(깃발) 변수라고 함
// 함수선언(아래)
function play_w(directw) { // 좌우로 슬라이드되는 함수선언(왼쪽|오른쪽)
	// 아래 3개의 조건중에 1개는 무조건 실행되는 구조
	if(directw=='right') {
		slideNum = slideNum + 1; // 슬라이드 번호 증가
		if(slideNum>2) {slideNum = 0;} // 슬라이드 이미지가 3개일때로 고정.
	} else if(directw=='left') {
		slideNum = slideNum -1;
		if(slideNum<0) {slideNum = 2;} // 순서대로 무한반복을 명시
	} else {
		slideNum = directw; //string으로 형태 변환
	}
	
	// rollingbtn 클래스 영역 안쪽의 li 태그에서 클래스가 seq인것 3개를 each함수로 반복(3번반복)
	// 결과는 모든 슬라이드 버튼을 작은 정원으로 바꾸는 명령
	$('.rollingbtn').find('li.seq a').each(function(){
		$('li.seq a img').attr('src',$('li.seq a img').attr('src').replace('_on.png','_off.png'));
		//  btn_rollbutt_on.png -> btn_rollbutt_off.png 이렇게 3개가 한꺼번에 변경되는 코드
	});
	// 아래 결과는 현재 선택된 슬라이드 이미지만 _on.png (타원형)으로 바꾸는 명령(replace)
	$('li.butt'+slideNum+' a img').attr('src',$('li.butt'+ slideNum + ' a img').attr('src').replace('_off.png', '_on.png'))
	// slideNum 조건 실행 : 아래 세가지중 한가지는 항상 실행됨
	if(slideNum == 0) {
		// 슬라이드 인덱스가 0번일때 li태그를 1초간 서서히 투명도를 0이되게 처리(안보이게됨)
		$('.viewImgList li.imglist1').animate({'opacity':0},1000); //서서히 사라지기
		$('.viewImgList li.imglist2').animate({'opacity':0},1000); //서서히 사라지기
		$('.viewImgList li.imglist0').animate({'opacity':1},1000); //서서히 나타나기
	} else if(slideNum == 1) {
		$('.viewImgList li.imglist0').animate({'opacity':0},1000); //서서히 사라지기
		$('.viewImgList li.imglist2').animate({'opacity':0},1000); //서서히 사라지기
		$('.viewImgList li.imglist1').animate({'opacity':1},1000); //서서히 나타나기
	} else if(slideNum == 2) {
		$('.viewImgList li.imglist0').animate({'opacity':0},1000); //서서히 사라지기
		$('.viewImgList li.imglist1').animate({'opacity':0},1000); //서서히 사라지기
		$('.viewImgList li.imglist2').animate({'opacity':1},1000); //서서히 나타나기
	}
	// 플래그(깃발) true(자동슬라이드), false(슬라이드멈춤)
	if(slideAuto) { //true일때
		clearTimeout(slideAuto); // play_w함수 실행 중지함.
	}
	slideAuto = setTimeout('play_w("right")', 3000); // 3초 단위로 play_w('right') 실행하라는 명령
} //play_w 함수 끝
