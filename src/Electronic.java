public class Electronic extends Product {
    private String brand;
    private String warrantyPeriod;

    public Electronic(String prodId, String prodName, int numberOfAvailableItems, double price, String brand, String warrantyPeriod) {
        super(prodId, prodName, numberOfAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    @Override
    public String toString() {

        return super.toString()+'\n'+ "Brand:-" + brand + '\n' + "Warranty period:-" + warrantyPeriod +'\n';
    }
}
