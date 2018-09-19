package org.summerframework.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Attributes {
    private List<Attribute> items = new ArrayList<>(3);

    public void add(Attribute attribute){
        if(this.items.contains(attribute)) return;
        this.items.add(attribute);
    }
    public void remove(Attribute attribute){
        this.items.remove(attribute);
    }
    public List<Attribute> unmodifiableItems(){
        return Collections.unmodifiableList(this.items);
    }
}
