package Assignment.Model;

public class Product {

    private String ProductID;
    private String ProductName;
    private Double Price;
    private Integer quantity;
    private Integer warrantyYear;

    public Product(String ProductID, String ProductName, Double Price, Integer quantity, Integer warrantyYear) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
        this.quantity = quantity;
        this.warrantyYear = warrantyYear;
    }

    // Constructor without ID (for new products before saving)
    public Product(String ProductName, Double Price, Integer quantity, Integer warrantyYear) {
        this.ProductName = ProductName;
        this.Price = Price;
        this.quantity = quantity;
        this.warrantyYear = warrantyYear;
    }

    public Product() {}

    // Getters and Setters
    public String getProductID() { return ProductID; }
    public void setProductID(String ProductID) { this.ProductID = ProductID; }

    public String getProductName() { return ProductName; }
    public void setProductName(String ProductName) { this.ProductName = ProductName; }

    public Double getPrice() { return Price; }
    public void setPrice(Double Price) { this.Price = Price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getWarrantyYear() { return warrantyYear; }
    public void setWarrantyYear(Integer warrantyYear) { this.warrantyYear = warrantyYear; }

    @Override
    public String toString() {
        // Formatted string for printing only the data values
        return String.format("%-12s %-31s %-18s %-16s %-7s", ProductID, ProductName, Price, quantity, warrantyYear);
    }
}