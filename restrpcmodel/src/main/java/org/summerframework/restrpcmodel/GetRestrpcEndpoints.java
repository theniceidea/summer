package org.summerframework.restrpcmodel;

import org.summerframework.model.Summer;

import java.util.Set;

public class GetRestrpcEndpoints extends Summer<Set<String>> {
    private String key;

    public static Set<String> sum(String key){
        GetRestrpcEndpoints summer = new GetRestrpcEndpoints();
        summer.setKey(key);
        return summer.baseSum();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
