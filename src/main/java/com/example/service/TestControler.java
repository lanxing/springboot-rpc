package com.example.service;

import com.example.domain.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-07, 下午11:55
 * @desc 类功能描述
 */
@RestController
@RequestMapping("/book")
public class TestControler {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Book testBook(){
        Book book = new Book();

        book.setAuthorName("高凡");
        book.setIdn("11111dddddd333333");
        book.setName("Zookeeper in action");

        return book;
    }
}
