package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.*;
import com.akranta.perfex_sb.repository.*;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.GenTlMachinemstService;
import com.akranta.perfex_sb.util.ValidationUtil;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.GenTlMachinemstRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//import java.util.List;
import java.util.Map;

@Service
public class GenTlMachinemstServiceImpl implements GenTlMachinemstService {

    private static final Logger logger = LoggerFactory.getLogger(GenTlMachinemstServiceImpl.class);

    private final GenTlMachinemstRepository masterRepository;
    private final GenTlFunctionallocnRepository functionalLocnRepository;
    private final GenTlMchemplinkRepository operatorRepository;
    private final GenTlMchmaintteamlinkRepository maintenanceRepository;
    private final GenTlMachineskillmstRepository machineSkillRepository;
    private final GenTlMchsubmchlinkRepository subEquipmentRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MACHINE = "GEN_TL_MACHINEMST";
    private static final String SEQ_IDENTIFIER_FUNCLOCN = "GEN_TL_FUNCTIONALLOCN";
    
    private static final int KEY_LENGTH_MACHINE = 10;
    private static final int KEY_LENGTH_FUNCLOCN = 12;
    
    private static final String PREFIX_MACHINE = "MCH";
    private static final String PREFIX_FUNCLOCN = "FNLN";
    
    private static final String DATE_FORMAT = "YY"; // YY format can be used if needed
    private static final String FORMAT_RESET = "Y";

    public GenTlMachinemstServiceImpl(
        GenTlMachinemstRepository masterRepository,
        GenTlFunctionallocnRepository functionalLocnRepository,
        GenTlMchemplinkRepository operatorRepository,
        GenTlMchmaintteamlinkRepository maintenanceRepository,
        GenTlMachineskillmstRepository machineSkillRepository,
        GenTlMchsubmchlinkRepository subEquipmentRepository,
        DbActionTemplate dbActionTemplate) {
        
        this.masterRepository = masterRepository;
        this.functionalLocnRepository = functionalLocnRepository;
        this.operatorRepository = operatorRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.machineSkillRepository = machineSkillRepository;
        this.subEquipmentRepository = subEquipmentRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<GenTlMachinemstRequest> saveMachineMaster(GenTlMachinemstRequest request) throws Exception {
        GenTlMachinemst master = request.getMaster();

        if (master == null) {
            throw new RuntimeException("No Machine Master Details provided");
        }

        GenTlMachinemstRequest result = new GenTlMachinemstRequest();

        // CHECK IF INSERT OR UPDATE
        String keyid = master.getKeyid();
        boolean isInsertMode = keyid == null || 
                              keyid.trim().isEmpty() || 
                              keyid.equals("{}") || 
                              keyid.equals("undefined") ||
                              !masterRepository.existsById(keyid);
        
        if (isInsertMode) {
            // ============= INSERT MODE =============
            logger.info("Starting INSERT mode for new Machine Master");
            
            // Generate Master Key ID
            String newMasterKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER_MACHINE, KEY_LENGTH_MACHINE, PREFIX_MACHINE, DATE_FORMAT, FORMAT_RESET
            );

            if (newMasterKeyid == null || newMasterKeyid.trim().isEmpty()) {
                logger.error("Failed to generate the Master Key ID");
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            master.setKeyid(newMasterKeyid);
            if (master.getCreatedon() == null) {
                master.setCreatedon(LocalDateTime.now());
            }
            master.setModifiedon(LocalDateTime.now());

            logger.info("Generated new Master Key ID: {}", newMasterKeyid);
            
            // Save Master
            GenTlMachinemst savedMaster = masterRepository.save(master);
            logger.info("Successfully saved Machine Master with Key ID: {}", savedMaster.getKeyid());

            // Handle Functional Location
            GenTlFunctionallocn functionalLocation = request.getFunctionalLocation();
            if (functionalLocation != null) {
                functionalLocation.setOriginalid(savedMaster.getKeyid());
                
                // Generate Functional Location Key ID
                String newFuncLocnKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_FUNCLOCN, KEY_LENGTH_FUNCLOCN, PREFIX_FUNCLOCN, DATE_FORMAT, FORMAT_RESET
                );
                
                if (newFuncLocnKeyid == null || newFuncLocnKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate Functional Location Key ID");
                    throw new RuntimeException("Failed to generate Functional Location Key ID");
                }
                
                functionalLocation.setKeyid(newFuncLocnKeyid);
                
                // Append machine keyid to element ID
                String currentElementId = functionalLocation.getElementid();
                if (currentElementId != null && !currentElementId.isEmpty()) {
                    functionalLocation.setElementid(currentElementId + "-" + savedMaster.getKeyid());
                }
                
                GenTlFunctionallocn savedFuncLocn = functionalLocnRepository.save(functionalLocation);
                logger.info("Successfully created Functional Location with Key: {}", newFuncLocnKeyid);
            }

            // Save Operator Grid (Delete first, then Insert - matching Eclipse pattern)
            List<GenTlMchemplink> savedOperators = new ArrayList<>();
            if (request.getOperatorGrid() != null && !request.getOperatorGrid().isEmpty()) {
                // Delete existing operators for this machine
                operatorRepository.deleteByMachineId(savedMaster.getKeyid());
                logger.info("Deleted existing operator links for Machine: {} in INSERT mode", savedMaster.getKeyid());
                
                // Insert new operators
                for (GenTlMchemplink operator : request.getOperatorGrid()) {
                    operator.setMachineid(savedMaster.getKeyid());
                    
                    if (operator.getCreatedby() == null || operator.getCreatedby().trim().isEmpty()) {
                        operator.setCreatedby(savedMaster.getCreatedby());
                    }
                    if (operator.getCreatedon() == null) {
                        operator.setCreatedon(LocalDateTime.now());
                    }
                    operator.setModifiedon(LocalDateTime.now());
                    
                    GenTlMchemplink savedOperator = operatorRepository.save(operator);
                    savedOperators.add(savedOperator);
                    logger.info("Successfully created Operator link for Machine: {} and Employee: {}", 
                        savedMaster.getKeyid(), operator.getEmployeeid());
                }
            }

            // Save Maintenance Grid (Delete first, then Insert - matching Eclipse pattern)
            List<GenTlMchmaintteamlink> savedMaintenance = new ArrayList<>();
            if (request.getMaintenanceGrid() != null && !request.getMaintenanceGrid().isEmpty()) {
                // Delete existing maintenance teams for this machine
                maintenanceRepository.deleteByMachineId(savedMaster.getKeyid());
                logger.info("Deleted existing maintenance team links for Machine: {} in INSERT mode", savedMaster.getKeyid());
                
                // Insert new maintenance teams
                for (GenTlMchmaintteamlink maintenance : request.getMaintenanceGrid()) {
                    maintenance.setMachineid(savedMaster.getKeyid());
                    
                    if (maintenance.getCreatedby() == null || maintenance.getCreatedby().trim().isEmpty()) {
                        maintenance.setCreatedby(savedMaster.getCreatedby());
                    }
                    if (maintenance.getCreatedon() == null) {
                        maintenance.setCreatedon(LocalDateTime.now());
                    }
                    maintenance.setModifiedon(LocalDateTime.now());
                    
                    GenTlMchmaintteamlink savedMaint = maintenanceRepository.save(maintenance);
                    savedMaintenance.add(savedMaint);
                    logger.info("Successfully created Maintenance Team link for Machine: {} and Team: {}", 
                        savedMaster.getKeyid(), maintenance.getMaintenanceteamid());
                }
            }

            // Save Operator Skill Grid (Delete first, then Insert - matching Eclipse pattern)
            List<GenTlMachineskillmst> savedOperatorSkills = new ArrayList<>();
            if (request.getOperatorSkillGrid() != null && !request.getOperatorSkillGrid().isEmpty()) {
                // Delete existing operator skills for this machine
                machineSkillRepository.deleteOperatorSkills(savedMaster.getKeyid());
                logger.info("Deleted existing operator skills for Machine: {} in INSERT mode", savedMaster.getKeyid());
                
                // Insert new operator skills
                for (GenTlMachineskillmst skill : request.getOperatorSkillGrid()) {
                    skill.setMachineid(savedMaster.getKeyid());
                    skill.setSkillfordepartment('O'); // O for Operator
                    
                    if (skill.getCreatedby() == null || skill.getCreatedby().trim().isEmpty()) {
                        skill.setCreatedby(savedMaster.getCreatedby());
                    }
                    if (skill.getCreatedon() == null) {
                        skill.setCreatedon(LocalDateTime.now());
                    }
                    skill.setModifiedon(LocalDateTime.now());
                    
                    GenTlMachineskillmst savedSkill = machineSkillRepository.save(skill);
                    savedOperatorSkills.add(savedSkill);
                    logger.info("Successfully created Operator Skill for Machine: {}", savedMaster.getKeyid());
                }
            }

            // Save Maintenance Skill Grid (Delete first, then Insert - matching Eclipse pattern)
            List<GenTlMachineskillmst> savedMaintenanceSkills = new ArrayList<>();
            if (request.getMaintenanceSkillGrid() != null && !request.getMaintenanceSkillGrid().isEmpty()) {
                // Delete existing maintenance skills for this machine
                machineSkillRepository.deleteMaintenanceSkills(savedMaster.getKeyid());
                logger.info("Deleted existing maintenance skills for Machine: {} in INSERT mode", savedMaster.getKeyid());
                
                // Insert new maintenance skills
                for (GenTlMachineskillmst skill : request.getMaintenanceSkillGrid()) {
                    skill.setMachineid(savedMaster.getKeyid());
                    skill.setSkillfordepartment('M'); // M for Maintenance
                    
                    if (skill.getCreatedby() == null || skill.getCreatedby().trim().isEmpty()) {
                        skill.setCreatedby(savedMaster.getCreatedby());
                    }
                    if (skill.getCreatedon() == null) {
                        skill.setCreatedon(LocalDateTime.now());
                    }
                    skill.setModifiedon(LocalDateTime.now());
                    
                    GenTlMachineskillmst savedSkill = machineSkillRepository.save(skill);
                    savedMaintenanceSkills.add(savedSkill);
                    logger.info("Successfully created Maintenance Skill for Machine: {}", savedMaster.getKeyid());
                }
            }

            // Save Sub Equipment Grid (Delete first, then Insert - matching Eclipse pattern)
            List<GenTlMchsubmchlink> savedSubEquipment = new ArrayList<>();
            if (request.getSubEquipmentGrid() != null && !request.getSubEquipmentGrid().isEmpty()) {
                // Delete existing sub equipment for this machine
                subEquipmentRepository.deleteByParentMachineId(savedMaster.getKeyid());
                logger.info("Deleted existing sub equipment links for Machine: {} in INSERT mode", savedMaster.getKeyid());
                
                // Insert new sub equipment
                for (GenTlMchsubmchlink subEquip : request.getSubEquipmentGrid()) {
                    subEquip.setParentmchid(savedMaster.getKeyid());
                    subEquip.setCellid(savedMaster.getCellid());
                    
                    if (subEquip.getCreatedon() == null) {
                        subEquip.setCreatedon(LocalDateTime.now());
                    }
                    
                    GenTlMchsubmchlink savedSubEquip = subEquipmentRepository.save(subEquip);
                    savedSubEquipment.add(savedSubEquip);
                    logger.info("Successfully created Sub Equipment link for Parent: {} and Child: {}", 
                        savedMaster.getKeyid(), subEquip.getChildmchid());
                }
            }

            // Prepare result
            result.setMaster(savedMaster);
            result.setFunctionalLocation(functionalLocation);
            result.setOperatorGrid(savedOperators);
            result.setMaintenanceGrid(savedMaintenance);
            result.setOperatorSkillGrid(savedOperatorSkills);
            result.setMaintenanceSkillGrid(savedMaintenanceSkills);
            result.setSubEquipmentGrid(savedSubEquipment);
            result.setFormActionMode(request.getFormActionMode());
            result.setFormMode(request.getFormMode());
            result.setFormHeader(request.getFormHeader());
            result.setFormType(request.getFormType());

            logger.info("Successfully completed INSERT mode for Machine Master: {}", newMasterKeyid);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } else {
            // ============= UPDATE MODE =============
            logger.info("Starting UPDATE mode for Machine Master with Key ID: {}", master.getKeyid());
            
            GenTlMachinemst existingMaster = masterRepository.findById(master.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException("Machine Master not found"));

            // Update all master fields
            existingMaster.setMachineno(master.getMachineno());
            existingMaster.setMachinename(master.getMachinename());
            existingMaster.setCellid(master.getCellid());
            existingMaster.setSubcellid(master.getSubcellid());
            existingMaster.setEquipmentgroup(master.getEquipmentgroup());
            existingMaster.setControltype(master.getControltype());
            existingMaster.setPurpose(master.getPurpose());
            existingMaster.setCategory(master.getCategory());
            existingMaster.setSubcategory(master.getSubcategory());
            existingMaster.setMachinerank(master.getMachinerank());
            existingMaster.setJhstep(master.getJhstep());
            existingMaster.setJhstepdate(master.getJhstepdate());
            existingMaster.setPhase(master.getPhase());
            existingMaster.setWires(master.getWires());
            existingMaster.setIpvolt(master.getIpvolt());
            existingMaster.setIpvoltmin(master.getIpvoltmin());
            existingMaster.setIpvoltmax(master.getIpvoltmax());
            existingMaster.setIpfreq(master.getIpfreq());
            existingMaster.setIpfreqmin(master.getIpfreqmin());
            existingMaster.setIpfreqmax(master.getIpfreqmax());
            existingMaster.setPowersupply(master.getPowersupply());
            existingMaster.setConnectedload(master.getConnectedload());
            existingMaster.setDbno(master.getDbno());
            existingMaster.setSbno(master.getSbno());
            existingMaster.setSpecification(master.getSpecification());
            existingMaster.setRemarks(master.getRemarks());
            existingMaster.setManufacturerid(master.getManufacturerid());
            existingMaster.setManufactureddate(master.getManufactureddate());
            existingMaster.setMake(master.getMake());
            existingMaster.setModel(master.getModel());
            existingMaster.setMfrslno(master.getMfrslno());
            existingMaster.setMfrremarks(master.getMfrremarks());
            existingMaster.setSupplierid(master.getSupplierid());
            existingMaster.setPono(master.getPono());
            existingMaster.setPodate(master.getPodate());
            existingMaster.setPurchasedate(master.getPurchasedate());
            existingMaster.setPurchaseprice(master.getPurchaseprice());
            existingMaster.setInstalleddate(master.getInstalleddate());
            existingMaster.setIsunderwarranty(master.getIsunderwarranty());
            existingMaster.setWarrantydate(master.getWarrantydate());
            existingMaster.setSupplierremarks(master.getSupplierremarks());
            existingMaster.setIsunderamc(master.getIsunderamc());
            existingMaster.setAmcdate(master.getAmcdate());
            existingMaster.setAmcvendor(master.getAmcvendor());
            existingMaster.setAmcrenewaldate(master.getAmcrenewaldate());
            existingMaster.setAmcremarks(master.getAmcremarks());
            existingMaster.setMachineorder(master.getMachineorder());
            existingMaster.setEffectivedate(master.getEffectivedate());
            existingMaster.setInactivateddate(master.getInactivateddate());
            existingMaster.setIncludeforproduction(master.getIncludeforproduction());
            existingMaster.setGivesfinaloutput(master.getGivesfinaloutput());
            existingMaster.setCostcentreid(master.getCostcentreid());
            existingMaster.setCircleid(master.getCircleid());
            existingMaster.setIscavityormandrel(master.getIscavityormandrel());
            existingMaster.setMaxmeterreading(master.getMaxmeterreading());
            existingMaster.setCurrencyid(master.getCurrencyid());
            existingMaster.setWorkcenter(master.getWorkcenter());
            existingMaster.setTechnicalid(master.getTechnicalid());
            existingMaster.setType(master.getType());
            existingMaster.setTradeid(master.getTradeid());
            existingMaster.setTempfield3(master.getTempfield3());
            existingMaster.setTempfield4(master.getTempfield4());
            existingMaster.setTempfield5(master.getTempfield5());
            existingMaster.setElementid(master.getElementid());
            existingMaster.setFlid(master.getFlid());
            existingMaster.setActive(master.getActive());
            existingMaster.setModifiedon(LocalDateTime.now());

            GenTlMachinemst updatedMaster = masterRepository.save(existingMaster);
            logger.info("Successfully updated Machine Master with Key ID: {}", updatedMaster.getKeyid());

            // // Update Functional Location (if provided)
            // if (request.getFunctionalLocation() != null) {
            //     GenTlFunctionallocn funcLocn = request.getFunctionalLocation();
            //     // Assuming functional location is linked by originalid
            //     // You may need to implement findByOriginalid in repository
            //     // For now, we'll just save/update based on keyid if it exists
            //     if (funcLocn.getKeyid() != null && !funcLocn.getKeyid().isEmpty()) {
            //         functionalLocnRepository.save(funcLocn);
            //         logger.info("Successfully updated Functional Location");
            //     }
            // }

             // ============= UPDATE FUNCTIONAL LOCATION (FIXED) =============
        GenTlFunctionallocn resultFuncLocn = null;
        if (request.getFunctionalLocation() != null) {
            GenTlFunctionallocn funcLocn = request.getFunctionalLocation();

            // originalid = machine keyid — this is the PK used in the legacy SQL WHERE clause
            funcLocn.setOriginalid(updatedMaster.getKeyid());

            // Try to find existing record by originalid (which is the @Id)
            GenTlFunctionallocn existingFuncLocn = functionalLocnRepository
                .findById(updatedMaster.getKeyid())
                .orElse(null);

            if (existingFuncLocn != null) {
                // UPDATE: patch only editable fields, preserve keyid
                existingFuncLocn.setElementid(funcLocn.getElementid());
                existingFuncLocn.setParentid(funcLocn.getParentid());
                existingFuncLocn.setDisplaycode(funcLocn.getDisplaycode());
                existingFuncLocn.setDescription(funcLocn.getDescription());
                existingFuncLocn.setElementtype(funcLocn.getElementtype());
                existingFuncLocn.setActive(funcLocn.getActive());
                // keyid stays unchanged — never overwrite the sequence-generated key

                resultFuncLocn = functionalLocnRepository.save(existingFuncLocn);
                logger.info("Successfully updated Functional Location for machine: {}", updatedMaster.getKeyid());
            } else {
                // No existing record — generate keyid and insert fresh
                String newFuncLocnKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_FUNCLOCN, KEY_LENGTH_FUNCLOCN, PREFIX_FUNCLOCN, DATE_FORMAT, FORMAT_RESET
                );

                if (newFuncLocnKeyid == null || newFuncLocnKeyid.trim().isEmpty()) {
                    throw new RuntimeException("Failed to generate Functional Location Key ID");
                }

                funcLocn.setKeyid(newFuncLocnKeyid);

                String currentElementId = funcLocn.getElementid();
                if (currentElementId != null && !currentElementId.isEmpty()) {
                    funcLocn.setElementid(currentElementId + "-" + updatedMaster.getKeyid());
                }

                resultFuncLocn = functionalLocnRepository.save(funcLocn);
                logger.info("Inserted new Functional Location for machine: {} with key: {}",
                    updatedMaster.getKeyid(), newFuncLocnKeyid);
            }
        }


            // Handle Operator Grid (Delete all and re-insert)
            List<GenTlMchemplink> resultOperators = new ArrayList<>();
            if (request.getOperatorGrid() != null) {
                // Delete existing operators for this machine
                operatorRepository.deleteByMachineId(master.getKeyid());
                logger.info("Deleted existing operator links for Machine: {}", master.getKeyid());
                
                // Insert new operators
                for (GenTlMchemplink operator : request.getOperatorGrid()) {
                    operator.setMachineid(master.getKeyid());
                    
                    if (operator.getCreatedby() == null || operator.getCreatedby().trim().isEmpty()) {
                        operator.setCreatedby(master.getCreatedby());
                    }
                    if (operator.getCreatedon() == null) {
                        operator.setCreatedon(LocalDateTime.now());
                    }
                    operator.setModifiedon(LocalDateTime.now());
                    
                    GenTlMchemplink savedOperator = operatorRepository.save(operator);
                    resultOperators.add(savedOperator);
                }
                logger.info("Re-inserted {} operator links for Machine: {}", 
                    resultOperators.size(), master.getKeyid());
            }

            // Handle Maintenance Grid (Delete all and re-insert)
            List<GenTlMchmaintteamlink> resultMaintenance = new ArrayList<>();
            if (request.getMaintenanceGrid() != null) {
                // Delete existing maintenance teams for this machine
                maintenanceRepository.deleteByMachineId(master.getKeyid());
                logger.info("Deleted existing maintenance team links for Machine: {}", master.getKeyid());
                
                // Insert new maintenance teams
                for (GenTlMchmaintteamlink maintenance : request.getMaintenanceGrid()) {
                    maintenance.setMachineid(master.getKeyid());
                    
                    if (maintenance.getCreatedby() == null || maintenance.getCreatedby().trim().isEmpty()) {
                        maintenance.setCreatedby(master.getCreatedby());
                    }
                    if (maintenance.getCreatedon() == null) {
                        maintenance.setCreatedon(LocalDateTime.now());
                    }
                    maintenance.setModifiedon(LocalDateTime.now());
                    
                    GenTlMchmaintteamlink savedMaint = maintenanceRepository.save(maintenance);
                    resultMaintenance.add(savedMaint);
                }
                logger.info("Re-inserted {} maintenance team links for Machine: {}", 
                    resultMaintenance.size(), master.getKeyid());
            }

            // Handle Operator Skill Grid (Delete all and re-insert)
            List<GenTlMachineskillmst> resultOperatorSkills = new ArrayList<>();
            if (request.getOperatorSkillGrid() != null) {
                // Delete existing operator skills for this machine
                machineSkillRepository.deleteOperatorSkills(master.getKeyid());
                logger.info("Deleted existing operator skills for Machine: {}", master.getKeyid());
                
                // Insert new operator skills
                for (GenTlMachineskillmst skill : request.getOperatorSkillGrid()) {
                    skill.setMachineid(master.getKeyid());
                    skill.setSkillfordepartment('O');
                    
                    if (skill.getCreatedby() == null || skill.getCreatedby().trim().isEmpty()) {
                        skill.setCreatedby(master.getCreatedby());
                    }
                    if (skill.getCreatedon() == null) {
                        skill.setCreatedon(LocalDateTime.now());
                    }
                    skill.setModifiedon(LocalDateTime.now());
                    
                    GenTlMachineskillmst savedSkill = machineSkillRepository.save(skill);
                    resultOperatorSkills.add(savedSkill);
                }
                logger.info("Re-inserted {} operator skills for Machine: {}", 
                    resultOperatorSkills.size(), master.getKeyid());
            }

            // Handle Maintenance Skill Grid (Delete all and re-insert)
            List<GenTlMachineskillmst> resultMaintenanceSkills = new ArrayList<>();
            if (request.getMaintenanceSkillGrid() != null) {
                // Delete existing maintenance skills for this machine
                machineSkillRepository.deleteMaintenanceSkills(master.getKeyid());
                logger.info("Deleted existing maintenance skills for Machine: {}", master.getKeyid());
                
                // Insert new maintenance skills
                for (GenTlMachineskillmst skill : request.getMaintenanceSkillGrid()) {
                    skill.setMachineid(master.getKeyid());
                    skill.setSkillfordepartment('M');
                    
                    if (skill.getCreatedby() == null || skill.getCreatedby().trim().isEmpty()) {
                        skill.setCreatedby(master.getCreatedby());
                    }
                    if (skill.getCreatedon() == null) {
                        skill.setCreatedon(LocalDateTime.now());
                    }
                    skill.setModifiedon(LocalDateTime.now());
                    
                    GenTlMachineskillmst savedSkill = machineSkillRepository.save(skill);
                    resultMaintenanceSkills.add(savedSkill);
                }
                logger.info("Re-inserted {} maintenance skills for Machine: {}", 
                    resultMaintenanceSkills.size(), master.getKeyid());
            }

            // Handle Sub Equipment Grid (Delete all and re-insert)
            List<GenTlMchsubmchlink> resultSubEquipment = new ArrayList<>();
            if (request.getSubEquipmentGrid() != null) {
                // Delete existing sub equipment for this machine
                subEquipmentRepository.deleteByParentMachineId(master.getKeyid());
                logger.info("Deleted existing sub equipment links for Machine: {}", master.getKeyid());
                
                // Insert new sub equipment
                for (GenTlMchsubmchlink subEquip : request.getSubEquipmentGrid()) {
                    subEquip.setParentmchid(master.getKeyid());
                    subEquip.setCellid(master.getCellid());
                    
                    if (subEquip.getCreatedon() == null) {
                        subEquip.setCreatedon(LocalDateTime.now());
                    }
                    
                    GenTlMchsubmchlink savedSubEquip = subEquipmentRepository.save(subEquip);
                    resultSubEquipment.add(savedSubEquip);
                }
                logger.info("Re-inserted {} sub equipment links for Machine: {}", 
                    resultSubEquipment.size(), master.getKeyid());
            }

            // Prepare result
            result.setMaster(updatedMaster);
            result.setFunctionalLocation(resultFuncLocn);
            result.setOperatorGrid(resultOperators);
            result.setMaintenanceGrid(resultMaintenance);
            result.setOperatorSkillGrid(resultOperatorSkills);
            result.setMaintenanceSkillGrid(resultMaintenanceSkills);
            result.setSubEquipmentGrid(resultSubEquipment);
            result.setFormActionMode(request.getFormActionMode());
            result.setFormMode(request.getFormMode());
            result.setFormHeader(request.getFormHeader());
            result.setFormType(request.getFormType());

            logger.info("Successfully completed UPDATE mode for Machine Master: {}", master.getKeyid());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }
@Override
    public List<Map<String, Object>> getOperatorData(String factId) {
        logger.info("Fetching operator data for factory ID: {}", factId);
        
        List<Map<String, Object>> result = masterRepository.getOperatorData(factId);
        
        if (result == null || result.isEmpty()) {
            logger.warn("No operator data found for factory ID: {}", factId);
        } else {
            logger.info("Found {} operator records", result.size());
        }
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getOperatorSkillData() {
        logger.info("Fetching operator skill data");
        
        List<Map<String, Object>> result = masterRepository.getOperatorSkillData();
        
        if (result == null || result.isEmpty()) {
            logger.warn("No operator skill data found");
        } else {
            logger.info("Found {} operator skill records", result.size());
        }
        
        return result;
    }

     @Override
    public List<Map<String, Object>> getMaintenanceTeamDataForMachine(String machineId) {
        logger.info("Fetching maintenance team data for machine: {}", machineId);
        
        
        
        List<Map<String, Object>> result = masterRepository.getMaintenanceTeamDataForMachine(machineId);
        
        if (result == null || result.isEmpty()) {
            logger.warn("No maintenance team data found for machine: {}", machineId);
        } else {
            logger.info("Found {} maintenance team records for machine: {}", result.size(), machineId);
        }
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getMaintenanceSkillData() {
        logger.info("Fetching maintenance skill data");
        
        List<Map<String, Object>> result = masterRepository.getMaintenanceSkillData();
        
        if (result == null || result.isEmpty()) {
            logger.warn("No maintenance skill data found");
        } else {
            logger.info("Found {} maintenance skill records", result.size());
        }
        
        return result;
    }
     @Override
    public List<Map<String, Object>> getEquipmentData(String equipmentNum) {
        logger.info("Fetching equipment data for equipment number: {}", equipmentNum);
        
        List<Map<String, Object>> result = masterRepository.getEquipmentData(equipmentNum);
        
        if (result == null || result.isEmpty()) {
            logger.warn("No equipment data found for equipment number: {}", equipmentNum);
        } else {
            logger.info("Found {} equipment records", result.size());
        }
        
        return result;
    }
    @Override
    public List<Map<String, Object>> getSubEquipmentData(String sectId, String eqpId) {
        logger.info("Fetching sub equipment data for sectId: {}, eqpId: {}", sectId, eqpId);
        
        // Validate and normalize parameters
        String validSectId = ValidationUtil.isValidKeyId(sectId) ? sectId : null;
        String validEqpId = ValidationUtil.isValidKeyId(eqpId) ? eqpId : null;
        
        logger.info("Validated parameters - sectId: {}, eqpId: {}", validSectId, validEqpId);
        
        List<Map<String, Object>> result = masterRepository.getSubEquipmentData(validSectId, validEqpId);
        
        if (result == null || result.isEmpty()) {
            logger.warn("No sub equipment data found for sectId: {}, eqpId: {}", validSectId, validEqpId);
        } else {
            logger.info("Found {} sub equipment records", result.size());
        }
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getFormCircle(String mchId) {
        logger.info("Fetching form circle data for machine: {}", mchId);
        
        List<Map<String, Object>> result = masterRepository.getFormCircle(mchId);
        
        if (result == null || result.isEmpty()) {
            logger.warn("No circle data found for machine: {}", mchId);
        } else {
            logger.info("Found {} circle records for machine: {}", result.size(), mchId);
        }
        
        return result;
    }

   @Override
public GenTlMachinemst getEquipmentMasterById(String machineId) {
    logger.info("Fetching equipment master data for machine ID: {}", machineId);
    
    GenTlMachinemst result = masterRepository.findBykeyid(machineId);
    
    if (result == null) {
        logger.warn("No equipment master data found for machine ID: {}", machineId);
    } else {
        logger.info("Found equipment master record for machine ID: {}", machineId);
    }
    
    return result;
}

@Override
public List<Map<String, Object>> recallOperatorData(String machineId) {
    logger.info("Fetching operator data for machine ID: {}", machineId);
    
    List<Map<String, Object>> result = masterRepository.recallOperatorData(machineId);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No operator data found for machine ID: {}", machineId);
    } else {
        logger.info("Found {} operator records for machine ID: {}", result.size(), machineId);
    }
    
    return result;
}

@Override
public List<Map<String, Object>> recallOperatorSkillData(String machineId) {
    logger.info("Fetching operator skill data for machine ID: {}", machineId);
    
    List<Map<String, Object>> result = masterRepository.recallOperatorSkillData(machineId);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No operator skill data found for machine ID: {}", machineId);
    } else {
        logger.info("Found {} operator skill records for machine ID: {}", result.size(), machineId);
    }
    
    return result;
}

@Override
public List<Map<String, Object>> recallMaintenanceData(String machineId) {
    logger.info("Fetching maintenance data for machine ID: {}", machineId);
    
    List<Map<String, Object>> result = masterRepository.recallMaintenanceData(machineId);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No maintenance data found for machine ID: {}", machineId);
    } else {
        logger.info("Found {} maintenance records for machine ID: {}", result.size(), machineId);
    }
    
    return result;
}

@Override
public List<Map<String, Object>> recallMaintenanceSkillData(String machineId) {
    logger.info("Fetching maintenance skill data for machine ID: {}", machineId);
    
    List<Map<String, Object>> result = masterRepository.recallMaintenanceSkillData(machineId);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No maintenance skill data found for machine ID: {}", machineId);
    } else {
        logger.info("Found {} maintenance skill records for machine ID: {}", result.size(), machineId);
    }
    
    return result;
}
@Override
public List<Map<String, Object>> recallEquipmentParameterData(String machineId) {
    logger.info("Fetching equipment parameter data for machine ID: {}", machineId);
    
    List<Map<String, Object>> result = masterRepository.recallEquipmentParameterData(machineId);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No equipment parameter data found for machine ID: {}", machineId);
    } else {
        logger.info("Found {} equipment parameter records for machine ID: {}", result.size(), machineId);
    }
    
    return result;
}

@Override
@Transactional
public boolean deleteOperatorSkill(String machineId, String skillDescription) throws Exception {
    logger.info("Deleting operator skill for machine: {} with skill: {}", machineId, skillDescription);
    
    if (machineId == null || machineId.trim().isEmpty()) {
        throw new IllegalArgumentException("Machine ID cannot be null or empty");
    }
    
    if (skillDescription == null || skillDescription.trim().isEmpty()) {
        throw new IllegalArgumentException("Skill description cannot be null or empty");
    }
    
    // Check if the skill exists for this machine
    List<Map<String, Object>> existingSkills = masterRepository.recallOperatorSkillData(machineId);
    boolean skillExists = existingSkills.stream()
        .anyMatch(skill -> skillDescription.equals(skill.get("MSKM_SKILLDESCRIPTION")));
    
    if (!skillExists) {
        logger.warn("Operator skill not found for machine: {} with skill: {}", machineId, skillDescription);
        throw new ResourceNotFoundException("Operator skill not found for machine: " + machineId + " with skill: " + skillDescription);
    }
    
    int rowsAffected = masterRepository.deleteOperatorSkill(machineId, skillDescription);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete operator skill for machine: {} with skill: {}", machineId, skillDescription);
        throw new RuntimeException("Failed to delete operator skill");
    }
    
    logger.info("Successfully deleted operator skill for machine: {} with skill: {}. Rows affected: {}", 
        machineId, skillDescription, rowsAffected);
    return true;
}
@Override
@Transactional
public boolean deleteMaintenanceSkill(String machineId, String skillDescription) throws Exception {
    logger.info("Deleting maintenance skill for machine: {} with skill: {}", machineId, skillDescription);
    
    if (machineId == null || machineId.trim().isEmpty()) {
        throw new IllegalArgumentException("Machine ID cannot be null or empty");
    }
    
    if (skillDescription == null || skillDescription.trim().isEmpty()) {
        throw new IllegalArgumentException("Skill description cannot be null or empty");
    }
    
    // Check if the skill exists for this machine
    List<Map<String, Object>> existingSkills = masterRepository.recallMaintenanceSkillData(machineId);
    boolean skillExists = existingSkills.stream()
        .anyMatch(skill -> skillDescription.equals(skill.get("MSKM_SKILLDESCRIPTION")));
    
    if (!skillExists) {
        logger.warn("Maintenance skill not found for machine: {} with skill: {}", machineId, skillDescription);
        throw new ResourceNotFoundException("Maintenance skill not found for machine: " + machineId + " with skill: " + skillDescription);
    }
    
    int rowsAffected = masterRepository.deleteMaintenanceSkill(machineId, skillDescription);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete maintenance skill for machine: {} with skill: {}", machineId, skillDescription);
        throw new RuntimeException("Failed to delete maintenance skill");
    }
    
    logger.info("Successfully deleted maintenance skill for machine: {} with skill: {}. Rows affected: {}", 
        machineId, skillDescription, rowsAffected);
    return true;
}

@Override
@Transactional
public boolean deleteOperatorMachineLink(String machineId, String employeeId) throws Exception {
    logger.info("Deleting operator machine link for machine: {} and employee: {}", machineId, employeeId);
    
    if (machineId == null || machineId.trim().isEmpty()) {
        throw new IllegalArgumentException("Machine ID cannot be null or empty");
    }
    
    if (employeeId == null || employeeId.trim().isEmpty()) {
        throw new IllegalArgumentException("Employee ID cannot be null or empty");
    }
    
    // Check if the link exists
    List<Map<String, Object>> existingOperators = masterRepository.recallOperatorData(machineId);
    boolean linkExists = existingOperators.stream()
        .anyMatch(operator -> employeeId.equals(operator.get("EMPM_KEYID")));
    
    if (!linkExists) {
        logger.warn("Operator machine link not found for machine: {} and employee: {}", machineId, employeeId);
        throw new ResourceNotFoundException("Operator machine link not found for machine: " + machineId + " and employee: " + employeeId);
    }
    
    int rowsAffected = masterRepository.deleteOperatorMachineLink(machineId, employeeId);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete operator machine link for machine: {} and employee: {}", machineId, employeeId);
        throw new RuntimeException("Failed to delete operator machine link");
    }
    
    logger.info("Successfully deleted operator machine link for machine: {} and employee: {}. Rows affected: {}", 
        machineId, employeeId, rowsAffected);
    return true;
}
@Override
@Transactional
public boolean deleteMaintenanceTeamMachineLink(String machineId, String maintenanceTeamId) throws Exception {
    logger.info("Deleting maintenance team machine link for machine: {} and team: {}", machineId, maintenanceTeamId);
    
    if (machineId == null || machineId.trim().isEmpty()) {
        throw new IllegalArgumentException("Machine ID cannot be null or empty");
    }
    
    if (maintenanceTeamId == null || maintenanceTeamId.trim().isEmpty()) {
        throw new IllegalArgumentException("Maintenance Team ID cannot be null or empty");
    }
    
    // Check if the link exists
    List<Map<String, Object>> existingTeams = masterRepository.recallMaintenanceData(machineId);
    boolean linkExists = existingTeams.stream()
        .anyMatch(team -> maintenanceTeamId.equals(team.get("MTMM_KEYID")));
    
    if (!linkExists) {
        logger.warn("Maintenance team machine link not found for machine: {} and team: {}", machineId, maintenanceTeamId);
        throw new ResourceNotFoundException("Maintenance team machine link not found for machine: " + machineId + " and team: " + maintenanceTeamId);
    }
    
    int rowsAffected = masterRepository.deleteMaintenanceTeamMachineLink(machineId, maintenanceTeamId);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete maintenance team machine link for machine: {} and team: {}", machineId, maintenanceTeamId);
        throw new RuntimeException("Failed to delete maintenance team machine link");
    }
    
    logger.info("Successfully deleted maintenance team machine link for machine: {} and team: {}. Rows affected: {}", 
        machineId, maintenanceTeamId, rowsAffected);
    return true;
}
    
}