package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TermTupleScanner extends AbstractTermTupleScanner {
    private int position=0;
    private List<String> stringList;
    private StringSplitter stringSplitter = new StringSplitter();
    /**
     * 缺省的构造函数
     */
    public TermTupleScanner(){
        stringList=new ArrayList<>();
        stringSplitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }
    /**
     * 构造函数
     *
     * @param input ：指定输入流对象，应该关联到一个文本文件
     */
    public TermTupleScanner(BufferedReader input) {
        super(input);
        stringList = new ArrayList<>();
        stringSplitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }
    /**
     * 获得下一个三元组
     *
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        try {
            if(stringList.isEmpty()){
                String line;
                while((line=input.readLine())!=null){
                    stringList.addAll(stringSplitter.splitByRegex(line.toLowerCase(Locale.ROOT)));
                }
            }
            if(stringList.size()==position)
                return null;
            AbstractTerm term =new Term(stringList.get(position).trim());
            return new TermTuple(term,position++);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
