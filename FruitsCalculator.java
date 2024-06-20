package com.roger.demo;

import java.util.ArrayList;
import java.util.List;

abstract class Fruit {
    private double price;
    private double discount = 1.0;
    private int weight;

    public Fruit(double price, int weight) {
        this.price = price;
        this.weight = weight;
    }

    public double getAmount() {
        return price * weight;
    }
    
    public void setDiscount(double discount) {
    	this.discount = discount;
    }
    
    public double getDiscountAmount() {
        return price * weight * discount;
    }
}

//苹果类
class Apple extends Fruit {
 public Apple(int weight) {
     super(8.0, weight);
 }

}

//草莓类
class Strawberry extends Fruit {
 public Strawberry(int weight) {
     super(13.0, weight);
 }
}

//芒果类
class Mango extends Fruit {
 public Mango(int weight) {
     super(20.0, weight);
 }
}

// 
interface FruitPromotionStrategy {
 double applyPromotion(Fruit fruit);
}

//无折扣策略
class NoPromotionStrategy implements FruitPromotionStrategy {
 @Override
 public double applyPromotion(Fruit fruit) {
     return fruit.getAmount();
 }
}

//草莓折扣策略：限时打8折
class StrawberryDiscountStrategy implements FruitPromotionStrategy {
 private static final double STRAWBERRY_DISCOUNT = 0.8;

 @Override
 public double applyPromotion(Fruit fruit) {
	 if (fruit instanceof Strawberry) {
		 fruit.setDiscount(STRAWBERRY_DISCOUNT);
		 return fruit.getDiscountAmount();
	 }
     return fruit.getAmount();
 }
}

// 满减
interface DiscountStrategy {
    double applyDiscount(double totalPrice);
}

//无满减策略
class NoReductionStrategy implements DiscountStrategy {
@Override
public double applyDiscount(double totalPrice) {
	return totalPrice;
}
}

//满减策略：满100减10块
class FullReductionStrategy implements DiscountStrategy {
 private static final double THRESHOLD = 100.0;
 private static final double REDUCTION_AMOUNT = 10.0;

 @Override
 public double applyDiscount(double totalPrice) {
     if (totalPrice >= THRESHOLD) {
         return totalPrice - REDUCTION_AMOUNT;
     }
     return totalPrice;
 }
}


class PriceCalculator {
    private DiscountStrategy discountStrategy;
    private FruitPromotionStrategy fruitPromotionStrategy;

    public PriceCalculator(DiscountStrategy discountStrategy, FruitPromotionStrategy fruitPromotionStrategy) {
        this.discountStrategy = discountStrategy;
        this.fruitPromotionStrategy = fruitPromotionStrategy;
    }

    public double calculatePrice(List<Fruit> fruits) {
        double totalPrice = 0.0;
        for (Fruit fruit : fruits) {
            totalPrice += fruitPromotionStrategy.applyPromotion(fruit);
        }

        totalPrice = discountStrategy.applyDiscount(totalPrice);

        return totalPrice;
    }
}

public class FruitsCalculator {
 public static void main(String[] args) {
	 NoReductionStrategy noReductionStrategy = new NoReductionStrategy();
     NoPromotionStrategy noPromotionStrategy = new NoPromotionStrategy();
     StrawberryDiscountStrategy strawberryDiscountStrategy = new StrawberryDiscountStrategy();
     FullReductionStrategy fullReductionStrategy = new FullReductionStrategy();
	 
	 
     List<Fruit> afruits = new ArrayList<>();
     afruits.add(new Apple(10));
     afruits.add(new Strawberry(10));
     PriceCalculator aCalculator = new PriceCalculator(noReductionStrategy, noPromotionStrategy);
     double aTotalPrice = aCalculator.calculatePrice(afruits);
     System.out.println("A需要支付的总价为：" + aTotalPrice + "元");
     
     List<Fruit> bfruits = new ArrayList<>();
     bfruits.add(new Apple(10));
     bfruits.add(new Strawberry(10));
     bfruits.add(new Mango(10));
     PriceCalculator bCalculator = new PriceCalculator(noReductionStrategy, noPromotionStrategy);
     double bTotalPrice = bCalculator.calculatePrice(bfruits);
     System.out.println("B需要支付的总价为：" + bTotalPrice + "元");
     
     List<Fruit> cfruits = new ArrayList<>();
     cfruits.add(new Apple(10));
     cfruits.add(new Strawberry(10));
     cfruits.add(new Mango(10));
     PriceCalculator cCalculator = new PriceCalculator(noReductionStrategy, strawberryDiscountStrategy);
     double cTotalPrice = cCalculator.calculatePrice(cfruits);
     System.out.println("C需要支付的总价为：" + cTotalPrice + "元");
     
     List<Fruit> dfruits = new ArrayList<>();
     dfruits.add(new Apple(10));
     dfruits.add(new Strawberry(10));
     dfruits.add(new Mango(10));
     PriceCalculator dCalculator = new PriceCalculator(fullReductionStrategy, strawberryDiscountStrategy);
     double dTotalPrice = dCalculator.calculatePrice(dfruits);
     System.out.println("D需要支付的总价为：" + dTotalPrice + "元");
 }
}




































