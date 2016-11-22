package com.thoughtworks.ketsu.support;

import com.thoughtworks.ketsu.domain.Order;
import com.thoughtworks.ketsu.domain.Payment;
import com.thoughtworks.ketsu.domain.RefundOrder;
import com.thoughtworks.ketsu.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public abstract class TestHelper {
    public static String USERNAME_A = "A";
    public static String USERNAME_B = "B";
    public static long ORDERID_1 = 1;
    public static long ORDERID_2 = 2;
    public static long REFUND_ORDERID_1 = 1;
    public static long REFUND_ORDERID_2 = 2;


    public static User user = new User(USERNAME_A);
    public static User otherUser = new User(USERNAME_B);
    public static Order order = new Order(REFUND_ORDERID_1);
    public static Order otherOrder = new Order(ORDERID_2);
    public static List<Order> orderList = asList(order, otherOrder);
    public static Payment payment = new Payment(ORDERID_1);
    public static RefundOrder refundOrder = new RefundOrder(REFUND_ORDERID_1);
    public static RefundOrder otherRefundOrder = new RefundOrder(REFUND_ORDERID_2);
    public static List<RefundOrder> refundOrderList = asList(refundOrder, otherRefundOrder);


    public static Map<String, Object> deployment(String appName, String releaseId) {
        return new HashMap<String, Object>() {{
            put("app", String.format("http://service-api.tw.com/apps/%s", appName));
            put("release", String.format("http://service-api.tw.com/apps/%s/releases/%s", appName, releaseId));
        }};
    }

    public static Map<String, Object> stackMap(String id, String name) {
        Map<String, Object> stackMap = new HashMap<String, Object>() {{
            put("id", id);
            put("name", name);
        }};
        return stackMap;
    }

    public static Map<String, Object> userMap(String email, String name) {
        return new HashMap<String, Object>() {{
            put("name", name);
            put("email", email);
        }};
    }
}
