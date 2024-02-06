package meeting_room;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ReserveInfoUI extends JFrame{
	JLabel resv_id = new JLabel("아이디");
	JLabel resv_name = new JLabel("이름");
	JLabel resv_tel = new JLabel("전화 번호");
	JLabel resv_room = new JLabel("방 번호");
	JLabel resv_time = new JLabel("시작 시간");
	JLabel resv_usetime = new JLabel("사용 시간");
	JLabel resv_person = new JLabel("인원");
	
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
