package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MainPageUI extends JFrame implements ActionListener {
	private MainPage mainPage;
	String id;
	String name;
	int money;
	MyInfoMgr mgr;
	public static int MONEY;
	
	//요소 생성
	JLabel memberinfo_lb = new JLabel("회원정보");	
	JLabel name_lb = new JLabel("이름 : ");
	static JLabel name_value_lb = new JLabel("");
	JLabel balance_lb = new JLabel("잔액 : ");
	static JLabel balance_value_lb = new JLabel("0원");
	
	//Swing에서 \n을 통한 줄바꿈을 지원하지 않아서, HTML을 활용하여 버튼 내의 줄바꿈
	JButton reserve_btn = new JButton("<html><div style='text-align: center;'>미팅룸<br>예약하기</div></html>");
	JButton deposit_btn = new JButton("충전");
	JButton purchasefood_btn = new JButton("음식 주문하기");
	JButton rentgame_btn = new JButton("보드게임 대여");
	JButton logout_btn = new JButton("로그아웃");
	JButton myinfo_btn = new JButton("내 정보");
	static JButton a = new JButton("");

	public MainPageUI() {
		setTitle("메인 페이지");
		setSize(800, 650);
		
		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기화
		
		id = LoginUI.ID;
		mgr = new MyInfoMgr();
		MyInfoBean bean = mgr.select(id);
		
		name = bean.getName();
		money = bean.getMoney();
		name_value_lb.setText(name);
		balance_value_lb.setText(money + "원");
		setIDMoney(money);
		
		
		// 로고 추가
        ImageIcon logoIcon = new ImageIcon("meeting_room/logo.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(45, 20, 700, 270);
        c.add(logoLabel);
		
		//요소 위치 지정
		memberinfo_lb.setBounds(480,  323,  100,  40);
		name_lb.setBounds(480,  360,  100,  40);	
		name_value_lb.setBounds(530, 360, 150, 40);
		balance_lb.setBounds(480, 397, 100, 40);
		balance_value_lb.setBounds(530, 397, 150, 40);
		
		//Font 지정
		Font font = new Font("Dialog", Font.PLAIN, 18); // 폰트의 크기만 변경 
		memberinfo_lb.setFont(font);
		name_lb.setFont(font);
		name_value_lb.setFont(font);
		balance_lb.setFont(font);
		balance_value_lb.setFont(font);
		reserve_btn.setFont(font);
		deposit_btn.setFont(font);
		purchasefood_btn.setFont(font);
		rentgame_btn.setFont(font);
		logout_btn.setFont(font);
		myinfo_btn.setFont(font);
		
		reserve_btn.setBounds(55, 330, 150, 100);
		deposit_btn.setBounds(230, 330, 150, 100);
		purchasefood_btn.setBounds(55, 440, 150, 100);
		rentgame_btn.setBounds(230, 440, 150, 100);
		myinfo_btn.setBounds(430, 440, 150, 100);
		logout_btn.setBounds(590, 440, 150, 100);
		
		//요소 추가
		c.add(memberinfo_lb);
		c.add(name_lb);
		c.add(name_value_lb);
		c.add(balance_lb);
		c.add(balance_value_lb);
		c.add(reserve_btn);
		c.add(deposit_btn);		
		c.add(purchasefood_btn);
		c.add(rentgame_btn);
		c.add(logout_btn);
		c.add(myinfo_btn);
		
		mainPage = new MainPage(this);
		
		//버튼 이벤트 추가
		reserve_btn.addActionListener(mainPage);
		deposit_btn.addActionListener(mainPage);
		logout_btn.addActionListener(mainPage);
		myinfo_btn.addActionListener(mainPage);
		purchasefood_btn.addActionListener(mainPage);
		rentgame_btn.addActionListener(mainPage);
		a.addActionListener(this);
		
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//프로그램 종료할 때 프로세스까지 함께 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		setResizable(false);
	}
	
	public static void setIDMoney(int money) {
		MONEY = money;
	}
	
	public static int getIDMoney() {
		return MONEY;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == a) {
			dispose();
			MainPageUI mainpage = new MainPageUI();
		}
	}
	
	public static void main(String[] args) {
		MainPageUI mainpage = new MainPageUI();
	}
}