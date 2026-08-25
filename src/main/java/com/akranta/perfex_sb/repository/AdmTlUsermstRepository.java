
package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.dto.UserLoginDetailsDto;
import com.akranta.perfex_sb.model.AdmTlUsermst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface AdmTlUsermstRepository extends JpaRepository<AdmTlUsermst, String> {

  @Query(value = """
                        SELECT  * FROM ADM_TL_USERMST  WHERE UPPER(USRM_LOGINID) = UPPER(:loginid)
            """, nativeQuery = true)
        AdmTlUsermst findByLoginid(@Param("loginid")String loginid);

        AdmTlUsermst findByKeyid(String keyid);

        @Query(value = """
                        SELECT
                            USRM.USRM_LOGINID AS loginId,
                            USRM.USRM_USERNAME AS userName,
                            EMPM.EMPM_NAME AS employeeName,
                            DEPT.DEPT_NAME AS deptName,
                            DESG.DESG_NAME AS designation,
                            USRM.USRM_PASSWORD AS password,
                            LGFR.LGFR_DEFSYSPASSWORD AS defaultpwd
                        FROM ADM_TL_USERMST USRM
                        JOIN GEN_TL_EMPLOYEEMST EMPM ON EMPM.EMPM_KEYID = USRM.USRM_CCNO
                        LEFT JOIN GEN_TL_DEPARTMENTMST DEPT ON EMPM.EMPM_DEPARTMENTID = DEPT.DEPT_KEYID
                        LEFT JOIN GEN_TL_DESIGNATIONMST DESG ON EMPM.EMPM_DESIGNATIONID = DESG.DESG_KEYID
                        JOIN ADM_TL_LOGINFRAMEWORK LGFR ON TRUE
                        WHERE UPPER(USRM.USRM_KEYID) = UPPER(:userKeyId)
                        """, nativeQuery = true)
        List<UserLoginDetailsDto> findUserDetails(@Param("userKeyId") String userKeyId);

        // Swetha - for User Creation - Role
        @Query(value = """
                        SELECT
                        '1' AS roleid,
                        '2' AS rolename
                        UNION ALL
                        SELECT
                        arul.ARUL_ROLEID AS roleid,
                        role.ROLE_NAME AS rolename
                        FROM ADM_TL_ROLEMST role
                        JOIN ADM_TL_USER_ROLE_LINK arul
                          ON arul.ARUL_ROLEID = role.ROLE_KEYID
                        WHERE arul.ARUL_USERID = :userId
                        """, nativeQuery = true)
        List<Map<String, Object>> findRolesByUserId(@Param("userId") String userId);

        @Query(value = "SELECT * FROM ADM_TL_USERMST WHERE UPPER(USRM_LOGINID) = UPPER(?1)", nativeQuery = true)
        AdmTlUsermst findByLoginIdIgnoreCase(String loginId);

        @Modifying
        @Transactional
        @Query(value = "UPDATE ADM_TL_PWDHISTORY " +
                        "SET PWDH_PASSWORDNO = PWDH_PASSWORDNO + 1 " +
                        "WHERE PWDH_USERID = :userId", nativeQuery = true)
        int updatePasswordHistory(@Param("userId") String userId);

        @Query(value = """
                        SELECT COALESCE(MAX(PWDH_PASSWORDNO), 0) + 1
                        FROM ADM_TL_PWDHISTORY
                        WHERE PWDH_USERID = :userId
                        """, nativeQuery = true)
        Integer getNextPasswordNo(@Param("userId") String userId);

        @Query(value = """
                        SELECT LGFR_PASSHISTORYREMEMBER
                        FROM ADM_TL_LOGINFRAMEWORK
                        WHERE LGFR_ACTIVE = 'Y'
                        """, nativeQuery = true)
        Integer getPasswordHistoryRememberCount();


        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM ADM_TL_PWDHISTORY
                        WHERE PWDH_PASSWORDNO > :rememberValue
                        AND PWDH_USERID = :userId
                        """, nativeQuery = true)
        int deleteOldPasswordHistory(
                        @Param("rememberValue") Integer rememberValue,
                        @Param("userId") String userId);

}