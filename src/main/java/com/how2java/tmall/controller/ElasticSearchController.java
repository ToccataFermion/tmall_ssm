package com.how2java.tmall.controller;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.ElasticSearchService;
import com.how2java.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.elasticsearch.common.inject.spi.Message;


@Controller
public class ElasticSearchController {

        @Autowired
        private ProductService productService;
        @Autowired
        private ElasticSearchService elasticService;

        @RequestMapping(value = "product",method = RequestMethod.POST)
        @ResponseBody
        public Message addProduct(
                String id
//                @RequestParam(name = "name")String name,
//                @RequestParam(name = "price")String price
//                @RequestParam(name = "detail")String detail
        ){

            return elasticService.addProduct(productService.get(Integer.parseInt(id)));
        }

        @RequestMapping(value = "product",method = RequestMethod.DELETE)
        @ResponseBody
        public Message delProduct(
               String id){
            return elasticService.delProduct(id);
        }

        @RequestMapping(value = "product",method = RequestMethod.PUT)
        @ResponseBody
        public Message updateProduct(
                String id
//                @RequestParam(name = "name")String name,
//                @RequestParam(name = "price")String price,
//                @RequestParam(name = "detail")String detail
        ){
            return elasticService.updateProduct(productService.get(Integer.parseInt(id)));
        }

        @RequestMapping(value = "product",method = RequestMethod.GET)
        @ResponseBody
        public Message searchProduct(
                @RequestParam( value="fieldName",required = false)String fieldName,
                @RequestParam(value= "name",required = false)String name,
                @RequestParam(value = "start",required = false)Integer start,
                @RequestParam(value = "count",required = false)Integer count,
                @RequestParam(value = "id",required = false)String id){
            if(id != null){
                return elasticService.getProduct(id);
            }else {
                return elasticService.searchProduct(fieldName,name,start,count);
            }
        }

    }
