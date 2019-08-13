package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.util.ElasticUtils;
import org.elasticsearch.common.inject.spi.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface ElasticSearchService {

        Message addProduct(Product product);

        Message delProduct(String id) ;

        Message getProduct(String id);

        Message searchProduct(String fieldName,String keyword,int start,int count);

        Message updateProduct(Product product);

        Message addAllProduct( List<Product>  ps) ;

        boolean checkExistIndex( String  indexName) ;


}
