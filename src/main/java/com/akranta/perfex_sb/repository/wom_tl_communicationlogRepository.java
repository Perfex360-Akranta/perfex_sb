package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.WomTlCommunicationlog;
import com.akranta.perfex_sb.model.WomTlCommunicationlog;
import java.util.List;
import java.util.Map;


public interface wom_tl_communicationlogRepository extends JpaRepository<WomTlCommunicationlog,String>{
     @Query(value = """
            SELECT WCML_KEYID, WCML_WONUMBER, TO_CHAR(WCML_DATE, 'DD-Mon-YYYY') AS WCML_DATE_STR,
                   TO_CHAR(WCML_DATE, 'HH24:MI') AS WCML_TIME_STR, WCML_COMMUNICATIONTEXT,
                   DEPT_NAME, EMPM_NAME
            FROM WOM_TL_COMMUNICATIONLOG
            LEFT JOIN GEN_TL_EMPLOYEEMST ON WCML_ENTEREDBY = EMPM_KEYID
            LEFT JOIN GEN_TL_DEPARTMENTMST ON WCML_LEVEL = DEPT_KEYID
            WHERE WCML_WONUMBER = :bdId
            """, nativeQuery = true)
    List<Map<String, Object>> getCommText(@Param("bdId") String bdId);
}
