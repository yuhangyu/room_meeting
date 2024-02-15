package meeting_room;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Encoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MyInfoUI extends JFrame implements ActionListener {
	
	MyInfoMgr mgr;
	MyInfoBean bean = new MyInfoBean();
	
	String id;
	String pw;
	String name;
	String phone;
	int amount;
	
	JLabel id_lb = new JLabel("���̵�");
	JLabel pw_lb = new JLabel("��й�ȣ");
	JLabel name_lb = new JLabel("�̸�");
	JLabel phone_lb = new JLabel("��ȭ��ȣ");
	
	JLabel pwcheck_lb = new JLabel("��й�ȣ Ȯ��");
	JLabel amount_lb = new JLabel("�ܾ�");
	
	JTextField id_tf = new JTextField("DefaultID", 10);
	JTextField pw_tf = new JPasswordField(10);
	JTextField name_tf = new JTextField("UserName", 10);
	JTextField phone_tf = new JTextField("PhoneNum", 10);
	
	JTextField pwcheck_tf = new JPasswordField(10);
	JTextField amount_tf = new JTextField(10);
	
	JButton my_usage_btn = new JButton("�̿� ����");
	JButton modify_info_btn = new JButton("���� ����");
	JButton ok_btn = new JButton("Ȯ��");
	
	public String getID(MyInfoBean bean) {
		return bean.getID();
	}
	
	public MyInfoUI() {		
		setTitle("�� ����");
		setSize(600, 760);

		id = LoginUI.ID;
		mgr = new MyInfoMgr();
		bean = mgr.select(id);
		pw = bean.getPW();
		name = bean.getName();
		phone = bean.getPhone();
		amount = bean.getMoney();
		
		
		id_tf.setText(id);
		name_tf.setText(name);
		phone_tf.setText(phone);
		amount_tf.setText(String.valueOf(amount));
		
		id_tf.setEnabled(false);
		amount_tf.setEnabled(false);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		id_lb.setBounds(185,  50,  100,  40);
		pw_lb.setBounds(185,  100,  100,  40);
		pwcheck_lb.setBounds(185, 150, 100, 40);
		name_lb.setBounds(185,  200,  100,  40);
		phone_lb.setBounds(185,  250,  100,  40);
		amount_lb.setBounds(185, 300, 100, 40);
		
		id_tf.setForeground(Color.RED);
		amount_tf.setForeground(Color.RED);
		
		id_tf.setBounds(280, 50, 100, 40);
		pw_tf.setBounds(280, 100, 100, 40);
		pwcheck_tf.setBounds(280, 150, 100, 40);
		name_tf.setBounds(280, 200, 100, 40);
		phone_tf.setBounds(280, 250, 100, 40);
		amount_tf.setBounds(280, 300, 100, 40);
		
		my_usage_btn.setBounds(185, 450, 200, 70);
		modify_info_btn.setBounds(185, 540, 90, 70);
		ok_btn.setBounds(295, 540, 90, 70);
		
		Font font = new Font("Dialog", Font.BOLD, 18);
		Font font2 = new Font("Dialog", Font.BOLD, 14);
		
		id_lb.setFont(font);
		pw_lb.setFont(font);
		name_lb.setFont(font);
		phone_lb.setFont(font);
		amount_lb.setFont(font);
		
		id_tf.setFont(font2);
		amount_tf.setFont(font2);
		
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
		
		c.add(pwcheck_lb);
		c.add(pwcheck_tf);
		c.add(amount_lb);
		c.add(amount_tf);
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���� â�� ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
								
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == modify_info_btn) {
			byte[] password = SeedEncoding.encrypt(pw_tf.getText());
			if (!id_tf.getText().equals(id)) {
				JOptionPane.showMessageDialog(this, "���̵�� ������ �� �����ϴ�.");
			} else if (pw_tf.getText().equals("")){
				JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է��ϼž� ������ ������ �� �ֽ��ϴ�.");
			} else if (!(pw_tf.getText().equals(pwcheck_tf.getText()))){
				JOptionPane.showMessageDialog(this, "��й�ȣ�� ��й�ȣ Ȯ�ο� �Է��� ��й�ȣ�� ���� �ٸ��ϴ�.");
			} else if (name_tf.getText().equals(name) && phone_tf.getText().equals(phone) && new String(password).equals(pw)) {
				JOptionPane.showMessageDialog(this, "������ ������ �����ϴ�.");
			} else if ("".equals(name_tf.getText()) || "".equals(phone_tf.getText()) || "".equals(pw_tf.getText())) {
				JOptionPane.showMessageDialog(this, "�������� ������ ������ �� �����ϴ�.");
			} else {
				change();
			}
		} else if (obj == ok_btn) {
			dispose();
		} else if (obj == my_usage_btn) {
			UsageHistoryUI UHUI = new UsageHistoryUI();
		}
	}
	
	public void change() {
		byte[] password = SeedEncoding.encrypt(pw_tf.getText());
		bean.setPW(new String(password));
		bean.setName(name_tf.getText());
		bean.setPhone(phone_tf.getText());
		if(mgr.update(bean)) {
			pw = bean.getPW();
			name = bean.getName();
			phone = bean.getPhone();
			JOptionPane.showMessageDialog(this, "������ ����Ǿ����ϴ�.");
			MainPageUI.name_value_lb.setText(name);
			dispose();
		}
	}

	public static void main(String[] args) {
		
	}
}