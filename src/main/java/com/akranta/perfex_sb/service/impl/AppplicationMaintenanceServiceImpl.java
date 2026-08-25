package com.akranta.perfex_sb.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.AppMainAbnDto;
import com.akranta.perfex_sb.dto.AppMainActPlanDto;
import com.akranta.perfex_sb.repository.ApplicationMaintananceRepo;
import com.akranta.perfex_sb.service.AppplicationMaintenanceService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class AppplicationMaintenanceServiceImpl implements AppplicationMaintenanceService {

    @Autowired
    private ApplicationMaintananceRepo repo;

    @Override
    public List<Map<String, Object>> getEmployeeList(String keyId) {
        return repo.getEmployeeList(keyId);
    }

    public void updateEmployeUserInactive(String keyId, String remarks) throws Exception {
        if (!ValidationUtil.isValidKeyId(keyId)) {
            throw new RuntimeException("No Key id");
        }

        repo.deactivateUser(keyId, remarks);
        repo.deactivateEmployee(keyId);

    }

    @Override
    public List<Map<String, Object>> getEmployeeLocation(String keyId, String location) {
        return repo.getEmployeeLocation(keyId, location);
    }

    public void updateEmployeeLocation(String keyId, String location) throws Exception {
        if (!ValidationUtil.isValidKeyId(keyId)) {
            throw new RuntimeException("No Key id");
        }

        repo.deleteTeamTradeLink(keyId);
        repo.deleteRoleTeam(keyId);
        repo.updateEmployeeLocation(keyId, location);

    }

    public void updateEmployeeActive(String keyId, String remarks, String validTill) throws Exception {
        if (!ValidationUtil.isValidKeyId(keyId)) {
            throw new RuntimeException("No Key id");
        }

        repo.activateEmployee(keyId);
        repo.activateUser(keyId, remarks, validTill);

    }

    @Override
    public String getFlid(String keyId) {
        return repo.findFlidByOriginalId(keyId);
    }

    @Override
    @Transactional
    public void deleteAbnormality(String abnKeyId) {

        repo.deleteAbnActionPlanDtl(abnKeyId);
        repo.deleteAbnActionPlanMst(abnKeyId);
        repo.deleteWhyWhyDtl(abnKeyId);
        repo.deleteWhyWhyMst(abnKeyId);
        repo.deleteDoneBy(abnKeyId);
        repo.deleteProblemAttBy(abnKeyId);
        repo.deleteEffectiveMst(abnKeyId);
        repo.deleteEffectiveDtl(abnKeyId);
        repo.deleteWhyWhyMst(abnKeyId);
        repo.deleteDocuments(abnKeyId);
        repo.deleteAbnormalityDtl(abnKeyId);
        repo.deleteAbnormality(abnKeyId);

    }

    @Override
    @Transactional
    public void deleteSuggestion(String kznKeyId) {
        repo.deleteSuggestion(kznKeyId);
    }

    @Override
    @Transactional
    public void deleteKaizen(String kznKeyId) {

        repo.deleteKznDocumentManager(kznKeyId);
        repo.deleteKznDocumentLayout(kznKeyId);
        repo.deleteKznActionPlanDtl(kznKeyId);
        repo.deleteKznActionPlanMst(kznKeyId);
        repo.deleteKznWorkflow(kznKeyId);
        repo.deleteKaizenHeader(kznKeyId);
        repo.deleteGraphData(kznKeyId);
        repo.deleteKaizenMaster(kznKeyId);

    }

    @Override
    @Transactional
    public void deleteActionPlan(String actPlanKeyId) {

        repo.deleteActionPlanDtl(actPlanKeyId);
        repo.deleteAbnActionPlanMst(actPlanKeyId);

    }

    @Override
    @Transactional
    public void deleteWhyWhy(String whywhyKeyId) {
        repo.deleteWhyWhyWhyDtl(whywhyKeyId);
        repo.deleteWhyProblemAttBy(whywhyKeyId);
        repo.deleteWhyDoneBy(whywhyKeyId);
        repo.deleteBdmWhyWhyMst(whywhyKeyId);
        repo.deleteWhyDocumentManager(whywhyKeyId);
        repo.deleteWhyDocumentLayout(whywhyKeyId);
        repo.deleteWhyWhyActionPlanDtl(whywhyKeyId);
        repo.deleteWhyWhyActionPlanMst(whywhyKeyId);
        repo.deleteOpl(whywhyKeyId);
        repo.deleteKaizenBank(whywhyKeyId);

    }

    @Override
    @Transactional
    public void deleteTrgCalendar(String trgKeyId) {

        repo.ETDeleteTrainingMaster(trgKeyId);
        repo.ETDeleteSession(trgKeyId);
        repo.ETDeleteUnqp(trgKeyId);
        repo.ETDeleteEmployee(trgKeyId);
        repo.ETDeleteEmpAtScore(trgKeyId);
        repo.ETDeleteQuad(trgKeyId);
        repo.ETDeleteFaculty(trgKeyId);
        repo.ETDeleteDocumentManager(trgKeyId);
        repo.ETDeleteDocumentManager(trgKeyId);

    }

    @Override
    @Transactional
    public void deleteLoss(String lossKeyId) {
        repo.deleteActionPlanDtlByLoss(lossKeyId);
        repo.deleteActionPlanMstByLoss(lossKeyId);
        repo.deleteLoss(lossKeyId);
    }

    @Override
    @Transactional
    public void abnormalityClosure(AppMainAbnDto dto) {

        String counterMeasure = dto.getCounterMeasure();
        LocalDateTime completedDate = dto.getCompletedDate();
        String completedBy = dto.getCompletedBy();
        String abnmKeyid = dto.getAbnmKeyid();
        String status = dto.getStatus();

        repo.updateAbnActionPlanDtl(status, counterMeasure, abnmKeyid);
        repo.updateAbnActionPlanMst(status, abnmKeyid);
        repo.updateAbnormality(status, counterMeasure, completedDate, completedBy, abnmKeyid);
    }

    @Override
@Transactional
public void abnormalityClosure(List<AppMainAbnDto> dtoList) {
    for (AppMainAbnDto dto : dtoList) {
        String counterMeasure = dto.getCounterMeasure();
        LocalDateTime completedDate = dto.getCompletedDate();
        String completedBy = dto.getCompletedBy();
        String abnmKeyid = dto.getAbnmKeyid();
        String status = dto.getStatus();

        repo.updateAbnActionPlanDtl(status, counterMeasure, abnmKeyid);
        repo.updateAbnActionPlanMst(status, abnmKeyid);
        repo.updateAbnormality(status, counterMeasure, completedDate , completedBy, abnmKeyid);
    }
}

    @Override
    @Transactional
    public void actionPlanClosure(AppMainActPlanDto dto) {
        String actionPlanId = dto.getActionPlanId();
        String detailId = dto.getDetailId();
        String status = dto.getStatus();
        String completedBy = dto.getCompletedBy();
        String counterMeasure = dto.getCounterMeasure();

        // getting LocalDateTime value
        LocalDateTime completedOn = dto.getCompletedOn();

        repo.updateActionPlanDtl(status, completedOn, completedBy, counterMeasure, detailId);
        repo.updateActionPlanMst(actionPlanId);

    }

    @Override
    @Transactional
    public void kaizenDateChange(String kznKeyId, LocalDateTime kznDate) {
        repo.updateKaizenDate(kznDate, kznKeyId);
        repo.updateKaizenBankDate(kznDate, kznKeyId);
    }

    @Override
    @Transactional
    public void fipProjectDateChange(String fipKeyId, LocalDateTime kznDate) {

        repo.updateFIProjectEndDate(fipKeyId, kznDate);
    }

}
