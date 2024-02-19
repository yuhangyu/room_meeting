package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class UsageHistoryUI extends JFrame implements ActionListener {
	JLabel usage_history_lb = new JLabel("�̿� ����");
	JLabel start_date_lb = new JLabel("�˻� ������");
	JLabel end_date_lb = new JLabel("�˻� ������");
	JButton ok_btn = new JButton("Ȯ��");
	JButton search_btn = new JButton("�˻�");
	JTable historyTable;
	JScrollPane historyPane;

	// ���� ��¥�� ����
	Date currentDate = new Date();
	Calendar calendar = Calendar.getInstance();
	
	// ���� ��¥�� �⺻������ ����
	int currentYear = calendar.get(Calendar.YEAR);
	int currentMonth = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH�� 0���� �����ϹǷ� 1�� ������
	int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
	
	SpinnerNumberModel spinnerNumberModel1 = new SpinnerNumberModel(currentYear, currentYear, 2030, 1);
	JSpinner YearSpinner1 = new JSpinner(spinnerNumberModel1);
	
	SpinnerNumberModel spinnerNumberModel2 = new SpinnerNumberModel(currentYear, currentYear, 2030, 1);
	JSpinner YearSpinner2 = new JSpinner(spinnerNumberModel2);
	
	SpinnerNumberModel spinnerNumberModel3 = new SpinnerNumberModel(currentMonth, 1, 12, 1);
	JSpinner MonthSpinner1 = new JSpinner(spinnerNumberModel3);
	
	SpinnerNumberModel spinnerNumberModel4 = new SpinnerNumberModel(currentMonth, 1, 12, 1);
	JSpinner MonthSpinner2 = new JSpinner(spinnerNumberModel4);
	
	SpinnerNumberModel spinnerNumberModel5 = new SpinnerNumberModel(currentDay, 1, 31, 1);
	JSpinner DaySpinner1 = new JSpinner(spinnerNumberModel5);
	
	SpinnerNumberModel spinnerNumberModel6 = new SpinnerNumberModel(currentDay, 1, 31, 1);
	JSpinner DaySpinner2 = new JSpinner(spinnerNumberModel6);

	UsageHistoryUI(){
		//��ư �̺�Ʈ �߰�
		ok_btn.addActionListener(this);
		search_btn.addActionListener(this);
		
		setTitle("�� ����");
		setSize(1000, 500);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�

		calendar.setTime(currentDate);
		
		usage_history_lb.setBounds(20,10,200,40);
		start_date_lb.setBounds(40, 50, 150, 50);
		end_date_lb.setBounds(420, 50, 150, 50);
		ok_btn.setBounds(800, 60, 140, 70);
		search_btn.setBounds(640, 60, 140, 70);
		
		viewhistoryPane(); // ���̺� Ȱ��ȭ �޼��� 
		historyPane.setBounds(40, 150, 905, 270); 

		YearSpinner1.setBounds(40, 100, 50, 30);
		YearSpinner2.setBounds(420, 100, 50, 30);
		MonthSpinner1.setBounds(100, 100, 40, 30);
		MonthSpinner2.setBounds(480, 100, 40, 30);
		DaySpinner1.setBounds(150, 100, 40, 30);
		DaySpinner2.setBounds(530, 100, 40, 30);
		
		Font font = new Font("Dialog", Font.BOLD, 27);
		Font font1 = new Font("Dialog", Font.BOLD, 18);

		usage_history_lb.setFont(font);
		start_date_lb.setFont(font1);
		end_date_lb.setFont(font1);
		
		c.add(usage_history_lb);
		c.add(start_date_lb);
		c.add(end_date_lb);
		c.add(YearSpinner1);
		c.add(YearSpinner2);
		c.add(MonthSpinner1);
		c.add(MonthSpinner2);
		c.add(DaySpinner1);
		c.add(DaySpinner2);
		c.add(ok_btn);
		c.add(search_btn);
		c.add(historyPane);

		historyTable.addMouseListener(new MouseAction());
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		// JSpinner�� ���Ǵ� DefaultFormatter ��������
		JFormattedTextField.AbstractFormatter formatter = ((JSpinner.DefaultEditor) YearSpinner1.getEditor()).getTextField().getFormatter();
		
		// AllowsInvalid�� false�� �����Ͽ� ��ȿ���� ���� ���� ������� �ʵ��� ��
		if (formatter instanceof DefaultFormatter) {
			((DefaultFormatter) formatter).setAllowsInvalid(false);
		}

		// JSpinner�� �����͸� �����Ͽ� ��ǥ�� ���� �������� ǥ�õǵ��� ��
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(YearSpinner1, "####");
		YearSpinner1.setEditor(editor);
		
		JSpinner.NumberEditor editor2 = new JSpinner.NumberEditor(YearSpinner2, "####");
		YearSpinner2.setEditor(editor2);
	}
	
	public void viewhistoryPane() {
		Vector<ReserveBean> vlist;
		MyInfoMgr mgr;
		
		mgr = new MyInfoMgr();
		vlist = mgr.reserveUser(LoginUI.ID);
		
		// ������ �� �÷��� �迭 ����
		String[] columnNames = {"����", "ID", "�̸�", "��ȭ��ȣ", "�̿� ��", "���� ��¥", "�̿� �ð�", "�̿� �ο�"};
		String[][] data = new String[vlist.size()][columnNames.length];
		
		//����, ���̵�, �̸�, ��ȭ��ȣ, �����ܾ� �� conts �迭�� ����
		for(int i = 0; i < vlist.size(); i++) {
			ReserveBean bean = vlist.get(i);
			data[i][0] = String.valueOf(i + 1);
			data[i][1] = bean.getResvid();
			data[i][2] = bean.getResvname();
			data[i][3] = bean.getResvphone();
			data[i][4] = bean.getResvroom();
			data[i][5] = bean.getResvtime();
			data[i][6] = String.valueOf(bean.getResvusetime());
			data[i][7] = String.valueOf(bean.getResvperson());
		}

		// DefaultTableModel�� ����Ͽ� JTable�� ������ ����
		//���̺� ���� ���� �Ұ���
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int i, int c){ return false; }
		};
		historyTable = new JTable(model);
		historyPane = new JScrollPane(historyTable);
		
		history();
	}
	
	public void history() {
		// ���� ��ũ�ѹ� ��Ȱ��ȭ
		historyPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
		//�÷� ������ ����
		historyTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		historyTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		historyTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		historyTable.getColumnModel().getColumn(3).setPreferredWidth(140);
		historyTable.getColumnModel().getColumn(4).setPreferredWidth(50);
		historyTable.getColumnModel().getColumn(5).setPreferredWidth(210);
		historyTable.getColumnModel().getColumn(6).setPreferredWidth(50);
		historyTable.getColumnModel().getColumn(7).setPreferredWidth(50);
		historyTable.setRowHeight(20);
		
		//ũ�� ���� �Ұ���
		historyTable.getTableHeader().setResizingAllowed(false);
		//��ġ ���� �Ұ���
		historyTable.getTableHeader().setReorderingAllowed(false);
	
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		for (int i = 0; i < historyTable.getColumnCount(); i++) {
			historyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj.equals(ok_btn)) {
			dispose();
		} else if (obj.equals(search_btn)) {
			reservesearch();
		}
	}

	public void reservesearch() {
		String syear = String.valueOf(YearSpinner1.getValue());
		String eyear = String.valueOf(YearSpinner2.getValue());
		String smonth = String.valueOf(MonthSpinner1.getValue());
		String emonth = String.valueOf(MonthSpinner2.getValue());
		String sday = String.valueOf(DaySpinner1.getValue());
		String eday = String.valueOf(DaySpinner2.getValue());
		
		if ("".equals(syear) || "".equals(eyear) || "".equals(smonth) || "".equals(emonth) || "".equals(sday) || "".equals(eday)) {
			errormsg();
			return;
		}
		
		if (Integer.parseInt(syear) == Integer.parseInt(eyear)) {
			if (Integer.parseInt(smonth) == Integer.parseInt(emonth)) {
				if (Integer.parseInt(sday) > Integer.parseInt(eday)) {
					errormsg();
					return;
				}
			} else if (Integer.parseInt(smonth) > Integer.parseInt(emonth)) {
				errormsg();
				return;
			}
		} else if (Integer.parseInt(syear) > Integer.parseInt(eyear)) {
			errormsg();
			return;
		}
		
		if (smonth.length() == 1) smonth = "0" + smonth;
		if (emonth.length() == 1) emonth = "0" + emonth;
		if (sday.length() == 1) sday = "0" + sday;
		if (eday.length() == 1) eday = "0" + eday;
		String startday = syear + "-" + smonth + "-" + sday + " 00:00:00";
		String endday = eyear + "-" + emonth + "-" + eday + " 23:59:59";
		
		Vector<ReserveBean> vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		vlist = mgr.reserveUserDetail(LoginUI.ID, startday, endday);
		
		// ������ �� �÷��� �迭 ����
		String[] columnNames = {"����", "ID", "�̸�", "��ȭ��ȣ", "�̿� ��", "���� ��¥", "�̿� �ð�", "�̿� �ο�"};
		String[][] data = new String[vlist.size()][columnNames.length];
		
		for (int i = 0; i < vlist.size(); i++) {
			ReserveBean bean = vlist.get(i);
			data[i][0] = String.valueOf(i + 1);
			data[i][1] = bean.getResvid();
			data[i][2] = bean.getResvname();
			data[i][3] = bean.getResvphone();
			data[i][4] = bean.getResvroom();
			data[i][5] = bean.getResvtime();
			data[i][6] = String.valueOf(bean.getResvusetime());
			data[i][7] = String.valueOf(bean.getResvperson());
		}
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int i, int c){ return false; }
		};
		historyTable.setModel(model);
		history();
	}
	
	public void errormsg() {
		JOptionPane optionPane;
		JDialog dialog;
		
		optionPane = new JOptionPane("��¥ ���� �����Դϴ�.", JOptionPane.ERROR_MESSAGE);
		dialog = optionPane.createDialog(null, "���� �ȳ�");
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	private class MouseAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = historyTable.getSelectedRow();
				TableModel model = historyTable.getModel();
				String room = String.valueOf(model.getValueAt(row,  4));
				String in_time = String.valueOf(model.getValueAt(row,  5));
				int use_time = Integer.parseInt((String)model.getValueAt(row,  6));
				UseDetailUI udui = new UseDetailUI(room, in_time, use_time);
			}
			super.mouseClicked(e);
		}
	}
	
	public static void main(String[] args) {
		
	}
}