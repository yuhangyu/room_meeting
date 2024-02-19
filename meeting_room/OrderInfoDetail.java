package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class OrderInfoDetail extends JFrame {
    
    JTable ODT;
    JScrollPane pane;
    JLabel room;
    JLabel requestlb;
    JTextArea requestta;
    String[][] name;
    
    public OrderInfoDetail(String no, String id, String time, int num, String request) {

        setSize(730, 500);
        setTitle(no + " - �ֹ� ���");

        Container c = getContentPane();
        c.setLayout(null);
        
        orderdetail(no, id, time, num, request);
        
        room = new JLabel(no + " �� �ֹ� ���");
        requestlb = new JLabel("�ֹ� ��û ����");
        
		Font font = new Font("Dialog", Font.BOLD, 20);
		room.setFont(font);
		requestlb.setFont(font);

		requestta.setEditable(false);
		
        pane.setBounds(10, 50, 700, 400);
        room.setBounds(10, 0, 200, 50);
        requestlb.setBounds(300, 10, 150, 40);
        requestta.setBounds(450, 20, 250, 20);
        
        c.add(pane);
        c.add(room);
        c.add(requestlb);
        c.add(requestta);
        
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
    public void orderdetail(String no, String id, String time, int num, String request) {
		requestta = new JTextArea();
    	if (request != null) {
    		requestta.setText(request);
    	}
    	
        Vector<OrderInfoBean> foodList;
        Vector<OrderInfoBean> gameList;
        
        OrderInfoMgr foodMgr = new OrderInfoMgr();
        OrdMgr gameMgr = new OrdMgr();
    	MyInfoMgr mgr = new MyInfoMgr();
        
        foodList = foodMgr.orderfood();
        gameList = gameMgr.ordergame();
        
        // �����͸� ���� ���� �� ��� ����
        Vector<Vector<String>> data = new Vector<>();
        Vector<String> header = new Vector<>();
        header.add("����");
        header.add("�з�");
        header.add("�ֹ� �ð�");
        header.add("�ֹ��� �׸�");
        header.add("����");
        header.add("����");
        header.add("����");
        
        int a = 1, b = 1;
        
        // ���� ������ �߰�
	        for (int i = 0; i < foodList.size(); i++) {
	            OrderInfoBean bean = foodList.get(i);
	            Vector<String> row = new Vector<>();
	            if (bean.getOrdertime().equals(time)&&bean.getRoom_no().equals(no)) {
	            	Vector<FoodBean> flist;
	            	
		            row.add(String.valueOf(a));
		            row.add("����");
		            row.add(bean.getOrdertime());
	            	
	            	flist = mgr.foodAll();
	            	for (int j = 0; j < flist.size(); j++) {
	            		FoodBean fbean = flist.get(j);
	            		if (fbean.getFood().equals(bean.getFoodname()))
	            			row.add(fbean.getFname() + " (" + bean.getFoodname() + ")");
	            	}
		            row.add(String.valueOf(bean.getFoodcount()));
		            row.add(String.valueOf(bean.getFoodprice()));
		            row.add(bean.isFoodstate() ? "�Ϸ�" : "�غ���...");
		            data.add(row);
		            a++;
		        }
	        }
	        
        // ���� ������ �߰�
        
	        for (int i = 0; i < gameList.size(); i++) {
	            OrderInfoBean bean = gameList.get(i);
	            Vector<String> row = new Vector<>();
	            if (bean.getOrdertime().equals(time) && bean.getRoom_no().equals(no)) {	 
	            	Vector<GameBean> glist;
		            row.add(String.valueOf(b));
		            row.add("����");
		            row.add(bean.getOrdertime());
		            
		            glist = mgr.gameAll();
		            for (int j = 0; j < glist.size(); j++) {
		            	GameBean gbean = glist.get(j);
		            	if (gbean.getGame().equals(bean.getGamename())) {
		            		row.add(gbean.getGname() + " (" + bean.getGamename() + ")");
		            		break;
		            	}
		            }
		            
		            row.add(String.valueOf(bean.getGamecount()));
		            row.add(String.valueOf(bean.getGameprice()));
		            row.add(bean.isGamestate() ? "�Ϸ�" : "�غ���...");
		            data.add(row);
		            b++;
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
        ODT.getColumnModel().getColumn(6).setPreferredWidth(50);
        ODT.setRowHeight(30);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int j = 0; j < ODT.getColumnCount(); j++) {
            ODT.getColumnModel().getColumn(j).setCellRenderer(centerRenderer);
        }

        // ���� Ŭ�� �̺�Ʈ �߰�
        ODT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int column = 6; // ���� Ŭ���� ���� �ε���
                    int row = ODT.getSelectedRow();
                    if (row != -1) {
                        // ���� Ŭ���� ���� ������ ó��
                        String selectedStatus = (String) ODT.getValueAt(row, column);
                        if (selectedStatus.equals("�غ���...")) {
                            // ���̺� ���� ������
                            DefaultTableModel model = (DefaultTableModel) ODT.getModel();
                            OrderInfoBean bean = new OrderInfoBean();
                            OrderInfoMgr mgr = new OrderInfoMgr();
                            
                            OrdMgr mgr1 = new OrdMgr();
                            
                            String asdf = (String) ODT.getValueAt(row, 3);
                            asdf = asdf.split("\\(")[1];
                            asdf = asdf.replaceAll("\\)", "");
                            if (asdf.charAt(0) == 'F') {
	                            bean.setFoodstate(true);
	                            bean.setRoom_no(no);
	                            bean.setFoodid(id);
	                            bean.setFoodname(asdf);
	                            bean.setOrdertime(time);
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
	                                    model1.setValueAt("�Ϸ�", num, 5);
	                            	}
	                            }
	                            
                            }else if (asdf.charAt(0) == 'G') {
                            	bean.setGamestate(true);
                            	bean.setRoom_no(no);
	                            bean.setGameid(id);
	                            bean.setGamename(asdf);
	                            bean.setOrdertime(time);
                            	if(mgr1.update(bean)) { 
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
	                                    model1.setValueAt("�Ϸ�", num, 5);
	                            	}
                            	}
                            }
                        }
                    }
                }
            }
        });
    }  
    
    public static void main(String[] args) {
	    
	}

}
