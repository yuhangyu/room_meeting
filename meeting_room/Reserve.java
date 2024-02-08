package meeting_room;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class Reserve implements ActionListener {
	private ReserveUI reserveUI;
	private JButton previousSelectedButton; // 이전 선택 버튼
	
	public Reserve(ReserveUI reserveUI) {
		this.reserveUI = reserveUI;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		
		// DB에서 받아온 DATE타입을 테이블에 업데이트 하기 위해 포맷을 변경하기 위한 선언
		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat newFormat2 = new SimpleDateFormat("HH:mm:ss");
		
		// 방을 클릭할때 테이블을 출력하고 선택된 방 레이블을 변경하고 선택한 방에 대한 예약 테이블 출력
		if(e.getSource() == clickedButton) {
			String roomName = clickedButton.getText();
			if(roomName != "예약") { // 예약 버튼을 눌렀을 때는 레이블을 변경하지 않음 
				reserveUI.chosen_room_value_lb.setText(roomName); // 레이블 변경
				
				Vector<ReserveBean> reservelist;
				MyInfoMgr mgr = new MyInfoMgr();
				reservelist = mgr.reserveAll();
				
				// 테이블 업데이트
				DefaultTableModel model = (DefaultTableModel) reserveUI.reserveTable.getModel();
	            model.setRowCount(0); // 기존 테이블 내용 지우기
				
				for(int i = 0; i < reservelist.size(); i++ ) {
					ReserveBean bean = reservelist.get(i);
					if(bean.getResvroom().equals(roomName)) { // 선택된 방과 reserve 테이블의 방을 비교 
						String OriginalResvtime = bean.getResvtime();
						int resvtime = bean.getResvusetime(); // 사용 시간을 받아옴
						
						try {
							Date date = originalFormat.parse(OriginalResvtime);
							System.out.println(OriginalResvtime + "\t" + bean.getResvusetime()
							 +"\t" + bean.getResvperson());
//							// getResvusetime()을 시간으로 변환하고 더하기
//				            Calendar calendar = Calendar.getInstance();
//				            calendar.setTime(date); // 캘린더의 데이트를 받아온 시간으로 변경
//				            calendar.add(Calendar.HOUR_OF_DAY, resvtime); // 캘린더에 사용시간 더하기
							
							
							String formattedDate = newFormat.format(date); // 포멧 변경 YY-MM-DD
							String formattedDate2 = newFormat2.format(date); // 포멧 변경 HH:MM:SS
//							String EndformattedDate2 = newFormat2.format(calendar.getTime()); // 캘린더에 사용시간을 더하고 포맷 변경하여 출력
							
							String[] rowData = { // 추가할 행의 데이터 
									formattedDate,
									formattedDate2,
									formattedDate2
		                    };
		                    model.addRow(rowData);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		}
		
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
			JButton buttonStateChanger = previousSelectedButton;
			// 예약된 방의 상태를 변경하기 위한 새로운 버튼 객체 생성 
			
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
	

