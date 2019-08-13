package com.how2java.tmall.util;

import com.how2java.tmall.pojo.Product;
import org.apache.http.HttpHost;
import org.apache.lucene.search.Query;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.sort.SortOrder;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ElasticUtils {
    // 相当于数据库名称（数据量小）
    public static String indexName = "how2java";
    public static String type = "product";



    // 初始化api客户端
    public static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("127.0.0.1", 9200, "http")
            ));

    // 关键字搜索 指定匹配类型
    public static List<Object> search(String type,String fieldName, String keyword, int start, int count) throws IOException {




        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //关键字匹配对应字段
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(fieldName, keyword);
        MatchQueryBuilder matchQueryBuilder2 = new MatchQueryBuilder("subTitle", keyword);
        MatchQueryBuilder matchQueryBuilder3 = new MatchQueryBuilder("id", keyword);

        //模糊匹配
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        matchQueryBuilder2.fuzziness(Fuzziness.AUTO);
        matchQueryBuilder3.fuzziness(Fuzziness.AUTO);
        //
        QueryBuilder qb = QueryBuilders.boolQuery()
                .should(matchQueryBuilder)
                .should(matchQueryBuilder2)
                .should(matchQueryBuilder2);
        //使用上面的Query
     //  sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.query(qb);
        //第几页
        sourceBuilder.from(start);
        //第几条
        sourceBuilder.size(count);
        //匹配度从高到低
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        // 查询的等待时间
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //指定索引库和类型
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(type);

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);

        SearchHits hits = searchResponse.getHits();

        List<Object> matchRsult = new LinkedList<>();

        for (SearchHit hit : hits.getHits())
        {
            matchRsult.add(hit.getSourceAsMap());
        }
        return matchRsult;
    }
    //删除指定类型
    public static void deleteDocument(Object object) throws IOException {
        if(object instanceof Product){
            DeleteRequest deleteRequest = new DeleteRequest (indexName,type, ((Product) object).getId().toString());
            System.out.println("已经从ElasticSearch服务器上删除id="+((Product) object).getId()+"的product文档");
            client.delete(deleteRequest);
        }
    }
    // 获得指定type指定id的数据 json
    public static List<Object> getDocument(String type,String id) throws IOException {
        // TODO Auto-generated method stub
        GetRequest request = new GetRequest(
                indexName,
                type,
                id);

        GetResponse response = client.get(request);

        if(!response.isExists()){
            System.out.println("检查到服务器上 "+type+" id="+id+ "的文档不存在");
            return null;
        }
        else{
            String source = response.getSourceAsString();
            System.out.print("获取到服务器上 "+type+" id="+id+ "的文档内容是：");
            System.out.println(source);
            List<Object> matchRsult = new LinkedList<Object>();
            matchRsult.add(response.getSourceAsMap());
            return matchRsult;
        }
    }
    // 插入指定type，数据
    public static void addDocument(Object object) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        if(object instanceof Product){
            jsonMap.put("id", ((Product)object).getId());
            jsonMap.put("name", ((Product)object).getName());
            jsonMap.put("price", ((Product)object).getOriginalPrice());
          //  jsonMap.put("detail", ((Product)object).getDetail());
            IndexRequest indexRequest = new IndexRequest(indexName, type, ((Product)object).getId().toString())
                    .source(jsonMap);
            client.index(indexRequest);
            System.out.println("已经向ElasticSearch服务器增加Product："+object);
        }

    }
    // 更新数据
    public static void updateDocument(Object object) throws IOException {

        if(object instanceof Product){
            UpdateRequest updateRequest = new UpdateRequest (indexName, type, ((Product) object).getId().toString())
                    .doc("name",((Product) object).getId())
                    .doc("price",((Product) object).getOriginalPrice());
                   // .doc("detail",((Product) object).getDetail());
            client.update(updateRequest);
            System.out.println("已经在ElasticSearch服务器修改产品为："+object);
        }


    }
    public static boolean checkExistIndex(String indexName) throws IOException {
        boolean result =true;
        try {

            OpenIndexRequest openIndexRequest = new OpenIndexRequest(indexName);
            client.indices().open(openIndexRequest).isAcknowledged();

        } catch (ElasticsearchStatusException ex) {
            String m = "Elasticsearch exception [type=index_not_found_exception, reason=no such index]";
            if (m.equals(ex.getMessage())) {
                result = false;
            }
        }
        if(result)
            System.out.println("索引:" +indexName + " 是存在的");
        else
            System.out.println("索引:" +indexName + " 不存在");

        return result;

    }

    private static void deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        client.indices().delete(request);
        System.out.println("删除了索引："+indexName);

    }

    public static void createIndex(String indexName) throws IOException {
        // TODO Auto-generated method stub
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        client.indices().create(request);
        System.out.println("创建了索引："+indexName);
    }

//     批量插入
    public static void batchInsert(List<Product> products) throws IOException {
        // TODO Auto-generated method stub
        BulkRequest request = new BulkRequest();

        for (Product product : products) {
            Map<String,Object> m  = product.toMap();
            IndexRequest indexRequest= new IndexRequest(indexName, type, String.valueOf(product.getId())).source(m);
            request.add(indexRequest);
        }

        client.bulk(request);
        System.out.println("批量插入完成");
    }
    public static String getIndexName() {
        return indexName;
    }

    public static void setIndexName(String indexName) {
        ElasticUtils.indexName = indexName;
    }

    public static RestHighLevelClient getClient() {
        return client;
    }

    public static void setClient(RestHighLevelClient client) {
        ElasticUtils.client = client;
    }

}
