package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		setSize(400, 500);
		
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
		
		id_lb.setBounds(85,  30,  100,  40);
		pw_lb.setBounds(85,  80,  100,  40);
		name_lb.setBounds(85,  130,  100,  40);
		phone_lb.setBounds(85,  180,  100,  40);
		
		id_tf.setBounds(180, 30, 100, 40);
		pw_tf.setBounds(180, 80, 100, 40);
		name_tf.setBounds(180, 130, 100, 40);
		phone_tf.setBounds(180, 180, 100, 40);
		
		my_usage_btn.setBounds(85, 250, 200, 70);
		modify_info_btn.setBounds(85, 340, 90, 70);
		ok_btn.setBounds(195, 340, 90, 70);
		
		
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
				} else {
					//비밀번호만 변경된 경우
					MyInfoBean bean = new MyInfoBean();
					bean.setID(id_tf.getText());
					bean.setPW(pw_tf.getText());
					bean.setName(name_tf.getText());
					bean.setPhone(phone_tf.getText());
					if(mgr.update(bean)) {
						System.out.println("수정완료");
					}
				}
			} else { //아이디, 이름, 전화번호가 변경된 경우
				System.out.println("dㅇ");
			}
		}
	}

	public static void main(String[] args) {
		MyInfo myinfo = new MyInfo();
	}
}
