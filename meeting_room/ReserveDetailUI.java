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

public class ReserveDetailUI extends JFrame implements ActionListener{
	private ReserveDetail rsvdetail;
	
	JLabel reserve_lb = new JLabel("미팅룸 예약하기 상세정보");
	JLabel chosen_room_lb = new JLabel("선택된 룸  ");
	JLabel chosen_room_info_lb;
	JLabel time_lb = new JLabel("사용 시간  ");
	JLabel add_person_lb = new JLabel("추가 인원 ");
	JLabel start_time_lb = new JLabel("시작 시간 ");
	JLabel start_hour_lb = new JLabel("시");
	JLabel start_min_lb = new JLabel("분");
	
	
	JLabel time_price_lb = new JLabel("시간 금액");
	JLabel person_price_lb = new JLabel("인원 금액");
	JLabel time_price_value_lb = new JLabel("0 원");
	JLabel person_price_value_lb = new JLabel("0 원");
	
	JLabel total_price_lb = new JLabel("총 금액");
	JLabel total_price_value_lb = new JLabel("0 원");
	
	
	
	JTextField time_tf = new JTextField(1); // 시간 입력받을 텍스트 필드
	// JLabel selected_add_person;  추가 인원 선택 레이블 
	
	String items[] = {"없음", "1명", "2명"}; // 콤보박스에 들어갈 항목
	JComboBox<String> select_add_person = new JComboBox<>(items);
	
	JButton payment_btn = new JButton("결제");
	JButton cancel_btn = new JButton("취소");
	
	JLabel hour_lb = new JLabel("시간");
	
	// SpinnerNumberModel을 사용하여 JSpinner 초기화 (9부터 시작, 24까지) 시를 조정
    SpinnerNumberModel spinnerNumberModel1 = new SpinnerNumberModel(9, 9, 24, 1);
    JSpinner hourSpinner = new JSpinner(spinnerNumberModel1);
    
    // SpinnerNumberModel을 사용하여 JSpinner 초기화 (0부터 시작, 50까지) 분을 조정
    SpinnerNumberModel spinnerNumberModel2 = new SpinnerNumberModel(0, 0, 50, 10);
    JSpinner minSpinner = new JSpinner(spinnerNumberModel2);
	
	
	
	
	public ReserveDetailUI(String selectedRoomInfo) {
		setTitle("미팅룸 예약하기 상세정보");
		setSize(400, 600);
		
		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기
		
		reserve_lb.setBounds(35,  10,  500,  40);
		chosen_room_lb.setBounds(80,  70,  90,  25);
		chosen_room_info_lb = new JLabel(selectedRoomInfo);
		chosen_room_info_lb.setBounds(180, 70, 90, 25);
		time_lb.setBounds(80, 110, 90, 25);
		time_tf.setBounds(180, 110, 30, 25);
		add_person_lb.setBounds(80, 150, 90, 25);
		select_add_person.setBounds(180, 150, 90, 25);
		start_time_lb.setBounds(80, 190, 90, 25);
		
		hour_lb.setBounds(220, 110, 50, 25);
		start_hour_lb.setBounds(235, 190, 50, 25);
		start_min_lb.setBounds(315, 190, 50, 25);
		
		// DocumentFilter를 사용하여 텍스트 필드의 입력을 제한
        ((AbstractDocument) time_tf.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                // 입력값이 숫자이고, 최대 1자리까지만 입력 가능하도록 제한
                if (text.matches("\\d") && fb.getDocument().getLength() + text.length() <= 1) {
                    super.replace(fb, offset, length, text, attrs);
                    // 여기서 금액 계산 등 추가 작업을 할 수 있습니다.
                    updatePrice(); // 예시로 추가된 메서드 호출
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
		
		
		rsvdetail = new ReserveDetail(this);
		
		payment_btn.addActionListener(rsvdetail);
		cancel_btn.addActionListener(rsvdetail);
		
		
		//Font 지정
		Font font = new Font("Dialog", Font.BOLD, 25); // 폰트굵게
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
		
		
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//프로그램 종료할 때 프로세스까지 함께 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
		setVisible(true);
		setResizable(false);
		
	}
	
	private void updatePrice() {} // 시간을 입력할때 업데이트할 메서드 
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	public static void main(String[] args) {
		String selectedRoomInfo = "잘못된 접근";
		ReserveDetailUI rsvdetail = new ReserveDetailUI(selectedRoomInfo);
	}

}
