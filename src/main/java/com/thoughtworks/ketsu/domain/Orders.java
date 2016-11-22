package com.thoughtworks.ketsu.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by zyongliu on 23/11/16.
 */
public interface Orders {
    Optional<Order> createOrder(User user, HashMap<String, Object> orderInfo);

    Optional<Order> findByUidOid(String uid, long oid);

    List<Order> findAllByUid(String username);

}
