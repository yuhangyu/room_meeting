package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginUI extends JFrame implements ActionListener, ItemListener, KeyListener {
	public static String ID;
	
	//��һ���
	JLabel id_lb = new JLabel("���̵�");	
	JLabel pw_lb = new JLabel("��й�ȣ");
	JTextField id_tf = new JTextField(20);
	JTextField pw_tf = new JPasswordField(20);
	JButton signup_btn = new JButton("ȸ������");
	JButton login_btn = new JButton("�α���");
	JCheckBox check_admin = new JCheckBox("������ �α���");
	
	int check_action = 1;
	private Statement stmt;
	private DBConnection pool;

	public LoginUI() {
		setTitle("�α���");
		setSize(800, 630);

		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null);  //������ �г� �ʱ�ȭ
		
		// �ΰ� �߰�
		ImageIcon logoIcon = new ImageIcon("meeting_room/logo.jpg");
		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setBounds(45, 20, 700, 270);
		c.add(logoLabel);
		
		//��� ��ġ ����
		id_lb.setBounds(255,  322,  100,  25);
		pw_lb.setBounds(255,  358,  100,  25);
		id_tf.setBounds(365, 322, 170, 25);
		pw_tf.setBounds(365, 358, 170, 25);
		signup_btn.setBounds(243, 440, 130, 70);
		login_btn.setBounds(401, 440, 130, 70);
		check_admin.setBounds(250, 394, 250, 25);
		
		//Font ����
		Font font = new Font("Dialog", Font.BOLD, 13); // ��Ʈ����
		Font font2 = new Font("Dialog", Font.BOLD, 22);
		
		id_lb.setFont(font2);
		pw_lb.setFont(font2);
		check_admin.setFont(font);
		
		//��ư �̺�Ʈ �߰�
		signup_btn.addActionListener(this);
		login_btn.addActionListener(this);
		check_admin.addItemListener(this);
		id_tf.addKeyListener(this);
		pw_tf.addKeyListener(this);
		
		//��� �߰�
		c.add(id_lb);
		c.add(pw_lb);
		c.add(id_tf);
		c.add(pw_tf);
		c.add(signup_btn);
		c.add(login_btn);
		c.add(check_admin);
		
		//ȭ�� �߾ӿ� ���� ����
		//���α׷� ������ �� ���μ������� �Բ� ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == signup_btn) {//ȸ������ ��ư ����
			SignUpUI suu = new SignUpUI();
		} else if(obj == login_btn ) {
			Login();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//����Ű�� �α��� ����
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Login();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void Login() {
		try {
			pool = DBConnection.getInstance();
			Connection con = pool.getConnection();
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String id = id_tf.getText().trim();
			byte[] password = SeedEncoding.encrypt(pw_tf.getText().trim());
			String pw = new String(password);
			ResultSet rs = stmt.executeQuery("SELECT * FROM member WHERE member_id='" + id + "' AND member_pw=('" + pw + "') AND member_level='" + check_action + "'");
			if (rs.next()) {
				ID = id;
				JOptionPane.showMessageDialog(this, "�α��� ����!");
				if (check_action == 1) { //�Ϲ� �����
					MainPageUI mainpage = new MainPageUI();
				} else if (check_action == 2) {
					AdminMainPageUI admin = new AdminMainPageUI();
				}
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "�α��� ����. �ٽ� �õ��ϼ���.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			//üũ�ڽ��� ���õǸ� ����Ǵ� �ڵ�
			check_action = 2;
		} else {
			//üũ�ڽ��� ���� �����Ǹ� ����Ǵ� �ڵ�
			check_action = 1;
		}
	}
	
	public static void main(String[] args) {
		LoginUI login = new LoginUI();
	}
}