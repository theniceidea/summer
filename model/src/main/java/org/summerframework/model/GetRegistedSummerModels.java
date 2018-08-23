package org.summerframework.model;

import java.util.Set;

public class GetRegistedSummerModels extends Summer<Set<Class<?>>> implements LocalSummer {
    private static SummerServiceBean<GetRegistedSummerModels> service;

    @Override
    public Set<Class<?>> sum(){
        service.sum(this);
        return this.getResult();
    }

}
