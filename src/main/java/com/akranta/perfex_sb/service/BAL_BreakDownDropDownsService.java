package com.akranta.perfex_sb.service;

import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.Bal_AssemblyMstDto;
import com.akranta.perfex_sb.dto.Bal_SubAssemblyMstDto;
import com.akranta.perfex_sb.model.BAL_BdmTlCausemst;
import com.akranta.perfex_sb.model.BAL_BdmTlPhenomenamst;
import com.akranta.perfex_sb.model.BAL_GenTlAssemblymst;
import com.akranta.perfex_sb.model.BAL_GenTlSubAssemblymst;


public interface BAL_BreakDownDropDownsService {

    public BAL_GenTlAssemblymst saveAssembly(Bal_AssemblyMstDto dto) throws Exception;

    public BAL_GenTlAssemblymst updateAssembly(Bal_AssemblyMstDto dto) throws Exception;

    public BAL_GenTlAssemblymst selectAssembly(String keyid) throws Exception;

    public void deleteAssembly(String delemode, String keyid) throws Exception;


    public BAL_GenTlSubAssemblymst saveSubAssembly(Bal_SubAssemblyMstDto dto) throws Exception;

    public BAL_GenTlSubAssemblymst updateSubAssembly(Bal_SubAssemblyMstDto dto) throws Exception;

    public BAL_GenTlSubAssemblymst selectSubAssembly(String keyid) throws Exception;
    public String getMachineIdBySubAssembly(String sbamKeyid) throws Exception;

    public void deleteSubAssembly(String delemode, String keyid) throws Exception;

    public BAL_BdmTlPhenomenamst savePhenomena(BAL_BdmTlPhenomenamst phenomenamst) throws Exception;
    
    public BAL_BdmTlPhenomenamst updatePhenomena(BAL_BdmTlPhenomenamst phenomenamst) throws Exception;

    public BAL_BdmTlPhenomenamst selectPhenomena(String keyid) throws Exception;

    public void deletePhenomena(String keyid) throws Exception;

    public BAL_BdmTlCausemst saveCause(BAL_BdmTlCausemst causemst) throws Exception;

    public BAL_BdmTlCausemst updateCause(BAL_BdmTlCausemst causemst) throws Exception;

    public BAL_BdmTlCausemst selectCause(String keyid) throws Exception;

    public void deleteCause(String causeId) throws Exception;


    
}

