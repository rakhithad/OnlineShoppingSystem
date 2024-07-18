import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInfoHandle {
    private ArrayList<User> userInfo;


    public UserInfoHandle() {
        userInfo = new ArrayList<>();

    }
    public ArrayList<User> getUserInfo() {
        return userInfo;
    }

    public void loadDataFromUserDetails() {
        try {
            String filePath = "UserDetails.txt";
            File Users = new File(filePath);
            Scanner fileReader = new Scanner(Users);
            while (fileReader.hasNextLine()) {
                String[] temp = fileReader.nextLine().split(",");
                char[] tempPassword = temp[1].toCharArray();
                User user = new User(temp[0],tempPassword);
                userInfo.add(user);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public void saveInfoFile(){
        String filePath = "UserDetails.txt";
        try (FileWriter fileWriter = new FileWriter(filePath,true)){
            for(User user: userInfo){
                String password = new String(user.getPassword());
                fileWriter.write(user.getName()+","+ password+'\n');
            }
            System.out.println("User details have been saved to the file.");
        }catch(IOException e){
            System.out.println("Error saving user details: " + e.getMessage());
        }
    }

}
