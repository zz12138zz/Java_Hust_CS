package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.Objects;

public class TermTuple extends AbstractTermTuple {
    /**
     * 无参构造函数
     */
    public TermTuple(){

    }
    /**
     * 构造函数
     * @param term:单词
     * @param curPos:位置
     */
    public TermTuple(AbstractTerm term,int curPos){
        this.term=term;
        this.curPos=curPos;
    }
    /**
     * 判断二个三元组内容是否相同
     *
     * @param obj ：要比较的另外一个三元组
     * @return 如果内容相等（三个属性内容都相等）返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TermTuple) {
            return Objects.equals(this.term, ((TermTuple) obj).term)&&this.curPos==((TermTuple) obj).curPos;
        }
        return false;
    }

    /**
     * 获得三元组的字符串表示
     *
     * @return ： 三元组的字符串表示
     */
    @Override
    public String toString() {
        return "<"+this.term.toString()+" freq:"+this.freq+" position"+this.curPos+">";
    }
}
