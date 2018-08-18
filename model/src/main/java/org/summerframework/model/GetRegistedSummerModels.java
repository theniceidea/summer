package org.summerframework.model;

import java.util.Set;

public class GetRegistedSummerModels extends Summer<Set<Class<?>>> implements LocalSummer {

    public static Set<Class<?>> sum(){
        return (new GetRegistedSummerModels()).baseSum();
    }

}
