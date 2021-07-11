package homework.ch11_13.p4;

import java.util.ArrayList;
import java.util.List;

public class CompositeIterator implements Iterator{
    protected List<Iterator> iterators=new ArrayList<>();
    public CompositeIterator(Iterator iterator){
        if(iterator.hasNext()) iterators.add(iterator);
    }
    @Override
    public boolean hasNext() {
        return !iterators.isEmpty();
    }

    @Override
    public Component next() {
        if(this.hasNext()){
            Component c=iterators.get(0).next();
            if(c instanceof CompositeComponent) iterators.add(((CompositeComponent) c).childs);
            if(!(iterators.get(0).hasNext()))  iterators.remove(0);
            return c;
        }
        return null;
    }
}
