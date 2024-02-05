package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MyInfo extends JFrame implements ActionListener{
	
	Vector<MyInfoBean> vlist;
	MyInfoMgr mgr;
	
	String id;
	String pw;
	String name;
	String phone;
	
	JLabel id_lb = new JLabel("아이디");
	JLabel pw_lb = new JLabel("비밀번호");
	JLabel name_lb = new JLabel("이름");
	JLabel phone_lb = new JLabel("전화번호");
	
	JTextField id_tf = new JTextField(10);
	JTextField pw_tf = new JTextField(10);
	JTextField name_tf = new JTextField(10);
	JTextField phone_tf = new JTextField(10);
	
	JButton my_usage_btn = new JButton("이용 내역");
	JButton modify_info_btn = new JButton("정보 수정");
	JButton ok_btn = new JButton("확인");
	
	public MyInfo() {
		setTitle("내 정보");
		setSize(395, 430);
		
		mgr = new MyInfoMgr();
		MyInfoBean bean = mgr.select("user");

		id = bean.getID();
		pw = bean.getPW();
		name = bean.getName();
		phone = bean.getPhone();
		
		id_tf.setText(id);
		name_tf.setText(name);
		phone_tf.setText(phone);
		
		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기
		
		id_lb.setBounds(60,  30,  100,  40);
		pw_lb.setBounds(60,  80,  100,  40);
		name_lb.setBounds(60,  130,  100,  40);
		phone_lb.setBounds(60,  180,  100,  40);
		
		id_tf.setBounds(165, 37, 155, 25);
		pw_tf.setBounds(165, 87, 155, 25);
		name_tf.setBounds(165, 137, 155, 25);
		phone_tf.setBounds(165, 187, 155, 25);
		
		my_usage_btn.setBounds(65, 240, 250, 45);
		modify_info_btn.setBounds(65, 310, 110, 50);
		ok_btn.setBounds(205, 310, 110, 50);
		
		
		Font font = new Font("Dialog", Font.BOLD, 18);
		
		id_lb.setFont(font);
		pw_lb.setFont(font);
		name_lb.setFont(font);
		phone_lb.setFont(font);
		
		my_usage_btn.addActionListener(this);
		modify_info_btn.addActionListener(this);
		ok_btn.addActionListener(this);
		
		c.add(id_lb);
		c.add(pw_lb);
		c.add(name_lb);
		c.add(phone_lb);
		
		c.add(id_tf);
		c.add(pw_tf);
		c.add(name_tf);
		c.add(phone_tf);
		
		c.add(my_usage_btn);
		c.add(modify_info_btn);
		c.add(ok_btn);		
		
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//프로그램 종료할 때 프로세스까지 함께 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == modify_info_btn) {
			if (id_tf.getText().equals(id) && name_tf.getText().equals(name) && phone_tf.getText().equals(phone)) {
				if ("".equals(pw_tf.getText()) || pw_tf.getText().equals(pw)) {
					//수정되지 않은 경우
					JOptionPane.showMessageDialog(this, "변경할 정보가 없습니다.");
				} else {
					//비밀번호만 변경된 경우
					change();
				}
			} else { //아이디, 이름, 전화번호가 변경된 경우
				change();
			}
		}
	}
	
	public void change() {
		MyInfoBean bean = new MyInfoBean();
		bean.setID(id_tf.getText());
		bean.setPW(pw_tf.getText());
		bean.setName(name_tf.getText());
		bean.setPhone(phone_tf.getText());
		if(mgr.update(bean)) {
			System.out.println("수정완료");
			id = bean.getID();
			pw = bean.getPW();
			name = bean.getName();
			phone = bean.getPhone();
			JOptionPane.showMessageDialog(this, "정보가 변경되었습니다.");
		}
	}

	public static void main(String[] args) {
		MyInfo myinfo = new MyInfo();
	}
}
