package meeting_room;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class OrderInfoDetail extends JFrame implements ActionListener{
	
	JTable ODT;
	JScrollPane pane;
	
	Container c = getContentPane();
	
	public OrderInfoDetail(String no) {
		setSize(800,400);
		setTitle(no);
		
		
		c.setLayout(null);
		
		orderdetail();
		
		pane.setBounds(0, 0, 700, 400);
		
		
		c.add(pane);
		
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public void orderdetail() {
		Vector<OrderInfoBean> vlist;
		OrderInfoMgr mgr = new OrderInfoMgr();
		vlist = mgr.orderfood();
		String header[] = {"����", "�ֹ��� ����", "���� ����", "����", "�ݾ�", "����"};
		String [][] conts = new String [vlist.size()][header.length];
		for(int i = 0; i < vlist.size();i++) {
			OrderInfoBean bean = vlist.get(i);
			conts[i][0] = String.valueOf(i+1);
			conts[i][1] = bean.getFoodname();
			conts[i][2] = String.valueOf(bean.getFoodcount());
			conts[i][3] = String.valueOf(bean.getFoodprice());
			conts[i][4] = String.valueOf(bean.getFoodprice()*bean.getFoodcount());
			conts[i][5] = String.valueOf(bean.isFoodstate());
		
		DefaultTableModel model = new DefaultTableModel(conts, header) {
			public boolean isCellEditable(int i, int c) {
			return false;
			}
		};
		ODT = new JTable(model);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		
		for (int j = 0; j < ODT.getColumnCount(); j++) {
		    ODT.getColumnModel().getColumn(j).setCellRenderer(centerRenderer);
		}
		pane = new JScrollPane(ODT);

		}
	}
}