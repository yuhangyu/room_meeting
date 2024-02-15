package meeting_room;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MyReserveUI extends JFrame implements ActionListener, ListSelectionListener{
	JLabel myReserve_lb = new JLabel("내 예약정보");
	JButton reserveCancel_btn = new JButton("예약 취소");
	JButton reserve_btn = new JButton("룸 예약");
	JButton ok_btn = new JButton("확인");
	
	JTable reserveTable;
    JScrollPane reservePane;
	
	public MyReserveUI() {
		setTitle("내 예약정보");
		setSize(712, 720);

		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null);  //컨텐츠 패널 초기화
		
		// 로고 추가
		ImageIcon logoIcon = new ImageIcon("meeting_room/logo.jpg");
		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setBounds(0, 20, 700, 270);
		c.add(logoLabel);
		
		viewlist();
		
		reservePane.setBounds(30, 380, 340, 250);
				
		c.add(reservePane);		
		
		reserveTable.getSelectionModel().addListSelectionListener(this);
		
		myReserve_lb.setBounds(25, 315, 500, 40);
		
		reserveCancel_btn.setBounds(430, 315, 200, 100);
		reserve_btn.setBounds(430, 435, 200, 100);
		ok_btn.setBounds(430, 555, 200, 100);
		
		//Font 지정
		Font font = new Font("Dialog", Font.BOLD, 32); // 폰트굵게
		Font font1 = new Font("Dialog", Font.PLAIN, 18);
		
		myReserve_lb.setFont(font);
		myReserve_lb.setForeground(Color.BLUE);
		
		reserve_btn.setFont(font1);
		reserveCancel_btn.setFont(font1);
		ok_btn.setFont(font1);
		
		c.add(myReserve_lb);
		c.add(reserve_btn);
		c.add(reserveCancel_btn);
		c.add(ok_btn);
		
		reserveCancel_btn.setEnabled(false);
		reserve_btn.addActionListener(this);
		reserveCancel_btn.addActionListener(this);
		ok_btn.addActionListener(this);
		
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						
		setVisible(true);
		setResizable(false);
	}
	
	@Override
    public void valueChanged(ListSelectionEvent e) {
        // 사용자가 어떤 행을 선택했는지 확인
        if (!e.getValueIsAdjusting() && reserveTable.getSelectedRow() != -1) {
            // 특정 행이 선택되었을 때 예약 취소 버튼 활성화
            reserveCancel_btn.setEnabled(true);
        } else {
            // 아무 행도 선택되지 않았을 때 예약 취소 버튼 비활성화
            reserveCancel_btn.setEnabled(false);
        }
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == reserve_btn) {
			ReserveUI rui = new ReserveUI();
			this.dispose();
		}
		else if (obj == reserveCancel_btn) {
			int row = reserveTable.getSelectedRow();
			TableModel tm = reserveTable.getModel();
			int option = JOptionPane.showOptionDialog(null, "해당 예약을 취소하시겠습니까?", "예약 확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"예약취소", "취소"}, "취소");
			
			if (option == JOptionPane.YES_OPTION) {
				MyInfoMgr mgr = new MyInfoMgr();
				ReserveBean bean = new ReserveBean();
				bean.setResvid(LoginUI.ID);
				bean.setResvroom((String) reserveTable.getValueAt(row, 3));
				bean.setResvtime((String) reserveTable.getValueAt(row, 0) + " " + (String) reserveTable.getValueAt(row, 1));
				
				String id = bean.getResvid();
				String room = bean.getResvroom();
				String time = bean.getResvtime();
				
				if (mgr.cancelresv(id, room, time)) {
					JOptionPane.showMessageDialog(null, "예약이 취소되었습니다.");
					viewlist();
				}
			}
		}
		else if (obj == ok_btn) {
			this.dispose();
		}
	}
	
	public void viewlist() {
		//테이블 추가
		// 데이터 및 컬럼명 배열 정의
		String[] columnNames = {"예약 날짜", "시작 시간", "종료 시간", "예약 룸"};
		String[][] data = {{"", "", "", ""}};
					
		// DefaultTableModel을 사용하여 JTable에 데이터 설정
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// 모든 셀을 편집 불가능하도록 설정
                return false;
			}
		};
		reserveTable = new JTable(model);
		reserveTable.getTableHeader().setReorderingAllowed(false); // 테이블 헤더 이동 비활성화

        // 테이블의 셀 편집을 비활성화하여 테이블 전체를 편집 불가능하게 만듭니다.
        reserveTable.setDefaultEditor(Object.class, null);
        reserveTable.getTableHeader().setResizingAllowed(false);
		
		reservePane = new JScrollPane(reserveTable);

		// 수평 스크롤바 비활성화
		reservePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// 컬럼 너비 조절
		reserveTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		reserveTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		reserveTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		reserveTable.getColumnModel().getColumn(3).setPreferredWidth(60);

		// 행 높이 조절
		reserveTable.setRowHeight(20);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < reserveTable.getColumnCount(); i++) {
			reserveTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		// DB에서 받아온 DATE타입을 테이블에 업데이트 하기 위해 포맷을 변경하기 위한 선언
		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat newFormat2 = new SimpleDateFormat("HH:mm:ss");
		
		Vector<ReserveBean> reservelist;
		MyInfoMgr mgr = new MyInfoMgr();
		reservelist = mgr.reserveAll();
		 
		String loginID = LoginUI.ID;
		// 테이블 업데이트
		model = (DefaultTableModel) reserveTable.getModel();
        model.setRowCount(0); // 기존 테이블 내용 지우기
        
        // reservelist를 날짜에 따라 정렬
        Collections.sort(reservelist, new Comparator<ReserveBean>() {
            @Override
            public int compare(ReserveBean bean1, ReserveBean bean2) {
                Date date1 = null;
                Date date2 = null;

                try {
                    date1 = originalFormat.parse(bean1.getResvtime());
                    date2 = originalFormat.parse(bean2.getResvtime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return date1.compareTo(date2);
            }
        });
        
        Date currentDate = new Date();
		
		for(int i = 0; i < reservelist.size(); i++ ) {
			ReserveBean bean = reservelist.get(i);
			
			// 로그인 아이디와 예약 bean의 같은 경우만 테이블에 출력 
			if(loginID.equals(bean.getResvid())) {
			
				try {
                    Date reservationDate = originalFormat.parse(bean.getResvtime());

                    // 시작시간이 현재 시간 이후인지 검사하고 이후인 경우에만 테이블에 출력 
                    if (reservationDate.after(currentDate)) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(reservationDate); // 캘린더의 데이트를 받아온 시간으로 변경
                        calendar.add(Calendar.HOUR_OF_DAY, bean.getResvusetime()); // 캘린더에 사용시간 더하기

                        String formattedDate = newFormat.format(reservationDate); // 포멧 변경 YY-MM-DD
                        String formattedDate2 = newFormat2.format(reservationDate); // 포멧 변경 HH:MM:SS
                        String EndformattedDate2 = newFormat2.format(calendar.getTime()); // 캘린더에 사용시간을 더하고 포맷 변경하여 출력

                        String[] rowData = { // 추가할 행의 데이터
                                formattedDate,
                                formattedDate2,
                                EndformattedDate2,
                                bean.getResvroom()
                        };
                        model.addRow(rowData);
                    }
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
			}
			
		}
	}
	
	public static void main(String[] args) {
		MyReserveUI mrUI = new MyReserveUI();
	}
}