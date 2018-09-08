package org.summerframework.model;

public class Task extends Summer{
    private Summer summer;
    public static void sum(Summer summer){
        Task task = new Task();
        task.summer = summer;
        task.sum();
    }

    public Summer getSummer() {
        return summer;
    }

    public void setSummer(Summer summer) {
        this.summer = summer;
    }
}
