package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class SalesDetailUI extends JFrame implements ActionListener {
	JLabel sales_history_lb = new JLabel("�Ǹ� ����");
	JLabel start_date_lb = new JLabel("�˻� ������");
	JLabel end_date_lb = new JLabel("�˻� ������");
	JButton ok_btn = new JButton("Ȯ��");
	JButton search_btn = new JButton("�˻�");
	
	JTable foodTable;
	JScrollPane foodPane;
	
	JTable gameTable;
	JScrollPane gamePane;
	
	
	
	
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
	
	SalesDetailUI(){
		//��ư �̺�Ʈ �߰�
		ok_btn.addActionListener(this);
		search_btn.addActionListener(this);
		
		setTitle("�Ǹ� ����");
		setSize(1000, 500);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
	
		calendar.setTime(currentDate);
		
		sales_history_lb.setBounds(20,10,200,40);
		start_date_lb.setBounds(40, 50, 150, 50);
		end_date_lb.setBounds(420, 50, 150, 50);
		ok_btn.setBounds(800, 60, 140, 70);
		search_btn.setBounds(640, 60, 140, 70);
		
		viewSalesPane(); // ���̺� Ȱ��ȭ �޼��� 
		foodPane.setBounds(40, 150, 430, 270); 
		gamePane.setBounds(500, 150, 430, 270);

		YearSpinner1.setBounds(40, 100, 50, 30);
		YearSpinner2.setBounds(420, 100, 50, 30);
		MonthSpinner1.setBounds(100, 100, 40, 30);
		MonthSpinner2.setBounds(480, 100, 40, 30);
		DaySpinner1.setBounds(150, 100, 40, 30);
		DaySpinner2.setBounds(530, 100, 40, 30);
		
		Font font = new Font("Dialog", Font.BOLD, 27);
		Font font1 = new Font("Dialog", Font.BOLD, 18);

		sales_history_lb.setFont(font);
		start_date_lb.setFont(font1);
		end_date_lb.setFont(font1);
		
		c.add(sales_history_lb);
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
		c.add(foodPane);
		c.add(gamePane);
		
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
	
	public void viewSalesPane() {
		// ������ �� �÷��� �迭 ����
		String[] columnNames = {"����", "����", "�� ����"};
		String[][] data = {};
		
		String[] columnNames1 = {"�������", "����", "�� ����"};
		String[][] data1 = {};
		
		// DefaultTableModel�� ����Ͽ� JTable�� ������ ����
		//���̺� ���� ���� �Ұ���
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int i, int c){ return false; }
		};
		
		DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1) {
			public boolean isCellEditable(int i, int c){ return false; }
		};
		
		
		foodTable = new JTable(model);
		foodPane = new JScrollPane(foodTable);
		
		gameTable = new JTable(model1);
		gamePane = new JScrollPane(gameTable);
				
		sales();
	}
	
	public void sales() {
		// ���� ��ũ�ѹ� ��Ȱ��ȭ
		foodPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		gamePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);	
			
		//�÷� ������ ����
		foodTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		foodTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		foodTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		
		foodTable.setRowHeight(20);
		
		gameTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		gameTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		gameTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		
		gameTable.setRowHeight(20);
		
				
		//ũ�� ���� �Ұ���
		foodTable.getTableHeader().setResizingAllowed(false);
		//��ġ ���� �Ұ���
		foodTable.getTableHeader().setReorderingAllowed(false);
		
		//ũ�� ���� �Ұ���
		gameTable.getTableHeader().setResizingAllowed(false);
		//��ġ ���� �Ұ���
		gameTable.getTableHeader().setReorderingAllowed(false);
		
		
			
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
				
		for (int i = 0; i < foodTable.getColumnCount(); i++) {
			foodTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
				
		DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
		centerRenderer1.setHorizontalAlignment(JLabel.CENTER);
					
		for (int i = 0; i < gameTable.getColumnCount(); i++) {
			gameTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer1);	
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj.equals(ok_btn)) {
			dispose();
		} else if (obj.equals(search_btn)) {
			salessearch();
		}
		
		
	}
	
	public void salessearch() {
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
		
		
		// ������ �� �÷��� �迭 ����
		String[] columnNames = {"����", "����", "�� ����"};
		String[][] data = {};
		
		String[] columnNames1 = {"�������", "����", "�� ����"};
		String[][] data1 = {};
		

		
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int i, int c){ return false; }
		};
		foodTable.setModel(model);
		
		DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1) {
			public boolean isCellEditable(int i, int c){ return false; }
		};
		gameTable.setModel(model1);
		
		
		
		sales();
		
	}
	
	public void errormsg() {
		JOptionPane optionPane;
		JDialog dialog;
		
		optionPane = new JOptionPane("��¥ ���� �����Դϴ�.", JOptionPane.ERROR_MESSAGE);
		dialog = optionPane.createDialog(null, "���� �ȳ�");
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	


	public static void main(String[] args) {
		SalesDetailUI sd = new SalesDetailUI();
	}

	

}
