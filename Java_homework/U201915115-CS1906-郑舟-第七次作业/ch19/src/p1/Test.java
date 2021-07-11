package p1;

public class Test {
    public static void main(String[] args) {
        Container<String> container= new Container<>(String.class,6);
        container.add("12");
        container.add("34");
        container.add("56");
        container.add("78");
        container.add("9");
        container.add("10");
        Iterator <String>it = container.iterator();
        while (it.hasNext()) {
            String s = (String) it.next();
            if (s != null)
                System.out.println(s);
        }
    }
}