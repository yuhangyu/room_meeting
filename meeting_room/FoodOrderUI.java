package meeting_room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// ���� �����͸� �����ϴ� Ŭ����
class Food {
    String type;
    String name;
    String detail;  // �� ���� �ʵ� �߰�
    String imageUrl;
    int price;

    Food(String type, String name, String detail, String imageUrl, int price) {  // �� ������ �޴� ������ �߰�
        this.type = type;
        this.name = name;
        this.detail = detail;  // �� ���� �ʵ� �ʱ�ȭ
        this.imageUrl = imageUrl;
        this.price = price;
    }

    String getType() { return type; }
    String getName() { return name; }
    String getDetail() { return detail; }  // �� ���� getter �޼��� �߰�
    String getImageUrl() { return imageUrl; }
    int getPrice() { return price; }
}

public class FoodOrderUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("���� �޴�");
  	  frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
  	  frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // ��ٱ��� ����Ʈ ����
        DefaultListModel<String> cartList = new DefaultListModel<>();
        JList<String> cart = new JList<>(cartList);
        JScrollPane cartScrollPane = new JScrollPane(cart); 

        // ���� ������ ����
        List<Food> foods = new ArrayList<>();
        foods.add(new Food("ī��", "���̽�Ƽ", "���̽�Ƽ�� �� ����", "http://example.com/iced_tea_image.jpg", 2000));
        foods.add(new Food("ī��", "��������", "���������� �� ����", "http://example.com/mango_smoothie_image.jpg", 4500));

        // �� �ֹ� �ݾ� ǥ��
        JLabel totalLabel = new JLabel("�� �ֹ� �ݾ�: 0");

        JPanel northPanel = new JPanel(new GridLayout(1, 5));
        String[] foodTypes = {"ī��", "����", "���", "���", "����", "��"};

        // centerPanel�� CardLayout���� ����
        CardLayout cl = new CardLayout();
        JPanel centerPanel = new JPanel(cl);

        // ���� �������� JPanel ����
        for (String foodType : foodTypes) {
            JPanel foodPanel = new JPanel(new GridLayout(0, 5)); // ���� ũ��� �������� ����
            JScrollPane scrollPane = new JScrollPane(foodPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  // ���� ��ũ�Ѹ� Ȱ��ȭ
            for (final Food food : foods) {
                if (food.getType().equals(foodType)) {
                    JButton foodButton = new JButton("<html><center><img src='" + food.getImageUrl() + "' width='100' height='100'/><br/>" + food.getName() + "<br/>" + food.getPrice() + "</center></html>");
                    foodButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // �˾� â�� �����ϰ� ������
                            JDialog detailDialog = new JDialog(frame, "���� �� ����", true);
                            detailDialog.setSize(300, 200);
                            detailDialog.setLocationRelativeTo(frame);

                            // �˾� â�� ���� ������ ǥ��
                            JPanel detailPanel = new JPanel();
                            detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

                            JLabel nameLabel = new JLabel("�̸�: " + food.getName());
                            JLabel detailLabel = new JLabel("�� ����: " + food.getDetail());
                            JLabel priceLabel = new JLabel("����: " + food.getPrice());

                            SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);  // �ʱ� �� 1, �ּ� �� 1, �ִ� �� 10, �ܰ� 1
                            JSpinner quantitySpinner = new JSpinner(model);
                            JLabel quantityLabel = new JLabel("����: ");
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


                             // '���' ��ư �߰�
                            JButton addButton = new JButton("���");

                            addButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // �ֹ� ������ ���� �߰�
                                    String foodNamePrice = food.getName() + " " + food.getPrice();

                                    // ���ǳʿ��� ���õ� ������ ������
                                    int quantity = (Integer) quantitySpinner.getValue();

                                    // �ֹ� ������ �̹� ���� ������ ������
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
                                        cartList.setElementAt(foodNamePrice + " x" + (oldCount + quantity), currentIndex);  // ���� ����
                                    } else {  // �ֹ� ������ ���� ������ ������
                                        cartList.addElement(foodNamePrice + " x" + quantity);  // ������ŭ ���� �߰�
                                    }

                                    // �� �ֹ� �ݾ� ������Ʈ
                                    totalLabel.setText("�� �ֹ� �ݾ�: " + (Integer.parseInt(totalLabel.getText().split(": ")[1]) + food.getPrice() * quantity));

                                    // �˾� â �ݱ�
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
            centerPanel.add(scrollPane, foodType);  // �� �г��� foodType �̸����� �߰�
        }

     // ���� ������ ��ư�� ActionListener �߰�
        for (String foodType : foodTypes) {
            JButton foodTypeButton = new JButton(foodType);
            foodTypeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // ��� ��ư�� ������ �ʱ�ȭ
                    for (Component comp : northPanel.getComponents()) {
                        if (comp instanceof JButton) {
                            comp.setBackground(null);
                        }
                    }
                    // ���� ���� ��ư�� ������ ����
                    foodTypeButton.setBackground(Color.YELLOW);
                    // �ش� ���� ������ �гη� ����
                    cl.show(centerPanel, foodType);
                }
            });
            northPanel.add(foodTypeButton);
        }


        JButton homeButton = new JButton("Ȩ");
//        homeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // ���� �������� ����
//                frame.setVisible(false);
//                // mainpage.java�� ����
//                MainPageUI mainPage = new MainPageUI();  // MainPage�� mainpage.java�� Ŭ���� �̸��̾�� �մϴ�.
//                mainPage.setVisible(true);  // MainPage �������� ������
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

        cl.show(centerPanel, "ī��");  // �ʱ⿡ ������ �г� ����
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton cancelButton = new JButton("���");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cart.getSelectedIndex() != -1) {
                    // ������ �׸��� ���ݰ� ������ ������
                    String selected = cartList.getElementAt(cart.getSelectedIndex());
                    String[] split = selected.split(" ");
                    int price = 0;
                    for (int i = split.length - 1; i >= 0; i--) {
                        try {
                            price = Integer.parseInt(split[i]);
                            break;
                        } catch (NumberFormatException ex) {
                            // ���ڰ� �ƴ� ���ڿ��� ������ �����ϰ� ��� ����
                        }
                    }
                    int quantity = split[split.length - 1].startsWith("x") ? Integer.parseInt(split[split.length - 1].substring(1)) : 1;  // ���� ������ �ִ� ��� ������

                    // ������ �׸� ����
                    cartList.remove(cart.getSelectedIndex()); 

                    // �� �ֹ� �ݾ� ������Ʈ
                    int total = Integer.parseInt(totalLabel.getText().split(": ")[1]);
                    totalLabel.setText("�� �ֹ� �ݾ�: " + (total - price * quantity));
                }
            }
        });
        buttonPanel.add(cancelButton);
       

        // �� �ֹ� �ݾ� ǥ��
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.add(totalLabel, BorderLayout.NORTH);

        // �ֹ� ��û���� �Է� �г�
        JPanel requestPanel = new JPanel();
        JLabel requestLabel = new JLabel("�ֹ� ��û����: ");
        JTextField requestField = new JTextField(20); // �ʵ� ũ�� ����
        requestField.setPreferredSize(new Dimension(200, 40)); // �ʵ� ũ�� ����
        requestPanel.add(requestLabel);
        requestPanel.add(requestField);
        totalPanel.add(requestPanel, BorderLayout.CENTER);

        rightPanel.add(totalPanel, BorderLayout.NORTH);

        // ��ư �г��� rightPanel�� SOUTH�� �߰�
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
                    cartList.clear(); // ��ٱ��� ����
                    requestField.setText(""); // �ֹ� ��û���� �ʱ�ȭ
                }
                totalLabel.setText("�� �ֹ� �ݾ�: 0");
            }
        });
        buttonPanel.add(purchaseButton);
        frame.add(panel);
        frame.setVisible(true);
    }
}
