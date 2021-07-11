package ch11_1;

import java.util.ArrayList;

public class TaskServiceImpl implements TaskService{
    ArrayList<Task> taskList=new ArrayList<>();
    @Override
    public void executeTasks(){
        for(Task e:taskList){
            e.execute();
        }
    }
    public void addTask(Task t){
        taskList.add(t);
    }
}
