package com.how2java.tmall.test;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ElasticSearchService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.service.impl.CategoryServiceImpl;
import com.how2java.tmall.service.impl.ElasticSearchServiceImpl;
import com.how2java.tmall.service.impl.ProductServiceImpl;
import com.how2java.tmall.util.ElasticUtils;
import com.how2java.tmall.util.SpringContextUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class InsertDataToElasticSearch {
//    @Autowired
//    static ElasticSearchService elasticSearchService;
//    @Autowired
//    static  ProductService productService;
//    @Autowired
//    static CategoryService categoryService;
    private static ApplicationContext applicationContext;
    public static void main(String[] args) throws Exception {
        String today = "2019-8-2";

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date now =sdf.parse(today);
        Date test = new Date();

        if(test.getTime()>now.getTime()+1000*60*60*24){
            System.err.println("――――――未成成功运行――――――");
            System.err.println("――――――未成成功运行――――――");
            System.err.println("本程序具有破坏作用，应该只运行一次，如果必须要再运行，需要修改today变量为今天，如:" + sdf.format(new Date()));
            return;
        }

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        CategoryService categoryService = (CategoryService) ac.getBean(CategoryService.class);
        ProductService  productService = ( ProductService) ac.getBean( ProductService.class);
        ElasticSearchService elasticSearchService = (ElasticSearchService) ac.getBean(ElasticSearchService.class);
        List<Product> ps=  new ArrayList<>();
        List<Category> cs=  categoryService.list();
        ElasticUtils.createIndex("how2java");
        System.out.println(elasticSearchService.checkExistIndex("how2java"));

        for ( Category c :cs
             ) {
        ps.addAll(productService.list(c.getId()));
            System.out.println(c.getName());
        }
      elasticSearchService.addAllProduct(ps);
    }
}
