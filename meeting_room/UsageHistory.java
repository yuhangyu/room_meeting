package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UsageHistory implements ActionListener{
	private UsageHistoryUI ush;
	
	public UsageHistory(UsageHistoryUI ush) {
		this.ush = ush;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceButton = (JButton) e.getSource();
		
		if(sourceButton.getText().equals("확인")) {
			ush.dispose();
		}
		
		else if(sourceButton.getText().equals("검색")) {
			// 이 부분에 검색 버튼 클릭시의 이벤트 구현 
		}
	}
		
	
	public static void main(String[] args) {
		
	}
}

