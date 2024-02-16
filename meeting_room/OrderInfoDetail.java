package meeting_room;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public OrderInfoDetail(String no, String id, String time, int num) {

        setSize(800,400);
        setTitle(no);
        
        
        c.setLayout(null);
        orderdetail(no,id, time, num);

        pane.setBounds(0, 0, 700, 400);
        
        
        c.add(pane);
        
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    public void orderdetail(String no, String id, String time, int num) {

        Vector<OrderInfoBean> foodList;
        Vector<OrderInfoBean> gameList;
        
        OrderInfoMgr foodMgr = new OrderInfoMgr();
        OrdMgr gameMgr = new OrdMgr();
        
        foodList = foodMgr.orderfood();
        gameList = gameMgr.ordergame();
        
        // �����͸� ���� ���� �� ��� ����
        Vector<Vector<String>> data = new Vector<>();
        Vector<String> header = new Vector<>();
        header.add("����");
        header.add("time");
        header.add("�ֹ��� �׸�");
        header.add("����");
        header.add("����");
        header.add("����");
        

        // ���� ������ �߰�
	        for (int i = 0; i < foodList.size(); i++) {
	            OrderInfoBean bean = foodList.get(i);
	            Vector<String> row = new Vector<>();
	            if (bean.getOrdertime().equals(time)&&bean.getRoom_no().equals(no)) {
		            row.add(String.valueOf(i + 1));
		            row.add(bean.getOrdertime());
		            row.add(bean.getFoodname());
		            row.add(String.valueOf(bean.getFoodcount()));
		            row.add(String.valueOf(bean.getFoodprice()));
		            row.add(bean.isFoodstate() ? "�Ϸ�" : "�غ���...");
		            data.add(row);
		            
		        }
	        }
	        
        // ���� ������ �߰�
        
	        for (int i = 0; i < gameList.size(); i++) {
	            OrderInfoBean bean = gameList.get(i);
	            Vector<String> row = new Vector<>();
	            if (bean.getOrdertime().equals(time) && bean.getRoom_no().equals(no)) {
	            row.add(String.valueOf(i + 1));
	            row.add(bean.getOrdertime());
	            row.add(bean.getGamename());
	            row.add(String.valueOf(bean.getGamecount()));
	            row.add(String.valueOf(bean.getGameprice()));
	            row.add(bean.isGamestate() ? "�Ϸ�" : "�غ���...");
	            data.add(row);
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
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        for (int j = 0; j < ODT.getColumnCount(); j++) {
            ODT.getColumnModel().getColumn(j).setCellRenderer(centerRenderer);
        }
        
        // ��ũ�� �г� ����
        pane = new JScrollPane(ODT);
        
        // ���� Ŭ�� �̺�Ʈ �߰�
        ODT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int column = 5; // ���� Ŭ���� ���� �ε���
                    int row = ODT.getSelectedRow();
                    if (row != -1) {
                        // ���� Ŭ���� ���� ������ ó��
                        String selectedStatus = (String) ODT.getValueAt(row, column);
                        if (selectedStatus.equals("�غ���...")) {
                            // ���̺� ���� ������
                            DefaultTableModel model = (DefaultTableModel) ODT.getModel();
                            // �ش� ���� ���¸� "�Ϸ�"�� ����
                            OrderInfoBean bean = new OrderInfoBean();
                            OrderInfoMgr mgr = new OrderInfoMgr();
                            
                            OrdMgr mgr1 = new OrdMgr();
                            
                            String asdf = (String) ODT.getValueAt(row, 2);
                            if (asdf.charAt(0) == 'F') {
	                            bean.setFoodstate(true);
	                            bean.setRoom_no(no);
	                            bean.setFoodid(id);
	                            bean.setFoodname(asdf);
	                            if(mgr.update(bean)) { 
	                            	model.setValueAt("�Ϸ�", row, column);
	                            	for(int i = 0; i < data.size();i++) {
	                                    String fost = (String) ODT.getValueAt(i, column);
	                            		if(fost != "�Ϸ�") return ;

	                            	}
	                            	OrderBean orderbean = new OrderBean();
	                            	orderbean.setOrder_state(true);
	                            	orderbean.setOrder_id(id);
	                            	orderbean.setOrder_room(no);
	                            	orderbean.setOrder_time(time);
	                            	OrderMgr mgr2 = new OrderMgr();
	                            	if(mgr2.update(orderbean)) {
	                                    DefaultTableModel model1 = (DefaultTableModel) OrderInfo.orderTable.getModel();
	                                    model1.setValueAt("�Ϸ�", num, column);
	                            	}
	                            }
	                            
                            }else if (asdf.charAt(0) == 'G') {
                            	bean.setGamestate(true);
                            	bean.setRoom_no(no);
	                            bean.setGameid(id);
	                            bean.setGamename(asdf);
                            	if(mgr1.update(bean)) { 
	                            	model.setValueAt("�Ϸ�", row, column);
                            	}
                            }
                        }
                    }
                }
            }
        });
    }  
    
    public static void main(String[] args) {
		OrderInfo as = new OrderInfo();
	}

}
