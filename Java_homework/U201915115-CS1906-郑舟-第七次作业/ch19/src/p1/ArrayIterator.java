package p1;

public class ArrayIterator <T> implements Iterator<T>{
    private int pos=0;
    private T[] a=null;
    public ArrayIterator(T[] array){
        a=array;
    }
    /**
     * 是否还有元素
     *
     * @return 如果元素还没有迭代完，返回true;否则返回false
     */
    @Override
    public boolean hasNext() {
        return !(pos >= a.length);
    }

    /**
     * 获取下一个元素
     *
     * @return 下一个元素
     */
    @Override
    public T next() {
        if(hasNext()){
            T c = a[pos];
            pos ++;
            return c;
        }
        else
            return null;

    }
}
