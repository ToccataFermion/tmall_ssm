package com.how2java.tmall.service.impl;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.ElasticSearchService;
import com.how2java.tmall.util.ElasticUtils;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.elasticsearch.common.inject.spi.Message;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {



    @Override
    public Message addProduct(Product product) {
        try {
            ElasticUtils.addDocument(product);
        } catch (IOException e) {
            e.printStackTrace();
            return new Message("500");
        }
        return new Message("200");
    }

    @Override
    public Message delProduct(String id) {
        Product product = new Product();
        product.setId(Integer.valueOf(id));
        try {
            ElasticUtils.deleteDocument(product);
        } catch (IOException e) {
            e.printStackTrace();
            return new Message("500");
        }
        return new Message("200");
    }

    @Override
    public Message getProduct(String id)  {
        try {
            return new Message(ElasticUtils.getDocument("product",id),"200",(Throwable)null);
        } catch (IOException e) {
            e.printStackTrace();
            return new Message("500");
        }
    }

    @Override
    public Message searchProduct(String fieldName,String keyword,int start,int count) {
        try {
            return new Message(ElasticUtils.search("product",fieldName,keyword,start,count),"200" ,(Throwable)null);
        } catch (IOException e) {
            e.printStackTrace();
            return new Message("500");
        }
    }

    @Override
    public Message updateProduct(Product product) {
        try {
            ElasticUtils.updateDocument(product);
        } catch (IOException e) {
            e.printStackTrace();
            return new Message("500");
        }
        return new Message("200");
    }
    @Override
    public Message addAllProduct(List<Product> ps) {
        try {
            ElasticUtils.batchInsert(ps);
        } catch (IOException e) {
            e.printStackTrace();
            return new Message("500");
        }
        return new Message("200");
    }
    @Override
    public boolean checkExistIndex(String indexName) {
        try {
            return ElasticUtils.checkExistIndex(indexName);
        } catch (IOException e) {
            e.printStackTrace();
            return  false ;
        }
    }
}
