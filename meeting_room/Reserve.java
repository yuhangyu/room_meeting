package meeting_room;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Reserve implements ActionListener {
	private ReserveUI reserveUI;
	private JButton previousSelectedButton;
	
	String formattedDate;
	String formattedDate2;
	String EndformattedDate;
	String EndformattedDate2;
	Date endtime;
	Date reservationDate;
	Calendar calendar;
 
	SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	SimpleDateFormat newFormat2 = new SimpleDateFormat("HH:mm:ss");
	
	public Reserve(ReserveUI reserveUI) {
		this.reserveUI = reserveUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		
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
	            
	            // reservelist�� ��¥�� ���� ����
	            Collections.sort(reservelist, new Comparator<ReserveBean>() {
	                @Override
	                public int compare(ReserveBean bean1, ReserveBean bean2) {
	                    Date date1 = null;
	                    Date date2 = null;

	                    try {
	                        date1 = originalFormat.parse(bean1.getResvtime());
	                        date2 = originalFormat.parse(bean2.getResvtime());
	                    } catch (ParseException e) {
	                        e.printStackTrace();
	                    }

	                    return date1.compareTo(date2);
	                }
	            });
	            
				
				for(int i = 0; i < reservelist.size(); i++ ) {
					ReserveBean bean = reservelist.get(i);
					try {
						// ���õ� ��� reserve ���̺��� ���� ���ϰ� ����ð��� ���� �ð� ������ ��쿡�� ���̺� �߰�
			                  Date reservationDate = originalFormat.parse(bean.getResvtime());
			                  Date currentDate = new Date();
			                  Calendar calendar = Calendar.getInstance();
			                  calendar.setTime(reservationDate); // Ķ������ ����Ʈ�� �޾ƿ� �ð����� ����
			                  calendar.add(Calendar.HOUR_OF_DAY, bean.getResvusetime()); // Ķ������ ���ð� ���ϱ�
			                  
			                  String EndformattedDate = originalFormat.format(calendar.getTime()); // Ķ������ ���ð��� ���ϰ� ���� �����Ͽ� ���
			                  String EndformattedDate2 = newFormat2.format(calendar.getTime());
			                  Date endtime = originalFormat.parse(EndformattedDate);
		                  
			                  
						if(bean.getResvroom().equals(roomName) && endtime.after(currentDate)) {
							String OriginalResvtime = bean.getResvtime();
							int resvtime = bean.getResvusetime(); //��� �ð��� �޾ƿ�
							
							try {
								Date date = originalFormat.parse(OriginalResvtime);
								
								
								String formattedDate = newFormat.format(date); //���� ���� YY-MM-DD
								String formattedDate2 = newFormat2.format(date); //���� ���� HH:MM:SS
								
								String[] rowData = { // �߰��� ���� ������ 
										formattedDate,
										formattedDate2,
										EndformattedDate2
								};
								model.addRow(rowData);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					} catch (ParseException e3) {
						e3.printStackTrace();
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