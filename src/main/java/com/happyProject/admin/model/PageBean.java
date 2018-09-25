package com.happyProject.admin.model;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
	private List<T> data = new ArrayList<T>(); // 当前页数据
	private Integer allRow; // 总记录数
	private Integer totalPage; // 总页数
	private Integer currentPage; // 当前页
	private Integer pageSize; // 每页记录数
	public PageBean(List<T> data, Integer allRow, Integer totalPage, Integer currentPage, Integer pageSize) {
		super();
		this.data = data;
		this.allRow = allRow;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	public PageBean() {
		super();
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public Integer getAllRow() {
		return allRow;
	}
	public void setAllRow(Integer allRow) {
		this.allRow = allRow;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "PageBean [data=" + data + ", allRow=" + allRow + ", totalPage=" + totalPage + ", currentPage="
				+ currentPage + ", pageSize=" + pageSize + "]";
	}
	
	
}
