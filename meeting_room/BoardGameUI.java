package meeting_room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Game {
    String type;
    String name;
    String imageUrl;
    int price;

    Game(String type, String name, String imageUrl, int price) {
        this.type = type;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    String getType() { return type; }
    String getName() { return name; }
    String getImageUrl() { return imageUrl; }
    int getPrice() { return price; }
}

public class BoardGameUI extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("게임 메뉴");
  	  frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 800);
  	  frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        DefaultListModel<String> cartList = new DefaultListModel<>();
        JList<String> cart = new JList<>(cartList);
        JScrollPane cartScrollPane = new JScrollPane(cart); 

        List<Game> games = new ArrayList<>();
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));

        games.add(new Game("보드 게임", "바둑", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));

        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));

        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));

        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));

        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        

        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));
        games.add(new Game("보드 게임", "체스", "이미지 URL", 3000));

        JLabel totalLabel = new JLabel("총 주문 금액: 0");

        JPanel northPanel = new JPanel(new GridLayout(1, 5));
        String[] gameTypes = {"보드 게임", "카드 게임", "롤플레잉 게임", "전략 게임", "협동 게임", "퍼즐 게임"};

        CardLayout cl = new CardLayout();
        JPanel centerPanel = new JPanel(cl);

        for (String gameType : gameTypes) {
            JPanel gamePanel = new JPanel(new GridLayout(0, 3));  // 세로 크기는 0으로 설정하여 자동으로 조절되도록 함
            for (Game game : games) {
                if (game.getType().equals(gameType)) {
                	JButton gameButton = new JButton("<html><center><img src='" + game.getImageUrl() + "' width='100' height='100'/><br/>" + game.getName() + "<br/>" + game.getPrice() + "</center></html>");
                	gameButton.addActionListener(new ActionListener() {
                	    @Override
                	    public void actionPerformed(ActionEvent e) {
                	        JDialog detailDialog = new JDialog(frame, "게임 상세 정보", true);
                	        detailDialog.setSize(300, 200);
                	        detailDialog.setLocationRelativeTo(frame);

                	        JPanel detailPanel = new JPanel();
                	        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

                	        JLabel nameLabel = new JLabel("게임 이름: " + game.getName());
                	        JLabel priceLabel = new JLabel("가격: " + game.getPrice());
                	        
                	        

                	        // 수량 선택 스피너 추가
                	        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);  // 초기 값 1, 최소 값 1, 최대 값 10, 단계 1
                	        JSpinner quantitySpinner = new JSpinner(model);
                	        JLabel quantityLabel = new JLabel("수량: ");
                	        quantityLabel.setLabelFor(quantitySpinner);

                	        detailPanel.add(nameLabel);
                	        detailPanel.add(priceLabel);
                	        detailPanel.add(quantityLabel);
                	        detailPanel.add(quantitySpinner);

                	        detailDialog.add(detailPanel);
                	        

                	        JButton addButton = new JButton("담기");
                	        addButton.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                	                int quantity = (Integer) quantitySpinner.getValue();
                	                String gameNamePrice = game.getName() + " " + game.getPrice();

                	                int currentIndex = -1;
                	                for (int i = 0; i < cartList.size(); i++) {
                	                    if (cartList.get(i).startsWith(game.getName())) {  // 게임 이름으로 찾기
                	                        currentIndex = i;
                	                        break;
                	                    }
                	                }

                	                if (currentIndex != -1) {  // 이미 리스트에 있다면
                	                    String oldEntry = cartList.getElementAt(currentIndex);
                	                    String[] split = oldEntry.split("x");
                	                    int oldCount = Integer.parseInt(split[split.length - 1].trim());
                	                    cartList.setElementAt(gameNamePrice + " x" + (oldCount + quantity), currentIndex);  // 수량 업데이트
                	                } else {  // 리스트에 없다면
                	                    cartList.addElement(gameNamePrice + " x" + quantity);  // 새로 추가
                	                }

                	                totalLabel.setText("총 주문 금액: " + (Integer.parseInt(totalLabel.getText().split(": ")[1]) + game.getPrice() * quantity));

                	                detailDialog.dispose();
                	            }
                	        });
                	        detailDialog.add(addButton, BorderLayout.SOUTH);

                            detailDialog.setVisible(true);
                            
                        }
                	    
                    });
                	gamePanel.add(gameButton);
                	centerPanel.add(gamePanel, gameType);
                	
                }
                
            }
            JScrollPane scrollPane = new JScrollPane(gamePanel);  // gamePanel을 JScrollPane에 추가
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  // 항상 세로 스크롤바 표시
            centerPanel.add(scrollPane, gameType);  // JScrollPane을 centerPanel에 추가
            
        }
        

        for (String gameType : gameTypes) {
            JButton gameTypeButton = new JButton(gameType);
            gameTypeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Component comp : northPanel.getComponents()) {
                        if (comp instanceof JButton) {
                            comp.setBackground(null);
                        }
                    }
                    gameTypeButton.setBackground(Color.YELLOW);
                    cl.show(centerPanel, gameType);
                }
            });
            northPanel.add(gameTypeButton);
        }
        
        
        

        JButton homeButton = new JButton("홈");
//        homeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                frame.setVisible(false);
//                MainPageUI mainPage = new MainPageUI();
//                mainPage.setVisible(true);
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

        cl.show(centerPanel, "보드 게임");
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));

        JButton cancelButton = new JButton("취소");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cart.getSelectedIndex() != -1) {
                    String selected = cartList.getElementAt(cart.getSelectedIndex());
                    String[] split = selected.split(" ");
                    int price = 0;
                    for (int i = split.length - 1; i >= 0; i--) {
                        try {
                            price = Integer.parseInt(split[i]);
                            break;
                        } catch (NumberFormatException ex) {
                        }
                    }
                    int quantity = split[split.length - 1].startsWith("x") ? Integer.parseInt(split[split.length - 1].substring(1)) : 1;

                    cartList.remove(cart.getSelectedIndex());

                    int total = Integer.parseInt(totalLabel.getText().split(": ")[1]);
                    totalLabel.setText("총 주문 금액: " + (total - price * quantity));
                }
            }
        });
        buttonPanel.add(cancelButton);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.add(totalLabel, BorderLayout.NORTH);

        JPanel requestPanel = new JPanel();
        JLabel requestLabel = new JLabel("주문 요청사항: ");
        JTextField requestField = new JTextField(20);
        requestField.setPreferredSize(new Dimension(200, 40));
        requestPanel.add(requestLabel);
        requestPanel.add(requestField);
        totalPanel.add(requestPanel, BorderLayout.CENTER);

        rightPanel.add(totalPanel, BorderLayout.NORTH);
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
                    cartList.clear();
                    requestField.setText("");
                }
                totalLabel.setText("총 주문 금액: 0");
            }
        });
        buttonPanel.add(purchaseButton);
        frame.add(panel);
        frame.setVisible(true);
    }
}