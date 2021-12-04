package com.jinax.pm_backend.Result;

import com.jinax.pm_backend.Entity.Tag;

import java.math.BigInteger;
import java.util.Map;

/**
 * @author : chara
 */
public class GetTopTagResult {
    private int id;
    private String name;
    private BigInteger count;

    public GetTopTagResult() {

    }

    public GetTopTagResult(int id, String name, BigInteger count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }
}
