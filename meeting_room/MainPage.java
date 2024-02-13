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
	String formattedDate; // ���� ���� YY-MM-DD
	String formattedDate2; // ���� ���� HH:MM:SS
	String EndformattedDate2; // Ķ������ ���ð��� ���ϰ� ���� �����Ͽ� ���
	
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
			case "<html><div style='text-align: center;'>���÷�<br>�����ϱ�</div></html>":
				// ���÷� �����ϱ� UI ����

				if (vlist.size() == 0) {
					ReserveUI rsv = new ReserveUI();
				} else {
					ReserveBean bean = vlist.get(0);
					String time = bean.getResvtime();
					String room = bean.getResvroom();
					int use = bean.getResvusetime();
					JOptionPane.showMessageDialog(null, "[���� ������ �ֽ��ϴ�.]\n������ �� : " + room + "\n���� ��¥ : " + time + "\n��� �ð� : " + use + "�ð�");
				}
				break;
			
			case "����":
				// ���� UI ���� 
				RechargeUI rcg = new RechargeUI();
				rcg.recharge_value_tf.setText("0");
				break;
			
			case "���� �ֹ��ϱ�":
				// ���� �ֹ� UI ����
				if (check() == true) {
					FoodOrderUI FUI = new FoodOrderUI();
				} else {
					JOptionPane.showMessageDialog(null, "���� ���� �ð��� ���� �ʾҽ��ϴ�.\n" + formattedDate2 + " ���� ��� �����մϴ�.");
				}
				break;
			
			case "������� �뿩":
				// ������� �뿩 UI ����
				if (check() == true) {
					GameOrderUI GUI = new GameOrderUI();
				} else {
					JOptionPane.showMessageDialog(null, "���� ���� �ð��� ���� �ʾҽ��ϴ�.\n" + formattedDate2 + " ���� ��� �����մϴ�.");
				}
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
		int resvtime = bean.getResvusetime(); // ��� �ð��� �޾ƿ�
			
		try {
			Date date = originalFormat.parse(OriginalResvtime);
				
			//getResvusetime()�� �ð����� ��ȯ�ϰ� ���ϱ�
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(date); // Ķ������ ����Ʈ�� �޾ƿ� �ð����� ����
	            calendar.add(Calendar.HOUR_OF_DAY, resvtime); // Ķ������ ���ð� ���ϱ�
			
			formattedDate = newFormat.format(date); // ���� ���� YY-MM-DD
			formattedDate2 = newFormat2.format(date); // ���� ���� HH:MM:SS
			EndformattedDate2 = newFormat2.format(calendar.getTime()); // Ķ������ ���ð��� ���ϰ� ���� �����Ͽ� ���
			
		      // ���� �ð� ��������
		      LocalTime currentTime = LocalTime.now();

		      // Ư�� �ð� ���� ����
		      LocalTime startTime = LocalTime.of(Integer.parseInt(formattedDate2.split(":")[0]), Integer.parseInt(formattedDate2.split(":")[1]));   // ���� �ð�: 09:00
		      LocalTime endTime = LocalTime.of(Integer.parseInt(EndformattedDate2.split(":")[0]), Integer.parseInt(EndformattedDate2.split(":")[1]));    // ���� �ð�: 17:00

		      // ���� �ð��� Ư�� �ð� ���� ���� �ִ��� Ȯ��
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
	
	// ���� �ð��� Ư�� �ð� ���� ���� �ִ��� Ȯ���ϴ� �޼���
	private static boolean isWithinTimeRange(LocalTime currentTime, LocalTime startTime, LocalTime endTime) {
		return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
	}
}