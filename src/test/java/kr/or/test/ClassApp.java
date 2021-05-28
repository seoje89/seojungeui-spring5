package kr.or.test;
/**
 * 이 클래스는 
 * 1.클래스 상속(extends)에 대한 연습
 * 2.오브젝트(객체) 생성과 new 키워드(예약어) 생성자메서드 사용 연습
 * 3.추상클래스(Abstract)에 대한 연습
 * @author 서정의
 *
 */
//오브젝트 생성에 필요한 new 키워드 생성자메서드 사용한 실습 클래스(아래)
class Circle {
	private int r; //원의 반지름으로 쓸 변수
	//생성자 메서드(아래)
	//public Circle() {} 만들지 않아도 자동으로 만들어진다.
	public Circle(int radius) {
		r = radius; //Get|Set중에 셋 저장 멤버변수 처리
	}
	//원의 넓이를 구하는 메서드 반환(리턴)값의 크기가 더블형(아래)
	public double getCircle() {
		double result = r*r*3.14;//원의 넓이를 구하는 계산식
		return result;
	}
}
//클래스 상속에 대한 연습(아래)
class Employee { //고용(사원,취업)
	int mSalary; // m멤버변수 사원 월급 변수
	String mDept; // 사원 부서 변수
	public void doJob() {
		System.out.println("환영합니다. 직원 여러분!");
	}
}
class Salesman extends Employee { // extends 확장(상속)
	public Salesman() { //클래스명과 동일한 이름의 메서드가 생성자 함수이다.
		// 자동으로 생성되는데, 개발자가 커스터마이징 할때 만든다.(아래)
		mDept = "판매 부서"; //부모 클래스의 변수를 이어받아 사용가능.
	}
	public void doJob() {
		System.out.println("환영합니다. "+ mDept + " 여러분!");
	}
}
class Development extends Employee {
	public Development() {
		mDept = "개발 부서";
	}
	public void doJob() {
		System.out.println("환영합니다. "+mDept+" 여러분!!!");
	}
}
//추상 클래스 구현 코딩
abstract class GraphicObject {
	int x,y;
	abstract void draw();//구현내용이 없고, 메서드명만 존재 = 추상메서드
	//추상메서드를 만드는 이유는 메서드가 너무 많으면 개발자가 구현할때 어려움이 생김.
	//위 문제를 해소하는게 추상메서드 = 수백개의 메서드를 이름만 모아서, 유지보수가 편리하게 처리
}
//추상 클래스 사용하는 방법(아래)
//스프링에서는 추상클래스보다는 인터페이스를 더 많이 사용. 사용의도는 동일
class Triangle extends GraphicObject {
	//삼각형 그리기
	@Override // 부모클래스의 메서드를 재정의해서 사용하는것 = 오버라이드 // 오버로드와 차이점 확인(step1)
	void draw() {
		// 삼각형을 만듬
		System.out.println("  *  ");
		System.out.println(" * * ");
		System.out.println("*****");
	}
}
class Rectangle extends GraphicObject {
	//사각형 그리기
	@Override
	void draw() { //오버라이드 @애노테이션이 있으면, 상속이라는 말.
		// 사각형을 만듬
		System.out.println("*****");
		System.out.println("*   *");
		System.out.println("*****");
	}
	
	
}
public class ClassApp {

	public static void main(String[] args) {
		//추상클래스를 오버라이드 메서드 사용(아래)
		GraphicObject triangleObject = new Triangle(); // 추상클래스는 객체로 만들지 못함.
		GraphicObject rectangleObject = new Rectangle();
		triangleObject.draw();
		rectangleObject.draw();
		//개발자가 입력한 반지름의 원의 넓이를 구하는 오브젝트를 생성(아래)
		Circle circle = new Circle(5);//반지름이 5인 원의 넓이를 구현하는 객체생성
		System.out.println("원의 넓이는 "+ circle.getCircle());
		circle = null; //메모리(오브젝트) 반환
		//클래스 상속과 메서드 실행 실습(아래)
		Employee employee = new Employee();
		Salesman salesman = new Salesman();
		Development development = new Development();
		employee.doJob();
		salesman.doJob();
		development.doJob();
	}
}
