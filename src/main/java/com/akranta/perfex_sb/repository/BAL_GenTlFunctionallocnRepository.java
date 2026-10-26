package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BAL_GenTlFunctionallocn;

public interface BAL_GenTlFunctionallocnRepository extends JpaRepository<BAL_GenTlFunctionallocn, String> 
{
    @Query(
    value = """
        SELECT FNLN_ELEMENTID
        FROM GEN_TL_FUNCTIONALLOCN
        WHERE FNLN_ORIGINALID = :originalId
        """,
    nativeQuery = true
)
String findElementIdsByOriginalId(
    @Param("originalId") String originalId
);
    

@Query(
        value = """
            SELECT FNLN_KEYID
            FROM GEN_TL_FUNCTIONALLOCN
            WHERE FNLN_ORIGINALID = :originalId
            """,
        nativeQuery = true
    )
    String findKeyidByOriginalId(
        @Param("originalId") String originalId
    );

    @Query(
    value = """
        SELECT COUNT(*)
        FROM GEN_TL_FUNCTIONALLOCN
        WHERE FNLN_ORIGINALID = :originalId
        """,
    nativeQuery = true
)
long countByOriginalId(@Param("originalId") String originalId);

@Modifying
@Query(
    value = """
        UPDATE GEN_TL_FUNCTIONALLOCN
        SET FNLN_ACTIVE = 'N'
        WHERE FNLN_ORIGINALID = :originalId
        """,
    nativeQuery = true
)
//void inactivateByOriginalId(@Param("originalId") String originalId);
int inactivateByOriginalId(@Param("originalId") String originalId);
}
