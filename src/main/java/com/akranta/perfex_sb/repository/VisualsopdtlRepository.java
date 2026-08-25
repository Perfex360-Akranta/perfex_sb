package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.Visualsopdtl;

import jakarta.transaction.Transactional;

public interface VisualsopdtlRepository extends JpaRepository<Visualsopdtl,String>{

    //getting count to avoid dulpicate
    @Query(
    value = """
        SELECT COUNT(*)
        FROM JHA_TL_VISUALSOPDTL
        WHERE VSOD_INSTRUCTION = :instruction
          AND VSOD_VSOM_KEYID = :vsomKeyid
          AND VSOD_KEYPOINT = :keypoint
        """,
    nativeQuery = true
)
int checkDuplicate(
    @Param("instruction") String instruction,
    @Param("vsomKeyid") String vsomKeyid,
    @Param("keypoint") String keypoint
);


//fectching detail table by keyid
@Query(value = """
        select * from jha_tl_visualsopdtl where vsod_keyid = :keyid 
        """, nativeQuery = true)
       Visualsopdtl findbykeyid(@Param("keyid") String keyid);


     

    //deleting the detail record by keyid   
    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM jha_tl_visualsopdtl WHERE vsod_keyid = :keyid",
        nativeQuery = true
    )
    int deleteByKeyId(@Param("keyid") String keyid);  


    //delete the detail record by refkeyid including image
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM gen_tl_allmoduleimgfile
        WHERE imfl_refkeyid = :keyId
        """, nativeQuery = true)
    int deleteByRefKeyId(@Param("keyId") String keyId);


    //fetching the all the detail records for particluar master key id including image 

//     @Query(value = """
//     SELECT
//         'vsod_keyid' AS VSOD_KEYID,
//         'Instruction' AS INSTRUCTION,
//         'Key Point' AS KEYPOINT,
//         'Importance Of Key Point' AS IMPORTANCEOFKEYPOINT,
//         'Tool Used' AS TOOLUSED,
//         'Image' AS IMAGE,
//         'PPE' AS PPE,
//         'PPE ?' AS PPECOUNT,
//         'vsodid' AS VSODID
//     UNION ALL
//     SELECT
//         d.VSOD_KEYID,
//         d.VSOD_INSTRUCTION,
//         d.VSOD_KEYPOINT,
//         d.VSOD_IMPORTANCEOFKEYPOINT,
//         d.VSOD_TOOLUSED,
//         i.IMFL_FILENAME,
//         d.VSOD_IMGPPE,
//         CASE
//             WHEN d.VSOD_IMGPPE = '{}' THEN 'No'
//             ELSE 'Yes'
//         END,
//         ''
//     FROM JHA_TL_VISUALSOPDTL d
//     LEFT JOIN GEN_TL_ALLMODULEIMGFILE i
//            ON d.VSOD_KEYID = i.IMFL_REFKEYID
//     WHERE d.VSOD_VSOM_KEYID = :vsomKeyId
//     ORDER BY d.VSOD_KEYID
// """, nativeQuery = true)
// List<Object[]> getVisualSopDetails(@Param("vsomKeyId") String vsomKeyId);


    @Query(
  value = """
    SELECT
            '1' AS vsod_keyid,
            '2' AS Instruction,
            '3' AS KeyPoint,
            '4' AS ImportanceOfKeyPoint,
            '5' AS ToolUsed,
            '6' AS Image,
            '7' AS PPE,
            '8' AS PPEcount,
            '9' AS vsodid
    UNION ALL 
    SELECT
            'vsod_keyid' AS vsod_keyid,
            'Instruction' AS Instruction,
            'Key Point' AS KeyPoint,
            'Importance Of Key Point' AS ImportanceOfKeyPoint,
            'Tool Used' AS ToolUsed,
            'Image' AS Image,
            'PPE' AS PPE,
            'PPE ?' AS PPEcount,
            'vsodid' AS vsodid
        UNION ALL
        SELECT *
        FROM (
            SELECT
                vsod_keyid,
                vsod_instruction AS Instruction,
                vsod_keypoint AS KeyPoint,
                vsod_importanceofkeypoint AS ImportanceOfKeyPoint,
                vsod_toolused AS ToolUsed,
                imfl_filename AS Image,
                vsod_imgppe AS PPE,
                CASE
                    WHEN vsod_imgppe = '{}' THEN 'No'
                    ELSE 'Yes'
                END AS PPEyes,
                '' AS vsodid
            FROM jha_tl_visualsopdtl
            LEFT JOIN gen_tl_allmoduleimgfile
                ON vsod_keyid = imfl_refkeyid
            WHERE  VSOD_VSOM_KEYID = :keyId
            ORDER BY vsod_keyid
        ) subquery
  """,
  nativeQuery = true
)
List<Map<String,Object>> getVisualSopDetails(@Param("keyId") String keyId);

//delete detail table records by using master keyid
@Modifying
@Transactional
@Query(
    value = "DELETE FROM jha_tl_visualsopdtl WHERE VSOD_VSOM_KEYID = :masterkeyid",
    nativeQuery = true
)
int deletebymasterkeyid(@Param("masterkeyid") String masterkeyid);




    



}
