package cookiesell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
 
public class ConnectDB {
    private Connection conn; //DB Ŀ�ؼ� ���� ��ü
    private Cookie cookie;
    private static final String USERNAME = "root";//DBMS���� �� ���̵�
    private static final String PASSWORD = "try1234";//DBMS���� �� ��й�ȣ
    private static final String URL = "jdbc:mysql://localhost:3306/cookiedb?serverTimezone=UTC";//DBMS������ db��
    private ResultSet rs; //������ or ���� ���� ���� ����
    private PreparedStatement pstmt; //������ ����

    
    public ConnectDB() { //�����ڸ� ���� ConnectDB instance�� ���� �Ǿ��� �� �ڵ����� DB Connect �߻�
        try {
            System.out.println("������");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("����̹� �ε� ����");
        } catch (Exception e) {
            System.out.println("����̹� �ε� ���� ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
          
    }
    //ȸ�� ����
    public int makeAccount(User user) {
    	int result = 0;
    	String SQL = "insert into customer values(?,?,?,?,?)";
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		
    		pstmt.setString(1, user.getId()); //ù��° ����ǥ�� ���̵�
    		pstmt.setString(2, user.getPswd());
    		pstmt.setString(3, user.getName());
    		pstmt.setString(4, user.getTel());
    		pstmt.setString(5, user.getSsn());
    		
    		result = pstmt.executeUpdate();
    		
    		if(result>0) {
    			System.out.println("���� �Ϸ�");
    		}else {
    			System.out.println("���� ����\n");
    		}
    		pstmt.close();		
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    	return result; //result=1�� ��� -> ȸ������ ����
    }
    
    //�α��� �˻�
    public int checking(String C_id, String C_pswd) {
    	String SQL = "Select C_pswd from customer where C_id = ?";
    	//�Է��� ������ string���� ���� ����, where �������� C_id ���� row�� pswd �� select��
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setString(1,C_id); //���ڷ� ���� id sql�� ����
    		
    		rs = pstmt.executeQuery(); //sql ������ ���-> rs��
    		//System.out.println(rs);
    		if(rs.next()) { //sql ���� ��� ���� = ���̵� ���� -> true
    			if(rs.getString(1).contentEquals(C_pswd)) //���� Ʃ���� C_pswd�� = �Է� ��й�ȣ��
    			{
    				return 1; //�α��� ����-> 1 ��ȯ
    			}
    			else {
    				return 0; //��й�ȣ ����ġ, �α��� ���� ->0
    			}
    		}
    		return -1; //���̵� ��ü�� ���� X
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return -2; //DB����
    }
    
    public int dropAccount(String C_id, String C_pswd) {
    	String SQL = "Delete from customer "
    			+ "where C_id = ? "
    			+ "and C_pswd = ?"; //�Է��� ���̵�� ��й�ȣ�� ��ġ�ϸ�(and)
    	int drop = 0;
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setString(1,C_id); //ù ��° ?�ڸ��� -> C_id
    		pstmt.setString(2,C_pswd); //�� ��° ?�ڸ��� pswd�Է� ���� ��
    		
    		drop = pstmt.executeUpdate();
    		
    		if(drop==1) {
    			System.out.println("Ż�� �Ϸ�"); //���� �� row�� 1�� �� �� 
    		}else {
    			System.out.println("Ż�� ����"); //������ row 0��-> ������ �� ���� or 2���̻� ����
    		}
    		pstmt.close();		
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    	return drop; //result=1�� ��� -> Ż�� ����
    
    }
    
    public void renewal (ArrayList<Cookie> cookielist) {
    	String SQL = "Select C.item_id, C.item_name, R.base, C.price, C.i_cnt "
    			+ "From cookiestorage C LEFT OUTER JOIN recipe R "
    			+ "ON C.item_id = R.item_id ";
    	//���/���� �Ӽ� �߰� ���� left outer join��
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		rs = pstmt.executeQuery(); //sql ������ ���
    		//System.out.println(rs);
    		cookielist.clear(); //��� �� ���� �� �ʱ�ȭ
    		int i = 0;//arraylist index��
    		while(rs.next()) { //select���� �����Ѵٸ�
    			cookie = new Cookie(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)); //���� ���� ��Ű ��ü�� ����
    			cookielist.add(i++, cookie); //������ ����� ��ü�� arraylist�� ���ҷ� ����
    		}
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    	}	
    }
    
    public int checkonCnt(String id) {
    	String SQL = "Select i_cnt from cookiestorage where item_id = ?";
    	
    	int k =0; 
    	try {
    		pstmt = conn.prepareStatement(SQL);
          	pstmt.setString(1, id);
          	rs = pstmt.executeQuery(); 
    		//System.out.println("���üũ����");
          	if(rs.next()) {
    		k = rs.getInt(1);
    		return k;
    		//System.out.println(k);
          	}
    		
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(k);
    		
    	}	
    	 return -1;//�ƴ� ��� return 
    }
    
    public void insertOrderList (OrderOption odop, String id) {
    	String SQL = "insert into order_list values(?,?,?,?,?,?,?,?,?,?)";
    	ArrayList<MyCookie> list = new ArrayList<MyCookie>();
    	int i =0;
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		list = odop.getCookielist();
    		while(i < list.size()){
    			pstmt.setString(1, id); //ù��° ����ǥ�� ���̵�
        		pstmt.setString(2, odop.getShoppingbag());
        		pstmt.setString(3, odop.getPayway());
        		pstmt.setInt(4, odop.getMonth());
        		pstmt.setInt(5, odop.getDay());
        		pstmt.setString(6, list.get(i).getCookie().getId());
        		pstmt.setInt(7, list.get(i).getCnt());
        		pstmt.setInt(8, list.get(i).getcatesum());
        		pstmt.setInt(9, odop.getTotal());
        		pstmt.setString(10, "X");
        		pstmt.executeUpdate();
        		i++;
    		}
    		System.out.println("����");
    		pstmt.close();		
    	}catch (Exception e) {
    		System.out.println(e.toString());
    		//System.out.println("insert����?");
    	}
    }
    
    public void updateStorage (OrderOption odop) {
    	String SQL ="Update cookiestorage "
    			+ "SET i_cnt= CASE WHEN i_cnt>= ? THEN i_cnt-? END " //2�� üũ
    			+ "Where item_id = ?";
    	ArrayList<MyCookie> list = new ArrayList<MyCookie>();
    	int i =0;
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		list = odop.getCookielist();
    		while(i < list.size()){
    			pstmt.setInt(1, list.get(i).getCnt()); 
    			pstmt.setInt(2, list.get(i).getCnt()); 
        		pstmt.setString(3, list.get(i).getCookie().getId());
        		
        		pstmt.executeUpdate();
        		i++;
    		}
    		System.out.println("��� ���� ����");
    		pstmt.close();		
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    }
    //tab2���� �ֹ� ���� ������ �Լ�
    public Info getInfo (String C_id, int month, int day) {
    	String SQL = "SELECT O.ID, C.item_name, Sum(O.priceSum), O.vmonth, O.vday, O.payment, O.shopbag, O.confirm "
    			+ "FROM order_list O LEFT OUTER JOIN cookiestorage C "
    			+ "ON O.item_id = C.item_id "
    			+ "WHERE O.ID =? "
    			+ "AND O.vmonth =? "
    			+ "AND O.vday =? "
    			+ "AND O.confirm = 'X' " //�ֹ� Ȯ������ ���� ������ ����� �̿뿡 �߰� ��
    			+ "Group By O.ID";
    	Info info = new Info();
    	
    	try {
    		
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setString(1,C_id);
    		pstmt.setInt(2,month);
    		pstmt.setInt(3,day);
    		rs = pstmt.executeQuery(); 
    		
    		if(rs.next()) {
    			
    			info.setItem_name(rs.getString(2));
    			info.setTotal(rs.getInt(3));
    			info.setMonth(rs.getInt(4));
    			info.setDay(rs.getInt(5));
    			info.setPayment(rs.getString(6));
    			info.setShoppingbag(rs.getString(7));
    			
    			
    			 //�ֹ� ���� ������ ��ü return
    		}
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    	}	
    	return info;//���� ���� ��ü return
    }

    
}