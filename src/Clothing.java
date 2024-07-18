public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(String prodId, String prodName, int numberOfAvailableItems, double price, String size, String color) {
        super(prodId, prodName, numberOfAvailableItems, price);
        this.color = color;
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return super.toString()+'\n'+ "Size:-" + size +'\n'+ "Colour:-" + color +'\n';
    }
}
