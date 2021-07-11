package p1;

public interface Iterator <T>{
    /**
     *  是否还有元素
     * @return 如果元素还没有迭代完，返回true;否则返回false
     */
    boolean hasNext();

    /**
     * 获取下一个元素
     * @return  下一个元素
     */
    T next();
}
