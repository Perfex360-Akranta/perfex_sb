package com.akranta.perfex_sb.dto;

public class MenuDto {

     private Long menuNumber;

    private Long parentNumber;

    private String menuCaption;

    private String isParent;

    public Long getMenuNumber() {
        return menuNumber;
    }

    public void setMenuNumber(Long menuNumber) {
        this.menuNumber = menuNumber;
    }

    public Long getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(Long parentNumber) {
        this.parentNumber = parentNumber;
    }

    public String getMenuCaption() {
        return menuCaption;
    }

    public void setMenuCaption(String menuCaption) {
        this.menuCaption = menuCaption;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    


}
