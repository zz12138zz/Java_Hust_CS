package p1;

import java.lang.reflect.Array;

public class Container <T> {
    private T[] elements;
    private int elementsCount = 0;
    private int size = 0;

    public Container(Class<? extends T>clz,int size){
        elements = (T[]) Array.newInstance(clz,size);
        this.size = size;
    }

    public boolean add(T e){
        if(elementsCount < size){
            elements[elementsCount ++] = e;
            return true;
        }
        else{
            return  false;
        }
    }

    /**
     * 返回容器的迭代器
     * @return :迭代器
     */
    public Iterator<T> iterator(){
        return new ArrayIterator<T>(elements);
    }

}
