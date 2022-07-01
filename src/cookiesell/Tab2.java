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
	Font f = new Font("����", Font.BOLD, 15);
	Font fbutton = new Font("����", Font.BOLD, 20);
	
	public Tab2(Main main) {
		this.main=main; 
		
		setBackground(Color.white);
		setLayout(null);
		
		check = new JPanel();
		
		check.setPreferredSize(new Dimension(800,500));
		//�ֹ�Ȯ�� �г� ������ ����
		check.setLayout(new GridLayout(5,0));//���� 5ĭ ���� ����
		check.setBorder(new TitledBorder(new LineBorder(Color.blue,3),"�ֹ�Ȯ��"));
		//�г� �׵θ� ����
		check.setBackground(bluee);
		//�г� ���� ����
		namelabel = new JLabel("�ֹ� ǰ�� ��: "+ cookiename+"...");
		timelabel =new JLabel("���೯¥: "+" "+month+"�� "+day+"�� ");
		baglabel = new JLabel("���ι� ����: "+shoppingbag);
		paylabel = new JLabel("���� ����(������) : "+payment);
		totallabel = new JLabel("�� �ݾ� : "+total +"�� ");
		//�ֹ�Ȯ�ο� �� ������Ʈ ����Ʈ ���� �ֱ� (�������� �� �ʱ�ȭ ���༭ ���빰�� �������� ǥ��)
		namelabel.setFont(f);
		timelabel.setFont(f);
		baglabel.setFont(f);
		paylabel.setFont(f);
		totallabel.setFont(f);
		//�� �� ��Ʈ �����ֱ�
		check.add(namelabel);
		check.add(timelabel);
		check.add(baglabel);
		check.add(paylabel);
		check.add(totallabel);
		check.setBounds(30, 10, 600, 600);
		//�� �� ������Ʈ �гο� ���ϱ�
		JButton confirm = new JButton("����");
		
		confirm.setFocusPainted(false); //focus�� �׵θ� ����
		confirm.setFont(fbutton); 
		confirm.setBackground(yellow);
		confirm.setBounds(650, 130, 200, 300);
		confirm.addActionListener (new ActionListener() {
			//�������̵� 
			public void actionPerformed(ActionEvent e) {
				fillOrder();// tab3���� �Ѱܹ��� id, month, day�� ���� ���� ���� �˻� �� ������
				render();
				
				JOptionPane.showMessageDialog(null, "���ŵǾ����ϴ�.","RENEW",JOptionPane.INFORMATION_MESSAGE);
					
			}});
		
		check.setVisible(true);
		add(check);
		add(confirm);
		
		
		
	}
	//method���� 
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

	//�� ä��� method
	public void render() {
		namelabel.setText("�ֹ� ǰ�� ��: "+ cookiename+"... ");
		timelabel.setText("���೯¥: "+" "+month+"�� "+day+"�� ");
		baglabel.setText("���ι� ����: "+shoppingbag);
		paylabel.setText("���� ����(������) : "+payment);
		totallabel.setText("�� �ݾ� : "+total +"�� ");
	}

}
