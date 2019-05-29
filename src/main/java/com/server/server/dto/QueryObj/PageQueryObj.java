package com.server.server.dto.QueryObj;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiParam;

import java.util.List;

public class PageQueryObj<T> implements IPage {
    private long size = 10;
    private long current = 0;
    @ApiParam(hidden = true)
    private long total;
    @ApiParam(hidden = true)
    private long pages;
    @ApiParam(hidden = true)
    List<T> records;
    @Override
    public List getRecords() {
        return records;
    }

    @Override
    public IPage setRecords(List records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public IPage setTotal(long total) {
        this.total = total;
        setPages(getPages());
        return this;
    }

    @Override
    public IPage setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public long getCurrent() {
        return current;
    }

    @Override
    public IPage setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public IPage setPages(long pages) {
        this.pages = pages;
        return this;
    }
}
