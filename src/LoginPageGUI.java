import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class LoginPageGUI extends JFrame{
    private User currentUser;
    private ShoppingCart shoppingCart;
    private UserInfoHandle userInfoHandler;
    private ShoppingCenterGUI centerGUI;
    private ShoppingManager shoppingManager;
    private JTextArea usernameField;
    private JPasswordField passwordField;
    private JLabel userNameLabel, passwordLabel;
    private JPanel panel1,panel2,panel3,panel4,panel5;
    private JButton signInBtn, signUpBtn;
    private ArrayList<User> userInfoArrayCopy;


    LoginPageGUI(ArrayList<User> userInfoArrayCopy){
        this.userInfoArrayCopy = userInfoArrayCopy;
        userInfoHandler = new UserInfoHandle();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        panel5.setPreferredSize(new Dimension(400,60));

        LoginPageFrame();
        userNameTxtArea();
        panel3.add(userNameLabel);
        panel3.add(usernameField);
        passwordTxtArea();
        panel4.add(passwordLabel);
        panel4.add(passwordField);
        panel1.add(panel3);
        panel1.add(panel4);
        signInBtn();
        panel2.add(signInBtn);
        signUpBtn();
        panel2.add(signUpBtn);

        add(panel5,BorderLayout.NORTH);
        add(panel1,BorderLayout.CENTER);
        add(panel2,BorderLayout.SOUTH);

        setSize(400,300);
        setVisible(true);
    }


    public void LoginPageFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login page");
        setLayout(new BorderLayout(0,30));
    }

    public void userNameTxtArea(){
        userNameLabel = new JLabel("User Name");
        usernameField = new JTextArea();
        usernameField.setPreferredSize(new Dimension(200, usernameField.getPreferredSize().height));
    }

    public void passwordTxtArea(){
        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, passwordField.getPreferredSize().height));
    }

    public void signInBtn(){
        signInBtn = new JButton("Sign In");
        signInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signInBtnAction();
            }
        });
    }

    public void signUpBtn(){
        signUpBtn = new JButton("Sign Up");
        signUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpBtnAction();
            }
        });
    }

    public void signUpBtnAction(){
        boolean userFound = false;
        for(User user :userInfoArrayCopy){
            if(user.getName().equals(usernameField.getText())){
                JOptionPane.showMessageDialog(null,"User already exist in the system","Alert",JOptionPane.WARNING_MESSAGE);
                userFound = true;
            }
        }
        if(!userFound){
            currentUser = new User(usernameField.getText(), passwordField.getPassword());
            userInfoHandler.getUserInfo().add(currentUser);
            userInfoHandler.saveInfoFile();
            shoppingCart = new ShoppingCart();
            shoppingManager = new WestminsterShoppingManager();
            shoppingManager.loadFiles();
            centerGUI = new ShoppingCenterGUI(shoppingManager,shoppingCart, usernameField.getText());
        }
    }

    public void signInBtnAction(){
        if(userInfoArrayCopy.isEmpty()){
            JOptionPane.showMessageDialog(null,"Invalid User name or Password","Alert",JOptionPane.WARNING_MESSAGE);
        }else{
            boolean found = false;
            for(User user : userInfoArrayCopy){
                if(Objects.equals(user.getName(), usernameField.getText()) && Arrays.equals(user.getPassword(), passwordField.getPassword())){
                    shoppingCart = new ShoppingCart();
                    shoppingManager = new WestminsterShoppingManager();
                    shoppingManager.loadFiles();
                    centerGUI = new ShoppingCenterGUI(shoppingManager,shoppingCart, usernameField.getText());
                    found = true;
                }
            }
            if(!found){
                JOptionPane.showMessageDialog(null,"Invalid User name or Password","Alert",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
