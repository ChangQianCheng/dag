package me.cqc.pattern.strategy;

/**
 * 在购物车计算最终价格，所以购物车应该包含折扣策略
 */
public class ShoppingCart {
    //持有策略对象
    private DiscountStrategy discountStrategy;

    public ShoppingCart(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    Double calculatePrice(double originalPrice) {
        return discountStrategy.getDiscount(originalPrice);
    }
}
