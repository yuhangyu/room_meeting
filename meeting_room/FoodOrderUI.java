package meeting_room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// 음식 데이터를 저장하는 클래스
class Food {
    String type;
    String name;
    String detail;  // 상세 정보 필드 추가
    String imageUrl;
    int price;

    Food(String type, String name, String detail, String imageUrl, int price) {  // 상세 정보를 받는 생성자 추가
        this.type = type;
        this.name = name;
        this.detail = detail;  // 상세 정보 필드 초기화
        this.imageUrl = imageUrl;
        this.price = price;
    }

    String getType() { return type; }
    String getName() { return name; }
    String getDetail() { return detail; }  // 상세 정보 getter 메서드 추가
    String getImageUrl() { return imageUrl; }
    int getPrice() { return price; }
}

public class FoodOrderUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("음식 메뉴");
  	  frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
  	  frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // 장바구니 리스트 생성
        DefaultListModel<String> cartList = new DefaultListModel<>();
        JList<String> cart = new JList<>(cartList);
        JScrollPane cartScrollPane = new JScrollPane(cart); 

        // 음식 데이터 생성
        List<Food> foods = new ArrayList<>();
        foods.add(new Food("카페", "아이스티", "아이스티의 상세 정보", "http://example.com/iced_tea_image.jpg", 2000));
        foods.add(new Food("카페", "망고스무디", "망고스무디의 상세 정보", "http://example.com/mango_smoothie_image.jpg", 4500));

        // 총 주문 금액 표시
        JLabel totalLabel = new JLabel("총 주문 금액: 0");

        JPanel northPanel = new JPanel(new GridLayout(1, 5));
        String[] foodTypes = {"카페", "음료", "면류", "밥류", "과자", "술"};

        // centerPanel을 CardLayout으로 설정
        CardLayout cl = new CardLayout();
        JPanel centerPanel = new JPanel(cl);

        // 음식 유형별로 JPanel 생성
        for (String foodType : foodTypes) {
            JPanel foodPanel = new JPanel(new GridLayout(0, 5)); // 세로 크기는 동적으로 조정
            JScrollPane scrollPane = new JScrollPane(foodPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  // 세로 스크롤만 활성화
            for (final Food food : foods) {
                if (food.getType().equals(foodType)) {
                    JButton foodButton = new JButton("<html><center><img src='" + food.getImageUrl() + "' width='100' height='100'/><br/>" + food.getName() + "<br/>" + food.getPrice() + "</center></html>");
                    foodButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // 팝업 창을 생성하고 보여줌
                            JDialog detailDialog = new JDialog(frame, "음식 상세 정보", true);
                            detailDialog.setSize(300, 200);
                            detailDialog.setLocationRelativeTo(frame);

                            // 팝업 창에 음식 정보를 표시
                            JPanel detailPanel = new JPanel();
                            detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

                            JLabel nameLabel = new JLabel("이름: " + food.getName());
                            JLabel detailLabel = new JLabel("상세 정보: " + food.getDetail());
                            JLabel priceLabel = new JLabel("가격: " + food.getPrice());

                            SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);  // 초기 값 1, 최소 값 1, 최대 값 10, 단계 1
                            JSpinner quantitySpinner = new JSpinner(model);
                            JLabel quantityLabel = new JLabel("수량: ");
                            quantitySpinner.setPreferredSize(new Dimension(140, 25));
                            
                            JPanel quantityPanel = new JPanel();
                            quantityPanel.add(quantityLabel);
                            quantityPanel.add(quantitySpinner);
                            quantityLabel.setLabelFor(quantitySpinner);
                            detailPanel.add(nameLabel);
                            detailPanel.add(detailLabel);
                            detailPanel.add(priceLabel);
                            detailPanel.add(quantityPanel);
                            detailDialog.add(detailPanel);


                             // '담기' 버튼 추가
                            JButton addButton = new JButton("담기");

                            addButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // 주문 내역에 음식 추가
                                    String foodNamePrice = food.getName() + " " + food.getPrice();

                                    // 스피너에서 선택된 수량을 가져옴
                                    int quantity = (Integer) quantitySpinner.getValue();

                                    // 주문 내역에 이미 같은 음식이 있으면
                                    int currentIndex = -1;
                                    for (int i = 0; i < cartList.size(); i++) {
                                        if (cartList.get(i).startsWith(foodNamePrice)) {
                                            currentIndex = i;
                                            break;
                                        }
                                    }

                                    if (currentIndex != -1) {
                                        String oldEntry = cartList.getElementAt(currentIndex);
                                        String[] split = oldEntry.split("x");
                                        int oldCount = split.length == 2 ? Integer.parseInt(split[1].trim()) : 1;
                                        cartList.setElementAt(foodNamePrice + " x" + (oldCount + quantity), currentIndex);  // 수량 증가
                                    } else {  // 주문 내역에 같은 음식이 없으면
                                        cartList.addElement(foodNamePrice + " x" + quantity);  // 수량만큼 새로 추가
                                    }

                                    // 총 주문 금액 업데이트
                                    totalLabel.setText("총 주문 금액: " + (Integer.parseInt(totalLabel.getText().split(": ")[1]) + food.getPrice() * quantity));

                                    // 팝업 창 닫기
                                    detailDialog.dispose();
                                }
                            });
                            detailPanel.add(addButton);

                            detailDialog.add(detailPanel);
                            detailDialog.setVisible(true);
                        }
                    });
                    foodPanel.add(foodButton);
                }
            }
            centerPanel.add(scrollPane, foodType);  // 각 패널을 foodType 이름으로 추가
        }

     // 음식 유형별 버튼에 ActionListener 추가
        for (String foodType : foodTypes) {
            JButton foodTypeButton = new JButton(foodType);
            foodTypeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 모든 버튼의 배경색을 초기화
                    for (Component comp : northPanel.getComponents()) {
                        if (comp instanceof JButton) {
                            comp.setBackground(null);
                        }
                    }
                    // 현재 누른 버튼의 배경색을 변경
                    foodTypeButton.setBackground(Color.YELLOW);
                    // 해당 음식 유형의 패널로 변경
                    cl.show(centerPanel, foodType);
                }
            });
            northPanel.add(foodTypeButton);
        }


        JButton homeButton = new JButton("홈");
//        homeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // 현재 프레임을 숨김
//                frame.setVisible(false);
//                // mainpage.java를 실행
//                MainPageUI mainPage = new MainPageUI();  // MainPage는 mainpage.java의 클래스 이름이어야 합니다.
//                mainPage.setVisible(true);  // MainPage 프레임을 보여줌
//            }
//        });
        
        // 홈 버튼을 눌렀을 때 새로운 메인 UI가 열리지 않고 현재 UI를 닫도록 수정
        homeButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	frame.dispose();
        	}
        });
        
        
        
        northPanel.add(homeButton);
        panel.add(northPanel, BorderLayout.NORTH);

        cl.show(centerPanel, "카페");  // 초기에 보여줄 패널 설정
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton cancelButton = new JButton("취소");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cart.getSelectedIndex() != -1) {
                    // 선택한 항목의 가격과 수량을 가져옴
                    String selected = cartList.getElementAt(cart.getSelectedIndex());
                    String[] split = selected.split(" ");
                    int price = 0;
                    for (int i = split.length - 1; i >= 0; i--) {
                        try {
                            price = Integer.parseInt(split[i]);
                            break;
                        } catch (NumberFormatException ex) {
                            // 숫자가 아닌 문자열을 만나면 무시하고 계속 진행
                        }
                    }
                    int quantity = split[split.length - 1].startsWith("x") ? Integer.parseInt(split[split.length - 1].substring(1)) : 1;  // 수량 정보가 있는 경우 가져옴

                    // 선택한 항목 삭제
                    cartList.remove(cart.getSelectedIndex()); 

                    // 총 주문 금액 업데이트
                    int total = Integer.parseInt(totalLabel.getText().split(": ")[1]);
                    totalLabel.setText("총 주문 금액: " + (total - price * quantity));
                }
            }
        });
        buttonPanel.add(cancelButton);
       

        // 총 주문 금액 표시
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.add(totalLabel, BorderLayout.NORTH);

        // 주문 요청사항 입력 패널
        JPanel requestPanel = new JPanel();
        JLabel requestLabel = new JLabel("주문 요청사항: ");
        JTextField requestField = new JTextField(20); // 필드 크기 설정
        requestField.setPreferredSize(new Dimension(200, 40)); // 필드 크기 조정
        requestPanel.add(requestLabel);
        requestPanel.add(requestField);
        totalPanel.add(requestPanel, BorderLayout.CENTER);

        rightPanel.add(totalPanel, BorderLayout.NORTH);

        // 버튼 패널을 rightPanel의 SOUTH에 추가
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(rightPanel, BorderLayout.EAST);

        JButton purchaseButton = new JButton("구매");
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cartList.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "장바구니가 비어있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "구매가 완료되었습니다.");
                    cartList.clear(); // 장바구니 비우기
                    requestField.setText(""); // 주문 요청사항 초기화
                }
                totalLabel.setText("총 주문 금액: 0");
            }
        });
        buttonPanel.add(purchaseButton);
        frame.add(panel);
        frame.setVisible(true);
    }
}
