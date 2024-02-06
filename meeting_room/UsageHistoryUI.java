package meeting_room;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
// JSpinner 사용
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatter;

import java.util.Calendar;
import java.util.Date;


public class UsageHistoryUI extends JFrame implements ActionListener{
	private UsageHistory uh;
	
	JLabel usage_history_lb = new JLabel("이용 내역");
	JLabel start_date_lb = new JLabel("검색 시작일");
	JLabel end_date_lb = new JLabel("검색 종료일");
	JButton ok_btn = new JButton("확인");
	JButton search_btn = new JButton("검색");
	
    
    JTable historyTable;
    JScrollPane historyPane;
    
        
	UsageHistoryUI(){
		uh = new UsageHistory(this);
		
		//버튼 이벤트 추가
		ok_btn.addActionListener(uh);
		search_btn.addActionListener(uh);
		
		setTitle("내 정보");
		setSize(850, 500);
		
		//컨텐츠 패널의 객체 메소드 호출
		Container c = getContentPane();
		c.setLayout(null); //컨텐츠 패널 초기
		
		usage_history_lb.setBounds(20,  10,  200,  40);
		start_date_lb.setBounds(40, 50, 150, 50);
		end_date_lb.setBounds(420, 50, 150, 50);
		ok_btn.setBounds(640, 320, 140, 70);
		search_btn.setBounds(640, 60, 140, 70);
		
		viewhistoryPane(); // 테이블 활성화 메서드 
		historyPane.setBounds(40, 150, 550, 270); 
        
      
		
		
		// 현재 날짜를 얻어옴
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        
        // 현재 날짜를 기본값으로 설정
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH는 0부터 시작하므로 1을 더해줌
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        SpinnerNumberModel spinnerNumberModel1 = new SpinnerNumberModel(currentYear, currentYear, 2030, 1);
        JSpinner YearSpinner1 = new JSpinner(spinnerNumberModel1);
        
        SpinnerNumberModel spinnerNumberModel2 = new SpinnerNumberModel(currentYear, currentYear, 2030, 1);
        JSpinner YearSpinner2 = new JSpinner(spinnerNumberModel2);
        
        SpinnerNumberModel spinnerNumberModel3 = new SpinnerNumberModel(currentMonth, 1, 12, 1);
        JSpinner MonthSpinner1 = new JSpinner(spinnerNumberModel3);
        
        SpinnerNumberModel spinnerNumberModel4 = new SpinnerNumberModel(currentMonth, 1, 12, 1);
        JSpinner MonthSpinner2 = new JSpinner(spinnerNumberModel4);
        
        SpinnerNumberModel spinnerNumberModel5 = new SpinnerNumberModel(currentDay, 1, 31, 1);
        JSpinner DaySpinner1 = new JSpinner(spinnerNumberModel5);
        
        SpinnerNumberModel spinnerNumberModel6 = new SpinnerNumberModel(currentDay, 1, 31, 1);
        JSpinner DaySpinner2 = new JSpinner(spinnerNumberModel6);
        
        
        YearSpinner1.setBounds(40, 100, 50, 30);
		YearSpinner2.setBounds(420, 100, 50, 30);
		MonthSpinner1.setBounds(100, 100, 40, 30);
		MonthSpinner2.setBounds(480, 100, 40, 30);
		DaySpinner1.setBounds(150, 100, 40, 30);
		DaySpinner2.setBounds(530, 100, 40, 30);
		
		Font font = new Font("Dialog", Font.BOLD, 27);
		Font font1 = new Font("Dialog", Font.BOLD, 18);

		usage_history_lb.setFont(font);
		start_date_lb.setFont(font1);
		end_date_lb.setFont(font1);
		
		c.add(usage_history_lb);
		c.add(start_date_lb);
		c.add(end_date_lb);
		
		c.add(YearSpinner1);
		c.add(YearSpinner2);
		c.add(MonthSpinner1);
		c.add(MonthSpinner2);
		c.add(DaySpinner1);
		c.add(DaySpinner2);
		c.add(ok_btn);
		c.add(search_btn);
		c.add(historyPane);
		
			
		//화면 중앙에 오게 설정
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
										
		setVisible(true);
		setResizable(false);
		
		// JSpinner에 사용되는 DefaultFormatter 가져오기
        JFormattedTextField.AbstractFormatter formatter = ((JSpinner.DefaultEditor) YearSpinner1.getEditor()).getTextField().getFormatter();

        // AllowsInvalid를 false로 설정하여 유효하지 않은 값을 허용하지 않도록 함
        if (formatter instanceof DefaultFormatter) {
            ((DefaultFormatter) formatter).setAllowsInvalid(false);
        }
        
        // JSpinner의 에디터를 변경하여 쉼표가 없는 형식으로 표시되도록 함
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(YearSpinner1, "####");
        YearSpinner1.setEditor(editor);
        
        JSpinner.NumberEditor editor2 = new JSpinner.NumberEditor(YearSpinner2, "####");
        YearSpinner2.setEditor(editor2);
	}
	
	public void viewhistoryPane() {
		// 데이터 및 컬럼명 배열 정의
	    String[] columnNames = {"예약 번호", "ID", "이름", "전화번호", "이용 룸", "예약 날짜", "이용 시간", "이용 인원"};
	    String[][] data = {{"TEST_01", "admin", "셰계최강귀요미", "010-0000-0000", "2인 1번", "2024-02-06", "2시간", "3명"}};
	
	    // DefaultTableModel을 사용하여 JTable에 데이터 설정
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    historyTable = new JTable(model);
	    historyPane = new JScrollPane(historyTable);

	    
	    
	    
	    // 수평 스크롤바 비활성화
	    historyPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	    // 컬럼 너비 조절
	    historyTable.getColumnModel().getColumn(0).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(1).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(2).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(3).setPreferredWidth(140);
	    historyTable.getColumnModel().getColumn(4).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(5).setPreferredWidth(120);
	    historyTable.getColumnModel().getColumn(6).setPreferredWidth(80);
	    historyTable.getColumnModel().getColumn(7).setPreferredWidth(80);

	    // 행 높이 조절
	    historyTable.setRowHeight(20);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < historyTable.getColumnCount(); i++) {
            historyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {
		UsageHistoryUI uh = new UsageHistoryUI();
		

	}

	

}
