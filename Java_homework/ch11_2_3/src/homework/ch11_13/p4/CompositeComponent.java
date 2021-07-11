package homework.ch11_13.p4;

public class CompositeComponent extends Component{
    protected ComponentList childs=new ComponentList();
    public CompositeComponent(){}
    public CompositeComponent(int id,String name,double price){
        super(id, name, price);
    }
    @Override
    public void add(Component component) throws UnsupportedOperationException {
        childs.add(component);
    }

    @Override
    public void remove(Component component) throws UnsupportedOperationException {
        childs.remove(component);
    }

    @Override
    public double calcPrice() {
        this.price = 0;
        for(Component e:this.childs){
            price+=e.getPrice();
        }
        return this.price;
    }

    @Override
    public Iterator iterator() {
        return new CompositeIterator(childs);
    }
    @Override
    public String toString(){
        StringBuilder s=new StringBuilder(super.toString()+"\nsub-components of "+this.name);
        for(Component e:childs){
            s.append("\n").append(e.toString());
        }
        return s.toString();

    }
}
