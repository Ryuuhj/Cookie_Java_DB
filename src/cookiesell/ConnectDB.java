package cookiesell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
 
public class ConnectDB {
    private Connection conn; //DB 커넥션 연결 객체
    private Cookie cookie;
    private static final String USERNAME = "root";//DBMS접속 시 아이디
    private static final String PASSWORD = "try1234";//DBMS접속 시 비밀번호
    private static final String URL = "jdbc:mysql://localhost:3306/cookiedb?serverTimezone=UTC";//DBMS접속할 db명
    private ResultSet rs; //쿼리값 or 쿼리 실행 여부 저장
    private PreparedStatement pstmt; //쿼리문 세팅

    
    public ConnectDB() { //생성자를 통해 ConnectDB instance가 생성 되었을 때 자동으로 DB Connect 발생
        try {
            System.out.println("생성자");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("드라이버 로딩 성공");
        } catch (Exception e) {
            System.out.println("드라이버 로딩 실패 ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
          
    }
    //회원 가입
    public int makeAccount(User user) {
    	int result = 0;
    	String SQL = "insert into customer values(?,?,?,?,?)";
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		
    		pstmt.setString(1, user.getId()); //첫번째 물음표에 아이디
    		pstmt.setString(2, user.getPswd());
    		pstmt.setString(3, user.getName());
    		pstmt.setString(4, user.getTel());
    		pstmt.setString(5, user.getSsn());
    		
    		result = pstmt.executeUpdate();
    		
    		if(result>0) {
    			System.out.println("가입 완료");
    		}else {
    			System.out.println("가입 실패\n");
    		}
    		pstmt.close();		
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    	return result; //result=1인 경우 -> 회원가입 성공
    }
    
    //로그인 검사
    public int checking(String C_id, String C_pswd) {
    	String SQL = "Select C_pswd from customer where C_id = ?";
    	//입력할 쿼리문 string으로 사전 정의, where 조건으로 C_id 같은 row의 pswd 를 select함
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setString(1,C_id); //인자로 받은 id sql에 대입
    		
    		rs = pstmt.executeQuery(); //sql 실행한 결과-> rs로
    		//System.out.println(rs);
    		if(rs.next()) { //sql 실행 결과 존재 = 아이디가 존재 -> true
    			if(rs.getString(1).contentEquals(C_pswd)) //나온 튜플의 C_pswd값 = 입력 비밀번호면
    			{
    				return 1; //로그인 성공-> 1 반환
    			}
    			else {
    				return 0; //비밀번호 불일치, 로그인 실패 ->0
    			}
    		}
    		return -1; //아이디 자체가 존재 X
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return -2; //DB오류
    }
    
    public int dropAccount(String C_id, String C_pswd) {
    	String SQL = "Delete from customer "
    			+ "where C_id = ? "
    			+ "and C_pswd = ?"; //입력한 아이디와 비밀번호가 일치하면(and)
    	int drop = 0;
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setString(1,C_id); //첫 번째 ?자리에 -> C_id
    		pstmt.setString(2,C_pswd); //두 번째 ?자리에 pswd입력 받은 값
    		
    		drop = pstmt.executeUpdate();
    		
    		if(drop==1) {
    			System.out.println("탈퇴 완료"); //삭제 된 row가 1개 일 때 
    		}else {
    			System.out.println("탈퇴 실패"); //삭제된 row 0개-> 삭제된 것 없음 or 2개이상 삭제
    		}
    		pstmt.close();		
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    	return drop; //result=1인 경우 -> 탈퇴 성공
    
    }
    
    public void renewal (ArrayList<Cookie> cookielist) {
    	String SQL = "Select C.item_id, C.item_name, R.base, C.price, C.i_cnt "
    			+ "From cookiestorage C LEFT OUTER JOIN recipe R "
    			+ "ON C.item_id = R.item_id ";
    	//비건/논비건 속성 추가 위해 left outer join함
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		rs = pstmt.executeQuery(); //sql 실행한 결과
    		//System.out.println(rs);
    		cookielist.clear(); //목록 값 갱신 전 초기화
    		int i = 0;//arraylist index용
    		while(rs.next()) { //select값이 존재한다면
    			cookie = new Cookie(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)); //나온 값을 쿠키 객체에 저장
    			cookielist.add(i++, cookie); //정보가 저장된 객체를 arraylist에 원소로 저장
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
    		//System.out.println("재고체크성공");
          	if(rs.next()) {
    		k = rs.getInt(1);
    		return k;
    		//System.out.println(k);
          	}
    		
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(k);
    		
    	}	
    	 return -1;//아닌 경우 return 
    }
    
    public void insertOrderList (OrderOption odop, String id) {
    	String SQL = "insert into order_list values(?,?,?,?,?,?,?,?,?,?)";
    	ArrayList<MyCookie> list = new ArrayList<MyCookie>();
    	int i =0;
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		list = odop.getCookielist();
    		while(i < list.size()){
    			pstmt.setString(1, id); //첫번째 물음표에 아이디
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
    		System.out.println("성공");
    		pstmt.close();		
    	}catch (Exception e) {
    		System.out.println(e.toString());
    		//System.out.println("insert오류?");
    	}
    }
    
    public void updateStorage (OrderOption odop) {
    	String SQL ="Update cookiestorage "
    			+ "SET i_cnt= CASE WHEN i_cnt>= ? THEN i_cnt-? END " //2차 체크
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
    		System.out.println("재고량 갱신 성공");
    		pstmt.close();		
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    }
    //tab2에서 주문 정보 갱신할 함수
    public Info getInfo (String C_id, int month, int day) {
    	String SQL = "SELECT O.ID, C.item_name, Sum(O.priceSum), O.vmonth, O.vday, O.payment, O.shopbag, O.confirm "
    			+ "FROM order_list O LEFT OUTER JOIN cookiestorage C "
    			+ "ON O.item_id = C.item_id "
    			+ "WHERE O.ID =? "
    			+ "AND O.vmonth =? "
    			+ "AND O.vday =? "
    			+ "AND O.confirm = 'X' " //주문 확정되지 않은 내역만 사용자 이용에 뜨게 함
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
    			
    			
    			 //주문 정보 저장한 객체 return
    		}
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    	}	
    	return info;//최종 생성 객체 return
    }

    
}