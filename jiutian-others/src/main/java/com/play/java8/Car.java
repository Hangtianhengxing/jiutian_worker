package com.play.java8;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright @ 2018 lihao.com
 * All right reserved.
 *
 * @author Li Hao
 * @since 2018/9/29  22:48
 */
class Car {
    @FunctionalInterface
    public interface Supplier<T> {
        T get();
    }

    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }


    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }


    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }


    public void repair() {
        System.out.println("Repaired " + this.toString());
    }


    public static void main(String[] args) {
        Supplier<Integer> supplier= () -> 100;
        System.out.println(supplier.get());
//构造器引用：它的语法是Class::new，或者更一般的Class< T >::new实例如下：
        Car car = Car.create(Car::new);
        System.out.println(car);
        Car car1 = Car.create(Car::new);
        Car car2 = Car.create(Car::new);
        Car car3 = new Car();
        List<Car> cars = Arrays.asList(car, car1, car2, car3);
        System.out.println("===================构造器引用========================");
        //静态方法引用：它的语法是Class::static_method，实例如下：
        cars.forEach(Car::collide);
        System.out.println("===================静态方法引用========================");
        //特定类的任意对象的方法引用：它的语法是Class::method实例如下：
        cars.forEach(Car::repair);
        System.out.println("==============特定类的任意对象的方法引用================");
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);
        System.out.println("===================特定对象的方法引用===================");

    }
}

