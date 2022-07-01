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

public class Tab3 extends JPanel implements ItemListener { //JPanel ��ӹް� ItemListener ���� ���� implements

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
	Font font = new Font("����", Font.BOLD, 20); //�� �ݾ� ǥ���� ��Ʈ
	String id="";
	
	String[] columName = {"��ǰ��", "����", "�ݾ�"};//���̺� ��ܿ� �� ColumName
	int pw, spb = -1; //���� ���� �Ǵ��ϱ� ���� ����(+ ��� �� �� ����)
	int month, day =0; //���� ����
	JLabel total;
	int totalprice;//���̺��� ���� ����� �� �ݾ� ���� ����
	
	private ArrayList<MyCookie> pickCookielist = new ArrayList<>();
	//���� �� ��Ű�� ���� ������ �迭�� ����-> ���̺� ���� ���� arraylist ����(ũ�� ������, �߰�, ���� ����)

	
	public Tab3(Main main) {//��ü ������ ����
		this.main = main; //Main �г� ������ �ٸ� tab�� �����ϱ� ���� ����

		setLayout(new GridLayout(2,0)); //���̺�/�ɼ� ���� �κ� ũ�� 2������ ������ 0*2
		
		//Table ���� part
		tablepanel = new JPanel(); //���̺� ���� �г� ����
		tablepanel.setLayout(new BorderLayout());//���̾ƿ� ����
		DefaultTableModel tableModel = new DefaultTableModel(columName,0); //���̺� �����ϱ� ���� ���̺� �� ��� (����Ʈ�� value���� �������)
		table = new JTable(tableModel); //���̺� �� �̿��� ���̺� ����
		
		JScrollPane scp = new JScrollPane(table);//���̺� ���� ��ũ�� �г� ����
		tablepanel.add("Center",scp); //��ũ�� �г� ���̺� ���� �г��� Center�� ���̱�
		clear =new JButton("�ʱ�ȭ"); //���̺� �ʱ�ȭ ��ų �ʱ�ȭ ��ư ����
		
		clear.addActionListener(new ActionListener() { //�ʱ�ȭ��ư�� �׼� ������ ����
			//�׼� �̺�Ʈ ������ override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<pickCookielist.size(); i++) { //�����鼭 ���ҵ� ��� �ٽ� �ǵ����� ���� for�� ������
					Cookie cookie = pickCookielist.get(i).getCookie(); //arraylist�� ��� ������ �ε������� get�ؼ� cookie ��ü�� �ο�
					cookie.setcnt(cookie.getcnt()+pickCookielist.get(i).getCnt()); //arraylist�� ��� ������ �̿��� ����� �ٽ� ����������ŭ ���ؼ� ���󺹱� ��Ű��
				}//��Ű�� ��� ���� �Ϸ�
				main.getTab1().getTab1Left().renderTab1Right();//������ ���� Tab1 ������ �ٽ� ���� (���� �� �� 1���� ���� �� �� ������ �� ���� �޼ҵ�ȣ��)
				table.setModel(tableModel); //���̺� ����
				pickCookielist.clear(); //����Ʈ �ʱ�ȭ
				totalprice = 0;//�� �ݾ� 0���� �ʱ�ȭ
				total.setText(String.valueOf(totalprice)); //�ϴܿ� ������ �� �ݾ� �󺧵� 0���� ����
			}
		});// statement�� �ʱ�ȭ ��ư�� ���� �׼� �̺�Ʈ �ڵ鸵 method overriding
		clear.setBackground(bluee);
		tablepanel.add("South",clear); //�ʱ�ȭ ��ư ���г� �ϴܿ� �߰�
		add(tablepanel); //�� �г� ���ϱ�	
		
		//���̺� ��Ÿ ����
		scp.getViewport().setBackground(Color.WHITE);//���̺� ���ºκ� ȸ��->���
		table.setAutoCreateColumnsFromModel(false);
		//��� �÷��� ���� ���� -> �ڵ����� ����
		DefaultTableCellRenderer dcr = new DefaultTableCellRenderer();
		dcr.setHorizontalAlignment(SwingConstants.CENTER);
		//���̺� ���� ��� ����
		
		TableColumnModel tcm = table.getColumnModel();
		for (int i=0;i<tcm.getColumnCount();i++) {
			tcm.getColumn(i).setCellRenderer(dcr);
		} //��� ���� ������ ������ �緣����
	
		//�г� �Ʒ��κ� ���� 
		JPanel bottom = new JPanel();
		
		bottom.setLayout(new GridLayout(0,2));
		//�Ʒ��κ� ���� ũ�� �¿� ���� ����
		JPanel bottomP = new JPanel();
		
		bottomP.setLayout(new GridLayout(4,0));
		//���� �гο� �� �г� ����, ���� 4��
		JPanel bottomP1 = new JPanel();
		JPanel bottomP2 = new JPanel();
		JPanel bottomP3 = new JPanel();
		bottomP1.setBackground(yellow);
		bottomP2.setBackground(yellow);
		bottomP3.setBackground(yellow);
		
		//�� ������ư�� �� �г� ����
		
		//1) ���� ���� �г�
		JPanel g1P = new JPanel();
		ButtonGroup g1 = new ButtonGroup();//���� ���� ������ ��ư �׷� ����
		pay1 = new JRadioButton("�ſ�ī��", false);
		pay2 = new JRadioButton("īī������", false);
		pay3 = new JRadioButton("������", false);
		pay1.setBackground(yellow);
		pay2.setBackground(yellow);
		pay3.setBackground(yellow);
		//��ư ����
		pay1.addItemListener(this);
		pay2.addItemListener(this);
		pay3.addItemListener(this);
		//�� ��ư�� ������ ������ ���(�ش� Ŭ���� ���� method�� override)
		g1P.add(new JLabel("�������            "));
		//��ư �׷쿡 ������Ʈ �߰�
		g1.add(pay1);
		g1.add(pay2);
		g1.add(pay3);
		//�гο� ������Ʈ �߰�
		g1P.add(pay1);
		g1P.add(pay2);
		g1P.add(pay3);
		//�����ο� �������� �г� �߰� (1��° ĭ)
		bottomP1.add(g1P);
		//2) ���ι� ���� ���� �г�
		JPanel g2P = new JPanel();//���� ����
		ButtonGroup g2 = new ButtonGroup();
		spbag1 = new JRadioButton("Yes", false);
		spbag2= new JRadioButton("No", false);
		spbag1.setBackground(yellow);
		spbag2.setBackground(yellow);
		spbag1.addItemListener(this);
		spbag2.addItemListener(this);
		g2P.add(new JLabel("���ι� ����         "));
		
		g2.add(spbag1);
		g2.add(spbag2);
		g2P.add(spbag1);
		g2P.add(spbag2);
		
		bottomP2.add(g2P);
		//3) �湮 ��¥ ���� �г�
		JPanel g3P = new JPanel();	
		tfMonth = new JTextField(5);
		tfDay = new JTextField(5);
		
		g3P.add(new JLabel("�湮 ��¥ ����       "));
		g3P.add(tfMonth);
		g3P.add(new JLabel(" / "));
		g3P.add(tfDay);
		bottomP3.add(g3P);
		//���� ��ģ 3�� �г� ���� ���̱�
		
		//4) �� �ݾ� �ȳ� �г�
		JPanel g4P = new JPanel();
		JLabel front = new JLabel("�� �ݾ�: ");
		total = new JLabel("0");//����Ʈ ��
		JLabel back = new JLabel(" ��");
		//�г� 4���� ������Ʈ�� ���̱�
		g4P.add(front);
		g4P.add(total);
		g4P.add(back);
		front.setFont(font);
		total.setFont(font);
		back.setFont(font);
		//�ϴ� ���� ������Ʈ ���� �Ϸ�
		g1P.setBackground(yellow);
		g2P.setBackground(yellow);
		g3P.setBackground(yellow);
		g4P.setBackground(yellow);
		front.setBackground(yellow);
		back.setBackground(yellow);
		//���� ����
		bottomP.add(bottomP1);
		bottomP.add(bottomP2);
		bottomP.add(bottomP3);
		bottomP.add(g4P);
		
		//���� ������Ʈ ���� �Ϸ�
		JButton confirm = new JButton(butdefault);
		confirm.setRolloverIcon(buthover);
		confirm.setBorderPainted(false);//��ư �׵θ� ���� ����
		
		confirm.addActionListener(new Confirm());
		
		
		bottom.add(bottomP);
		bottom.add(confirm);
		add(bottom);
	}
	public void itemStateChanged (ItemEvent e) {
		Object o = e.getSource();
		if(o == pay1) {
			pw = 1; //�������� ���� ���ںο�-> ���߿� ����Ҷ� �����ؼ� ���
		}else if(o==pay2) {
			pw = 2;
		}else if(o==pay3) {
			pw = 3;
		}else if(o==spbag1) {
			spb = 0;
		}else if(o==spbag2) {
			spb = 1;}
	}
	//private ��Ű arraylist�� get�ؼ� �̰��� ������ arraylist�� �����ϱ� ���� method ������ ����
	public ArrayList<MyCookie> getCookielist() {
		return pickCookielist;
	}
	//���� Ȯ�� ��ư Ŭ���� �߻��ϴ� �̺�Ʈ ó�� ���� ������ inner class
	private class Confirm implements ActionListener { 
		
		public void actionPerformed (ActionEvent e) {
			//orderoption ���� ���ϸ� �ֹ� �ȳѾ�� ��
			if(tfMonth.getText().isEmpty() || tfDay.getText().isEmpty()) {
				month=-1;
				day=-1;} //null�� ��� ��� �Ұ��� �ο�
			else {
				month = Integer.parseInt(tfMonth.getText()); //null�� ��� ���� �߻� ����
				day = Integer.parseInt(tfDay.getText());} //���� ��¥ Ȯ���� ���� �Է°� ������ ��ȯ�� �޾ƿ��� (vmonth, vday�� ���� ��)}
			id = main.getUserId(); //main���� ��ü�� �����س��� �α��� ���� �� ���̵� �޾ƿ� ������
			
			if (pw < 0) {
				JOptionPane.showMessageDialog(null, "��� ���� �Է��ϼ���.","Error!",JOptionPane.ERROR_MESSAGE);
			}else if (spb < 0) {
				JOptionPane.showMessageDialog(null, "��� ���� �Է��ϼ���.","Error!",JOptionPane.ERROR_MESSAGE);	
			}else if (month<= 0||month>12) {
				JOptionPane.showMessageDialog(null, "��� ���� �Է��ϼ���.","Error!",JOptionPane.ERROR_MESSAGE);		
			}else if (day <= 0||day>31) {
				JOptionPane.showMessageDialog(null, "��� ���� �Է��ϼ���.","Error!",JOptionPane.ERROR_MESSAGE);		
			}else if(id ==""){
				JOptionPane.showMessageDialog(null, "�α��� �� �̿��ϼ���.","Error!",JOptionPane.ERROR_MESSAGE);
			}else {//��� �ɼ��� �����ϸ鼭
				if(totalprice !=0) {//���̺��� ����� ���� ���� ��
				OrderOption orderOption = optionset(totalprice, pw, spb, month, day, pickCookielist); //tab2�� ������ ���� ����	
					if(compare(pickCookielist)==1){
						db.insertOrderList(orderOption, id);//�ֹ� ���̺� ���� Ʃ�� �߰�
						db.updateStorage(orderOption);//�ܿ� ��� ���� 
						main.getTab2().gettingmd(id, month, day);
						JOptionPane.showMessageDialog(null, "��û �Ϸ�Ǿ����ϴ�.","Permit",JOptionPane.INFORMATION_MESSAGE);
				}
					else {
						JOptionPane.showMessageDialog(null, "ǰ�� ��ǰ�� ���ԵǾ� �ֽ��ϴ�.","Sorry",JOptionPane.ERROR_MESSAGE);
				}
					DefaultTableModel tableModel = new DefaultTableModel(columName,0); 
					table.setModel(tableModel);
					//���̺� �ʱ�ȭ
					pickCookielist.clear();
					//���� ��� �ʱ�ȭ
					totalprice = 0;
					//�� ���� �ʱ�ȭ
					total.setText(String.valueOf(totalprice));
					//���� �� ����
					}else{//���̺��� ����� �ִ� ���
						JOptionPane.showMessageDialog(null, "�ֹ� ����� �����ϴ�. ","Error!",JOptionPane.ERROR_MESSAGE);}
			}
		}
	}
	//���̺� ���� ��Ű �߰��ϴ� method ���� 
	public void addCookies(MyCookie myCookie) {
		for(int i=0; i<pickCookielist.size(); i++) {
			if(pickCookielist.get(i).getName().equals(myCookie.getName())){//���� ��ǰ ���� �� �����ǰ�
				pickCookielist.get(i).setCnt(pickCookielist.get(i).getCnt()+myCookie.getCnt());
				renderTable();//���̺� ����
				return;
			}
		}
		pickCookielist.add(myCookie); //mycookie ��ü ���̺� ���� arraylist�� �߰�
		renderTable();//���̺� ����
		for(MyCookie cookie : pickCookielist) { //cookielist �迭�� for�� ������ consolâ�� ����ǰ�(Ȯ�ο�)
			System.out.println(cookie.getName()+" "+cookie.getPrice()+" "+cookie.getCnt());
		}
	}
	//Orderoption�� ���� Ȯ���� value�� �����ؼ� tab2���� ��밡���ϰ�
	
	public OrderOption optionset (int total, int payment, int spb, int month, int day, ArrayList<MyCookie> cookielist) {
		
		OrderOption orderOption = new OrderOption();
		
		orderOption.setPayway(payment);
		orderOption.setShoppingbag(spb);
		orderOption.setMonth(month);
		orderOption.setDay(day);
		orderOption.setCookielist(cookielist);
		orderOption.setTotal(total);
		//�� value���� ����
		return orderOption;
		//tab2���� ������ ���� ��밡���ϰ� �� ������ ��ü�� ��ȯ
	}
	
	public int compare(ArrayList<MyCookie> list) {
		int k = 0;
		int i =0;
		while (i<list.size()) {
			if(list.get(i).getCnt()>db.checkonCnt(list.get(i).getCookie().getId())){ //���� ��ϵ� �ǽð� ������� �ֹ� ����� ū ���
				//System.out.println("compare �Լ� ����"+k);
				return 0; //�ʰ��ϴ� ��� ������ ���-> ����
			}
			i++; //����Ʈ �ȿ� �����ϴ� ��� ��ǰ �˻�
		}
		return 1; //��� �� ���������
	}

	//���̺� ���� method
	private void renderTable() {
		DefaultTableModel tableModel = new DefaultTableModel(columName,0);
		totalprice = 0;//�Ѿ� �ʱ�ȭ
		for (int i =0; i<pickCookielist.size();i++) { //��� ��Ű�� ���� ��� ���̺� ����
			String cookiename = pickCookielist.get(i).getName();
			int cookiecount = pickCookielist.get(i).getCnt();
			int cookieprice = pickCookielist.get(i).getPrice() * cookiecount;
			//�̸�, ����, ��Ű �� �� �ݾ� ���
			totalprice += cookieprice; //���� �հ�ݾ� ���
			Object[] data = {cookiename, cookiecount, cookieprice};
			//Object list ���� ���̺� �𵨿� �߰�
			tableModel.addRow(data);
		}
		table.setModel(tableModel);
		//������ ���̺� �𵨷� ���̺� ����
		total.setText(String.valueOf(totalprice));
		//���� �� ����
	}
}
