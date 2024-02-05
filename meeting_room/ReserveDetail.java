package meeting_room;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.AttributeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;

public class ReserveDetail extends JFrame implements ActionListener{
	
	JLabel reserve_lb = new JLabel("���÷� �����ϱ� ������");
	JLabel chosen_room_lb = new JLabel("���õ� ��  ");
	JLabel chosen_room_info_lb = new JLabel("2�� 1����");
	JLabel time_lb = new JLabel("��� �ð�  ");
	JLabel add_person_lb = new JLabel("�߰� �ο� ");
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
	
	// SpinnerNumberModel�� ����Ͽ� JSpinner �ʱ�ȭ (9���� ����, 24����) �ø� ����
    SpinnerNumberModel spinnerNumberModel1 = new SpinnerNumberModel(9, 9, 24, 1);
    JSpinner hourSpinner = new JSpinner(spinnerNumberModel1);
    
    // SpinnerNumberModel�� ����Ͽ� JSpinner �ʱ�ȭ (0���� ����, 50����) ���� ����
    SpinnerNumberModel spinnerNumberModel2 = new SpinnerNumberModel(0, 0, 50, 10);
    JSpinner minSpinner = new JSpinner(spinnerNumberModel2);
	
	
	
	
	public ReserveDetail() {
		setTitle("���÷� �����ϱ� ������");
		setSize(400, 600);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�
		
		reserve_lb.setBounds(35,  10,  500,  40);
		chosen_room_lb.setBounds(80,  70,  90,  25);
		chosen_room_info_lb.setBounds(180, 70, 90, 25);
		time_lb.setBounds(80, 110, 90, 25);
		time_tf.setBounds(180, 110, 30, 25);
		add_person_lb.setBounds(80, 150, 90, 25);
		select_add_person.setBounds(180, 150, 90, 25);
		start_time_lb.setBounds(80, 190, 90, 25);
		
		hour_lb.setBounds(220, 110, 50, 25);
		start_hour_lb.setBounds(235, 190, 50, 25);
		start_min_lb.setBounds(315, 190, 50, 25);
		
		// DocumentFilter�� ����Ͽ� �ؽ�Ʈ �ʵ��� �Է��� ����
        ((AbstractDocument) time_tf.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                // �Է°��� �����̰�, �ִ� 1�ڸ������� �Է� �����ϵ��� ����
                if (text.matches("\\d") && fb.getDocument().getLength() + text.length() <= 1) {
                    super.replace(fb, offset, length, text, attrs);
                    // ���⼭ �ݾ� ��� �� �߰� �۾��� �� �� �ֽ��ϴ�.
                    updatePrice(); // ���÷� �߰��� �޼��� ȣ��
                }
            }
        });
		
        hourSpinner.setBounds(180, 190, 50, 30);
        minSpinner.setBounds(260, 190, 50, 30);
		
		time_price_lb.setBounds(80, 270, 90, 25);
		time_price_value_lb.setBounds(180, 270, 90, 25);
		person_price_lb.setBounds(80, 310, 90, 25);
		person_price_value_lb.setBounds(180, 310, 90, 25);
		total_price_lb.setBounds(80, 350, 90, 25);
		total_price_value_lb.setBounds(180, 350, 90, 25);
		
		payment_btn.setBounds(40, 410, 140, 60);
		cancel_btn.setBounds(200, 410, 140, 60);
		
		
		
		payment_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
		
		
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
		
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���μ������� �Բ� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
		setVisible(true);
		setResizable(false);
		
	}
	
	private void updatePrice() {} // �ð��� �Է��Ҷ� ������Ʈ�� �޼��� 
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	public static void main(String[] args) {
		ReserveDetail rsvdetail = new ReserveDetail();
	}

	

}
