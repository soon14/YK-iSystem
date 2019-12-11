package com.yksys.isystem.common.core.dto;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @program: YK-iSystem
 * @description: 数据表格视图分页
 * @author: YuKai Fan
 * @create: 2019-12-04 09:23
 **/
@Data
public class DataTableViewPage<T> {
    //封装的数据
    private List<T> data;
    //数据总数
    private long total;

    public DataTableViewPage(List<T> data) {
        if (data instanceof Page) {
            PageInfo a = new PageInfo(data);
            this.data = a.getList();
            this.total = a.getTotal();
        }
    }
}