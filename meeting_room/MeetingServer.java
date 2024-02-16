package meeting_room;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MeetingServer extends JFrame {
	
	public static final int PORT = 8123;
	ServerSocket server;
	Vector<ClientThread2> vc;
	
	static List slist = new List();
	
	public MeetingServer() {
		setTitle("����� ����Ʈ");
		setSize(300, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		slist.add("** ������ ����Ʈ **");
		p2.add(BorderLayout.CENTER, slist);
		add(p2);
		
		try {
			server = new ServerSocket(PORT);
			vc = new Vector<ClientThread2>();
		} catch (Exception e) {
			System.err.println("Error in Server");
			System.exit(1);
		}
		System.out.println("***********************");
		System.out.println("������ �����Ǿ����ϴ�.");
		System.out.println("***********************");
		try {
			while (true) {
				Socket sock = server.accept();
				ClientThread2 ct = new ClientThread2(sock);
				ct.start();
				vc.addElement(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in Socket");
		}
	}
	
	public void sendAllMessage(String msg) {
		for (int i = 0; i < vc.size(); i++) {
			ClientThread2 ct = vc.get(i);
			ct.sendMessage(msg);
		}
	}
	
	public void removeClient(ClientThread2 ct) {
		vc.remove(ct);
	}
	
	//���ӵ� ��� id ����Ʈ ���� ex)aaa;bbb;ȫ�浿;��ȣ��;
	public String getIdList() {
		String list = "";
		slist.removeAll();
		slist.add("** ����� ����Ʈ **");
		for (int i = 0; i < vc.size(); i++) {
			ClientThread2 ct = vc.get(i);
			slist.add(ct.id);
			list += ct.id + ";";
		}
		return list;
	}
	
	class Order extends JFrame {
		
		JTable membertable;
		JScrollPane pane;
		Container c = getContentPane();
		
		public Order() {
			setTitle("�ֹ�");
			setSize(400, 300);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
			setResizable(false);
			
			c.setLayout(null);
			
			JLabel info = new JLabel("�ֹ� ����");
			
			Font font = new Font("Dialog", Font.BOLD, 40);
			info.setFont(font);
			
			info.setBounds(105, 20, 200, 50);
			
			c.add(info);
		}
		
		//24.02.14 �����ؾ��Ԥ���������������������������
		public void orderlist() {
			Vector<MyInfoBean> vlist;
			MyInfoMgr mgr;
			
			mgr = new MyInfoMgr();
			vlist = mgr.selectAll();
			
			String header[]= {"��ȣ", "���̵�", "�̸�", "��ȭ��ȣ", "�����ܾ�"};
			String[][] conts = new String[vlist.size()][header.length];
			
			//����, ���̵�, �̸�, ��ȭ��ȣ, �����ܾ� �� conts �迭�� ����
			for(int i = 0; i < vlist.size(); i++) {
				MyInfoBean bean = vlist.get(i);
				conts[i][0] = String.valueOf(i + 1);
				conts[i][1] = bean.getID();
				conts[i][2] = bean.getName();
				conts[i][3] = bean.getPhone();
				conts[i][4] = String.valueOf(bean.getMoney());
			}
			
			//���̺� ���� ���� �Ұ���
			DefaultTableModel model = new DefaultTableModel(conts, header) {
				public boolean isCellEditable(int i, int c){ return false; }
			};
			
			//���̺� ��� ����
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	            
			//����
			membertable = new JTable(model);
			pane = new JScrollPane(membertable);
			pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// �׻� ���� ��ũ�ѹ� ǥ��
			
			//��Ʈ ����
			Font font = new Font("Dialog", Font.BOLD, 16);
			membertable.setFont(font);

			//ũ�� ���� �Ұ���
			membertable.getTableHeader().setResizingAllowed(false);
			
			//�÷� ������ ����
			membertable.getColumnModel().getColumn(0).setPreferredWidth(25);
			membertable.getColumnModel().getColumn(1).setPreferredWidth(70);
			membertable.getColumnModel().getColumn(2).setPreferredWidth(70);
			membertable.getColumnModel().getColumn(3).setPreferredWidth(160);
			membertable.getColumnModel().getColumn(4).setPreferredWidth(110);
			membertable.setRowHeight(30);

			//header �߰�, ��� ����
			for (int i = 0; i < membertable.getColumnCount(); i++) {
				membertable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		}
	}
	
	//�Ű����� id������ ClientThread2�� �˻� : �ӼӸ�, ������ ���
	public ClientThread2 findClient(String id) {
		ClientThread2 ct = null;
		for (int i = 0; i < vc.size(); i++) {
			ct = vc.elementAt(i);
			if (ct.id.equals(id)) break;
		}
		return ct;
	}
	
	class ClientThread2 extends Thread{
		
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		String id;
		
		public ClientThread2(Socket sock) {
			try {
				this.sock = sock;
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(sock.getOutputStream(),true);
				System.out.println(sock.toString() + " ���ӵ�...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					String line = in.readLine();
					if(line==null)
						break;
					else
						routine(line);
				}
			} catch (Exception e) {
				removeClient(this);
				System.err.println(sock+"["+ id +"]������...");
				for (int i = 0; i < slist.getItemCount(); i++) {
					if (slist.getItem(i).equals(id)) {
						slist.remove(i);
						break;
					}
				}
			}
		}
		
		public void routine(String line) {
			int idx = line.indexOf(MeetingProtocol.MODE);
			String cmd = line.substring(0, idx);
			String data = line.substring(idx + 1);
			
			if (cmd.equals(MeetingProtocol.ID)) {
				id = data;
				sendMessage(MeetingProtocol.CHATLIST + MeetingProtocol.MODE + getIdList());
			}
		}
		
		public void sendMessage(String msg) {
			out.println(msg);
		}
		
	}//--ClientThread2
	
	public static void main(String[] args) {
		new MeetingServer();
	}
}