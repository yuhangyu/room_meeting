package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MyInfoUI extends JFrame implements ActionListener {
	
	MyInfoMgr mgr;
	MyInfoBean bean = new MyInfoBean();
	
	String id;
	String pw;
	String name;
	String phone;
	
	JLabel id_lb = new JLabel("아이디");
	JLabel pw_lb = new JLabel("비밀번호");
	JLabel name_lb = new JLabel("이름");
	JLabel phone_lb = new JLabel("전화번호");
	
	JTextField id_tf = new JTextField("DefaultID", 10);
	JTextField pw_tf = new JPasswordField(10);
	JTextField name_tf = new JTextField("UserName", 10);
	JTextField phone_tf = new JTextField("PhoneNum", 10);
	
	JButton my_usage_btn = new JButton("이용 내역");
	JButton modify_info_btn = new JButton("정보 수정");
	JButton ok_btn = new JButton("확인");
	
	public String getID(MyInfoBean bean) {
		return bean.getID();
	}
	
	public MyInfoUI() {
		setTitle("내 정보");
		setSize(400, 500);

		//정보 저장 id, pw, name, phone(tel)
		id = LoginUI.ID;
		mgr = new MyInfoMgr();
		bean = mgr.select(id);
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
		//프로그램 종료할 때 현재 창만 종료
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
								
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == modify_info_btn) {
			if (!id_tf.getText().equals(id)) {
				JOptionPane.showMessageDialog(this, "아이디는 변경할 수 없습니다.");
			} else if (name_tf.getText().equals(name) && phone_tf.getText().equals(phone) && pw_tf.getText().equals(pw)) {
				JOptionPane.showMessageDialog(this, "변경할 정보가 없습니다.");
			} else if ("".equals(name_tf.getText()) || "".equals(phone_tf.getText()) || "".equals(pw_tf.getText())) {
				JOptionPane.showMessageDialog(this, "공백으로 정보를 변경할 수 없습니다.");
			} else {
				change();
			}
		} else if (obj == ok_btn) {
			dispose();
		}
	}
	
	public void change() {
		bean.setPW(md5Hash(pw_tf.getText()));
		bean.setName(name_tf.getText());
		bean.setPhone(phone_tf.getText());
		if(mgr.update(bean)) {
			pw = bean.getPW();
			name = bean.getName();
			phone = bean.getPhone();
			JOptionPane.showMessageDialog(this, "정보가 변경되었습니다.");
			MainPageUI.name_value_lb.setText(name);
			dispose();
		}
	}
	
	// MD5 해시 함수
	private static String md5Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02x", b));
			}
			
			return hexString.toString();
		
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		MyInfoUI myinfo = new MyInfoUI();
	}
}