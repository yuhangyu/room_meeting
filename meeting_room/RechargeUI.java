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

public class RechargeUI extends JFrame implements ActionListener {
	ImageIcon icon = new ImageIcon("meeting_room/img.png"); // 로고에 사용할 이미지 아이콘
	JLabel logo_lb = new JLabel(icon); // 로고 레이블
	
	JButton charge_1000_btn = new JButton("1000원");
	JButton charge_5000_btn = new JButton("5000원");
	JButton charge_10000_btn = new JButton("10000원");
	JButton charge_30000_btn = new JButton("30000원");
	JButton charge_50000_btn = new JButton("50000원");
	
	JLabel recharge_amount_lb = new JLabel("충전금액");
	static JTextField recharge_value_tf = new JTextField("0", 30);
	JLabel won_lb = new JLabel("원");
	
	JButton recharge_btn = new JButton("충전하기");
	static JButton cancel_btn = new JButton("취소");
	
	public RechargeUI(){
		setTitle("충전");
		setSize(800, 800);
		
		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기
		
		// 로고 추가
        ImageIcon logoIcon = new ImageIcon("meeting_room/logo.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(45, 50, 700, 270);
        c.add(logoLabel);
		
        recharge_value_tf.setText("0");
        
		Font font = new Font("Dialog", Font.PLAIN, 18);
		
		charge_1000_btn.setBounds(30, 410, 120, 70);
		charge_5000_btn.setBounds(180, 410, 120, 70);
		charge_10000_btn.setBounds(330, 410, 120, 70);
		charge_30000_btn.setBounds(480, 410, 120, 70);
		charge_50000_btn.setBounds(630, 410, 120, 70);

		won_lb.setBounds(485, 530, 50, 35);
		recharge_amount_lb.setBounds(250, 530, 120, 35);
		recharge_value_tf.setBounds(350, 530, 120, 35);
		recharge_btn.setBounds(250, 600, 120, 70);
		cancel_btn.setBounds(400, 600, 120, 70);
		
		// 텍스트 필드를 우측으로 정렬
		recharge_value_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		recharge_amount_lb.setFont(font);
		won_lb.setFont(font);
		
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
		
		charge_1000_btn.addActionListener(this);
		charge_5000_btn.addActionListener(this);
		charge_10000_btn.addActionListener(this);
		charge_30000_btn.addActionListener(this);
		charge_50000_btn.addActionListener(this);	
		cancel_btn.addActionListener(this);
		recharge_btn.addActionListener(this);

		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//현재 창만 종료
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == charge_1000_btn) {
			updateRechargeAmount(1000);
		} else if (obj == charge_5000_btn) {
			updateRechargeAmount(5000);
		} else if (obj == charge_10000_btn) {
			updateRechargeAmount(10000);
		} else if (obj == charge_30000_btn) {
			updateRechargeAmount(30000);
		} else if (obj == charge_50000_btn) {
			updateRechargeAmount(50000);
		} else if (obj == cancel_btn) {
			dispose();
		} else if (obj == recharge_btn) {
			RechargeDetailUI rcdui = new RechargeDetailUI();
		}
	}
	
	private void updateRechargeAmount(int money) {
		int currentAmount = Integer.parseInt(recharge_value_tf.getText());
		currentAmount += money;
		recharge_value_tf.setText(Integer.toString(currentAmount));
	}
	
	public static void main(String[] args) {
		
	}
}