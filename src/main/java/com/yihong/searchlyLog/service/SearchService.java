package com.yihong.searchlyLog.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihong.searchlyLog.model.Article;
import com.yihong.searchlyLog.model.EsQuery;
import com.yihong.searchlyLog.model.EsResult;
import com.yihong.searchlyLog.model.Log;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Search;
import io.searchbox.indices.DeleteIndex;
@Service
public class SearchService {
	 final static Logger logger = LoggerFactory.getLogger(SearchService.class);

	 @Autowired
	 private JestClient jestClient;

	 public List<Log> searchLogs(String indexName, String typeName, String query) {
		 try{
	    		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	            searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
	            Search search = new Search.Builder(searchSourceBuilder.toString())
	                    .addIndex(indexName)
	                    .addType(typeName)
	                    .build();
	            JestResult result = jestClient.execute(search);
	            return result.getSourceAsObjectList(Log.class);
	    	}catch (IOException e) {
	            logger.error("Search error", e);
	        } catch (Exception e) {
	            logger.error("Search error", e);
	        }
	    	return null;
	 }
	 public List<Article> searchArticles(String indexName, String typeName, String query) {
	     try {
	            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	            searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
	            Search search = new Search.Builder(searchSourceBuilder.toString())
	                    .addIndex(indexName)
	                    .addType(typeName)
	                    .build();
	            JestResult result = jestClient.execute(search);
	            return result.getSourceAsObjectList(Article.class);
	        } catch (IOException e) {
	            logger.error("Search error", e);
	        } catch (Exception e) {
	            logger.error("Search error", e);
	        }
	     return null;
	 }
	 public Boolean deleteLogsById(String indexName, String typeName, String id){
	    try{
	    	Delete delete = new Delete.Builder(id).index(indexName).type(typeName).build();
	    	jestClient.execute(delete);
	    	return true;
	    }catch(IOException e){
	    	logger.error("delete error",e);
	    }catch (Exception e) {
	        logger.error("Search error", e);
	    }
	    return false;
	  }
	 public Boolean deleteLogsByIndex(String indexName){
		 try{
			DeleteIndex deleteIndex = new DeleteIndex.Builder(indexName).build();
			jestClient.execute(deleteIndex);
			return true;
		 }catch(IOException e){
		    logger.error("delete error",e);
		 }catch (Exception e) {
		    logger.error("delete error", e);
		 }
		 return false;
	 }
	 public EsResult<Log> searchLogsPagely(String indexName, String typeName, String query){
		 try{
			 
			  SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	          searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
	          Search search = new Search.Builder(searchSourceBuilder.toString())
	                    .addIndex(indexName)
	                    .addType(typeName)
	                    .setParameter("from", 0)
	                    .setParameter("size", 2)
	                    .build();
	         JestResult jestResult = jestClient.execute(search);
	         int hitCount = jestResult.getJsonObject().get("hits").getAsJsonObject().get("total").getAsInt();
	         logger.info(String.valueOf(hitCount));
//			 Search.Builder builder = new Search.Builder(esQuery.getQueryString());
//			 builder.addIndex(indexName)
//			        .addType(typeName)
//			        .setParameter("from", esQuery.getFrom())
//			        .setParameter("size", esQuery.getSize());
//			 if(esQuery.getSort() != null){
//				 builder.addSort(esQuery.getSort());
//			 }
//			 JestResult jestResult = jestClient.execute(builder.build());
			 //int hitCount = jestResult.getJsonObject().get("hits").getAsJsonObject().get("total").getAsInt();

			 EsResult<Log> result = new EsResult<Log>();
			 result.setTotalNum(hitCount);
			 result.setResultList(jestResult.getSourceAsObjectList(Log.class));

			 return result;
		 }catch(IOException e){
			 logger.error("search error",e);
		 }catch (Exception e) {
			 logger.error("Search error", e);
		 }
		 return null;
	 }


}
