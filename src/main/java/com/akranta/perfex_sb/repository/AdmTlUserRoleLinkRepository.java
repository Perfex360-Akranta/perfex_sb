package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.AdmTlUserRoleLink;

public interface AdmTlUserRoleLinkRepository extends JpaRepository<AdmTlUserRoleLink, String> {
    List<AdmTlUserRoleLink> findByUserid(String userid);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ADM_TL_USER_ROLE_LINK " +
            "WHERE ARUL_ROLEID = :roleId " +
            "AND ARUL_USERID = :userId", nativeQuery = true)
    void deleteByRoleIdAndUserId(@Param("roleId") String roleId,
            @Param("userId") String userId);

    @Modifying
        @Transactional
        @Query(value = """
                        INSERT INTO public.adm_tl_user_role_link
                        (arul_userid, arul_roleid, arul_active,
                         arul_createdby, arul_createdon, arul_modifiedon)
                        VALUES
                        (:userid, :roleid, :active,
                         :createdby, :createdon, :modifiedon)
                        """, nativeQuery = true)
        void insertUserRole(
                        @Param("userid") String userid,
                        @Param("roleid") String roleid,
                        @Param("active") Character active,
                        @Param("createdby") String createdby,
                        @Param("createdon") LocalDateTime createdon,
                        @Param("modifiedon") LocalDateTime modifiedon);
}
