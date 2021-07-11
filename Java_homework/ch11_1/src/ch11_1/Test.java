package ch11_1;

public class Test {
    public static void main(String[] args) {
        Task ex1 = new example_1();
        Task ex2 = new example_2();
        Task ex3 = new example_3();
        TaskService s = new TaskServiceImpl();
        s.addTask(ex1);
        s.addTask(ex2);
        s.addTask(ex3);
        s.executeTasks();
    }

}
