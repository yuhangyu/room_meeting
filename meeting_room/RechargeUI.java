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
	ImageIcon icon = new ImageIcon("meeting_room/img.png"); // �ΰ� ����� �̹��� ������
	JLabel logo_lb = new JLabel(icon); // �ΰ� ���̺�
	
	JButton charge_1000_btn = new JButton("1000��");
	JButton charge_5000_btn = new JButton("5000��");
	JButton charge_10000_btn = new JButton("10000��");
	JButton charge_30000_btn = new JButton("30000��");
	JButton charge_50000_btn = new JButton("50000��");
	
	JLabel recharge_amount_lb = new JLabel("�����ݾ�");
	static JTextField recharge_value_tf = new JTextField("0", 30);
	JLabel won_lb = new JLabel("��");
	
	JButton recharge_btn = new JButton("�����ϱ�");
	JButton cancel_btn = new JButton("���");
	
	public RechargeUI(){
		setTitle("����");
		setSize(800, 800);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		// �ΰ� �߰�
        ImageIcon logoIcon = new ImageIcon("meeting_room/logo.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(45, 50, 700, 270);
        c.add(logoLabel);
		
		Font font = new Font("Dialog", Font.PLAIN, 18);
		
		charge_1000_btn.setBounds(30, 410, 120, 70);
		charge_5000_btn.setBounds(180, 410, 120, 70);
		charge_10000_btn.setBounds(330, 410, 120, 70);
		charge_30000_btn.setBounds(480, 410, 120, 70);
		charge_50000_btn.setBounds(630, 410, 120, 70);
		
		recharge_amount_lb.setBounds(250, 530, 120, 35);
		recharge_value_tf.setBounds(350, 530, 120, 35);
		// �ؽ�Ʈ �ʵ带 �������� ����
		recharge_value_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		won_lb.setBounds(485, 530, 50, 35);
		
		recharge_amount_lb.setFont(font);
		won_lb.setFont(font);
		
		recharge_btn.setBounds(250, 600, 120, 70);
		cancel_btn.setBounds(400, 600, 120, 70);
		
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

		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���� â�� ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setVisible(true);
		setResizable(false);
		
		// 1000 ~ 50000 ��ư�� �̺�Ʈ ���� - ���� �����ݾ׿��� ���� ��ư�� �ݾ׸�ŭ ���ϱ� 
 		Recharge rc = new Recharge(recharge_value_tf);
		charge_1000_btn.addActionListener(rc);
		charge_5000_btn.addActionListener(rc);
		charge_10000_btn.addActionListener(rc);
		charge_30000_btn.addActionListener(rc);
		charge_50000_btn.addActionListener(rc);	
		// ��� 
		cancel_btn.addActionListener(rc);
		//�����ϱ�
		recharge_btn.addActionListener(rc);
	}

	public static void main(String[] args) {
		RechargeUI rcg = new RechargeUI();
	}
}