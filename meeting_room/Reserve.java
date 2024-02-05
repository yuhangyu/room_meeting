package meeting_room;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Reserve implements ActionListener {
    private ReserveUI reserveUI;
    private JButton previousSelectedButton; // ���� ���� ��ư

    public Reserve(ReserveUI reserveUI) {
    	this.reserveUI = reserveUI;
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		
		
		if (clickedButton != reserveUI.reserve_btn && clickedButton != reserveUI.cancel_btn) {
		        
			// ������ ���õ� ��ư�� �ִٸ� �ʱ�ȭ
			if (previousSelectedButton != null) {
				previousSelectedButton.setBackground(null);
			}
		
			// ���� ���õ� ��ư ������Ʈ
			clickedButton.setBackground(Color.CYAN);
			reserveUI.reserve_btn.setEnabled(true);
           
			previousSelectedButton = clickedButton;
		}
		
		// �����ϱ� ��ư Ŭ����
		else if (clickedButton == reserveUI.reserve_btn) {
			String selectedRoomInfo = previousSelectedButton.getText();
			// ������ Ŭ���� �� ��ư�� �ؽ�Ʈ
			
			ReserveDetailUI rdUI = new ReserveDetailUI(selectedRoomInfo);
            rdUI.setVisible(true);
		}
		
		// ��� ��ư Ŭ����
		else if (clickedButton == reserveUI.cancel_btn) {
			reserveUI.dispose();
		}
        
		
	}
}
	
	

    