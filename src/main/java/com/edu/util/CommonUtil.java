package com.edu.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edu.service.IF_MemberService;
import com.edu.vo.MemberVO;

/**
 * 이 클래스는 이 프로젝트에서 공통으로 사용하는 유틸리티기능을 모아놓은 클래스
 * @author 서정의
 * 컨트롤러 기능 @Controller(jsp와 바인딩이 필요할때는 필수 애노테이션)
 * 컴포넌트 기능 @Component는 MVC가 아닌 기능들을 모아놓은 스프링빈 명시, 유틸메서드 포함. 해당페이지에서는 jsp와 바인딩이 필요해서 사용안함
 */
@Controller
public class CommonUtil {
	//멤버변수 생성
	private Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	@Inject
	private IF_MemberService memberService; //스프링빈을 주입받아서(DI) 객체준비
	
	//XSS 크로스사이트스크립트 방지용 코드로 파싱하는 메서드(아래)
	public String unScript(String data) {
		//if(data == null || data.trim().equals("")) {} <- 옛날방식
		if(data.isEmpty()) {
			return "";
		}
		String ret = data;
		ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		return ret;
	}
	//첨부파일 업로드/다운로드/삭제/인서트/수정에 모두 사용될 저장경로지정. 전역으로 사용
	@Resource(name="uploadPath")
	private String uploadPath; // root-context에 있는 업로드경로 클래스빈의 id값을 받아서 String 변수에 입력
	
	public String getUploadPath() {
		return uploadPath;
	}

	//첨부파일이 이미지인지 아닌지 확인하는 데이터생성
	private ArrayList<String> checkImgArray = new ArrayList<String>() {
		{
		add("gif");
		add("jpg");
		add("jpeg");
		add("png");
		add("bmp");
		}
	};
	public ArrayList<String> getCheckImgArray() {
		return checkImgArray;
	}
	
	//RestAPI서버 맛보기 ID중복체크(제대로 만들면 @RestController 사용)
	@RequestMapping(value="/id_check", method=RequestMethod.GET)
	@ResponseBody //반환받는 값의 헤더값을 제외하고, 내용(body)만 반환받겠다고 명시
	public String id_check(@RequestParam("user_id") String user_id) throws Exception {
		//중복아이디를 체크로직(아래)
		String memberCnt = "1"; // 중복ID가 있을때, 기본값1
		if(!user_id.isEmpty()) { // user_id가 공백이 아니라면,
			MemberVO memberVO = memberService.readMember(user_id);
			logger.info("디버그: " + memberVO); //user_id를 공백을 전송해도 null이기 때문에 조건이 한가지 더 필요함
			if(memberVO == null) { // 중복아이디가 존재하지 않으면 {}안을 실행
				//isEmpty는 null체크 + "" 공백체크 모두 포함
				memberCnt = "0";
			}
		}
		return memberCnt; //0.jsp 이렇게 작동X. 이유는 @ResponseBody때문, RestAPI는 값만 반환
	}
	
	//파일 업로드 공통메서드(Admin컨트롤러에서 사용 + Home컨트롤러에서도 사용)
	public String fileUpload(MultipartFile file) throws IOException {
		// UUID클래스로 저장될 고유식별 파일명을 생성 후 물리적으로 저장
		String realFileName = file.getOriginalFilename();
		// 폴더에 저장할 PK파일명을 생성(아래)
		UUID uid = UUID.randomUUID(); //유니크ID값 생성
		String saveFileName = uid.toString() + "." + StringUtils.getFilenameExtension(realFileName);
		byte[] fileData = file.getBytes();
		File target = new File(uploadPath, saveFileName);
		FileCopyUtils.copy(fileData, target); //물리적으로 폴더에 저장됨
		return saveFileName;
	}
}

