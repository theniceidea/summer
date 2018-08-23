package org.summerframework.model;

import java.util.Set;

public class GetRegistedSummerModels extends Summer<Set<Class<?>>> implements LocalSummer {
    private static SummerServiceBean<Set<Class<?>>> service;

    public static Set<Class<?>> sum(){
        return service.sum(new GetRegistedSummerModels());
    }

}
