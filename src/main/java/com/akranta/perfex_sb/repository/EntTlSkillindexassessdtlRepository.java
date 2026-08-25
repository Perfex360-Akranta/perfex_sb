package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.EntTlSkillindexassessdtl;

public interface EntTlSkillindexassessdtlRepository extends JpaRepository<EntTlSkillindexassessdtl, String> {

        /* A. SELECT SIAD_KEYID */

        @Query("""
                SELECT CASE WHEN COUNT(e) > 0
                THEN true ELSE false END
                FROM EntTlSkillindexassessdtl e
                WHERE e.siam_keyid = :siamKeyId
                AND e.reviewhalf = :reviewHalf
            """)
    boolean existsCurrentHalfYear(
            @Param("siamKeyId") String siamKeyId,
            @Param("reviewHalf") String reviewHalf);

        @Query(value = """
                            SELECT SIAD_KEYID
                            FROM ENT_TL_SKILLINDEXASSESSDTL
                            WHERE SIAD_SIAM_KEYID = :siamKeyid
                              AND SIAD_EMPM_KEYID = :empmKeyid
                              AND SIAD_REVIEWID = :reviewId
                        """, nativeQuery = true)
        String findDetailKeyId(
                        @Param("siamKeyid") String siamKeyid,
                        @Param("empmKeyid") String empmKeyid,
                        @Param("reviewId") String reviewId);

        /* B. DELETE */

        @Modifying
        @Query(value = """
                            DELETE FROM ENT_TL_SKILLINDEXASSESSDTL
                            WHERE SIAD_SIAM_KEYID = :siamKeyid
                              AND SIAD_EMPM_KEYID = :empmKeyid
                              AND SIAD_REVIEWID = :reviewId
                        """, nativeQuery = true)
        void deleteExistingDetail(
                        @Param("siamKeyid") String siamKeyid,
                        @Param("empmKeyid") String empmKeyid,
                        @Param("reviewId") String reviewId);

        @Modifying
        @Query(value = """
                            UPDATE ENT_TL_SKILLINDEXASSESSDTL A
                            SET SIAD_TOTAL = (
                                SELECT SUM(B.SIAD_SCORE)
                                FROM ENT_TL_SKILLINDEXASSESSDTL B
                                WHERE B.SIAD_SIAM_KEYID = A.SIAD_SIAM_KEYID
                                  AND B.SIAD_CRITERIAID = A.SIAD_CRITERIAID
                                  AND B.SIAD_EMPM_KEYID = A.SIAD_EMPM_KEYID
                            )
                            WHERE A.SIAD_SIAM_KEYID = :siamKeyid
                        """, nativeQuery = true)
        void updateTotal(@Param("siamKeyid") String siamKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                            DELETE FROM ENT_TL_SKILLINDEXASSESSDTL
                            WHERE SIAD_EMPM_KEYID IN (
                                SELECT EMP FROM (
                                    SELECT
                                        D.SIAD_EMPM_KEYID AS EMP,
                                        SUM(D.SIAD_SCORE) AS SCORE
                                    FROM ENT_TL_SKILLINDEXASSESSDTL D,
                                         ENT_TL_SKILLINDEXASSESSMST M
                                    WHERE M.SIAM_KEYID = D.SIAD_SIAM_KEYID
                                      AND M.SIAM_FLID = :flId
                                      AND M.SIAM_UNIQUEPOSID = :uniquePosId
                                      AND M.SIAM_REVIEWDATE = :reviewDate
                                    GROUP BY D.SIAD_EMPM_KEYID
                                ) T
                                WHERE SCORE <= 0
                            ) and SIAD_SIAM_KEYID = :siamKeyid
                        """, nativeQuery = true)
        int deleteEmployeesWithZeroScore(
                        @Param("flId") String flId,
                        @Param("uniquePosId") String uniquePosId,
                        @Param("reviewDate") LocalDateTime reviewDate,
                    @Param("siamKeyid") String siamKeyid);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE ENT_TL_SKILLINDEXASSESSDTL siad
            SET SIAD_ACTIVE = 'N'
            FROM ENT_TL_SKILLINDEXASSESSMST siam
            WHERE siam.siam_keyid = siad.siad_siam_keyid
              AND siad.siad_empm_keyid = :empmKeyid
               AND (
                siam.siam_flid <> :flid
                OR siam.siam_uniqueposid <> :uniqueposid
              ) and siad.siad_review_half = :reviewHalf 
              AND siad.siad_active = 'Y'
            """, nativeQuery = true)
    int inactivateExistingAssessment(
            @Param("empmKeyid") String empmKeyid,
            @Param("reviewHalf") String reviewHalf,
            @Param("flid") String flid,
            @Param("uniqueposid") String uniqueposid);

@Query(value = """
            Select distinct siam.siam_flid
            FROM ENT_TL_SKILLINDEXASSESSMST siam 
            join ENT_TL_SKILLINDEXASSESSDTL siad on siam.siam_keyid = siad.siad_siam_keyid
            WHERE  siad.siad_empm_keyid = :empmKeyid
               AND (
                siam.siam_flid <> :flid
                OR siam.siam_uniqueposid <> :uniqueposid
              ) and siad.siad_review_half = :reviewHalf
              AND siad.siad_active = 'Y'  limit 1
            """, nativeQuery = true)
    String inactivateExistingAssessmentFlid(
            @Param("empmKeyid") String empmKeyid,
            @Param("reviewHalf") String reviewHalf,
            @Param("flid") String flid,
            @Param("uniqueposid") String uniqueposid);


@Modifying
  @Query(value = """
      UPDATE ENT_TL_SKILLINDEXASSESSMST MST
      SET
          SIAM_TEMPFILED1 = SRC.FINAL_COUNT::TEXT,
          SIAM_MODIFIEDON = NOW()
      FROM (
          SELECT
              COALESCE(LC.FLID, AR.FLID) AS FLID,
              COALESCE(LC.TRADE, AR.TRADE) AS TRADE,
              (COALESCE(LC.LIVE_COUNT, 0) + COALESCE(AR.ASSESSED_REMOVED_COUNT, 0)) AS FINAL_COUNT
          FROM (
              SELECT
                  VW.FLID,
                  CASE VW.TRDM_CLASSIFICATION
                      WHEN 'M' THEN 'ETPM0001'
                      WHEN 'P' THEN 'ETPM0002'
                      WHEN 'S' THEN 'ETPM0003'
                      WHEN 'T' THEN 'ETPM0004'
                  END AS TRADE,
                  COUNT(DISTINCT VW.FRT_EMPM_KEYID) AS LIVE_COUNT
              FROM GEN_VW_FNLN_TRADEEMPCOUNT VW
              JOIN GEN_TL_EMPLOYEEMST E ON E.EMPM_KEYID = VW.FRT_EMPM_KEYID
              WHERE VW.FLID = :flid
                AND E.EMPM_EMPLOYEETYPE <> 'M'
                AND E.EMPM_ACTIVE = 'Y'
              GROUP BY VW.FLID, VW.TRDM_CLASSIFICATION
          ) LC
          FULL OUTER JOIN (
              SELECT
                  SIAM.SIAM_FLID AS FLID,
                  SIAM.SIAM_UNIQUEPOSID AS TRADE,
                  COUNT(DISTINCT SIAD.SIAD_EMPM_KEYID) AS ASSESSED_REMOVED_COUNT
              FROM ENT_TL_SKILLINDEXASSESSMST SIAM
              JOIN ENT_TL_SKILLINDEXASSESSDTL SIAD ON SIAD.SIAD_SIAM_KEYID = SIAM.SIAM_KEYID
              LEFT JOIN GEN_VW_FNLN_TRADEEMPCOUNT VW
                  ON VW.FRT_EMPM_KEYID = SIAD.SIAD_EMPM_KEYID
                 AND VW.FLID = SIAM.SIAM_FLID
                 AND (
                      CASE VW.TRDM_CLASSIFICATION
                          WHEN 'M' THEN 'ETPM0001'
                          WHEN 'P' THEN 'ETPM0002'
                          WHEN 'S' THEN 'ETPM0003'
                          WHEN 'T' THEN 'ETPM0004'
                      END
                 ) = SIAM.SIAM_UNIQUEPOSID
              WHERE SIAM.SIAM_FLID = :flid
                AND VW.FRT_EMPM_KEYID IS NULL
                AND SIAD.SIAD_ACTIVE = 'Y'
                AND SIAD.SIAD_REVIEW_HALF = :reviewHalf
              GROUP BY SIAM.SIAM_FLID, SIAM.SIAM_UNIQUEPOSID
          ) AR ON AR.FLID = LC.FLID AND AR.TRADE = LC.TRADE
      ) SRC
      WHERE MST.SIAM_FLID = SRC.FLID
        AND MST.SIAM_UNIQUEPOSID = SRC.TRADE
        AND MST.SIAM_TEMPFILED1::INT <> SRC.FINAL_COUNT
        AND MST.SIAM_KEYID IN (                
            SELECT DISTINCT SIAD_SIAM_KEYID
            FROM ENT_TL_SKILLINDEXASSESSDTL
            WHERE SIAD_REVIEW_HALF = :reviewHalf 
        )
      """, nativeQuery = true)
  int updateSkillIndexCountTempField(@Param("flid") String flid,
      @Param("reviewHalf") String reviewHalf);

}
