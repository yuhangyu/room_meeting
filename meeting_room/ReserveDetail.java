package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ReserveDetail implements ActionListener {
	private ReserveDetailUI rdUI;
	
	public ReserveDetail(ReserveDetailUI rdUI) {
		this.rdUI = rdUI;
		// time_tf�� DocumentListener �߰�
		rdUI.time_tf.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				updatePrice();
			}
			
			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				updatePrice();
			}
			
			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				updatePrice();
			}
		});
	
		// select_add_person �޺��ڽ��� ActionListener �߰�
		rdUI.select_add_person.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updatePrice();
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		
		if (clickedButton == rdUI.cancel_btn) {
			rdUI.dispose();
		} else if (clickedButton == rdUI.payment_btn) {
			// ���� �̺�Ʈ ������
		}
	}
	
	private void updatePrice() {   
		// �ؽ�Ʈ �ʵ��� ���� �����ͼ� ó��
		String timeText = rdUI.time_tf.getText();
		String roomInfo = rdUI.chosen_room_info_lb.getText();
		String personInfo = (String) rdUI.select_add_person.getSelectedItem();
		MyInfoMgr mgr = new MyInfoMgr();
		RoomBean bean = mgr.room(roomInfo);
		
		int roomInfo_members = roomInfo.charAt(0) - '0'; // �� ������ ù��° ���ڸ� INT������ ��ȯ 
		int timePrice = 0; // �ð� �ݾ� ���̺� ǥ��
		int personPrice = 0; // �ο� �ݾ� ���̺� ǥ��
		int totalPrice = 0 ; // �� �ݾ� ���̺� ǥ��
		
		int personInfo_int = 0; // �߰� �ο� 
		
		// �߰� �ο� int �� ��ȯ 
		if(personInfo.equals("����")) {
			personInfo_int = 0;
		} else if(personInfo.equals("1��")) {
			personInfo_int = 1;
		} else if(personInfo.equals("2��")) {
			personInfo_int = 2;
		}
	
		try {
			// �Է°��� �����̰ų� ������� ���� ó��
			// ���ڿ� �齺���̽� Ű�� �Է��� �� �ֵ��� �� 
			if (timeText.matches("\\d*")) {
				// ���� ó���� ���� ���ڷ� ��ȯ
				int hours = timeText.isEmpty() ? 0 : Integer.parseInt(timeText);
				 
				timePrice = hours * bean.getRperson() * 10000;
				personPrice = personInfo_int * bean.getRadd();
				totalPrice = timePrice + personPrice;
				
				// �� ������Ʈ
				rdUI.person_price_value_lb.setText(personPrice + " ��");
				rdUI.time_price_value_lb.setText(timePrice + " ��");
				rdUI.total_price_value_lb.setText(totalPrice + " ��");
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "���ڸ� �Է� �����մϴ�.");
		}
	}
}
