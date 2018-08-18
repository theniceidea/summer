package org.summerframework.restrpcmodel;

import org.summerframework.model.Summer;

import java.util.List;

public class GetRestrpcEndpoint extends Summer<String> {
    private Summer<?> summer;

    public static String sum(Summer<?> summer){
        GetRestrpcEndpoint model = new GetRestrpcEndpoint();
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
