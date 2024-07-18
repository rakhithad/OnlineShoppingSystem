import java.util.ArrayList;

public interface ShoppingManager {

    void consoleMenu();

    void addProd(String productId);

    void removeProd(String productId);

    void printProdList();

    void saveFiles();

    void loadFiles();

    ArrayList<Product> getProdList();
}
