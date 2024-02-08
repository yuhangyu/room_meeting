package meeting_room;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class OrderInfo extends JFrame implements ActionListener{
	JTable orderTable;
	JScrollPane pane;
	static JButton jb = new JButton(" ");
	
	Container c = getContentPane();
	
	public OrderInfo() {
		setSize(1000,440);
		setTitle("주문 현황");
		
		c.setLayout(null);
		
		orderview();
		
		pane.setBounds(0, 0 , 900,440);
		c.add(pane);
		
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {

	
	}
		
	
	
	public void orderview() {
		Vector<OrderInfoBean> vlist ;
		OrderInfoMgr mgr = new OrderInfoMgr();
		vlist = mgr.orderfood();
		String header[] = {"순서", "주문한 방", "주문 시간","총 금액", "주문 상태"};
		String [][] conts = new String [vlist.size()][header.length];
		for (int i = 0; i < vlist.size(); i++) {
			OrderInfoBean  bean = vlist.get(i);
			conts[i][0] = String.valueOf(i+1);
			conts[i][1] = String.valueOf(bean.getRoom_no());
			conts[i][2] = bean.getOrdertime();
			conts[i][3] = String.valueOf(bean.getFoodprice()+bean.getGameprice());
			conts[i][4] = String.valueOf(bean.isFoodstate());
		}
		DefaultTableModel model = new DefaultTableModel(conts, header) {
			public boolean isCellEditable(int i, int c) {return false;}
			
		};
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		orderTable = new JTable(model);
		pane = new JScrollPane(orderTable);
		
		Font fnt = new Font("Dialog", Font.BOLD, 15);
		orderTable.setFont(fnt);
		
		for (int i = 0; i < orderTable.getColumnCount(); i++) {
			orderTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			
		}
	}
	public static void main(String[] args) {
		OrderInfo orderinfo = new OrderInfo();
	}
}
