package org.summerframework.rpcmodel;

import org.summerframework.model.SummerSum;

public class ParseJson extends SummerSum<Object> {
    private Class<?> cls;
    private String text;

    public static Object sum(Class<?> cls, String text){
        ParseJson json = new ParseJson();
        json.setCls(cls);
        json.setText(text);
        return json.baseSum();
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
