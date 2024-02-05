package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ReserveDetail implements ActionListener{
	private ReserveDetailUI rdUI;
	
	public ReserveDetail(ReserveDetailUI rdUI) {
		this.rdUI = rdUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		
		if (clickedButton == rdUI.cancel_btn) {
			rdUI.dispose();
		}
		
		else if (clickedButton == rdUI.payment_btn) {
			// 결제 이벤ㅌ 구현부
		}
	}	
}
