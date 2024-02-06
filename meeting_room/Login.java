package meeting_room;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener, ItemListener{
	
	//��һ���
	JLabel id_lb = new JLabel("���̵�");	
	JLabel pw_lb = new JLabel("��й�ȣ");
	JTextField id_tf = new JTextField(20);
	JTextField pw_tf = new JPasswordField(20);
	JButton signup_btn = new JButton("ȸ������");
	JButton login_btn = new JButton("�α���");
	
	//JLabel admin_lb = new JLabel("������ �α���");
	JCheckBox check_admin = new JCheckBox("������ �α���");
	
	int check_action = 0;
	
	private Statement stmt;

	private DBConnection pool;


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
		check_admin.addItemListener(this);
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
		
		try {
			pool = DBConnection.getInstance();
			Connection con = pool.getConnection();
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == signup_btn) {//ȸ������ ��ư ����
			SignUp signup = new SignUp();
		}else if(obj == login_btn) {
			if (check_action == 1) {//������ �α��� ��
				try {
					ResultSet rs = stmt.executeQuery("SELECT * FROM admin WHERE admin_id='" + id_tf.getText() + "' AND admin_pw='" + pw_tf.getText() + "'");
					if (rs.next()) {
						JOptionPane.showMessageDialog(this, "�α��� ����!");
						AdminMainPage admin = new AdminMainPage();
						dispose();
					} else {
						JOptionPane.showMessageDialog(this, "�α��� ����. �ٽ� �õ��ϼ���.");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (check_action == 0) {//������� �α��� ��
				try {
					ResultSet rs = stmt.executeQuery("SELECT * FROM member WHERE member_id='" + id_tf.getText() + "' AND member_pw='" + pw_tf.getText() + "'");
					if (rs.next()) {
						JOptionPane.showMessageDialog(this, "�α��� ����!");
						MainPageUI mainpage = new MainPageUI();
						dispose();
					} else {
						JOptionPane.showMessageDialog(this, "�α��� ����. �ٽ� �õ��ϼ���.");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			// üũ�ڽ��� ���õǸ� ����Ǵ� �ڵ�
			check_action = 1;
		} else {
			// üũ�ڽ��� ���� �����Ǹ� ����Ǵ� �ڵ�
			check_action = 0;
		}
	}

	public static void main(String[] args) {
		Login login = new Login();
	}
}
