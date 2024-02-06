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
	
	//요소생성
	JLabel id_lb = new JLabel("아이디");	
	JLabel pw_lb = new JLabel("비밀번호");
	JTextField id_tf = new JTextField(20);
	JTextField pw_tf = new JPasswordField(20);
	JButton signup_btn = new JButton("회원가입");
	JButton login_btn = new JButton("로그인");
	
	//JLabel admin_lb = new JLabel("관리자 로그인");
	JCheckBox check_admin = new JCheckBox("관리자 로그인");
	
	int check_action = 0;
	
	private Statement stmt;

	private DBConnection pool;


	public Login() {
		setTitle("로그인");
		setSize(300, 230);

		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null);  //컨텐츠 패널 초기화
		
		//요소 위치 지정
		id_lb.setBounds(30,  22,  50,  25);
		pw_lb.setBounds(30,  58,  50,  25);
		id_tf.setBounds(90, 22, 170, 25);
		pw_tf.setBounds(90, 58, 170, 25);
		signup_btn.setBounds(28, 130, 100, 40);
		login_btn.setBounds(156, 130, 100, 40);
		
		//admin_lb.setBounds(50, 94, 170, 25);
		check_admin.setBounds(30, 94, 150, 25);
		
		//버튼 이벤트 추가
		signup_btn.addActionListener(this);
		login_btn.addActionListener(this);
		check_admin.addItemListener(this);
		//요소 추가
		c.add(id_lb);
		c.add(pw_lb);
		c.add(id_tf);
		c.add(pw_tf);
		c.add(signup_btn);
		c.add(login_btn);
		//c.add(admin_lb);
		c.add(check_admin);
		
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//프로그램 종료할 때 프로세스까지 함께 종료
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
		
		if(obj == signup_btn) {//회원가입 버튼 동작
			SignUp signup = new SignUp();
		}else if(obj == login_btn) {
			if (check_action == 1) {//관리자 로그인 시
				try {
					ResultSet rs = stmt.executeQuery("SELECT * FROM admin WHERE admin_id='" + id_tf.getText() + "' AND admin_pw='" + pw_tf.getText() + "'");
					if (rs.next()) {
						JOptionPane.showMessageDialog(this, "로그인 성공!");
						AdminMainPage admin = new AdminMainPage();
						dispose();
					} else {
						JOptionPane.showMessageDialog(this, "로그인 실패. 다시 시도하세요.");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (check_action == 0) {//비관리자 로그인 시
				try {
					ResultSet rs = stmt.executeQuery("SELECT * FROM member WHERE member_id='" + id_tf.getText() + "' AND member_pw='" + pw_tf.getText() + "'");
					if (rs.next()) {
						JOptionPane.showMessageDialog(this, "로그인 성공!");
						MainPageUI mainpage = new MainPageUI();
						dispose();
					} else {
						JOptionPane.showMessageDialog(this, "로그인 실패. 다시 시도하세요.");
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
			// 체크박스가 선택되면 실행되는 코드
			check_action = 1;
		} else {
			// 체크박스가 선택 해제되면 실행되는 코드
			check_action = 0;
		}
	}

	public static void main(String[] args) {
		Login login = new Login();
	}
}
