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
        JFrame frame = new JFrame("���� �޴�");
  	  frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 800);
  	  frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        DefaultListModel<String> cartList = new DefaultListModel<>();
        JList<String> cart = new JList<>(cartList);
        JScrollPane cartScrollPane = new JScrollPane(cart); 

        List<Game> games = new ArrayList<>();
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));

        games.add(new Game("���� ����", "�ٵ�", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));

        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));

        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));

        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));

        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        

        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));
        games.add(new Game("���� ����", "ü��", "�̹��� URL", 3000));

        JLabel totalLabel = new JLabel("�� �ֹ� �ݾ�: 0");

        JPanel northPanel = new JPanel(new GridLayout(1, 5));
        String[] gameTypes = {"���� ����", "ī�� ����", "���÷��� ����", "���� ����", "���� ����", "���� ����"};

        CardLayout cl = new CardLayout();
        JPanel centerPanel = new JPanel(cl);

        for (String gameType : gameTypes) {
            JPanel gamePanel = new JPanel(new GridLayout(0, 3));  // ���� ũ��� 0���� �����Ͽ� �ڵ����� �����ǵ��� ��
            for (Game game : games) {
                if (game.getType().equals(gameType)) {
                	JButton gameButton = new JButton("<html><center><img src='" + game.getImageUrl() + "' width='100' height='100'/><br/>" + game.getName() + "<br/>" + game.getPrice() + "</center></html>");
                	gameButton.addActionListener(new ActionListener() {
                	    @Override
                	    public void actionPerformed(ActionEvent e) {
                	        JDialog detailDialog = new JDialog(frame, "���� �� ����", true);
                	        detailDialog.setSize(300, 200);
                	        detailDialog.setLocationRelativeTo(frame);

                	        JPanel detailPanel = new JPanel();
                	        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

                	        JLabel nameLabel = new JLabel("���� �̸�: " + game.getName());
                	        JLabel priceLabel = new JLabel("����: " + game.getPrice());
                	        
                	        

                	        // ���� ���� ���ǳ� �߰�
                	        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);  // �ʱ� �� 1, �ּ� �� 1, �ִ� �� 10, �ܰ� 1
                	        JSpinner quantitySpinner = new JSpinner(model);
                	        JLabel quantityLabel = new JLabel("����: ");
                	        quantityLabel.setLabelFor(quantitySpinner);

                	        detailPanel.add(nameLabel);
                	        detailPanel.add(priceLabel);
                	        detailPanel.add(quantityLabel);
                	        detailPanel.add(quantitySpinner);

                	        detailDialog.add(detailPanel);
                	        

                	        JButton addButton = new JButton("���");
                	        addButton.addActionListener(new ActionListener() {
                	            @Override
                	            public void actionPerformed(ActionEvent e) {
                	                int quantity = (Integer) quantitySpinner.getValue();
                	                String gameNamePrice = game.getName() + " " + game.getPrice();

                	                int currentIndex = -1;
                	                for (int i = 0; i < cartList.size(); i++) {
                	                    if (cartList.get(i).startsWith(game.getName())) {  // ���� �̸����� ã��
                	                        currentIndex = i;
                	                        break;
                	                    }
                	                }

                	                if (currentIndex != -1) {  // �̹� ����Ʈ�� �ִٸ�
                	                    String oldEntry = cartList.getElementAt(currentIndex);
                	                    String[] split = oldEntry.split("x");
                	                    int oldCount = Integer.parseInt(split[split.length - 1].trim());
                	                    cartList.setElementAt(gameNamePrice + " x" + (oldCount + quantity), currentIndex);  // ���� ������Ʈ
                	                } else {  // ����Ʈ�� ���ٸ�
                	                    cartList.addElement(gameNamePrice + " x" + quantity);  // ���� �߰�
                	                }

                	                totalLabel.setText("�� �ֹ� �ݾ�: " + (Integer.parseInt(totalLabel.getText().split(": ")[1]) + game.getPrice() * quantity));

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
            JScrollPane scrollPane = new JScrollPane(gamePanel);  // gamePanel�� JScrollPane�� �߰�
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  // �׻� ���� ��ũ�ѹ� ǥ��
            centerPanel.add(scrollPane, gameType);  // JScrollPane�� centerPanel�� �߰�
            
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
        
        
        

        JButton homeButton = new JButton("Ȩ");
//        homeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                frame.setVisible(false);
//                MainPageUI mainPage = new MainPageUI();
//                mainPage.setVisible(true);
//            }
//        });
        
        // Ȩ ��ư�� ������ �� ���ο� ���� UI�� ������ �ʰ� ���� UI�� �ݵ��� ����
        homeButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              frame.dispose();      
          	}
        });
        
        
        northPanel.add(homeButton);
        panel.add(northPanel, BorderLayout.NORTH);

        cl.show(centerPanel, "���� ����");
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));

        JButton cancelButton = new JButton("���");
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
                    totalLabel.setText("�� �ֹ� �ݾ�: " + (total - price * quantity));
                }
            }
        });
        buttonPanel.add(cancelButton);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.add(totalLabel, BorderLayout.NORTH);

        JPanel requestPanel = new JPanel();
        JLabel requestLabel = new JLabel("�ֹ� ��û����: ");
        JTextField requestField = new JTextField(20);
        requestField.setPreferredSize(new Dimension(200, 40));
        requestPanel.add(requestLabel);
        requestPanel.add(requestField);
        totalPanel.add(requestPanel, BorderLayout.CENTER);

        rightPanel.add(totalPanel, BorderLayout.NORTH);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(rightPanel, BorderLayout.EAST);

        JButton purchaseButton = new JButton("����");
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cartList.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "��ٱ��ϰ� ����ֽ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "���Ű� �Ϸ�Ǿ����ϴ�.");
                    cartList.clear();
                    requestField.setText("");
                }
                totalLabel.setText("�� �ֹ� �ݾ�: 0");
            }
        });
        buttonPanel.add(purchaseButton);
        frame.add(panel);
        frame.setVisible(true);
    }
}