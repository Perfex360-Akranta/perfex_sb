package com.akranta.perfex_sb.dto;

public class MenuTreeRecordDto {

    private String menuNumber;
    private String parentNumber;
    private String menuName;
    private String menuCaption;
    private String menuLevel;
    private String menuSortNumber;
    private boolean isParent;
    private String formName;
    private String relatedFilter;
    private char filterNeed;
    private boolean master;
    private String reportFileName;

    public String getMenuNumber() { return menuNumber; }
    public void setMenuNumber(String menuNumber) { this.menuNumber = menuNumber; }

    public String getParentNumber() { return parentNumber; }
    public void setParentNumber(String parentNumber) { this.parentNumber = parentNumber; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public String getMenuCaption() { return menuCaption; }
    public void setMenuCaption(String menuCaption) { this.menuCaption = menuCaption; }

    public String getMenuLevel() { return menuLevel; }
    public void setMenuLevel(String menuLevel) { this.menuLevel = menuLevel; }

    public String getMenuSortNumber() { return menuSortNumber; }
    public void setMenuSortNumber(String menuSortNumber) { this.menuSortNumber = menuSortNumber; }

    public void setParent(boolean isParent) { this.isParent = isParent; }
    public boolean isParent() { return isParent; }

    public String getFormName() { return formName; }
    public void setFormName(String formName) { this.formName = formName; }

    public String getRelatedFilter() { return relatedFilter; }
    public void setRelatedFilter(String relatedFilter) { this.relatedFilter = relatedFilter; }

    public void setFilterNeed(char filterNeed) { this.filterNeed = filterNeed; }
    public char getFilterNeed() { return filterNeed; }

    public void setMaster(boolean master) { this.master = master; }
    public boolean isMaster() { return master; }

    public void setReportFileName(String reportFileName) { this.reportFileName = reportFileName; }
    public String getReportFileName() { return reportFileName; }
}