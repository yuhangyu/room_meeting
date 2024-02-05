package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyInfo extends JFrame implements ActionListener{
	JLabel id_lb = new JLabel("���̵�");
	JLabel pw_lb = new JLabel("��й�ȣ");
	JLabel name_lb = new JLabel("�̸�");
	JLabel phone_lb = new JLabel("��ȭ��ȣ");
	
	JTextField id_tf = new JTextField("DefaultID", 10);
	JTextField pw_tf = new JTextField(10);
	JTextField name_tf = new JTextField("UserName", 10);
	JTextField phone_tf = new JTextField("PhoneNum", 10);
	
	JButton my_usage_btn = new JButton("�̿� ����");
	JButton modify_info_btn = new JButton("���� ����");
	JButton ok_btn = new JButton("Ȯ��");
	
	public MyInfo() {
		setTitle("�� ����");
		setSize(400, 500);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		id_lb.setBounds(85,  30,  100,  40);
		pw_lb.setBounds(85,  80,  100,  40);
		name_lb.setBounds(85,  130,  100,  40);
		phone_lb.setBounds(85,  180,  100,  40);
		
		id_tf.setBounds(180, 30, 100, 40);
		pw_tf.setBounds(180, 80, 100, 40);
		name_tf.setBounds(180, 130, 100, 40);
		phone_tf.setBounds(180, 180, 100, 40);
		
		my_usage_btn.setBounds(85, 250, 200, 70);
		modify_info_btn.setBounds(85, 340, 90, 70);
		ok_btn.setBounds(195, 340, 90, 70);
		
		
		Font font = new Font("Dialog", Font.BOLD, 18);
		
		id_lb.setFont(font);
		pw_lb.setFont(font);
		name_lb.setFont(font);
		phone_lb.setFont(font);
		
		my_usage_btn.addActionListener(this);
		modify_info_btn.addActionListener(this);
		ok_btn.addActionListener(this);
		
		c.add(id_lb);
		c.add(pw_lb);
		c.add(name_lb);
		c.add(phone_lb);
		
		c.add(id_tf);
		c.add(pw_tf);
		c.add(name_tf);
		c.add(phone_tf);
		
		c.add(my_usage_btn);
		c.add(modify_info_btn);
		c.add(ok_btn);		
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���μ������� �Բ� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {
		MyInfo myinfo = new MyInfo();
	}

	

}
