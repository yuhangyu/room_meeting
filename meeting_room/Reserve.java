package meeting_room;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Reserve implements ActionListener {
    private ReserveUI reserveUI;
    private JButton previousSelectedButton; // 이전 선택 버튼

    public Reserve(ReserveUI reserveUI) {
    	this.reserveUI = reserveUI;
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		
		
		if (clickedButton != reserveUI.reserve_btn && clickedButton != reserveUI.cancel_btn) {
		        
			// 이전에 선택된 버튼이 있다면 초기화
			if (previousSelectedButton != null) {
				previousSelectedButton.setBackground(null);
			}
		
			// 현재 선택된 버튼 업데이트
			clickedButton.setBackground(Color.CYAN);
			reserveUI.reserve_btn.setEnabled(true);
           
			previousSelectedButton = clickedButton;
		}
		
		// 예약하기 버튼 클릭시
		else if (clickedButton == reserveUI.reserve_btn) {
			String selectedRoomInfo = previousSelectedButton.getText();
			// 이전에 클릭한 방 버튼의 텍스트
			
			ReserveDetailUI rdUI = new ReserveDetailUI(selectedRoomInfo);
            rdUI.setVisible(true);
		}
		
		// 취소 버튼 클릭시
		else if (clickedButton == reserveUI.cancel_btn) {
			reserveUI.dispose();
		}
        
		
	}
}
	
	

    