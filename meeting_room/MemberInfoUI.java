package meeting_room;

import java.awt.Color;
import java.awt.Container;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MemberInfoUI extends JFrame implements ActionListener {
	
	Vector<MyInfoBean> vlist;
	MyInfoMgr mgr;

	List list;
	JLabel label;
	JTable membertable;
	JScrollPane pane;
	
	public MemberInfoUI() {
		setSize(550, 500);
		setTitle("회원 정보 관리");

		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기

		mgr = new MyInfoMgr();
		vlist = mgr.selectAll();

		String header[]= {"번호", "아이디", "이름", "전화번호", "현재잔액"};
		String[][] conts;
		conts=new String[vlist.size()][header.length];
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
		membertable = new JTable(model);
		pane = new JScrollPane(membertable);		
		
		//컬럼 사이즈 지정
		membertable.getColumnModel().getColumn(0).setPreferredWidth(30);
		membertable.getColumnModel().getColumn(1).setPreferredWidth(70);
		membertable.getColumnModel().getColumn(2).setPreferredWidth(70);
		membertable.getColumnModel().getColumn(3).setPreferredWidth(160);
		membertable.getColumnModel().getColumn(4).setPreferredWidth(110);

		//요소 위치 지정
		pane.setBounds(0, 0, 535, 500);
		
		c.add(pane);
		
		membertable.addMouseListener(new MouseAction());
		
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		//프로그램 종료할 때 프로세스까지 함께 종료
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setVisible(true);
		setResizable(false);
	}
	
	private class MouseAction extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount()==2) {
				int row = membertable.getSelectedRow();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public static void main(String[] args) {
		MemberInfoUI MIUI = new MemberInfoUI();
	}
}