package Assignment.Config;

public class AppConfig {
    
    // Tax configuration
    private static double taxRate = 0.06; // Default 6%
    
    // Discount configuration
    private static double memberDiscount = 0.05; // Default 5% for members
    
    // Getters
    public static double getTaxRate() {
        return taxRate;
    }
    
    public static double getMemberDiscount() {
        return memberDiscount;
    }
    
    // Setters
    public static void setTaxRate(double rate) {
        if (rate >= 0 && rate <= 1) {
            taxRate = rate;
        } else {
            throw new IllegalArgumentException("Tax rate must be between 0 and 1");
        }
    }
    
    public static void setMemberDiscount(double discount) {
        if (discount >= 0 && discount <= 1) {
            memberDiscount = discount;
        } else {
            throw new IllegalArgumentException("Discount must be between 0 and 1");
        }
    }
    
    // Display formatted percentages
    public static String getTaxRateFormatted() {
        return String.format("%.0f%%", taxRate * 100);
    }
    
    public static String getMemberDiscountFormatted() {
        return String.format("%.0f%%", memberDiscount * 100);
    }
}