package com.ruoyi.project.family.orgchart.domain;

import java.io.Serializable;
import java.util.List;
/**
 * 家族组织结构对象
 *
 * @author haibin
 * @date 2023-01-02
 */
public class OrgChartInfo implements Serializable {

    public static final String BLUECLS = "blue-cls";//蓝色
    public static final String ROSEREDCLS = "rose-red-cls";//玫红色
    public static final String BRIGHTREDCLS = "bright-red-cls";//亮红色

    private String name;
    private String title;

    private String className;

    private List<OrgChartInfo> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<OrgChartInfo> getChildren() {
        return children;
    }

    public void setChildren(List<OrgChartInfo> children) {
        this.children = children;
    }
}
