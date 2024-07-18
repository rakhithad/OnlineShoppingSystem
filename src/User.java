public class User {
    private String name;
    private char[] password;

    public User(String name, char[] password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public char[] getPassword() {
        return password;
    }
}
