package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ReserveInfoUI extends JFrame implements ActionListener {
	JTable reservetable;
	JScrollPane pane;
	static JButton jb = new JButton(" ");
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
	
	Container c = getContentPane();
	int a = 0;
	boolean flag = false;
	String header[] = {"��ȣ", "���̵�", "�̸�", "��ȭ��ȣ","�� ��ȣ", "���� ��¥", "�̿� �ð�", "�̿� �ο�"};
	String [][] conts;
	
	public ReserveInfoUI() {
		setSize(1000,440);
		setTitle("���� ���� ����");
		
		c.setLayout(null);
		
		prviewlist();
		
		pane.setBounds(0,0,986,440);	
		
		jb.addActionListener(this);
		
		c.add(jb);

		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jb) {
			dispose();
			ReserveInfoUI RIUI = new ReserveInfoUI();
		}
	}
	
	private class MouseAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = reservetable.getSelectedRow();
				
				if (row != -1) {
					TableModel tm = reservetable.getModel();
					String time = (String)tm.getValueAt(row, 5) + " " + (String) tm.getValueAt(row, 6);
					
					int option = JOptionPane.showOptionDialog(null, "�ش� ������ ����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"�������", "���"}, "���");
					
					if (option == JOptionPane.YES_OPTION) {
						try {
							Date resvday = originalFormat.parse(time);
							Date currentDate = new Date();
							
							if (resvday.before(currentDate)) {
								int option1 = JOptionPane.showOptionDialog(null, "�̹� ���۵� �����Դϴ�.\n���� �ش� ������ ����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"�������", "���"}, "���");
								if (option1 == JOptionPane.YES_OPTION) {
									cancelresv(row);
								} else {
									return;
								}
							} else {
								cancelresv(row);
							}
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			super.mouseClicked(e);
		}
	}
	
	public void prviewlist() {		
		Vector<ReserveBean>vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		
		vlist = mgr.reserveAll();
		for (int i = 0; i < vlist.size(); i++) {
			ReserveBean bean = vlist.get(i);
			
			if (getlist(bean)) a++;
		}
		
		String [][] conts = new String[a][header.length];
		money = new int[a];
		
		a = 0;
		
		for (int i = 0; i < vlist.size(); i++) {
			ReserveBean bean = vlist.get(i);
			
			if (getlist(bean)) {
				conts[a][0] = String.valueOf(a + 1);
				conts[a][1] = bean.getResvid();
				conts[a][2] = bean.getResvname();
				conts[a][3] = bean.getResvphone();
				conts[a][4] = bean.getResvroom();
				conts[a][5] = bean.getResvtime();
				conts[a][6] = String.valueOf(bean.getResvusetime());
				conts[a][7] = String.valueOf(bean.getResvperson());
				money[a] = bean.getResvtotal();
				a++;
			}
		}
				
		DefaultTableModel model = new DefaultTableModel(conts, header) {
			public boolean isCellEditable(int i, int c) {return false;}
		};
		
		if (reservetable != null) {
			model = (DefaultTableModel) reservetable.getModel();  // ���� �� ��������
			model.setRowCount(0);  // ���� �� �ʱ�ȭ
			
			a = 0;
			for (int i = 0; i < vlist.size(); i++) {
				ReserveBean bean = vlist.get(i);
				if (getlist(bean)) {
					model.addRow(conts[a]);  // ���ο� �����ͷ� �� ������Ʈ
					a++;
				}
			}
		} else {
			reservetable = new JTable(model);
			pane = new JScrollPane(reservetable);
		}
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		
		Font font = new Font("Dialog",Font.BOLD, 16);
		reservetable.setFont(font);
		
		reservetable.getTableHeader().setResizingAllowed(false);
		reservetable.getTableHeader().setReorderingAllowed(false);
		
		reservetable.getColumnModel().getColumn(0).setPreferredWidth(20);
		reservetable.getColumnModel().getColumn(1).setPreferredWidth(50);
		reservetable.getColumnModel().getColumn(2).setPreferredWidth(40);
		reservetable.getColumnModel().getColumn(3).setPreferredWidth(40);
		reservetable.getColumnModel().getColumn(4).setPreferredWidth(10);
		reservetable.getColumnModel().getColumn(5).setPreferredWidth(80);
		reservetable.getColumnModel().getColumn(6).setPreferredWidth(10);
		reservetable.getColumnModel().getColumn(7).setPreferredWidth(10);
		reservetable.setRowHeight(30);
		
		for (int i = 0; i < reservetable.getColumnCount(); i++) {
			reservetable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		reservetable.addMouseListener(new MouseAction());
		pane.setBounds(0,0,986,440);
		c.add(pane);
	}
	
	public boolean getlist(ReserveBean bean) {
		flag = false;
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
			
			LocalDate currentDate = LocalDate.now();
			LocalDate resvDate = LocalDate.parse(formattedDate);
			
			if (resvDate.isAfter(currentDate) || resvDate.equals(currentDate)) {
				flag = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public void cancelresv(int row) {
		MyInfoMgr mgr = new MyInfoMgr();
		ReserveBean bean = new ReserveBean();
		bean.setResvid((String) reservetable.getValueAt(row, 1));
		bean.setResvroom((String) reservetable.getValueAt(row, 4));
		bean.setResvtime((String) reservetable.getValueAt(row, 5));
		
		String id = bean.getResvid();
		String room = bean.getResvroom();
		String time2 = bean.getResvtime();
		
		MyInfoBean bean2 = mgr.select(id);
		int usermoney = bean2.getMoney();
		
		bean2.setID(id);
		bean2.setMoney(usermoney + money[row]);

		if (mgr.cancelresv(id, room, time2) && mgr.charge(bean2)) {
			JOptionPane.showMessageDialog(null, "������ ��ҵǾ����ϴ�.");
			prviewlist();
		}
	}
	
	public static void main(String[] args) {
		new ReserveInfoUI();
	}
}