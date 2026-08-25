package com.akranta.perfex_sb.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;



import com.akranta.perfex_sb.dto.UpstreamdefectmstSaveDto;
import com.akranta.perfex_sb.model.UpstreamdefectDet;
import com.akranta.perfex_sb.model.Upstreamdefectmst;

public interface UpstreamdefectmstService {

    public UpstreamdefectmstSaveDto createorupdateUpstreamdefectmst(UpstreamdefectmstSaveDto upstreamdefectmstSaveDto);

    List<Map<String, Object>> getElementId(String loginflid, double loginlevel, 
                                        String loginElementid, String empId);

    public Upstreamdefectmst getbyUpsmId(String keyid);     
    
    List<Map<String, Object>> getbyUpstreamdefectkeyid (String keyid);

    public void deleteNewUpstreamDefect(String upsmKeyId);

    public void deleteNewUpstreamDefectDetails(String upsdKeyid);

    //List<Map<String, Object>> getbyUpstreamdefectkeyid(String keyid);

    



    

}
