package homework.ch11_13.p4;

public class Test {
    public static void main(String[] args){
        Component computer=ComponentFactory.creat();
       // System.out.println(computer.toString());
        System.out.println("id: " + computer.getId() + ", name: " + computer.getName() + ", price:" + computer.getPrice());
        computer.remove(new AtomicComponent(1,"Keyboard",20));
        computer.remove(new AtomicComponent(2,"Mouse",20.0));
        computer.remove(new AtomicComponent(3,"Monitor",20));
        computer.remove(new CompositeComponent(4,"Main frame",4000));
        Iterator it = computer.iterator(); // 首先得到迭代器
        while (it.hasNext()){
            Component c = it.next();
            //注意这里不能打印c.toString(), toString()方法会递归调用子组件的toString()
            System.out.println("id: " + c.getId() + ", name: " + c.getName() + ", price:" + c.getPrice());
        }
    }
}
