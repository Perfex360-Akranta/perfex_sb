package com.akranta.perfex_sb.service;

import java.util.List;

import com.akranta.perfex_sb.dto.AttendanceFilter;
import com.akranta.perfex_sb.model.EntTlTragcalmst;
import com.akranta.perfex_sb.model.EntTlTrgCalSession;
import com.akranta.perfex_sb.model.EntTlTrgFaculty;
import com.akranta.perfex_sb.model.EntTlTtgCalEmpatScore;
import com.akranta.perfex_sb.model.EntTlTrgCalUnqp;

public interface GridBasedTrainingCalendarService {

    List<EntTlTragcalmst> saveOrUpdate(List<EntTlTragcalmst> masters) throws Exception;

    List<EntTlTragcalmst> create(List<EntTlTragcalmst> masters) throws Exception;

    List<EntTlTragcalmst> update(List<EntTlTragcalmst> masters) throws Exception;

    EntTlTragcalmst getById(String etcmKeyid);

    void deleteById(String etcmKeyid);

     String getAssesType(String etcmKeyid) throws Exception;

     
    String checkAssessmentCompleted(String etcmKeyid) throws Exception;
    
      List<String[]> gwtJHRoleUniquePos(String calendarFlid, String calendarId) throws Exception;

    EntTlTrgCalSession createSession(EntTlTrgCalSession session) throws Exception;
    
     List<String[]> getsession(String etcmKeyid) throws Exception;
         String getEmpAttendanceCount(String etcmKeyid) throws Exception;

    String getMaxMarks(String etcmKeyid) throws Exception;

    String getCutoff(String etcmKeyid) throws Exception;

    EntTlTrgCalSession updateSession(EntTlTrgCalSession session) throws Exception;
     String getEmpDataCount(String etcmKeyid) throws Exception;

    List<String[]> getNewUniqPosData(String etcmKeyid) throws Exception;

    int checkUniquePosition(String etcmKeyid, String roleKeyid, String uniqueKeyid);
     List<String[]> getAllUniqueEmployee(String etcmKeyid, java.util.Map<String, Object> payload) throws Exception;
List<String[]> getListTrgCalendarModify(java.util.Map<String,Object> payload) throws Exception;
List<String[]> getListTrgCalendarView(java.util.Map<String,Object> payload) throws Exception;

      List<String[]> getAllUniqueEmployeePopup(java.util.Map<String, Object> payload) throws Exception;

         List<EntTlTtgCalEmpatScore> createEmployeeAttendance(List<EntTlTtgCalEmpatScore> scores,
                                                         AttendanceFilter filter) throws Exception;
       List<String[]> getListTrgCalendar(String etcmKeyid, java.util.Map<String, Object> payload) throws Exception;
       
      List<String[]> getEmployeeAttendance(String etcmKeyid, java.util.Map<String, Object> payload) throws Exception;

    int checkSessionDuplicate(String etcmKeyid,
                              String sessionDate,
                              String fromTime,
                              String toTime,
                              String sessionId);

    String deleteDetailRecord(String keyId, String gridId, String trainingId) throws Exception;

    EntTlTrgFaculty createOrUpdateFaculty(EntTlTrgFaculty faculty) throws Exception;

    EntTlTrgCalUnqp createUniquePosition(EntTlTrgCalUnqp unique) throws Exception;

    EntTlTrgCalUnqp updateUniquePosition(EntTlTrgCalUnqp unique) throws Exception;

    List<String[]> getFaculty(String etcmKeyid) throws Exception;

}
