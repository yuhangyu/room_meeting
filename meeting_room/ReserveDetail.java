package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ReserveDetail implements ActionListener {
	private ReserveDetailUI rdUI;
	
	int timePrice = 0; // 시간 금액 레이블에 표시
	int personPrice = 0; // 인원 금액 레이블에 표시
	int totalPrice = 0 ; // 총 금액 레이블에 표시
	int personInfo_int = 0; // 추가 인원
	
	public ReserveDetail(ReserveDetailUI rdUI) {
		this.rdUI = rdUI;
		// time_tf에 DocumentListener 추가
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
	
		// select_add_person 콤보박스에 ActionListener 추가
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
			// 결제 이벤트 구현부
			reserve();
		}
	}
	
	public void errorMsg() {
		JOptionPane optionPane;
		JDialog dialog;
		
		optionPane = new JOptionPane("예약 시간 선택 오류입니다.", JOptionPane.ERROR_MESSAGE);
		dialog = optionPane.createDialog(rdUI, "예약 안내");
		dialog.setLocationRelativeTo(rdUI);
		dialog.setVisible(true);
	}
	
	public void reserve() {
		int currentYear = rdUI.currentYear;
		int currentMonth = rdUI.currentMonth; // Calendar.MONTH는 0부터 시작하므로 1을 더해줌
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
			optionPane = new JOptionPane("이용할 시간을 입력해주세요.", JOptionPane.ERROR_MESSAGE);
			dialog = optionPane.createDialog(rdUI, "예약 안내");
			dialog.setLocationRelativeTo(rdUI);
			dialog.setVisible(true);
			return;
		}
		
		ReserveBean bean = new ReserveBean();
		MyInfoMgr mgr = new MyInfoMgr();
		
		String id = LoginUI.ID;
		String str = selectYear.toString() + "-" + selectMonth.toString() + "-" + selectDay.toString() + " " + selectHour.toString() + ":" + selectMin.toString();
		
		MyInfoBean bean2 = mgr.select(id);
		String personInfo = (String) rdUI.select_add_person.getSelectedItem();
		int personInfo_int = 0; // 추가 인원 

		// 추가 인원 int 형 변환 
		if(personInfo.equals("없음")) {
			personInfo_int = 0;
		} else if(personInfo.equals("1명")) {
			personInfo_int = 1;
		} else if(personInfo.equals("2명")) {
			personInfo_int = 2;
		}
		
		int money = bean2.getMoney();
		if (money < totalPrice) {
			optionPane = new JOptionPane("잔액이 부족합니다.", JOptionPane.ERROR_MESSAGE);
			dialog = optionPane.createDialog(rdUI, "예약 안내");
			dialog.setLocationRelativeTo(rdUI);
			dialog.setVisible(true);
			return;
		}
		
		String roomInfo = rdUI.chosen_room_info_lb.getText();
		RoomBean bean3 = mgr.room(roomInfo);
		
		bean.setResvid(bean2.getID());
		bean.setResvname(bean2.getName());
		bean.setResvphone(bean2.getPhone());
		bean.setResvroom(roomInfo);
		bean.setResvtime(str);
		bean.setResvusetime(Integer.parseInt(rdUI.time_tf.getText()));
		bean.setResvperson(bean3.getRperson() + personInfo_int);
		
		if(mgr.reserve(bean)) {
			bean2.setMoney(money - totalPrice);
			if (mgr.charge(bean2)) {
				bean.reserveRoom();
				if (bean.isReserved()) {
		            ReserveUI.buttonStateChange.setEnabled(false);
		        }
				
				optionPane = new JOptionPane("예약이 완료되었습니다.", JOptionPane.INFORMATION_MESSAGE);
				dialog = optionPane.createDialog(rdUI, "예약 안내");
				dialog.setLocationRelativeTo(rdUI);
				dialog.setVisible(true);
				rdUI.dispose();
				ReserveUI.a.doClick();
				return;
			}
		}
	}
	
	private void updatePrice() {   
		// 텍스트 필드의 값을 가져와서 처리
		String timeText = rdUI.time_tf.getText();
		String roomInfo = rdUI.chosen_room_info_lb.getText();
		String personInfo = (String) rdUI.select_add_person.getSelectedItem();
		MyInfoMgr mgr = new MyInfoMgr();
		RoomBean bean = mgr.room(roomInfo);
		
		// 추가 인원 int 형 변환 
		if(personInfo.equals("없음")) {
			personInfo_int = 0;
		} else if(personInfo.equals("1명")) {
			personInfo_int = 1;
		} else if(personInfo.equals("2명")) {
			personInfo_int = 2;
		}
	
		try {
			// 입력값이 숫자이거나 비어있을 때만 처리
			// 숫자와 백스페이스 키만 입력할 수 있도록 함 
			if (timeText.matches("\\d*")) {
				// 예외 처리를 통해 숫자로 변환
				int hours = timeText.isEmpty() ? 0 : Integer.parseInt(timeText);
				 
				timePrice = hours * bean.getRperson() * 10000;
				personPrice = personInfo_int * bean.getRadd();
				totalPrice = timePrice + personPrice;
				
				// 값 업데이트
				rdUI.person_price_value_lb.setText(personPrice + " 원");
				rdUI.time_price_value_lb.setText(timePrice + " 원");
				rdUI.total_price_value_lb.setText(totalPrice + " 원");
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "숫자만 입력 가능합니다.");
		}
	}
}
