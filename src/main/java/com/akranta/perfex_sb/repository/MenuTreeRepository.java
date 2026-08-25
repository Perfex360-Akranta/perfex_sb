    package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.MenuTree;

public interface MenuTreeRepository extends JpaRepository<MenuTree, Long> {

    @Query(value = """
            SELECT MNUM_MENUNUMBER, MNUM_PARENTNUMBER, MNUM_MENUNAME, MNUM_MENUCAPTION, MNUM_FORMNAME,
                   MNUM_MODE, MNUM_ISPARENT, MNUM_MENULEVEL, MNUM_IMAGEINDEX, MNUM_ISMASTER, MNUM_ROOTNUMBER, MNUM_SORTNUMBER,
                   TRIM(MNUM_TABLENAME) AS MNUM_TABLENAME, TRIM(MNUM_LOADFORMARGUMENT) AS MNUM_LOADFORMARGUMENT,
                   TRIM(MNUM_MASTINTEGSQL) AS MNUM_MASTINTEGSQL, TRIM(MNUM_MASTINTEGORDERBYSQL) AS MNUM_MASTINTEGORDERBYSQL,
                   MNUM_SIMILARCOLUMN, MNUM_SHORTCUTKEY, MNUM_ACTIVE, MNUM_CREATEDON, ARML_MENUID, ARUL_USERID
            FROM ADM_VW_MENUMST M JOIN ADM_VW_MENUUSERRIGHTS ON MNUM_MENUNUMBER = ARML_MENUID
            WHERE ARUL_USERID = :userId AND MNUM_MENULEVEL = 1
            ORDER BY mnum_menulevel, mnum_sortnumber, mnum_parentnumber, mnum_menunumber
            """, nativeQuery = true)
    List<Map<String, Object>> getRootMenus(@Param("userId") String userId);

    @Query(value = """
            SELECT MNUM_MENUNUMBER, MNUM_PARENTNUMBER, MNUM_MENUNAME, MNUM_MENUCAPTION, MNUM_FORMNAME,
                   MNUM_MODE, MNUM_ISPARENT, MNUM_MENULEVEL, MNUM_IMAGEINDEX, MNUM_ISMASTER, MNUM_ROOTNUMBER, MNUM_SORTNUMBER,
                   TRIM(MNUM_TABLENAME) AS MNUM_TABLENAME, TRIM(MNUM_LOADFORMARGUMENT) AS MNUM_LOADFORMARGUMENT,
                   TRIM(MNUM_MASTINTEGSQL) AS MNUM_MASTINTEGSQL, TRIM(MNUM_MASTINTEGORDERBYSQL) AS MNUM_MASTINTEGORDERBYSQL,
                   MNUM_SIMILARCOLUMN, MNUM_SHORTCUTKEY, MNUM_ACTIVE, MNUM_CREATEDON, ARML_MENUID, ARUL_USERID
            FROM ADM_VW_MENUMST M JOIN ADM_VW_MENUUSERRIGHTS ON MNUM_MENUNUMBER = ARML_MENUID
            WHERE ARUL_USERID = :userId
              AND MNUM_PARENTNUMBER <> MNUM_MENUNUMBER
              AND MNUM_PARENTNUMBER = CAST(:parentNumber AS numeric)
            ORDER BY mnum_menulevel, mnum_sortnumber, mnum_parentnumber, mnum_menunumber
            """, nativeQuery = true)
    List<Map<String, Object>> getChildMenus(@Param("parentNumber") String parentNumber, @Param("userId") String userId);

    @Query(value = """
            SELECT MNUM_MENUNUMBER, MNUM_PARENTNUMBER, MNUM_MENUNAME, MNUM_MENUCAPTION, MNUM_FORMNAME,
                   MNUM_MODE, MNUM_ISPARENT, MNUM_MENULEVEL, MNUM_IMAGEINDEX, MNUM_ISMASTER, MNUM_ROOTNUMBER, MNUM_SORTNUMBER,
                   TRIM(MNUM_TABLENAME) AS MNUM_TABLENAME, TRIM(MNUM_LOADFORMARGUMENT) AS MNUM_LOADFORMARGUMENT,
                   TRIM(MNUM_MASTINTEGSQL) AS MNUM_MASTINTEGSQL, TRIM(MNUM_MASTINTEGORDERBYSQL) AS MNUM_MASTINTEGORDERBYSQL,
                   MNUM_SIMILARCOLUMN, MNUM_SHORTCUTKEY, MNUM_ACTIVE, MNUM_CREATEDON, ARML_MENUID, ARUL_USERID
            FROM ADM_VW_MENUMST M JOIN ADM_VW_MENUUSERRIGHTS ON MNUM_MENUNUMBER = ARML_MENUID
            WHERE ARUL_USERID = :userId
            ORDER BY mnum_menulevel, mnum_sortnumber, mnum_parentnumber, mnum_menunumber
            """, nativeQuery = true)
    List<Map<String, Object>> getAllUserMenus(@Param("userId") String userId);
}