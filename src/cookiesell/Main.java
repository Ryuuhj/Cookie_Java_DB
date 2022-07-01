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
		frame.setTitle("��Ű ���� �ý���"); // ���α׷� �̸� ����
		frame.setSize(1100,700); //������ ������ ����
		//frame.setResizable(false); //������ ������ �Ұ���
		frame.setLocationRelativeTo(null); //������ �� ����� ����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ ��ư ������ â ����
		JTabbedPane maintab = new JTabbedPane();//�� �г� ����
		tab1 = new Tab1(this); //�� 1-����ȭ�� ����
		tab2 = new Tab2(this); //�� 2 - ���������� ����
		tab3 = new Tab3(this); //�� 3 - ��ٱ��� ����
		tab4 = new Tab4(this);
		
		maintab.addTab("����ȭ��",tab1);
		maintab.addTab("��ٱ���",tab3);
		maintab.addTab("����������",tab2);//�����ǿ� ���̱�
		maintab.addTab("�α���", tab4);
		
        frame.getContentPane().add(maintab); //�� �����ӿ� ���̱�
        frame.setVisible(true); // â ���̰��ϱ�
        
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
	public void setUser(String id, String pswd) { //�α��� ��, ��� �ǿ��� id�� ���� ���� �̿� �����ϰ� main���� ������ ������ �� �����ϴ� ���
		userlog.setuser(id, pswd);
		//System.out.println(userlog.getId()+ userlog.getPswd()); //������
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
