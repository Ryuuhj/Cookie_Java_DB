package cookiesell;
import java.awt.*;
import javax.swing.*;

public class Tab1 extends JPanel{
	private Main main;
	private Tab1Left pn1;
	private Tab1Right pn2; //탭의 왼쪽, 오른쪽 분할해서 붙임
	
	public Tab1(Main main) {
		this.main = main; //Main을 매개변수로 참조 
		
		pn1 = new Tab1Left(); //Tab1Left 생성
		pn2 = new Tab1Right(main); //오른쪽 패널이 main을 경유해서 left tab의 메소드 사용할거라서 매개변수로 줌
		
		pn1.connectRight(pn2); //오른쪽 패널에 왼쪽 패널 쿠키 정보등 전달해 연결
		
		pn1.setLayout(null); //왼쪽 패널 레이아웃 안정함

		
		setLayout(new GridBagLayout());
		setBackground(Color.white);
		
		//왼쪽<-> 오른쪽 패널 비율 나누기 위해 그리드백 레이아웃
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.BOTH; //x,y축 다채움

        gbc.weightx=0.1; // 가로 비율 2:1
        gbc.weighty=0.1; // 세로 비율 1:1
        gbc.gridx=0;  
        gbc.gridy=0; 
        add(pn1,gbc); // 0,0 위치에 패널 배치

        gbc.weightx=0.2;
        gbc.weighty=0.1; // 세로 비율 1:1
        gbc.gridx=1;  
        gbc.gridy=0; 
        add(pn2,gbc); // 0,1 위치에 패널 배치
		
	}

	public Tab1Left getTab1Left() {
		return pn1; //Tab1에서 Tab1Left를 반환하는 method (다른 class에서 tab1left참조 -> Tab3에서 사용)
	}
}
