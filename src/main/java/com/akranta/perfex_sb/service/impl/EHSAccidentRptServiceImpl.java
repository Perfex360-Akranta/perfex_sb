package com.akranta.perfex_sb.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.AssignResponsibilityDto;
import com.akranta.perfex_sb.dto.IncidentApprovalDto;
import com.akranta.perfex_sb.dto.IncidentClosureDto;
import com.akranta.perfex_sb.dto.IncidentVerifyDto;
import com.akranta.perfex_sb.dto.SheTlIncidentEmployeenDto;
import com.akranta.perfex_sb.dto.UpdateIncidentDto;
import com.akranta.perfex_sb.model.GenTlMommst;
import com.akranta.perfex_sb.model.SheTlIncidentEmployeenew;
import com.akranta.perfex_sb.model.SheTlIncidentmstnew;
import com.akranta.perfex_sb.repository.SheTlIncidentEmployeenewRepository;
import com.akranta.perfex_sb.repository.SheTlIncidentmstnewRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.EHSAccidentRptService;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.validation.constraints.Null;


@Service
public class EHSAccidentRptServiceImpl implements EHSAccidentRptService {
    
    @Autowired
    private SheTlIncidentmstnewRepository repository;

    @Autowired
    private SheTlIncidentEmployeenewRepository employeeRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(EHSAccidentRptServiceImpl.class);

    private static final String SEQ_IDENTIFIER = "SHE_TL_INCIDENTMSTNEW";
    private static final int KEY_LENGTH = 11;
    private static final String PREFIX = "INN";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_EMP = "SHE_TL_INCIDENT_EMPLOYEENEW";
    private static final int KEY_LENGTH_EMP = 10;
    private static final String PREFIX_EMP = "SIE";
    private static final String DATE_FORMAT_EMP = null;
    private static final String FORMAT_RESET_EMP = "N";

    @Override
    public SheTlIncidentmstnew save(SheTlIncidentmstnew sheTlIncidentmstnew) throws Exception {
        if(!ValidationUtil.isValidKeyId(sheTlIncidentmstnew.getKeyid())){
            String newKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, 
                    FORMAT_RESET, DATE_FORMAT);

            if (newKeyId == null || newKeyId.trim().isEmpty()){
                logger.info("not able to generate");
                throw new RuntimeException("Failed to generate Master Key ID");

            }

            logger.info("Genrated new Key ID: {}",newKeyId);
            sheTlIncidentmstnew.setKeyid(newKeyId);
        } else {
            if (repository.existsById(sheTlIncidentmstnew.getKeyid())) {
                return repository.save(sheTlIncidentmstnew);
            }
        }
        return repository.save(sheTlIncidentmstnew);
    }

    @Override
    public SheTlIncidentEmployeenew save(SheTlIncidentEmployeenDto sheTlIncidentEmployeenDto) throws Exception {
       
        SheTlIncidentEmployeenew employee = sheTlIncidentEmployeenDto.getSheTlIncidentEmployeenew();

        if(!ValidationUtil.isValidKeyId(employee.getKeyid())){
            String newKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_EMP, KEY_LENGTH_EMP, PREFIX_EMP, 
                    FORMAT_RESET_EMP, DATE_FORMAT_EMP);

            if (newKeyId == null || newKeyId.trim().isEmpty()){
                logger.info("not able to generate");
                throw new RuntimeException("Failed to generate Master Key ID");

            }

            logger.info("Genrated new Key ID: {}",newKeyId);
            employee.setKeyid(newKeyId);
        } else {
            if (repository.existsById(employee.getKeyid())) 
                {
                int injuryCount = employeeRepository.getInjuryCount(employee.getSinc_keyid());
                String count = Integer.toString(injuryCount);
                if(ValidationUtil.isValidKeyId(count))
                    {
                        int update = employeeRepository.updateNoOfEmployees(injuryCount, employee.getSinc_keyid());

                    }
                 return employeeRepository.save(employee);
            }
        }
        int injuryCount = employeeRepository.getInjuryCount(employee.getSinc_keyid());
                String count = Integer.toString(injuryCount);
                if(ValidationUtil.isValidKeyId(count))
                    {
                        int update = employeeRepository.updateNoOfEmployees(injuryCount, employee.getSinc_keyid());
                        
                    }
        return employeeRepository.save(employee);

     }
    @Override
      public SheTlIncidentmstnew getSheTlIncidentmstnewById(String keyId) {

        if (keyId == null || keyId.trim().isEmpty()) {
            logger.warn("Invalid keyId provided: {}", keyId);
            return null;
        }

        SheTlIncidentmstnew mst = repository.findBySinmKeyIdNative(keyId);
        if (mst != null) {
            logger.debug("Found SHETL record for keyId: {}", keyId);
            return mst;
        } else {
            logger.warn("No SHETL record found for keyId: {}", keyId);
            return null;
        }

    }

    @Override
    public int updateIncident(UpdateIncidentDto incidentDto) {

        // Handle null investigation remarks
        if (incidentDto.getInvstremarks() == null || incidentDto.getInvstremarks().isEmpty()) {
            incidentDto.setInvstremarks("-");
        }

        // Handle null root cause due to
        if (incidentDto.getRootcausedueto() == null) {
            incidentDto.setRootcausedueto("-");
        }

        return repository.updateIncident(
                incidentDto.getExact_desc(),
                incidentDto.getExact_type(),
                incidentDto.getAnalysed_by(),
                incidentDto.getAnalysed_date(),
                incidentDto.getAnalysed_shift(),
                incidentDto.getRptrecommendations(),
                incidentDto.getInvestigation(),
                incidentDto.getInvstremarks(),
                incidentDto.getRootcausedueto(),
                incidentDto.getKeyid()
        );
    }
 
    @Override
    public int assignResponsibility(AssignResponsibilityDto dto) {
        // Call repository method using DTO fields
        return repository.assignResponsibility(
                dto.getSINM_RESPONSIBILITY(),
                dto.getSINM_RESPONSERMRKS(),
                dto.getSINM_TARGETDATE(),
                dto.getSINM_KEYID()
        );
    }

    @Override
    public int updateIncidentClosure(IncidentClosureDto dto) {

        // Call repository method using DTO fields
        return repository.updateIncidentClosure(
                dto.getCompletedBy(),
                dto.getDateCompleted(),
                dto.getRevisedDate(),
                dto.getStatus(),
                dto.getActionTaken(),
                dto.getCompletedRemarks(),
                dto.getKeyId()
        );
    }

    @Override
    public List<Map<String, Object>> getActionPlanIds(String keyid) {

        List<Object[]> results = repository.findActionPlanIds(keyid);

        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("aplmKeyId", row[0]);
            map.put("apldKeyId", row[1]);
            response.add(map);
        }

        return response;
    }

     
    @Override
    @Transactional
    public String DeleteIncident(String masterRefId) 
    {
        int deleteIncident = repository.deleteByKeyId(masterRefId);
        int deleteEmployee = employeeRepository.deleteByKeyId(masterRefId);

        String aplmKeyId = repository.findAplmKeyidByMasterRefId(masterRefId);

        if(aplmKeyId != null)
            {
                String mstKeyId = repository.findKeyIdByAplmKeyId(aplmKeyId);
                String dtlKeyid = repository.findKeyIdByMasterRefId(masterRefId);
                int DeleteDtl = repository.deleteActionPlanDtl(dtlKeyid); 
                int DeleteMst = repository.deleteActionPlanMst(mstKeyId);
                 
            }
        return "Deleted";
       
    }

    @Override
    public String verifyIncident(IncidentVerifyDto dto) {

        int result = repository.updateIncidentVerification(
                dto.getVerifiedBy(),
                dto.getDateVerified(),
                dto.getVerifyStatus(),
                dto.getVerifyRemarks(),
                dto.getKeyId()
        );

        if(result > 0) {
            return "Incident verified successfully";
        } else {
            return "Incident verification failed";
        }
    }


    @Override
    public boolean approveIncident(IncidentApprovalDto dto) {
        int updatedRows = repository.updateIncidentApproval(
                dto.getApprovalBy(),
                dto.getApprovalDate(),
                dto.getApprovalStatus(),
                dto.getApprovalRemarks(),
                dto.getKeyId()
        );
        return updatedRows > 0;
    }
}
