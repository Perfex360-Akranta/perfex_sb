package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "adm_tl_menumst")
public class MenuTree {

    @Id
    @Column(name = "mnum_menunumber")
    private Long menuNumber;

    @Column(name = "mnum_parentnumber")
    private Long parentNumber;

    @Column(name = "mnum_menuname")
    private String menuName;

    @Column(name = "mnum_menucaption")
    private String menuCaption;

    @Column(name = "mnum_formname")
    private String formName;

    @Column(name = "mnum_mode")
    private String mode;

    @Column(name = "mnum_isparent")
    private String isParent;

    @Column(name = "mnum_menulevel")
    private Integer menuLevel;

    @Column(name = "mnum_imageindex")
    private Integer imageIndex;

    @Column(name = "mnum_ismaster")
    private String isMaster;

    @Column(name = "mnum_rootnumber")
    private Integer rootNumber;

    @Column(name = "mnum_sortnumber")
    private Integer sortNumber;

    @Column(name = "mnum_tablename")
    private String tableName;

    @Column(name = "mnum_loadformargument")
    private String loadFormArgument;

    @Column(name = "mnum_mastintegsql")
    private String mastIntegSql;

    @Column(name = "mnum_mastintegorderbysql")
    private String mastIntegOrderBySql;

    @Column(name = "mnum_similarcolumn")
    private String similarColumn;

    @Column(name = "mnum_shortcutkey")
    private String shortcutKey;

    @Column(name = "mnum_active")
    private String active;

    @Column(name = "mnum_createdon")
    private LocalDateTime createdOn;

    // getters and setters

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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCaption() {
        return menuCaption;
    }

    public void setMenuCaption(String menuCaption) {
        this.menuCaption = menuCaption;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Integer getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(Integer imageIndex) {
        this.imageIndex = imageIndex;
    }

    public String getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(String isMaster) {
        this.isMaster = isMaster;
    }

    public Integer getRootNumber() {
        return rootNumber;
    }

    public void setRootNumber(Integer rootNumber) {
        this.rootNumber = rootNumber;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getLoadFormArgument() {
        return loadFormArgument;
    }

    public void setLoadFormArgument(String loadFormArgument) {
        this.loadFormArgument = loadFormArgument;
    }

    public String getMastIntegSql() {
        return mastIntegSql;
    }

    public void setMastIntegSql(String mastIntegSql) {
        this.mastIntegSql = mastIntegSql;
    }

    public String getMastIntegOrderBySql() {
        return mastIntegOrderBySql;
    }

    public void setMastIntegOrderBySql(String mastIntegOrderBySql) {
        this.mastIntegOrderBySql = mastIntegOrderBySql;
    }

    public String getSimilarColumn() {
        return similarColumn;
    }

    public void setSimilarColumn(String similarColumn) {
        this.similarColumn = similarColumn;
    }

    public String getShortcutKey() {
        return shortcutKey;
    }

    public void setShortcutKey(String shortcutKey) {
        this.shortcutKey = shortcutKey;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}