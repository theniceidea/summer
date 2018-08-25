package org.summerframework.demo.model;

import java.util.HashSet;
import java.util.Set;

public class RewriteTest {
    public static void test(){
        TestModel5 testModel5 = new TestModel5();
        HashSet<String> strings = new HashSet<>();
        strings.add("sdfasdf");
        testModel5.setResult(strings);

        Set<String> sum = testModel5.sum();
        System.out.println("");
    }
}
