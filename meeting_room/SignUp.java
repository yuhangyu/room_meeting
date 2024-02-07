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
		
		if(sourceButton.getText().equals("���̵� �ߺ�Ȯ��")) {
			// �� �κп� ���̵� �ߺ�Ȯ�� �̺�Ʈ ����
		}
		else if(sourceButton.getText().equals("ȸ������")) {
			// �� �κп� ȸ������ �̺�Ʈ ���� 
		}
		else if(sourceButton.getText().equals("���")) {
			suu.dispose();
		}
		
	}

	
}
