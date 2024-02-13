package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Encoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
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
	
	private static byte pbUserKey[] = "0123456789abcdef".getBytes(); // 16
	private static byte pbCipher[] = new byte[50];
	
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
			byte[] password = encrypt(pw_tf.getText());
			if (!id_tf.getText().equals(id)) {
				JOptionPane.showMessageDialog(this, "아이디는 변경할 수 없습니다.");
			} else if (name_tf.getText().equals(name) && phone_tf.getText().equals(phone) && new String(password).equals(pw)) {
				JOptionPane.showMessageDialog(this, "변경할 정보가 없습니다.");
			} else if ("".equals(name_tf.getText()) || "".equals(phone_tf.getText()) || "".equals(pw_tf.getText())) {
				JOptionPane.showMessageDialog(this, "공백으로 정보를 변경할 수 없습니다.");
			} else {
				change();
			}
		} else if (obj == ok_btn) {
			dispose();
		} else if (obj == my_usage_btn) {
			UsageHistoryUI UHUI = new UsageHistoryUI();
		}
	}
	
	public void change() {
		byte[] password = encrypt(pw_tf.getText());
		bean.setPW(new String(password));
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

	public static byte[] encrypt(String str){
		byte[] userBytes = str.getBytes();
		byte pbData[] = new byte[16];
			
		for(int i=0; i<userBytes.length; i++) {
			if (i < userBytes.length) 
				pbData[i] = userBytes[i];
			else
				pbData[i] = 0x00;
		}
		
		//암호화 함수 호출
		pbCipher = KISA_SEED_ECB.SEED_ECB_Encrypt(pbUserKey, pbData,  0, pbData.length);
		
		/**JDK1.8 일 때 사용  */
		java.util.Base64.Encoder encoder = Base64.getEncoder(); 
		byte[] encArray = encoder.encode(Arrays.copyOf(pbCipher, 16)); // 인코딩할 바이트 배열의 길이를 16으로 수정
		 
		return encArray;
	}

	public static void main(String[] args) {
		MyInfoUI myinfo = new MyInfoUI();
	}
}