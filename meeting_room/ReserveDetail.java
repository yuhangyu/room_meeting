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
		}
	}
	
	private void updatePrice() {   
		// 텍스트 필드의 값을 가져와서 처리
		String timeText = rdUI.time_tf.getText();
		String roomInfo = rdUI.chosen_room_info_lb.getText();
		String personInfo = (String) rdUI.select_add_person.getSelectedItem();
		MyInfoMgr mgr = new MyInfoMgr();
		RoomBean bean = mgr.room(roomInfo);
		
		int roomInfo_members = roomInfo.charAt(0) - '0'; // 방 정보의 첫번째 글자를 INT형으로 변환 
		int timePrice = 0; // 시간 금액 레이블에 표시
		int personPrice = 0; // 인원 금액 레이블에 표시
		int totalPrice = 0 ; // 총 금액 레이블에 표시
		
		int personInfo_int = 0; // 추가 인원 
		
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
