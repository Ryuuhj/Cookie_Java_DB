package cookiesell;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Tab1Left extends JPanel{
	//private Cookie cookie;
	private ConnectDB db = new ConnectDB();
	private Tab1Right rightpanel; //오른쪽 패널 참조
	JPanel leftP = new JPanel(); //버튼 붙일 패널
	private ArrayList<Cookie> cookieList = new ArrayList<Cookie>(); //cookiestorage에 존재하는 쿠키 정보 객체로 저장해 담을 arraylist
			
	JButton[] leftb = new JButton[15]; //패널 왼쪽에 뜰 쿠키 담을 버튼

	
	public Tab1Left(){
		
		leftP.setLayout(new GridLayout(15,0)); //패널 격자 생성 세로 5칸
		leftP.setBackground(Color.white);//배경색 설정
		leftP.setSize(300,700);
		leftP.setVisible(true);
		//배경 세팅
		db.renewal(cookieList); //db로부터 현재 쿠키 재고 받아와 갱신하는 함수
		
		for(int i=0; i<cookieList.size(); i++) { //왼쪽 버튼 생성
			Cookie cookie1 = cookieList.get(i); //리스트에서 쿠키 객체 하나씩 꺼내옴
			leftb[i] = new JButton(cookie1.getName()); //객체에 저장된 쿠키이름 꺼내서 버튼에 사용
			leftb[i].setSize(280,50);
			//쿠키 버튼들 생성
			leftb[i].addActionListener(new ActionListener() {//각 이벤트 리스너추가
			    public void actionPerformed(ActionEvent e) {
			    	//System.out.println(id);
			    	rightpanel.setSelectedCookieUI(cookie1);//오른쪽 패널의 선택 쿠키 UI세팅 method 실행
			    	rightpanel.setCookie(cookie1); //오른쪽 패널에 선택한 쿠키 정보 넘김
			    }
			});//한 줄에 이벤트 핸들러 statement

			leftP.add(leftb[i]); // 왼쪽 패널에 버튼들 넣기(격자 당 한개씩)
		
		}
		//왼편에 붙일 패널에 쿠키버튼 세팅 완료
		add(leftP); //세팅된 패널 붙이기
		
	}
	
	public void connectRight(Tab1Right panel) { //오른쪽 패널에서 쓸 setPanel 정의
		this.rightpanel = panel; //왼쪽 패널과 오른쪽 패널 서로 참조해 연결되게
	}

	public void renewLeftPanel() {//주문 후 변경된 db적용 위해 다시 정보 받아옴
		db.renewal(cookieList);
	}
	
	public void renderTab1Right() { //오른쪽 패널 랜더링 (선택에 따라 UI 바뀌도록)
		rightpanel.setSelectedCookieUI(cookieList.get(0)); //장바구니 탭에서 초기화 후 메인탭으로 돌아갔을 때 첫번째 쿠키 정보 띄움
	}

}
