//: concurrency/restaurant2/RestaurantWithQueues.java
// {Args: 5}
package com.quyc.learn.javabasic.thinkinginjava.cuncurrency;

import com.quyc.learn.javabasic.thinkinginjava.enumlearn.menu.Course;
import com.quyc.learn.javabasic.thinkinginjava.enumlearn.menu.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static com.quyc.learn.javabasic.thinkinginjava.net.mindview.util.Print.print;

// This is given to the waiter, who gives it to the chef:
class OrderQ { // (A data-transfer object)
    private static int counter = 0;
    private final int id = counter++;
    private final CustomerQ customer;
    private final WaitPersonQ waitPerson;
    private final Food food;

    public OrderQ(CustomerQ cust, WaitPersonQ wp, Food f) {
        customer = cust;
        waitPerson = wp;
        food = f;
    }

    public Food item() {
        return food;
    }

    public CustomerQ getCustomer() {
        return customer;
    }

    public WaitPersonQ getWaitPerson() {
        return waitPerson;
    }

    @Override
    public String toString() {
        return "Order: " + id + " item: " + food +
                " for: " + customer +
                " served by: " + waitPerson;
    }
}

// This is what comes back from the chef:
class Plate {
    private final OrderQ order;
    private final Food food;

    public Plate(OrderQ ord, Food f) {
        order = ord;
        food = f;
    }

    public OrderQ getOrder() {
        return order;
    }

    public Food getFood() {
        return food;
    }

    @Override
    public String toString() {
        return food.toString();
    }
}

class CustomerQ implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final WaitPersonQ waitPerson;
    // Only one course at a time can be received:
    private SynchronousQueue<Plate> placeSetting =
            new SynchronousQueue<Plate>();

    public CustomerQ(WaitPersonQ w) {
        waitPerson = w;
    }

    public void deliver(Plate p) throws InterruptedException {
        // Only blocks if customer is still
        // eating the previous course:
        placeSetting.put(p);
    }

    @Override
    public void run() {
        for (Course course : Course.values()) {
            Food food = course.randomSelection();
            try {
                waitPerson.placeOrder(this, food);
                // Blocks until course has been delivered:
                print(this + "eating " + placeSetting.take());
            } catch (InterruptedException e) {
                print(this + "waiting for " +
                        course + " interrupted");
                break;
            }
        }
        print(this + "finished meal, leaving");
    }

    @Override
    public String toString() {
        return "CustomerQ " + id + " ";
    }
}

class WaitPersonQ implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final RestaurantQ restaurant;
    BlockingQueue<Plate> filledOrders =
            new LinkedBlockingQueue<Plate>();

    public WaitPersonQ(RestaurantQ rest) {
        restaurant = rest;
    }

    public void placeOrder(CustomerQ cust, Food food) {
        try {
            // Shouldn't actually block because this is
            // a LinkedBlockingQueue with no size limit:
            restaurant.orders.put(new OrderQ(cust, this, food));
        } catch (InterruptedException e) {
            print(this + " placeOrder interrupted");
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Blocks until a course is ready
                Plate plate = filledOrders.take();
                print(this + "received " + plate +
                        " delivering to " +
                        plate.getOrder().getCustomer());
                plate.getOrder().getCustomer().deliver(plate);
            }
        } catch (InterruptedException e) {
            print(this + " interrupted");
        }
        print(this + " off duty");
    }

    @Override
    public String toString() {
        return "WaitPersonQ " + id + " ";
    }
}

class ChefQ implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final RestaurantQ restaurant;
    private static Random rand = new Random(47);

    public ChefQ(RestaurantQ rest) {
        restaurant = rest;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Blocks until an order appears:
                OrderQ order = restaurant.orders.take();
                Food requestedItem = order.item();
                // Time to prepare order:
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                Plate plate = new Plate(order, requestedItem);
                order.getWaitPerson().filledOrders.put(plate);
            }
        } catch (InterruptedException e) {
            print(this + " interrupted");
        }
        print(this + " off duty");
    }

    @Override
    public String toString() {
        return "ChefQ " + id + " ";
    }
}

class RestaurantQ implements Runnable {
    private List<WaitPersonQ> waitPersons =
            new ArrayList<WaitPersonQ>();
    private List<ChefQ> chefs = new ArrayList<ChefQ>();
    private ExecutorService exec;
    private static Random rand = new Random(47);
    BlockingQueue<OrderQ>
            orders = new LinkedBlockingQueue<OrderQ>();

    public RestaurantQ(ExecutorService e, int nWaitPersons,
                       int nChefs) {
        exec = e;
        for (int i = 0; i < nWaitPersons; i++) {
            WaitPersonQ waitPerson = new WaitPersonQ(this);
            waitPersons.add(waitPerson);
            exec.execute(waitPerson);
        }
        for (int i = 0; i < nChefs; i++) {
            ChefQ chef = new ChefQ(this);
            chefs.add(chef);
            exec.execute(chef);
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // A new customer arrives; assign a WaitPerson:
                WaitPersonQ wp = waitPersons.get(
                        rand.nextInt(waitPersons.size()));
                CustomerQ c = new CustomerQ(wp);
                exec.execute(c);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            print("RestaurantQ interrupted");
        }
        print("RestaurantQ closing");
    }
}

public class RestaurantWithQueues {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        RestaurantQ restaurant = new RestaurantQ(exec, 5, 2);
        exec.execute(restaurant);
        if (args.length > 0) // Optional argument
        {
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        } else {
            print("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
} /* Output: (Sample)
WaitPersonQ 0 received SPRING_ROLLS delivering to CustomerQ 1
CustomerQ 1 eating SPRING_ROLLS
WaitPersonQ 3 received SPRING_ROLLS delivering to CustomerQ 0
CustomerQ 0 eating SPRING_ROLLS
WaitPersonQ 0 received BURRITO delivering to CustomerQ 1
CustomerQ 1 eating BURRITO
WaitPersonQ 3 received SPRING_ROLLS delivering to CustomerQ 2
CustomerQ 2 eating SPRING_ROLLS
WaitPersonQ 1 received SOUP delivering to CustomerQ 3
CustomerQ 3 eating SOUP
WaitPersonQ 3 received VINDALOO delivering to CustomerQ 0
CustomerQ 0 eating VINDALOO
WaitPersonQ 0 received FRUIT delivering to CustomerQ 1
...
*///:~
