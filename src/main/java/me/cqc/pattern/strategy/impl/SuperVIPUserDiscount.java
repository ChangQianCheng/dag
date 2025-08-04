package me.cqc.pattern.strategy.impl;

import me.cqc.pattern.strategy.DiscountStrategy;

public class SuperVIPUserDiscount implements DiscountStrategy {
    @Override
    public Double getDiscount(double originalPrice) {
        return originalPrice * 0.8;
    }
}
