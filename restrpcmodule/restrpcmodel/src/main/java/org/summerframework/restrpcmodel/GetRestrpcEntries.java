package org.summerframework.restrpcmodel;

import org.summerframework.model.Summer;

import java.util.Set;

public class GetRestrpcEntries extends Summer<Set<String>> {
    private String className;

    public static Set<String> sum(String key){
        GetRestrpcEntries summer = new GetRestrpcEntries();
        summer.setClassName(key);
        return summer.sum();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
