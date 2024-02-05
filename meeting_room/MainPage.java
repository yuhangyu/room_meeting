package meeting_room;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class MainPage implements ActionListener{
	private MainPageUI mainPageUI;
	
	public MainPage(MainPageUI mainPageUI) {
		this.mainPageUI = mainPageUI;
		
	}
	
	
	@Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();

        switch (sourceButton.getText()) {
            case "<html><div style='text-align: center;'>미팅룸<br>예약하기</div></html>":
                // 미팅룸 예약하기 UI 띄우기
                ReserveUI rsv = new ReserveUI();
                rsv.setVisible(true);
                break;

            case "충전":
                // 충전 UI 띄우기 
                RechargeUI rcg = new RechargeUI();
                rcg.setVisible(true);
                break;

            case "음식 주문하기":
                // 음식 주문 기능 구현
                
                break;

            case "보드게임 대여":
                // 보드게임 대여 기능 구현
                
                break;

            case "로그아웃":
            	mainPageUI.dispose();
                // 로그인 UI 띄우기
            	LoginUI login = new LoginUI();
                login.setVisible(true);
                break;

            case "내 정보":
                // 내 정보 UI 띄우기
            	MyInfoUI myinfo = new MyInfoUI();
                myinfo.setVisible(true);
                break;

            default:
                break;
        
        }
	}
}


