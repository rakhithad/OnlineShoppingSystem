import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class ShoppingCenterGUI extends JFrame {
    // Instance variables
    private ShoppingManager manager;
    private ShoppingCart shoppingCart;
    private ShoppingCartGUI shoppingCartGUI;
    private JLabel text;
    private JComboBox Category;
    private JButton sort;
    private JButton ShoppingCart;
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JScrollPane scrollPane;
    private JLabel textTitle;
    private JLabel infoData;
    private JButton addCart;
    private JPanel addCartPanel;
    private String name;


    ShoppingCenterGUI(ShoppingManager manager, ShoppingCart shoppingCart, String name) {
        this.manager = manager;
        this.shoppingCart = shoppingCart;
        this.name = name;


        ShoppingCenterFrame();
        ShoppingCenterCategoryBox();
        shoppingTableSortBtn();
        ShoppingCenterCartBtn();
        ShoppingCenterItemTable();
        ShoppingCenterSelectedProdInfo();
        ShoppingCenterAddCartBtn();


        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(1000, 100));
        add(panel1, BorderLayout.NORTH);
        panel1.add(text);
        panel1.add(Category);
        panel1.add(sort);
        panel1.add(ShoppingCart);

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(1000, 300));
        add(panel2, BorderLayout.CENTER);
        panel2.add(scrollPane);

        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.setPreferredSize(new Dimension(540, 230));
        add(panel3, BorderLayout.SOUTH);
        panel3.add(textTitle, BorderLayout.NORTH);
        panel3.add(infoData, BorderLayout.WEST);
        panel3.add(addCartPanel, BorderLayout.SOUTH);

        setSize(650, 700);
        setVisible(true);
    }



    public void ShoppingCenterFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Westminster Shopping Center");
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    public void ShoppingCenterCategoryBox() {
        text = new JLabel("Select Product Category");

        Category = new JComboBox(new String[]{"All", "Electronic", "Clothes"});
        Category.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e != null) {
                    categoryBoxAction();
                }
            }
        });
        Category.setLocation(620, 630);
    }

    public void shoppingTableSortBtn() {
        sort = new JButton("Sort");
        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e != null) {
                    tableSortMethod();
                }
            }
        });
    }

    public void ShoppingCenterCartBtn() {
        ShoppingCart = new JButton("Shopping cart");
        ShoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCartGUI = new ShoppingCartGUI(shoppingCart, name);
                shoppingCartGUI.shoppingCartTable();
            }
        });
    }


    public void ShoppingCenterItemTable() {
        // Table model and initialization
        String[] ColumnName = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        tableModel = new DefaultTableModel(new Object[][]{}, ColumnName) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        for (Product product : manager.getProdList()) {
            if (product instanceof Electronic) {
                tableModel.addRow(new Object[]{product.getProdId(), product.getProdName(), "Electronics", product.getPrice(), (product.getBrand() + ',' + product.getWarrantyPeriod())});
            } else if (product instanceof Clothing) {
                tableModel.addRow(new Object[]{product.getProdId(), product.getProdName(), "Clothing", product.getPrice(), (product.getSize() + ',' + product.getColor())});
            }
        }


        productTable = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                // Highlight rows based on conditions
                ArrayList<Integer> rowsToHighlight = new ArrayList<>();
                for (Product product : manager.getProdList()) {
                    if (product.getNumberOfAvailableItems() < 3) {
                        for (int i = 0; i < productTable.getRowCount(); i++) {
                            Object rowNum = productTable.getValueAt(i, 0);
                            if (product.getProdId().equals(rowNum.toString())) {
                                rowsToHighlight.add(i);
                            }
                        }
                    }
                }
                // Set background color based on identified rows
                if (rowsToHighlight.contains(row)) {
                    component.setBackground(Color.RED);
                } else {
                    component.setBackground(getBackground());
                }
                return component;
            }
        };


        JTableHeader TableHeader = productTable.getTableHeader();
        TableHeader.setReorderingAllowed(false);
        ListSelectionModel selectionModel = productTable.getSelectionModel();
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                rowSelectAction();
            }
        });


        TableColumn column = productTable.getColumnModel().getColumn(4);
        column.setPreferredWidth(170);
        scrollPane = new JScrollPane(productTable);
        Dimension preferredSize = new Dimension(580, 250);
        productTable.setPreferredScrollableViewportSize(preferredSize);
    }

    public void ShoppingCenterSelectedProdInfo() {
        textTitle = new JLabel();
        textTitle.setHorizontalAlignment(JLabel.LEFT);
        textTitle.setFont(new Font("TimesRoman", Font.BOLD, 17));

        infoData = new JLabel();
        infoData.setBounds(0, 250, 100, 50);
        infoData.setFont(new Font("TimesRoman", Font.PLAIN, 15));
    }

    public void ShoppingCenterAddCartBtn() {
        addCart = new JButton("Add to cart the item");
        addCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCartBtnAction();
            }
        });
        addCartPanel = new JPanel(new FlowLayout());
        addCartPanel.add(addCart);
    }




    public void categoryBoxAction() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }

        if (Category.getSelectedIndex() == 0) {
            for (Product product : manager.getProdList()) {
                if (product instanceof Electronic) {
                    tableModel.addRow(new Object[]{product.getProdId(), product.getProdName(), "Electronics", product.getPrice(), (product.getBrand() + ',' + product.getWarrantyPeriod())});
                } else if (product instanceof Clothing) {
                    tableModel.addRow(new Object[]{product.getProdId(), product.getProdName(), "Clothing", product.getPrice(), (product.getSize() + ',' + product.getColor())});
                }
            }
        } else if (Category.getSelectedIndex() == 1) {
            for (Product product : manager.getProdList()) {
                if (product instanceof Electronic) {
                    tableModel.addRow(new Object[]{product.getProdId(), product.getProdName(), "Electronics", product.getPrice(), (product.getBrand() + ',' + product.getWarrantyPeriod())});
                }
            }
        } else if (Category.getSelectedIndex() == 2) {
            for (Product product : manager.getProdList()) {
                if (product instanceof Clothing) {
                    tableModel.addRow(new Object[]{product.getProdId(), product.getProdName(), "Clothing", product.getPrice(), (product.getSize() + ',' + product.getColor())});
                }
            }
        }
    }

    public void rowSelectAction() {
        int SelectedRow = productTable.getSelectedRow();
        if (SelectedRow != -1) {
            for (Product product : manager.getProdList()) {
                if (product.getProdId() == tableModel.getValueAt(SelectedRow, 0)) {
                    if (product instanceof Electronic) {
                        textTitle.setText("Selected product details");
                        infoData.setText("<html>Product Id:- " + product.getProdId() +
                                "<br>Category:- " + "Electronic" +
                                "<br>Name:- " + product.getProdName() +
                                "<br>Brand:- " + product.getBrand() +
                                "<br>Warranty:- " + product.getWarrantyPeriod() +
                                "<br>Items available:- " + product.getNumberOfAvailableItems() + "</html>");

                    } else if (product instanceof Clothing) {
                        textTitle.setText("Selected product details");
                        infoData.setText("<html>Product Id:- " + product.getProdId() +
                                "<br>Category:- " + "Clothing" +
                                "<br>Name:- " + product.getProdName() +
                                "<br>Size:- " + product.getSize() +
                                "<br>Colour:- " + product.getColor() +
                                "<br>Items available:- " + product.getNumberOfAvailableItems() + "</html>");
                    }
                }
            }
        }
    }

    public void addCartBtnAction() {
        Object selectedRow = tableModel.getValueAt(productTable.getSelectedRow(), 0);
        for (Product product : manager.getProdList()) {
            if (product.getNumberOfAvailableItems() != 0) {
                if (product.getProdId().equals(selectedRow)) {
                    if (shoppingCart.getSelectedProducts().isEmpty()) {
                        shoppingCart.addProd(product);
                        int availableItem = product.getNumberOfAvailableItems();
                        availableItem -= 1;
                        product.setNumberOfAvailableItems(availableItem);
                    } else {
                        boolean found = false;
                        for (Product product1 : shoppingCart.getSelectedProducts()) {
                            if (Objects.equals(product.getProdId(), product1.getProdId())) {
                                int quantity = shoppingCart.getProdIdAndQuantity().get(product1.getProdId()) + 1;
                                shoppingCart.addItemsToPIQhashmap(product1.getProdId(), quantity);
                                found = true;
                                int availableItem = product.getNumberOfAvailableItems();
                                availableItem -= 1;
                                product.setNumberOfAvailableItems(availableItem);
                                break;
                            }
                        }
                        if (!found) {
                            shoppingCart.addProd(product);
                            int availableItem = product.getNumberOfAvailableItems();
                            availableItem -= 1;
                            product.setNumberOfAvailableItems(availableItem);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void tableSortMethod() {
        Collections.sort(manager.getProdList(), Comparator.comparing(Product::getProdName));
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        for (Product product : manager.getProdList()) {
            if (product instanceof Clothing) {
                tableModel.addRow(new Object[]{product.getProdId(), product.getProdName(), "Clothing", product.getPrice(), (product.getSize() + ',' + product.getColor())});
            } else if (product instanceof Electronic) {
                tableModel.addRow(new Object[]{product.getProdId(), product.getProdName(), "Electronics", product.getPrice(), (product.getBrand() + ',' + product.getWarrantyPeriod())});
            }
        }
    }
}

