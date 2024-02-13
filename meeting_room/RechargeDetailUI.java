package meeting_room;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RechargeDetailUI extends JFrame implements ActionListener {
	
	JLabel recharge_check = new JLabel("���� Ȯ��");
	JLabel amount = new JLabel("���� �ݾ� : ");
	JLabel amount_value = new JLabel("value");
	JLabel pw_check = new JLabel("��й�ȣ Ȯ�� ");
	JTextField pw_check_tf = new JPasswordField(20);
	JLabel recharge_check_1 = new JLabel("���� �����Ͻðڽ��ϱ�?");
	JLabel won_lb = new JLabel("��");
	
	JButton recharge = new JButton("����");
	JButton cancel = new JButton("���");
	
	MyInfoMgr mgr;
	int money;
	String id;
	
	public RechargeDetailUI() {
		setTitle("���� ��");
		setSize(400, 500);
		amount_value.setText(RechargeUI.recharge_value_tf.getText());
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
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
		
		recharge.addActionListener(this);
		cancel.addActionListener(this);
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���� â�� ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setVisible(true);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == recharge) {
			id = LoginUI.ID;
			mgr = new MyInfoMgr();
			MyInfoBean bean = mgr.select(id);
			money = bean.getMoney();
			
			byte[] password = SeedEncoding.encrypt(pw_check_tf.getText().trim());
			String pw = new String(password);
			if (!pw.equals(bean.getPW())) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� Ʋ�Ƚ��ϴ�. �ٽ� �� �� Ȯ�����ּ���.");
				return;
			}
		
			bean.setID(id);
			bean.setMoney(Integer.parseInt(amount_value.getText()) + money);
			if(mgr.charge(bean)) {
				JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
				mgr.select(bean.getID());
				MainPageUI.balance_value_lb.setText(String.valueOf(bean.getMoney()));
				RechargeUI.recharge_value_tf.setText("0");
				RechargeUI.cancel_btn.doClick();
				dispose();
			}
		} else if (e.getSource() == cancel) {
			dispose();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
