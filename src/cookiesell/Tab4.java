package cookiesell;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class loginPanel extends JPanel{
	JTextField tfid, tfpassword; //�α���â ��
	JLabel lid, lpw;
	JPanel login, join, p1, p2, p3;
	Tab4 tab4; //���� �гε��� ��ȯ�� �Ͼ�Ƿ� tab4�� ���� �����ֱ� ���� ����
	JButton logbtn, joinbtn, dropbtn;
	int success=-1; //���� ConnectDB���� ȸ������, �α���, Ż�� SQL�� ���� ����� return�� ���� ���� ����, -1�� �ʱ�ȭ
	
	public loginPanel(Tab4 tab4) {
		this.tab4 = tab4;
		setLayout(new GridLayout(3, 0));
		setSize(300,200);
		setLocation(400,100);
		

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		//�г� ����
		lid = new JLabel("ID");
		lpw = new JLabel("Password");
		//�� ����
		tfid = new JTextField(10);
		tfpassword = new JTextField(10);
		//�α���
		logbtn = new JButton("�α���");
		joinbtn = new JButton("ȸ������");
		dropbtn = new JButton("ȸ��Ż��");
		//�α��� ��ư
		p1.add(lid);
		p1.add(tfid);
		p2.add(lpw);
		p2.add(tfpassword);
		p3.add(logbtn);
		p3.add(joinbtn);
		p3.add(dropbtn);
		//�α���â
		add(p1);
		add(p2);
		add(p3);
		
		logbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				success = tab4.getmain().useDB().checking(tfid.getText(), tfpassword.getText()); //db���� checking �޼ҵ� ���-> ���ϰ� success�� ����
				//�α��� �������� ���
				if (success == 1) {
					tab4.getmain().setUser(tfid.getText(), tfpassword.getText()); //maintab���� ���� user ��ü�� ���� ���� (�ĺ���)
					JOptionPane.showMessageDialog(null, "�α��� ����!", "Success", JOptionPane.PLAIN_MESSAGE);
					init(); //ȭ�� �ʱ�ȭ
					setVisible(false); //�α��� �� ȭ�� �Ⱥ��̰�
					
				}
				else if(success == 0){
					JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.","Fail",JOptionPane.ERROR_MESSAGE);
				}
				else if(success ==-1) {
					JOptionPane.showMessageDialog(null, "�������� �ʴ� ���̵��Դϴ�.","Fail",JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "�����ͺ��̽� ���� �����Դϴ�.","Fail",JOptionPane.ERROR_MESSAGE);
				}
				init();
				
			}
		});
		joinbtn.addActionListener(new ActionListener() { //ȸ������ ��ư ������ ���
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tab4.change("joinPanel");	//ȸ�� ���� �гη� �Ѿ
			}
		});
		dropbtn.addActionListener(new ActionListener() { //Ż�� ��ư Ŭ����
			
			@Override
			public void actionPerformed(ActionEvent e) {
				success = tab4.getmain().useDB().dropAccount(tfid.getText(), tfpassword.getText()); //ConnectDB�� dropAccount method����
				if (success == 1) { //���� ������ 1��ȯ
					JOptionPane.showMessageDialog(null, "Ż�� �Ǿ����ϴ�.", "Success", JOptionPane.PLAIN_MESSAGE);
					init();
				}
				else{ //Ż�� ó�� ���� -> ���� ����ġ
					JOptionPane.showMessageDialog(null, "�Է������� ��ġ���� �ʽ��ϴ�.","Fail",JOptionPane.ERROR_MESSAGE);
				}
				init();
			}
		});
	}
	public void init() {
		tfid.setText("");
		tfpassword.setText(null);
		success = -1; //���� �Ǵ� ���� ���� �ʱ�ȭ
		
	}
}
//ȸ������ ��ư Ŭ���� �Ѿ ȸ������ �г�
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
		//�г� ����
		lid = new JLabel("ID");
		lpw = new JLabel("Password");
		lname = new JLabel("Name");
		ltel= new JLabel("Tel");
		lbirth = new JLabel("Birth(YYMMDD)");
		//�� ����
		tfid = new JTextField(10);
		tfpassword = new JTextField(10);
		tfname = new JTextField(10);
		tftel = new JTextField(15);
		tfbirth = new JTextField(6);
		//ȸ�����Խ� ���� �Է��� ��
		
		ok = new JButton("�����ϱ�");
		cancel = new JButton("���");
		
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
		
		ok.addActionListener(new ActionListener() { //Ȯ�� ��ư�� ���� �̺�Ʈ ������		
			@Override
			public void actionPerformed(ActionEvent e) {
				userjoin.joinsetting(tfid.getText(), tfpassword.getText(), tfname.getText(), tftel.getText(), tfbirth.getText());
				success = tab4.getmain().useDB().makeAccount(userjoin); //���� ����� �õ�
				if (success == 1) { //������� ���� 1��
					JOptionPane.showMessageDialog(null, "ȸ�� ������ �Ϸ�Ǿ����ϴ�", "Success", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, "ȸ�� ���Կ� �����߽��ϴ�. �ٽ� �õ��ϼ���","Fail",JOptionPane.ERROR_MESSAGE);
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
	public void init() { //�Է�â �ʱ�ȭ
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
		
	public void change(String panelName) { //ȸ������ <-> �α��� â ��ư ���ؼ� �ѳ���� ���� method
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

