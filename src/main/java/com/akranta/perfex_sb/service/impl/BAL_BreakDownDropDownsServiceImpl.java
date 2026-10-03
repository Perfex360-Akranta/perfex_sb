package com.akranta.perfex_sb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.Bal_AssemblyMstDto;
import com.akranta.perfex_sb.dto.Bal_SubAssemblyMstDto;
import com.akranta.perfex_sb.model.BAL_BdmTlCausemst;
import com.akranta.perfex_sb.model.BAL_BdmTlPhenomenamst;
import com.akranta.perfex_sb.model.BAL_GenTlAssemblymst;
import com.akranta.perfex_sb.model.BAL_GenTlFunctionallocn;
import com.akranta.perfex_sb.model.BAL_GenTlSubAssemblymst;
import com.akranta.perfex_sb.repository.BAL_GenTlAssemblymstRepository;
import com.akranta.perfex_sb.repository.BAL_GenTlFunctionallocnRepository;
import com.akranta.perfex_sb.repository.BAL_GenTlSubAssemblymstRepository;
import com.akranta.perfex_sb.repository.BAL_BdmTlCausemstRepository;
import com.akranta.perfex_sb.repository.BAL_BdmTlPhenomenamstRepository ;
import com.akranta.perfex_sb.service.BAL_BreakDownDropDownsService;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.util.ValidationUtil;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BAL_BreakDownDropDownsServiceImpl implements BAL_BreakDownDropDownsService
{
    private static final int KEY_LENGTH = 8;
    
    private static final String DATE_FORMAT = "";
    private static final String FORMAT_RESET = "N";

    private static final String seqIdentfrAssm = "GEN_TL_ASSEMBLYMST";
    private static final String PREFIXAssm = "ASM";

    private static final String seqIdentfrfun = "GEN_TL_FUNCTIONALLOCN";
    private static final String PREFIXfun = "FNLN";

    @Autowired
    private DbActionTemplate dbActionTemplate;

     @Autowired
    private BAL_GenTlAssemblymstRepository assemblymstRepository;

    @Autowired
    private BAL_GenTlFunctionallocnRepository functionallocnRepository;

    private static final Logger logger = LoggerFactory.getLogger(BAL_BreakDownDropDownsServiceImpl.class);

    @Override
    public BAL_GenTlAssemblymst saveAssembly(Bal_AssemblyMstDto dto) throws Exception 
    {
        BAL_GenTlAssemblymst assemblymst = dto.getAssemblymst();
        BAL_GenTlFunctionallocn functionallocn = dto.getGenTlFunctionallocn();

       BAL_GenTlAssemblymst result = new BAL_GenTlAssemblymst();

        if(!ValidationUtil.isValidKeyId(assemblymst.getKeyid()))
            {
                logger.info("seqIdentfrAssm Keyid {} ",seqIdentfrAssm);
                logger.info("KEY_LENGTH Keyid {} ",KEY_LENGTH);
                logger.info("PREFIXAssm Keyid {} ",PREFIXAssm);
                logger.info("DATE_FORMAT Keyid {} ",DATE_FORMAT);
                logger.info("FORMAT_RESET Keyid {} ",FORMAT_RESET);
               
                  String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfrAssm,KEY_LENGTH,PREFIXAssm,DATE_FORMAT,FORMAT_RESET);
                  logger.info("New Keyid {} ",newKeyId);
                  assemblymst.setKeyid(newKeyId);
                  assemblymst.setCode(assemblymst.getKeyid());
                  
                   if(functionallocn!= null)
                    {
                        String elementId = functionallocnRepository.findElementIdsByOriginalId(functionallocn.getOriginalid());
                        if(ValidationUtil.isValidKeyId(elementId))
                            {
                                String newKeyIdFunc = dbActionTemplate.getSequenceNumber(seqIdentfrfun,KEY_LENGTH,PREFIXfun,DATE_FORMAT,FORMAT_RESET);
                                functionallocn.setKeyid(newKeyIdFunc);
                                functionallocn.setOriginalid(assemblymst.getKeyid());
                                functionallocn.setElementid(elementId+"-"+assemblymst.getKeyid());
                                functionallocn.setParentid(elementId);
                                functionallocn.setDisplaycode(assemblymst.getName());
                                BAL_GenTlFunctionallocn fTlFunctionallocnSave = functionallocnRepository.save(functionallocn);

                            }
                       
                    }
                    result = assemblymstRepository.save(assemblymst);
                }
        return result;
        
    }

    @Override
    public BAL_GenTlAssemblymst updateAssembly(Bal_AssemblyMstDto dto) throws Exception
    {
        BAL_GenTlAssemblymst assemblymst = dto.getAssemblymst();
        BAL_GenTlFunctionallocn functionallocn = dto.getGenTlFunctionallocn();

        assemblymst.setCode(assemblymst.getKeyid()); // keyid already exists, just keep code in sync

        if (functionallocn != null)
        {
            String elementId = functionallocnRepository.findElementIdsByOriginalId(functionallocn.getOriginalid());
            if (ValidationUtil.isValidKeyId(elementId))
            {
                //String existingKeyid = functionallocnRepository.findKeyidByOriginalId(functionallocn.getOriginalid());
                //functionallocn.setKeyid(existingKeyid);

                String existingKeyid = functionallocnRepository.findKeyidByOriginalId(assemblymst.getKeyid());
                functionallocn.setKeyid(existingKeyid);


                functionallocn.setOriginalid(assemblymst.getKeyid());
                functionallocn.setElementid(elementId + "-" + assemblymst.getKeyid());
                functionallocn.setParentid(elementId);
                functionallocn.setDisplaycode(assemblymst.getName());
                functionallocnRepository.save(functionallocn); // JPA save = update since keyid already set
            }
        }

        BAL_GenTlAssemblymst result = assemblymstRepository.save(assemblymst); // JPA save = update since keyid already set
        return result;
    }

    @Override
    public BAL_GenTlAssemblymst selectAssembly(String keyid) throws Exception
    {
        return assemblymstRepository.findById(keyid)
            .orElseThrow(() -> new Exception("Assembly not found for keyid: " + keyid));
    }

    @Override
    @Transactional
    public void deleteAssembly(String delemode, String keyid) throws Exception
    {
        // Mirrors legacy: for hard delete (D), block if this assembly is still referenced
        // in the functional location table. Inactivate (I) mode skips this check.
        if (!delemode.equals("I"))
        {
            long count = functionallocnRepository.countByOriginalId(keyid);
            if (count > 0)
            {
                throw new Exception("Original Id Exists"); // mirrors BusinessApplicationExceptions in legacy
        }
    }

    if (delemode.equals("I"))
    {
        assemblymstRepository.inactivateByKeyid(keyid);
    }
    else
    {
        assemblymstRepository.deleteById(keyid);
    }

    // Regardless of mode, cascade-inactivate the linked functional location row if one exists
    long linkedCount = functionallocnRepository.countByOriginalId(keyid);
    if (linkedCount > 0)
    {
        functionallocnRepository.inactivateByOriginalId(keyid);
    }
}


@Autowired
private BAL_GenTlSubAssemblymstRepository subAssemblymstRepository;

private static final String seqIdentfrSbam = "GEN_TL_SUBASSEMBLYMST"; 
//private static final String PREFIXSbam = "SBAM"; 
private static final String PREFIXSbam = "SBM"; 

@Override
public BAL_GenTlSubAssemblymst saveSubAssembly(Bal_SubAssemblyMstDto dto) throws Exception
{
    BAL_GenTlSubAssemblymst subAssemblymst = dto.getSubAssemblymst();
    BAL_GenTlFunctionallocn functionallocn = dto.getGenTlFunctionallocn();

    BAL_GenTlSubAssemblymst result = new BAL_GenTlSubAssemblymst();

    if (!ValidationUtil.isValidKeyId(subAssemblymst.getKeyid()))
    {
        String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfrSbam, KEY_LENGTH, PREFIXSbam, DATE_FORMAT, FORMAT_RESET);
        subAssemblymst.setKeyid(newKeyId);
        subAssemblymst.setCode(subAssemblymst.getKeyid());

        // assemblyid should already be set on subAssemblymst from the incoming DTO (parent link, like hdnAssemblyId)

        if (functionallocn != null)
        {
            // NOTE: functionallocn.getOriginalid() here = the PARENT ASSEMBLY's keyid (set by Eclipse before sending)
            String elementId = functionallocnRepository.findElementIdsByOriginalId(functionallocn.getOriginalid());
            if (ValidationUtil.isValidKeyId(elementId))
            {
                String newKeyIdFunc = dbActionTemplate.getSequenceNumber(seqIdentfrfun, KEY_LENGTH, PREFIXfun, DATE_FORMAT, FORMAT_RESET);
                functionallocn.setKeyid(newKeyIdFunc);
                functionallocn.setOriginalid(subAssemblymst.getKeyid());              // now points to the sub-assembly, not the assembly
                functionallocn.setElementid(elementId + "-" + subAssemblymst.getKeyid());
                functionallocn.setParentid(elementId);
                functionallocn.setDisplaycode(subAssemblymst.getName());
                functionallocn.setDescription(subAssemblymst.getName());
                functionallocn.setElementtype("S");                                  // matches legacy DAO's fnlnElementtype "S"
                functionallocn.setActive('Y');
                functionallocnRepository.save(functionallocn);
            }
        }

        result = subAssemblymstRepository.save(subAssemblymst);
    }

    return result;
}

@Override
public BAL_GenTlSubAssemblymst updateSubAssembly(Bal_SubAssemblyMstDto dto) throws Exception
{
    BAL_GenTlSubAssemblymst subAssemblymst = dto.getSubAssemblymst();
    BAL_GenTlFunctionallocn functionallocn = dto.getGenTlFunctionallocn();

    subAssemblymst.setCode(subAssemblymst.getKeyid()); // keyid already exists, just keep code in sync

    if (functionallocn != null)
    {
        String elementId = functionallocnRepository.findElementIdsByOriginalId(functionallocn.getOriginalid());
        if (ValidationUtil.isValidKeyId(elementId))
        {
            // Look up by the SUB-ASSEMBLY's own keyid, not the parent assembly id in functionallocn.getOriginalid()
            // (same fix pattern as assembly update — avoids updating the wrong functionallocn row)
            String existingKeyid = functionallocnRepository.findKeyidByOriginalId(subAssemblymst.getKeyid());
            functionallocn.setKeyid(existingKeyid);

            functionallocn.setOriginalid(subAssemblymst.getKeyid());
            functionallocn.setElementid(elementId + "-" + subAssemblymst.getKeyid());
            functionallocn.setParentid(elementId);
            functionallocn.setDisplaycode(subAssemblymst.getName());
            functionallocn.setDescription(subAssemblymst.getName());
            functionallocn.setElementtype("S");
            functionallocn.setActive('Y'); // Character type, single quotes
            functionallocnRepository.save(functionallocn); // JPA save = update since keyid already set
        }
    }

    BAL_GenTlSubAssemblymst result = subAssemblymstRepository.save(subAssemblymst); // JPA save = update since keyid already set
    return result;
}

@Override
public BAL_GenTlSubAssemblymst selectSubAssembly(String keyid) throws Exception
{
    return subAssemblymstRepository.findById(keyid)
        .orElseThrow(() -> new Exception("SubAssembly not found for keyid: " + keyid));
}

@Override
public String getMachineIdBySubAssembly(String sbamKeyid) throws Exception
{
    String machineId = subAssemblymstRepository.getMachineIdByAssembly(sbamKeyid);
    return machineId != null ? machineId : "";
} 




@Override
@Transactional
public void deleteSubAssembly(String delemode, String keyid) throws Exception
{
    System.out.println(
        ">>> deleteSubAssembly START, delemode=[" + delemode +
        "] keyid=[" + keyid + "]"
    );

    if (keyid == null || keyid.trim().isEmpty())
    {
        throw new IllegalArgumentException("Subassembly keyid is required");
    }

    if (!subAssemblymstRepository.existsById(keyid))
    {
        throw new IllegalArgumentException(
            "Subassembly does not exist: " + keyid
        );
    }

    long linkedCount =
        functionallocnRepository.countByOriginalId(keyid);

    System.out.println(
        ">>> Function allocation linkedCount=[" + linkedCount + "]"
    );

    /*
     * I = explicitly requested inactive mode.
     * If a function-allocation link exists, also use soft delete,
     * because physical deletion can break the reference.
     */
    if ("I".equalsIgnoreCase(delemode) || linkedCount > 0)
    {
        System.out.println(
            ">>> Soft deleting subassembly keyid=[" + keyid + "]"
        );

        subAssemblymstRepository.inactivateByKeyid(keyid);

        if (linkedCount > 0)
        {
            functionallocnRepository.inactivateByOriginalId(keyid);
        }
    }
    else if ("D".equalsIgnoreCase(delemode))
    {
        System.out.println(
            ">>> Hard deleting subassembly keyid=[" + keyid + "]"
        );

        subAssemblymstRepository.deleteById(keyid);
    }
    else
    {
        throw new IllegalArgumentException(
            "Invalid delete mode: " + delemode
        );
    }

    System.out.println(">>> deleteSubAssembly END");
}
// Added by priyanka

@Autowired
private BAL_BdmTlPhenomenamstRepository phenomenamstRepository;

private static final String seqIdentfrPhm = "BDM_TL_PHENOMENAMST"; // confirm matches your sequence generator's table key
private static final String PREFIXPhm = "PHM";

@Override
public BAL_BdmTlPhenomenamst savePhenomena(BAL_BdmTlPhenomenamst phenomenamst) throws Exception
{
    if (!ValidationUtil.isValidKeyId(phenomenamst.getKeyid()))
    {
        String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfrPhm, KEY_LENGTH, PREFIXPhm, DATE_FORMAT, FORMAT_RESET);
        phenomenamst.setKeyid(newKeyId);
    }
    return phenomenamstRepository.save(phenomenamst);
}

@Override
public BAL_BdmTlPhenomenamst updatePhenomena(BAL_BdmTlPhenomenamst phenomenamst) throws Exception
{
    return phenomenamstRepository.save(phenomenamst);
}

@Override
public BAL_BdmTlPhenomenamst selectPhenomena(String keyid) throws Exception
{
    return phenomenamstRepository.findById(keyid)
        .orElseThrow(() -> new Exception("Phenomena not found for keyid: " + keyid));
}

@Override
@Transactional
public void deletePhenomena(String keyid) throws Exception
{
    phenomenamstRepository.deleteById(keyid);
}

@Autowired
private BAL_BdmTlCausemstRepository causemstRepository;

private static final String seqIdentfrCsm = "BDM_TL_CAUSEMST"; // confirm exact value matches your legacy TableNames.TBL_BDM_TL_CAUSEMST
private static final String PREFIXCsm = "CSM";

@Override
public BAL_BdmTlCausemst saveCause(BAL_BdmTlCausemst causemst) throws Exception
{
    String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfrCsm, KEY_LENGTH, PREFIXCsm, DATE_FORMAT, FORMAT_RESET);
    causemst.setKeyid(newKeyId);
    causemst.setCode(causemst.getKeyid()); // matches legacy: bdmTlCausemst.setBcsmCode(bdmTlCausemst.getBcsmKeyid())

    return causemstRepository.save(causemst);
}

@Override
public BAL_BdmTlCausemst updateCause(BAL_BdmTlCausemst causemst) throws Exception
{
    //return causemstRepository.save(causemst); // JPA save = update since keyid already set
    if (causemst.getCode() == null || causemst.getCode().isEmpty())
    {
        causemst.setCode(causemst.getKeyid());
    }
    return causemstRepository.save(causemst);
}

@Override
public BAL_BdmTlCausemst selectCause(String keyid) throws Exception
{
    return causemstRepository.findById(keyid)
        .orElseThrow(() -> new Exception("Cause not found for keyid: " + keyid));
}

@Override
@Transactional
public void deleteCause(String causeId) throws Exception
{
    causemstRepository.deleteById(causeId);
    causemstRepository.inactivatePhncauselinkByElementId(causeId);
}

    
}
