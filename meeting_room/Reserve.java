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
	private JButton previousSelectedButton; // ���� ���� ��ư
	
	public Reserve(ReserveUI reserveUI) {
		this.reserveUI = reserveUI;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		
		// DB���� �޾ƿ� DATEŸ���� ���̺� ������Ʈ �ϱ� ���� ������ �����ϱ� ���� ����
		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat newFormat2 = new SimpleDateFormat("HH:mm:ss");
		
		// ���� Ŭ���Ҷ� ���̺��� ����ϰ� ���õ� �� ���̺��� �����ϰ� ������ �濡 ���� ���� ���̺� ���
		if(e.getSource() == clickedButton) {
			String roomName = clickedButton.getText();
			if(roomName != "����") { // ���� ��ư�� ������ ���� ���̺��� �������� ���� 
				reserveUI.chosen_room_value_lb.setText(roomName); // ���̺� ����
				
				Vector<ReserveBean> reservelist;
				MyInfoMgr mgr = new MyInfoMgr();
				reservelist = mgr.reserveAll();
				
				// ���̺� ������Ʈ
				DefaultTableModel model = (DefaultTableModel) reserveUI.reserveTable.getModel();
	            model.setRowCount(0); // ���� ���̺� ���� �����
				
				for(int i = 0; i < reservelist.size(); i++ ) {
					ReserveBean bean = reservelist.get(i);
					if(bean.getResvroom().equals(roomName)) { // ���õ� ��� reserve ���̺��� ���� �� 
						String OriginalResvtime = bean.getResvtime();
						int resvtime = bean.getResvusetime(); // ��� �ð��� �޾ƿ�
						
						try {
							Date date = originalFormat.parse(OriginalResvtime);
							System.out.println(OriginalResvtime + "\t" + bean.getResvusetime()
							 +"\t" + bean.getResvperson());
//							// getResvusetime()�� �ð����� ��ȯ�ϰ� ���ϱ�
//				            Calendar calendar = Calendar.getInstance();
//				            calendar.setTime(date); // Ķ������ ����Ʈ�� �޾ƿ� �ð����� ����
//				            calendar.add(Calendar.HOUR_OF_DAY, resvtime); // Ķ������ ���ð� ���ϱ�
							
							
							String formattedDate = newFormat.format(date); // ���� ���� YY-MM-DD
							String formattedDate2 = newFormat2.format(date); // ���� ���� HH:MM:SS
//							String EndformattedDate2 = newFormat2.format(calendar.getTime()); // Ķ������ ���ð��� ���ϰ� ���� �����Ͽ� ���
							
							String[] rowData = { // �߰��� ���� ������ 
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
		
			// ������ ���õ� ��ư�� �ִٸ� �ʱ�ȭ
			if (previousSelectedButton != null) {
				previousSelectedButton.setBackground(null);
			}
		
			// ���� ���õ� ��ư ������Ʈ
			clickedButton.setBackground(Color.CYAN);
			reserveUI.reserve_btn.setEnabled(true);
			previousSelectedButton = clickedButton;
		}
		
		// �����ϱ� ��ư Ŭ����
		else if (clickedButton == reserveUI.reserve_btn) {
			JButton buttonStateChanger = previousSelectedButton;
			// ����� ���� ���¸� �����ϱ� ���� ���ο� ��ư ��ü ���� 
			
			String selectedRoomInfo = previousSelectedButton.getText();
			// ������ Ŭ���� �� ��ư�� �ؽ�Ʈ
			
			ReserveDetailUI rdUI = new ReserveDetailUI(selectedRoomInfo);
			rdUI.setVisible(true);
		}
		
		// ��� ��ư Ŭ����
		else if (clickedButton == reserveUI.cancel_btn) {
			reserveUI.dispose();
		}
	}
}
	

