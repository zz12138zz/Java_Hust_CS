package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;

import java.io.*;
import java.util.*;

/**
 * AbstractIndex的具体实现类
 */
public class Index extends AbstractIndex {
    /**
     * 无参构造函数
     */
    public Index(){

    }
    /**
     * 返回索引的字符串表示
     *
     * @return 索引的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder buf=new StringBuilder();
        for (Map.Entry<AbstractTerm, AbstractPostingList> entry : termToPostingListMapping.entrySet()) {
            buf.append(entry.getKey().toString()).append("-->").append(entry.getValue().toString());
        }
        return buf.toString();
    }

    /**
     * 添加文档到索引，更新索引内部的HashMap
     *
     * @param document ：文档的AbstractDocument子类型表示
     */
    @Override
    public void addDocument(AbstractDocument document) {
        if(!this.docIdToDocPathMapping.containsKey(document.getDocId()))
            docIdToDocPathMapping.put(document.getDocId(),document.getDocPath());
        for(AbstractTermTuple tuple:document.getTuples()){
            if(this.termToPostingListMapping.containsKey(tuple.term)){
                AbstractPostingList l=this.termToPostingListMapping.get(tuple.term);
                int index=l.indexOf(document.getDocId());
                if(index!=-1){
                    l.get(index).setFreq(l.get(index).getFreq()+1);
                    l.get(index).getPositions().add(tuple.curPos);
                }else {
                    List<Integer> positions=new ArrayList<>();
                    positions.add(tuple.curPos);
                    l.add(new Posting(document.getDocId(),1,positions));
                }
            }else{
                List<Integer> positions=new ArrayList<>();
                positions.add(tuple.curPos);
                PostingList list=new PostingList();
                list.add(new Posting(document.getDocId(),1,positions));
                this.termToPostingListMapping.put(tuple.term,list);
            }
        }
    }

    /**
     * <pre>
     * 从索引文件里加载已经构建好的索引.内部调用FileSerializable接口方法readObject即可
     * @param file ：索引文件
     * </pre>
     */
    @Override
    public void load(File file) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            this.readObject(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * 将在内存里构建好的索引写入到文件. 内部调用FileSerializable接口方法writeObject即可
     * @param file ：写入的目标索引文件
     * </pre>
     */
    @Override
    public void save(File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            this.writeObject(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回指定单词的PostingList
     *
     * @param term : 指定的单词
     * @return ：指定单词的PostingList;如果索引字典没有该单词，则返回null
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {
        return this.termToPostingListMapping.get(term);
    }

    /**
     * 返回索引的字典.字典为索引里所有单词的并集
     *
     * @return ：索引中Term列表
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        return this.termToPostingListMapping.keySet();
    }

    /**
     * <pre>
     * 对索引进行优化，包括：
     *      对索引里每个单词的PostingList按docId从小到大排序
     *      同时对每个Posting里的positions从小到大排序
     * 在内存中把索引构建完后执行该方法
     * </pre>
     */
    @Override
    public void optimize() {
        for(Map.Entry<AbstractTerm,AbstractPostingList> entry:this.termToPostingListMapping.entrySet()){
            for(int i=0;i<entry.getValue().size();i++){
                entry.getValue().get(i).sort();
            }
            entry.getValue().sort();
        }
    }

    /**
     * 根据docId获得对应文档的完全路径名
     *
     * @param docId ：文档id
     * @return : 对应文档的完全路径名
     */
    @Override
    public String getDocName(int docId) {
        return this.docIdToDocPathMapping.get(docId);
    }

    /**
     * 写到二进制文件
     *
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     *
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            Index temp=(Index)in.readObject();
            this.docIdToDocPathMapping=temp.docIdToDocPathMapping;
            this.termToPostingListMapping=temp.termToPostingListMapping;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
