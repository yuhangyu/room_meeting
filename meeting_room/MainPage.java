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
            case "<html><div style='text-align: center;'>���÷�<br>�����ϱ�</div></html>":
                // ���÷� �����ϱ� UI ����
                ReserveUI rsv = new ReserveUI();
                rsv.setVisible(true);
                break;

            case "����":
                // ���� UI ���� 
                RechargeUI rcg = new RechargeUI();
                rcg.setVisible(true);
                break;

            case "���� �ֹ��ϱ�":
                // ���� �ֹ� ��� ����
                
                break;

            case "������� �뿩":
                // ������� �뿩 ��� ����
                
                break;

            case "�α׾ƿ�":
            	mainPageUI.dispose();
                // �α��� UI ����
            	LoginUI login = new LoginUI();
                login.setVisible(true);
                break;

            case "�� ����":
                // �� ���� UI ����
            	MyInfoUI myinfo = new MyInfoUI();
                myinfo.setVisible(true);
                break;

            default:
                break;
        
        }
	}
}


