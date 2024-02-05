package meeting_room;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
	
	//��һ���
	JLabel id_lb = new JLabel("���̵�");	
	JLabel pw_lb = new JLabel("��й�ȣ");
	JTextField id_tf = new JTextField(20);
	JTextField pw_tf = new JPasswordField(20);
	JButton signup_btn = new JButton("ȸ������");
	JButton login_btn = new JButton("�α���");
	
	//JLabel admin_lb = new JLabel("������ �α���");
	JCheckBox check_admin = new JCheckBox("������ �α���");
	
	
	public Login() {
		setTitle("�α���");
		setSize(300, 230);

		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null);  //������ �г� �ʱ�ȭ
		
		//��� ��ġ ����
		id_lb.setBounds(30,  22,  50,  25);
		pw_lb.setBounds(30,  58,  50,  25);
		id_tf.setBounds(90, 22, 170, 25);
		pw_tf.setBounds(90, 58, 170, 25);
		signup_btn.setBounds(28, 130, 100, 40);
		login_btn.setBounds(156, 130, 100, 40);
		
		//admin_lb.setBounds(50, 94, 170, 25);
		check_admin.setBounds(30, 94, 150, 25);
		
		//��ư �̺�Ʈ �߰�
		signup_btn.addActionListener(this);
		login_btn.addActionListener(this);
		
		//��� �߰�
		c.add(id_lb);
		c.add(pw_lb);
		c.add(id_tf);
		c.add(pw_tf);
		c.add(signup_btn);
		c.add(login_btn);
		//c.add(admin_lb);
		c.add(check_admin);
		
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
		Login login = new Login();

	}

	
}
