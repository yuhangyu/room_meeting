package meeting_room;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Vector;

public class SignUpUI extends JFrame implements ActionListener {
	
	//요소 생성
	JLabel id_lb = new JLabel("아이디");
	JLabel pw_lb = new JLabel("비밀번호");
	JLabel pwcheck_lb = new JLabel("비밀번호확인");
	JLabel name_lb = new JLabel("이름");	
	JLabel phone_lb = new JLabel("전화번호");
	
	JTextField id_tf = new JTextField(40);
	JTextField pw_tf = new JPasswordField(40);
	JTextField pwcheck_tf = new JPasswordField(40);
	JTextField name_tf = new JTextField(40);
	JTextField phone_tf = new JTextField(40);
	
	JButton id_check_btn = new JButton("아이디 중복확인");
	JButton signup_btn = new JButton("회원가입");
	JButton cancel_btn = new JButton("취소");

	private static byte pbUserKey[] = "0123456789abcdef".getBytes(); // 16
	private static byte pbCipher[] = new byte[50];
	
	public SignUpUI() {
		setTitle("회원가입");
		setSize(320, 400);
		
		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기화
		
		//요소 위치 지정
		id_lb.setBounds(30, 20, 90, 25);
		pw_lb.setBounds(30, 100, 90, 25);
		pwcheck_lb.setBounds(30, 145, 90, 25);
		name_lb.setBounds(30, 190, 90, 25);
		phone_lb.setBounds(30, 235, 90, 25);
		
		id_tf.setBounds(130, 20, 150, 25);
		pw_tf.setBounds(130, 100, 150, 25);
		pwcheck_tf.setBounds(130, 145, 150, 25);
		name_tf.setBounds(130, 190, 150, 25);
		phone_tf.setBounds(130, 235, 150, 25);
		
		id_check_btn.setBounds(130, 55, 150, 25);
		signup_btn.setBounds(40, 290, 100, 40);
		cancel_btn.setBounds(168, 290, 100, 40);
		
		//요소 추가
		c.add(id_lb);
		c.add(pw_lb);
		c.add(pwcheck_lb);
		c.add(name_lb);
		c.add(phone_lb);
		c.add(id_tf);
		c.add(pw_tf);
		c.add(pwcheck_tf);
		c.add(name_tf);;
		c.add(phone_tf);
		c.add(id_check_btn);
		c.add(signup_btn);
		c.add(cancel_btn);
		
		//버튼 이벤트 추가
		id_check_btn.addActionListener(this);
		signup_btn.addActionListener(this);
		cancel_btn.addActionListener(this);

		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//프로그램 종료할 때 프로세스까지 함께 종료
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		MyInfoMgr mgr;
		String id = id_tf.getText();
		String pw = pw_tf.getText();
		String pw2 = pwcheck_tf.getText();
		String name = name_tf.getText();
		String phone = phone_tf.getText();
		
		if (obj == id_check_btn) {
			idcheck(id);
		} else if (obj == signup_btn) {
			mgr = new MyInfoMgr();
			MyInfoBean bean = mgr.select(id);
			if ("".equals(id)) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
				return;
			} else if (id.equals(bean.getID())) {
				JOptionPane.showMessageDialog(this, "이미 등록된 아이디입니다.");
				return;
			}
			
			if ("".equals(pw) || "".equals(pw2)) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요.");
				return;
			} else if (!pw.equals(pw2)) {
				JOptionPane.showMessageDialog(this, "입력한 비밀번호가 서로 다릅니다.");
				return;
			} else if (pw.length() < 4 || pw2.length() < 4) {
				JOptionPane.showMessageDialog(this, "비밀번호는 4자리 이상 입력해주세요.");
				return;
			}
			phone = phone_format(phone);
			if ("".equals(name) || "".equals(phone)) {
				JOptionPane.showMessageDialog(this, "공백인 칸을 입력해주세요.");
				return;
			} else if (phone.length() != 13) {
				JOptionPane.showMessageDialog(this, "11자리 휴대전화 번호를 제대로 입력해주세요.");
				return;
			}
			
			byte[] password = encrypt(pw_tf.getText());
			pw = new String(password);
			bean = new MyInfoBean();
			bean.setID(id);
			bean.setPW(pw);
			bean.setName(name);
			bean.setPhone(phone_format(phone));
			if (mgr.signup(bean)) {
				JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
				dispose();
				return;
			}
		} else if (obj == cancel_btn) {
			dispose();
		}
	}
	
	public void idcheck(String id) {
		MyInfoMgr mgr = new MyInfoMgr();
		MyInfoBean bean = mgr.select(id);
		if ("".equals(id)) {
			JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
			return;
		} else if (id.equals(bean.getID())) {
			JOptionPane.showMessageDialog(this, "이미 등록된 아이디입니다.");
			return;
		} else {
			JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.");
			return;
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
	
	public String phone_format(String number) {
	      String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
	      return number.replaceAll(regEx, "$1-$2-$3");
	}
	
	public static void main(String[] args) {
		SignUpUI signup = new SignUpUI();
	}
}