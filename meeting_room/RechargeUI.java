package meeting_room;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RechargeUI extends JFrame {
	ImageIcon icon = new ImageIcon("meeting_room/img.png"); // 로고에 사용할 이미지 아이콘
	JLabel logo_lb = new JLabel(icon); // 로고 레이블
	
	JButton charge_1000_btn = new JButton("1000원");
	JButton charge_5000_btn = new JButton("5000원");
	JButton charge_10000_btn = new JButton("10000원");
	JButton charge_30000_btn = new JButton("30000원");
	JButton charge_50000_btn = new JButton("50000원");
	
	JLabel recharge_amount_lb = new JLabel("충전금액");
	JTextField recharge_value_tf = new JTextField("0", 30);
	JLabel won_lb = new JLabel("원");
	
	JButton recharge_btn = new JButton("충전하기");
	JButton cancel_btn = new JButton("취소");
	
	public RechargeUI(){
		setTitle("충전");
		setSize(800, 600);
		
		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기
		
		// 이미지 크기에 맞게 JLabel 크기 조절 
		Dimension imageSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
		logo_lb.setPreferredSize(imageSize);
		       
		// 이미지를 수평으로 중앙, 사용자가 정한 세로 좌표에 위치시킴
		int x = (getWidth() - imageSize.width) / 2;
		logo_lb.setBounds(x, 35, imageSize.width, imageSize.height);
		
		Font font = new Font("Dialog", Font.PLAIN, 18);
		
		charge_1000_btn.setBounds(30, 210, 120, 70);
		charge_5000_btn.setBounds(180, 210, 120, 70);
		charge_10000_btn.setBounds(330, 210, 120, 70);
		charge_30000_btn.setBounds(480, 210, 120, 70);
		charge_50000_btn.setBounds(630, 210, 120, 70);
		
		recharge_amount_lb.setBounds(250, 330, 120, 35);
		recharge_value_tf.setBounds(350, 330, 120, 35);
		// 텍스트 필드를 우측으로 정렬
		recharge_value_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		won_lb.setBounds(485, 330, 50, 35);
		
		recharge_amount_lb.setFont(font);
		won_lb.setFont(font);
		
		recharge_btn.setBounds(250 , 400, 120, 70);
		cancel_btn.setBounds(400, 400, 120, 70);
		
		c.add(logo_lb);
		c.add(charge_1000_btn);
		c.add(charge_5000_btn);
		c.add(charge_10000_btn);
		c.add(charge_30000_btn);
		c.add(charge_50000_btn);
		c.add(recharge_amount_lb);
		c.add(recharge_value_tf);
		c.add(won_lb);
		c.add(recharge_btn);
		c.add(cancel_btn);

		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//현재 창만 종료
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setVisible(true);
		setResizable(false);
		
		// 1000 ~ 50000 버튼의 이벤트 구현 - 현재 충전금액에서 누른 버튼의 금액만큼 더하기 
 		Recharge rc = new Recharge(recharge_value_tf);
		charge_1000_btn.addActionListener(rc);
		charge_5000_btn.addActionListener(rc);
		charge_10000_btn.addActionListener(rc);
		charge_30000_btn.addActionListener(rc);
		charge_50000_btn.addActionListener(rc);	
		// 취소 
		cancel_btn.addActionListener(rc);
		//충전하기
		recharge_btn.addActionListener(rc);
	}

	public static void main(String[] args) {
		RechargeUI rcg = new RechargeUI();
	}
}