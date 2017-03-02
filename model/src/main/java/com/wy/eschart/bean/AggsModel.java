package com.wy.eschart.bean;

import java.util.Map;

/**
 * Created by wangyang on 2017/3/1.
 */
public class AggsModel {

    private String name;
    private String parent;
    private String nature;
    private String element;
    private Map<String, ?> body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public Map<String, ?> getBody() {
        return body;
    }

    public void setBody(Map<String, ?> body) {
        this.body = body;
    }


}
