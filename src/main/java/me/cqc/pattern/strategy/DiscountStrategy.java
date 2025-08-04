package me.cqc.pattern.strategy;


/**
 * 折扣策略
 */
public interface DiscountStrategy {
    /**
     * 根据原价计算折扣价
     * @param originalPrice 原价
     * @return 折扣价
     */
    Double getDiscount(double originalPrice);
}
