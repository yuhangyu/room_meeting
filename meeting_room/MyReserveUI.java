package meeting_room;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MyReserveUI extends JFrame implements ActionListener, ListSelectionListener{
	JLabel myReserve_lb = new JLabel("�� ��������");
	JButton reserveCancel_btn = new JButton("���� ���");
	JButton reserve_btn = new JButton("�� ����");
	JButton ok_btn = new JButton("Ȯ��");
	
	JTable reserveTable;
	JScrollPane reservePane;
	
	int[] money;
	
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
	
	public MyReserveUI() {
		setTitle("�� ��������");
		setSize(712, 720);

		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null);//������ �г� �ʱ�ȭ
		
		// �ΰ� �߰�
		ImageIcon logoIcon = new ImageIcon("meeting_room/logo.jpg");
		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setBounds(0, 20, 700, 270);
		c.add(logoLabel);
		
		viewlist();
		
		reservePane.setBounds(30, 380, 340, 250);
				
		c.add(reservePane);		
		
		reserveTable.getSelectionModel().addListSelectionListener(this);
		
		myReserve_lb.setBounds(25, 315, 500, 40);
		
		reserveCancel_btn.setBounds(430, 315, 200, 100);
		reserve_btn.setBounds(430, 435, 200, 100);
		ok_btn.setBounds(430, 555, 200, 100);
		
		//Font ����
		Font font = new Font("Dialog", Font.BOLD, 32); // ��Ʈ����
		Font font1 = new Font("Dialog", Font.PLAIN, 18);
		
		myReserve_lb.setFont(font);
		myReserve_lb.setForeground(Color.BLUE);
		
		reserve_btn.setFont(font1);
		reserveCancel_btn.setFont(font1);
		ok_btn.setFont(font1);
		
		c.add(myReserve_lb);
		c.add(reserve_btn);
		c.add(reserveCancel_btn);
		c.add(ok_btn);
		
		reserveCancel_btn.setEnabled(false);
		reserve_btn.addActionListener(this);
		reserveCancel_btn.addActionListener(this);
		ok_btn.addActionListener(this);
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// ����ڰ� � ���� �����ߴ��� Ȯ��
		if (!e.getValueIsAdjusting() && reserveTable.getSelectedRow() != -1) {
			// Ư�� ���� ���õǾ��� �� ���� ��� ��ư Ȱ��ȭ
			reserveCancel_btn.setEnabled(true);
		} else {
			// �ƹ� �൵ ���õ��� �ʾ��� �� ���� ��� ��ư ��Ȱ��ȭ
			reserveCancel_btn.setEnabled(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == reserve_btn) {
			ReserveUI rui = new ReserveUI();
			this.dispose();
		} else if (obj == reserveCancel_btn) {
			int row = reserveTable.getSelectedRow();
			TableModel tm = reserveTable.getModel();
			String time = (String)tm.getValueAt(row, 0) + " " + (String) tm.getValueAt(row,1);
			
			try {
				Date resvday = originalFormat.parse(time);
				Date currentDate = new Date();
				
				if (resvday.before(currentDate)) {
					JOptionPane.showMessageDialog(this, "�ð��� ���� ������ ����� �� �����ϴ�.");
					return;
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			int option = JOptionPane.showOptionDialog(null, "�ش� ������ ����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"�������", "���"}, "���");
			
			if (option == JOptionPane.YES_OPTION) {
				MyInfoMgr mgr = new MyInfoMgr();
				ReserveBean bean = new ReserveBean();
				bean.setResvid(LoginUI.ID);
				bean.setResvroom((String) reserveTable.getValueAt(row, 3));
				bean.setResvtime(time);
				
				String id = bean.getResvid();
				String room = bean.getResvroom();
				String time2 = bean.getResvtime();
				
				MyInfoBean bean2 = mgr.select(id);
				int usermoney = bean2.getMoney();
				
				bean2.setID(id);
				bean2.setMoney(usermoney + money[row]);
				
				if (mgr.cancelresv(id, room, time2) && mgr.charge(bean2)) {
					JOptionPane.showMessageDialog(null, "������ ��ҵǾ����ϴ�.");
					mgr.select(bean2.getID());
					MainPageUI.balance_value_lb.setText(String.valueOf(bean2.getMoney()) + "��");
					RechargeUI.recharge_value_tf.setText("0");
					RechargeUI.cancel_btn.doClick();
					viewlist();
				}
			}
		} else if (obj == ok_btn) {
			this.dispose();
		}
	}
	
	public void viewlist() {
		//���̺� �߰�
		Date currentDate = new Date();
		Vector<ReserveBean> reservelist;
		MyInfoMgr mgr = new MyInfoMgr();
		reservelist = mgr.reserveAll();
		 
		String loginID = LoginUI.ID;
		
		int a = 0;
		money = new int[reservelist.size()];
		
		for(int i = 0; i < reservelist.size(); i++ ) {
			ReserveBean bean = reservelist.get(i);
			try {
				reservationDate = originalFormat.parse(bean.getResvtime());
				calendar = Calendar.getInstance();
				calendar.setTime(reservationDate);
				calendar.add(Calendar.HOUR_OF_DAY, bean.getResvusetime());
				
				EndformattedDate = originalFormat.format(calendar.getTime());
				EndformattedDate2 = newFormat2.format(calendar.getTime());
				endtime = originalFormat.parse(EndformattedDate);
				formattedDate = newFormat.format(reservationDate);
				formattedDate2 = newFormat2.format(reservationDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(loginID.equals(bean.getResvid())) {
				if(endtime.after(currentDate)) {
					a++;
				}
			}
		}

		// ������ �� �÷��� �迭 ����
		String[] columnNames = {"���� ��¥", "���� �ð�", "���� �ð�", "���� ��"};
		String[][] data = new String[a][columnNames.length];
		
		//reservelist�� ��¥�� ���� ����
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
		
		a = 0;
		for(int i = 0; i < reservelist.size(); i++ ) {
			ReserveBean bean = reservelist.get(i);
			try {
				reservationDate = originalFormat.parse(bean.getResvtime());
				calendar = Calendar.getInstance();
				calendar.setTime(reservationDate);
				calendar.add(Calendar.HOUR_OF_DAY, bean.getResvusetime());
				
				EndformattedDate = originalFormat.format(calendar.getTime());
				EndformattedDate2 = newFormat2.format(calendar.getTime());
				endtime = originalFormat.parse(EndformattedDate);
				formattedDate = newFormat.format(reservationDate);
				formattedDate2 = newFormat2.format(reservationDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(loginID.equals(bean.getResvid())) {
				if(endtime.after(currentDate)) {
					data[a][0] = formattedDate;
					data[a][1] = formattedDate2;
					data[a][2] = EndformattedDate2;
					data[a][3] = bean.getResvroom();
					money[a] = bean.getResvtotal();
					a++;
				}
			}
		}
		
		// DefaultTableModel�� ����Ͽ� JTable�� ������ ����
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		if (reserveTable != null) {
			model = (DefaultTableModel) reserveTable.getModel();// ���� �� ��������
			model.setRowCount(0);// ���� �� �ʱ�ȭ
			
			for (int i = 0; i < a; i++) {
				model.addRow(data[i]);// ���ο� �����ͷ� �� ������Ʈ
			}
		} else {
			reserveTable = new JTable(model);
			reserveTable.getTableHeader().setReorderingAllowed(false);
			reserveTable.getTableHeader().setResizingAllowed(false);
			reserveTable.setDefaultEditor(Object.class, null);
			
			reservePane = new JScrollPane(reserveTable);
		}
		// ���� ��ũ�ѹ� ��Ȱ��ȭ
		reservePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// �÷� �ʺ� ����
		reserveTable.getColumnModel().getColumn(0).setPreferredWidth(90);
		reserveTable.getColumnModel().getColumn(1).setPreferredWidth(90);
		reserveTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		reserveTable.getColumnModel().getColumn(3).setPreferredWidth(70);

		// �� ���� ����
		reserveTable.setRowHeight(20);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < reserveTable.getColumnCount(); i++) {
			reserveTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}
	
	public static void main(String[] args) {
		
	}
}