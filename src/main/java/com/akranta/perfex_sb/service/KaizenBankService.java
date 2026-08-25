package com.akranta.perfex_sb.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.model.KznTlKaizenBankMst;

public interface KaizenBankService {

    KznTlKaizenBankMst save(KznTlKaizenBankMst kznTlKaizenBankMst) throws Exception;

    KznTlKaizenBankMst findById(String id);

    String selectKznData(String keyId);

    
    String updateKaizenWorkflowStatus(String keyId, String status, String kaizen, String acrejby,
            BigDecimal implementCost, LocalDateTime targetDate, String mocRequired, String responsibility,
            String verifyRemarks,String mocitem);


    public List<KznTlKaizenBankMst> multipleSave(List<KznTlKaizenBankMst> mltSuggs);
    
    List<Map<String, Object>> findCategoryRecall(String keyid) throws Exception;
    

    

}
