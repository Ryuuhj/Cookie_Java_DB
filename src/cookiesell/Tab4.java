package cookiesell;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class loginPanel extends JPanel{
	JTextField tfid, tfpassword; //로그인창 용
	JLabel lid, lpw;
	JPanel login, join, p1, p2, p3;
	Tab4 tab4; //여러 패널들의 전환이 일어나므로 tab4에 연동 시켜주기 위해 선언
	JButton logbtn, joinbtn, dropbtn;
	int success=-1; //추후 ConnectDB에서 회원가입, 로그인, 탈퇴 SQL문 수행 결과로 return될 값을 받을 변수, -1로 초기화
	
	public loginPanel(Tab4 tab4) {
		this.tab4 = tab4;
		setLayout(new GridLayout(3, 0));
		setSize(300,200);
		setLocation(400,100);
		

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		//패널 선언
		lid = new JLabel("ID");
		lpw = new JLabel("Password");
		//라벨 선언
		tfid = new JTextField(10);
		tfpassword = new JTextField(10);
		//로그인
		logbtn = new JButton("로그인");
		joinbtn = new JButton("회원가입");
		dropbtn = new JButton("회원탈퇴");
		//로그인 버튼
		p1.add(lid);
		p1.add(tfid);
		p2.add(lpw);
		p2.add(tfpassword);
		p3.add(logbtn);
		p3.add(joinbtn);
		p3.add(dropbtn);
		//로그인창
		add(p1);
		add(p2);
		add(p3);
		
		logbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				success = tab4.getmain().useDB().checking(tfid.getText(), tfpassword.getText()); //db에서 checking 메소드 사용-> 리턴값 success에 저장
				//로그인 성공했을 경우
				if (success == 1) {
					tab4.getmain().setUser(tfid.getText(), tfpassword.getText()); //maintab에서 통용될 user 객체에 정보 복사 (식별용)
					JOptionPane.showMessageDialog(null, "로그인 성공!", "Success", JOptionPane.PLAIN_MESSAGE);
					init(); //화면 초기화
					setVisible(false); //로그인 후 화면 안보이게
					
				}
				else if(success == 0){
					JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.","Fail",JOptionPane.ERROR_MESSAGE);
				}
				else if(success ==-1) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다.","Fail",JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "데이터베이스 접속 오류입니다.","Fail",JOptionPane.ERROR_MESSAGE);
				}
				init();
				
			}
		});
		joinbtn.addActionListener(new ActionListener() { //회원가입 버튼 눌렀을 경우
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tab4.change("joinPanel");	//회원 가입 패널로 넘어감
			}
		});
		dropbtn.addActionListener(new ActionListener() { //탈퇴 버튼 클릭시
			
			@Override
			public void actionPerformed(ActionEvent e) {
				success = tab4.getmain().useDB().dropAccount(tfid.getText(), tfpassword.getText()); //ConnectDB의 dropAccount method실행
				if (success == 1) { //삭제 성공시 1반환
					JOptionPane.showMessageDialog(null, "탈퇴 되었습니다.", "Success", JOptionPane.PLAIN_MESSAGE);
					init();
				}
				else{ //탈퇴 처리 실패 -> 정보 불일치
					JOptionPane.showMessageDialog(null, "입력정보가 일치하지 않습니다.","Fail",JOptionPane.ERROR_MESSAGE);
				}
				init();
			}
		});
	}
	public void init() {
		tfid.setText("");
		tfpassword.setText(null);
		success = -1; //성공 판단 여부 정수 초기화
		
	}
}
//회원가입 버튼 클릭시 넘어갈 회원가입 패널
class joinPanel extends JPanel{
	JTextField tfid, tfpassword,tfname, tftel, tfbirth; 
	JLabel lid, lpw,lname, ltel, lbirth;
	JPanel p1, p2, p3, p4, p5, p6;
	Tab4 tab4;
	JButton ok, cancel;
	User userjoin;
	int success=-1;
	
	public joinPanel(Tab4 tab4) {
		this.tab4 = tab4;
		userjoin = new User();
		setLayout(new GridLayout(6, 0));
		setSize(300,400);
		setLocation(500,100);
		
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		//패널 선언
		lid = new JLabel("ID");
		lpw = new JLabel("Password");
		lname = new JLabel("Name");
		ltel= new JLabel("Tel");
		lbirth = new JLabel("Birth(YYMMDD)");
		//라벨 선언
		tfid = new JTextField(10);
		tfpassword = new JTextField(10);
		tfname = new JTextField(10);
		tftel = new JTextField(15);
		tfbirth = new JTextField(6);
		//회원가입시 정보 입력할 곳
		
		ok = new JButton("가입하기");
		cancel = new JButton("취소");
		
		p1.add(lid);
		p1.add(tfid);
		p2.add(lpw);
		p2.add(tfpassword);
		p3.add(lname);
		p3.add(tfname);
		p4.add(ltel);
		p4.add(tftel);
		p5.add(lbirth);
		p5.add(tfbirth);
		p6.add(ok);
		p6.add(cancel);
		
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		add(p5);
		add(p6);
		
		ok.addActionListener(new ActionListener() { //확인 버튼에 대한 이벤트 리스너		
			@Override
			public void actionPerformed(ActionEvent e) {
				userjoin.joinsetting(tfid.getText(), tfpassword.getText(), tfname.getText(), tftel.getText(), tfbirth.getText());
				success = tab4.getmain().useDB().makeAccount(userjoin); //계정 만들기 시도
				if (success == 1) { //만들어진 계정 1개
					JOptionPane.showMessageDialog(null, "회원 가입이 완료되었습니다", "Success", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, "회원 가입에 실패했습니다. 다시 시도하세요","Fail",JOptionPane.ERROR_MESSAGE);
				}
				init();
				
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tab4.change("loignPanel");	
			}
		});
		
	}	
	public void init() { //입력창 초기화
		tfid.setText("");
		tfpassword.setText(null);
		tfname.setText("");
		tftel.setText("");
		tfbirth.setText("");
		success = -1;
	}
}

public class Tab4 extends JPanel {
	private Main main;
	public loginPanel loginpanel;
	public joinPanel joinpanel;

	public Tab4(Main main) {
		this.main = main;
		setLayout(new BorderLayout());
		setVisible(true);
		
		loginpanel = new loginPanel(this);
		loginpanel.setVisible(true);
		joinpanel = new joinPanel(this);
		joinpanel.setVisible(false);
		add(loginpanel,"Center");
		add(joinpanel,"Center");
		}
		
	public void change(String panelName) { //회원가입 <-> 로그인 창 버튼 통해서 넘나들기 위한 method
		if (panelName.equals("joinPanel")) {
				loginpanel.setVisible(false);
				joinpanel.setVisible(true);
				repaint();
		}
		else {
			loginpanel.setVisible(true);
			joinpanel.setVisible(false);
			repaint();
		}
	}
	public Main getmain() {
		return main;
	}	

}

