package homework.ch11_13.p4;

public class AtomicComponent extends Component{
    public AtomicComponent(){}
    public AtomicComponent(int id,String name,double price){
        super(id, name, price);
    }

    @Override
    public void add(Component component) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't add a component to an atomic component");
    }

    @Override
    public void remove(Component component) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't delete a component from an atomic component");
    }

    @Override
    public double calcPrice() {
        return this.price;
    }

    @Override
    public Iterator iterator() {
        return new NullIterator();
    }
}
