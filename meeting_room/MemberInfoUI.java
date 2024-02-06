package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MemberInfoUI extends JFrame implements ActionListener {

	JTable membertable;
	JScrollPane pane;
	static JButton jb;

	//������ �г��� ��ü �޼ҵ� ȣ��
	Container c = getContentPane();
	
	public MemberInfoUI() {
		setSize(550, 400);
		setTitle("ȸ�� ���� ����");

		c.setLayout(null); //������ �г� �ʱ�

		viewlist();
		
		//��� ��ġ ����
		pane.setBounds(0, 0, 535, 400);

		jb = new JButton("a");
		jb.addActionListener(this);
		c.add(jb);
		
		//�̺�Ʈ �߰�
		membertable.addMouseListener(new MouseAction());
		
		//��� �߰�
		c.add(pane);
		
		//ȭ�� �߾ӿ� ���� ����
		setLocationRelativeTo(null);
		//���α׷� ������ �� ���μ������� �Բ� ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb) {
			dispose();
			MemberInfoUI MIUI = new MemberInfoUI();
		}
	}
	
	private class MouseAction extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount()==2) {
				int row = membertable.getSelectedRow();
				TableModel mmm = membertable.getModel();
				MemUpdateUI MUUI = new MemUpdateUI(String.valueOf(mmm.getValueAt(row, 1)));
			}
		}
	}
	
	public void viewlist() {
		Vector<MyInfoBean> vlist;
		MyInfoMgr mgr;
		
		mgr = new MyInfoMgr();
		vlist = mgr.selectAll();
		
		String header[]= {"��ȣ", "���̵�", "�̸�", "��ȭ��ȣ", "�����ܾ�"};
		String[][] conts;
		conts = new String[vlist.size()][header.length];
		for(int i = 0; i < vlist.size(); i++) {
			MyInfoBean bean = vlist.get(i);
			conts[i][0] = String.valueOf(i + 1);
			conts[i][1] = bean.getID();
			conts[i][2] = bean.getName();
			conts[i][3] = bean.getPhone();
			conts[i][4] = String.valueOf(bean.getMoney());
		}
		DefaultTableModel model = new DefaultTableModel(conts, header) {
			public boolean isCellEditable(int i, int c){ return false; }
		};
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		membertable = new JTable(model);
		
		Font font = new Font("Dialog", Font.BOLD, 17);
		membertable.setFont(font);
		
		pane = new JScrollPane(membertable);		
		
		// ���� ��ũ�ѹ� ��Ȱ��ȭ
	      pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//�÷� ������ ����
		membertable.getColumnModel().getColumn(0).setPreferredWidth(25);
		membertable.getColumnModel().getColumn(1).setPreferredWidth(70);
		membertable.getColumnModel().getColumn(2).setPreferredWidth(70);
		membertable.getColumnModel().getColumn(3).setPreferredWidth(160);
		membertable.getColumnModel().getColumn(4).setPreferredWidth(110);
		membertable.setRowHeight(30);

		for (int i = 0; i < membertable.getColumnCount(); i++) {
			membertable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}
	
	public static void main(String[] args) {
		MemberInfoUI MIUI = new MemberInfoUI();
	}
}