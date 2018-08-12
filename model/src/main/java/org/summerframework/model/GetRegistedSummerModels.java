package org.summerframework.model;

import java.util.Set;

public class GetRegistedSummerModels extends SummerSum<Set<Class<?>>> implements LocalSum {

    public static Set<Class<?>> sum(){
        return (new GetRegistedSummerModels()).baseSum();
    }

}
