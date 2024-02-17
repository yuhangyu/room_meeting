package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class UseDetailUI extends JFrame {
	
    JTable ODT;
    JScrollPane pane;
    JLabel room;
    String[][] name;
	
	public UseDetailUI(String room_num, String in_time, int use_time) {
		setSize(780, 500);
        setTitle("�ֹ� ����");

        Container c = getContentPane();
        c.setLayout(null);
        
        orderdetail(room_num, in_time, use_time);
        
        room = new JLabel("�ֹ� ����");
        
		Font font = new Font("Dialog", Font.BOLD, 20);
		room.setFont(font);
		
        pane.setBounds(10, 50, 750, 400);
        room.setBounds(10, 0, 200, 50);
        
        c.add(pane);
        c.add(room);
        
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
	}
	
	public void orderdetail(String room_num, String in_time, int use_time) {
		// �����͸� ���� ���� �� ��� ����
		Vector<Vector<String>> data = new Vector<>();
		Vector<String> header = new Vector<>();
		header.add("����");
		header.add("�з�");
		header.add("�ֹ� �ð�");
		header.add("�ֹ��� �׸�");
		header.add("����");
		header.add("����");
		header.add("��û ����");
		
		MyInfoMgr mymgr = new MyInfoMgr();
		
		TotalBean tbean = mymgr.getuse(room_num, LoginUI.ID, in_time);
		
		String stime = tbean.getIntime();
		String etime = tbean.getOuttime();

		int a = 1;
		
		Vector<OrderBean> olist;
		olist = mymgr.order(LoginUI.ID, room_num, stime, etime);
		for (int i = 0; i < olist.size(); i++) {
			OrderBean obean = olist.get(i);
			
			Vector<OrderInfoBean> oilist;
			oilist = mymgr.userfoodsales(room_num, LoginUI.ID, obean.order_time);
			for (int j = 0; j < oilist.size(); j++) {
				Vector<String> row = new Vector<>();
				OrderInfoBean oibean = oilist.get(j);
				
				row.add(String.valueOf(a));
				row.add("����");
				row.add(oibean.getOrdertime());
				
				Vector<FoodBean> flist;
				flist = mymgr.foodAll();
            	for (int k= 0; k < flist.size(); k++) {
            		FoodBean fbean = flist.get(k);
            		if (oibean.getFoodname().equals(fbean.getFood())) {
            			row.add(fbean.getFname());
            			break;
            		}
            	}
            	row.add(String.valueOf(oibean.getFoodcount()));
            	row.add(String.valueOf(oibean.getFoodprice()));
				row.add(obean.getOrder_request());
    			data.add(row);
				a++;
			}
		}
		
		olist = mymgr.order(LoginUI.ID, room_num, stime, etime);
		for (int i = 0; i < olist.size(); i++) {
			OrderBean obean = olist.get(i);
			
			Vector<OrderInfoBean> oilist;
			oilist = mymgr.usergamesales(room_num, LoginUI.ID, obean.order_time);
			for (int j = 0; j < oilist.size(); j++) {
				Vector<String> row = new Vector<>();
				OrderInfoBean oibean = oilist.get(j);
				row.add(String.valueOf(a));
				row.add("����");
				row.add(oibean.getOrdertime());
				
				Vector<GameBean> glist;
				glist = mymgr.gameAll();
				for (int k = 0; k < glist.size(); k++) {
					GameBean gbean = glist.get(k);
	            	if (gbean.getGame().equals(oibean.getGamename())) {
	            		row.add(gbean.getGname());
	        			break;
	        		}
				}
				row.add(String.valueOf(oibean.getGamecount()));
				row.add(String.valueOf(oibean.getGameprice()));
				row.add(obean.getOrder_request());
				data.add(row);
				a++;
			}
		}
        
        // ���̺� �� ���� �� ������ ����
        DefaultTableModel model = new DefaultTableModel(data, header) {
            public boolean isCellEditable(int i, int c) {
                return false;
            }
        };
        
        // ���̺� ���� �� ��Ÿ�� ����
        ODT = new JTable(model);
        ODT.getTableHeader().setReorderingAllowed(false);
        ODT.getTableHeader().setResizingAllowed(false);
		ODT.setDefaultEditor(Object.class, null);
        // ��ũ�� �г� ����
        pane = new JScrollPane(ODT);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// �÷� �ʺ� ����
        ODT.getColumnModel().getColumn(0).setPreferredWidth(40);
        ODT.getColumnModel().getColumn(1).setPreferredWidth(40);
        ODT.getColumnModel().getColumn(2).setPreferredWidth(100);
        ODT.getColumnModel().getColumn(3).setPreferredWidth(90);
        ODT.getColumnModel().getColumn(4).setPreferredWidth(50);
        ODT.getColumnModel().getColumn(5).setPreferredWidth(50);
        ODT.getColumnModel().getColumn(6).setPreferredWidth(120);
        ODT.setRowHeight(20);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int j = 0; j < ODT.getColumnCount(); j++) {
            ODT.getColumnModel().getColumn(j).setCellRenderer(centerRenderer);
        }
    }
	
	private static boolean isDateTimeInRange(LocalDateTime targetDateTime, LocalDateTime startTime, LocalDateTime endTime) {
        return !targetDateTime.isBefore(startTime) && !targetDateTime.isAfter(endTime);
    }
}