package com.akranta.perfex_sb.dto;

public class MetaConfigDto {
       private int modelRowIndex;
    private int parentHeaderRowIndex;
    private Integer childHeaderRowIndex;
    private int orderRowIndex;
    private int dataStartIndex;

    public int getModelRowIndex() {
        return modelRowIndex;
    }

    public void setModelRowIndex(int modelRowIndex) {
        this.modelRowIndex = modelRowIndex;
    }

    public int getParentHeaderRowIndex() {
        return parentHeaderRowIndex;
    }

    public void setParentHeaderRowIndex(int parentHeaderRowIndex) {
        this.parentHeaderRowIndex = parentHeaderRowIndex;
    }

    public Integer getChildHeaderRowIndex() {
        return childHeaderRowIndex;
    }

    public void setChildHeaderRowIndex(Integer childHeaderRowIndex) {
        this.childHeaderRowIndex = childHeaderRowIndex;
    }

    public int getOrderRowIndex() {
        return orderRowIndex;
    }

    public void setOrderRowIndex(int orderRowIndex) {
        this.orderRowIndex = orderRowIndex;
    }

    public int getDataStartIndex() {
        return dataStartIndex;
    }

    public void setDataStartIndex(int dataStartIndex) {
        this.dataStartIndex = dataStartIndex;
    }


    
}
