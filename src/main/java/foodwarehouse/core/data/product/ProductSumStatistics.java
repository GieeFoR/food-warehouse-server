package foodwarehouse.core.data.product;

public class ProductSumStatistics implements Comparable<ProductSumStatistics> {
    private Product product;
    private int regularQuantity = 0;
    private int discountQuantity = 0;
    private Integer allQuantity;

    public ProductSumStatistics(Product product, int regularQuantity, int discountQuantity) {
        this.product = product;
        this.regularQuantity += regularQuantity;
        this.discountQuantity += discountQuantity;
        this.allQuantity = this.regularQuantity + this.discountQuantity;
    }

    public void addQuantity(int regularQuantity, int discountQuantity) {
        this.regularQuantity += regularQuantity;
        this.discountQuantity += discountQuantity;
        this.allQuantity = this.regularQuantity + this.discountQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getRegularQuantity() {
        return regularQuantity;
    }

    public int getDiscountQuantity() {
        return discountQuantity;
    }

    public Integer getAllQuantity() {
        return allQuantity;
    }

    @Override
    public int compareTo(ProductSumStatistics o) {
        return this.getAllQuantity().compareTo(o.getAllQuantity());
    }
}
