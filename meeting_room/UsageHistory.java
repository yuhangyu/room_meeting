package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class UsageHistory implements ActionListener{
	private UsageHistoryUI ush;
	
	public UsageHistory(UsageHistoryUI ush) {
		this.ush = ush;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceButton = (JButton) e.getSource();
		
		if(sourceButton.getText().equals("Ȯ��")) {
			ush.dispose();
		}
		
		else if(sourceButton.getText().equals("�˻�")) {
			// �� �κп� �˻� ��ư Ŭ������ �̺�Ʈ ���� 
		}
	}
	
	public static void main(String[] args) {
		
	}
}