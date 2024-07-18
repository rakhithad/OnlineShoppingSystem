import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class UserHistoryHandle {
    private ArrayList<UserHistory> userHistoryArrayList;
    private UserHistory userHistory;


    public UserHistoryHandle() {
        userHistoryArrayList = new ArrayList<>();
    }

    public ArrayList<UserHistory> getUserHistoryArrayList() {
        return userHistoryArrayList;
    }

    public void saveHistoryToFile(){
        String filePath = "UserHistory.txt";
        try (FileWriter fileWriter = new FileWriter(filePath)){
            for(UserHistory userHistory: userHistoryArrayList){
                String temp = userHistory.getProdMap().toString();
                fileWriter.write(userHistory.getUserName()+","+temp.replaceAll("[{}]","").replaceAll(" ","")+'\n');
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public void LoadHistoryFromFile() {
        String filePath = "UserHistory.txt";
        File shoppingHistory = new File(filePath);

        try (Scanner fileReader = new Scanner(shoppingHistory)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] temp = line.split(",");
                if(temp.length >= 2) {
                    String name = temp[0];
                    String[] temp2 = Arrays.copyOfRange(temp, 1, temp.length);
                    HashMap<String, Integer> tempMap = new HashMap<>();

                    for (String kv : temp2) {
                        String[] parts = kv.split("=");
                        String key = parts[0];
                        int value = Integer.parseInt(parts[1]);
                        tempMap.put(key, value);
                    }

                    userHistory = new UserHistory(name, tempMap);
                    userHistoryArrayList.add(userHistory);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public String toString() {
        return "UserHistoryHandle{" +
                "userHistoryArrayList=" + userHistoryArrayList +
                '}';
    }
}
