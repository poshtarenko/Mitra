package com.mitra.db.filter;

public class AbstractFilter implements Filter{
    private Integer limit;
    private Integer offset;

    public AbstractFilter(Integer limit, Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public int getOffset() {
        return offset;
    }
}
