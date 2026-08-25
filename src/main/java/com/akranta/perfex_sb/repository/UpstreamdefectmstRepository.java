package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.Upstreamdefectmst;

import jakarta.transaction.Transactional;

public interface UpstreamdefectmstRepository extends JpaRepository<Upstreamdefectmst, String> {

    @Query(
  value = """
    SELECT fa.FNLN_ELEMENTID,
           fa.FNLN_KEYID,
           rm.ROLE_LEVEL,
           rm.ROLE_NAME,
           rm.ROLE_KEYID
    FROM GEN_TL_FUNCTIONALLOCN fa
    JOIN GEN_TL_FNLNROLETEAM frt
      ON fa.FNLN_KEYID = frt.FRT_FNLN_KEYID
    JOIN ADM_TL_ROLEMST rm
      ON frt.FRT_ROLE_KEYID = rm.ROLE_KEYID
    WHERE frt.FRT_EMPM_KEYID = :empId
      AND rm.ROLE_LEVEL = :loginlevel
      AND (:loginflid IS NULL OR frt.FRT_FNLN_KEYID = :loginflid)
  """,
  nativeQuery = true
)
List<Object[]> getElementIdNative(
        @Param("loginflid") String loginflid,
        @Param("loginlevel") double loginlevel,
        @Param("empId") String empId
);

@Query(
    value = "SELECT * FROM gen_tl_upstreamdefect_mst WHERE UPSM_KEYID = :keyid",
    nativeQuery = true
)
Upstreamdefectmst findByKeyid(@Param("UPSM_KEYID") String keyid);

//delete the master table
 @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM GEN_TL_UPSTREAMDEFECT_MST " +
                "WHERE UPSM_KEYID = :upsmKeyId",
        nativeQuery = true
    )
    void deleteMasterByKey(@Param("upsmKeyId") String upsmKeyId);

  

}
