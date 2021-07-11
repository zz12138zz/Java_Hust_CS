package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.*;

public class IndexSearcher extends AbstractIndexSearcher {
    /**
     * 从指定索引文件打开索引，加载到index对象里. 一定要先打开索引，才能执行search方法
     *
     * @param indexFile ：指定索引文件
     */
    @Override
    public void open(String indexFile) {
        this.index.load(new File(indexFile));
        index.optimize();
    }

    /**
     * 根据单个检索词进行搜索
     *
     * @param queryTerm ：检索词
     * @param sorter    ：排序器
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList postingList = index.search(queryTerm);
        if (postingList == null) return new AbstractHit[0];
        List<AbstractHit> list = new ArrayList<>();
        for (int i=0;i<postingList.size();i++) {
            int docId = postingList.get(i).getDocId();
            String docPath = index.getDocName(docId);
            Map<AbstractTerm,AbstractPosting>termPostingMapping=new TreeMap<>();
            termPostingMapping.put(queryTerm,postingList.get(i));
            AbstractHit temp = new Hit(docId, docPath,termPostingMapping);
            temp.setScore(postingList.get(i).getFreq());
            temp.setScore(sorter.score(temp));
            list.add(temp);
        }
        sorter.sort(list);
        return list.toArray(new AbstractHit[0]);
    }

    /**
     * 根据二个检索词进行搜索
     *
     * @param queryTerm1 ：第1个检索词
     * @param queryTerm2 ：第2个检索词
     * @param sorter     ：    排序器
     * @param combine    ：   多个检索词的逻辑组合方式
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList postingList_1=index.search(queryTerm1);
        AbstractPostingList postingList_2=index.search(queryTerm2);
        List<AbstractHit> list = new ArrayList<>();
        if(combine==LogicalCombination.AND){
            for(int i=0;i<postingList_1.size();i++){
                for(int j=0;j<postingList_2.size();j++){
                    if(postingList_1.get(i).getDocId()==postingList_2.get(j).getDocId()){
                        int docId=postingList_1.get(i).getDocId();
                        String docPath=index.getDocName(docId);
                        Map<AbstractTerm,AbstractPosting>termPostingMapping=new TreeMap<>();
                        termPostingMapping.put(queryTerm1,postingList_1.get(i));
                        termPostingMapping.put(queryTerm2,postingList_2.get(j));
                        AbstractHit hit=new Hit(docId,docPath,termPostingMapping);
                        hit.setScore(postingList_1.get(i).getFreq()+postingList_2.get(j).getFreq());
                        hit.setScore(sorter.score(hit));
                        list.add(hit);
                    }
                }
            }
            sorter.sort(list);
            return list.toArray(new AbstractHit[0]);
        }
        if(combine==LogicalCombination.OR){
            for(int i=0;i<postingList_1.size();i++){
                for(int j=0;j<postingList_2.size();j++){
                    if(postingList_1.get(i).getDocId()==postingList_2.get(j).getDocId()){
                        int docId=postingList_1.get(i).getDocId();
                        String docPath=index.getDocName(docId);
                        Map<AbstractTerm,AbstractPosting>termPostingMapping=new TreeMap<>();
                        termPostingMapping.put(queryTerm1,postingList_1.get(i));
                        termPostingMapping.put(queryTerm2,postingList_2.get(j));
                        AbstractHit hit=new Hit(docId,docPath,termPostingMapping);
                        hit.setScore(postingList_1.get(i).getFreq()+postingList_2.get(j).getFreq());
                        hit.setScore(sorter.score(hit));
                        list.add(hit);
                    }
                }
            }
            boolean flag=true;
            for(int i=0;i<postingList_1.size();i++){
                for (AbstractHit abstractHit : list) {
                    if (postingList_1.get(i).getDocId() == abstractHit.getDocId()) {
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    flag=false;
                    int docId=postingList_1.get(i).getDocId();
                    String docPath=index.getDocName(docId);
                    Map<AbstractTerm,AbstractPosting>termPostingMapping=new TreeMap<>();
                    termPostingMapping.put(queryTerm1,postingList_1.get(i));
                    AbstractHit hit=new Hit(docId,docPath,termPostingMapping);
                    hit.setScore(postingList_1.get(i).getFreq());
                    hit.setScore(sorter.score(hit));
                    list.add(hit);
                }
            }
            for(int i=0;i<postingList_2.size();i++){
                for (AbstractHit abstractHit : list) {
                    if (postingList_2.get(i).getDocId() == abstractHit.getDocId()) {
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    flag=false;
                    int docId=postingList_2.get(i).getDocId();
                    String docPath=index.getDocName(docId);
                    Map<AbstractTerm,AbstractPosting>termPostingMapping=new TreeMap<>();
                    termPostingMapping.put(queryTerm1,postingList_2.get(i));
                    AbstractHit hit=new Hit(docId,docPath,termPostingMapping);
                    hit.setScore(postingList_2.get(i).getFreq());
                    hit.setScore(sorter.score(hit));
                    list.add(hit);
                }
            }
            sorter.sort(list);
            return list.toArray(new AbstractHit[0]);
        }
        return new AbstractHit[0];
    }

    /**
     * 将二个检索词作为短语进行搜索
     * @param queryTerm1：短语的第一个单词
     * @param queryTerm2：短语的第二个单词
     * @param sorter：排序器
     * @return 命中结果的数组
     */
//    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter){
        AbstractHit[] resultsOfAnd=this.search(queryTerm1, queryTerm2, sorter,LogicalCombination.AND);
        ArrayList<AbstractHit> arrayOfResults=new ArrayList<>();
        for(AbstractHit e:resultsOfAnd)
        {
            List<Integer> positionsOfQueryTerm1=e.getTermPostingMapping().get(queryTerm1).getPositions();
            List<Integer> positionsOfQueryTerm2=e.getTermPostingMapping().get(queryTerm2).getPositions();
            for(Integer pos1:positionsOfQueryTerm1){
                for(Integer pos2:positionsOfQueryTerm2){
                    if(pos2-1==pos1){
                        if(!arrayOfResults.contains(e))
                            arrayOfResults.add(e);
                    }
                }
            }
        }
        return arrayOfResults.toArray(new AbstractHit[0]);
    }
}