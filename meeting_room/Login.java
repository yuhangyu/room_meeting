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
	
	//요소생성
	JLabel id_lb = new JLabel("아이디");	
	JLabel pw_lb = new JLabel("비밀번호");
	JTextField id_tf = new JTextField(20);
	JTextField pw_tf = new JPasswordField(20);
	JButton signup_btn = new JButton("회원가입");
	JButton login_btn = new JButton("로그인");
	
	//JLabel admin_lb = new JLabel("관리자 로그인");
	JCheckBox check_admin = new JCheckBox("관리자 로그인");
	
	
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
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {
		Login login = new Login();

	}

	
}
