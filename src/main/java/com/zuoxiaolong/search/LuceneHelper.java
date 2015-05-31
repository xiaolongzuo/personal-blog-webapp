package com.zuoxiaolong.search;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.model.ViewMode;

/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author 左潇龙
 * @since 2015年5月31日 下午11:20:02
 */
public abstract class LuceneHelper {
	
	private static final Logger logger = Logger.getLogger(LuceneHelper.class);
	
	private static final String INDEX_PATH = "/Users/zuoxiaolong/project/eclipse-myself/zuoxiaolong/src/main/webapp/index";//Configuration.getContextPath("index");
	
	public static void updateIndex() throws IOException {
		List<Map<String, String>> articles = ArticleDao.getArticles("create_date", ViewMode.DYNAMIC);
		Directory dir = FSDirectory.open(Paths.get(INDEX_PATH));
		Analyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(dir, indexWriterConfig);
		for (Map<String, String> article : articles) {
			Document document = new Document();
			Field idField = new IntField("id", Integer.valueOf(article.get("id")), Field.Store.YES);
			Field subjectField = new TextField("subject", article.get("subject") , Field.Store.NO);
			Field field = new StringField("content", article.get("subject") , Field.Store.YES);
			document.add(idField);
			document.add(subjectField);
			document.add(field);
			writer.addDocument(document);
			if (logger.isInfoEnabled()) {
				logger.info("updated index for : [" + article.get("subject") + "]");
			}
		}
		writer.close();
	}
	
	public static List<Integer> search(String searchText) throws IOException, ParseException, InvalidTokenOffsetsException {
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_PATH)));
		IndexSearcher searcher = new IndexSearcher(reader);
	    Analyzer analyzer = new SmartChineseAnalyzer();
	    QueryParser parser = new QueryParser("subject", analyzer);
	    Query query = parser.parse(searchText);
	    TopDocs result = searcher.search(query, 100);
	    ScoreDoc[] scoreDocs = result.scoreDocs;
//	    for (int i = 0; i < scoreDocs.length; i++) {
//			Document document = searcher.doc(scoreDocs[i].doc);
//			int id = Integer.valueOf(document.get("id"));
//			String subject = document.get("content");
//			System.out.println(id + "  :  " + subject);
//		}
//	  //高亮设置  
        SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<B>","</B>");//设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀  
        Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(query));  
        highlighter.setTextFragmenter(new SimpleFragmenter(150));//设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据  
        for(int i=0;i<scoreDocs.length;i++){  
            Document doc = searcher.doc(scoreDocs[i].doc);  
            String cString = doc.get("content");
            System.out.println(cString);
            TokenStream tokenStream = analyzer.tokenStream("content",cString);  
            String str = highlighter.getBestFragment(tokenStream, cString);  
            System.out.println(str);  
        }  
	    return null;
	}

}
