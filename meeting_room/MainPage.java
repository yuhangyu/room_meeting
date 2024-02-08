package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainPage implements ActionListener{
	private MainPageUI mainPageUI;
	
	public MainPage(MainPageUI mainPageUI) {
		this.mainPageUI = mainPageUI;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceButton = (JButton) e.getSource();
		
		switch (sourceButton.getText()) {
			case "<html><div style='text-align: center;'>���÷�<br>�����ϱ�</div></html>":
				// ���÷� �����ϱ� UI ����
				ReserveUI rsv = new ReserveUI();
				break;
			
			case "����":
				// ���� UI ���� 
				RechargeUI rcg = new RechargeUI();
				break;
			
			case "���� �ֹ��ϱ�":
				// ���� �ֹ� UI ����
				FoodOrderUI.main(null);
				break;
			
			case "������� �뿩":
				// ������� �뿩 UI ����
				BoardGame.main(null);
				break;
				
			case "�� ����":
				// �� ���� UI ����
				MyInfoUI myinfo = new MyInfoUI();
				break;
			
			case "�α׾ƿ�":
				mainPageUI.dispose();
				// �α��� UI ����
				LoginUI login = new LoginUI();
				break;
			
			default:
				break;  
		}
	}
}