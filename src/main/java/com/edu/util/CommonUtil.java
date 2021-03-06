package com.edu.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edu.dao.IF_BoardDAO;
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
	@Inject
	private IF_BoardDAO boardDAO;
	
	//첨부파일 업로드/다운로드/삭제/인서트/수정에 모두 사용될 저장경로를 1개지정해서 [전역]으로사용
	@Resource(name="uploadPath")
	private String uploadPath;//root-context 업로드경로 클래스빈의 id값을 받아서 String변수 입력
	public String getUploadPath() {
		return uploadPath;
	}
	
	//첨부파일 개별삭제 Ajax로 받아서 처리, @ResponseBody사용
	@RequestMapping(value="/file_delete", method=RequestMethod.POST)
	@ResponseBody
	public String file_delete(@RequestParam("save_file_name")String save_file_name) { //Ajax는 예외처리를 스프링에 던지지 않고, try~catch문으로 처리.
		String result = "";//Ajax로 보내는 값변수
		try {
			boardDAO.deleteAttach(save_file_name);
			File target = new File(uploadPath + "/" + save_file_name);
			if(target.exists()) {
				target.delete();
			}
			result = "success";
		} catch (Exception e) {
			result = "fail: " + e.toString();
		}
		return result;//Ajax에서 바로확인 가능
	}
	//다운로드 처리도 같은 페이지에서 결과값만 반환받는 @ResponseBody 사용
	@RequestMapping(value="/download", method=RequestMethod.GET)
	@ResponseBody
	public FileSystemResource download(@RequestParam("save_file_name")String save_file_name, @RequestParam("real_file_name")String real_file_name, HttpServletResponse response) throws Exception {
		//FileSyste...은 스프링 코어에서 제공하는 전용 파일처리 클래스
		File file = new File(uploadPath + "/" + save_file_name);
		response.setContentType("application/download; utf-8");//아래한글,ppt문서등에서 한글내용이 깨지는 것을 방지하는 코드추가
		real_file_name = URLEncoder.encode(real_file_name);//ie에서 URL한글일때 에러발생방시 코드 추가
		response.setHeader("Content-Disposition", "attachment; filename=" + real_file_name);
		return new FileSystemResource(file);
	}
	
	//페이지 이동이 아닌 같은 페이지에 결과값만 반환하는 @ResponseBody 필요
	@RequestMapping(value="/image_preview",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> imagePreview(@RequestParam("save_file_name") String save_file_name, HttpServletResponse response) throws Exception {
		//파일을 입출력할때는 스트림(byte형식의 파일을 입출력할때 발생하는 통로)이 발생
		FileInputStream fis = null; //입력통로
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		fis = new FileInputStream(uploadPath + "/" + save_file_name);
		int readCount = 0;
		byte[] buffer = new byte[1024]; //임시저장소 크기 지정 1k
		byte[] fileArray = null; // 출력스트림결과 저장하는 공간
		//반복문 : 목적, fis 입력받는 save_file_nmae 바이트값이(배열) -1일때까지 반복
		while((readCount=fis.read(buffer)) != -1) {
			//입력통로 fis에서 출력통로 baos로 보냄. 이유는 파일입출력은 byte단위로만 가능
			baos.write(buffer, 0, readCount); // (rawData, 종료조건, 반복횟수)
			//결과는 baos에 누적된 결과가 들어가게됨. - jsp로 보내주면 됨
		}
		fileArray = baos.toByteArray();//baos클래스를 byte[] 배열로 형변환
		fis.close();//메모리 초기화
		baos.close();//메모리 초기화
		//fileArray값을 jsp로 보내주는 준비작업, final 이 메서드에만 사용하겠다고 명시.
		final HttpHeaders headers = new HttpHeaders();
		//크롬 개발자도구>네트워크>image_preview클릭>헤더탭확인
		String ext = FilenameUtils.getExtension(save_file_name);
		//이미지 확장자에 따라서 매칭되는 헤더값이 변해야지만, 이미지 미리보기가 정상으로 보입니다.
		switch(ext.toLowerCase()) {//선택조건:확장자를 소문자로 바꿔서 비교
		case "png":
			headers.setContentType(MediaType.IMAGE_PNG);
			break;//스위치문 빠져나가기
		case "jpg":
			headers.setContentType(MediaType.IMAGE_JPEG);
			break;
		case "gif":
			headers.setContentType(MediaType.IMAGE_GIF);
			break;
		case "jpeg":
			headers.setContentType(MediaType.IMAGE_JPEG);
			break;
		case "bmp":
			headers.setContentType(MediaType.parseMediaType("image/bmp"));
			break;
		default:break;
		}
		
		return new ResponseEntity<byte[]>(fileArray,headers,HttpStatus.CREATED);//객체생성시 초기값(rawData,헤더정보,HTTP상태값)
	}
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
	
	//관리자단에서 사용 : RestAPI서버 맛보기 ID중복체크(제대로 만들면 @RestController 사용)
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
	//사용자단에서 사용: JsonView 방식 사용
	@RequestMapping(value="/id_check_2010", method=RequestMethod.GET)
	public String id_check_2010(Model model, @RequestParam("user_id") String user_id) throws Exception {
		String memberCnt = "1"; //중복 ID가 있는것을 기본값으로 지정
		if(!user_id.isEmpty()) {
			MemberVO memberVO = memberService.readMember(user_id);
			if(memberVO == null) { //중복ID가 없다면
				memberCnt = "0";
			}
		}
		model.addAttribute("memberCnt", memberCnt);
		return "jsonView";//jsp파일명 대신에 servlet에서 정의한 스프링빈 ID명을 적으면, json객체로 결과를 반환함
	}
	
	
	//파일 업로드 공통메서드(Admin컨트롤러에서 사용 + Home컨트롤러에서도 사용)
	public String fileUpload(MultipartFile file) throws IOException {
		// UUID클래스로 저장될 고유식별 파일명을 생성 후 물리적으로 저장
		String realFileName = file.getOriginalFilename();
		// 폴더에 저장할 PK파일명을 생성(아래)
		UUID uid = UUID.randomUUID(); //유니크ID값 생성
		String saveFileName = uid.toString() + "." + StringUtils.getFilenameExtension(realFileName);
		//file의 MultipartFile 클래스형 객체. 클래스형 자료(멤버변수,메서드..)는 직접 저장을 할수 없음.
		//그래서 바이트형으로 파싱(변환)해서 저장해야함. -> bit형 이진 비트형 자료로 파싱
		//자바자료형 정수: byte<short<int<long, 실수형(소수점):float<double
		byte[] fileData = file.getBytes(); // getBytes 메서드로 데이터를 bit형으로 파싱해서 저장
		File target = new File(uploadPath, saveFileName);
		FileCopyUtils.copy(fileData, target); //물리적으로 폴더에 저장됨
		return saveFileName;
	}

	public void profile_upload(String user_id, HttpServletRequest request, MultipartFile file) throws IOException {
		// 프로필이미지는 보안이 필요한 폴더가 아닌 resources 폴더에 업로드 처리
		String folderPath = request.getServletContext().getRealPath("/resources/profile"); // url로 업로드할수 없기 때문에, 서버경로를 구하는 명령
		File makeFolder = new File(folderPath); // 공백인 개체 생성
		if(!makeFolder.exists()) {
			makeFolder.mkdir(); // 여기서 신규폴더가 생성됨
		}
		// 1개에 파일이 1000개 이상이면, 조회속도가 매우 느려짐
		// 년월폴더를 생성후, 해당년월에 업로드된 파일은 년월폴더로 관리
		byte[] fileData = file.getBytes();
		File target = new File(makeFolder,user_id+".jpg");//user_id는 PK이기 때문에 기존파일이 있다면 덮어쓰면서 저장됨
		FileCopyUtils.copy(fileData, target); // 첨부파일이 업로드폴더에 저장됨
	}

	public void profile_delete(String user_id, HttpServletRequest request) {
		// 프로필 이미지가 프로필 폴더에 존재하면 삭제하는 로직
		String folderPath = request.getServletContext().getRealPath("/resources/profile");
		File target = new File(folderPath,user_id+".jpg");
		if(target.exists()) {
			target.delete();//프로필 파일 실제로 삭제됨
		}
	}
}

