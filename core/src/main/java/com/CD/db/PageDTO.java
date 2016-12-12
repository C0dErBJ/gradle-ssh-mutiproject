package com.CD.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 封装分页时的页面信息的类
 *
 * @author qian_qi
 *         2012-10-15
 */
public class PageDTO<U> implements Serializable {

    //页面大小
    protected int[] pageSizeList = {10, 25, 50, 100, 200, 300, 500};
    //单页显示的数据数
    private int pageSize = 10;
    //当前页码数
    private int pageNo = 1;
    //总条数
    private int rowCount = 0;
    //总页数
    private int pageCount = 1;
    //起始行数
    private int startIndex = 0;
    //结束行数
    private int endIndex = 0;
    //结果集
    protected List<?> resultList;
    //第一页
    protected int firstPageNo = 1;
    //前一页
    protected int prePageNo = 1;
    //下一页
    protected int nextPageNo = 1;
    //最后一页
    protected int lastPageNo = 1;
    //禁用第一页
    private boolean enableFirstPageNo = false;
    //禁用前一页
    private boolean enablePrePageNo = false;
    //禁用下一页
    private boolean enableNextPageNo = false;
    //禁用最后一页
    private boolean enableLastPageNo = false;

    public PageDTO() {
    }

    /**
     * 构造函数
     *
     * @param pageSize   页面记录的条数
     * @param pageNo     第几页
     * @param rowCount   总共有多少条记录
     * @param resultList 当前页的记录的数据
     */
    public PageDTO(int pageSize, int pageNo, int rowCount, List<?> resultList) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.rowCount = rowCount;
        if (resultList == null) {
            resultList = new LinkedList<Object>();
        }
        this.resultList = resultList;

        if (rowCount % pageSize == 0)
            this.pageCount = rowCount / pageSize;
        else
            this.pageCount = rowCount / pageSize + 1;
        if (pageCount == 0) {
            pageCount = 1;
        }
        //每页显示的条目数 乘以 当前页码数减1，等于当前页从第几条开始显示
        this.startIndex = pageSize * (pageNo - 1);
        //
//		this.endIndex = this.startIndex + resultList.size();
        //如果当前索引加上页数减一小于记录的条数减一
        //则当前页的最后索引为记录条数减一
        if (rowCount - 1 < this.startIndex + pageSize - 1) {
            this.endIndex = rowCount - 1;
        } else {
            this.endIndex = this.startIndex + pageSize - 1;
        }

        //最后一页的页码数位总页数
        this.lastPageNo = this.pageCount;
        //如果当前页大于1，则前一页页码等于当前页减1
        if (this.pageNo > 1)
            this.prePageNo = this.pageNo - 1;
        //如果当前页为最后一页，则下一页页码为最后一页，否则下一页页码为当前页加1
        if (this.pageNo == this.lastPageNo) {
            this.nextPageNo = this.lastPageNo;
        } else {
            this.nextPageNo = this.pageNo + 1;
        }
        //如果当前页是第一页,则禁用首页和上一页
        if (this.pageNo == 1) {
            this.enableFirstPageNo = false;
            this.enablePrePageNo = false;
        } else {
            this.enableFirstPageNo = true;
            this.enablePrePageNo = true;
        }

        //如果当前页是最后一页,则禁用最后一页和下一页
        if (this.pageNo == this.lastPageNo) {
            this.enableLastPageNo = false;
            this.enableNextPageNo = false;
        } else {
            this.enableNextPageNo = true;
            this.enableLastPageNo = true;
        }
    }

    /**
     * 获取页面大小的索引，
     *
     * @return
     */
    public Object[] getPageSizeIndexs() {
        List<String> result = new ArrayList<String>(pageSizeList.length);
        for (int i = 0; i < pageSizeList.length; i++) {
            result.add(String.valueOf(pageSizeList[i]));
        }
        Object[] indexs = (result.toArray());
        return indexs;
    }

    /**
     * @return
     */
    public Object[] getPageNoIndexs() {
        List<String> result = new ArrayList<String>(pageCount);
        for (int i = 0; i < pageCount; i++) {
            result.add(String.valueOf(i + 1));
        }
        Object[] indexs = (result.toArray());
        return indexs;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int[] getPageSizeList() {
        return pageSizeList;
    }

    public void setPageSizeList(int[] pageSizeList) {
        this.pageSizeList = pageSizeList;
    }

    public List<?> getResultList() {
        return resultList;
    }

    public void setResultList(List<?> resultList) {
        this.resultList = resultList;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getFirstPageNo() {
        return firstPageNo;
    }

    public void setFirstPageNo(int firstPageNo) {
        this.firstPageNo = firstPageNo;
    }

    public int getLastPageNo() {
        return lastPageNo;
    }

    public void setLastPageNo(int lastPageNo) {
        this.lastPageNo = lastPageNo;
    }

    public int getNextPageNo() {
        return nextPageNo;
    }

    public void setNextPageNo(int nextPageNo) {
        this.nextPageNo = nextPageNo;
    }

    public int getPrePageNo() {
        return prePageNo;
    }

    public void setPrePageNo(int prePageNo) {
        this.prePageNo = prePageNo;
    }

    public boolean isEnableFirstPageNo() {
        return enableFirstPageNo;
    }

    public void setEnableFirstPageNo(boolean enableFirstPageNo) {
        this.enableFirstPageNo = enableFirstPageNo;
    }

    public boolean isEnablePrePageNo() {
        return enablePrePageNo;
    }

    public void setEnablePrePageNo(boolean enablePrePageNo) {
        this.enablePrePageNo = enablePrePageNo;
    }

    public boolean isEnableNextPageNo() {
        return enableNextPageNo;
    }

    public void setEnableNextPageNo(boolean enableNextPageNo) {
        this.enableNextPageNo = enableNextPageNo;
    }

    public boolean isEnableLastPageNo() {
        return enableLastPageNo;
    }

    public void setEnableLastPageNo(boolean enableLastPageNo) {
        this.enableLastPageNo = enableLastPageNo;
    }

}
