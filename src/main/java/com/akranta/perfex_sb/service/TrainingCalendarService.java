package com.akranta.perfex_sb.service;

import java.util.List;

import com.akranta.perfex_sb.dto.AttendanceFilter;
import com.akranta.perfex_sb.model.EntTlTragcalmst;
import com.akranta.perfex_sb.model.EntTlTrgCalSession;
import com.akranta.perfex_sb.model.EntTlTrgCalUnqp;
import com.akranta.perfex_sb.model.EntTlTtgCalEmpatScore;
import com.akranta.perfex_sb.model.EntTlTrgCalEmp;
import com.akranta.perfex_sb.model.EntTlTrgFaculty;

public interface TrainingCalendarService {

    EntTlTragcalmst create(EntTlTragcalmst master,
                           EntTlTrgCalSession session,
                           EntTlTrgFaculty faculty,
                           List<EntTlTrgCalUnqp> uniquePositions) throws Exception;

    EntTlTragcalmst update(EntTlTragcalmst master,
                           EntTlTrgCalSession session,
                           EntTlTrgFaculty faculty,
                           List<EntTlTrgCalUnqp> uniquePositions) throws Exception;

    void delete(String etcmKeyid) throws Exception;

    void deleteSession(String etcmKeyid, String sessionKeyid);

    int checkSessionDuplicate(String etcmKeyid, String sessionDate, String fromTime, String toTime, String sessionId );

    List<String[]> getsession(String etcmKeyid) throws Exception;

    List<String[]> getNewUniqPosData(String etcmKeyid) throws Exception;

    List<String[]> gwtJHRoleUniquePos(String calendarFlid, String calendarId) throws Exception;

    List<String[]> getAllUniqueEmployee(String etcmKeyid, java.util.Map<String, Object> payload) throws Exception;
    
    List<String[]> getAllUniqueEmployeePopup(java.util.Map<String, Object> payload) throws Exception;

    List<String[]> getEmployeeAttendance(String etcmKeyid, java.util.Map<String, Object> payload) throws Exception;

    String deleteDetailRecord(String keyId, String gridId, String trainingId);

    EntTlTragcalmst getById(String etcmKeyid);

    String getEmpDataCount(String etcmKeyid) throws Exception;

    String getEmpAttendanceCount(String etcmKeyid) throws Exception;

    String getMaxMarks(String etcmKeyid) throws Exception;

    String getCutoff(String etcmKeyid) throws Exception;

    String getAssesType(String etcmKeyid) throws Exception;

    List<String[]> getFaculty(String etcmKeyid) throws Exception;

    int checkUniquePosition(String etcmKeyid, String roleKeyid, String uniqueKeyid);

    List<String[]> checkJHForRole(String roleKeyid);

    List<EntTlTrgCalUnqp> createMultipleUnique(List<EntTlTrgCalUnqp> uniqueList) throws Exception;

    EntTlTrgCalEmp createEmployee(EntTlTrgCalEmp employee) throws Exception;

    List<EntTlTrgCalEmp> createSessionEmployee(List<EntTlTrgCalEmp> employees) throws Exception;

    List<EntTlTtgCalEmpatScore> createEmployeeAttendance(List<EntTlTtgCalEmpatScore> scores,
                                                         AttendanceFilter filter) throws Exception;

    String checkAssessmentCompleted(String etcmKeyid) throws Exception;

    public String resetAssessmentForMaintenance(String etcmKeyid) throws Exception;

    public String getAssessmentComStatus(String etcmKeyid) throws Exception;
    
}
