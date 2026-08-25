package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.model.OplTlMst;
import com.akranta.perfex_sb.model.BdmTlYycountermeasurelink;
import com.akranta.perfex_sb.model.GenTlDocupdates;

import java.util.List;
import java.util.Map;

public interface OplTlMstService {
    OplTlMst create(OplTlMst opl);
    OplTlMst getByKeyid(String keyid);
    List<OplTlMst> getAll();
    OplTlMst update(String keyid, OplTlMst opl);
    void delete(String keyid);

    // ✅ NEW (matches your Eclipse DAO create(opl, yylink, docupdates))
    OplTlMst save(OplTlMst oplTlMst, BdmTlYycountermeasurelink link, GenTlDocupdates doc);

   
    List<Map<String,Object>> recallStudents(String oplId, String cellId, String oplKeyid);



}
