package com.zuoxiaolong.search;

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

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.QuestionDao;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.DaoFactory;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月31日 下午11:20:02
 */
public abstract class LuceneHelper {

    private static final Logger logger = Logger.getLogger(LuceneHelper.class);

    private static final String INDEX_PATH = Configuration.getContextPath("index");

    private static final String SEPARATOR = "##########!!!!!!!!!!&&&&&&&&&&";

    public static void generateIndex() {
        generateArticleIndex();
        generateQuestionIndex();
    }

    private static void generateQuestionIndex() {
        generateIndex("/question",  "id", "title", "content", DaoFactory.getDao(QuestionDao.class).getAll(ViewMode.DYNAMIC));
    }

    public static List<Map<String, String>> searchQuestion(String searchText) {
        return search(searchText, "/question", "title", id -> DaoFactory.getDao(QuestionDao.class).getQuestion(id, ViewMode.DYNAMIC));
    }

    private static void generateArticleIndex() {
        generateIndex("/article", "id","subject","content",DaoFactory.getDao(ArticleDao.class).getArticles("create_date", Status.published, ViewMode.DYNAMIC));
    }

    public static List<Map<String, String>> searchArticle(String searchText) {
        return search(searchText, "/article", "subject", id -> DaoFactory.getDao(ArticleDao.class).getArticle(id, ViewMode.DYNAMIC));
    }

    private static void generateIndex(String path, String id, String title, String content, List<Map<String, String>> dataList) {
        try {
            Directory dir = FSDirectory.open(Paths.get(INDEX_PATH + path));
            Analyzer analyzer = new SmartChineseAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriterConfig.setOpenMode(OpenMode.CREATE);
            IndexWriter writer = new IndexWriter(dir, indexWriterConfig);
            for (Map<String, String> data : dataList) {
                Document document = new Document();
                Field idField = new IntField("id", Integer.valueOf(data.get(id)), Field.Store.YES);
                Field indexedContentField = new TextField("indexedContent", data.get(title) + SEPARATOR + data.get(content), Field.Store.YES);
                document.add(idField);
                document.add(indexedContentField);
                writer.addDocument(document);
                if (logger.isInfoEnabled()) {
                    logger.info("add index for : [" + data.get(title) + "]");
                }
            }
            writer.close();
        } catch (Exception e) {
            logger.error("add index failed ..." , e);
        }
    }

    private static List<Map<String, String>> search(String searchText, String path, String title, LoadQuery loadQuery) {
        try {
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_PATH + path)));
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new SmartChineseAnalyzer();
            QueryParser parser = new QueryParser("indexedContent", analyzer);
            Query query = parser.parse(searchText);
            TopDocs resultDocs = searcher.search(query, 100);
            ScoreDoc[] scoreDocs = resultDocs.scoreDocs;
            //高亮设置
            SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<span style=\"color:red;\">", "</span>");
            Highlighter highlighter = new Highlighter(simpleHtmlFormatter, new QueryScorer(query));
            highlighter.setTextFragmenter(new SimpleFragmenter(150));
            List<Map<String, String>> result = new ArrayList<>();
            List<Integer> idList = new ArrayList<>();
            for (int i = 0; i < scoreDocs.length; i++) {
                Document doc = searcher.doc(scoreDocs[i].doc);
                Integer id = Integer.valueOf(doc.get("id"));
                if (!idList.contains(id)) {
                    String indexedContent = doc.get("indexedContent");
                    TokenStream tokenStream = analyzer.tokenStream("indexedContent", indexedContent);
                    Map<String, String> data = loadQuery.getById(id);
                    String highlighterString = highlighter.getBestFragment(tokenStream, indexedContent);
                    if (highlighterString.contains(SEPARATOR)) {
                        String[] array = highlighterString.split(SEPARATOR);
                        data.put(title,array[0]);
                        if (array.length > 1) {
                            data.put("summary" , array[1]);
                        }
                    } else {
                        data.put("summary" , highlighterString);
                    }
                    result.add(data);
                    idList.add(id);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("search failed ..." , e);
        }
        return new ArrayList<>();
    }

    interface LoadQuery {

        Map<String, String> getById(Integer id);

    }

}
