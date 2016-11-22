package com.thoughtworks.ketsu.domain;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyongliu on 23/11/16.
 */
public class Order implements Record{
    private long oid;
    private String username;

    public Order(long oid) {
        this.oid = oid;
    }

    public long getOid() {
        return oid;
    }

    public String getUsername() {
        return username;
    }


    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toRefJson(routes);
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<>();

    }
}
