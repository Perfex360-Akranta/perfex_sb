package com.akranta.perfex_sb.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.MomMstAndDtlDto;
import com.akranta.perfex_sb.dto.momActionPlanDto;
import com.akranta.perfex_sb.model.GenTlActionPlanMst;
import com.akranta.perfex_sb.model.GenTlMommst;
import com.akranta.perfex_sb.model.GenTlVisitors;

public interface NewMoMeetingService {

        ResponseEntity<MomMstAndDtlDto> saveMOM(MomMstAndDtlDto momMstAndDtlDto) throws Exception;

        ResponseEntity<MomMstAndDtlDto> saveAttendance(
                        MomMstAndDtlDto momMstAndDtlDto)
                        throws Exception;

        List<Map<String, Object>> mommstRecall(LocalDate mstDate, String flid, String shift, String type,String pillarId);

        // [MOD260344, OTHERS, JH, DISCUSSION ON SPRINGBOOT, , , , , , REMARKS, TGT002,
        // OTH, DISCUSSION ON SPRINGBOOT, REMARKS]

        // List<Map<String, Object>> newMomGrid(String KeyId, String flid, String momdate, String shift, String type);

        List<Map<String, Object>> momGridVisitor(String masterKeyid, String flid, String date, String shift,
                        String type,
                        String pillarid, String recall);

        GenTlVisitors saveVisitors(GenTlVisitors genTlVisitors) throws Exception;

        GenTlMommst getMommstById(String keyId);

        List<Map<String, Object>> fillagendadata(String date, String location);

        void deleteDetails(String mstKeyId, String actionPlanMstId);

        void deleteVisitor(String visiKeyid);

        GenTlActionPlanMst createActionPlan(momActionPlanDto dto) throws Exception;

        GenTlActionPlanMst updateActionPlan(momActionPlanDto dto) throws Exception;
        List<Map<String, Object>> newMomGrid(String KeyId, String flid, String momdate, String shift, String type,
                        String pillarid);
	
        
                        String getFild(String originalId);

}
