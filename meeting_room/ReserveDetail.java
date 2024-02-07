package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
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
			
			int currentYear = rdUI.currentYear;
			int currentMonth = rdUI.currentMonth; // Calendar.MONTH�� 0���� �����ϹǷ� 1�� ������
			int currentDay = rdUI.currentDay;
			int currentHour = rdUI.currentHour;
			int currentMin = rdUI.currentMinute;
			
			Object selectYear = rdUI.yearSpinner.getValue();
			Object selectMonth = rdUI.monthSpinner.getValue();
			Object selectDay = rdUI.daySpinner.getValue();
			Object selectHour = rdUI.hourSpinner.getValue();
			Object selectMin = rdUI.minSpinner.getValue();
			
			JOptionPane optionPane;
			JDialog dialog;
			
			if (currentYear == ((Integer)selectYear).intValue()) {
				if (currentMonth == ((Integer)selectMonth).intValue()) {
					if (currentDay > ((Integer)selectDay).intValue()) {
						errorMsg();
						return;
					} else if (currentDay == ((Integer)selectDay).intValue()) {
						if (currentHour == ((Integer)selectHour).intValue()) {
							if (currentMin > ((Integer)selectMin).intValue() || currentMin == ((Integer)selectMin).intValue()) {
								errorMsg();
								return;
							}
						} else if (currentHour > ((Integer)selectHour).intValue()) {
							errorMsg();
							return;
						}
					}
				} else if (currentMonth > ((Integer)selectMonth).intValue()) {
					errorMsg();
					return;
				}
			}
			
			if ("".equals(rdUI.time_tf.getText())) {
				optionPane = new JOptionPane("�̿��� �ð��� �Է����ּ���.", JOptionPane.ERROR_MESSAGE);
				dialog = optionPane.createDialog(rdUI, "���� �ȳ�");
				dialog.setLocationRelativeTo(rdUI);
				dialog.setVisible(true);
				return;
			}
		}
	}
	
	public void errorMsg() {
		JOptionPane optionPane;
		JDialog dialog;
		
		optionPane = new JOptionPane("���� �ð� ���� �����Դϴ�.", JOptionPane.ERROR_MESSAGE);
		dialog = optionPane.createDialog(rdUI, "���� �ȳ�");
		dialog.setLocationRelativeTo(rdUI);
		dialog.setVisible(true);
	}
	
	private void updatePrice() {   
		// �ؽ�Ʈ �ʵ��� ���� �����ͼ� ó��
		String timeText = rdUI.time_tf.getText();
		String roomInfo = rdUI.chosen_room_info_lb.getText();
		String personInfo = (String) rdUI.select_add_person.getSelectedItem();
		MyInfoMgr mgr = new MyInfoMgr();
		RoomBean bean = mgr.room(roomInfo);
		
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
