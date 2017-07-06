package com.example.domain;

import java.io.Serializable;

/**
 * @author muqi
 * @version 1.00
 * @date 2016-11-22, 下午2:34
 * @desc 类功能描述
 */
public class Book implements Serializable {
    private static final long serialVersionUID = -8893314667281621226L;

    private String name;

    private String idn;

    private String authorName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdn() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
