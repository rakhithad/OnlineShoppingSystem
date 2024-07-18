import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoppingCartGUI extends JFrame {

    private ShoppingCart shoppingCart;
    private UserHistory history;
    private UserHistoryHandle historyHandle;
    private DefaultTableModel tableModel;
    private JTable cartTable;
    private JButton buyBtn, removeBtn;
    private JPanel panel1, panel2, panel3, panel4, panel5;
    private String userName;
    private String selectedProductInfo;
    private JLabel total, finalPrice, firstUserDis, threeItemsDis;


    ShoppingCartGUI(ShoppingCart shoppingCart, String userName) {
        this.shoppingCart = shoppingCart;
        this.userName = userName;
        historyHandle = new UserHistoryHandle();
        historyHandle.LoadHistoryFromFile();

        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel(new GridLayout(4,1));
        panel5.setPreferredSize(new Dimension(400,100));

        ShoppingCartFrame();
        ShoppingCartTable();
        ShoppingCartRemoveButton();
        pricePanel();
        ShoppingCartBuyButton();
        panel2.add(panel5);

        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH);
        add(panel4, BorderLayout.EAST);
        setSize(650, 500);
        setVisible(true);
    }


    public void ShoppingCartFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Shopping Cart");
        setLayout(new BorderLayout());
    }


    public void ShoppingCartTable() {
        String[] columnName = {"Product", "Quantity", "Price(£)"};

        tableModel = new DefaultTableModel(new Object[][]{}, columnName) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cartTable = new JTable(tableModel);
        JTableHeader TableHeader = cartTable.getTableHeader();
        TableHeader.setReorderingAllowed(false);
        cartTable.setRowHeight(60);


        ListSelectionModel selectionModel = cartTable.getSelectionModel();
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    shoppingCartTableSelect();
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(cartTable);
        Dimension preferredSize = new Dimension(580, 250);
        cartTable.setPreferredScrollableViewportSize(preferredSize);
        panel1.add(scrollPane);
    }


    public void ShoppingCartBuyButton() {
        buyBtn = new JButton("Confirm the Order");
        buyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShoppingCartBuyButtonActionPerformed();
            }
        });
        panel3.add(buyBtn);
    }


    public void ShoppingCartRemoveButton() {
        removeBtn = new JButton("Remove the Item");
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCartRemoveButtonAction();
            }
        });
        panel4.add(removeBtn);
    }


    public void shoppingCartTable() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (Product product : shoppingCart.getSelectedProducts()) {
            double price = shoppingCart.getProdIdAndQuantity().get(product.getProdId()) * product.getPrice();
            String formattedPrice = decimalFormat.format(price);

            if (product instanceof Electronic) {
                String productInfo = "<html>" + product.getProdId() + "<br>" + product.getProdName() + "<br>" + product.getBrand() + "," + product.getWarrantyPeriod() + "</html>";
                tableModel.addRow(new Object[]{productInfo, shoppingCart.getProdIdAndQuantity().get(product.getProdId()), formattedPrice});
            } else if (product instanceof Clothing) {
                String productInfo = "<html>" + product.getProdId() + "<br>" + product.getProdName() + "<br>" + product.getSize() + "," + product.getColor() + "</html>";
                tableModel.addRow(new Object[]{productInfo, shoppingCart.getProdIdAndQuantity().get(product.getProdId()), formattedPrice});
            }
        }
    }


    public void pricePanel() {
        total = new JLabel();
        firstUserDis = new JLabel();
        threeItemsDis = new JLabel();
        finalPrice = new JLabel();
        pricePanelAction();
        total.setText("Total " + shoppingCart.getTotal()+"£");
        firstUserDis.setText("First purchase discount (10%) "+shoppingCart.getFirstUserDiscount()+"£");
        threeItemsDis.setText("Three items in the same category discount (20%) "+shoppingCart.getThreeItemDiscount()+"£");
        finalPrice.setText("Final price " + shoppingCart.getFinalPrice()+"£");
    }


    public void pricePanelAction() {
        if (historyHandle.getUserHistoryArrayList().isEmpty()) {
            boolean founded = false;
            for (HashMap.Entry<String, Integer> entry : shoppingCart.getProdIdAndQuantity().entrySet()) {
                if (entry.getValue() >= 3) {
                    panel5.add(total);
                    panel5.add(firstUserDis);
                    panel5.add(threeItemsDis);
                    panel5.add(finalPrice);
                    shoppingCart.calculateTotalCost(true, true);
                    founded = true;
                }
            }
            if (!founded) {
                panel5.add(total);
                panel5.add(firstUserDis);
                panel5.add(finalPrice);
                shoppingCart.calculateTotalCost(true, false);
            }
        } else {
            boolean cancelLoop = false;
            for (UserHistory userHistory1 : historyHandle.getUserHistoryArrayList()) {
                if (userHistory1.getUserName().equals(userName)) {
                    cancelLoop = true;
                    boolean found = false;
                    for (HashMap.Entry<String, Integer> entry : shoppingCart.getProdIdAndQuantity().entrySet()) {
                        System.out.println(entry.getValue());
                        if (entry.getValue() >= 3) {
                            panel5.add(total);
                            panel5.add(threeItemsDis);
                            panel5.add(finalPrice);
                            shoppingCart.calculateTotalCost(false, true);
                            found = true;
                        }
                    }
                    if (!found) {
                        panel5.add(total);
                        panel5.add(finalPrice);
                        shoppingCart.calculateTotalCost(false, false);
                    }
                }
            }
            if (!cancelLoop) {
                boolean notFound = true;
                for (HashMap.Entry<String, Integer> entry : shoppingCart.getProdIdAndQuantity().entrySet()) {
                    if (entry.getValue() >= 3) {
                        panel5.add(total);
                        panel5.add(firstUserDis);
                        panel5.add(threeItemsDis);
                        panel5.add(finalPrice);
                        shoppingCart.calculateTotalCost(true, true);
                        notFound = false;
                    }
                }
                if (notFound) {
                    panel5.add(total);
                    panel5.add(firstUserDis);
                    panel5.add(finalPrice);
                    shoppingCart.calculateTotalCost(true, false);
                }
            }
        }
    }


    public void ShoppingCartBuyButtonActionPerformed() {
        history = new UserHistory(userName, shoppingCart.getProdIdAndQuantity());
        boolean userFound = false;
        for (UserHistory userDetails : historyHandle.getUserHistoryArrayList()) {
            if (userDetails.getUserName().equals(userName)) {
                for (HashMap.Entry<String, Integer> entry : shoppingCart.getProdIdAndQuantity().entrySet()) {
                    String keys = entry.getKey();
                    int newValue = entry.getValue();

                    if (userDetails.getProdMap().containsKey(keys)) {
                        int value = history.getProdMap().get(keys) + newValue;

                        // Add the existing value to the new value and update in productMap
                        userDetails.getProdMap().put(keys, value);

                    } else {
                        // If the key is not present, simply put the new value in productMap
                        userDetails.getProdMap().put(keys, newValue);
                    }
                }
                userFound = true;
            }
        }
        if (!userFound) {
            historyHandle.getUserHistoryArrayList().add(history);
        }
        historyHandle.saveHistoryToFile();
        shoppingCart.getSelectedProducts().clear();
        shoppingCart.getProdIdAndQuantity().clear();
    }


    public void shoppingCartTableSelect() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow != -1) {
            selectedProductInfo = tableModel.getValueAt(selectedRow, 0).toString();
        }
    }


    public void shoppingCartRemoveButtonAction() {
        if (selectedProductInfo != null) {
            Pattern pattern = Pattern.compile("<html>(.*?)<br>");
            Matcher matcher = pattern.matcher(selectedProductInfo);


            if (matcher.find()) {
                String productId = matcher.group(1);
                shoppingCart.removeProd(productId);
            }
        }
    }
}
