package cookiesell;
import java.awt.Color;

import javax.swing.*;

public class Main {
	private JFrame frame;
	private Tab1 tab1;
	private Tab2 tab2;
	private Tab3 tab3;
	private Tab4 tab4;
	private User userlog;
	private ConnectDB db;

	
	public Main() {
		frame = new JFrame();
		db = new ConnectDB();
		userlog = new User();
		frame.setTitle("쿠키 예약 시스템"); // 프로그램 이름 지정
		frame.setSize(1100,700); //프레임 사이즈 지정
		//frame.setResizable(false); //사이즈 재조정 불가능
		frame.setLocationRelativeTo(null); //윈도우 참 가운데에 띄우기
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //나가기 버튼 누르면 창 끄기
		JTabbedPane maintab = new JTabbedPane();//탭 패널 생성
		tab1 = new Tab1(this); //탭 1-메인화면 생성
		tab2 = new Tab2(this); //탭 2 - 마이페이지 생성
		tab3 = new Tab3(this); //탭 3 - 장바구니 생성
		tab4 = new Tab4(this);
		
		maintab.addTab("메인화면",tab1);
		maintab.addTab("장바구니",tab3);
		maintab.addTab("마이페이지",tab2);//메인탭에 붙이기
		maintab.addTab("로그인", tab4);
		
        frame.getContentPane().add(maintab); //탭 프레임에 붙이기
        frame.setVisible(true); // 창 보이게하기
        
	}
	
	public Tab1 getTab1() {
		return tab1;
	}
	
	public Tab2 getTab2() {
		return tab2;
	}
	
	public Tab3 getTab3() {
		return tab3;
	}
	public void setUser(String id, String pswd) { //로그인 시, 모든 탭에서 id등 유저 정보 이용 가능하게 main에다 정보를 저장한 뒤 공유하는 방식
		userlog.setuser(id, pswd);
		//System.out.println(userlog.getId()+ userlog.getPswd()); //디버깅용
	}
	public String getUserId() {
		return userlog.getId();
	}
	public String getUserpw() {
		return userlog.getPswd();
	}
	public ConnectDB useDB() {
		return db;
	}
	
	public static void main(String[] args) {
		Main main = new Main();
	}
	
}
