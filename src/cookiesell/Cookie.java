package cookiesell;
//��ü ��� ��Ű���� �� ��ü�� ������ class 
public class Cookie { 
	private String item_id; //��Ű ���̵� ����
	private String item_name; //��Ű �̸� ����
	private String description; //���, ���� �з�
	private int price; //��Ű ���� ����
	private int i_cnt;//��Ű ��� ����


	public Cookie(String id, String name, String description, int price, int cnt) {
		this.item_id = id;
		this.item_name = name;
		this.price = price;
		this.i_cnt = cnt;
		this.description = description; //�����ڷ� �޾ƿ� �� class�� �����ϴ� ������ �����
	}
	
	//private �Ӽ��� ���� �����ϰ� getter setter ����
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
