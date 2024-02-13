package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;


public class MainPage implements ActionListener{
	private MainPageUI mainPageUI;
	String formattedDate; // 포멧 변경 YY-MM-DD
	String formattedDate2; // 포멧 변경 HH:MM:SS
	String EndformattedDate2; // 캘린더에 사용시간을 더하고 포맷 변경하여 출력
	
	public MainPage(MainPageUI mainPageUI) {
		this.mainPageUI = mainPageUI;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		JButton sourceButton = (JButton) e.getSource();
		MyInfoMgr mgr = new MyInfoMgr();
		Vector<ReserveBean> vlist;
		vlist = mgr.reserveUser(LoginUI.ID);
		
		switch (sourceButton.getText()) {
			case "<html><div style='text-align: center;'>미팅룸<br>예약하기</div></html>":
				// 미팅룸 예약하기 UI 띄우기

				if (vlist.size() == 0) {
					ReserveUI rsv = new ReserveUI();
				} else {
					ReserveBean bean = vlist.get(0);
					String time = bean.getResvtime();
					String room = bean.getResvroom();
					int use = bean.getResvusetime();
					JOptionPane.showMessageDialog(null, "[예약 정보가 있습니다.]\n예약한 방 : " + room + "\n예약 날짜 : " + time + "\n사용 시간 : " + use + "시간");
				}
				break;
			
			case "충전":
				// 충전 UI 띄우기 
				RechargeUI rcg = new RechargeUI();
				rcg.recharge_value_tf.setText("0");
				break;
			
			case "음식 주문하기":
				// 음식 주문 UI 띄우기
				if (check() == true) {
					FoodOrderUI FUI = new FoodOrderUI();
				} else {
					JOptionPane.showMessageDialog(null, "아직 예약 시간이 되지 않았습니다.\n" + formattedDate2 + " 이후 사용 가능합니다.");
				}
				break;
			
			case "보드게임 대여":
				// 보드게임 대여 UI 띄우기
				if (check() == true) {
					GameOrderUI GUI = new GameOrderUI();
				} else {
					JOptionPane.showMessageDialog(null, "아직 예약 시간이 되지 않았습니다.\n" + formattedDate2 + " 이후 사용 가능합니다.");
				}
				break;
				
			case "내 정보":
				// 내 정보 UI 띄우기
				MyInfoUI myinfo = new MyInfoUI();
				break;
			
			case "로그아웃":
				mainPageUI.dispose();
				// 로그인 UI 띄우기
				LoginUI login = new LoginUI();
				break;
			
			default:
				break;  
		}
	}
	
	public boolean check() {
		boolean flag = false;
		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat newFormat2 = new SimpleDateFormat("HH:mm:ss");

		MyInfoMgr mgr = new MyInfoMgr();
		Vector<ReserveBean> vlist;
		vlist = mgr.reserveUser(LoginUI.ID);
		ReserveBean bean = vlist.get(0);
		String OriginalResvtime = bean.getResvtime();
		int resvtime = bean.getResvusetime(); // 사용 시간을 받아옴
			
		try {
			Date date = originalFormat.parse(OriginalResvtime);
				
			//getResvusetime()을 시간으로 변환하고 더하기
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(date); // 캘린더의 데이트를 받아온 시간으로 변경
	            calendar.add(Calendar.HOUR_OF_DAY, resvtime); // 캘린더에 사용시간 더하기
			
			formattedDate = newFormat.format(date); // 포멧 변경 YY-MM-DD
			formattedDate2 = newFormat2.format(date); // 포멧 변경 HH:MM:SS
			EndformattedDate2 = newFormat2.format(calendar.getTime()); // 캘린더에 사용시간을 더하고 포맷 변경하여 출력
			
		      // 현재 시간 가져오기
		      LocalTime currentTime = LocalTime.now();

		      // 특정 시간 범위 설정
		      LocalTime startTime = LocalTime.of(Integer.parseInt(formattedDate2.split(":")[0]), Integer.parseInt(formattedDate2.split(":")[1]));   // 시작 시간: 09:00
		      LocalTime endTime = LocalTime.of(Integer.parseInt(EndformattedDate2.split(":")[0]), Integer.parseInt(EndformattedDate2.split(":")[1]));    // 종료 시간: 17:00

		      // 현재 시간이 특정 시간 범위 내에 있는지 확인
		      if (isWithinTimeRange(currentTime, startTime, endTime)) {
		      	flag = true;
		      } else {
		      	flag = false;
		      }
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return flag;
	}
	
	// 현재 시간이 특정 시간 범위 내에 있는지 확인하는 메서드
	private static boolean isWithinTimeRange(LocalTime currentTime, LocalTime startTime, LocalTime endTime) {
		return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
	}
}