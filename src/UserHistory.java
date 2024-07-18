import java.util.HashMap;

public class UserHistory {
    private String userName;
    private HashMap<String,Integer> productMap;

    public UserHistory(String userName, HashMap<String, Integer> productMap) {
        this.userName = userName;
        this.productMap = productMap;
    }

    public String getUserName() {
        return userName;
    }

    public HashMap<String, Integer> getProdMap() {
        return productMap;
    }

    @Override
    public String toString() {
        return "UserHistory{" +
                "userName='" + userName + '\'' +
                ", productMap=" + productMap +
                '}';
    }
}
