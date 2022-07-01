package cookiesell;
//전체 재고 쿠키마다 각 객체로 생성할 class 
public class Cookie { 
	private String item_id; //쿠키 아이디 저장
	private String item_name; //쿠키 이름 저장
	private String description; //비건, 논비건 분류
	private int price; //쿠키 가격 저장
	private int i_cnt;//쿠키 재고량 저장


	public Cookie(String id, String name, String description, int price, int cnt) {
		this.item_id = id;
		this.item_name = name;
		this.price = price;
		this.i_cnt = cnt;
		this.description = description; //생성자로 받아온 값 class에 세팅하는 생성자 만들기
	}
	
	//private 속성들 접근 가능하게 getter setter 생성
	public String getId() { 
		return item_id;
	}
	public void setId(String id) {
		this.item_id = id;
	}

	public String getName() {
		return item_name;
	}
	public void setName(String name) {
		this.item_name = name;
	}

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public int getcnt() {
		return i_cnt;
	}
	public void setcnt(int cnt) {
		this.i_cnt = cnt;
	}
	
	public String getdescription() {
		return description;
	}
	public void setdescription(String description) {
		this.description = description;
	}
	
}
