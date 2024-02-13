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
	
	public OrderInfoDetail() {
		setSize(800,400);
		setTitle("No.");
		
		
		c.setLayout(null);
		
//		pane.setBounds(0, 0, 700, 400);

		
//		c.add(pane);
		
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
		String header[] = {"순서", "주문한 음식", "음식 개수", "가격", "금액"};
		String [][] conts = new String [vlist.size()][header.length];
		for(int i = 0; i < vlist.size();i++) {
			OrderInfoBean bean = vlist.get(i);
			conts[i][0] = String.valueOf(i+1);
			conts[i][1] = bean.getFoodname();
			conts[i][2] = String.valueOf(bean.getFoodcount());
			conts[i][3] = String.valueOf(bean.getFoodprice());
			conts[i][4] = String.valueOf(bean.getFoodprice()*bean.getFoodcount());
		}
//		DefaultTableModel model = new DefaultTableModel(conts, header);{
//			public boolean isCellEditable(int i, int c) {return false;}
//		};
//		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//		
	}
	
	public static void main(String[] args) {
		OrderInfoDetail OID = new OrderInfoDetail();
	}
}
