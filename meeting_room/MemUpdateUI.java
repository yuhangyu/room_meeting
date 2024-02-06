package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MemUpdateUI extends JFrame implements ActionListener {
	
	MyInfoMgr mgr;
	MyInfoBean bean = new MyInfoBean();
	
	String id;
	String pw;
	String name;
	String phone;
	
	JLabel id_lb = new JLabel("���̵�");
	JLabel name_lb = new JLabel("�̸�");
	JLabel phone_lb = new JLabel("��ȭ��ȣ");
	
	JTextField id_tf = new JTextField("DefaultID", 10);
	JTextField name_tf = new JTextField("UserName", 10);
	JTextField phone_tf = new JTextField("PhoneNum", 10);
	
	JButton modify_info_btn = new JButton("���� ����");
	JButton ok_btn = new JButton("Ȯ��");
	
	public MemUpdateUI(String id) {
		this.id = id;

		setTitle("�� ����");
		setSize(400, 350);

		//���� ���� id, pw, name, phone(tel)
		mgr = new MyInfoMgr();
		bean = mgr.select(this.id);
		pw = bean.getPW();
		name = bean.getName();
		phone = bean.getPhone();
		
		id_tf.setText(id);
		name_tf.setText(name);
		phone_tf.setText(phone);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		id_lb.setBounds(85,  30,  100,  40);
		name_lb.setBounds(85,  80,  100,  40);
		phone_lb.setBounds(85,  130,  100,  40);
		
		id_tf.setBounds(180, 30, 100, 40);
		name_tf.setBounds(180, 80, 100, 40);
		phone_tf.setBounds(180, 130, 100, 40);
		
		modify_info_btn.setBounds(85, 200, 90, 70);
		ok_btn.setBounds(195, 200, 90, 70);
		
		Font font = new Font("Dialog", Font.BOLD, 18);
		
		id_lb.setFont(font);
		name_lb.setFont(font);
		phone_lb.setFont(font);
		
		modify_info_btn.addActionListener(this);
		ok_btn.addActionListener(this);
		
		c.add(id_lb);
		c.add(name_lb);
		c.add(phone_lb);
		
		c.add(id_tf);
		c.add(name_tf);
		c.add(phone_tf);
		
		c.add(modify_info_btn);
		c.add(ok_btn);		
		
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
			if (!id_tf.getText().equals(id)) {
				JOptionPane.showMessageDialog(this, "���̵�� ������ �� �����ϴ�.");
			} else if (name_tf.getText().equals(name) && phone_tf.getText().equals(phone)) {
				JOptionPane.showMessageDialog(this, "������ ������ �����ϴ�.");
			} else if ("".equals(name_tf.getText()) || "".equals(phone_tf.getText())) {
				JOptionPane.showMessageDialog(this, "�������� ������ ������ �� �����ϴ�.");
			} else {
				change();
			}
		} else if (obj == ok_btn) {
			dispose();
		}
	}
	
	public void change() {
		bean.setName(name_tf.getText());
		bean.setPhone(phone_tf.getText());
		if(mgr.update(bean)) {
			name = bean.getName();
			phone = bean.getPhone();
			JOptionPane.showMessageDialog(this, "������ ����Ǿ����ϴ�.");
			dispose();
			MemberInfoUI.jb.doClick();
		}
	}
	
	public static void main(String[] args) {
		
	}
}