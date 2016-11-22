package com.thoughtworks.ketsu.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by zyongliu on 23/11/16.
 */
public interface RefundOrders {
    RefundOrder create(HashMap<String, Object> refundOrderInfo);

    List<RefundOrder> findAllRefundOrder();

    Optional<RefundOrder> findByUidRoid(String username, long roid);
}
