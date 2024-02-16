package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SalesUI extends JFrame  {
	
	JTable totaltable;
	JScrollPane pane;

	public SalesUI() {
		setSize(1100, 522);
		setTitle("매출 현황");

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Font font = new Font("Dialog", Font.BOLD, 50);
		
		JLabel title = new JLabel(now.format(formatter) + " 매출 현황");
		
		viewlist();
		
		Container c = getContentPane();
		c.setLayout(null);
		
		title.setFont(font);
		title.setBounds(290, 15, 600, 45);
		pane.setBounds(55, 70, 986, 385);
		
		totaltable.addMouseListener(new MouseAction());
		
		c.add(title);
		c.add(pane);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	private class MouseAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = totaltable.getSelectedRow();
				TableModel model = totaltable.getModel();
				String room = String.valueOf(model.getValueAt(row,  0));
				SalesDetailUI sdui = new SalesDetailUI(room);
			}
			super.mouseClicked(e);
		}
	}
	
	public void viewlist() {
		Vector<TotalBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
				
		String header[] = {"방 번호", "음식 매출", "게임 매출", "방 매출","총 매출"};
		String [][] conts = new String[12][header.length];
		String a;
		int food_total = 0, game_total = 0, room_total = 0, total = 0;
		
		for (int i = 0; i < 12; i++) {
			food_total = 0;
			game_total = 0;
			room_total = 0;
			total = 0;
			
			if (i < 9) a = "R0" + (i + 1);
			else a = "R" + String.valueOf(i + 1);
			
			vlist = mgr.salesAll(a);
			
			for (int j = 0; j < vlist.size(); j++) {
				TotalBean bean = vlist.get(j);
				food_total += bean.getFood_total();
				game_total += bean.getGame_total();
				room_total += bean.getRoom_total();
				total += bean.getTotal();
			}
			conts[i][0] = a;
			conts[i][1] = String.valueOf(food_total);
			conts[i][2] = String.valueOf(game_total);
			conts[i][3] = String.valueOf(room_total);
			conts[i][4] = String.valueOf(total);
		}
		
		DefaultTableModel model = new DefaultTableModel(conts, header) {
			public boolean isCellEditable(int i, int c) {return false;}
		};
		
		if (totaltable != null) {
			model = (DefaultTableModel) totaltable.getModel();  // 기존 모델 가져오기
			model.setRowCount(0);  // 모델의 행 초기화
			for (int i = 0; i < 12; i++) {
				total = 0;
				
				if (i < 10) a = "0" + (i + 1);
				else a = String.valueOf(i);
				
				vlist = mgr.salesAll("R" + a);
				for (int j = 0; j < vlist.size(); j++) {
					model.addRow(conts[j]);  // 새로운 데이터로 모델 업데이트
				}
			}
		}
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		totaltable = new JTable(model);
		pane = new JScrollPane(totaltable);
		
		Font font = new Font("Dialog",Font.BOLD, 16);
		totaltable.setFont(font);
		
		totaltable.getTableHeader().setResizingAllowed(false);
		totaltable.getTableHeader().setReorderingAllowed(false);
		
		totaltable.getColumnModel().getColumn(0).setPreferredWidth(20);
		totaltable.getColumnModel().getColumn(1).setPreferredWidth(40);
		totaltable.getColumnModel().getColumn(2).setPreferredWidth(40);
		totaltable.getColumnModel().getColumn(3).setPreferredWidth(40);
		totaltable.getColumnModel().getColumn(4).setPreferredWidth(40);
		totaltable.setRowHeight(30);
		
		for (int i = 0; i < totaltable.getColumnCount(); i++) {
			totaltable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}
	
	public static void main(String[] args) {
		new SalesUI();
	}
}