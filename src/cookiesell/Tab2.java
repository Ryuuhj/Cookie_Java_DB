package cookiesell;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Tab2 extends JPanel{
	private Main main;
	ConnectDB db = new ConnectDB();
	Tab4 tab4 = new Tab4(main);
	JPanel check, list; 
	Color bluee = new Color(204,229,255);
	Color yellow = new Color(255,242,204);

	JLabel namelabel, timelabel, baglabel, paylabel,totallabel;
	String payment,shoppingbag,cookiename,id = " ";
	int total = 0;
	int month, setmonth = 0;
	int day, setday = 0;
	Font f = new Font("굴림", Font.BOLD, 15);
	Font fbutton = new Font("굴림", Font.BOLD, 20);
	
	public Tab2(Main main) {
		this.main=main; 
		
		setBackground(Color.white);
		setLayout(null);
		
		check = new JPanel();
		
		check.setPreferredSize(new Dimension(800,500));
		//주문확인 패널 사이즈 고정
		check.setLayout(new GridLayout(5,0));//세로 5칸 격자 생성
		check.setBorder(new TitledBorder(new LineBorder(Color.blue,3),"주문확인"));
		//패널 테두리 설정
		check.setBackground(bluee);
		//패널 배경색 설정
		namelabel = new JLabel("주문 품목 명: "+ cookiename+"...");
		timelabel =new JLabel("예약날짜: "+" "+month+"월 "+day+"일 ");
		baglabel = new JLabel("쇼핑백 여부: "+shoppingbag);
		paylabel = new JLabel("결제 수단(선결제) : "+payment);
		totallabel = new JLabel("총 금액 : "+total +"원 ");
		//주문확인에 들어갈 컴포넌트 디폴트 만들어서 넣기 (변수선언 시 초기화 해줘서 내용물은 공백으로 표시)
		namelabel.setFont(f);
		timelabel.setFont(f);
		baglabel.setFont(f);
		paylabel.setFont(f);
		totallabel.setFont(f);
		//각 라벨 폰트 맟춰주기
		check.add(namelabel);
		check.add(timelabel);
		check.add(baglabel);
		check.add(paylabel);
		check.add(totallabel);
		check.setBounds(30, 10, 600, 600);
		//각 라벨 컴포넌트 패널에 더하기
		JButton confirm = new JButton("갱신");
		
		confirm.setFocusPainted(false); //focus시 테두리 없앰
		confirm.setFont(fbutton); 
		confirm.setBackground(yellow);
		confirm.setBounds(650, 130, 200, 300);
		confirm.addActionListener (new ActionListener() {
			//오버라이드 
			public void actionPerformed(ActionEvent e) {
				fillOrder();// tab3에서 넘겨받은 id, month, day를 통해 예약 정보 검색 후 갱신함
				render();
				
				JOptionPane.showMessageDialog(null, "갱신되었습니다.","RENEW",JOptionPane.INFORMATION_MESSAGE);
					
			}});
		
		check.setVisible(true);
		add(check);
		add(confirm);
		
		
		
	}
	//method정의 
	public void gettingmd(String C_id, int m, int d) {
		id = C_id;
		setmonth = m;
		setday = d;
	}
	public void fillOrder() {
		Info info = db.getInfo(id,setmonth,setday);
		cookiename = info.getItem_name();
		total = info.getTotal();
		month = info.getMonth();
		day = info.getDay();
		payment = info.getPayment();
		shoppingbag = info.getShoppingbag();
		
		render();
		
	}

	//라벨 채출력 method
	public void render() {
		namelabel.setText("주문 품목 명: "+ cookiename+"... ");
		timelabel.setText("예약날짜: "+" "+month+"월 "+day+"일 ");
		baglabel.setText("쇼핑백 여부: "+shoppingbag);
		paylabel.setText("결제 수단(선결제) : "+payment);
		totallabel.setText("총 금액 : "+total +"원 ");
	}

}
