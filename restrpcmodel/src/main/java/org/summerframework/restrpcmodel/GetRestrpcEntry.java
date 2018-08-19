package org.summerframework.restrpcmodel;

import org.summerframework.model.Summer;

public class GetRestrpcEntry extends Summer<String> {
    private Summer<?> summer;

    public static String sum(Summer<?> summer){
        GetRestrpcEntry model = new GetRestrpcEntry();
        model.setSummer(summer);
        return model.baseSum();
    }

    public Summer<?> getSummer() {
        return summer;
    }

    public void setSummer(Summer<?> summer) {
        this.summer = summer;
    }
}
