package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainPageUI extends JFrame implements ActionListener {
	
	String id;
	String name;
	int money;
	MyInfoMgr mgr;
	public static int MONEY;

	String formattedDate; // 포멧 변경 YY-MM-DD
	String formattedDate2; // 포멧 변경 HH:MM:SS
	String EndformattedDate2; // 캘린더에 사용시간을 더하고 포맷 변경하여 출력
	
	//요소 생성
	JLabel memberinfo_lb = new JLabel("회원정보");	
	JLabel name_lb = new JLabel("이름 : ");
	JLabel balance_lb = new JLabel("잔액 : ");
	static JLabel name_value_lb = new JLabel("");
	static JLabel balance_value_lb = new JLabel("0원");
	
	//Swing에서 \n을 통한 줄바꿈을 지원하지 않아서, HTML을 활용하여 버튼 내의 줄바꿈
	JButton reserve_btn = new JButton("<html><div style='text-align: center;'>미팅룸<br>예약하기</div></html>");
	JButton deposit_btn = new JButton("충전");
	JButton myinfo_btn = new JButton("내 정보");
	JButton logout_btn = new JButton("로그아웃");
	static JButton purchasefood_btn = new JButton("음식 주문하기");
	static JButton rentgame_btn = new JButton("보드게임 대여");
	static JButton as = new JButton("");

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
		MONEY = money;
		
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
		
		//버튼 이벤트 추가
		reserve_btn.addActionListener(this);
		deposit_btn.addActionListener(this);
		logout_btn.addActionListener(this);
		myinfo_btn.addActionListener(this);
		purchasefood_btn.addActionListener(this);
		rentgame_btn.addActionListener(this);
		as.addActionListener(this);
		
		Vector<ReserveBean> vlist;
		vlist = mgr.reserveUser(LoginUI.ID);
		if (vlist.size() == 0) {
			purchasefood_btn.setEnabled(false);
			rentgame_btn.setEnabled(false);
		}
		
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//프로그램 종료할 때 프로세스까지 함께 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		//금액 업데이트를 위한 버튼 이벤트
		if (obj == as) {
			id = LoginUI.ID;
			mgr = new MyInfoMgr();
			MyInfoBean bean = mgr.select(id);
			
			money = bean.getMoney();
			balance_value_lb.setText(money + "원");
			MONEY = money;
		} else if (obj == reserve_btn) {
			ReserveUI rui = new ReserveUI();
		} else if (obj == deposit_btn ) {
			RechargeUI rcg = new RechargeUI();
		} else if (obj == purchasefood_btn) {
			if (check() == true) {
				FoodOrderUI FUI = new FoodOrderUI();
			} else {
				JOptionPane.showMessageDialog(null, "아직 예약 시간이 되지 않았습니다.\n" + formattedDate2 + " 이후 사용 가능합니다.");
			}
		} else if (obj == rentgame_btn) {
			if (check() == true) {
				GameOrderUI GUI = new GameOrderUI();
			} else {
				JOptionPane.showMessageDialog(null, "아직 예약 시간이 되지 않았습니다.\n" + formattedDate2 + " 이후 사용 가능합니다.");
			}
		} else if (obj == myinfo_btn) {
			MyInfoUI myinfo = new MyInfoUI();
		} else if (obj == logout_btn) {
			dispose();
			LoginUI login = new LoginUI();
		}
	}
	
	public boolean check() {
		boolean flag = false;
		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat newFormat2 = new SimpleDateFormat("HH:mm:ss");

		MyInfoMgr mgr = new MyInfoMgr();
		Vector<ReserveBean> vlist;
		vlist = mgr.reserveUser(LoginUI.ID);
		ReserveBean bean = vlist.get(0);
		String OriginalResvtime = bean.getResvtime();
		int resvtime = bean.getResvusetime(); // 사용 시간을 받아옴
			
		try {
			Date date = originalFormat.parse(OriginalResvtime);
				
			//getResvusetime()을 시간으로 변환하고 더하기
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date); // 캘린더의 데이트를 받아온 시간으로 변경
			calendar.add(Calendar.HOUR_OF_DAY, resvtime); // 캘린더에 사용시간 더하기
			
			formattedDate = newFormat.format(date); // 포멧 변경 YY-MM-DD
			formattedDate2 = newFormat2.format(date); // 포멧 변경 HH:MM:SS
			EndformattedDate2 = newFormat2.format(calendar.getTime()); // 캘린더에 사용시간을 더하고 포맷 변경하여 출력
			
			// 현재 시간 가져오기
			LocalTime currentTime = LocalTime.now();
			
			// 특정 시간 범위 설정
			LocalTime startTime = LocalTime.of(Integer.parseInt(formattedDate2.split(":")[0]), Integer.parseInt(formattedDate2.split(":")[1]));   // 시작 시간: 09:00
			LocalTime endTime = LocalTime.of(Integer.parseInt(EndformattedDate2.split(":")[0]), Integer.parseInt(EndformattedDate2.split(":")[1]));    // 종료 시간: 17:00
			
			// 현재 시간이 특정 시간 범위 내에 있는지 확인
			if (isWithinTimeRange(currentTime, startTime, endTime)) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return flag;
	}
	
	// 현재 시간이 특정 시간 범위 내에 있는지 확인하는 메서드
	private static boolean isWithinTimeRange(LocalTime currentTime, LocalTime startTime, LocalTime endTime) {
		return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
	}
	
	public static void main(String[] args) {
		
	}
}