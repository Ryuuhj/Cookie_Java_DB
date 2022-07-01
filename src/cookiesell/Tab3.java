package cookiesell;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Tab3 extends JPanel implements ItemListener { //JPanel 상속받고 ItemListener 동작 위해 implements

	private Main main;
	ConnectDB db = new ConnectDB();
	JButton clear;
	JTextField tfMonth, tfDay;
	JRadioButton pay1, pay2, pay3,spbag1, spbag2;
	JTable table;
	JPanel tablepanel;
	ImageIcon butdefault = new ImageIcon("img/button1.png");
	ImageIcon buthover = new ImageIcon("img/button2.png");
	Color bluee = new Color(204,229,255);
	Color yellow = new Color(255,255,204);
	Font font = new Font("굴림", Font.BOLD, 20); //총 금액 표기할 폰트
	String id="";
	
	String[] columName = {"제품명", "수량", "금액"};//테이블 상단에 들어갈 ColumName
	int pw, spb = -1; //선택 유무 판단하기 위한 변수(+ 통과 시 값 담을)
	int month, day =0; //이하 동문
	JLabel total;
	int totalprice;//테이블을 통해 계산할 총 금액 담을 변수
	
	private ArrayList<MyCookie> pickCookielist = new ArrayList<>();
	//고객이 고른 쿠키에 대한 정보들 배열로 저장-> 테이블 생성 위한 arraylist 생성(크기 가변적, 추가, 삭제 가능)

	
	public Tab3(Main main) {//객체 생성자 정의
		this.main = main; //Main 패널 경유해 다른 tab에 관여하기 위해 참조

		setLayout(new GridLayout(2,0)); //테이블/옵션 선택 부분 크게 2가지로 나뉘어 0*2
		
		//Table 생성 part
		tablepanel = new JPanel(); //테이블 붙일 패널 생성
		tablepanel.setLayout(new BorderLayout());//레이아웃 설정
		DefaultTableModel tableModel = new DefaultTableModel(columName,0); //테이블 갱신하기 위해 테이블 모델 사용 (디폴트로 value값만 띄워놓음)
		table = new JTable(tableModel); //테이블 모델 이용해 테이블 생성
		
		JScrollPane scp = new JScrollPane(table);//테이블 붙일 스크롤 패널 생성
		tablepanel.add("Center",scp); //스크롤 패널 테이블 붙일 패널의 Center에 붙이기
		clear =new JButton("초기화"); //테이블 초기화 시킬 초기화 버튼 생성
		
		clear.addActionListener(new ActionListener() { //초기화버튼에 액션 리스너 선언
			//액션 이벤트 리스너 override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<pickCookielist.size(); i++) { //담으면서 감소된 재고 다시 되돌리기 위해 for문 돌리기
					Cookie cookie = pickCookielist.get(i).getCookie(); //arraylist에 담긴 정보들 인덱스별로 get해서 cookie 객체에 부여
					cookie.setcnt(cookie.getcnt()+pickCookielist.get(i).getCnt()); //arraylist에 담긴 정보를 이용해 재고량에 다시 담은개수만큼 더해서 원상복구 시키기
				}//쿠키들 재고량 복구 완료
				main.getTab1().getTab1Left().renderTab1Right();//복구된 재고로 Tab1 오른쪽 다시 갱신 (메인 안 탭 1안의 왼쪽 탭 안 오른쪽 탭 설정 메소드호출)
				table.setModel(tableModel); //테이블 비우기
				pickCookielist.clear(); //리스트 초기화
				totalprice = 0;//총 금액 0으로 초기화
				total.setText(String.valueOf(totalprice)); //하단에 나오는 총 금액 라벨도 0으로 갱신
			}
		});// statement에 초기화 버튼에 대한 액션 이벤트 핸들링 method overriding
		clear.setBackground(bluee);
		tablepanel.add("South",clear); //초기화 버튼 탭패널 하단에 추가
		add(tablepanel); //탭 패널 더하기	
		
		//테이블 기타 설정
		scp.getViewport().setBackground(Color.WHITE);//테이블 없는부분 회색->흰색
		table.setAutoCreateColumnsFromModel(false);
		//헤더 컬럼라벨 직접 제어 -> 자동생성 금지
		DefaultTableCellRenderer dcr = new DefaultTableCellRenderer();
		dcr.setHorizontalAlignment(SwingConstants.CENTER);
		//테이블 내용 가운데 정렬
		
		TableColumnModel tcm = table.getColumnModel();
		for (int i=0;i<tcm.getColumnCount();i++) {
			tcm.getColumn(i).setCellRenderer(dcr);
		} //가운데 정렬 설정한 값으로 재랜더링
	
		//패널 아래부분 세팅 
		JPanel bottom = new JPanel();
		
		bottom.setLayout(new GridLayout(0,2));
		//아래부분 제일 크게 좌우 분할 설정
		JPanel bottomP = new JPanel();
		
		bottomP.setLayout(new GridLayout(4,0));
		//왼쪽 패널에 들어갈 패널 생성, 세로 4간
		JPanel bottomP1 = new JPanel();
		JPanel bottomP2 = new JPanel();
		JPanel bottomP3 = new JPanel();
		bottomP1.setBackground(yellow);
		bottomP2.setBackground(yellow);
		bottomP3.setBackground(yellow);
		
		//각 라디오버튼들 들어갈 패널 생성
		
		//1) 결제 수단 패널
		JPanel g1P = new JPanel();
		ButtonGroup g1 = new ButtonGroup();//다중 선택 방지용 버튼 그룹 설정
		pay1 = new JRadioButton("신용카드", false);
		pay2 = new JRadioButton("카카오페이", false);
		pay3 = new JRadioButton("페이코", false);
		pay1.setBackground(yellow);
		pay2.setBackground(yellow);
		pay3.setBackground(yellow);
		//버튼 생성
		pay1.addItemListener(this);
		pay2.addItemListener(this);
		pay3.addItemListener(this);
		//각 버튼에 아이템 리스너 등록(해당 클래스 내에 method로 override)
		g1P.add(new JLabel("결제방식            "));
		//버튼 그룹에 컴포넌트 추가
		g1.add(pay1);
		g1.add(pay2);
		g1.add(pay3);
		//패널에 컴포넌트 추가
		g1P.add(pay1);
		g1P.add(pay2);
		g1P.add(pay3);
		//좌측부에 결제수단 패널 추가 (1번째 칸)
		bottomP1.add(g1P);
		//2) 쇼핑백 제공 여부 패널
		JPanel g2P = new JPanel();//이하 동문
		ButtonGroup g2 = new ButtonGroup();
		spbag1 = new JRadioButton("Yes", false);
		spbag2= new JRadioButton("No", false);
		spbag1.setBackground(yellow);
		spbag2.setBackground(yellow);
		spbag1.addItemListener(this);
		spbag2.addItemListener(this);
		g2P.add(new JLabel("쇼핑백 제공         "));
		
		g2.add(spbag1);
		g2.add(spbag2);
		g2P.add(spbag1);
		g2P.add(spbag2);
		
		bottomP2.add(g2P);
		//3) 방문 날짜 설정 패널
		JPanel g3P = new JPanel();	
		tfMonth = new JTextField(5);
		tfDay = new JTextField(5);
		
		g3P.add(new JLabel("방문 날짜 선택       "));
		g3P.add(tfMonth);
		g3P.add(new JLabel(" / "));
		g3P.add(tfDay);
		bottomP3.add(g3P);
		//구성 마친 3번 패널 최종 붙이기
		
		//4) 총 금액 안내 패널
		JPanel g4P = new JPanel();
		JLabel front = new JLabel("총 금액: ");
		total = new JLabel("0");//디폴트 값
		JLabel back = new JLabel(" 원");
		//패널 4번에 컴포넌트들 붙이기
		g4P.add(front);
		g4P.add(total);
		g4P.add(back);
		front.setFont(font);
		total.setFont(font);
		back.setFont(font);
		//하단 좌측 컴포넌트 구성 완료
		g1P.setBackground(yellow);
		g2P.setBackground(yellow);
		g3P.setBackground(yellow);
		g4P.setBackground(yellow);
		front.setBackground(yellow);
		back.setBackground(yellow);
		//배경색 설정
		bottomP.add(bottomP1);
		bottomP.add(bottomP2);
		bottomP.add(bottomP3);
		bottomP.add(g4P);
		
		//좌측 컴포넌트 세팅 완료
		JButton confirm = new JButton(butdefault);
		confirm.setRolloverIcon(buthover);
		confirm.setBorderPainted(false);//버튼 테두리 설정 해제
		
		confirm.addActionListener(new Confirm());
		
		
		bottom.add(bottomP);
		bottom.add(confirm);
		add(bottom);
	}
	public void itemStateChanged (ItemEvent e) {
		Object o = e.getSource();
		if(o == pay1) {
			pw = 1; //결제수단 따라 숫자부여-> 나중에 출력할때 번역해서 출력
		}else if(o==pay2) {
			pw = 2;
		}else if(o==pay3) {
			pw = 3;
		}else if(o==spbag1) {
			spb = 0;
		}else if(o==spbag2) {
			spb = 1;}
	}
	//private 쿠키 arraylist를 get해서 이곳에 생성된 arraylist로 저장하기 위한 method 별도로 정의
	public ArrayList<MyCookie> getCookielist() {
		return pickCookielist;
	}
	//예약 확정 버튼 클릭시 발생하는 이벤트 처리 위해 정의한 inner class
	private class Confirm implements ActionListener { 
		
		public void actionPerformed (ActionEvent e) {
			//orderoption 만족 안하면 주문 안넘어가게 함
			if(tfMonth.getText().isEmpty() || tfDay.getText().isEmpty()) {
				month=-1;
				day=-1;} //null일 경우 통과 불가능 부여
			else {
				month = Integer.parseInt(tfMonth.getText()); //null일 경우 오류 발생 방지
				day = Integer.parseInt(tfDay.getText());} //최종 날짜 확정을 위해 입력값 정수로 전환해 받아오기 (vmonth, vday에 넣을 값)}
			id = main.getUserId(); //main에서 객체로 생성해놓은 로그인 정보 중 아이디 받아와 저장함
			
			if (pw < 0) {
				JOptionPane.showMessageDialog(null, "모든 값을 입력하세요.","Error!",JOptionPane.ERROR_MESSAGE);
			}else if (spb < 0) {
				JOptionPane.showMessageDialog(null, "모든 값을 입력하세요.","Error!",JOptionPane.ERROR_MESSAGE);	
			}else if (month<= 0||month>12) {
				JOptionPane.showMessageDialog(null, "모든 값을 입력하세요.","Error!",JOptionPane.ERROR_MESSAGE);		
			}else if (day <= 0||day>31) {
				JOptionPane.showMessageDialog(null, "모든 값을 입력하세요.","Error!",JOptionPane.ERROR_MESSAGE);		
			}else if(id ==""){
				JOptionPane.showMessageDialog(null, "로그인 후 이용하세요.","Error!",JOptionPane.ERROR_MESSAGE);
			}else {//모든 옵션을 선택하면서
				if(totalprice !=0) {//테이블이 비워져 있지 않을 때
				OrderOption orderOption = optionset(totalprice, pw, spb, month, day, pickCookielist); //tab2로 전달할 값들 세팅	
					if(compare(pickCookielist)==1){
						db.insertOrderList(orderOption, id);//주문 테이블에 정보 튜플 추가
						db.updateStorage(orderOption);//잔여 재고량 갱신 
						main.getTab2().gettingmd(id, month, day);
						JOptionPane.showMessageDialog(null, "신청 완료되었습니다.","Permit",JOptionPane.INFORMATION_MESSAGE);
				}
					else {
						JOptionPane.showMessageDialog(null, "품절 상품이 포함되어 있습니다.","Sorry",JOptionPane.ERROR_MESSAGE);
				}
					DefaultTableModel tableModel = new DefaultTableModel(columName,0); 
					table.setModel(tableModel);
					//테이블 초기화
					pickCookielist.clear();
					//담은 목록 초기화
					totalprice = 0;
					//총 가격 초기화
					total.setText(String.valueOf(totalprice));
					//가격 라벨 갱신
					}else{//테이블이 비워져 있는 경우
						JOptionPane.showMessageDialog(null, "주문 목록이 없습니다. ","Error!",JOptionPane.ERROR_MESSAGE);}
			}
		}
	}
	//테이블에 담은 쿠키 추가하는 method 정의 
	public void addCookies(MyCookie myCookie) {
		for(int i=0; i<pickCookielist.size(); i++) {
			if(pickCookielist.get(i).getName().equals(myCookie.getName())){//같은 제품 담을 시 누적되게
				pickCookielist.get(i).setCnt(pickCookielist.get(i).getCnt()+myCookie.getCnt());
				renderTable();//테이블 갱신
				return;
			}
		}
		pickCookielist.add(myCookie); //mycookie 객체 테이블에 담을 arraylist에 추가
		renderTable();//테이블 갱신
		for(MyCookie cookie : pickCookielist) { //cookielist 배열을 for문 돌려서 consol창에 실행되게(확인용)
			System.out.println(cookie.getName()+" "+cookie.getPrice()+" "+cookie.getCnt());
		}
	}
	//Orderoption에 예약 확정한 value들 저장해서 tab2에서 사용가능하게
	
	public OrderOption optionset (int total, int payment, int spb, int month, int day, ArrayList<MyCookie> cookielist) {
		
		OrderOption orderOption = new OrderOption();
		
		orderOption.setPayway(payment);
		orderOption.setShoppingbag(spb);
		orderOption.setMonth(month);
		orderOption.setDay(day);
		orderOption.setCookielist(cookielist);
		orderOption.setTotal(total);
		//각 value값들 세팅
		return orderOption;
		//tab2에서 저장한 값들 사용가능하게 값 저장한 객체를 반환
	}
	
	public int compare(ArrayList<MyCookie> list) {
		int k = 0;
		int i =0;
		while (i<list.size()) {
			if(list.get(i).getCnt()>db.checkonCnt(list.get(i).getCookie().getId())){ //현재 등록된 실시간 재고량보다 주문 재고량이 큰 경우
				//System.out.println("compare 함수 오류"+k);
				return 0; //초과하는 재고량 나왔을 경우-> 오류
			}
			i++; //리스트 안에 존재하는 모든 상품 검사
		}
		return 1; //재고량 다 괜찮을경우
	}

	//테이블 갱신 method
	private void renderTable() {
		DefaultTableModel tableModel = new DefaultTableModel(columName,0);
		totalprice = 0;//총액 초기화
		for (int i =0; i<pickCookielist.size();i++) { //담긴 쿠키가 있을 경우 테이블 생성
			String cookiename = pickCookielist.get(i).getName();
			int cookiecount = pickCookielist.get(i).getCnt();
			int cookieprice = pickCookielist.get(i).getPrice() * cookiecount;
			//이름, 수량, 쿠키 당 총 금액 출력
			totalprice += cookieprice; //최종 합계금액 계산
			Object[] data = {cookiename, cookiecount, cookieprice};
			//Object list 만들어서 테이블 모델에 추가
			tableModel.addRow(data);
		}
		table.setModel(tableModel);
		//수정한 테이블 모델로 테이블 갱신
		total.setText(String.valueOf(totalprice));
		//최종 값 갱신
	}
}
