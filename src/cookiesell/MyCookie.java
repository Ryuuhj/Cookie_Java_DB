package cookiesell;

//고객이 고른 쿠키 정보 담을 객체 생성할 MyCookie class 생성
public class MyCookie{
	private Cookie cookie; //전체 쿠키중에 선택하는 것이므로 기존 전체 쿠키 다루는 Cookie class 참조
	private String name; //이름
	private int price; //가격
	private int cnt; //숫자
	private int minisum;
	
//mycookie 객체 생성시 정보 담아올 생성자 정의
	public MyCookie(Cookie cookie, String name, int price, int cnt) {
		//super();
		this.cookie = cookie; //쿠키에 대한 전체 정보 전달받기 위해 cookie 객체 매개변수로 사용
		this.name = name; //이름 설정
		this.price = price; //가격
		this.cnt = cnt; //구매 개수
		this.minisum = price * cnt;
	}
//마찬가지로 각 private 속성에 접근하기 위한 getter, setter 설정
	public Cookie getCookie() {
		return cookie;
	}
	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getcatesum() {
		return minisum;
	}
}
