package org.summerframework.core.srv;

import org.summerframework.model.Summer;

public class Summers {
    public static boolean sum(Summer model){
        return Manager.callService(model);
    }
}
