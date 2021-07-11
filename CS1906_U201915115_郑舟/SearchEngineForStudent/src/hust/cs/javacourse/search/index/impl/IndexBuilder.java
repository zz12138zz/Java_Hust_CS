package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IndexBuilder extends AbstractIndexBuilder {
    /**
     *
     * @param docBuilder:用于构造Index的文档构造器
     */
    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }

    /**
     * <pre>
     * 构建指定目录下的所有文本文件的倒排索引.
     *      需要遍历和解析目录下的每个文本文件, 得到对应的Document对象，再依次加入到索引，并将索引保存到文件.
     * @param rootDirectory ：指定目录
     * @return ：构建好的索引
     * </pre>
     */
    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        AbstractIndex index=new Index();
        List<String> files=FileUtil.list(rootDirectory);
        for(String s:files){
            File f=new File(s);
            AbstractDocument d=docBuilder.build(docId++,s,f);
            index.addDocument(d);
        }
        return index;
    }
}
