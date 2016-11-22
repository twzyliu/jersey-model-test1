package com.thoughtworks.ketsu.domain;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyongliu on 23/11/16.
 */
public class RefundOrder implements Record{

    private long roid;
    private String username;

    public RefundOrder(long roid) {

        this.roid = roid;
    }

    public long getRoid() {
        return roid;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<>();
    }

    public String getUsername() {
        return username;
    }
}
