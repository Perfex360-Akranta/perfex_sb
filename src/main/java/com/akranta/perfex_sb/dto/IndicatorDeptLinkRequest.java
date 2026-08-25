package com.akranta.perfex_sb.dto;

import java.util.List;



public class IndicatorDeptLinkRequest {

    List <NewIndicatorDeptLinkRequestDto> NewIndicatorDeptLinkRequestDto;

   

    private String pillCode;
    private String indicatorId;
    private String deptId;
    private String isIndicatorFactory;
    private String drillLevel;
    private String createdBy;
    private List<IndicatorDeptLinkItem> methodPillarFactlink;

    // Inner class for list items
    public static class IndicatorDeptLinkItem {
        private String keyid;
        private String indicatorid;
        private String deptid;
        private String isDelete; // "Y" or "N"

        // Getters and Setters
        public String getKeyid() {
            return keyid;
        }

        public void setKeyid(String keyid) {
            this.keyid = keyid;
        }

        public String getIndicatorid() {
            return indicatorid;
        }

        public void setIndicatorid(String indicatorid) {
            this.indicatorid = indicatorid;
        }

        public String getDeptid() {
            return deptid;
        }

        public void setDeptid(String deptid) {
            this.deptid = deptid;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }
    }

    // Getters and Setters
    public String getPillCode() {
        return pillCode;
    }

    public void setPillCode(String pillCode) {
        this.pillCode = pillCode;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getIsIndicatorFactory() {
        return isIndicatorFactory;
    }

    public void setIsIndicatorFactory(String isIndicatorFactory) {
        this.isIndicatorFactory = isIndicatorFactory;
    }

    public String getDrillLevel() {
        return drillLevel;
    }

    public void setDrillLevel(String drillLevel) {
        this.drillLevel = drillLevel;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<IndicatorDeptLinkItem> getMethodPillarFactlink() {
        return methodPillarFactlink;
    }

    public void setMethodPillarFactlink(List<IndicatorDeptLinkItem> methodPillarFactlink) {
        this.methodPillarFactlink = methodPillarFactlink;
    }

    public List<NewIndicatorDeptLinkRequestDto> getNewIndicatorDeptLinkRequestDto() {
        return NewIndicatorDeptLinkRequestDto;
    }

    public void setNewIndicatorDeptLinkRequestDto(List<NewIndicatorDeptLinkRequestDto> newIndicatorDeptLinkRequestDto) {
        NewIndicatorDeptLinkRequestDto = newIndicatorDeptLinkRequestDto;
    }
}