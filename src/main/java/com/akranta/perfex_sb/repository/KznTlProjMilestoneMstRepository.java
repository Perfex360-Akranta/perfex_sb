package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.KznTlProjMilestoneMst;

public interface KznTlProjMilestoneMstRepository extends JpaRepository<KznTlProjMilestoneMst, String> {
    
    @Query(value = """
      SELECT '1' as keyid,'2' as Stages,'3' as Milestone,'4' as Description,'5' as Responsiblity,'6' as Status,'7' as Status,'8' as CompletedDate
			 union all
            SELECT 'Keyid' as keyid,'Stages','Milestone','Description','Responsiblity','Status','Completed by','Completed Date' 
			 union all select kmmm_keyid as keyid,CASE KMMM_STAGES   WHEN 'D' THEN 'Define' 
					    WHEN 'M' THEN 'Measure' 
					    WHEN 'A' THEN 'Analyse' 
					    WHEN 'I' THEN 'Improve' 						
                        WHEN 'C' THEN 'CONTROL' 
					    ELSE NULL 
					END,kmmd_milestone,kmmd_description,b.empm_name  as Responsiblity,
                    CASE KMMM_STATUS 
					    WHEN 'P' THEN 'Pending' 
					    WHEN 'W' THEN 'Work In Progress' 
					    WHEN 'C' THEN 'Completed' 
					    WHEN 'S' THEN 'Short Close' 
                     ELSE NULL END,a.empm_name,to_char(KMMM_TODATE,'dd-Mon-YYYY')
			 from KZN_TL_PROJ_MILESTONE_DTL JOIN KZN_TL_PROJ_MILESTONE_MST ON KMMM_KEYID = KMMD_KMMM_KEYID  					
              JOIN gen_tl_employeemst a ON a.empm_keyid= kmmm_empm_keyid 
              JOIN gen_tl_employeemst b ON b.empm_keyid = kmmd_empm_keyid 
              where kmmm_kzpm_keyid =:keyid 
              """, nativeQuery = true)
    List<Map<String, Object>> getProjectMileStones(@Param("keyid") String keyid);


	@Query(value = """
		 SELECT '1' AS KEYID, '2' as Assigend,'3' as selectval,'4' as Select ,'5' as Milestones,'6' as Description,'7' as TargetDate, 
'8' as temp1,'9' as RevisedDate, '10' as AssignedTo,'11' as Status, '12' as Remarks,'13' as Delete,'14' as History,'15' as status1,'16' AS KMMD_TEMPFIELD1, 0 AS DATAORDER 
UNION 
      SELECT 'keyid' AS KEYID, 'Assigend','selectval','Select','Milestones','Description','Target Date', 
' ','Revised date', 'Assigned To','Status', 'Remarks','Delete','History','status','milestone mst id' AS KMMD_TEMPFIELD1, 1 AS DATAORDER 
UNION 
SELECT KMMD.KMMD_KEYID AS KEYID, KMMD.EMPM_NAME, '', '', COALESCE(KMMD.KMMD_MILESTONE, M.MILM_NAME) AS KMMD_MILESTONE, 
COALESCE(KMMD.KMMD_DESCRIPTION, M.MILM_DESC) AS KMMD_DESCRIPTION,TO_CHAR(KMMD.KMMD_TARGETDATE, 'DD-Mon-YYYY'), '', '', 
KMMD.KMMD_EMPM_KEYID, KMMD.KMMD_STATUS, KMMD.KMMD_REMARKS,'','', 
CASE KMMD.STATUS 
	WHEN 'P' THEN 'Pending' 
	WHEN 'W' THEN 'Work In Progress' 
	WHEN 'C' THEN 'Completed' 
	WHEN 'S' THEN 'Short Close' 
END AS STATUS, 
M.MILM_KEYID AS KMMD_TEMPFIELD1,2 AS DATAORDER 
FROM( 
SELECT 
D.KMMD_KEYID,E.EMPM_NAME,D.KMMD_MILESTONE,D.KMMD_DESCRIPTION, D.KMMD_TARGETDATE,D.KMMD_EMPM_KEYID, 
D.KMMD_STATUS, D.KMMD_REMARKS,D.KMMD_STATUS AS STATUS,  D.KMMD_TEMPFIELD1 
 FROM KZN_TL_PROJ_MILESTONE_DTL AS D 
JOIN GEN_TL_EMPLOYEEMST AS E ON D.KMMD_EMPM_KEYID = E.EMPM_KEYID 
JOIN KZN_TL_PROJ_MILESTONE_MST AS MST ON MST.KMMM_KEYID = D.KMMD_KMMM_KEYID 
WHERE MST.KMMM_STAGES = 'M' AND D.KMMD_KMMM_KEYID = '' ) AS KMMD 
LEFT JOIN KZN_TL_MILESTONEMST AS M  ON M.MILM_KEYID = KMMD.KMMD_TEMPFIELD1 
WHERE M.MILM_TYPE = 'M'  			
Union SELECT kmmd_keyid AS keyid, empm_name, '', '',kmmd_milestone, kmmd_description,
TO_CHAR(kmmd_targetdate, 'DD-Mon-YYYY') AS kmmd_targetdate,'', '', kmmd_empm_keyid, kmmd_status, kmmd_remarks, '', '',
CASE kmmd_status
	WHEN 'P' THEN 'Pending' 
	WHEN 'W' THEN 'Work In Progress' 
	WHEN 'C' THEN 'Completed'
	WHEN 'S' THEN 'Short Close'
END AS status, KMMD_TEMPFIELD1, 3 AS dataorder 
FROM KZN_TL_PROJ_MILESTONE_DTL D JOIN GEN_TL_EMPLOYEEMST E ON D.kmmd_empm_keyid = E.empm_keyid 
JOIN KZN_TL_PROJ_MILESTONE_MST M ON M.kmmm_keyid = D.kmmd_kmmm_keyid 
WHERE M.kmmm_stages = :stage 
AND D.KMMD_TEMPFIELD1 = '-' AND D.kmmd_kmmm_keyid = :keyid 
ORDER BY dataorder, KMMD_TEMPFIELD1
              """, nativeQuery = true)
    List<Map<String, Object>> getProjectAllMileStones(@Param("stage") String stage,@Param("keyid") String keyid);


	@Modifying
    @Query(value = """
      Delete from KZN_TL_PROJ_MILESTONE_MST where KMMM_KEYID = :keyid 
              """, nativeQuery = true)
    int deleteMasterMilestone(@Param("keyid") String keyid);
}
