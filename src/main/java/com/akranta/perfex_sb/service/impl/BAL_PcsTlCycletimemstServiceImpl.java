package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.BAL_PcsTlCycletimemstSaveDto;
import com.akranta.perfex_sb.model.BAL_PcsTlCycletimehistory;
import com.akranta.perfex_sb.model.BAL_PcsTlCycletimemst;
import com.akranta.perfex_sb.repository.BAL_PcsTlCycletimehistoryRepository;
import com.akranta.perfex_sb.repository.BAL_PcsTlCycletimemstRepository;
import com.akranta.perfex_sb.service.BAL_PcsTlCycletimemstService;
import com.akranta.perfex_sb.service.DbActionTemplate;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BAL_PcsTlCycletimemstServiceImpl implements BAL_PcsTlCycletimemstService {

    private static final Logger logger = LoggerFactory.getLogger(BAL_PcsTlCycletimemstServiceImpl.class);

    @Autowired
    private BAL_PcsTlCycletimemstRepository mstRepository;

    @Autowired
    private BAL_PcsTlCycletimehistoryRepository hstRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    // same identifiers / prefixes as the old DaoImpl
    private static final String SEQ_IDENTIFIER_MST = "PCS_TL_CYCLETIMEMST";
    private static final String PREFIX_MST = "CMG";
    private static final String SEQ_IDENTIFIER_HST = "PCS_TL_CYCLETIMEHISTORY";
    private static final String PREFIX_HST = "CMH";
    private static final int KEY_LENGTH = 10;
    private static final String EMPTY_PRODGROUP = "{}";

    // history.tempfield1/2/3 are character(1) NOT NULL in the DB
    private static final Character HISTORY_TEMPFIELD_DEFAULT = ' ';

    @Override
@Transactional
public BAL_PcsTlCycletimemstSaveDto createorupdateCycletime(BAL_PcsTlCycletimemstSaveDto dto) {

    if (dto == null || dto.getCycletimemstList() == null || dto.getCycletimemstList().isEmpty()) {
        throw new IllegalArgumentException("Cycle time list should not be empty");
    }

    List<BAL_PcsTlCycletimemst> savedList = new ArrayList<>();

    for (BAL_PcsTlCycletimemst newRec : dto.getCycletimemstList()) {

        String[] cycleTimeKeyid = new String[1];
        int retval = 0;

        // Find the existing record (by keyid, or machine + product + cavity)
        BAL_PcsTlCycletimemst oldRec = findOldRecord(newRec);

        // Check whether this cycle time is already used in PCS
        if (oldRec != null) {
            retval = checkCycleTimeInPcs(newRec, cycleTimeKeyid, oldRec.getCavity());
            logger.info("retval -- {}", retval);
        }

        if (oldRec == null) {
            // Nothing exists yet -> INSERT
            savedList.add(insertNew(newRec, dto.getCreatedby()));

        } else if (retval != 1) {
            // Record exists and is not used in PCS -> UPDATE the same row
            if (isValidKeyId(cycleTimeKeyid[0])) {
                newRec.setKeyid(cycleTimeKeyid[0]);
            } else if (!isValidKeyId(newRec.getKeyid())) {
                newRec.setKeyid(oldRec.getKeyid());
            }

            BAL_PcsTlCycletimehistory history = buildHistory(oldRec, newRec.getFromdate());
            if (hstRepository.existsById(history.getKeyid())) {
                hstRepository.save(history);
            }
            savedList.add(updateMaster(newRec));

        } else {
            // retval == 1 -> used in PCS: archive old values under a new history key, then UPDATE master
            BAL_PcsTlCycletimehistory history = buildHistory(oldRec, newRec.getFromdate());
            history.setKeyid(nextKey(SEQ_IDENTIFIER_HST, PREFIX_HST));
            hstRepository.save(history);

            if (!isValidKeyId(newRec.getKeyid())) {
                newRec.setKeyid(oldRec.getKeyid());
            }
            savedList.add(updateMaster(newRec));
        }
    }

    BAL_PcsTlCycletimemstSaveDto response = new BAL_PcsTlCycletimemstSaveDto();
    response.setCreatedby(dto.getCreatedby());
    response.setCycletimemstList(savedList);
    return response;
}

    // @Override
    // @Transactional
    // public BAL_PcsTlCycletimemstSaveDto createorupdateCycletime(BAL_PcsTlCycletimemstSaveDto dto) {

    //     if (dto == null || dto.getCycletimemstList() == null || dto.getCycletimemstList().isEmpty()) {
    //         throw new IllegalArgumentException("Cycle time list should not be empty");
    //     }

    //     List<BAL_PcsTlCycletimemst> savedList = new ArrayList<>();

    //     for (BAL_PcsTlCycletimemst newRec : dto.getCycletimemstList()) {

    //         String[] cycleTimeKeyid = new String[1];  
    //         int retval = 0;

           
    //         BAL_PcsTlCycletimemst oldRec = findOldRecord(newRec);

            
    //         if (oldRec != null) {
    //             retval = checkCycleTimeInPcs(newRec, cycleTimeKeyid, oldRec.getCavity());
    //             logger.info("retval -- {}", retval);
    //         }

           
    //         if (retval != 1 && !isValidKeyId(cycleTimeKeyid[0])) {
               
    //             savedList.add(insertNew(newRec, dto.getCreatedby()));

    //         } else if (retval != 1 && (isValidKeyId(cycleTimeKeyid[0]) || isValidKeyId(newRec.getKeyid()))) {
                
    //             if (!isValidKeyId(newRec.getKeyid())
    //                     || (isValidKeyId(cycleTimeKeyid[0]) && !cycleTimeKeyid[0].equals(newRec.getKeyid()))) {
    //                 newRec.setKeyid(cycleTimeKeyid[0]);
    //             }

              
    //             BAL_PcsTlCycletimehistory history = buildHistory(oldRec, newRec.getFromdate());

                
    //             if (hstRepository.existsById(history.getKeyid())) {
    //                 hstRepository.save(history);
    //             }
    //             savedList.add(updateMaster(newRec));

    //         } else if (retval == 1) {
                
    //             BAL_PcsTlCycletimehistory history = buildHistory(oldRec, newRec.getFromdate());
    //             history.setKeyid(nextKey(SEQ_IDENTIFIER_HST, PREFIX_HST));
    //             hstRepository.save(history);

                
    //             if (!isValidKeyId(newRec.getKeyid())) {
    //                 newRec.setKeyid(oldRec.getKeyid());
    //             }
    //             savedList.add(updateMaster(newRec));
    //         }
    //     }

    //     BAL_PcsTlCycletimemstSaveDto response = new BAL_PcsTlCycletimemstSaveDto();
    //     response.setCreatedby(dto.getCreatedby());
    //     response.setCycletimemstList(savedList);
    //     return response;
    // }

   @Override
    @Transactional
    public void deleteByKeyid(String keyid) throws Exception {

        BAL_PcsTlCycletimemst existing = mstRepository.findById(keyid)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cycletime not found for keyid: " + keyid));

        mstRepository.deleteByKeyid(keyid);
    }

  
    
    private BAL_PcsTlCycletimemst findOldRecord(BAL_PcsTlCycletimemst newRec) {
        if (isValidKeyId(newRec.getKeyid())) {
            return mstRepository.findById(newRec.getKeyid()).orElse(null);
        }
        List<BAL_PcsTlCycletimemst> list = mstRepository.findByMachineProductCavity(
                newRec.getMachineid(), newRec.getProductid(), newRec.getCavity());
        return list.isEmpty() ? null : list.get(0);
    }


    private int checkCycleTimeInPcs(BAL_PcsTlCycletimemst newRec, String[] cycleTimeKeyid, Integer cavity) {
        String result = mstRepository.checkCycleTimeInPcs(newRec.getMachineid(), newRec.getProductid(), cavity);
        if (result == null) {
            return 0;
        }
        try {
            return Integer.parseInt(result.trim());
        } catch (NumberFormatException e) {
            cycleTimeKeyid[0] = result.trim();
            return 0;
        }
    }

   
    private BAL_PcsTlCycletimemst insertNew(BAL_PcsTlCycletimemst newRec, String createdby) {
        newRec.setKeyid(nextKey(SEQ_IDENTIFIER_MST, PREFIX_MST));
        if (!isValidKeyId(newRec.getCreatedby())) {
            newRec.setCreatedby(createdby);
        }
        if (newRec.getActive() == null) {
            newRec.setActive('Y');   // the grid query only shows rows with cytm_active = 'Y'
        }
        LocalDateTime now = LocalDateTime.now();
        newRec.setCreatedon(now);
        newRec.setModifiedon(now);
        BAL_PcsTlCycletimemst saved = mstRepository.save(newRec);
        logger.info("Inserted cycle time with Key ID: {}", saved.getKeyid());
        return saved;
    }


    private BAL_PcsTlCycletimemst updateMaster(BAL_PcsTlCycletimemst n) {
        BAL_PcsTlCycletimemst t = mstRepository.findById(n.getKeyid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cycle time record not found for keyid: " + n.getKeyid()));

        if (n.getCellid() != null)       t.setCellid(n.getCellid());
        if (n.getProductid() != null)    t.setProductid(n.getProductid());
        if (n.getCycletime() != null)    t.setCycletime(n.getCycletime());
        if (n.getManpower() != null)     t.setManpower(n.getManpower());
        if (n.getFromdate() != null)     t.setFromdate(n.getFromdate());
        if (n.getTilldate() != null)     t.setTilldate(n.getTilldate());
        if (n.getProdgroupid() != null)  t.setProdgroupid(n.getProdgroupid());
        if (n.getMachineid() != null)    t.setMachineid(n.getMachineid());
        if (n.getSectionid() != null)    t.setSectionid(n.getSectionid());
        if (n.getFactoryid() != null)    t.setFactoryid(n.getFactoryid());
        if (n.getCavity() != null)       t.setCavity(n.getCavity());
        if (n.getMandrels() != null)     t.setMandrels(n.getMandrels());
        if (n.getTempfield1() != null)   t.setTempfield1(n.getTempfield1());
        if (n.getTempfield2() != null)   t.setTempfield2(n.getTempfield2());
        if (n.getTempfield3() != null)   t.setTempfield3(n.getTempfield3());
        if (n.getActive() != null)       t.setActive(n.getActive());
        t.setModifiedon(LocalDateTime.now());

        BAL_PcsTlCycletimemst saved = mstRepository.save(t);
        logger.info("Updated cycle time with Key ID: {}", saved.getKeyid());
        return saved;
    }


    private BAL_PcsTlCycletimehistory buildHistory(BAL_PcsTlCycletimemst old, LocalDateTime newFromdate) {
        if (newFromdate == null) {
            throw new IllegalArgumentException("fromdate is required to close the old cycle time");
        }
        BAL_PcsTlCycletimehistory h = new BAL_PcsTlCycletimehistory();
        h.setKeyid(old.getKeyid());
        h.setCellid(old.getCellid());
        h.setProductid(old.getProductid());
        h.setCycletime(old.getCycletime());
        h.setManpower(old.getManpower());
        h.setFromdate(old.getFromdate());
        h.setProdgroupid(isValidKeyId(old.getProdgroupid()) ? old.getProdgroupid() : EMPTY_PRODGROUP);
        h.setMachineid(old.getMachineid());
        h.setSectionid(old.getSectionid());
        h.setFactoryid(old.getFactoryid());
        h.setCavity(old.getCavity());
        h.setMandrels(old.getMandrels());
        h.setTempfield1(old.getTempfield1() != null ? old.getTempfield1() : HISTORY_TEMPFIELD_DEFAULT);
        h.setTempfield2(HISTORY_TEMPFIELD_DEFAULT);
        h.setTempfield3(HISTORY_TEMPFIELD_DEFAULT);
        h.setActive(old.getActive());
        h.setCreatedby(old.getCreatedby());
        h.setCreatedon(old.getCreatedon());
        h.setModifiedon(old.getModifiedon());
        
        h.setTilldate(newFromdate.minusDays(1));
        return h;
    }

    private String nextKey(String seqIdentifier, String prefix) {
        try {
            String key = dbActionTemplate.getSequenceNumber(seqIdentifier, KEY_LENGTH, prefix, null, null);
            if (key == null || key.trim().isEmpty()) {
                throw new IllegalStateException("Failed to generate key for " + seqIdentifier);
            }
            return key;
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate key for " + seqIdentifier + ": " + e.getMessage(), e);
        }
    }

   
    private boolean isValidKeyId(String s) {
        return s != null && !s.trim().isEmpty();
    }
}