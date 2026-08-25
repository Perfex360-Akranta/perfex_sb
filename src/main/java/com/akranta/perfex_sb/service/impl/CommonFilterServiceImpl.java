package com.akranta.perfex_sb.service.impl;
import java.util.Comparator;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.akranta.perfex_sb.controller.GenTlMachinemstController;
import com.akranta.perfex_sb.dto.ComboFilterDto;
import com.akranta.perfex_sb.dto.CommonFilterDto;
import com.akranta.perfex_sb.dto.DropDownDto;
import com.akranta.perfex_sb.model.AdmTlConfigurationmst;
import com.akranta.perfex_sb.model.GenTlSectionmst;
import com.akranta.perfex_sb.repository.CommonFilterRepository;
import com.akranta.perfex_sb.service.CommonFilterService;
import com.akranta.perfex_sb.util.ValidationUtil;
import com.akranta.perfex_sb.repository.GenTlSectionmstRepository;
import com.akranta.perfex_sb.repository.AdmTlConfigurationmstRepository;


@Service
public class CommonFilterServiceImpl implements CommonFilterService 
{
    @Autowired
    private CommonFilterRepository repository;
    @Autowired
    private GenTlSectionmstRepository sectionmstRepository;

    @Autowired
    private AdmTlConfigurationmstRepository configurationmstRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate; 

    private static final Logger logger = LoggerFactory.getLogger(CommonFilterServiceImpl.class);

    

    @Override
    public List<DropDownDto> getEmployeeComboList(CommonFilterDto commonFilterDto) 
    {
        ComboFilterDto comboFilter = new ComboFilterDto();
                List<Object> params = new ArrayList<>();

                /*
                 * Employee dropdown configuration
                 */

                comboFilter.setTableName("GEN_TL_EMPLOYEEMST E");

                comboFilter.setIdField("E.EMPM_KEYID");

                comboFilter.setNameField("TRIM(E.EMPM_NAME)");

                comboFilter.setCodeField("TRIM(E.EMPM_CODE)");

                comboFilter.setOrderByField("LABEL");

                /*
                 * Base employee condition
                 */

                comboFilter.setCondSql("""
                                        AND E.EMPM_ACTIVE = 'Y'
                                """);

                //logger.info("Cell Id {} ",commonFilterDto.getCellId());
                if(commonFilterDto != null)
                    {

                if ((ValidationUtil.isValidKeyId(commonFilterDto.getCellId())) && "N".equals(commonFilterDto.getAbnmOthers()) ) 
                {

                        comboFilter.setCondSql("""
                                        AND E.EMPM_KEYID IN (
                                                SELECT DISTINCT EMPM_KEYID
                                                FROM
                                                   GEN_TL_EMPLOYEEMST,
                                                   GEN_TL_FNLNROLETEAM
                                                   WHERE FRT_EMPM_KEYID = EMPM_KEYID
                                                AND
                                                FRT_FNLN_KEYID IN (
                                                                SELECT FLID
                                                                FROM GEN_MV_FLIDHIERARCHY
                                                                WHERE FNLN_ORIGINALID = ?
                                                        )
                                                )
                                        """);
                        params.add(commonFilterDto.getCellId());
                }
                        
                else if ((ValidationUtil.isValidKeyId(commonFilterDto.getCellId()) ) &&  "Y".equals(commonFilterDto.getAbnmOthers()))
                {
                        comboFilter.setCondSql("""
                                        AND E.EMPM_KEYID IN (
                                                SELECT DISTINCT EMPM_KEYID
                                                FROM
                                                   GEN_TL_EMPLOYEEMST,
                                                   GEN_TL_FNLNROLETEAM
                                                   WHERE FRT_EMPM_KEYID = EMPM_KEYID
                                                AND
                                                FRT_FNLN_KEYID IN (
                                                                SELECT FLID
                                                                FROM GEN_MV_FLIDHIERARCHY
                                                                WHERE FNLN_ORIGINALID <> ?
                                                        )
                                                )
                                        """);
                        // comboFilter.getSqlParams().put("cellId",commonFilterDto.getCellId()
                        params.add(commonFilterDto.getCellId());
                                    }            

                }       

        //logger.info("Cell Id = " + commonFilterDto.getCellId());
        //logger.info("Params = " + params);
        //logger.info("Params Array = " + java.util.Arrays.toString(params.toArray()));   

        return repository.fillComboValues(comboFilter,params.toArray());
        
    }



    @Override
    public List<DropDownDto> getTAbnTagClassCombo(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();
        
        comboFilter.setTableName("ABN_TL_TAGMST");
        comboFilter.setIdField("TAGM_KEYID");
        comboFilter.setNameField("TRIM(TAGM_NAME)");
        comboFilter.setCodeField("TRIM(TAGM_CODE)");
        comboFilter.setOrderByField("LABEL");
        
        
        comboFilter.setCondSql("""
                                AND TAGM_ACTIVE = 'Y'
                             """);
        
        return repository.fillComboValues(comboFilter,params.toArray());
    }


     @Override
    public List<DropDownDto> getAbnTypeCombo(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();
        
        comboFilter.setTableName("ABN_TL_TYPEMST");
        comboFilter.setIdField("ABTM_KEYID");
        comboFilter.setNameField("TRIM(ABTM_NAME)");
        comboFilter.setCodeField("TRIM(ABTM_CODE)");
        comboFilter.setOrderByField("LABEL");
        
        
        comboFilter.setCondSql("""
                                AND ABTM_ACTIVE = 'Y'
                             """);
        
        return repository.fillComboValues(comboFilter,params.toArray());
    }



     @Override
     public List<DropDownDto> getAbnSubTypecombo(CommonFilterDto commonFilterDto) 
     {
         ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();
        
        comboFilter.setTableName("ABN_TL_HTASOCMST");
        comboFilter.setIdField("AHSM_KEYID");
        comboFilter.setNameField("TRIM(AHSM_NAME)");
        comboFilter.setCodeField("TRIM(AHSM_CODE)");
        comboFilter.setOrderByField("LABEL");
        
        
        comboFilter.setCondSql("""
                                AND AHSM_ACTIVE = 'Y'
                             """);
        
        if(commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getAbnormalityTypeId()))
        {
                comboFilter.setCondSql("""
                                AND AHSM_ABNORMALITYTYPE = ?
                             """);
                params.add(commonFilterDto.getAbnormalityTypeId());
        }
        
        return repository.fillComboValues(comboFilter,params.toArray());
        
     }



     @Override
     public List<DropDownDto> getAbnImpactcombo(CommonFilterDto commonFilterDto) 
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();
        
        comboFilter.setTableName("ABN_TL_IMPACTMST");
        comboFilter.setIdField("ABIM_KEYID");
        comboFilter.setNameField("TRIM(ABIM_NAME)");
        comboFilter.setCodeField("TRIM(ABIM_CODE)");
        comboFilter.setOrderByField("LABEL");


        if(commonFilterDto!= null && ValidationUtil.isValidKeyId(commonFilterDto.getComboKey()))
         {
            comboFilter.setId(commonFilterDto.getComboKey());
         }
        
       
        
        comboFilter.setCondSql("""
                                AND ABIM_ACTIVE = 'Y'
                             """);

         logger.info("Running comboValue Key");
        
        return repository.fillComboValues(comboFilter,params.toArray());
       
     }



     @Override
     public List<DropDownDto> getAbnCategorycombo(CommonFilterDto commonFilterDto) 
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();
        
        comboFilter.setTableName("ABN_TL_CATEGORYMST");
        comboFilter.setIdField("ABCM_KEYID");
        comboFilter.setNameField("TRIM(ABCM_NAME)");
        comboFilter.setCodeField("TRIM(ABCM_CODE)");
        comboFilter.setOrderByField("LABEL");
        
        
        comboFilter.setCondSql("""
                                AND ABCM_ACTIVE = 'Y'
                             """);
        
        return repository.fillComboValues(comboFilter,params.toArray());

      
     }



     @Override
     public List<DropDownDto> getAbnTradecombo(CommonFilterDto commonFilterDto) 
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();
        
        comboFilter.setTableName("GEN_TL_TRADEMST");
        comboFilter.setIdField("TRDM_KEYID");
        comboFilter.setNameField("TRIM(TRDM_NAME)");
        comboFilter.setCodeField("TRIM(TRDM_CODE)");
        comboFilter.setOrderByField("LABEL");
        
        
        comboFilter.setCondSql("""
                                AND TRDM_ACTIVE = 'Y'
                             """);
        
        return repository.fillComboValues(comboFilter,params.toArray());
        
     }
     @Override
     public List<DropDownDto> getcombo_ccno(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_EMPLOYEEMST");
        comboFilter.setIdField("EMPM_KEYID");
        comboFilter.setNameField("EMPM_CODE");
        comboFilter.setCodeField("EMPM_NAME");
        comboFilter.setOrderByField("LABEL");

        String ccno = (commonFilterDto != null) ? commonFilterDto.getCcno() : null;

        logger.info("ccno in service impl => {}", ccno);

        StringBuilder condSql = new StringBuilder();
        condSql.append(" AND EMPM_KEYID NOT IN (SELECT USRM_CCNO FROM ADM_TL_USERMST");

        if (ValidationUtil.isValidKeyId(ccno)) {
            condSql.append(" WHERE USRM_CCNO <> ?");
            params.add(ccno);
        }

        condSql.append(")");

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     
@Override
     public List<DropDownDto> getProfidComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("ADM_TL_LOGINFRAMEWORK");
        comboFilter.setIdField("LGFR_KEYID");
        comboFilter.setNameField("LGFR_KEYID");
        comboFilter.setCodeField("LGFR_CREATEDDATE");

        comboFilter.setCondSql("""
                                AND LGFR_ACTIVE = 'Y'
                             """);

        return repository.fillComboValues(comboFilter, params.toArray());
     }


     @Override
     public List<DropDownDto> getDepartmentComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_DEPARTMENTMST");
        comboFilter.setIdField("DEPT_KEYID");
        comboFilter.setNameField("TRIM(DEPT_NAME)");
        comboFilter.setCodeField("TRIM(DEPT_CODE)");
        comboFilter.setOrderByField("LABEL");

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     @Override
     public List<DropDownDto> getDesignationComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_DESIGNATIONMST");
        comboFilter.setIdField("DESG_KEYID");
        comboFilter.setNameField("TRIM(DESG_NAME)");
        comboFilter.setCodeField("TRIM(DESG_CODE)");
        comboFilter.setOrderByField("LABEL");

        return repository.fillComboValues(comboFilter, params.toArray());
     }

   //   @Override
   //   public List<DropDownDto> getRoleComboList(CommonFilterDto commonFilterDto)
   //   {
   //      ComboFilterDto comboFilter = new ComboFilterDto();
   //      List<Object> params = new ArrayList<>();

   //      comboFilter.setTableName("ADM_TL_ROLEMST");
   //      comboFilter.setIdField("ROLE_KEYID");
   //      comboFilter.setNameField("TRIM(ROLE_NAME)");
   //      comboFilter.setCodeField("TRIM(ROLE_CODE)");
   //      comboFilter.setOrderByField("LABEL");

   //      logger.info("flid => {}", commonFilterDto != null ? commonFilterDto.getFlId() : null);

   //      return repository.fillComboValues(comboFilter, params.toArray());
   //   }

@Override
     public List<DropDownDto> getLopcCategoryCombo(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_LOPCCATEGORY");
        comboFilter.setIdField("LOCY_KEYID");
        comboFilter.setNameField("TRIM(LOCY_NAME)");
        comboFilter.setOrderByField("LABEL");

        return repository.fillComboValues(comboFilter, params.toArray());
     }
  @Override
     public List<DropDownDto> getUomComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("ADM_TL_UOMMST");
        comboFilter.setId(commonFilterDto.getComboKey());
        comboFilter.setIdField("UOMM_KEYID");
        comboFilter.setNameField("TRIM(UOMM_DESCRIPTION)");
        comboFilter.setCodeField("TRIM(UOMM_CODE)");
        comboFilter.setOrderByField("LABEL");

        return repository.fillComboValues(comboFilter, params.toArray());
     } 
      
     @Override
     public List<DropDownDto> getShiftComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        String wostatus = (commonFilterDto != null) ? commonFilterDto.getWostatus() : null;

        if (ValidationUtil.isValidKeyId(wostatus)) {
            if ("yes".equals(wostatus)) {
                comboFilter.setIdField("SFTM_KEYID");
            } else {
                comboFilter.setIdField("SFTM_CODE");
            }
            comboFilter.setCodeField("SFTM_CODE");
        } else {
            comboFilter.setIdField("SFTM_KEYID");
            comboFilter.setNameField("SFTM_NAME");
        }

        String factoryId = (commonFilterDto != null) ? commonFilterDto.getFactoryId() : null;

        if (ValidationUtil.isValidKeyId(factoryId)) {
            comboFilter.setCondSql("""
                    AND SFTM_FACTORYID = ?
                    AND SFTM_ACTIVE = 'Y'
                    AND SFTM_SHIFTORDER < 4
                    """);
            params.add(factoryId);
        }

        comboFilter.setTableName("GEN_TL_SHIFTMST");

        return repository.fillComboValues(comboFilter, params.toArray());
     }
    @Override
     public List<DropDownDto> getActionPlanEmployeeComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_EMPLOYEEMST E");
        comboFilter.setIdField("E.EMPM_KEYID");
        comboFilter.setNameField("TRIM(E.EMPM_NAME)");
        comboFilter.setCodeField("TRIM(E.EMPM_CODE)");
        comboFilter.setOrderByField("LABEL");

        String isPbuHead = commonFilterDto != null ? commonFilterDto.getIsPbuHead() : null;
        String roleId = commonFilterDto != null ? commonFilterDto.getRoleId() : null;
        String tradeid = commonFilterDto != null ? commonFilterDto.getTradeid() : null;
        String type = commonFilterDto != null ? commonFilterDto.getType() : null;
        String pillarid = commonFilterDto != null ? commonFilterDto.getPillarid() : null;
        String locationId = commonFilterDto != null ? commonFilterDto.getLocationId() : null;
        String locnId = commonFilterDto != null ? commonFilterDto.getLocnId() : null;
        String others = commonFilterDto != null ? commonFilterDto.getOthers() : null;
        String flid = commonFilterDto != null ? commonFilterDto.getFlId() : null;
        String rtal = commonFilterDto != null ? commonFilterDto.getRtal() : null;
        String code = commonFilterDto != null ? commonFilterDto.getCode() : null;

        StringBuilder condSql = new StringBuilder(" AND E.EMPM_ACTIVE = 'Y' ");

        // Type-ahead search box filter (matches against NAME-CODE combined label)
        if (ValidationUtil.isValidKeyId(code)) {
            condSql.append(" AND UPPER(E.EMPM_NAME || '-' || E.EMPM_CODE) LIKE UPPER(?) ");
            params.add("%" + code + "%");
        }

        if (ValidationUtil.isValidKeyId(tradeid) || ValidationUtil.isValidKeyId(roleId)) {

            StringBuilder sub = new StringBuilder();
            sub.append(" AND E.EMPM_KEYID IN ( ");
            sub.append(" SELECT DISTINCT EMPM_KEYID ");
            sub.append(" FROM GEN_TL_EMPLOYEEMST ");
            sub.append(" JOIN GEN_TL_FNLNROLETEAM ON FRT_EMPM_KEYID = EMPM_KEYID ");
            sub.append(" JOIN GEN_TL_TEAMTRADELINK ON FRP_FRT_KEYID = FRT_KEYID ");
            sub.append(" JOIN gen_mv_flidhierarchy ON flid = FRT_FNLN_KEYID ");
            sub.append(" WHERE EMPM_ACTIVE = 'Y' ");

            if ("Y".equals(others)) {
                sub.append(" AND POSITION((SELECT FNLN_KEYID FROM gen_tl_functionallocn WHERE FNLN_ORIGINALID = ?) IN (parentflids || flid)) > 0 ");
                params.add(locationId);
            } else {
                sub.append(" AND POSITION(? IN (parentflids || flid)) > 0 ");
                params.add(flid);
            }

            if (ValidationUtil.isValidKeyId(roleId)) {
                sub.append(" AND FRT_ROLE_KEYID = ? ");
                params.add(roleId);
            }

            if (ValidationUtil.isValidKeyId(tradeid)) {
                sub.append(" AND FRP_TRADEID = ? ");
                params.add(tradeid);
            }

            sub.append(") ");
            condSql.append(sub);

        } else if (ValidationUtil.isValidKeyId(type)) {

            StringBuilder sub = new StringBuilder();
            sub.append(" AND E.EMPM_KEYID IN ( ");
            sub.append(" SELECT DISTINCT EMPM_KEYID ");
            sub.append(" FROM GEN_TL_EMPLOYEEMST ");
            sub.append(" JOIN GEN_TL_FNLNROLETEAM ON FRT_EMPM_KEYID = EMPM_KEYID ");
            sub.append(" LEFT JOIN GEN_TL_TEAMTRADELINK ON FRT_KEYID = FRP_FRT_KEYID ");
            sub.append(" LEFT JOIN ADM_TL_ROLEMST ON FRT_ROLE_KEYID = ROLE_KEYID ");
            sub.append(" LEFT JOIN GEN_TL_MOMATTENDANCE ON MOMA_EMPLOYEEID = EMPM_KEYID ");
            sub.append(" JOIN gen_mv_flidhierarchy ON FLID = FRT_FNLN_KEYID ");
            sub.append(" LEFT JOIN GEN_TL_MOMMST ON MOMA_MOMS_KEYID = MOMS_KEYID ");
            sub.append(" WHERE 1=1 ");

            if ("Y".equals(others)) {
                sub.append(" AND POSITION((SELECT FNLN_KEYID FROM gen_tl_functionallocn WHERE FNLN_ORIGINALID = ?) IN (parentflids || flid)) > 0 ");
                params.add(locationId);
            } else if ("Others".equals(type)) {
                sub.append(" AND flid = ? ");
                params.add(flid);
            } else if (!"Production".equals(type)) {
                sub.append(" AND POSITION(? IN (parentflids || flid)) > 0 ");
                params.add(flid);
            }

            if (!"Others".equals(type) && !"Dmt".equals(type) && !"JH".equals(type) && !"Pillar".equals(type)) {
                sub.append(" AND ROLE_KEYID IN (SELECT MRMP_ROLE_KEYID FROM GEN_TL_MEETINGTYPE_ROLE_MAP WHERE MRMP_MEETING_TYPE = UPPER(?) AND EMPM_ACTIVE = 'Y' AND MRMP_PILLAR_ID = '-') ");
                params.add(type);
            } else if ("Pillar".equals(type)) {
                sub.append(" AND ROLE_KEYID IN (SELECT MRMP_ROLE_KEYID FROM GEN_TL_MEETINGTYPE_ROLE_MAP WHERE MRMP_MEETING_TYPE = UPPER(?) AND EMPM_ACTIVE = 'Y' AND MRMP_PILLAR_ID = ?) ");
                params.add(type);
                params.add(pillarid);
            }

            if (ValidationUtil.isValidKeyId(roleId)) {
                sub.append(" AND FRT_ROLE_KEYID = ? AND EMPM_ACTIVE = 'Y' ");
                params.add(roleId);
            }

            if (ValidationUtil.isValidKeyId(tradeid)) {
                sub.append(" AND FRP_TRADEID = ? AND EMPM_ACTIVE = 'Y' ");
                params.add(tradeid);
            }

            sub.append(") ");
            condSql.append(sub);

        } else {

            StringBuilder sub = new StringBuilder();
            sub.append(" AND E.EMPM_KEYID IN ( ");
            sub.append(" SELECT DISTINCT EMPM_KEYID ");
            sub.append(" FROM GEN_TL_EMPLOYEEMST ");
            sub.append(" JOIN GEN_TL_FNLNROLETEAM ON FRT_EMPM_KEYID = EMPM_KEYID ");
            sub.append(" LEFT JOIN GEN_TL_TEAMTRADELINK ON FRT_KEYID = FRP_FRT_KEYID ");
            sub.append(" LEFT JOIN ADM_TL_ROLEMST ON FRT_ROLE_KEYID = ROLE_KEYID ");
            sub.append(" LEFT JOIN GEN_TL_MOMATTENDANCE ON MOMA_EMPLOYEEID = EMPM_KEYID ");
            sub.append(" JOIN gen_mv_flidhierarchy ON FLID = FRT_FNLN_KEYID ");
            sub.append(" LEFT JOIN GEN_TL_MOMMST ON MOMA_MOMS_KEYID = MOMS_KEYID ");
            sub.append(" WHERE 1=1 ");

            if ("Y".equals(others)) {
                sub.append(" AND POSITION((SELECT FNLN_KEYID FROM gen_tl_functionallocn WHERE FNLN_ORIGINALID = ?) IN (parentflids || flid)) > 0 ");
                params.add(locationId);
            } else {
                sub.append(" AND POSITION(? IN (parentflids || flid)) > 0 ");
                params.add(flid);
            }

            sub.append(") ");
            condSql.append(sub);
        }

        if (ValidationUtil.isValidKeyId(locnId) && !ValidationUtil.isValidKeyId(isPbuHead)) {
            condSql.append(" AND E.EMPM_LOCATION = ? ");
            params.add(locnId);
        }

        if (ValidationUtil.isValidKeyId(rtal) && !"empFilter".equals(rtal)) {
            String[] rtlRole = rtal.split(",");
            if (rtlRole.length > 1 && ValidationUtil.isValidKeyId(rtlRole[1])) {
                condSql.append(" AND E.EMPM_ROLEID = ? AND E.EMPM_KEYID NOT IN (SELECT EREL_EMPM_KEYID FROM ENT_TL_ROLE_EMP_LINK WHERE EREL_RTAL_KEYID = ?) ");
                params.add(rtlRole[1]);
                params.add(rtlRole[0]);
            } else {
                condSql.append(" AND E.EMPM_KEYID NOT IN (SELECT EREL_EMPM_KEYID FROM ENT_TL_ROLE_EMP_LINK WHERE EREL_ACTIVE = 'Y') ");
            }
        }

        comboFilter.setCondSql(condSql.toString());

        if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getComboKey())) {
            comboFilter.setId(commonFilterDto.getComboKey());
        }

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     @Override
     public List<DropDownDto> getCompanyComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_VW_FACTORYLAYOUT");
        comboFilter.setIdField("COMP_KEYID");
        comboFilter.setNameField("TRIM(COMP_NAME)");
        comboFilter.setCodeField("TRIM(COMP_CODE)");
        comboFilter.setOrderByField("LABEL");

        String keyid = (commonFilterDto != null) ? commonFilterDto.getKeyid() : null;

        StringBuilder condSql = new StringBuilder(" AND COMP_ACTIVE = 'Y' ");

        if (ValidationUtil.isValidKeyId(keyid) && keyid.length() >= 3 && "CMP".equals(keyid.substring(0, 3))) {
            condSql.append(" AND COMP_KEYID <> ? ");
            params.add(keyid);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     
     @Override
     public List<DropDownDto> getLocationComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_LOCATIONMST");
        comboFilter.setIdField("LOCN_KEYID");
        comboFilter.setNameField("TRIM(LOCN_NAME)");
        comboFilter.setCodeField("TRIM(LOCN_CODE)");
        comboFilter.setOrderByField("LABEL");

        String keyid = (commonFilterDto != null) ? commonFilterDto.getKeyid() : null;
        String companyId = (commonFilterDto != null) ? commonFilterDto.getCompanyId() : null;

        // NOTE: matches legacy behavior exactly — if companyId is present it
        // OVERWRITES the keyid-exclude condition rather than combining with
        // it, since the legacy code called setCondSql() twice in sequence.
        if (ValidationUtil.isValidKeyId(keyid)) {
            comboFilter.setCondSql(" AND LOCN_KEYID <> ? ");
            params.add(keyid);
        }

        if (ValidationUtil.isValidKeyId(companyId)) {
            params.clear();
            comboFilter.setCondSql(" AND LOCN_COMPANYID = ? ");
            params.add(companyId);
        }

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     @Override
     public List<DropDownDto> getSbuComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_SBUMST");
        comboFilter.setIdField("SBUT_KEYID");
        comboFilter.setNameField("SBUT_NAME");
        comboFilter.setCodeField("SBUT_CODE");
        comboFilter.setOrderByField("LABEL");

        String locationId = (commonFilterDto != null) ? commonFilterDto.getLocationId() : null;
        String companyId = (commonFilterDto != null) ? commonFilterDto.getCompanyId() : null;

        StringBuilder condSql = new StringBuilder();

        if (ValidationUtil.isValidKeyId(locationId)) {
            condSql.append(" AND SBUT_LOCATIONID = ? ");
            params.add(locationId);
        }

        if (ValidationUtil.isValidKeyId(companyId)) {
            condSql.append(" AND SBUT_COMPANYID = ? ");
            params.add(companyId);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }

     @Override
     public List<DropDownDto> getPbuComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_PBUMST");
        comboFilter.setIdField("PBUT_KEYID");
        comboFilter.setNameField("PBUT_NAME");
        comboFilter.setCodeField("PBUT_CODE");
        comboFilter.setOrderByField("LABEL");

        String sbuId = (commonFilterDto != null) ? commonFilterDto.getSbuId() : null;
        String locationId = (commonFilterDto != null) ? commonFilterDto.getLocationId() : null;
        String companyId = (commonFilterDto != null) ? commonFilterDto.getCompanyId() : null;

        StringBuilder condSql = new StringBuilder();

        if (ValidationUtil.isValidKeyId(sbuId)) {
            condSql.append(" AND PBUT_SBUID = ? ");
            params.add(sbuId);
        }

        if (ValidationUtil.isValidKeyId(locationId)) {
            condSql.append(" AND PBUT_SBUID IN (SELECT SBUT_KEYID FROM GEN_TL_SBUMST WHERE SBUT_LOCATIONID = ?) ");
            params.add(locationId);
        }

        if (ValidationUtil.isValidKeyId(companyId)) {
            condSql.append(" AND PBUT_SBUID IN (SELECT SBUT_KEYID FROM GEN_TL_SBUMST WHERE SBUT_COMPANYID = ?) ");
            params.add(companyId);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }
@Override
     public List<DropDownDto> getSectionComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_VW_FACTORYLAYOUT");
        comboFilter.setIdField("SECT_KEYID");
        comboFilter.setNameField("TRIM(SECT_NAME)");
        comboFilter.setCodeField("TRIM(SECT_CODE)");
        comboFilter.setOrderByField("LABEL");

        String keyid = (commonFilterDto != null) ? commonFilterDto.getKeyid() : null;
        String factoryId = (commonFilterDto != null) ? commonFilterDto.getFactoryId() : null;
        String companyId = (commonFilterDto != null) ? commonFilterDto.getCompanyId() : null;
        String locationId = (commonFilterDto != null) ? commonFilterDto.getLocationId() : null;
        String sbuId = (commonFilterDto != null) ? commonFilterDto.getSbuId() : null;
        String pbuId = (commonFilterDto != null) ? commonFilterDto.getPbuId() : null;
        String ap = (commonFilterDto != null) ? commonFilterDto.getAp() : null;
        String code = (commonFilterDto != null) ? commonFilterDto.getCode() : null;

        StringBuilder condSql = new StringBuilder();

        if (!"frmFilter".equals(ap)) {
            condSql.append(" AND SECT_ACTIVE = 'Y' ");
        }

        // FIXED: was SECT_COMPANYID — the view exposes this as COMP_KEYID
        if (ValidationUtil.isValidKeyId(companyId)) {
            condSql.append(" AND COMP_KEYID = ? ");
            params.add(companyId);
        }
        if (ValidationUtil.isValidKeyId(locationId)) {
            condSql.append(" AND LOCN_KEYID = ? ");
            params.add(locationId);
        }
        if (ValidationUtil.isValidKeyId(sbuId)) {
            condSql.append(" AND SBUT_KEYID = ? ");
            params.add(sbuId);
        }
        if (ValidationUtil.isValidKeyId(pbuId)) {
            condSql.append(" AND PBUT_KEYID = ? ");
            params.add(pbuId);
        }
        if (ValidationUtil.isValidKeyId(factoryId)) {
            condSql.append(" AND FACT_KEYID = ? ");
            params.add(factoryId);
        }

        if (ValidationUtil.isValidKeyId(code)) {
            condSql.append(" AND UPPER(SECT_CODE || ' - ' || SECT_NAME) LIKE UPPER(?) ");
            params.add("%" + code + "%");
        }

        if (ValidationUtil.isValidKeyId(keyid) && keyid.length() >= 3 && keyid.substring(0, 3).equals("LIN")) {
            condSql.append(" AND SECT_KEYID <> ? ");
            params.add(keyid);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     @Override
     public List<DropDownDto> getCellComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_VW_FACTORYLAYOUT");
        comboFilter.setIdField("CELL_KEYID");
        comboFilter.setNameField("TRIM(CELL_NAME)");
        comboFilter.setCodeField("TRIM(CELL_CODE)");
        comboFilter.setOrderByField("LABEL");

        String keyid = (commonFilterDto != null) ? commonFilterDto.getKeyid() : null;
        String sectionid = (commonFilterDto != null) ? commonFilterDto.getSectionid() : null;
        String lineNotToShown = (commonFilterDto != null) ? commonFilterDto.getLineNotToShown() : null;
        String pcsEnabled = (commonFilterDto != null) ? commonFilterDto.getPcsEnabled() : "N";
        String companyId = (commonFilterDto != null) ? commonFilterDto.getCompanyId() : null;
        String locationId = (commonFilterDto != null) ? commonFilterDto.getLocationId() : null;
        String sbuId = (commonFilterDto != null) ? commonFilterDto.getSbuId() : null;
        String pbuId = (commonFilterDto != null) ? commonFilterDto.getPbuId() : null;
        String factoryId = (commonFilterDto != null) ? commonFilterDto.getFactoryId() : null;
        String costCenterId = (commonFilterDto != null) ? commonFilterDto.getCostCenterId() : null;
        String ap = (commonFilterDto != null) ? commonFilterDto.getAp() : null;
        String code = (commonFilterDto != null) ? commonFilterDto.getCode() : null;

        StringBuilder condSql = new StringBuilder();

        if (!"frmFilter".equals(ap) && !"frmuniqueposition".equals(ap)) {
            condSql.append(" AND CELL_ACTIVE = 'Y' ");
        }

        if (ValidationUtil.isValidKeyId(costCenterId)) {
            condSql.append(" AND CELL_COSTCENTREID = ? ");
            params.add(costCenterId);
        }

        if ("Y".equals(pcsEnabled)) {
            condSql.append(" AND CELL_KEYID IN (SELECT PELC_CELLID FROM PCS_TL_ENABLELOSSCAPTURE WHERE PELC_ISPCSENABLED = 'Y') ");
        }

        // NOTE: inferred column names for the flattened view — verify each
        // against raw SQL the way COMP_KEYID was confirmed for company.
        if (ValidationUtil.isValidKeyId(companyId)) {
            condSql.append(" AND COMP_KEYID = ? ");
            params.add(companyId);
        }
        if (ValidationUtil.isValidKeyId(locationId)) {
            condSql.append(" AND LOCN_KEYID = ? ");
            params.add(locationId);
        }
        if (ValidationUtil.isValidKeyId(sbuId)) {
            condSql.append(" AND SBUT_KEYID = ? ");
            params.add(sbuId);
        }
        if (ValidationUtil.isValidKeyId(pbuId)) {
            condSql.append(" AND PBUT_KEYID = ? ");
            params.add(pbuId);
        }
        if (ValidationUtil.isValidKeyId(factoryId)) {
            condSql.append(" AND FACT_KEYID = ? ");
            params.add(factoryId);
        }

        if (ValidationUtil.isValidKeyId(code)) {
            condSql.append(" AND UPPER(CELL_CODE || ' - ' || CELL_NAME) LIKE UPPER(?) ");
            params.add("%" + code + "%");
        }

        // NOTE: matches legacy behavior exactly — keyid/sectionid conditions
        // OVERWRITE the lineNotToShown exclusion below if either is present,
        // since the legacy code called setCondSql() a second time. If keyid
        // or sectionid is set, lineNotToShown is silently ignored.
        StringBuilder overrideCondSql = new StringBuilder();
        List<Object> overrideParams = new ArrayList<>();

        if (ValidationUtil.isValidKeyId(keyid) && keyid.length() >= 3 && keyid.substring(0, 3).equals("CEL")) {
            overrideCondSql.append(" AND CELL_KEYID <> ? ");
            overrideParams.add(keyid);
        }
        if (ValidationUtil.isValidKeyId(sectionid)) {
            overrideCondSql.append(" AND CELL_SECTIONID = ? ");
            overrideParams.add(sectionid);
        }

        if (overrideCondSql.length() > 0) {
            condSql.append(overrideCondSql);
            params.addAll(overrideParams);
        } else if (ValidationUtil.isValidKeyId(lineNotToShown)) {
            condSql.append(" AND CELL_KEYID <> ? ");
            params.add(lineNotToShown);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     @Override
     public List<DropDownDto> getPhenomenaCombo(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("PCS_TL_LOSSPHENOMENAMST");
        comboFilter.setIdField("PLPM_KEYID");
        comboFilter.setNameField("PLPM_NAME");

        String flid = (commonFilterDto != null) ? commonFilterDto.getFlId() : null;

        // NOTE: lossId/cellId-based filtering exists in legacy code but is
        // entirely commented out there — not ported, matching current
        // (inactive) behavior. Only the flid condition is live.
        if (ValidationUtil.isValidKeyId(flid)) {
            comboFilter.setCondSql("""
                    AND PLPM_KEYID IN (
                        SELECT PPFL_PLPM_KEYID
                        FROM PCS_TL_LOSSPHENFACTORYLINK
                        WHERE PPFL_FACTORYID = ?
                    )
                    """);
            params.add(flid);
        }

        return repository.fillComboValues(comboFilter, params.toArray());
     }
 @Override
    public List<DropDownDto> getLossCombo(CommonFilterDto commonFilterDto)
    {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();
        comboFilter.setTableName("PCS_VW_LOSSNAMESFORENTRY");
        comboFilter.setIdField("KEYID");
        comboFilter.setNameField("CHILDLOSS");
        // NOTE: intentionally NOT calling setOrderByField("ORDERNO") here.
        // Postgres rejects "SELECT DISTINCT ... ORDER BY ORDERNO" because
        // ORDERNO isn't in the select list, and fillComboValues() in the
        // shared repository can't be changed to add it. Leaving orderByField
        // unset makes the repo fall back to its default "ORDER BY LABEL",
        // which is valid SQL. We then re-sort by ORDERNO ourselves below.

        String isQtyLoss = (commonFilterDto != null) ? commonFilterDto.getIsQtyLoss() : null;
        String sectId = (commonFilterDto != null) ? commonFilterDto.getSectionId() : null;

        AdmTlConfigurationmst mouldConfig = configurationmstRepository.findByCode("MOULD_SECTION_CODES");
        String mouldSectionCode = (mouldConfig != null) ? mouldConfig.getSettingvalue() : null;

        String sectCode = null;
        if (ValidationUtil.isValidKeyId(sectId)) {
            GenTlSectionmst section = sectionmstRepository.findByKeyid(sectId);
            sectCode = (section != null) ? section.getCode() : null;
        }

        StringBuilder condSql = new StringBuilder();
        if (ValidationUtil.isValidKeyId(sectCode) && ValidationUtil.isValidKeyId(mouldSectionCode)
                && mouldSectionCode.indexOf(sectCode) >= 0) {
            condSql.append(" AND UPPER(CHILDLOSS) NOT LIKE '%NO PLAN%' ");
        }

        if ("Y".equals(isQtyLoss)) {
            condSql = new StringBuilder("""
                    AND KEYID IN (
                        SELECT PLCM_KEYID FROM PCS_TL_LOGCONFIGURATION
                        WHERE PLCM_UOM = (SELECT UOMM_KEYID FROM ADM_TL_UOMMST WHERE UOMM_CODE = 'NOS')
                    )
                    """);
        } else if ("N".equals(isQtyLoss)) {
            condSql = new StringBuilder("""
                    AND KEYID IN (
                        SELECT PLCM_KEYID FROM PCS_TL_LOGCONFIGURATION
                        WHERE PLCM_UOM NOT IN (SELECT UOMM_KEYID FROM ADM_TL_UOMMST WHERE UOMM_CODE = 'NOS')
                    )
                    """);
        }

        comboFilter.setCondSql(condSql.toString());

        List<DropDownDto> result = repository.fillComboValues(comboFilter, params.toArray());

        // ---- Re-sort by ORDERNO in Java since the repo can't select it ----
        Map<String, Integer> orderMap = new HashMap<>();
        jdbcTemplate.query(
                "SELECT DISTINCT KEYID, ORDERNO FROM PCS_VW_LOSSNAMESFORENTRY",
                rs -> {
                    orderMap.put(rs.getString("KEYID"), rs.getInt("ORDERNO"));
                }
        );

        result.sort(Comparator.comparingInt(
        d -> orderMap.getOrDefault(d.value(), Integer.MAX_VALUE)));
        // ---------------------------------------------------------------

        return result;
    }


    @Override
     public List<DropDownDto> getEquipmentNameCombo(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("PCS_TL_EQUIPMENT");
        comboFilter.setIdField("PLE_KEYID");
        comboFilter.setNameField("PLE_NAME");

        return repository.fillComboValues(comboFilter, params.toArray());
     }

    @Override
public List<DropDownDto> getLossComboList(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    String lossFrom = commonFilterDto != null ? commonFilterDto.getLossFrom() : null;
    logger.info("lossFrom = {}", lossFrom);

    if ("PCSLogConfig".equals(lossFrom))
    {
        comboFilter.setIdField("PLCM_KEYID");
        comboFilter.setNameField("PLCM_LOSSNO");
        comboFilter.setCodeField("REPLACE(PLCM_PARAMETERNAME, '(+)', '')");
        comboFilter.setTableName("PCS_TL_LOGCONFIGURATION");
        comboFilter.setOrderByField("LABEL");

        comboFilter.setCondSql("""
                                AND PLCM_ISLOSS = 'M'
                             """);
    }
    else
    {
        logger.warn("getLossComboList called with unsupported/missing lossFrom: {}", lossFrom);
        return new ArrayList<>();
    }

    return repository.fillComboValues(comboFilter, params.toArray());
}

@Override
     public List<DropDownDto> getJHKaizenBeltComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("KZN_TL_BELTDETAILSMST");
        comboFilter.setIdField("KBEL_KEYID");
        comboFilter.setNameField("KBEL_NAME");

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     @Override
     public List<DropDownDto> getJHKaizenCategoryComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("KZN_TL_CATEGORYMST");
        comboFilter.setIdField("KCTM_KEYID");
        comboFilter.setNameField("KCTM_NAME");

        return repository.fillComboValues(comboFilter, params.toArray());
     }

     @Override
     public List<DropDownDto> getProjectMetricsKpiIndicator(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("KPI_VW_INDICATOR_PATH");
        comboFilter.setIdField("KINK_KEYID");
        comboFilter.setCodeField("INDICATOR_PATH");

        String flid = (commonFilterDto != null) ? commonFilterDto.getFlId() : null;
        String pillarCode = (commonFilterDto != null) ? commonFilterDto.getPillarCode() : null;

        StringBuilder condSql = new StringBuilder();

        if (ValidationUtil.isValidKeyId(flid)) {
            condSql.append("""
                    AND KINK_KEYID IN (
                        SELECT KINK_KEYID
                        FROM KPI_TL_INDICATOR, KPI_TL_INDICATOR_DEPT_LINK, GEN_MV_FLIDHIERARCHY
                        WHERE KINK_KEYID = KIDL_INDICATORID
                        AND FLID = KIDL_DEPTID
                        AND POSITION(? IN (PARENTFLIDS || '-' || FLID)) > 0
                        AND KINK_TYPE = 'KPI'
                    )
                    """);
            params.add(flid);
        }

        if (ValidationUtil.isValidKeyId(pillarCode)) {
            condSql.append(" AND TPMP_CODE = ? ");
            params.add(pillarCode);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     @Override
     public List<DropDownDto> getComboWave(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("KZN_TL_PROJECTCREATIONMST");
        comboFilter.setIdField("KZPM_WAVE");
        comboFilter.setNameField("KZPM_WAVE");

        String pillarid = (commonFilterDto != null) ? commonFilterDto.getPillarid() : null;
        String flid = (commonFilterDto != null) ? commonFilterDto.getFlId() : null;
        String pillargrp = (commonFilterDto != null) ? commonFilterDto.getPillargrp() : null;

        StringBuilder condSql = new StringBuilder();

        // NOTE: matches legacy behavior exactly — condition is applied
        // whenever pillargrp is non-null (not empty-string-safe like
        // ValidationUtil.isValidKeyId), regardless of whether pillarid/flid
        // themselves are valid.
        if (pillargrp != null) {
            condSql.append(" AND MGRM_PILLARID = ? AND MGRM_FLID = ? ");
            params.add(pillarid);
            params.add(flid);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     @Override
     public List<DropDownDto> getDmcEmployeeCombo(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_EMPLOYEEMST,GEN_TL_FNLNROLETEAM,GEN_VW_FNLN,ADM_TL_ROLEMST");
        comboFilter.setIdField("EMPM_KEYID");
        comboFilter.setNameField("EMPM_NAME");
        comboFilter.setCodeField("EMPM_CODE");

        String flid = (commonFilterDto != null) ? commonFilterDto.getFlId() : null;

        StringBuilder condSql = new StringBuilder();
        condSql.append(" AND FRT_EMPM_KEYID = EMPM_KEYID ");
        condSql.append(" AND FRT_FNLN_KEYID = FNLN_KEYID ");
        condSql.append(" AND DISPLAYCODE IN ('LOCN','SBU','PBU','SECT') ");
        condSql.append(" AND FRT_ACTIVE = 'Y' ");
        condSql.append(" AND EMPM_ACTIVE = 'Y' ");
        condSql.append(" AND FRT_ROLE_KEYID = ROLE_KEYID ");
        condSql.append(" AND ROLE_KEYID NOT IN ('AROL0068') ");

        if (ValidationUtil.isValidKeyId(flid)) {
            condSql.append(" AND EMPM_LOCATION = (SELECT LOCN_KEYID FROM GEN_VW_FNLN WHERE FNLN_KEYID = ?) ");
            params.add(flid);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     @Override
     public List<DropDownDto> getEmployeeList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_EMPLOYEEMST");
        comboFilter.setIdField("EMPM_KEYID");
        comboFilter.setNameField("TRIM(EMPM_NAME)");
        comboFilter.setCodeField("TRIM(EMPM_CODE)");
        comboFilter.setOrderByField("LABEL");

        String isPbuHead = commonFilterDto != null ? commonFilterDto.getIsPbuHead() : null;
        String gradeId = commonFilterDto != null ? commonFilterDto.getGradeId() : null;
        String trade = commonFilterDto != null ? commonFilterDto.getTradeid() : null;
        String rtal = commonFilterDto != null ? commonFilterDto.getRtal() : null;
        String roleId = commonFilterDto != null ? commonFilterDto.getRoleId() : null;
        String cellId = commonFilterDto != null ? commonFilterDto.getCellId() : null;
        String mgr = commonFilterDto != null ? commonFilterDto.getMgr() : null;
        String flid = commonFilterDto != null ? commonFilterDto.getFlId() : null;
        String locnId = commonFilterDto != null ? commonFilterDto.getLocnId() : null;
        String others = commonFilterDto != null ? commonFilterDto.getOthers() : null;
        String loginEmpshow = commonFilterDto != null ? commonFilterDto.getLoginEmpshow() : null;
        String locn = commonFilterDto != null ? commonFilterDto.getLocn() : null;
        String isDmtLeader = commonFilterDto != null ? commonFilterDto.getIsDmtLeader() : null;
        String dmtId = commonFilterDto != null ? commonFilterDto.getDmtId() : null;
        String currentUserCcno = commonFilterDto != null ? commonFilterDto.getCurrentUserCcno() : null;
        String code = commonFilterDto != null ? commonFilterDto.getCode() : null;
        String comboKey = commonFilterDto != null ? commonFilterDto.getComboKey() : null;

        // loginEmpshow: fold the current user's CC-No into the id-highlight
        // list, unless explicitly turned off with "false"
        String idList = comboKey;
        if (!"false".equals(loginEmpshow) && ValidationUtil.isValidKeyId(currentUserCcno)) {
            idList = ValidationUtil.isValidKeyId(idList) ? (idList + "," + currentUserCcno) : currentUserCcno;
        }

        StringBuilder condSql = new StringBuilder(" AND EMPM_ACTIVE = 'Y' ");

        if (ValidationUtil.isValidKeyId(cellId) && ValidationUtil.isValidKeyId(trade)) {

            StringBuilder sub = new StringBuilder();
            sub.append(" SELECT DISTINCT EMPM_KEYID ");
            sub.append(" FROM GEN_TL_EMPLOYEEMST, GEN_TL_TEAMTRADELINK, GEN_TL_FNLNROLETEAM ");
            sub.append(" WHERE FRP_FRT_KEYID = FRT_KEYID AND FRT_EMPM_KEYID = EMPM_KEYID AND EMPM_ACTIVE = 'Y' ");

            if (!"others".equals(trade)) {
                sub.append(" AND FRP_TRADEID = ? ");
                params.add(trade);
            }

            if ("Y".equals(others)) {
                sub.append(" AND FRT_FNLN_KEYID NOT IN ( ");
            } else {
                sub.append(" AND FRT_FNLN_KEYID IN ( ");
            }
            sub.append(" SELECT flid FROM gen_mv_flidhierarchy ");
            sub.append(" WHERE POSITION((SELECT CELL_FLID FROM GEN_TL_CELLMST WHERE CELL_KEYID = ?) IN (parentflids || flid)) > 0 ) ");
            params.add(cellId);

            condSql.append(" AND EMPM_KEYID IN (").append(sub).append(") ");

        } else if (ValidationUtil.isValidKeyId(cellId)) {

            if ("Y".equals(others)) {
                condSql.append(" AND EMPM_KEYID NOT IN (SELECT DISTINCT EMPM_KEYID FROM GEN_TL_EMPLOYEEMST, GEN_TL_FNLNROLETEAM WHERE FRT_EMPM_KEYID = EMPM_KEYID AND FRT_FNLN_KEYID IN (SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE FNLN_ORIGINALID = ?)) ");
                params.add(cellId);
            } else if ("Y".equals(locn)) {
                condSql.append(" AND EMPM_KEYID NOT IN (SELECT DISTINCT EMPM_KEYID FROM GEN_TL_EMPLOYEEMST, GEN_TL_FNLNROLETEAM WHERE FRT_EMPM_KEYID = EMPM_KEYID AND EMPM_LOCATION = ? AND FRT_FNLN_KEYID IN (SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE FNLN_ORIGINALID = ?)) ");
                params.add(locnId);
                params.add(cellId);
            } else {
                condSql.append(" AND EMPM_KEYID IN (SELECT DISTINCT EMPM_KEYID FROM GEN_TL_EMPLOYEEMST, GEN_TL_FNLNROLETEAM WHERE FRT_EMPM_KEYID = EMPM_KEYID AND FRT_FNLN_KEYID IN (SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE FNLN_ORIGINALID = ?)) ");
                params.add(cellId);
            }

        } else if (ValidationUtil.isValidKeyId(trade)) {

            StringBuilder sub = new StringBuilder();
            sub.append(" SELECT DISTINCT EMPM_KEYID ");
            sub.append(" FROM GEN_TL_EMPLOYEEMST, GEN_TL_TEAMTRADELINK, GEN_TL_FNLNROLETEAM ");
            sub.append(" WHERE FRP_FRT_KEYID = FRT_KEYID AND FRT_EMPM_KEYID = EMPM_KEYID AND EMPM_ACTIVE = 'Y' ");

            if (!"others".equals(trade)) {
                sub.append(" AND FRP_TRADEID = ? ");
                params.add(trade);
            }

            if (ValidationUtil.isValidKeyId(flid)) {
                if ("Y".equals(others)) {
                    sub.append(" AND FRT_FNLN_KEYID NOT IN ");
                } else {
                    sub.append(" AND FRT_FNLN_KEYID IN ");
                }
                sub.append(" (SELECT flid FROM gen_mv_flidhierarchy WHERE POSITION(? IN (parentflids || flid)) > 0) ");
                params.add(flid);
            }

            condSql.append(" AND EMPM_KEYID IN (").append(sub).append(") ");
        }

        if (ValidationUtil.isValidKeyId(gradeId)) {
            condSql.append(" AND EMPM_GRADEID IN ('{}', (SELECT GRDM_KEYID FROM gen_tl_empgrademst WHERE GRDM_ACTIVE = 'Y' AND GRDM_KEYID = ?)) ");
            params.add(gradeId);
        } else if (ValidationUtil.isValidKeyId(mgr)) {
            condSql.append(" AND EMPM_KEYID IN (SELECT EEMM_MANAGER_ID FROM ENT_TL_EMPMANAGERMST) ");
        }

        if (!ValidationUtil.isValidKeyId(isPbuHead) && ValidationUtil.isValidKeyId(flid)) {
            if (!ValidationUtil.isValidKeyId(trade)) {
                condSql.append(" AND EMPM_KEYID IN (SELECT DISTINCT EMPM_KEYID FROM GEN_TL_EMPLOYEEMST, GEN_TL_FNLNROLETEAM WHERE FRT_EMPM_KEYID = EMPM_KEYID AND FRT_FNLN_KEYID = ? ");
                params.add(flid);
                if ("Y".equals(others)) {
                    condSql.append(" UNION SELECT EMPM_KEYID FROM GEN_TL_EMPLOYEEMST WHERE EMPM_KEYID NOT IN (SELECT FRT_EMPM_KEYID FROM GEN_TL_FNLNROLETEAM WHERE FRT_FNLN_KEYID = ?) ");
                    params.add(flid);
                }
                condSql.append(") ");
            }
        } else if (ValidationUtil.isValidKeyId(roleId)) {
            condSql.append(" AND EMPM_ROLEID = ? ");
            params.add(roleId);
            rtal = ValidationUtil.isValidKeyId(rtal) ? (rtal + "," + roleId) : roleId;
        } else if (ValidationUtil.isValidKeyId(isPbuHead)) {
            // NOTE: matches legacy exactly — OVERWRITES condSql entirely,
            // discarding EMPM_ACTIVE and every condition built above.
            condSql = new StringBuilder();
            condSql.append(" AND EMPM_KEYID IN (SELECT DISTINCT FRT_EMPM_KEYID FROM GEN_TL_FNLNROLETEAM WHERE FRT_FNLN_KEYID IN (SELECT FNLN_KEYID FROM GEN_VW_FNLN WHERE FNLN_ORIGINALID IN (SELECT PBUT_KEYID FROM GEN_VW_FNLN WHERE FNLN_KEYID = ?)) AND FRT_ROLE_KEYID IN (SELECT ROLE_KEYID FROM ADM_TL_ROLEMST WHERE ROLE_NAME = 'PBU HEAD')) ");
            params.clear();
            params.add(flid);
        } else if (ValidationUtil.isValidKeyId(isDmtLeader)) {
            // NOTE: matches legacy exactly — also OVERWRITES condSql entirely.
            condSql = new StringBuilder();
            condSql.append(" AND EMPM_KEYID IN (SELECT DISTINCT FRT_EMPM_KEYID FROM GEN_TL_FNLNROLETEAM WHERE FRT_FNLN_KEYID IN (SELECT FNLN_KEYID FROM GEN_VW_FNLN WHERE FNLN_ORIGINALID = ?) AND FRT_ROLE_KEYID IN (SELECT ROLE_KEYID FROM ADM_TL_ROLEMST WHERE ROLE_NAME = 'DMT LEADER')) ");
            params.clear();
            params.add(dmtId);
        }

        if (ValidationUtil.isValidKeyId(locnId) && !ValidationUtil.isValidKeyId(isPbuHead)) {
            condSql.append(" AND EMPM_LOCATION = ? ");
            params.add(locnId);
        }

        // Role-exclusion filter (rtal), ported from getEmployeeComboList(filter, rtal)
        if (ValidationUtil.isValidKeyId(rtal) && !"empFilter".equals(rtal)) {
            String[] rtlRole = rtal.split(",");
            if (rtlRole.length > 1 && ValidationUtil.isValidKeyId(rtlRole[1])) {
                condSql.append(" AND EMPM_ROLEID = ? AND EMPM_KEYID NOT IN (SELECT EREL_EMPM_KEYID FROM ENT_TL_ROLE_EMP_LINK WHERE EREL_RTAL_KEYID = ?) ");
                params.add(rtlRole[1]);
                params.add(rtlRole[0]);
            } else {
                condSql.append(" AND EMPM_KEYID NOT IN (SELECT EREL_EMPM_KEYID FROM ENT_TL_ROLE_EMP_LINK WHERE EREL_ACTIVE = 'Y') ");
            }
        }

        // Type-ahead search box filter
        if (ValidationUtil.isValidKeyId(code)) {
            condSql.append(" AND UPPER(EMPM_NAME || '-' || EMPM_CODE) LIKE UPPER(?) ");
            params.add("%" + code + "%");
        }

        comboFilter.setCondSql(condSql.toString());

        if (ValidationUtil.isValidKeyId(idList)) {
            comboFilter.setId(idList);
        }

        return repository.fillComboValues(comboFilter, params.toArray());
     }

   //   @Override
   //   public List<DropDownDto> getEmployeeCombo(CommonFilterDto commonFilterDto)
   //   {
   //      ComboFilterDto comboFilter = new ComboFilterDto();
   //      List<Object> params = new ArrayList<>();

   //      comboFilter.setTableName("GEN_TL_EMPLOYEEMST");
   //      comboFilter.setIdField("EMPM_KEYID");
   //      comboFilter.setNameField("TRIM(EMPM_NAME)");
   //      comboFilter.setCodeField("TRIM(EMPM_CODE)");
   //      comboFilter.setOrderByField("LABEL");

   //      String locnid = commonFilterDto != null ? commonFilterDto.getLocnId() : null;
   //      String cellid = commonFilterDto != null ? commonFilterDto.getCellId() : null;
   //      String rtal = commonFilterDto != null ? commonFilterDto.getRtal() : null;
   //      String tradeid = commonFilterDto != null ? commonFilterDto.getTradeid() : null;
   //      String yymode = commonFilterDto != null ? commonFilterDto.getYymode() : null;
   //      String others = commonFilterDto != null ? commonFilterDto.getOthers() : null;
   //      String currentUserCcno = commonFilterDto != null ? commonFilterDto.getCurrentUserCcno() : null;
   //      String comboKey = commonFilterDto != null ? commonFilterDto.getComboKey() : null;

   //      String idList = comboKey;
   //      if (ValidationUtil.isValidKeyId(currentUserCcno)) {
   //          idList = ValidationUtil.isValidKeyId(idList) ? (idList + "," + currentUserCcno) : currentUserCcno;
   //      }

   //      StringBuilder condSql = new StringBuilder(" AND EMPM_ACTIVE = 'Y' ");

   //      if (ValidationUtil.isValidKeyId(cellid)) {

   //          StringBuilder sub = new StringBuilder();
   //          sub.append(" SELECT DISTINCT EMPM_KEYID ");
   //          sub.append(" FROM GEN_TL_EMPLOYEEMST E ");
   //          sub.append(" JOIN GEN_TL_FNLNROLETEAM R ON R.FRT_EMPM_KEYID = E.EMPM_KEYID ");

   //          if (ValidationUtil.isValidKeyId(tradeid) && "Y".equals(yymode) && "N".equals(others)) {

   //              sub.append(" JOIN GEN_TL_TEAMTRADELINK T ON T.FRP_FRT_KEYID = R.FRT_KEYID ");
   //              sub.append(" WHERE E.EMPM_ACTIVE = 'Y' ");
   //              sub.append(" AND R.FRT_FNLN_KEYID IN (SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE FNLN_ORIGINALID = ?) ");
   //              sub.append(" AND FRP_TRADEID = ? ");
   //              params.add(cellid);
   //              params.add(tradeid);

   //          } else if (ValidationUtil.isValidKeyId(tradeid) && "Y".equals(yymode) && "Y".equals(others)) {

   //              sub.append(" JOIN GEN_TL_TEAMTRADELINK T ON T.FRP_FRT_KEYID = R.FRT_KEYID ");
   //              sub.append(" WHERE E.EMPM_ACTIVE = 'Y' ");
   //              // NOTE: matches legacy exactly — cell/trade filter lines for
   //              // this branch are commented out in the source, so this
   //              // branch just requires an active employee with any
   //              // team-trade link at all.

   //          } else {

   //              sub.append(" WHERE E.EMPM_ACTIVE = 'Y' ");
   //              sub.append(" AND R.FRT_FNLN_KEYID IN (SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE FNLN_ORIGINALID = ?) ");
   //              params.add(cellid);
   //          }

   //          condSql.append(" AND EMPM_KEYID IN (").append(sub).append(") ");
   //      }

   //      if (ValidationUtil.isValidKeyId(locnid)) {
   //          condSql.append(" AND EMPM_LOCATION = ? ");
   //          params.add(locnid);
   //      }

   //      if (ValidationUtil.isValidKeyId(rtal) && !"empFilter".equals(rtal)) {
   //          String[] rtlRole = rtal.split(",");
   //          if (rtlRole.length > 1 && ValidationUtil.isValidKeyId(rtlRole[1])) {
   //              condSql.append(" AND EMPM_ROLEID = ? AND EMPM_KEYID NOT IN (SELECT EREL_EMPM_KEYID FROM ENT_TL_ROLE_EMP_LINK WHERE EREL_RTAL_KEYID = ?) ");
   //              params.add(rtlRole[1]);
   //              params.add(rtlRole[0]);
   //          } else {
   //              condSql.append(" AND EMPM_KEYID NOT IN (SELECT EREL_EMPM_KEYID FROM ENT_TL_ROLE_EMP_LINK WHERE EREL_ACTIVE = 'Y') ");
   //          }
   //      }

   //      comboFilter.setCondSql(condSql.toString());

   //      Object[] paramsArray = params.toArray();

   //      if (ValidationUtil.isValidKeyId(idList)) {
   //          comboFilter.setId(idList);

   //          // fillComboValues embeds condSql into BOTH UNION branches when
   //          // an id is set, but only consumes one copy of the params array
   //          // for all placeholders across both branches — so we must
   //          // duplicate the params to match.
   //          if (!"grid".equals(comboFilter.getMode()) && !ValidationUtil.isValidKeyId(comboFilter.getName())) {
   //              List<Object> doubled = new ArrayList<>(params);
   //              doubled.addAll(params);
   //              paramsArray = doubled.toArray();
   //          }
   //      }
   //     return repository.fillComboValues(comboFilter, paramsArray);
   //   }

   @Override
     public List<DropDownDto> getEmployeeCombo(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_EMPLOYEEMST");
        comboFilter.setIdField("EMPM_KEYID");
        comboFilter.setNameField("TRIM(EMPM_NAME)");
        comboFilter.setCodeField("TRIM(EMPM_CODE)");
        comboFilter.setOrderByField("LABEL");

        String locnid = commonFilterDto != null ? commonFilterDto.getLocnId() : null;
        String cellid = commonFilterDto != null ? commonFilterDto.getCellId() : null;
        String rtal = commonFilterDto != null ? commonFilterDto.getRtal() : null;
        String tradeid = commonFilterDto != null ? commonFilterDto.getTradeid() : null;
        String yymode = commonFilterDto != null ? commonFilterDto.getYymode() : null;
        String others = commonFilterDto != null ? commonFilterDto.getOthers() : null;
        String currentUserCcno = commonFilterDto != null ? commonFilterDto.getCurrentUserCcno() : null;
        String comboKey = commonFilterDto != null ? commonFilterDto.getComboKey() : null;

        String idList = comboKey;
        if (ValidationUtil.isValidKeyId(currentUserCcno)) {
            idList = ValidationUtil.isValidKeyId(idList) ? (idList + "," + currentUserCcno) : currentUserCcno;
        }

        StringBuilder condSql = new StringBuilder(" AND EMPM_ACTIVE = 'Y' ");

        if (ValidationUtil.isValidKeyId(cellid)) {

            StringBuilder sub = new StringBuilder();
            sub.append(" SELECT DISTINCT EMPM_KEYID ");
            sub.append(" FROM GEN_TL_EMPLOYEEMST E ");
            sub.append(" JOIN GEN_TL_FNLNROLETEAM R ON R.FRT_EMPM_KEYID = E.EMPM_KEYID ");

            if (ValidationUtil.isValidKeyId(tradeid) && "Y".equals(yymode) && "N".equals(others)) {

                sub.append(" JOIN GEN_TL_TEAMTRADELINK T ON T.FRP_FRT_KEYID = R.FRT_KEYID ");
                sub.append(" WHERE E.EMPM_ACTIVE = 'Y' ");
                sub.append(" AND R.FRT_FNLN_KEYID IN (SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE FNLN_ORIGINALID = ?) ");
                sub.append(" AND FRP_TRADEID = ? ");
                params.add(cellid);
                params.add(tradeid);

            } else if (ValidationUtil.isValidKeyId(tradeid) && "Y".equals(yymode) && "Y".equals(others)) {

                sub.append(" JOIN GEN_TL_TEAMTRADELINK T ON T.FRP_FRT_KEYID = R.FRT_KEYID ");
                sub.append(" WHERE E.EMPM_ACTIVE = 'Y' ");

            } else {

                sub.append(" WHERE E.EMPM_ACTIVE = 'Y' ");
                sub.append(" AND R.FRT_FNLN_KEYID IN (SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE FNLN_ORIGINALID = ?) ");
                params.add(cellid);
            }

            condSql.append(" AND EMPM_KEYID IN (").append(sub).append(") ");
        }

        if (ValidationUtil.isValidKeyId(locnid)) {
            condSql.append(" AND EMPM_LOCATION = ? ");
            params.add(locnid);
        }

        if (ValidationUtil.isValidKeyId(rtal) && !"empFilter".equals(rtal)) {
            String[] rtlRole = rtal.split(",");
            if (rtlRole.length > 1 && ValidationUtil.isValidKeyId(rtlRole[1])) {
                condSql.append(" AND EMPM_ROLEID = ? AND EMPM_KEYID NOT IN (SELECT EREL_EMPM_KEYID FROM ENT_TL_ROLE_EMP_LINK WHERE EREL_RTAL_KEYID = ?) ");
                params.add(rtlRole[1]);
                params.add(rtlRole[0]);
            } else {
                condSql.append(" AND EMPM_KEYID NOT IN (SELECT EREL_EMPM_KEYID FROM ENT_TL_ROLE_EMP_LINK WHERE EREL_ACTIVE = 'Y') ");
            }
        }

        if (!ValidationUtil.isValidKeyId(idList)) {
            comboFilter.setCondSql(condSql.toString());
            return repository.fillComboValues(comboFilter, params.toArray());
        }

        // idList is set — replicate the legacy UNION manually instead of
        // using comboFilter.setId(...), since fillComboValues applies
        // condSql to BOTH UNION branches, but legacy behavior only applies
        // it to the "rest" branch, not the highlighted-id branch.
        List<DropDownDto> result = new ArrayList<>();
        String[] ids = idList.split(",");

        // Branch 1 — highlighted id(s): active + id match only, no extra filter
        ComboFilterDto idFilter = new ComboFilterDto();
        idFilter.setTableName("GEN_TL_EMPLOYEEMST");
        idFilter.setIdField("EMPM_KEYID");
        idFilter.setNameField("TRIM(EMPM_NAME)");
        idFilter.setCodeField("TRIM(EMPM_CODE)");
        idFilter.setOrderByField("LABEL");

        StringBuilder idCond = new StringBuilder(" AND EMPM_ACTIVE = 'Y' ");
        List<Object> idParams = new ArrayList<>();
        if (ids.length > 1) {
            idCond.append(" AND EMPM_KEYID IN (");
            for (int i = 0; i < ids.length; i++) {
                idCond.append(i > 0 ? ",?" : "?");
                idParams.add(ids[i].trim());
            }
            idCond.append(") ");
        } else {
            idCond.append(" AND EMPM_KEYID = ? ");
            idParams.add(ids[0].trim());
        }
        idFilter.setCondSql(idCond.toString());
        result.addAll(repository.fillComboValues(idFilter, idParams.toArray()));

        // Branch 2 — everyone else, with the full filter, excluding the highlighted id(s)
        StringBuilder restCond = new StringBuilder(condSql);
        List<Object> restParams = new ArrayList<>(params);
        if (ids.length > 1) {
            restCond.append(" AND EMPM_KEYID NOT IN (");
            for (int i = 0; i < ids.length; i++) {
                restCond.append(i > 0 ? ",?" : "?");
                restParams.add(ids[i].trim());
            }
            restCond.append(") ");
        } else {
            restCond.append(" AND EMPM_KEYID <> ? ");
            restParams.add(ids[0].trim());
        }
        comboFilter.setCondSql(restCond.toString());
        result.addAll(repository.fillComboValues(comboFilter, restParams.toArray()));

        return result;
     }



//-------------------------------------------------HARI---------------------------------

//******************************Training Calender*********************************************************

 @Override
   public List<DropDownDto> getETTradeComboList(CommonFilterDto commonFilterDto) {
      ComboFilterDto comboFilter = new ComboFilterDto();
      List<Object> params = new ArrayList<>();

      comboFilter.setTableName("ENT_TL_TRGCALFUNCTION");
      comboFilter.setIdField("ETFN_KEYID");
      comboFilter.setNameField("ETFN_NAME");
      comboFilter.setId(commonFilterDto.getComboKey());

      return repository.fillComboValues(comboFilter, params.toArray());
   }



   @Override
   public List<DropDownDto> getFacultyComboList(CommonFilterDto commonFilterDto) {
      ComboFilterDto comboFilter = new ComboFilterDto();
      List<Object> params = new ArrayList<>();

      comboFilter.setIdField("EMPID");
      comboFilter.setNameField("EMPROLE");
      comboFilter.setTableName("ENT_VW_FACULTYMST_NEW");

      String sectId = commonFilterDto.getSectionId();
      String flId = commonFilterDto.getFlId();
      String locnid = commonFilterDto.getLocationId();
      String facultyFilterType = "Y".equals(commonFilterDto.getFacultyOthers()) ? "other" : "dmt";

      StringBuilder sb = new StringBuilder();

      String resolvedSectId = null;
      if (ValidationUtil.isValidKeyId(sectId)) {
         resolvedSectId = sectId;
      } else if (ValidationUtil.isValidKeyId(flId)) {
         resolvedSectId = repository.resolveSectId(flId);
         logger.info("flId {} resolved to sectId {}", flId, resolvedSectId);
      }

      if (ValidationUtil.isValidKeyId(resolvedSectId)) {
         if ("other".equals(facultyFilterType)) {
            sb.append(" AND faculty_sect_keyid IS NOT NULL");
            sb.append(" AND faculty_sect_keyid <> ?");
            sb.append(" AND emp_sect_keyid <> ?");
            params.add(resolvedSectId);
            params.add(resolvedSectId);
            if (ValidationUtil.isValidKeyId(locnid)) {
               sb.append(" AND locn_keyid = ?");
               params.add(locnid);
            }
         } else {
            sb.append(" AND (");
            sb.append("   faculty_sect_keyid = ?");
            sb.append("   OR emp_sect_keyid = ?");
            sb.append("   OR (faculty_sect_keyid IS NULL");
            params.add(resolvedSectId);
            params.add(resolvedSectId);
            if (ValidationUtil.isValidKeyId(locnid)) {
               sb.append("       AND locn_keyid = ?");
               params.add(locnid);
            }
            sb.append("   )");
            sb.append(" )");
         }
      } else if (ValidationUtil.isValidKeyId(locnid)) {
         sb.append(" AND locn_keyid = ?");
         params.add(locnid);
      }

      comboFilter.setCondSql(sb.toString());

      return repository.fillComboValues(comboFilter, params.toArray());
   }


 @Override
   public List<DropDownDto> getTopicComboList(CommonFilterDto commonFilterDto) {
      ComboFilterDto comboFilter = new ComboFilterDto();
      List<Object> params = new ArrayList<>();

      comboFilter.setIdField("TOPI_KEYID");
      comboFilter.setNameField("TOPI_NAME");
      comboFilter.setTableName("ENT_TL_TOPICMST");

      StringBuilder sb = new StringBuilder();

      if (ValidationUtil.isValidKeyId(commonFilterDto.getRoleId())) {
         logger.info("Role is {}", commonFilterDto.getRoleId());
         sb.append(
               " AND TOPI_KEYID IN (SELECT TMTM_TOPI_KEYID FROM ENT_TL_UNIQPOSTOPIC_LINKMST WHERE TMTM_ROLE_KEYID = ?)");
         params.add(commonFilterDto.getRoleId());
      }

      if (ValidationUtil.isValidKeyId(commonFilterDto.getTopicType())) {
         logger.info("Related is {}", commonFilterDto.getTopicType());
         sb.append(" AND TOPI_RELATEDTO = ?");
         params.add(commonFilterDto.getTopicType());
      }

      comboFilter.setCondSql(sb.toString());

      return repository.fillComboValues(comboFilter, params.toArray());
   }

@Override
   public List<DropDownDto> getRoleComboList(CommonFilterDto commonFilterDto) {
      ComboFilterDto comboFilter = new ComboFilterDto();
      List<Object> params = new ArrayList<>();

      comboFilter.setIdField("ROLE_KEYID");
      comboFilter.setNameField("ROLE_NAME");

      logger.info("{} commonfiltertopic", commonFilterDto.getFlId());

      StringBuilder keyIdCnd = new StringBuilder();
      List<Object> keyIdParams = new ArrayList<>();

      if (ValidationUtil.isValidKeyId(commonFilterDto.getRoleId())) {
         keyIdCnd.append(" AND ROLE_KEYID = ? ");
         keyIdParams.add(commonFilterDto.getRoleId());
      }

      if (ValidationUtil.isValidKeyId(commonFilterDto.getTopicId())) {
         keyIdCnd.append(" AND ROLE_KEYID IN ( ")
               .append(" SELECT TMTM_ROLE_KEYID ")
               .append(" FROM ENT_TL_UNIQPOSTOPIC_LINKMST ")
               .append(" WHERE TMTM_TOPI_KEYID = ? ")
               .append(")");
         keyIdParams.add(commonFilterDto.getTopicId());
      }

      if (ValidationUtil.isValidKeyId(commonFilterDto.getFlId())) {

         comboFilter.setNameField("ROLE_NAME || '-' || FNLN_DISPLAYCODE");

         StringBuilder cnd = new StringBuilder(" AND ROLE_FLID = FLID ");

         String type = ValidationUtil.isValidKeyId(commonFilterDto.getChildFlids())
               ? commonFilterDto.getChildFlids()
               : "Y";

         if ("Y".equals(type)) {
            cnd.append(" AND POSITION(? IN (PARENTFLIDS || FLID)) > 0 ");
            params.add(commonFilterDto.getFlId());
         } else {
            cnd.append(" AND FLID = ? ");
            params.add(commonFilterDto.getFlId());
         }

         params.addAll(keyIdParams);
         cnd.append(keyIdCnd);

         comboFilter.setCondSql(cnd.toString());
         comboFilter.setTableName("ent_vw_rolemst");

      } else {
         params.addAll(keyIdParams);
         comboFilter.setCondSql(keyIdCnd.toString());
         comboFilter.setTableName("GEN_TL_ROLEMST");
      }

      return repository.fillComboValues(comboFilter, params.toArray());
   }



 @Override
   public List<DropDownDto> getVenueComboList(CommonFilterDto commonFilterDto) {
      ComboFilterDto comboFilter = new ComboFilterDto();
      List<Object> params = new ArrayList<>();

      comboFilter.setIdField("VENU_KEYID");
      comboFilter.setCodeField("VENU_CODE");
      comboFilter.setNameField("VENU_NAME");
      comboFilter.setTableName("ENT_TL_VENUEMST");

      if (ValidationUtil.isValidKeyId(commonFilterDto.getFlId())) {
         StringBuilder sb = new StringBuilder();
         sb.append(" AND VENU_FLID IN (SELECT FNLN_KEYID FROM GEN_TL_FUNCTIONALLOCN WHERE ");
         sb.append(" FNLN_ORIGINALID IN (SELECT SUBSTR(FNLN_ELEMENTID,12,10) ");
         sb.append(" FROM GEN_VW_FNLN WHERE FNLN_KEYID = ?) ) ");
         comboFilter.setCondSql(sb.toString());
         params.add(commonFilterDto.getFlId());
      }

      return repository.fillComboValues(comboFilter, params.toArray());
   }


@Override
   public List<DropDownDto> getDeliveryModeCombo(CommonFilterDto commonFilterDto) {
      ComboFilterDto comboFilter = new ComboFilterDto();
      List<Object> params = new ArrayList<>();

      comboFilter.setNameField("TMOD_NAME");
      comboFilter.setCodeField("TMOD_CODE");
      comboFilter.setIdField("TMOD_KEYID");
      comboFilter.setCondSql(" AND TMOD_ACTIVE = 'Y' ");
      comboFilter.setTableName("ENT_TL_DELIVERYMODEMST");

      return repository.fillComboValues(comboFilter, params.toArray());
   }


@Override
   public List<DropDownDto> getCategoryComboList(CommonFilterDto commonFilterDto) {
      ComboFilterDto comboFilter = new ComboFilterDto();
      List<Object> params = new ArrayList<>();

      comboFilter.setIdField("TCAT_KEYID");
      comboFilter.setNameField("TCAT_NAME");
      comboFilter.setCondSql(" AND TCAT_ACTIVE = 'Y' ");
      comboFilter.setTableName("ENT_TL_TOPICCATEGORYMST");

      return repository.fillComboValues(comboFilter, params.toArray());
   }


@Override
   public List<DropDownDto> getGridTopicComboList(CommonFilterDto commonFilterDto) {
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setIdField("TOPI_KEYID");
    comboFilter.setNameField("TOPI_NAME");
    comboFilter.setTableName("ENT_TL_TOPICMST");

    StringBuilder sb = new StringBuilder();

    if (ValidationUtil.isValidKeyId(commonFilterDto.getRoleId())) {
        logger.info("Range is {}", commonFilterDto.getRoleId());
        sb.append(" AND TOPI_KEYID IN (SELECT TMTM_TOPI_KEYID FROM ENT_TL_UNIQPOSTOPIC_LINKMST WHERE TMTM_ROLE_KEYID = ?)");
        params.add(commonFilterDto.getRoleId());
    }

    if (ValidationUtil.isValidKeyId(commonFilterDto.getFlId())) {
        logger.info("Flid is {}", commonFilterDto.getFlId());
        sb.append(" AND TOPI_LOCATIONID = ?");
        params.add(commonFilterDto.getFlId());
    }

    if (ValidationUtil.isValidKeyId(commonFilterDto.getTopicType())) {
        logger.info("Related is {}", commonFilterDto.getTopicType());
        sb.append(" AND TOPI_RELATEDTO = ?");
        params.add(commonFilterDto.getTopicType());
    }

    comboFilter.setCondSql(sb.toString());

    return repository.fillComboValues(comboFilter, params.toArray());
}


@Override
public List<DropDownDto> getTradeComboList(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setIdField("TRDM_KEYID");
    comboFilter.setNameField("TRDM_NAME");
    comboFilter.setTableName("GEN_TL_TRADEMST");

    return repository.fillComboValues(comboFilter, params.toArray());
}

//****************SkillIndex****************
@Override
public List<DropDownDto> getEmpTypeCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setCodeField("ETPM_CODE");
    comboFilter.setIdField("ETPM_KEYID");
    comboFilter.setNameField("ETPM_NAME");
    comboFilter.setTableName("GEN_TL_EMPTYPE_MST");

    return repository.fillComboValues(comboFilter, params.toArray());
}
//**************KnowWhy*************

@Override
public List<DropDownDto> getDefactPhenamenComboList(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setIdField("QPHM_KEYID");
    comboFilter.setNameField("QPHM_NAME");
    comboFilter.setTableName("QTM_TL_PHENOMENAMST");

    return repository.fillComboValues(comboFilter, params.toArray());
}

//*****Kaizen*************
@Override
public List<DropDownDto> getKznThemeCategory(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setNameField("KZCT_NAME");
    comboFilter.setIdField("KZCT_KEYID");
    comboFilter.setTableName("KZN_TL_CATEGORYTHMMST");
    comboFilter.setCondSql(" AND KZCT_ACTIVE = 'Y' ");

    return repository.fillComboValues(comboFilter, params.toArray());
}

@Override
public List<DropDownDto> getKznNoName(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setIdField("KZPM_PROJECTNO");
    comboFilter.setCodeField("KZPM_PROJECTNO");
    comboFilter.setNameField("KZPM_PROJECTNAME");
    comboFilter.setTableName("KZN_TL_PROJECTCREATIONMST");

    return repository.fillComboValues(comboFilter, params.toArray());
}

@Override
public List<DropDownDto> getWhyWhyCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setCodeField("WWMS_KEYID");
    comboFilter.setIdField("WWMS_KEYID");

    if (ValidationUtil.isValidKeyId(commonFilterDto.getWwmsKeyid())) {
        comboFilter.setCondSql(" AND WWMS_REFDOCNO = ? AND WWMS_REFDOCTYPE = 'KZN' ");
        params.add(commonFilterDto.getWwmsKeyid());
    }

    comboFilter.setTableName("BDM_TL_WHYWHYMST");

    return repository.fillComboValues(comboFilter, params.toArray());
}

@Override
public List<DropDownDto> getKpiCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    logger.info("KPI Flid {}", commonFilterDto.getFlId());

    comboFilter.setNameField("KINK_INDICATORNAME");
    comboFilter.setIdField("KINK_KEYID");
    comboFilter.setTableName("KPI_TL_INDICATOR, KPI_TL_INDICATOR_DEPT_LINK");
    comboFilter.setCondSql(" AND KIDL_INDICATORID = KINK_KEYID AND KIDL_DEPTID = ? ");
    params.add(commonFilterDto.getFlId());

    return repository.fillComboValues(comboFilter, params.toArray());
}

@Override
public List<DropDownDto> getMould(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setCodeField("MLDM_DESCRIPTION");
    comboFilter.setIdField("MLDM_MOULDID");
    comboFilter.setTableName("GEN_TL_MOULDMST");

    if (ValidationUtil.isValidKeyId(commonFilterDto.getMchId())) {
        StringBuilder sb = new StringBuilder();
        sb.append(" AND MLDM_MOULDID IN (SELECT MMLK_MOULDID FROM GEN_TL_MOULDMACHINELINK");
        sb.append(" WHERE MMLK_MACHINEID = ?)");
        comboFilter.setCondSql(sb.toString());
        params.add(commonFilterDto.getMchId());
    }

    return repository.fillComboValues(comboFilter, params.toArray());
}
//--------------------------------------------GOPI------------------------------------
// ---- Why Why Analysis: Pillar ----
@Override
public List<DropDownDto> getWhyWhyPillarCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("GEN_TL_TPMPILLARMST");
    comboFilter.setIdField("TPMP_KEYID");
    comboFilter.setNameField("TRIM(TPMP_NAME)");
    comboFilter.setCodeField("TRIM(TPMP_CODE)");
    comboFilter.setOrderByField("LABEL");

    return repository.fillComboValues(comboFilter, params.toArray());
}
// ---- OPL: Process ----
@Override
public List<DropDownDto> getOplProcessCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("QTM_TL_PROCESSMST");
    comboFilter.setIdField("QPOM_KEYID");
    comboFilter.setNameField("TRIM(QPOM_NAME)");
    comboFilter.setOrderByField("LABEL");

    if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getFlId())) {
        comboFilter.setCondSql("""
                                AND QPOM_FLID IN (
                                    SELECT FLID FROM GEN_MV_FLIDHIERARCHY
                                    WHERE POSITION(? IN (COALESCE(PARENTFLIDS,'') || '-' || COALESCE(FLID,''))) > 0
                                )
                             """);
        params.add(commonFilterDto.getFlId());
    }

    return repository.fillComboValues(comboFilter, params.toArray());
}
// ---- Field Audit Sheet: Service Provider ----
@Override
public List<DropDownDto> getFieldAuditServiceProviderCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("ADM_TL_SERVICEPROVIDERMST");
    comboFilter.setIdField("ASPM_KEYID");
    comboFilter.setNameField("TRIM(ASPM_NAME)");
    comboFilter.setOrderByField("LABEL");

    return repository.fillComboValues(comboFilter, params.toArray());
}
// ---- Visual SOP: Maintenance Section (reuses Trade master) ----
@Override
public List<DropDownDto> getVisualSopMaintSectionCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("GEN_TL_TRADEMST");
    comboFilter.setIdField("TRDM_KEYID");
    comboFilter.setNameField("TRIM(TRDM_NAME)");
    comboFilter.setCodeField("TRIM(TRDM_CODE)");
    comboFilter.setOrderByField("LABEL");

    return repository.fillComboValues(comboFilter, params.toArray());
}

// ---- Critical Process: Unit of Measurement ----
    @Override
    public List<DropDownDto> getUomCombo(CommonFilterDto commonFilterDto)
    {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("ADM_TL_UOMMST");
        comboFilter.setIdField("UOMM_KEYID");
        comboFilter.setNameField("TRIM(UOMM_DESCRIPTION)");
        comboFilter.setCodeField("TRIM(UOMM_CODE)");
        comboFilter.setOrderByField("LABEL");

        return repository.fillComboValues(comboFilter, params.toArray());
    }

    // ---- Process FMEA: Sub Process ----
    @Override
    public List<DropDownDto> getFmeaSubProcessCombo(CommonFilterDto commonFilterDto)
    {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("QTM_TL_SUBPROCESSMST");
        comboFilter.setIdField("SUBP_KEYID");
        comboFilter.setNameField("TRIM(SUBP_NAME)");
        comboFilter.setOrderByField("LABEL");

        if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getProcessId()))
        {
            comboFilter.setCondSql("""
                                    AND SUBP_PROCESSID = ?
                                 """);
            params.add(commonFilterDto.getProcessId());
        }

        return repository.fillComboValues(comboFilter, params.toArray());
    }
    // ---- FMEA: Equipment Area (sub-equipment section) ----
@Override
public List<DropDownDto> getFmeaEquipmentAreaCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("GEN_TL_EQUIP_SECTIONMST");
    comboFilter.setIdField("ESEC_KEYID");
    comboFilter.setNameField("TRIM(ESEC_NAME)");
    comboFilter.setCodeField("TRIM(ESEC_CODE)");
    comboFilter.setOrderByField("LABEL");

    if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getFlId())) {
        comboFilter.setCondSql("""
                                AND ESEC_PARENTFLID = ?
                             """);
        params.add(commonFilterDto.getFlId());
    } else if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getCellId())) {
        comboFilter.setCondSql("""
                                AND ESEC_PARENTFLID IN (
                                    SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE FNLN_ORIGINALID = ?
                                )
                             """);
        params.add(commonFilterDto.getCellId());
    }

    return repository.fillComboValues(comboFilter, params.toArray());
}
// ---- Upstream Defect: Defect (Phenomena) ----
@Override
public List<DropDownDto> getUpstreamDefectCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("QTM_TL_PHENOMENAMST");
    comboFilter.setIdField("QPHM_KEYID");
    comboFilter.setNameField("TRIM(QPHM_NAME)");
    comboFilter.setOrderByField("LABEL");

    String sectionId = commonFilterDto != null ? commonFilterDto.getSectionId() : null;
    String defectMode = commonFilterDto != null ? commonFilterDto.getDefectMode() : null;

    if ("OTHERS".equals(defectMode)) {
        comboFilter.setCondSql("""
                                AND QPHM_KEYID NOT IN (
                                    SELECT PHNM_QPHM_KEYID FROM QTM_TL_PHENOMENA_MAPPING
                                    WHERE PHNM_ACTIVE = 'Y' AND PHNM_SECT_FLID = ?
                                )
                             """);
    } else {
        comboFilter.setCondSql("""
                                AND QPHM_KEYID IN (
                                    SELECT PHNM_QPHM_KEYID FROM QTM_TL_PHENOMENA_MAPPING
                                    WHERE PHNM_ACTIVE = 'Y' AND PHNM_SECT_FLID = ?
                                )
                             """);
    }
    params.add(sectionId);

    return repository.fillComboValues(comboFilter, params.toArray());
}
    
// ---- Complaint Gallery: Customer Name ----
@Override
public List<DropDownDto> getComplaintGalleryCustomerCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("GEN_TL_PARTNORMST");
    comboFilter.setIdField("PNOR_KEYID");
    comboFilter.setNameField("TRIM(PNOR_NAME)");
    comboFilter.setOrderByField("LABEL");

    return repository.fillComboValues(comboFilter, params.toArray());
}

// ---- Complaint Gallery: Grade Specification ----
// ---- Complaint Gallery: Grade Specification ----
@Override
public List<DropDownDto> getComplaintGalleryGradeSpecCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("PCS_TL_GRADESPECMST");
    comboFilter.setIdField("GSPC_KEYID");
    comboFilter.setNameField("TRIM(GSPC_NAME)");
    comboFilter.setCodeField("TRIM(GSPC_CODE)");
    comboFilter.setOrderByField("LABEL");

    if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getFlId())) {

        boolean isOthers = "OTHERS".equals(commonFilterDto.getGradeMode());

        if (isOthers) {
            comboFilter.setCondSql("""
                                    AND GSPC_FLID IN (
                                        SELECT FNLN_KEYID FROM GEN_TL_FUNCTIONALLOCN
                                        WHERE FNLN_ORIGINALID IN (
                                            SELECT SUBSTR(FNLN_ELEMENTID,12,10)
                                            FROM GEN_VW_FNLN WHERE FNLN_KEYID = ?
                                        )
                                    )
                                 """);
        } else {
            comboFilter.setCondSql("""
                                    AND GSPC_FLID = ?
                                 """);
        }
        params.add(commonFilterDto.getFlId());
    }

    return repository.fillComboValues(comboFilter, params.toArray());
}

// ---- Complaint Gallery: Defect Phenomena ----
// ---- Complaint Gallery: Defect Phenomena (fresh) ----
@Override
public List<DropDownDto> getComplaintGalleryDefectPhenomenaCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("QTM_TL_PHENOMENAMST");
    comboFilter.setIdField("QPHM_KEYID");
    comboFilter.setNameField("TRIM(QPHM_NAME)");
    comboFilter.setOrderByField("LABEL");

    if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getSectionId())) {

        boolean isOthers = "OTHERS".equals(commonFilterDto.getDefectMode());

        if (isOthers) {
            comboFilter.setCondSql("""
                                    AND QPHM_KEYID NOT IN (
                                        SELECT PHNM_QPHM_KEYID FROM QTM_TL_PHENOMENA_MAPPING
                                        WHERE PHNM_ACTIVE = 'Y' AND PHNM_SECT_FLID = ?
                                    )
                                 """);
        } else {
            comboFilter.setCondSql("""
                                    AND QPHM_KEYID IN (
                                        SELECT PHNM_QPHM_KEYID FROM QTM_TL_PHENOMENA_MAPPING
                                        WHERE PHNM_ACTIVE = 'Y' AND PHNM_SECT_FLID = ?
                                    )
                                 """);
        }
        params.add(commonFilterDto.getSectionId());
    }

    return repository.fillComboValues(comboFilter, params.toArray());
   

    
}
 //**************************************PRIYANKA**************************************
 //****************************serviceimpl***********************************
 @Override
    public List<DropDownDto> getCheckTypeCombo(CommonFilterDto commonFilterDto)
    {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("PLM_TL_CHECKTYPEMST");
        comboFilter.setIdField("CHEK_KEYID");
        comboFilter.setNameField("TRIM(CHEK_NAME)");
        comboFilter.setOrderByField("LABEL");

        comboFilter.setCondSql("""
                            AND CHEK_ACTIVE = 'Y'
                         """);

        // If this combo needs to pre-select/exclude an existing value (like others do with comboKey)
        if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getComboKey())) {
            comboFilter.setId(commonFilterDto.getComboKey());
        }

        return repository.fillComboValues(comboFilter, params.toArray());
    }

    @Override
public List<DropDownDto> getSpareComboList(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("GEN_TL_SPARESMST");
    comboFilter.setIdField("SPRM_KEYID");
    comboFilter.setCodeField("SPRM_PARTNO");
    comboFilter.setNameField("SPRM_PARTNAME");
    comboFilter.setOrderByField("LABEL");

    String assmId = (commonFilterDto != null) ? commonFilterDto.getAssmId() : null;
    String machineId = (commonFilterDto != null) ? commonFilterDto.getMachineId() : null;

    StringBuilder condSql = new StringBuilder();

    if (ValidationUtil.isValidKeyId(assmId)) {
        condSql.append("""
                AND SPRM_KEYID IN (
                        SELECT FNLN_ORIGINALID
                        FROM GEN_TL_FUNCTIONALLOCN
                        WHERE POSITION(? IN FNLN_ELEMENTID) > 0
                        AND FNLN_ELEMENTTYPE = 'SPR'
                )
                """);
        params.add(assmId);
    }

    if (ValidationUtil.isValidKeyId(machineId)) {
        condSql.append("""
                AND SPRM_KEYID IN (
                        SELECT FNLN_ORIGINALID
                        FROM GEN_TL_FUNCTIONALLOCN
                        WHERE POSITION(? IN FNLN_ELEMENTID) > 0
                        AND FNLN_ELEMENTTYPE = 'SPR'
                )
                """);
        params.add(machineId);
    }

    if (condSql.length() > 0) {
        comboFilter.setCondSql(condSql.toString());
    }

    if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getComboKey())) {
        comboFilter.setId(commonFilterDto.getComboKey());
    }

    return repository.fillComboValues(comboFilter, params.toArray());
}

@Override
public List<DropDownDto> getCheckingToolCombo(CommonFilterDto commonFilterDto)
{
    ComboFilterDto comboFilter = new ComboFilterDto();
    List<Object> params = new ArrayList<>();

    comboFilter.setTableName("PLM_TL_CHECKINGTOOL");
    comboFilter.setIdField("CHKT_KEYID");
    comboFilter.setCodeField("CHKT_CODE");
    comboFilter.setNameField("CHKT_NAME");
    comboFilter.setOrderByField("LABEL");

    if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getComboKey())) {
        comboFilter.setId(commonFilterDto.getComboKey());
    }

    return repository.fillComboValues(comboFilter, params.toArray());
}
//********************************************KPI************************************ */
@Override
    public List<DropDownDto> getParentComboList(CommonFilterDto commonFilterDto)
    {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("KPI_TL_INDICATOR");
        comboFilter.setIdField("KINK_KEYID");
        comboFilter.setNameField("KINK_INDICATORNAME");
        comboFilter.setOrderByField("LABEL");

        StringBuilder condSql = new StringBuilder(" AND KINK_KEYID = KINK_PARENTID ");
        

        String pillarId = (commonFilterDto != null) ? commonFilterDto.getPillarid() : null;
        String locationId = (commonFilterDto != null) ? commonFilterDto.getLossId() : null;

        if (ValidationUtil.isValidKeyId(pillarId)) {
            condSql.append(" AND KINK_PILLARID = ? ");
            params.add(pillarId);
        }

        if (ValidationUtil.isValidKeyId(locationId)) {
            condSql.append(" AND KINK_LOCATION = ? ");
            params.add(locationId);
        }

        comboFilter.setCondSql(condSql.toString());

        if (commonFilterDto != null && ValidationUtil.isValidKeyId(commonFilterDto.getComboKey())) {
            comboFilter.setId(commonFilterDto.getComboKey());
        }

        return repository.fillComboValues(comboFilter, params.toArray());
    }
//***********************************************************************
//*************************************MOM***********************************
@Override
     public List<DropDownDto> getPillarGroupCombo(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_MOM_GROUPMST EM JOIN GEN_MV_FLIDHIERARCHY MV ON MV.FLID = EM.MGRM_FLID");
        comboFilter.setIdField("MGRM_KEYID");
        comboFilter.setNameField("MGRM_NAME");

        String pillarid = (commonFilterDto != null) ? commonFilterDto.getPillarid() : null;
        String locationId = (commonFilterDto != null) ? commonFilterDto.getLocationId() : null;

        StringBuilder condSql = new StringBuilder();

        if (pillarid != null) {
            condSql.append(" AND MGRM_PILLARID = ? ");
            condSql.append(" AND POSITION((SELECT FNLN_KEYID FROM gen_tl_functionallocn WHERE FNLN_ORIGINALID = ?) IN (PARENTFLIDS || FLID)) > 0 ");
            params.add(pillarid);
            params.add(locationId);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }

     @Override
     public List<DropDownDto> getMOMRoleComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("ADM_TL_ROLEMST");
        comboFilter.setIdField("ROLE_KEYID");
        comboFilter.setNameField("TRIM(ROLE_NAME)");
        comboFilter.setCodeField("TRIM(ROLE_CODE)");
        comboFilter.setOrderByField("LABEL");

        logger.info("flid => {}", commonFilterDto != null ? commonFilterDto.getFlId() : null);

        return repository.fillComboValues(comboFilter, params.toArray());
     }

     @Override
     public List<DropDownDto> getRoleComboListNewMom(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("ADM_TL_ROLEMST");
        comboFilter.setIdField("ROLE_KEYID");
        comboFilter.setNameField("TRIM(ROLE_NAME)");
        comboFilter.setCodeField("TRIM(ROLE_CODE)");
        comboFilter.setOrderByField("LABEL");

        logger.info("flid (new mom) => {}", commonFilterDto != null ? commonFilterDto.getFlId() : null);

        return repository.fillComboValues(comboFilter, params.toArray());
     }

     @Override
     public List<DropDownDto> getRoleBasedEmployee(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("GEN_TL_EMPLOYEEMST");
        comboFilter.setIdField("EMPM_KEYID");
        comboFilter.setNameField("EMPM_NAME");
        comboFilter.setCodeField("EMPM_CODE");

        String flid = commonFilterDto != null ? commonFilterDto.getFlId() : null;
        String roleId = commonFilterDto != null ? commonFilterDto.getRoleId() : null;
        String othersFlag = commonFilterDto != null ? commonFilterDto.getOthersFlag() : null;
        String locnid = commonFilterDto != null ? commonFilterDto.getLocnId() : null;

        StringBuilder condSql = new StringBuilder();
        condSql.append(" AND EMPM_KEYID IN ( SELECT DISTINCT e.empm_keyid FROM gen_tl_employeemst e ");
        condSql.append(" JOIN gen_tl_fnlnroleteam frt ON frt.frt_empm_keyid = e.empm_keyid ");
        condSql.append(" JOIN gen_mv_flidhierarchy h ON h.flid = frt.frt_fnln_keyid WHERE 1=1 ");

        if (ValidationUtil.isValidKeyId(othersFlag)) {
            condSql.append(" AND POSITION((SELECT fnln_keyid FROM gen_tl_functionallocn WHERE fnln_originalid = ?) IN (h.parentflids || '/' || h.flid)) > 0 ");
            params.add(locnid);
        }

        if (ValidationUtil.isValidKeyId(flid)) {
            condSql.append(" AND frt.frt_fnln_keyid IN (SELECT flid FROM gen_mv_flidhierarchy WHERE POSITION(? IN (parentflids || '-' || flid)) > 0) ");
            params.add(flid);
        }

        if (ValidationUtil.isValidKeyId(roleId)) {
            condSql.append(" AND frt_role_keyid = ? ");
            params.add(roleId);
        }

        condSql.append(" ) ");

        if (ValidationUtil.isValidKeyId(locnid)) {
            condSql.append(" AND empm_location = ? AND empm_active = 'Y' ");
            params.add(locnid);
        }

        comboFilter.setCondSql(condSql.toString());

        return repository.fillComboValues(comboFilter, params.toArray());
     }
     //************************************equip */
     @Override
     public List<DropDownDto> getMachineComboList(CommonFilterDto commonFilterDto)
     {
        String keyid = commonFilterDto != null ? commonFilterDto.getKeyid() : null;
        String cellid = commonFilterDto != null ? commonFilterDto.getCellId() : null;
        String flid = commonFilterDto != null ? commonFilterDto.getFlId() : null;
        String machineNotToShown = commonFilterDto != null ? commonFilterDto.getMachineNotToShown() : null;
        String circleId = commonFilterDto != null ? commonFilterDto.getCircleId() : null;
        String pcsEnabled = commonFilterDto != null ? commonFilterDto.getPcsEnabled() : "N";
        String eqpGrpId = commonFilterDto != null ? commonFilterDto.getEqpGrpId() : null;
        String eqpSubGrpId = commonFilterDto != null ? commonFilterDto.getEqpSubGrpId() : null;
        String mouldId = commonFilterDto != null ? commonFilterDto.getMouldId() : null;
        String empch = commonFilterDto != null ? commonFilterDto.getEmpId() : null;
        String companyId = commonFilterDto != null ? commonFilterDto.getCompanyId() : null;
        String locationId = commonFilterDto != null ? commonFilterDto.getLocationId() : null;
        String sbuId = commonFilterDto != null ? commonFilterDto.getSbuId() : null;
        String pbuId = commonFilterDto != null ? commonFilterDto.getPbuId() : null;
        String factoryId = commonFilterDto != null ? commonFilterDto.getFactoryId() : null;
        String sectionid = commonFilterDto != null ? commonFilterDto.getSectionid() : null;
        String code = commonFilterDto != null ? commonFilterDto.getCode() : null;
        String comboKey = commonFilterDto != null ? commonFilterDto.getComboKey() : null;

        StringBuilder condSql = new StringBuilder(" AND MCHM_ACTIVE = 'Y' AND MCHM_TYPE = 'MCH' ");
        List<Object> params = new ArrayList<>();

        if (ValidationUtil.isValidKeyId(cellid)) {
            condSql.append(" AND MCHM_CELLID = ? ");
            params.add(cellid);
        }

        if (ValidationUtil.isValidKeyId(flid)) {
            condSql.append(" AND MCHM_FLID IN (SELECT FLID FROM gen_mv_flidhierarchy WHERE POSITION(? IN (PARENTFLIDS || FLID)) > 0) ");
            params.add(flid);
        }

        if (ValidationUtil.isValidKeyId(keyid)) {
            condSql.append(" AND MCHM_KEYID <> ? ");
            params.add(keyid);
        }

        if (ValidationUtil.isValidKeyId(machineNotToShown)) {
            condSql.append(" AND MCHM_KEYID <> ? ");
            params.add(machineNotToShown);
        }

        if (ValidationUtil.isValidKeyId(circleId)) {
            condSql.append(" AND MCHM_KEYID IN (SELECT MCLK_MACHINEID FROM GEN_TL_MCHCIRCLELINK WHERE MCLK_ACTIVE = 'Y' AND MCLK_CIRCLEID = ?) ");
            params.add(circleId);

            if (ValidationUtil.isValidKeyId(mouldId)) {
                condSql.append(" AND MCHM_KEYID IN (SELECT MMLK_MACHINEID FROM GEN_TL_MOULDMACHINELINK WHERE MMLK_MOULDID = ?) ");
                params.add(mouldId);
            }

            if (ValidationUtil.isValidKeyId(eqpGrpId)) {
                condSql.append(" AND MCHM_EQUIPMENTGROUP = ? ");
                params.add(eqpGrpId);
            }

            if (ValidationUtil.isValidKeyId(eqpSubGrpId)) {
                condSql.append(" AND MCHM_EQUIPMENTGROUP IN (SELECT EQGM_KEYID FROM EQG_EQPGROUPMST WHERE EQGM_SUBGROUPID = ?) ");
                params.add(eqpSubGrpId);
            }
        }

        if ("Y".equals(pcsEnabled)) {
            condSql.append(" AND (MCHM_KEYID IN (SELECT PELC_MACHINEID FROM PCS_TL_ENABLELOSSCAPTURE WHERE PELC_ISPCSENABLED = 'Y') ");
            condSql.append(" OR MCHM_CELLID IN (SELECT PELC_CELLID FROM PCS_TL_ENABLELOSSCAPTURE WHERE PELC_ISPCSENABLED = 'Y')) ");
        }

        if (ValidationUtil.isValidKeyId(empch)) {
            condSql.append(" AND MCHM_KEYID IN (SELECT EFLL_FUNCLOCN FROM GEN_TL_EMPFUNCLOCNLINK WHERE EFLL_FUNCLOCNTYPE = 'MCHM' AND EFLL_EMPLOYEEID = ?) ");
            params.add(empch);
        }

        // NOTE: inferred column names for the flattened view — verify each
        // against raw SQL, the same way COMP_KEYID was confirmed earlier.
        if (ValidationUtil.isValidKeyId(companyId)) {
            condSql.append(" AND COMP_KEYID = ? ");
            params.add(companyId);
        }
        if (ValidationUtil.isValidKeyId(locationId)) {
            condSql.append(" AND LOCN_KEYID = ? ");
            params.add(locationId);
        }
        if (ValidationUtil.isValidKeyId(sbuId)) {
            condSql.append(" AND SBUT_KEYID = ? ");
            params.add(sbuId);
        }
        if (ValidationUtil.isValidKeyId(pbuId)) {
            condSql.append(" AND PBUT_KEYID = ? ");
            params.add(pbuId);
        }
        if (ValidationUtil.isValidKeyId(factoryId)) {
            condSql.append(" AND FACT_KEYID = ? ");
            params.add(factoryId);
        }
        if (ValidationUtil.isValidKeyId(sectionid)) {
            condSql.append(" AND SECT_KEYID = ? ");
            params.add(sectionid);
        }

        if (ValidationUtil.isValidKeyId(code)) {
            condSql.append(" AND UPPER(MCHM_MACHINENAME || MCHM_MACHINENO) LIKE UPPER(?) ");
            params.add("%" + code + "%");
        }

        ComboFilterDto comboFilter = new ComboFilterDto();
        comboFilter.setTableName("GEN_VW_FACTORYLAYOUT");
        comboFilter.setIdField("MCHM_KEYID");
        comboFilter.setNameField("TRIM(MCHM_MACHINENAME)");
        comboFilter.setCodeField("TRIM(MCHM_MACHINENO)");
        comboFilter.setOrderByField("LABEL");

        if (!ValidationUtil.isValidKeyId(comboKey)) {
            comboFilter.setCondSql(condSql.toString());
            return repository.fillComboValues(comboFilter, params.toArray());
        }

        // comboKey set — replicate the legacy id-highlight UNION manually,
        // since fillComboValues would otherwise apply condSql to BOTH
        // branches, but legacy only applies it to the "rest" branch.
        List<DropDownDto> result = new ArrayList<>();
        String[] ids = comboKey.split(",");

        ComboFilterDto idFilter = new ComboFilterDto();
        idFilter.setTableName("GEN_VW_FACTORYLAYOUT");
        idFilter.setIdField("MCHM_KEYID");
        idFilter.setNameField("TRIM(MCHM_MACHINENAME)");
        idFilter.setCodeField("TRIM(MCHM_MACHINENO)");
        idFilter.setOrderByField("LABEL");

        StringBuilder idCond = new StringBuilder(" AND MCHM_ACTIVE = 'Y' AND MCHM_TYPE = 'MCH' ");
        List<Object> idParams = new ArrayList<>();
        if (ids.length > 1) {
            idCond.append(" AND MCHM_KEYID IN (");
            for (int i = 0; i < ids.length; i++) {
                idCond.append(i > 0 ? ",?" : "?");
                idParams.add(ids[i].trim());
            }
            idCond.append(") ");
        } else {
            idCond.append(" AND MCHM_KEYID = ? ");
            idParams.add(ids[0].trim());
        }
        idFilter.setCondSql(idCond.toString());
        result.addAll(repository.fillComboValues(idFilter, idParams.toArray()));

        StringBuilder restCond = new StringBuilder(condSql);
        List<Object> restParams = new ArrayList<>(params);
        if (ids.length > 1) {
            restCond.append(" AND MCHM_KEYID NOT IN (");
            for (int i = 0; i < ids.length; i++) {
                restCond.append(i > 0 ? ",?" : "?");
                restParams.add(ids[i].trim());
            }
            restCond.append(") ");
        } else {
            restCond.append(" AND MCHM_KEYID <> ? ");
            restParams.add(ids[0].trim());
        }
        comboFilter.setCondSql(restCond.toString());
        result.addAll(repository.fillComboValues(comboFilter, restParams.toArray()));

        return result;
     }
@Override
     public List<DropDownDto> getEffectiveComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("BDM_TL_WHYWHYEFFECTIVE");
        comboFilter.setIdField("WHYE_KEYID");
        comboFilter.setNameField("WHYE_EFFECTIVENAME");

        // NOTE: cmbAssmbid/assembly id is captured in the legacy servlet but
        // never actually used in the query — matching current (unused)
        // behavior.

        return repository.fillComboValues(comboFilter, params.toArray());
     }

     //***********************************************fieldaudit*******************************************
     @Override
     public List<DropDownDto> getPPEType(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        comboFilter.setTableName("JHA_TL_PPEMST");
        comboFilter.setIdField("PPEM_KEYID");
        comboFilter.setNameField("PPEM_NAME");

        return repository.fillComboValues(comboFilter, params.toArray());
     }

    // ***********************************************role************************************
    @Override
     public List<DropDownDto> getUserRollComboList(CommonFilterDto commonFilterDto)
     {
        ComboFilterDto comboFilter = new ComboFilterDto();
        List<Object> params = new ArrayList<>();

        String empId = commonFilterDto != null ? commonFilterDto.getEmpId() : null;

        if (empId != null) {

            comboFilter.setIdField("ROLE_KEYID || '-' || FLID");
            comboFilter.setCodeField("ROLE_NAME || ' - ' || FNLN_DISPLAYCODE");
            comboFilter.setTableName("GEN_TL_FNLNROLETEAM, ADM_TL_ROLEORDER, GEN_MV_FLIDHIERARCHY");

            StringBuilder condSql = new StringBuilder();
            condSql.append(" AND ROLE_KEYID = FRT_ROLE_KEYID AND FLID = FRT_FNLN_KEYID ");
            condSql.append(" AND FRT_EMPM_KEYID = ? ");
            params.add(empId.trim());

            comboFilter.setCondSql(condSql.toString());

        } else {

            comboFilter.setIdField("ROLE_KEYID");
            comboFilter.setCodeField("ROLE_NAME");
            comboFilter.setTableName("ADM_TL_ROLEMST");
        }

        return repository.fillComboValuesRole(comboFilter, params.toArray());
     }

     @Override
     public String getCurrentShift(CommonFilterDto commonFilterDto)
     {
        // NOTE: factId is captured for API-shape compatibility but was never
        // actually used in the legacy SQL — matching that (unused) behavior.

        String shiftId = repository.getCurrentShiftPrimary();
        if (ValidationUtil.isValidKeyId(shiftId)) {
            return shiftId;
        }

        shiftId = repository.getCurrentShiftOvernightFallback();
        return ValidationUtil.isValidKeyId(shiftId) ? shiftId : "";
     }
     
}
