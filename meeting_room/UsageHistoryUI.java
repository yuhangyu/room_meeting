package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;


// JSpinner ���
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultFormatter;

import java.util.Calendar;
import java.util.Date;


public class UsageHistoryUI extends JFrame implements ActionListener{
	JLabel usage_history_lb = new JLabel("�̿� ����");
	JLabel start_date_lb = new JLabel("�˻� ������");
	JLabel end_date_lb = new JLabel("�˻� ������");
	JButton ok_btn = new JButton("Ȯ��");
	JButton search_btn = new JButton("�˻�");
	
	UsageHistoryUI(){
		setTitle("�� ����");
		setSize(750, 500);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		usage_history_lb.setBounds(20,  10,  200,  40);
		start_date_lb.setBounds(40, 50, 150, 50);
		end_date_lb.setBounds(320, 50, 150, 50);
		//ok_btn.setBounds(520, 20, 140, 70);
		search_btn.setBounds(540, 60, 140, 70);
		
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
		YearSpinner2.setBounds(320, 100, 50, 30);
		MonthSpinner1.setBounds(100, 100, 40, 30);
		MonthSpinner2.setBounds(380, 100, 40, 30);
		DaySpinner1.setBounds(150, 100, 40, 30);
		DaySpinner2.setBounds(430, 100, 40, 30);
		
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
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���μ������� �Բ� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
										
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {
		UsageHistoryUI uh = new UsageHistoryUI();

	}

	

}
