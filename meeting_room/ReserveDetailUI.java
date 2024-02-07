package meeting_room;

import java.awt.Color;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;

public class ReserveDetailUI extends JFrame implements ActionListener {
	private ReserveDetail rsvdetail;

	JLabel reserve_lb = new JLabel("���÷� �����ϱ� ������");
	JLabel chosen_room_lb = new JLabel("���õ� ��  ");
	JLabel chosen_room_info_lb;
	JLabel time_lb = new JLabel("��� �ð�  ");
	JLabel add_person_lb = new JLabel("�߰� �ο� ");
	JLabel start_date_lb = new JLabel("���� ��¥ ");
	JLabel start_time_lb = new JLabel("���� �ð� ");
	JLabel start_hour_lb = new JLabel("��");
	JLabel start_min_lb = new JLabel("��");
	JLabel time_price_lb = new JLabel("�ð� �ݾ�");
	JLabel person_price_lb = new JLabel("�ο� �ݾ�");
	JLabel time_price_value_lb = new JLabel("0 ��");
	JLabel person_price_value_lb = new JLabel("0 ��");
	JLabel total_price_lb = new JLabel("�� �ݾ�");
	JLabel total_price_value_lb = new JLabel("0 ��");
	
	JTextField time_tf = new JTextField(1); // �ð� �Է¹��� �ؽ�Ʈ �ʵ�
	// JLabel selected_add_person;  �߰� �ο� ���� ���̺� 
	
	String items[] = {"����", "1��", "2��"}; // �޺��ڽ��� �� �׸�
	JComboBox<String> select_add_person = new JComboBox<>(items);
	
	JButton payment_btn = new JButton("����");
	JButton cancel_btn = new JButton("���");
	
	JLabel hour_lb = new JLabel("�ð�");
	
	
	// Calendar ��ü ���� �� ���� �ð� ����
	Calendar cal = Calendar.getInstance();
	int currentHour = cal.get(Calendar.HOUR_OF_DAY);
	int currentMinute = cal.get(Calendar.MINUTE);
	
	// SpinnerNumberModel�� ����Ͽ� JSpinner �ʱ�ȭ (9���� ����, 24����) �ø� ����
	// ���� �ð��� '��' �� ����Ʈ�� 
	SpinnerNumberModel spinnerNumberModel1 = new SpinnerNumberModel(currentHour, 9, 25, 1);
	JSpinner hourSpinner = new JSpinner(spinnerNumberModel1);
	
	// SpinnerNumberModel�� ����Ͽ� JSpinner �ʱ�ȭ (0���� ����, 60����) ���� ����
	SpinnerNumberModel spinnerNumberModel2 = new SpinnerNumberModel((currentMinute / 10 * 10) + 10, 0, 60, 10);
	JSpinner minSpinner = new JSpinner(spinnerNumberModel2);
	
	// ���� ��¥�� �⺻������ ����
	int currentYear = cal.get(Calendar.YEAR);
	int currentMonth = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH�� 0���� �����ϹǷ� 1�� ������
	int currentDay = cal.get(Calendar.DAY_OF_MONTH);
	
	SpinnerNumberModel spinnerNumberModel3 = new SpinnerNumberModel(currentYear, currentYear, 2030, 1);
	JSpinner yearSpinner = new JSpinner(spinnerNumberModel3);
	
	SpinnerNumberModel spinnerNumberModel4 = new SpinnerNumberModel(currentMonth, 1, 13, 1);
	JSpinner monthSpinner = new JSpinner(spinnerNumberModel4);
	
	SpinnerNumberModel spinnerNumberModel5 = new SpinnerNumberModel(currentDay, 1, 32, 1);
	JSpinner daySpinner = new JSpinner(spinnerNumberModel5);
	
	public ReserveDetailUI(String selectedRoomInfo) {
		setTitle("���÷� �����ϱ� ������");
		setSize(400, 600);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		reserve_lb.setBounds(35,  10,  500,  40);
		chosen_room_lb.setBounds(80,  70,  90,  25);
		chosen_room_info_lb = new JLabel(selectedRoomInfo);
		chosen_room_info_lb.setBounds(180, 70, 90, 25);
		time_lb.setBounds(80, 110, 90, 25);
		time_tf.setBounds(180, 110, 30, 25);
		add_person_lb.setBounds(80, 150, 90, 25);
		select_add_person.setBounds(180, 150, 90, 25);
		start_time_lb.setBounds(80, 190, 90, 25);
		start_date_lb.setBounds(80, 230, 90, 25);
		
		hour_lb.setBounds(220, 110, 50, 25);
		start_hour_lb.setBounds(235, 190, 50, 25);
		start_min_lb.setBounds(315, 190, 50, 25);
		
		if ((int)minSpinner.getValue() == 60) {
			minSpinner.setValue(0);
		}
		
		// DocumentFilter�� ����Ͽ� �ؽ�Ʈ �ʵ��� �Է��� ����
		((AbstractDocument) time_tf.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
				// �Է°��� �����̰ų� �齺���̽� Ű�� ���� ó��, �ִ� �Է� �ڸ����� 1�ڸ��� ���� 
				if ((text == null || text.matches("\\d")) && fb.getDocument().getLength() + (text == null ? 0 : text.length()) - length <= 1) {
					super.replace(fb, offset, length, text, attrs);
					// ���⼭ �ݾ� ��� �� �߰� �۾��� �� �� �ֽ��ϴ�.
					updatePrice(); // ���÷� �߰��� �޼��� ȣ��
				}
			}
		});
		
		// hourSpinner �� ���� �̺�Ʈ ������ �߰�
		hourSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = (int) hourSpinner.getValue();
				if (newValue > 24) {
					hourSpinner.setValue(9); // 24�� �ʰ��ϸ� 9�� ����
				}
			}
		});
			
		// minSpinner �� ���� �̺�Ʈ ������ �߰�
		minSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = (int) minSpinner.getValue();
				if (newValue >= 60) {
					minSpinner.setValue(0); // 60 �̻��̸� 0���� ����
				}
			}
		});
		
		// monthSpinner �� ���� �̺�Ʈ ������ �߰�
		monthSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = (int) monthSpinner.getValue();
				if (newValue > 12) {
					monthSpinner.setValue(1); // 12�� �ʰ��ϸ� 1�� ����
				}
			}
		});
		
		// daySpinner �� ���� �̺�Ʈ ������ �߰�
		daySpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = (int) daySpinner.getValue();
				if (newValue > 31) {
					daySpinner.setValue(1); // 31�� �ʰ��ϸ� 1�� ����
				}
			}
		});
		
		// �ؽ�Ʈ �ʵ��� ���� �ٲ� �� ���� time_price_value_lb �� ���� 
		time_tf.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				updatePrice();
			}
			
			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				updatePrice();
			}
			
			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				updatePrice();
			}
		});
		
		// �޺� �ڽ��� ���� ����� �� ���� 
		select_add_person.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updatePrice(); // �޺��ڽ� ���� ����� ������ updatePrice �޼��� ȣ��
			}
		});
	
		hourSpinner.setBounds(180, 190, 50, 30);
		minSpinner.setBounds(260, 190, 50, 30);
		
		yearSpinner.setBounds(180, 230, 50, 30);
		monthSpinner.setBounds(240, 230, 50, 30);
		daySpinner.setBounds(300, 230, 50, 30);
		
		time_price_lb.setBounds(80, 320, 90, 25);
		time_price_value_lb.setBounds(180, 320, 90, 25);
		person_price_lb.setBounds(80, 360, 90, 25);
		person_price_value_lb.setBounds(180, 360, 90, 25);
		total_price_lb.setBounds(80, 400, 90, 25);
		total_price_value_lb.setBounds(180, 400, 90, 25);
		
		payment_btn.setBounds(40, 460, 140, 60);
		cancel_btn.setBounds(200, 460, 140, 60);
		
		rsvdetail = new ReserveDetail(this);
		
		payment_btn.addActionListener(rsvdetail);
		cancel_btn.addActionListener(rsvdetail);
		
		//Font ����
		Font font = new Font("Dialog", Font.BOLD, 25); // ��Ʈ����
		Font font2 = new Font("Dialog", Font.PLAIN, 17);
		
		reserve_lb.setFont(font);
		chosen_room_lb.setFont(font2);
		chosen_room_info_lb.setFont(font2);
		chosen_room_info_lb.setForeground(Color.RED);
		time_lb.setFont(font2);
		add_person_lb.setFont(font2);
		select_add_person.setFont(font2);
		start_time_lb.setFont(font2);
		start_date_lb.setFont(font2);
		
		time_price_lb.setFont(font2);
		time_price_value_lb.setFont(font2);
		person_price_lb.setFont(font2);
		person_price_value_lb.setFont(font2);
		total_price_lb.setFont(font2);
		total_price_value_lb.setFont(font2);
		
		payment_btn.setFont(font2);
		cancel_btn.setFont(font2);
		
		hour_lb.setFont(font2);
		start_hour_lb.setFont(font2);
		start_min_lb.setFont(font2);
		
		c.add(reserve_lb);
		c.add(chosen_room_lb);
		c.add(chosen_room_info_lb);
		c.add(time_lb);
		c.add(time_tf);
		c.add(add_person_lb);
		c.add(select_add_person);
		c.add(start_time_lb);
		c.add(start_date_lb);
		
		c.add(time_price_lb);
		c.add(time_price_value_lb);
		c.add(person_price_lb);
		c.add(person_price_value_lb);
		c.add(total_price_lb);
		c.add(total_price_value_lb);
		c.add(payment_btn);
		c.add(cancel_btn);
		c.add(hour_lb);
		c.add(start_hour_lb);
		c.add(start_min_lb);
		c.add(hourSpinner);
		c.add(minSpinner);
		c.add(yearSpinner);
		c.add(monthSpinner);
		c.add(daySpinner);
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���μ������� �Բ� ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setVisible(true);
		setResizable(false);
		
		// JSpinner�� ���Ǵ� DefaultFormatter ��������
		JFormattedTextField.AbstractFormatter formatter = ((JSpinner.DefaultEditor) yearSpinner.getEditor()).getTextField().getFormatter();
	
		// AllowsInvalid�� false�� �����Ͽ� ��ȿ���� ���� ���� ������� �ʵ��� ��
		if (formatter instanceof DefaultFormatter) {
			((DefaultFormatter) formatter).setAllowsInvalid(false);
		}
		
		// JSpinner�� �����͸� �����Ͽ� ��ǥ�� ���� �������� ǥ�õǵ��� ��
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "####");
		yearSpinner.setEditor(editor);
	}
	
	private void updatePrice() {} // �ð��� �Է��Ҷ� ������Ʈ�� �޼��� 
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	public static void main(String[] args) {
		
	}
}