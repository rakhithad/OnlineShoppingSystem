public abstract class Product {
    private String prodId;
    private String prodName;
    private int numberOfAvailableItems;
    private double price;

    public Product(String prodId, String prodName, int numberOfAvailableItems, double price) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.numberOfAvailableItems = numberOfAvailableItems;
        this.price = price;
    }

    public String getProdId() {
        return prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public int getNumberOfAvailableItems() {
        return numberOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setNumberOfAvailableItems(int numberOfAvailableItems) {
        this.numberOfAvailableItems = numberOfAvailableItems;
    }

    public String getBrand(){
        return getBrand();
    }
    public String getWarrantyPeriod() {
        return getWarrantyPeriod();
    }
    public String getSize() {return getSize();}
    public String getColor() {
        return getColor();
    }

    @Override
    public String toString() {
        return "ProductId:-" + prodId + '\n' + "Product Name:-" + prodName + '\n' + "Available items:-" + numberOfAvailableItems +'\n'+ "Price:-" + price;
    }


}
