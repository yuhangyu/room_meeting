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
        Vector<OrderInfoBean> foodList;
        Vector<OrderInfoBean> gameList;
        OrderInfoMgr foodMgr = new OrderInfoMgr();
        foodList = foodMgr.orderfood();
        OrdMgr gameMgr = new OrdMgr();
        gameList = gameMgr.ordergame();
        
        // �����͸� ���� ���� �� ��� ����
        Vector<Vector<String>> data = new Vector<>();
        Vector<String> header = new Vector<>();
        header.add("����");
        header.add("����");
        header.add("�ֹ��� �׸�");
        header.add("����");
        header.add("����");
        header.add("����");
        
        // ���� ������ �߰�
        for (int i = 0; i < foodList.size(); i++) {
            OrderInfoBean bean = foodList.get(i);
            Vector<String> row = new Vector<>();
            row.add("����");
            row.add(String.valueOf(i + 1));
            row.add(bean.getFoodname());
            row.add(String.valueOf(bean.getFoodcount()));
            row.add(String.valueOf(bean.getFoodprice()));
            row.add(bean.isFoodstate() ? "�Ϸ�" : "�غ���...");
            data.add(row);
        }
        
        // ���� ������ �߰�
        for (int i = 0; i < gameList.size(); i++) {
            OrderInfoBean bean = gameList.get(i);
            Vector<String> row = new Vector<>();
            row.add("����");
            row.add(String.valueOf(i + 1));
            row.add(bean.getGamename());
            row.add(String.valueOf(bean.getGamecount()));
            row.add(String.valueOf(bean.getGameprice()));
            row.add(bean.isGamestate() ? "�Ϸ�" : "�غ���...");
            data.add(row);
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
                            model.setValueAt("�Ϸ�", row, column);
              
                            
                        }
                    }
                }
            }
        });
    }
}
