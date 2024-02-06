package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
// JSpinner ���
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatter;

import java.util.Calendar;
import java.util.Date;


public class UsageHistoryUI extends JFrame implements ActionListener{
	private UsageHistory uh;
	
	JLabel usage_history_lb = new JLabel("�̿� ����");
	JLabel start_date_lb = new JLabel("�˻� ������");
	JLabel end_date_lb = new JLabel("�˻� ������");
	JButton ok_btn = new JButton("Ȯ��");
	JButton search_btn = new JButton("�˻�");
	
    
    JTable historyTable;
    JScrollPane historyPane;
    
        
	UsageHistoryUI(){
		uh = new UsageHistory(this);
		
		//��ư �̺�Ʈ �߰�
		ok_btn.addActionListener(uh);
		search_btn.addActionListener(uh);
		
		setTitle("�� ����");
		setSize(850, 500);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		usage_history_lb.setBounds(20,  10,  200,  40);
		start_date_lb.setBounds(40, 50, 150, 50);
		end_date_lb.setBounds(420, 50, 150, 50);
		ok_btn.setBounds(640, 320, 140, 70);
		search_btn.setBounds(640, 60, 140, 70);
		
		viewhistoryPane(); // ���̺� Ȱ��ȭ �޼��� 
		historyPane.setBounds(40, 150, 550, 270); 
        
      
		
		
		// ���� ��¥�� ����
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        
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
		// ������ �� �÷��� �迭 ����
	    String[] columnNames = {"���� ��ȣ", "ID", "�̸�", "��ȭ��ȣ", "�̿� ��", "���� ��¥", "�̿� �ð�", "�̿� �ο�"};
	    String[][] data = {{"TEST_01", "admin", "�ΰ��ְ��Ϳ��", "010-0000-0000", "2�� 1��", "2024-02-06", "2�ð�", "3��"}};
	
	    // DefaultTableModel�� ����Ͽ� JTable�� ������ ����
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    historyTable = new JTable(model);
	    historyPane = new JScrollPane(historyTable);

	    
	    
	    
	    // ���� ��ũ�ѹ� ��Ȱ��ȭ
	    historyPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	    // �÷� �ʺ� ����
	    historyTable.getColumnModel().getColumn(0).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(1).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(2).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(3).setPreferredWidth(140);
	    historyTable.getColumnModel().getColumn(4).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(5).setPreferredWidth(120);
	    historyTable.getColumnModel().getColumn(6).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(7).setPreferredWidth(80);

	    // �� ���� ����
	    historyTable.setRowHeight(20);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < historyTable.getColumnCount(); i++) {
            historyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {
		UsageHistoryUI uh = new UsageHistoryUI();
		

	}

	

}
