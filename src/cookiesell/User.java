package cookiesell;

public class User {
	private String C_id ="";
	private String C_pswd;
	private String C_name;
	private String phonenum;
	private String SSN;

	public String getId() {
		return C_id;
	}
	public String getPswd() {
		return C_pswd;
	}
	public void setPswd(String pswd) {
		this.C_pswd = pswd;
	}
	public String getName() {
		return C_name;
	}
	public void setname(String name) {
		this.C_name = name;
	}
	public String getTel() {
		return phonenum;
	}
	public void setTel(String tel) {
		this.phonenum = tel;
	}
	public String getSsn() {
		return SSN;
	}
	public void setSsn(String ssn) {
		this.SSN = ssn;
	}
	public void joinsetting(String id, String pswd, String name, String tel, String ssn) {
		this.C_id = id;
		this.C_pswd = pswd;
		this.C_name = name;
		this.phonenum = tel;
		this.SSN = ssn;
	}
	public void setuser(String id, String pswd) { //main에서 사용
		this.C_id = id;
		this.C_pswd = pswd;
	}
	@Override
	public String toString() {
		String str = String.format(" id: %s\n pswd: %s\n name: %s\n phoneNumber: %s\n birth: %s\n", C_id, C_pswd, C_name, phonenum, SSN);
		return str;
	}
	
}
