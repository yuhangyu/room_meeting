package meeting_room;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpUI extends JFrame implements ActionListener{
	
	//요소 생성
	JLabel id_lb = new JLabel("아이디");
	JLabel pw_lb = new JLabel("비밀번호");
	JLabel pwcheck_lb = new JLabel("비밀번호확인");
	JLabel name_lb = new JLabel("이름");	
	JLabel phone_lb = new JLabel("전화번호");
	
	JTextField id_tf = new JTextField(40);
	JTextField pw_tf = new JPasswordField(40);
	JTextField pwcheck_tf = new JPasswordField(40);
	JTextField name_tf = new JTextField(40);
	JTextField phone_tf = new JTextField(40);
	
	JButton id_check_btn = new JButton("아이디 중복확인");
	JButton signup_btn = new JButton("회원가입");
	JButton cancel_btn = new JButton("취소");
	
	public SignUpUI() {
		setTitle("회원가입");
		setSize(320, 400);
		
		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기화
		
		//요소 위치 지정
		id_lb.setBounds(30, 20, 90, 25);
		pw_lb.setBounds(30, 100, 90, 25);
		pwcheck_lb.setBounds(30, 145, 90, 25);
		name_lb.setBounds(30, 190, 90, 25);
		phone_lb.setBounds(30, 235, 90, 25);
				
		id_tf.setBounds(130, 20, 150, 25);
		pw_tf.setBounds(130, 100, 150, 25);
		pwcheck_tf.setBounds(130, 145, 150, 25);
		name_tf.setBounds(130, 190, 150, 25);
		phone_tf.setBounds(130, 235, 150, 25);
				
		id_check_btn.setBounds(130, 55, 150, 25);
		signup_btn.setBounds(40, 290, 100, 40);
		cancel_btn.setBounds(168, 290, 100, 40);
				
		//버튼 이벤트 추가
		id_check_btn.addActionListener(this);
		signup_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
				
		//요소 추가
		c.add(id_lb);
		c.add(pw_lb);
		c.add(pwcheck_lb);
		c.add(name_lb);
		c.add(phone_lb);
				
		c.add(id_tf);
		c.add(pw_tf);
		c.add(pwcheck_tf);
		c.add(name_tf);;
		c.add(phone_tf);
				
		c.add(id_check_btn);
		c.add(signup_btn);
		c.add(cancel_btn);
				
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
		SignUpUI signup = new SignUpUI();

	}

	
	

}
