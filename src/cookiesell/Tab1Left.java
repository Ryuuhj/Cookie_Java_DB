package cookiesell;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Tab1Left extends JPanel{
	//private Cookie cookie;
	private ConnectDB db = new ConnectDB();
	private Tab1Right rightpanel; //������ �г� ����
	JPanel leftP = new JPanel(); //��ư ���� �г�
	private ArrayList<Cookie> cookieList = new ArrayList<Cookie>(); //cookiestorage�� �����ϴ� ��Ű ���� ��ü�� ������ ���� arraylist
			
	JButton[] leftb = new JButton[15]; //�г� ���ʿ� �� ��Ű ���� ��ư

	
	public Tab1Left(){
		
		leftP.setLayout(new GridLayout(15,0)); //�г� ���� ���� ���� 5ĭ
		leftP.setBackground(Color.white);//���� ����
		leftP.setSize(300,700);
		leftP.setVisible(true);
		//��� ����
		db.renewal(cookieList); //db�κ��� ���� ��Ű ��� �޾ƿ� �����ϴ� �Լ�
		
		for(int i=0; i<cookieList.size(); i++) { //���� ��ư ����
			Cookie cookie1 = cookieList.get(i); //����Ʈ���� ��Ű ��ü �ϳ��� ������
			leftb[i] = new JButton(cookie1.getName()); //��ü�� ����� ��Ű�̸� ������ ��ư�� ���
			leftb[i].setSize(280,50);
			//��Ű ��ư�� ����
			leftb[i].addActionListener(new ActionListener() {//�� �̺�Ʈ �������߰�
			    public void actionPerformed(ActionEvent e) {
			    	//System.out.println(id);
			    	rightpanel.setSelectedCookieUI(cookie1);//������ �г��� ���� ��Ű UI���� method ����
			    	rightpanel.setCookie(cookie1); //������ �гο� ������ ��Ű ���� �ѱ�
			    }
			});//�� �ٿ� �̺�Ʈ �ڵ鷯 statement

			leftP.add(leftb[i]); // ���� �гο� ��ư�� �ֱ�(���� �� �Ѱ���)
		
		}
		//���� ���� �гο� ��Ű��ư ���� �Ϸ�
		add(leftP); //���õ� �г� ���̱�
		
	}
	
	public void connectRight(Tab1Right panel) { //������ �гο��� �� setPanel ����
		this.rightpanel = panel; //���� �гΰ� ������ �г� ���� ������ ����ǰ�
	}

	public void renewLeftPanel() {//�ֹ� �� ����� db���� ���� �ٽ� ���� �޾ƿ�
		db.renewal(cookieList);
	}
	
	public void renderTab1Right() { //������ �г� ������ (���ÿ� ���� UI �ٲ��)
		rightpanel.setSelectedCookieUI(cookieList.get(0)); //��ٱ��� �ǿ��� �ʱ�ȭ �� ���������� ���ư��� �� ù��° ��Ű ���� ���
	}

}
