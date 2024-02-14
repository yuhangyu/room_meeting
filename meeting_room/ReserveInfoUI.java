package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ReserveInfoUI extends JFrame implements ActionListener{
	JTable reservetable;
	JScrollPane pane;
	static JButton jb = new JButton(" ");
	
	Container c = getContentPane();
	
	public ReserveInfoUI() {
		setSize(1000,440);
		setTitle("예약 정보 관리");
		
		c.setLayout(null);
		
		prviewlist();
		
		pane.setBounds(0,0,986,440);	
		
		reservetable.addMouseListener(new MouseAction());
		jb.addActionListener(this);
			
		c.add(pane);
		c.add(jb);

		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jb) {
			dispose();
			ReserveInfoUI RIUI = new ReserveInfoUI();
		}
	}
	
	private class MouseAction extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = reservetable.getSelectedRow();
				TableModel tm = reservetable.getModel();
				int option = JOptionPane.showOptionDialog(null, "해당 예약을 취소하시겠습니까?", "예약 확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"예약취소", "취소"}, "취소");
				
				if (option == JOptionPane.YES_OPTION) {
					MyInfoMgr mgr = new MyInfoMgr();
					ReserveBean bean = new ReserveBean();
					bean.setResvid((String) reservetable.getValueAt(row, 1));
					bean.setResvroom((String) reservetable.getValueAt(row, 4));
					bean.setResvtime((String) reservetable.getValueAt(row, 5));
					
					String id = bean.getResvid();
					String room = bean.getResvroom();
					String time = bean.getResvtime();
					
					if (mgr.cancelresv(id, room, time)) {
						JOptionPane.showMessageDialog(null, "예약이 취소되었습니다.");
						prviewlist();
					}
				}
			}
			super.mouseClicked(e);
		}
	}
	
	public void prviewlist() {		
		Vector<ReserveBean>vlist;
		MyInfoMgr mgr = new MyInfoMgr();
		
		vlist = mgr.reserveAll();
		
		String header[] = {"번호", "아이디", "이름", "전화번호","방 번호", "예약 날짜", "이용 시간", "이용 인원"};
		String [][] conts = new String[vlist.size()][header.length];
		
		for (int i = 0; i < vlist.size(); i++) {
			ReserveBean bean = vlist.get(i);
			
			conts[i][0] = String.valueOf(i+1);
			conts[i][1] = bean.getResvid();
			conts[i][2] = bean.getResvname();
			conts[i][3] = bean.getResvphone();
			conts[i][4] = bean.getResvroom();
			conts[i][5] = bean.getResvtime();
			conts[i][6] = String.valueOf(bean.getResvusetime());
			conts[i][7] = String.valueOf(bean.getResvperson());
		}
				
		DefaultTableModel model = new DefaultTableModel(conts, header) {
			public boolean isCellEditable(int i, int c) {return false;}
		};
		
		if (reservetable != null) {
			model = (DefaultTableModel) reservetable.getModel();  // 기존 모델 가져오기
			model.setRowCount(0);  // 모델의 행 초기화
			
			for (int i = 0; i < vlist.size(); i++) {
				model.addRow(conts[i]);  // 새로운 데이터로 모델 업데이트
			}
		}
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		reservetable = new JTable(model);
		pane = new JScrollPane(reservetable);
		
		Font font = new Font("Dialog",Font.BOLD, 16);
		reservetable.setFont(font);
		
		reservetable.getTableHeader().setResizingAllowed(false);
		reservetable.getTableHeader().setReorderingAllowed(false);
		
		reservetable.getColumnModel().getColumn(0).setPreferredWidth(20);
		reservetable.getColumnModel().getColumn(1).setPreferredWidth(50);
		reservetable.getColumnModel().getColumn(2).setPreferredWidth(40);
		reservetable.getColumnModel().getColumn(3).setPreferredWidth(40);
		reservetable.getColumnModel().getColumn(4).setPreferredWidth(10);
		reservetable.getColumnModel().getColumn(5).setPreferredWidth(80);
		reservetable.getColumnModel().getColumn(6).setPreferredWidth(10);
		reservetable.getColumnModel().getColumn(7).setPreferredWidth(10);
		reservetable.setRowHeight(30);
		
		for (int i = 0; i < reservetable.getColumnCount(); i++) {
			reservetable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}
	
	public static void main(String[] args) {
		
	}
}