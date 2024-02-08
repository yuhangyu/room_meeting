package meeting_room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UsageHistory implements ActionListener{
	private UsageHistoryUI ush;

	public UsageHistory(UsageHistoryUI ush) {
		this.ush = ush;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceButton = (JButton) e.getSource();
		
		if(sourceButton.getText().equals("확인")) {
			ush.dispose();
		} else if(sourceButton.getText().equals("검색")) {
			reservesearch();
		}
	}
	
	public void errormsg() {
		JOptionPane optionPane;
		JDialog dialog;
		
		optionPane = new JOptionPane("날짜 선택 오류입니다.", JOptionPane.ERROR_MESSAGE);
		dialog = optionPane.createDialog(ush, "오류 안내");
		dialog.setLocationRelativeTo(ush);
		dialog.setVisible(true);
	}
	
	public void reservesearch() {
		String syear = String.valueOf(ush.YearSpinner1.getValue());
		String eyear = String.valueOf(ush.YearSpinner2.getValue());
		String smonth = String.valueOf(ush.MonthSpinner1.getValue());
		String emonth = String.valueOf(ush.MonthSpinner2.getValue());
		String sday = String.valueOf(ush.DaySpinner1.getValue());
		String eday = String.valueOf(ush.DaySpinner2.getValue());
		
		if ("".equals(syear) || "".equals(eyear) || "".equals(smonth) || "".equals(emonth) || "".equals(sday) || "".equals(eday)) {
			errormsg();
			return;
		}
		
		if (Integer.parseInt(syear) == Integer.parseInt(eyear)) {
			if (Integer.parseInt(smonth) == Integer.parseInt(emonth)) {
				if (Integer.parseInt(sday) > Integer.parseInt(eday)) {
					errormsg();
					return;
				}
			} else if (Integer.parseInt(smonth) > Integer.parseInt(emonth)) {
				errormsg();
				return;
			}
		} else if (Integer.parseInt(syear) > Integer.parseInt(eyear)) {
			errormsg();
			return;
		}
		
		if (smonth.length() == 1) smonth = "0" + smonth;
		if (emonth.length() == 1) emonth = "0" + emonth;
		if (sday.length() == 1) sday = "0" + sday;
		if (eday.length() == 1) eday = "0" + eday;
		String startday = syear + "-" + smonth + "-" + sday + " 00:00:00";
		String endday = eyear + "-" + emonth + "-" + eday + " 23:59:59";
		
		Vector<ReserveBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.reserveUserDetail(LoginUI.ID, startday, endday);
		
		// 데이터 및 컬럼명 배열 정의
		String[] columnNames = {"순번", "ID", "이름", "전화번호", "이용 룸", "예약 날짜", "이용 시간", "이용 인원"};
		String[][] data = new String[vlist.size()][columnNames.length];
		
		for (int i = 0; i < vlist.size(); i++) {
			ReserveBean bean = vlist.get(i);
			data[i][0] = String.valueOf(i + 1);
			data[i][1] = bean.getResvid();
			data[i][2] = bean.getResvname();
			data[i][3] = bean.getResvphone();
			data[i][4] = bean.getResvroom();
			data[i][5] = bean.getResvtime();
			data[i][6] = String.valueOf(bean.getResvusetime());
			data[i][7] = String.valueOf(bean.getResvperson());
		}
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int i, int c){ return false; }
		};
		ush.historyTable.setModel(model);
		ush.history();
	}
	
	public static void main(String[] args) {
		new UsageHistoryUI();
	}
}