package meeting_room;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ReserveInfoUI extends JFrame{
	JLabel resv_id = new JLabel("���̵�");
	JLabel resv_name = new JLabel("�̸�");
	JLabel resv_tel = new JLabel("��ȭ ��ȣ");
	JLabel resv_room = new JLabel("�� ��ȣ");
	JLabel resv_time = new JLabel("���� �ð�");
	JLabel resv_usetime = new JLabel("��� �ð�");
	JLabel resv_person = new JLabel("�ο�");
	
	public ReserveInfoUI() {
		Container c = getContentPane();
		
		c.setLayout(null);
		
		
		
		
		
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
	public static void main(String[] args) {
		ReserveInfoUI reserveinfo_ui = new ReserveInfoUI();
	}

}
