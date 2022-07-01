package cookiesell;
import java.util.ArrayList;

public class OrderOption {
	
	private int total;
	private ArrayList<MyCookie> cookielist = new ArrayList<MyCookie>();
	//Orederoption에 저장한 주문 쿠키 list 생성
	private String payment;
	private String shoppingbag;
	private int month, day;
	public OrderOption() {
		
	}

	public ArrayList<MyCookie> getCookielist() {
		return cookielist;
	}

	public void setCookielist(ArrayList<MyCookie> cookielist) {
		this.cookielist = cookielist;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPayway() {
		return payment;
	}
	public void setPayway(int payway) {
		if(payway == 1) {
			this.payment = "신용카드";
		}else if (payway == 2) {
			this.payment = "카카오페이";
		}else if (payway == 3) {
			this.payment = "페이코";
		}
	}
	public String getShoppingbag() {
		return shoppingbag;
	}
	public void setShoppingbag(int shoppingbag) {
		if(shoppingbag == 0) {
			this.shoppingbag = "O";
		}else if(shoppingbag == 1) {
			this.shoppingbag = "X";
		}
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
}
