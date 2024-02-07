package meeting_room;

import java.awt.Color;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ReserveUI extends JFrame implements ActionListener{
	private Reserve reserve;
	String id;
	
	JLabel reserve_lb = new JLabel("미팅룸 예약하기");
	JLabel room2_lb = new JLabel("2인");
	JLabel room4_lb = new JLabel("4인");
	JLabel room8_lb = new JLabel("8인");
	
	JLabel memberinfo_lb = new JLabel("회원정보");	
	JLabel name_lb = new JLabel("이름 : ");
	JLabel name_value_lb = new JLabel("집가고싶다");
	JLabel balance_lb = new JLabel("잔액 : ");
	JLabel balance_value_lb = new JLabel("<TEST> 원");
	
	JButton room2_1_btn = new JButton("2인 1번 룸");
	JButton room2_2_btn = new JButton("2인 2번 룸");
	JButton room2_3_btn = new JButton("2인 3번 룸");
	JButton room2_4_btn = new JButton("2인 4번 룸");
	JButton room4_1_btn = new JButton("4인 1번 룸");
	JButton room4_2_btn = new JButton("4인 2번 룸");
	JButton room4_3_btn = new JButton("4인 3번 룸");
	JButton room4_4_btn = new JButton("4인 4번 룸");
	JButton room4_5_btn = new JButton("4인 5번 룸");
	JButton room4_6_btn = new JButton("4인 6번 룸");
	JButton room8_1_btn = new JButton("8인 1번 룸");
	JButton room8_2_btn = new JButton("8인 2번 룸");
	
	JButton reserve_btn = new JButton("예약");
	JButton cancel_btn = new JButton("취소");

	Container c = getContentPane();
   
	public ReserveUI() {
		setTitle("미팅룸 예약하기");
		setSize(800, 750);
		
		c.setLayout(null);
		
		id = LoginUI.getLoginID();
		
		name_value_lb.setText(MainPageUI.name_value_lb.getText());
		balance_value_lb.setText(MainPageUI.balance_value_lb.getText());
		
		reserve_lb.setBounds(5,  2,  500,  40);
		room2_lb.setBounds(65, 75, 100, 20);
		room4_lb.setBounds(265, 75, 100, 20);
		room8_lb.setBounds(465, 75, 100, 20);
		
		memberinfo_lb.setBounds(590,  13,  100,  40);
		name_lb.setBounds(590,  50,  100,  40);	
		name_value_lb.setBounds(640, 50, 100, 40);
		balance_lb.setBounds(590, 87, 100, 40);
		balance_value_lb.setBounds(640, 87, 150, 40);
		
		room2_1_btn.setBounds(5, 110, 160, 85);
		room2_2_btn.setBounds(5, 210, 160, 85);
		room2_3_btn.setBounds(5, 310, 160, 85);
		room2_4_btn.setBounds(5, 410, 160, 85);
		
		room4_1_btn.setBounds(210, 110, 160, 85);
		room4_2_btn.setBounds(210, 210, 160, 85);
		room4_3_btn.setBounds(210, 310, 160, 85);
		room4_4_btn.setBounds(210, 410, 160, 85);
		room4_5_btn.setBounds(210, 510, 160, 85);
		room4_6_btn.setBounds(210, 610, 160, 85);
		room8_1_btn.setBounds(405, 110, 160, 85);
		room8_2_btn.setBounds(405, 210, 160, 85);
		reserve_btn.setBounds(605, 150, 160, 85);
		cancel_btn.setBounds(605, 250, 160, 85);
		
		reserve = new Reserve(this);
		
        room2_1_btn.addActionListener(reserve);
		room2_2_btn.addActionListener(reserve);
		room2_3_btn.addActionListener(reserve);
		room2_4_btn.addActionListener(reserve);
		room4_1_btn.addActionListener(reserve);
		room4_2_btn.addActionListener(reserve);
		room4_3_btn.addActionListener(reserve);
		room4_4_btn.addActionListener(reserve);
		room4_5_btn.addActionListener(reserve);
		room4_6_btn.addActionListener(reserve);
		room8_1_btn.addActionListener(reserve);
		room8_2_btn.addActionListener(reserve);
		reserve_btn.addActionListener(reserve);
		cancel_btn.addActionListener(reserve);
		
		//Font 지정
		Font font = new Font("Dialog", Font.BOLD, 30); // 폰트굵게
		Font font2 = new Font("Dialog", Font.PLAIN, 23); 
		Font font3 = new Font("Dialog", Font.PLAIN, 18);
		
		reserve_lb.setFont(font);
		room2_lb.setFont(font2);
		room4_lb.setFont(font2);
		room8_lb.setFont(font2);
		memberinfo_lb.setFont(font3);
		name_lb.setFont(font3);
		name_value_lb.setFont(font3);;
		balance_lb.setFont(font3);
		balance_value_lb.setFont(font3);
		
		room2_1_btn.setFont(font2);
		room2_2_btn.setFont(font2);
		room2_3_btn.setFont(font2);
		room2_4_btn.setFont(font2);
		
		room4_1_btn.setFont(font2);
		room4_2_btn.setFont(font2);
		room4_3_btn.setFont(font2);
		room4_4_btn.setFont(font2);
		room4_5_btn.setFont(font2);
		room4_6_btn.setFont(font2);
		
		room8_1_btn.setFont(font2);
		room8_2_btn.setFont(font2);
		
		reserve_btn.setFont(font2);
		reserve_btn.setEnabled(false); // 예약 버튼은 미팅룸을 선택햐야 활성화 되도록 설정

		cancel_btn.setFont(font2);
		
		RoomCheck();
		
		//요소 추가
		c.add(reserve_lb);
		c.add(room2_lb);
		c.add(room4_lb);
		c.add(room8_lb);
		
		c.add(memberinfo_lb);
		c.add(name_lb);
		c.add(name_value_lb);
		c.add(balance_lb);
		c.add(balance_value_lb);
		
		c.add(room2_1_btn);
		c.add(room2_2_btn);
		c.add(room2_3_btn);
		c.add(room2_4_btn);
		
		c.add(room4_1_btn);
		c.add(room4_2_btn);
		c.add(room4_3_btn);
		c.add(room4_4_btn);
		c.add(room4_5_btn);
		c.add(room4_6_btn);
		
		c.add(room8_1_btn);
		c.add(room8_2_btn);
		
		c.add(reserve_btn);
		c.add(cancel_btn);
		
		
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		setVisible(true);
		setResizable(false);
	}
	
	public void RoomCheck() {
		//room4_2_btn.setEnabled(false); // 사용중인 룸 예시로 디폴트 설정 
		//room4_2_btn.setBackground(new Color(255, 180, 180)); // 사용중인 방 색 변경
		Vector<RoomBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.roomAll();
		
		for(int i = 0; i < vlist.size(); i++) {
			RoomBean bean = vlist.get(i);
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {
		ReserveUI rsv = new ReserveUI();

	}

}
