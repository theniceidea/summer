package org.summerframework.demovertx2.model;

import org.summerframework.model.AsyncSummer;
import org.summerframework.model.Summer;

public class RedisGet extends AsyncSummer<String> {
    private String key;

    public RedisGet c(String key){
        this.setKey(key);
        this.sum();
        return this;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
