import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {
    private ArrayList<Product> SelectedProducts;
    private HashMap<String,Integer> prodIdAndQuantity;
    private String total;
    private String firstUserDiscount;
    private String threeItemDiscount;
    private String finalPrice;

    public ShoppingCart() {
        SelectedProducts = new ArrayList<>();
        prodIdAndQuantity = new HashMap<>();
    }

    public void addProd(Product product){
        SelectedProducts.add(product);
        prodIdAndQuantity.put(product.getProdId(),1);


    }

    public void removeProd(String productId){
        Object removingProduct = null;
        for(Product product : SelectedProducts){
            if(productId.equals(product.getProdId())){
                removingProduct = product;
            }
        }
        SelectedProducts.remove(removingProduct);
        String removingKey = null;
        for(HashMap.Entry<String, Integer> entry : prodIdAndQuantity.entrySet()){
            if(productId.equals(entry.getKey())){
                removingKey = entry.getKey();
            }
        }
        prodIdAndQuantity.remove(removingKey);
    }

    public void calculateTotalCost(boolean firstPurchase, boolean threeItems){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        double price = 0;
        double discountedPrice = 0;
        for(Product product : SelectedProducts){
            price = price + (product.getPrice()* prodIdAndQuantity.get(product.getProdId()));
        }
        total = decimalFormat.format(price);
        double firstUserDis = (price*10)/100;
        firstUserDiscount = decimalFormat.format(firstUserDis);
        double threeItemDis = (price*20)/100;
        threeItemDiscount = decimalFormat.format(threeItemDis);

        if(firstPurchase && threeItems){
            discountedPrice = price - ((price*30)/100);
        }else if(firstPurchase){
            discountedPrice = price -((price*10)/100);
        }else if(threeItems){
            discountedPrice = price -((price*20)/100);
        }else{
            discountedPrice = price;
        }
        finalPrice = decimalFormat.format(discountedPrice);
    }

    public ArrayList<Product> getSelectedProducts() {
        return SelectedProducts;
    }

    public HashMap<String, Integer> getProdIdAndQuantity() {
        return prodIdAndQuantity;
    }

    public void addItemsToPIQhashmap(String key,int value){
        prodIdAndQuantity.put(key,value);
    }

    public String getTotal() {
        return total;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public String getFirstUserDiscount() {
        return firstUserDiscount;
    }

    public String getThreeItemDiscount() {
        return threeItemDiscount;
    }
}
