package meeting_room;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RechargeDetailUI extends JFrame {
	
	JLabel recharge_check = new JLabel("충전 확인");
	JLabel amount = new JLabel("충전 금액 : ");
	static JLabel amount_value = new JLabel("value");
	JLabel pw_check = new JLabel("비밀번호 확인 ");
	JTextField pw_check_tf = new JPasswordField(20);
	JLabel recharge_check_1 = new JLabel("정말 충전하시겠습니까?");
	JLabel won_lb = new JLabel("원");
	
	static JButton recharge = new JButton("충전");
	static JButton cancel = new JButton("취소");
	
	public RechargeDetailUI() {
		
		setTitle("충전 상세");
		setSize(400, 500);
		amount_value.setText(RechargeUI.recharge_value_tf.getText());
		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기
		
		Font font = new Font("Dialog", Font.PLAIN, 18);
		Font font2 = new Font("Dialog", Font.PLAIN, 28);
		
		recharge_check.setFont(font2);
		recharge_check_1.setFont(font2);
		amount.setFont(font);
		amount_value.setFont(font);
		pw_check.setFont(font);
		recharge.setFont(font);
		cancel.setFont(font);
		won_lb.setFont(font);
		
		recharge_check.setForeground(Color.RED);
		recharge_check_1.setForeground(Color.RED);
		amount_value.setForeground(Color.MAGENTA);
		
		recharge_check.setBounds(120, 30, 200, 30);
		amount.setBounds(100, 80, 100, 25);
		amount_value.setBounds(200, 80, 100, 25);
		won_lb.setBounds(260, 80, 100, 25);
		pw_check.setBounds(65, 120, 150, 25);
		pw_check_tf.setBounds(200, 120, 100, 25);
		recharge_check_1.setBounds(35, 200, 350, 30);
		recharge.setBounds(50, 300, 130, 100);
		cancel.setBounds(210, 300, 130, 100);

		
		c.add(recharge_check);
		c.add(amount);
		c.add(amount_value);
		c.add(pw_check);
		c.add(pw_check_tf);
		c.add(recharge_check_1);
		c.add(recharge);
		c.add(cancel);
		c.add(won_lb);
		
		RechargeDetail rcd = new RechargeDetail(this);
		recharge.addActionListener(rcd);
		cancel.addActionListener(rcd);
		
		
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//현재 창만 종료
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setVisible(true);
		setResizable(false);
	}

	public static void main(String[] args) {
		RechargeDetailUI rcdu = new RechargeDetailUI();

	}

}
