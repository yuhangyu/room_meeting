package meeting_room;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame implements ActionListener{
	private SignUpUI suu;
	
	public SignUp(SignUpUI suu) {
		this.suu = suu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceButton = (JButton) e.getSource();
		
		if(sourceButton.getText().equals("아이디 중복확인")) {
			// 이 부분에 아이디 중복확인 이벤트 구현
		}
		else if(sourceButton.getText().equals("회원가입")) {
			// 이 부분에 회원가입 이벤트 구현 
		}
		else if(sourceButton.getText().equals("취소")) {
			suu.dispose();
		}
		
	}

	
}
