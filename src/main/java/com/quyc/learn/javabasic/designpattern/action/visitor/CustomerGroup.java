package com.quyc.learn.javabasic.designpattern.action.visitor;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by quyuanchao on 2019/2/16 23:18.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
class CustomerGroup {

    private List<Customer> customers = new ArrayList<>();

    void accept(Visitor visitor) {
        for (Customer customer : customers) {
            customer.accept(visitor);
        }
    }

    void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
