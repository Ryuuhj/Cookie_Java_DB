package cookiesell;
//세밀한 주문정보는 ConnectDB에서 

public class Info {
	private int total=0;
	private String payment,shoppingbag,item_name,confirm =" ";
	private int month, day=0;
	
	public Info(){
		
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getShoppingbag() {
		return shoppingbag;
	}

	public void setShoppingbag(String shoppingbag) {
		this.shoppingbag = shoppingbag;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
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
	
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

}
