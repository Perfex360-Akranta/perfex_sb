package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.MenuTree;

public interface MenuTreeRepository extends JpaRepository<MenuTree, Long> {

    // Interface fields are implicitly static final, and text blocks are compile-time
    // constants, so they can be concatenated inside @Query.
    String MENU_SELECT = """
            SELECT M.MNUM_MENUNUMBER, M.MNUM_PARENTNUMBER, M.MNUM_MENUNAME, M.MNUM_MENUCAPTION, M.MNUM_FORMNAME,
                   M.MNUM_MODE, M.MNUM_ISPARENT, M.MNUM_MENULEVEL, M.MNUM_IMAGEINDEX, M.MNUM_ISMASTER,
                   M.MNUM_ROOTNUMBER, M.MNUM_SORTNUMBER,
                   TRIM(M.MNUM_TABLENAME) AS MNUM_TABLENAME,
                   TRIM(M.MNUM_LOADFORMARGUMENT) AS MNUM_LOADFORMARGUMENT,
                   TRIM(M.MNUM_MASTINTEGSQL) AS MNUM_MASTINTEGSQL,
                   TRIM(M.MNUM_MASTINTEGORDERBYSQL) AS MNUM_MASTINTEGORDERBYSQL,
                   M.MNUM_SIMILARCOLUMN, M.MNUM_SHORTCUTKEY, M.MNUM_ACTIVE, M.MNUM_CREATEDON,
                   R.ARML_MENUID, R.ARUL_USERID,
                   COALESCE(MD.MNUM_ISANGULAR, 'N')  AS MNUM_ISANGULAR,
                   COALESCE(MD.MNUM_ANGULARPATH, '') AS MNUM_ANGULARPATH,
                   COALESCE(MD.MNUM_ANGICON, '')     AS MNUM_ANGICON,
                   COALESCE(MD.MNUM_ISREACT, 'N')    AS MNUM_ISREACT,
                   COALESCE(MD.MNUM_REACTPATH, '')   AS MNUM_REACTPATH,
                   COALESCE(MD.MNUM_RNICON, '')      AS MNUM_RNICON,
                   COALESCE(MD.MNUM_ICON, '')        AS MNUM_ICON
            FROM ADM_VW_MENUMST_BACKUP_TESTING M
            JOIN ADM_VW_MENUUSERRIGHTS R ON M.MNUM_MENUNUMBER = R.ARML_MENUID
            LEFT JOIN ADM_TL_MENUMST_DETAIL MD ON MD.MNUM_MENUNUMBER = M.MNUM_MENUNUMBER
            """;

    String MENU_ORDER = """
            ORDER BY M.MNUM_MENULEVEL, M.MNUM_SORTNUMBER, M.MNUM_PARENTNUMBER, M.MNUM_MENUNUMBER
            """;

    @Query(value = MENU_SELECT + """
            WHERE R.ARUL_USERID = :userId AND M.MNUM_MENULEVEL = 1
            """ + MENU_ORDER, nativeQuery = true)
    List<Map<String, Object>> getRootMenus(@Param("userId") String userId);

    @Query(value = MENU_SELECT + """
            WHERE R.ARUL_USERID = :userId
              AND M.MNUM_PARENTNUMBER <> M.MNUM_MENUNUMBER
              AND M.MNUM_PARENTNUMBER = CAST(:parentNumber AS numeric)
            """ + MENU_ORDER, nativeQuery = true)
    List<Map<String, Object>> getChildMenus(@Param("parentNumber") String parentNumber,
                                            @Param("userId") String userId);

    @Query(value = MENU_SELECT + """
            WHERE R.ARUL_USERID = :userId
            """ + MENU_ORDER, nativeQuery = true)
    List<Map<String, Object>> getAllUserMenus(@Param("userId") String userId);
}