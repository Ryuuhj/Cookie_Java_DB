package cookiesell;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Tab1Right extends JPanel{
	private Main main;
	ConnectDB db = new ConnectDB();
	private JLabel pic; //top패널 들어갈 이미지
	private JLabel[] imagelabel = new JLabel[4]; //bottom패널 들어갈 정보 라벨 
	private JLabel need; //희망 구매 수량 들어갈 라벨
	private Cookie cookie; //쿠키 class 객체 선언
	JPanel top, bottom; 
	public int a = 0; //재고 이벤트때 사용
	private int needn = 1;//수량 조절할 숫자 (희망 구매 수량)

	Font f =new Font("굴림", Font.BOLD,20); //라벨 폰트
	Color btcolor = new Color(255,255,204);//하단 패널에 넣을 색 정의
	
	
	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	} //왼쪽 패널로부터 쿠키 정보 받아옴
	public Tab1Right(Main main) { //메인 클래스 통해서 다른 클래스 참조하기 위해 main 인자로
		this.main = main; 
		setLayout(new GridLayout(2,0));	// 크게 2부분으로 분할
		setPreferredSize(new Dimension(300,800)); //오른쪽 패널 사이즈 정하기
		setMaximumSize(this.getPreferredSize());//사이즈 고정
		setMinimumSize(getPreferredSize());
		//글자크기에 따른 패널 사이즈 고정
		
		top = new JPanel(); //패널 위 영역에 붙일 패널(사진 담을)
		bottom = new JPanel(); //패널 아래 영역에 붙일 패널(정보담음)
		bottom.setLayout(new GridLayout(5,0)); //아래 정보는 5줄 담기 위해 격자 생성
		//top패널에 들어갈 컴포넌트 설정
		pic = new JLabel(); //사진 들어갈 라벨 생성
		pic.setHorizontalAlignment(JLabel.CENTER);//라벨 중앙 정렬
		pic.setBorder(BorderFactory.createEmptyBorder(50,0,0,0)); //위쪽 안쪽 여백 50
		top.setBackground(Color.white); //위 패널 배경 색 흰색
		top.add(pic); //이미지 들어갈 라벨 세팅
		
		//bottom 패널에 들어갈 컴포넌트 설정
		bottom.setBackground(btcolor); //아래 패널 배경색 설정
		for (int i =0;i<4;i++) { //for문으로 아래 들어갈 정보들 들어갈 라벨 세팅
			imagelabel[i] = new JLabel();
			imagelabel[i].setHorizontalAlignment(JLabel.CENTER);
			imagelabel[i].setFont(f);
			bottom.add(imagelabel[i]); //하단 패널에 생성한대로 라벨 넣기
		}
		
		JPanel combobox = new JPanel(); //수량 들어갈 패널 지정
		combobox.setBackground(new Color(204,229,255));//수량 패널 색 지정
		combobox.setLayout(new FlowLayout());
		
		JButton minusButton = new JButton("-"); //마이너스 버튼생성
		minusButton.setFocusPainted(false);
        minusButton.setBounds(20, 390, 50, 30); //마이너스 버튼 위치 지정
        combobox.add(minusButton);//수량 패널에 부착
        minusButton.setBackground(Color.white); //버튼 배경색
		
        need = new JLabel("1"); //담을 수량 표시할 라벨 (기본: 1)
        need.setBounds(80, 390, 80, 30);//위치 지정
        combobox.add(need); //마이너스버튼 옆에 담기
        //담을 수량 조절 컴포넌트 구성
        JButton plusButton = new JButton("+"); //플러스 버튼
		plusButton.setFocusPainted(false);
		plusButton.setBounds(100, 390, 50, 30);
		combobox.add(plusButton);
        plusButton.setBackground(Color.white);
       //담기 버튼 생성
        JButton ok = new JButton("담기");
		ok.setFocusPainted(false);
		combobox.add(ok);
		ok.addActionListener(new ActionListener() { //담기 눌렀을 때 이벤트 리스너
			public void actionPerformed(ActionEvent e) {
				if (cookie.getcnt() <= 0) { //cookie의 변수 중 재고 변수 체크-> 0보다 작거나 같으면(품절)
					JOptionPane.showMessageDialog(null, "재고 부족.","Sorry",JOptionPane.WARNING_MESSAGE);
					//재고 부족 메시지 -> 아무일도 발생X
				}else {//재고 남아있으면
					main.getTab3().addCookies(new MyCookie(cookie, cookie.getName(), cookie.getPrice(), needn));
					//메인 프레임의 getTab3 function을 이용해 Tab3클래스의 addCookies method 사용
					//(담은 쿠키 리스트에 해당 쿠키 정보, 수량 추가 MyCookie 객체로 만들어 추가)
					cookie.setcnt(cookie.getcnt()-needn);
					//재고 쿠키 잔여량 갱신 (기존 개수-담은 개수)
					if(cookie.getcnt() <= 0) { //담고 나서 쿠키 잔여수량 0일때
						imagelabel[3].setText("재고 소진"); //재고량 라벨-> 재고 소진으로 전환
						needn = 0; // 담기 수량 0으로 지정(못담게)
						need.setText("X"); //담기 개수 표시 X로 표시
					}else {
						imagelabel[3].setText("수량: "+ cookie.getcnt()+"개"); //갱신된 남은 개수 표시
						needn = 1; //담기 수량 초기화
						need.setText(Integer.toString(needn)); //담기 개수 표시 1로 초기화
					}
				}
			}
		}); // statement에 담기 버튼에 대한 액션 이벤트 핸들링 method overriding
        
        plusButton.addActionListener(new ActionListener() { //플러스버튼 이벤트 리스너
			public void actionPerformed(ActionEvent e) {
				if (needn >= cookie.getcnt()) { //희망 개수가 재고를 초과할 때
					JOptionPane.showMessageDialog(null, "재고 수량 이상 구매할 수 없습니다.","Error!",JOptionPane.ERROR_MESSAGE);
					//재고 부족 메시지 표시 -> 아무 일 X
				}else {
					needn++; //담을 개수가 재고 이하일때 -> 무사히 +1
					need.setText(Integer.toString(needn)); //+1된 개수로 갱신
				}
			}
		}); // statement에 플러스 버튼에 대한 액션 이벤트 핸들링 method overriding
        
        minusButton.addActionListener(new ActionListener() {//마이너스 버튼 이벤트 리스너
			public void actionPerformed(ActionEvent e) {
				if(needn <= 1) { //개수가 1개 미만으로 떨어질 때
					JOptionPane.showMessageDialog(null, "구매 가능 최소수량은 1 개입니다.","Error!",JOptionPane.ERROR_MESSAGE);
					//최소수량 알림 메시지 표시-> 아무 일 발생X
				}else { //1개 초과인 상태에서 마이너스 작동 시켰을 때
					needn--; //담기 개수 -1 무사 작동
					need.setText(Integer.toString(needn)); //-1된 개수 갱신
				}
			}
		});
        
		bottom.add(combobox); //수량 조절 패널 아래 패널에 붙이기		
		add(top);
		add(bottom);
		//마지막으로 패널들 최종적으로 붙이기

	}
	public void setSelectedCookieUI(Cookie cookie) { //Tab1Right class의 method-> 오른 패널 쿠키 UI만드는 메소드 (왼패널에서 작동)
		pic.setIcon(new ImageIcon("img/"+cookie.getId()+".png"));
		//쿠키 객체의 getImg() method 이용해서 img 변수 값 (=이미지 파일 이름) 가져오기
		imagelabel[0].setText("제품명: "+ cookie.getName());
		imagelabel[1].setText("특징: "+ cookie.getdescription());
		imagelabel[2].setText("가격: "+ cookie.getPrice()+"원");
		imagelabel[3].setText("수량: "+ cookie.getcnt()+"개"); 
		//매개변수가 가리키는 쿠키 객체 정보로 UI 세팅
		if(cookie.getcnt() <= 0) { //속성 중 재고 속성 체크-> 없을때 == 품절일때
			imagelabel[3].setText("재고 소진"); //품절 표시 (수량 라벨 수정)
			needn = 0; // 담을 수량 0으로 설정-> 못담게
			need.setText("X"); //담을 수량 라벨 X로 표시
		}else { //쿠키 품절 아닐때
			needn = 1;
			need.setText(Integer.toString(needn)); //디폴트 값 1로 설정
		}
	}

} 