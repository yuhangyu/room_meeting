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
	
	//��� ����
	JLabel id_lb = new JLabel("���̵�");
	JLabel pw_lb = new JLabel("��й�ȣ");
	JLabel pwcheck_lb = new JLabel("��й�ȣȮ��");
	JLabel name_lb = new JLabel("�̸�");	
	JLabel phone_lb = new JLabel("��ȭ��ȣ");
	
	JTextField id_tf = new JTextField(40);
	JTextField pw_tf = new JPasswordField(40);
	JTextField pwcheck_tf = new JPasswordField(40);
	JTextField name_tf = new JTextField(40);
	JTextField phone_tf = new JTextField(40);
	
	JButton id_check_btn = new JButton("���̵� �ߺ�Ȯ��");
	JButton signup_btn = new JButton("ȸ������");
	JButton cancel_btn = new JButton("���");

	private static byte pbUserKey[] = "0123456789abcdef".getBytes(); // 16
	private static byte pbCipher[] = new byte[50];
	
	public SignUpUI() {
		setTitle("ȸ������");
		setSize(320, 400);
		
		//������ �г��� ��ü �޼ҵ� ȣ��
		Container c = getContentPane();
		c.setLayout(null); //������ �г� �ʱ�ȭ
		
		//��� ��ġ ����
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
		
		//��� �߰�
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
		
		//��ư �̺�Ʈ �߰�
		id_check_btn.addActionListener(this);
		signup_btn.addActionListener(this);
		cancel_btn.addActionListener(this);

		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���μ������� �Բ� ����
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
				JOptionPane.showMessageDialog(this, "���̵� �Է����ּ���.");
				return;
			} else if (id.equals(bean.getID())) {
				JOptionPane.showMessageDialog(this, "�̹� ��ϵ� ���̵��Դϴ�.");
				return;
			}
			
			if ("".equals(pw) || "".equals(pw2)) {
				JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է����ּ���.");
				return;
			} else if (!pw.equals(pw2)) {
				JOptionPane.showMessageDialog(this, "�Է��� ��й�ȣ�� ���� �ٸ��ϴ�.");
				return;
			} else if (pw.length() < 4 || pw2.length() < 4) {
				JOptionPane.showMessageDialog(this, "��й�ȣ�� 4�ڸ� �̻� �Է����ּ���.");
				return;
			}
			phone = phone_format(phone);
			if ("".equals(name) || "".equals(phone)) {
				JOptionPane.showMessageDialog(this, "������ ĭ�� �Է����ּ���.");
				return;
			} else if (phone.length() != 13) {
				JOptionPane.showMessageDialog(this, "11�ڸ� �޴���ȭ ��ȣ�� ����� �Է����ּ���.");
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
				JOptionPane.showMessageDialog(this, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
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
			JOptionPane.showMessageDialog(this, "���̵� �Է����ּ���.");
			return;
		} else if (id.equals(bean.getID())) {
			JOptionPane.showMessageDialog(this, "�̹� ��ϵ� ���̵��Դϴ�.");
			return;
		} else {
			JOptionPane.showMessageDialog(this, "��� ������ ���̵��Դϴ�.");
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
		
		//��ȣȭ �Լ� ȣ��
		pbCipher = KISA_SEED_ECB.SEED_ECB_Encrypt(pbUserKey, pbData,  0, pbData.length);
		
		/**JDK1.8 �� �� ���  */
		java.util.Base64.Encoder encoder = Base64.getEncoder(); 
		byte[] encArray = encoder.encode(Arrays.copyOf(pbCipher, 16)); // ���ڵ��� ����Ʈ �迭�� ���̸� 16���� ����
		 
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