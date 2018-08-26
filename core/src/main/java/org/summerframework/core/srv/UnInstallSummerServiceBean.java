package org.summerframework.core.srv;

import org.summerframework.model.LocalSummer;
import org.summerframework.model.RemoteServiceSummer;
import org.summerframework.model.Summer;
import org.summerframework.model.SummerServiceBean;

public class UnInstallSummerServiceBean implements SummerServiceBean<Summer> {
    public static final UnInstallSummerServiceBean Instance = new UnInstallSummerServiceBean();

    @Override
    public void sum(Summer summer) {
        if(summer instanceof LocalSummer){
            throw new RuntimeException("service " + summer.getClass()
                .getName() + " not found");
        }
        if(ServiceInstall.installed(RemoteServiceSummer.class)){
            RemoteServiceSummer.Instance(summer).sum();
        }else{
            throw new RuntimeException("service " + summer.getClass()
                .getName() + " not found");
        }
    }
}
