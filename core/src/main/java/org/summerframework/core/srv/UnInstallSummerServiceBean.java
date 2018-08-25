package org.summerframework.core.srv;

import org.summerframework.model.*;

import static java.util.Objects.nonNull;

public class UnInstallSummerServiceBean implements SummerServiceBean<Summer> {
    public static final UnInstallSummerServiceBean Instance = new UnInstallSummerServiceBean();

    @Override
    public void sum(Summer summer) {
        if(summer instanceof LocalSummer){
            if(summer instanceof OptionalSummer) return;
            throw new RuntimeException("service " + summer.getClass()
                .getName() + " not found");
        }
        if(nonNull(ServiceInstall.remoteSummerServiceBean)){
            RemoteServiceSummer model = RemoteServiceSummer.New(summer);
            ServiceInstall.remoteSummerServiceBean.sum(model);
        }else{
            if(summer instanceof OptionalSummer) return;
            throw new RuntimeException("service " + summer.getClass()
                .getName() + " not found");
        }
    }
}
