package me.cqc.pattern.strategy;

import me.cqc.pattern.strategy.impl.NormalUserDiscount;
import me.cqc.pattern.strategy.impl.SuperVIPUserDiscount;
import me.cqc.pattern.strategy.impl.VIPUserDiscount;

public class Client {

    public static void main(String[] args) {
        Double originalPrice = 1000.0;
        // 普通用户购物
        ShoppingCart normalCart = new ShoppingCart(new NormalUserDiscount());
        System.out.println("普通用户支付：" + normalCart.calculatePrice(originalPrice)); // 1000.0

        // VIP用户购物
        ShoppingCart vipCart = new ShoppingCart(new VIPUserDiscount());
        System.out.println("VIP用户支付：" + vipCart.calculatePrice(originalPrice)); // 900.0

        // 动态切换策略：VIP升级为超级VIP
        vipCart.setDiscountStrategy(new SuperVIPUserDiscount());
        System.out.println("升级后超级VIP支付：" + vipCart.calculatePrice(originalPrice)); // 800.0
    }
}
