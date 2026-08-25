package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.dto.PcsSaveRequestDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class PcsSaveRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PcsSaveRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ---------- table helpers ----------

    public String findSectionCode(String sectKeyid) {
        return jdbcTemplate.queryForObject(
                "SELECT sect_code FROM gen_tl_sectionmst WHERE sect_keyid = :sectId",
                new MapSqlParameterSource("sectId", sectKeyid),
                String.class
        );
    }

    public boolean tableExists(String tableName) {
        Integer cnt = jdbcTemplate.queryForObject(
                "SELECT count(1) FROM pg_tables WHERE tablename = :tbl",
                new MapSqlParameterSource("tbl", tableName.toLowerCase()),
                Integer.class
        );
        return cnt != null && cnt > 0;
    }

    public void cloneDetailTable(String tableName) {
        jdbcTemplate.update("CREATE TABLE " + tableName + " AS SELECT * FROM pcs_tl_dtl", Map.of());
    }

    public void addPrimaryKey(String tableName) {
        String pkConst = tableName.replace("pcs_tl_", "");
        jdbcTemplate.update(
                "ALTER TABLE " + tableName + " ADD CONSTRAINT pk_pcs_tl_dtl_" + pkConst + " PRIMARY KEY (pldetailsid)",
                Map.of()
        );
    }

    public void addForeignKey(String tableName) {
        String fkConst = tableName.replace("pcs_tl_", "");
        jdbcTemplate.update(
                "ALTER TABLE " + tableName +
                        " ADD CONSTRAINT fk_plmasterid_" + fkConst +
                        " FOREIGN KEY (plmasterid) REFERENCES pcs_tl_mst (prlm_keyid)",
                Map.of()
        );
    }

    // ---------- shift split helper ----------

    public List<ShiftSegment> callProdLossShiftCal(LocalDateTime fromTime, LocalDateTime toTime, int losstime) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("fromTime", fromTime)
                .addValue("toTime", toTime)
                .addValue("losstime", losstime);

        return jdbcTemplate.query(
                "SELECT * FROM PCS_FN_PRODLOSSSHIFTCAL(:fromTime, :toTime, :losstime)",
                params,
                new ShiftSegmentRowMapper()
        );
    }

    public Integer diffFromTpmStart(LocalDateTime entryDate) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("entryDate", entryDate);
        Integer diff = jdbcTemplate.queryForObject(
                """
                        SELECT (:entryDate::date - cnfm_fromdate::date)
                        FROM adm_tl_configurationmst
                        WHERE cnfm_code = 'TPMSTARTDATE'
                        LIMIT 1
                        """,
                params,
                Integer.class
        );
        return diff;
    }

    // ---------- lookups ----------

    public String findSectionIdByCellId(String cellId) {
        return jdbcTemplate.queryForObject(
                "SELECT cell_sectionid FROM gen_tl_cellmst WHERE cell_keyid = :cellId",
                new MapSqlParameterSource("cellId", cellId),
                String.class
        );
    }

    public boolean masterExists(String keyid) {
        Integer cnt = jdbcTemplate.queryForObject(
                "SELECT count(1) FROM pcs_tl_mst WHERE prlm_keyid = :id",
                new MapSqlParameterSource("id", keyid),
                Integer.class
        );
        return cnt != null && cnt > 0;
    }

    // ---------- loss cause ----------

    public boolean lossCauseExists(String causeId) {
        Integer cnt = jdbcTemplate.queryForObject(
                "SELECT count(1) FROM pcs_tl_losscausemst WHERE plcs_keyid = :id",
                new MapSqlParameterSource("id", causeId),
                Integer.class
        );
        return cnt != null && cnt > 0;
    }

    public boolean lossReasonExists(String keyId) {
        Integer cnt = jdbcTemplate.queryForObject(
                "SELECT count(1) FROM pcs_tl_lossreasonlink WHERE plrk_keyid = :id",
                new MapSqlParameterSource("id", keyId),
                Integer.class
        );
        return cnt != null && cnt > 0;
    }

    public boolean lossCaptureExists(String keyId) {
        Integer cnt = jdbcTemplate.queryForObject(
                "SELECT count(1) FROM pcs_tl_losscapture WHERE plos_keyid = :id",
                new MapSqlParameterSource("id", keyId),
                Integer.class
        );
        return cnt != null && cnt > 0;
    }

    public int insertLossCause(String causeId, String phenomenaId, String description) {
        String sql = """
                INSERT INTO pcs_tl_losscausemst (
                    plcs_keyid, plcs_phenomenaid, plcs_description, plcs_tempfield2, plcs_tempfield3
                ) VALUES (
                    :plcs_keyid, :plcs_phenomenaid, :plcs_description, :plcs_tempfield2, :plcs_tempfield3
                )
                """;
        return jdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("plcs_keyid", causeId)
                .addValue("plcs_phenomenaid", phenomenaId)
                .addValue("plcs_description", description == null ? "-" : description)
                .addValue("plcs_tempfield2", "{}")
                .addValue("plcs_tempfield3", "{}"));
    }

    // ---------- ancillary time ----------

    public void deleteAncilliaryTime(String detailId, String lossId) {
        jdbcTemplate.update(
                "DELETE FROM pcs_tl_ancilliarytime WHERE ptat_pldeatilsid = :detailId AND ptat_lossid = :lossId",
                new MapSqlParameterSource()
                        .addValue("detailId", detailId)
                        .addValue("lossId", lossId)
        );
    }

    // ---------- loss minutes aggregation & single column update ----------

    public BigDecimal sumLossMinutes(String detailId, String lossId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("detailId", detailId)
                .addValue("lossId", lossId);
        BigDecimal total = jdbcTemplate.queryForObject(
                """
                        SELECT COALESCE(SUM(plrk_minutes), 0)
                        FROM pcs_tl_lossreasonlink
                        WHERE plrk_pldetailid = :detailId
                          AND plrk_lossid = :lossId
                        """,
                params,
                BigDecimal.class
        );
        return total == null ? BigDecimal.ZERO : total;
    }

    public int updateSingleLossColumn(String tableName, String detailId, String columnName, BigDecimal value) {
        if (columnName == null || !columnName.matches("^[a-zA-Z0-9_]+$")) {
            return 0;
        }
        String col = columnName.toLowerCase();
        if (!columnExists(tableName, col)) {
            return 0;
        }
        String sql = "UPDATE " + tableName + " SET " + col + " = :val WHERE pldetailsid = :id";
        try {
            return jdbcTemplate.update(sql, new MapSqlParameterSource()
                    .addValue("val", value)
                    .addValue("id", detailId));
        } catch (Exception ex) {
            return 0;
        }
    }

    public boolean columnExists(String tableName, String columnName) {
        Integer cnt = jdbcTemplate.queryForObject(
                """
                SELECT count(1)
                FROM information_schema.columns
                WHERE table_schema = current_schema()
                  AND table_name = :tableName
                  AND column_name = :columnName
                """,
                new MapSqlParameterSource()
                        .addValue("tableName", tableName.toLowerCase())
                        .addValue("columnName", columnName.toLowerCase()),
                Integer.class
        );
        return cnt != null && cnt > 0;
    }

    public String findExistingMasterKey(LocalDateTime entryDate,
                                        String shiftId,
                                        String sectionId,
                                        String cellId,
                                        String subgroupId,
                                        String machineId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("entryDate", entryDate)
                .addValue("shiftId", shiftId)
                .addValue("sectionId", sectionId)
                .addValue("cellId", cellId)
                .addValue("subgroupId", subgroupId)
                .addValue("machineId", machineId);

        List<String> ids = jdbcTemplate.query(
                """
                        SELECT prlm_keyid
                        FROM pcs_tl_mst
                        WHERE prlm_entrydate::date = :entryDate::date
                          AND prlm_shiftid = :shiftId
                          AND prlm_sectionid = :sectionId
                          AND prlm_cellid = :cellId
                          AND prlm_subgroupid = :subgroupId
                          AND prlm_machineid = :machineId
                        """,
                params,
                (rs, rowNum) -> rs.getString(1)
        );
        return ids.isEmpty() ? null : ids.get(0);
    }

    // ---------- master ----------

    public int insertMaster(PcsSaveRequestDto.PcsMasterDto m) {
        String sql = """
                INSERT INTO pcs_tl_mst (
                    prlm_keyid, prlm_date, prlm_factoryid, prlm_sectionid, prlm_cellid, prlm_machineid,
                    prlm_shiftid, prlm_entrydate, prlm_entryby, prlm_updatedby, prlm_updateddate,
                    prlm_approvedby, prlm_approvedate, prlm_apporvedflag, prlm_shiftincharge,
                    prlm_subgroupid, prlm_isnoplan, prlm_logtype, prlm_timeorqty, prlm_completedflag,
                    prlm_tempfield2, prlm_flid, prlm_elementid, prlm_active, prlm_createdby,
                    prlm_createdon, prlm_modifiedon
                ) VALUES (
                    :prlm_keyid, :prlm_date, :prlm_factoryid, :prlm_sectionid, :prlm_cellid, :prlm_machineid,
                    :prlm_shiftid, :prlm_entrydate, :prlm_entryby, :prlm_updatedby, :prlm_updateddate,
                    :prlm_approvedby, :prlm_approvedate, :prlm_apporvedflag, :prlm_shiftincharge,
                    :prlm_subgroupid, :prlm_isnoplan, :prlm_logtype, :prlm_timeorqty, :prlm_completedflag,
                    :prlm_tempfield2, :prlm_flid, :prlm_elementid, :prlm_active, :prlm_createdby,
                    :prlm_createdon, :prlm_modifiedon
                )
                """;
        return jdbcTemplate.update(sql, masterParams(m));
    }

    public int updateMaster(PcsSaveRequestDto.PcsMasterDto m) {
        String sql = """
                UPDATE pcs_tl_mst SET
                    prlm_date = :prlm_date,
                    prlm_factoryid = :prlm_factoryid,
                    prlm_sectionid = :prlm_sectionid,
                    prlm_cellid = :prlm_cellid,
                    prlm_machineid = :prlm_machineid,
                    prlm_shiftid = :prlm_shiftid,
                    prlm_entrydate = :prlm_entrydate,
                    prlm_entryby = :prlm_entryby,
                    prlm_updatedby = :prlm_updatedby,
                    prlm_updateddate = :prlm_updateddate,
                    prlm_approvedby = :prlm_approvedby,
                    prlm_approvedate = :prlm_approvedate,
                    prlm_apporvedflag = :prlm_apporvedflag,
                    prlm_shiftincharge = :prlm_shiftincharge,
                    prlm_subgroupid = :prlm_subgroupid,
                    prlm_isnoplan = :prlm_isnoplan,
                    prlm_logtype = :prlm_logtype,
                    prlm_timeorqty = :prlm_timeorqty,
                    prlm_completedflag = :prlm_completedflag,
                    prlm_tempfield2 = :prlm_tempfield2,
                    prlm_flid = :prlm_flid,
                    prlm_elementid = :prlm_elementid,
                    prlm_active = :prlm_active,
                    prlm_createdby = :prlm_createdby,
                    prlm_createdon = :prlm_createdon,
                    prlm_modifiedon = :prlm_modifiedon
                WHERE prlm_keyid = :prlm_keyid
                """;
        return jdbcTemplate.update(sql, masterParams(m));
    }

    private MapSqlParameterSource masterParams(PcsSaveRequestDto.PcsMasterDto m) {
        return new MapSqlParameterSource()
                .addValue("prlm_keyid", m.getPrlmKeyid())
                .addValue("prlm_date", m.getPrlmDate())
                .addValue("prlm_factoryid", m.getPrlmFactoryid())
                .addValue("prlm_sectionid", m.getPrlmSectionid())
                .addValue("prlm_cellid", m.getPrlmCellid())
                .addValue("prlm_machineid", m.getPrlmMachineid())
                .addValue("prlm_shiftid", m.getPrlmShiftid())
                .addValue("prlm_entrydate", m.getPrlmEntrydate())
                .addValue("prlm_entryby", m.getPrlmEntryby())
                .addValue("prlm_updatedby", m.getPrlmUpdatedby())
                .addValue("prlm_updateddate", m.getPrlmUpdateddate())
                .addValue("prlm_approvedby", m.getPrlmApprovedby())
                .addValue("prlm_approvedate", m.getPrlmApprovedate())
                .addValue("prlm_apporvedflag", m.getPrlmApporvedflag())
                .addValue("prlm_shiftincharge", m.getPrlmShiftincharge())
                .addValue("prlm_subgroupid", m.getPrlmSubgroupid())
                .addValue("prlm_isnoplan", m.getPrlmIsnoplan())
                .addValue("prlm_logtype", m.getPrlmLogtype())
                .addValue("prlm_timeorqty", m.getPrlmTimeorqty())
                .addValue("prlm_completedflag", m.getPrlmCompletedflag())
                .addValue("prlm_tempfield2", m.getPrlmTempfield2())
                .addValue("prlm_flid", m.getPrlmFlid())
                .addValue("prlm_elementid", m.getPrlmElementid())
                .addValue("prlm_active", m.getPrlmActive())
                .addValue("prlm_createdby", m.getPrlmCreatedby())
                .addValue("prlm_createdon", m.getPrlmCreatedon())
                .addValue("prlm_modifiedon", m.getPrlmModifiedon());
    }

    // ---------- detail ----------

    public int insertDetail(String tableName, PcsSaveRequestDto.PcsDetailDto d) {
        String sql = """
                INSERT INTO %s (
                    pldetailsid, plmasterid, cellid, machineid, operators, calendartime, noplaninmins,
                    noofproducts, productid, plannedqty, rawmaterialtype, weight, wno, operationno,
                    operationdescription, theoriticalcycletime, actualcycletime, batchno, cavityavailable,
                    cavityused, mandrelavailable, mandrelused, producedqty, defectsandreworkloss_ml,
                    rejectedqty, reworkqty, defecttime_sl, productionlosses, equipmentfailure_ml,
                    setupandadjustment_ml, toolchangeloss_ml, startuploss_ml, minorstoppageloss_ml,
                    speedloss_ml, shutdownloss_ml, managementloss_ml, commonutilityloss_sl,
                    operatingmotionloss_ml, lineorganisationloss_ml, logisticsloss_ml,
                    measuringandadjloss_ml, dietoolandjigloss_ml, energyloss_ml, yieldloss_ml,
                    unaccountedtime, loadingtime, effectiveprodmins, mchavailabletime, productionavltime,
                    productiontime, roa, rop, roq, oee, completedflag, remarks, trimmingqty, expansionqty,
                    modelchangepart, reprocessing, inspectedqty, qaacceptedqty, backlogqty,
                    actualruntime_old, netutilisationtime_old, plantavltime_old, ae, au, oe,
                    pfprocessfailureloss, ancillaryutilityloss, mgmloadingtime, mgmproductiontime,
                    mgmoe, mgmroa, mgmrop, mgmroq, mgmoee, active, createdby, createdon, modifiedon
                ) VALUES (
                    :pldetailsid, :plmasterid, :cellid, :machineid, :operators, :calendartime, :noplaninmins,
                    :noofproducts, :productid, :plannedqty, :rawmaterialtype, :weight, :wno, :operationno,
                    :operationdescription, :theoriticalcycletime, :actualcycletime, :batchno, :cavityavailable,
                    :cavityused, :mandrelavailable, :mandrelused, :producedqty, :defectsandreworkloss_ml,
                    :rejectedqty, :reworkqty, :defecttime_sl, :productionlosses, :equipmentfailure_ml,
                    :setupandadjustment_ml, :toolchangeloss_ml, :startuploss_ml, :minorstoppageloss_ml,
                    :speedloss_ml, :shutdownloss_ml, :managementloss_ml, :commonutilityloss_sl,
                    :operatingmotionloss_ml, :lineorganisationloss_ml, :logisticsloss_ml,
                    :measuringandadjloss_ml, :dietoolandjigloss_ml, :energyloss_ml, :yieldloss_ml,
                    :unaccountedtime, :loadingtime, :effectiveprodmins, :mchavailabletime, :productionavltime,
                    :productiontime, :roa, :rop, :roq, :oee, :completedflag, :remarks, :trimmingqty, :expansionqty,
                    :modelchangepart, :reprocessing, :inspectedqty, :qaacceptedqty, :backlogqty,
                    :actualruntime_old, :netutilisationtime_old, :plantavltime_old, :ae, :au, :oe,
                    :pfprocessfailureloss, :ancillaryutilityloss, :mgmloadingtime, :mgmproductiontime,
                    :mgmoe, :mgmroa, :mgmrop, :mgmroq, :mgmoee, :active, :createdby, :createdon, :modifiedon
                )
                """.formatted(tableName);
        return jdbcTemplate.update(sql, detailParams(d));
    }

    public int updateDetail(String tableName, PcsSaveRequestDto.PcsDetailDto d) {
        String sql = """
                UPDATE %s SET
                    plmasterid = :plmasterid,
                    cellid = :cellid,
                    machineid = :machineid,
                    operators = :operators,
                    calendartime = :calendartime,
                    noplaninmins = :noplaninmins,
                    noofproducts = :noofproducts,
                    productid = :productid,
                    plannedqty = :plannedqty,
                    rawmaterialtype = :rawmaterialtype,
                    weight = :weight,
                    wno = :wno,
                    operationno = :operationno,
                    operationdescription = :operationdescription,
                    theoriticalcycletime = :theoriticalcycletime,
                    actualcycletime = :actualcycletime,
                    batchno = :batchno,
                    cavityavailable = :cavityavailable,
                    cavityused = :cavityused,
                    mandrelavailable = :mandrelavailable,
                    mandrelused = :mandrelused,
                    producedqty = :producedqty,
                    defectsandreworkloss_ml = :defectsandreworkloss_ml,
                    rejectedqty = :rejectedqty,
                    reworkqty = :reworkqty,
                    defecttime_sl = :defecttime_sl,
                    productionlosses = :productionlosses,
                    equipmentfailure_ml = :equipmentfailure_ml,
                    setupandadjustment_ml = :setupandadjustment_ml,
                    toolchangeloss_ml = :toolchangeloss_ml,
                    startuploss_ml = :startuploss_ml,
                    minorstoppageloss_ml = :minorstoppageloss_ml,
                    speedloss_ml = :speedloss_ml,
                    shutdownloss_ml = :shutdownloss_ml,
                    managementloss_ml = :managementloss_ml,
                    commonutilityloss_sl = :commonutilityloss_sl,
                    operatingmotionloss_ml = :operatingmotionloss_ml,
                    lineorganisationloss_ml = :lineorganisationloss_ml,
                    logisticsloss_ml = :logisticsloss_ml,
                    measuringandadjloss_ml = :measuringandadjloss_ml,
                    dietoolandjigloss_ml = :dietoolandjigloss_ml,
                    energyloss_ml = :energyloss_ml,
                    yieldloss_ml = :yieldloss_ml,
                    unaccountedtime = :unaccountedtime,
                    loadingtime = :loadingtime,
                    effectiveprodmins = :effectiveprodmins,
                    mchavailabletime = :mchavailabletime,
                    productionavltime = :productionavltime,
                    productiontime = :productiontime,
                    roa = :roa,
                    rop = :rop,
                    roq = :roq,
                    oee = :oee,
                    completedflag = :completedflag,
                    remarks = :remarks,
                    trimmingqty = :trimmingqty,
                    expansionqty = :expansionqty,
                    modelchangepart = :modelchangepart,
                    reprocessing = :reprocessing,
                    inspectedqty = :inspectedqty,
                    qaacceptedqty = :qaacceptedqty,
                    backlogqty = :backlogqty,
                    actualruntime_old = :actualruntime_old,
                    netutilisationtime_old = :netutilisationtime_old,
                    plantavltime_old = :plantavltime_old,
                    ae = :ae,
                    au = :au,
                    oe = :oe,
                    pfprocessfailureloss = :pfprocessfailureloss,
                    ancillaryutilityloss = :ancillaryutilityloss,
                    mgmloadingtime = :mgmloadingtime,
                    mgmproductiontime = :mgmproductiontime,
                    mgmoe = :mgmoe,
                    mgmroa = :mgmroa,
                    mgmrop = :mgmrop,
                    mgmroq = :mgmroq,
                    mgmoee = :mgmoee,
                    active = :active,
                    createdby = :createdby,
                    createdon = :createdon,
                    modifiedon = :modifiedon
                WHERE pldetailsid = :pldetailsid
                """.formatted(tableName);
        return jdbcTemplate.update(sql, detailParams(d));
    }

    private MapSqlParameterSource detailParams(PcsSaveRequestDto.PcsDetailDto d) {
        return new MapSqlParameterSource()
                .addValue("pldetailsid", d.getPldetailsid())
                .addValue("plmasterid", d.getPlmasterid())
                .addValue("cellid", d.getCellid())
                .addValue("machineid", d.getMachineid())
                .addValue("operators", d.getOperators())
                .addValue("calendartime", d.getCalendartime())
                .addValue("noplaninmins", d.getNoplaninmins())
                .addValue("noofproducts", d.getNoofproducts())
                .addValue("productid", d.getProductid())
                .addValue("plannedqty", d.getPlannedqty())
                .addValue("rawmaterialtype", d.getRawmaterialtype())
                .addValue("weight", d.getWeight())
                .addValue("wno", d.getWno())
                .addValue("operationno", d.getOperationno())
                .addValue("operationdescription", d.getOperationdescription())
                .addValue("theoriticalcycletime", d.getTheoriticalcycletime())
                .addValue("actualcycletime", d.getActualcycletime())
                .addValue("batchno", d.getBatchno())
                .addValue("cavityavailable", d.getCavityavailable())
                .addValue("cavityused", d.getCavityused())
                .addValue("mandrelavailable", d.getMandrelavailable())
                .addValue("mandrelused", d.getMandrelused())
                .addValue("producedqty", d.getProducedqty())
                .addValue("defectsandreworkloss_ml", d.getDefectsandreworklossMl())
                .addValue("rejectedqty", d.getRejectedqty())
                .addValue("reworkqty", d.getReworkqty())
                .addValue("defecttime_sl", d.getDefecttimeSl())
                .addValue("productionlosses", d.getProductionlosses())
                .addValue("equipmentfailure_ml", d.getEquipmentfailureMl())
                .addValue("setupandadjustment_ml", d.getSetupandadjustmentMl())
                .addValue("toolchangeloss_ml", d.getToolchangelossMl())
                .addValue("startuploss_ml", d.getStartuplossMl())
                .addValue("minorstoppageloss_ml", d.getMinorstoppagelossMl())
                .addValue("speedloss_ml", d.getSpeedlossMl())
                .addValue("shutdownloss_ml", d.getShutdownlossMl())
                .addValue("managementloss_ml", d.getManagementlossMl())
                .addValue("commonutilityloss_sl", d.getCommonutilitylossSl())
                .addValue("operatingmotionloss_ml", d.getOperatingmotionlossMl())
                .addValue("lineorganisationloss_ml", d.getLineorganisationlossMl())
                .addValue("logisticsloss_ml", d.getLogisticslossMl())
                .addValue("measuringandadjloss_ml", d.getMeasuringandadjlossMl())
                .addValue("dietoolandjigloss_ml", d.getDietoolandjiglossMl())
                .addValue("energyloss_ml", d.getEnergylossMl())
                .addValue("yieldloss_ml", d.getYieldlossMl())
                .addValue("unaccountedtime", d.getUnaccountedtime())
                .addValue("loadingtime", d.getLoadingtime())
                .addValue("effectiveprodmins", d.getEffectiveprodmins())
                .addValue("mchavailabletime", d.getMchavailabletime())
                .addValue("productionavltime", d.getProductionavltime())
                .addValue("productiontime", d.getProductiontime())
                .addValue("roa", d.getRoa())
                .addValue("rop", d.getRop())
                .addValue("roq", d.getRoq())
                .addValue("oee", d.getOee())
                .addValue("completedflag", d.getCompletedflag())
                .addValue("remarks", d.getRemarks())
                .addValue("trimmingqty", d.getTrimmingqty())
                .addValue("expansionqty", d.getExpansionqty())
                .addValue("modelchangepart", d.getModelchangepart())
                .addValue("reprocessing", d.getReprocessing())
                .addValue("inspectedqty", d.getInspectedqty())
                .addValue("qaacceptedqty", d.getQaacceptedqty())
                .addValue("backlogqty", d.getBacklogqty())
                .addValue("actualruntime_old", d.getActualruntimeOld())
                .addValue("netutilisationtime_old", d.getNetutilisationtimeOld())
                .addValue("plantavltime_old", d.getPlantavltimeOld())
                .addValue("ae", d.getAe())
                .addValue("au", d.getAu())
                .addValue("oe", d.getOe())
                .addValue("pfprocessfailureloss", d.getPfprocessfailureloss())
                .addValue("ancillaryutilityloss", d.getAncillaryutilityloss())
                .addValue("mgmloadingtime", d.getMgmloadingtime())
                .addValue("mgmproductiontime", d.getMgmproductiontime())
                .addValue("mgmoe", d.getMgmoe())
                .addValue("mgmroa", d.getMgmroa())
                .addValue("mgmrop", d.getMgmrop())
                .addValue("mgmroq", d.getMgmroq())
                .addValue("mgmoee", d.getMgmoee())
                .addValue("active", d.getActive())
                .addValue("createdby", d.getCreatedby())
                .addValue("createdon", d.getCreatedon())
                .addValue("modifiedon", d.getModifiedon());
    }

    public void updateLossColumns(String tableName, String pldetailsid, Map<String, BigDecimal> losses) {
        if (losses == null || losses.isEmpty()) return;
        for (Map.Entry<String, BigDecimal> entry : losses.entrySet()) {
            String col = entry.getKey();
            BigDecimal val = entry.getValue();
            if (col == null || val == null) continue;
            String normalizedCol = col.trim().toLowerCase();
            if (!normalizedCol.matches("^[a-z][a-z0-9_]*$")) continue;
            try {
                String sql = "UPDATE " + tableName + " SET " + normalizedCol + " = :val WHERE pldetailsid = :id";
                jdbcTemplate.update(sql, new MapSqlParameterSource().addValue("val", val).addValue("id", pldetailsid));
            } catch (Exception e) {
                // Unknown column names are skipped — do not fail the whole transaction
            }
        }
    }

    // ---------- loss reason link ----------

    public int insertLossReason(PcsSaveRequestDto.PcsLossReasonLinkDto d) {
        String sql = """
                INSERT INTO pcs_tl_lossreasonlink (
                    plrk_keyid, plrk_lossid, plrk_reasonid, plrk_causeid, plrk_rootcauseid, plrk_pldetailid,
                    plrk_wno, plrk_minutes, plrk_instance, plrk_date, plrk_shiftid, plrk_hourno, plrk_factoryid,
                    plrk_sectionid, plrk_cellid, plrk_machineid, plrk_subgroupid, plrk_remarks, plrk_msrno,
                    plrk_processid, plrk_flid, plrk_elementid, plrk_active, plrk_createdby, plrk_createdon,
                    plrk_modifiedon
                ) VALUES (
                    :plrk_keyid, :plrk_lossid, :plrk_reasonid, :plrk_causeid, :plrk_rootcauseid, :plrk_pldetailid,
                    :plrk_wno, :plrk_minutes, :plrk_instance, :plrk_date, :plrk_shiftid, :plrk_hourno, :plrk_factoryid,
                    :plrk_sectionid, :plrk_cellid, :plrk_machineid, :plrk_subgroupid, :plrk_remarks, :plrk_msrno,
                    :plrk_processid, :plrk_flid, :plrk_elementid, :plrk_active, :plrk_createdby, :plrk_createdon,
                    :plrk_modifiedon
                )
                """;
        return jdbcTemplate.update(sql, lossReasonParams(d));
    }

    public int updateLossReason(PcsSaveRequestDto.PcsLossReasonLinkDto d) {
        String sql = """
                UPDATE pcs_tl_lossreasonlink SET
                    plrk_lossid = :plrk_lossid,
                    plrk_reasonid = :plrk_reasonid,
                    plrk_causeid = :plrk_causeid,
                    plrk_rootcauseid = :plrk_rootcauseid,
                    plrk_pldetailid = :plrk_pldetailid,
                    plrk_wno = :plrk_wno,
                    plrk_minutes = :plrk_minutes,
                    plrk_instance = :plrk_instance,
                    plrk_date = :plrk_date,
                    plrk_shiftid = :plrk_shiftid,
                    plrk_hourno = :plrk_hourno,
                    plrk_factoryid = :plrk_factoryid,
                    plrk_sectionid = :plrk_sectionid,
                    plrk_cellid = :plrk_cellid,
                    plrk_machineid = :plrk_machineid,
                    plrk_subgroupid = :plrk_subgroupid,
                    plrk_remarks = :plrk_remarks,
                    plrk_msrno = :plrk_msrno,
                    plrk_processid = :plrk_processid,
                    plrk_flid = :plrk_flid,
                    plrk_elementid = :plrk_elementid,
                    plrk_active = :plrk_active,
                    plrk_createdby = :plrk_createdby,
                    plrk_createdon = :plrk_createdon,
                    plrk_modifiedon = :plrk_modifiedon
                WHERE plrk_keyid = :plrk_keyid
                """;
        return jdbcTemplate.update(sql, lossReasonParams(d));
    }

    private MapSqlParameterSource lossReasonParams(PcsSaveRequestDto.PcsLossReasonLinkDto d) {
        return new MapSqlParameterSource()
                .addValue("plrk_keyid", d.getPlrkKeyid())
                .addValue("plrk_lossid", d.getPlrkLossid())
                .addValue("plrk_reasonid", d.getPlrkReasonid())
                .addValue("plrk_causeid", d.getPlrkCauseid())
                .addValue("plrk_rootcauseid", d.getPlrkRootcauseid())
                .addValue("plrk_pldetailid", d.getPlrkPldetailid())
                .addValue("plrk_wno", d.getPlrkWno())
                .addValue("plrk_minutes", d.getPlrkMinutes())
                .addValue("plrk_instance", d.getPlrkInstance())
                .addValue("plrk_date", d.getPlrkDate())
                .addValue("plrk_shiftid", d.getPlrkShiftid())
                .addValue("plrk_hourno", d.getPlrkHourno())
                .addValue("plrk_factoryid", d.getPlrkFactoryid())
                .addValue("plrk_sectionid", d.getPlrkSectionid())
                .addValue("plrk_cellid", d.getPlrkCellid())
                .addValue("plrk_machineid", d.getPlrkMachineid())
                .addValue("plrk_subgroupid", d.getPlrkSubgroupid())
                .addValue("plrk_remarks", d.getPlrkRemarks())
                .addValue("plrk_msrno", d.getPlrkMsrno())
                .addValue("plrk_processid", d.getPlrkProcessid())
                .addValue("plrk_flid", d.getPlrkFlid())
                .addValue("plrk_elementid", d.getPlrkElementid())
                .addValue("plrk_active", d.getPlrkActive())
                .addValue("plrk_createdby", d.getPlrkCreatedby())
                .addValue("plrk_createdon", d.getPlrkCreatedon())
                .addValue("plrk_modifiedon", d.getPlrkModifiedon());
    }

    // ---------- loss capture ----------

    public int insertLossCapture(PcsSaveRequestDto.PcsLossCaptureDto d) {
        String sql = """
                INSERT INTO pcs_tl_losscapture (
                    plos_keyid, plos_flid, plos_date, plos_shiftid, plos_fromtime, plos_totime,
                    plos_losstime, plos_lossreason, plos_lossid, plos_tradeid, plos_prod_impact,
                    plos_prod_imp_qty, plos_lossdescription, plos_pldetailsid, plos_equipment,
                    plos_detectedby, plos_tempfield4, plos_tempfield5, plos_active, plos_createdby,
                    plos_createdon, plos_modifiedon
                ) VALUES (
                    :plos_keyid, :plos_flid, :plos_date, :plos_shiftid, :plos_fromtime, :plos_totime,
                    :plos_losstime, :plos_lossreason, :plos_lossid, :plos_tradeid, :plos_prod_impact,
                    :plos_prod_imp_qty, :plos_lossdescription, :plos_pldetailsid, :plos_equipment,
                    :plos_detectedby, :plos_tempfield4, :plos_tempfield5, :plos_active, :plos_createdby,
                    :plos_createdon, :plos_modifiedon
                )
                """;
        return jdbcTemplate.update(sql, lossCaptureParams(d));
    }

    public int updateLossCapture(PcsSaveRequestDto.PcsLossCaptureDto d) {
        String sql = """
                UPDATE pcs_tl_losscapture SET
                    plos_flid = :plos_flid,
                    plos_date = :plos_date,
                    plos_shiftid = :plos_shiftid,
                    plos_fromtime = :plos_fromtime,
                    plos_totime = :plos_totime,
                    plos_losstime = :plos_losstime,
                    plos_lossreason = :plos_lossreason,
                    plos_lossid = :plos_lossid,
                    plos_tradeid = :plos_tradeid,
                    plos_prod_impact = :plos_prod_impact,
                    plos_prod_imp_qty = :plos_prod_imp_qty,
                    plos_lossdescription = :plos_lossdescription,
                    plos_pldetailsid = :plos_pldetailsid,
                    plos_equipment = :plos_equipment,
                    plos_detectedby = :plos_detectedby,
                    plos_tempfield4 = :plos_tempfield4,
                    plos_tempfield5 = :plos_tempfield5,
                    plos_active = :plos_active,
                    plos_createdby = :plos_createdby,
                    plos_createdon = :plos_createdon,
                    plos_modifiedon = :plos_modifiedon
                WHERE plos_keyid = :plos_keyid
                """;
        return jdbcTemplate.update(sql, lossCaptureParams(d));
    }

    private MapSqlParameterSource lossCaptureParams(PcsSaveRequestDto.PcsLossCaptureDto d) {
        return new MapSqlParameterSource()
                .addValue("plos_keyid", d.getPlosKeyid())
                .addValue("plos_flid", d.getPlosFlid())
                .addValue("plos_date", d.getPlosDate())
                .addValue("plos_shiftid", d.getPlosShiftid())
                .addValue("plos_fromtime", d.getPlosFromtime())
                .addValue("plos_totime", d.getPlosTotime())
                .addValue("plos_losstime", d.getPlosLosstime())
                .addValue("plos_lossreason", d.getPlosLossreason())
                .addValue("plos_lossid", d.getPlosLossid())
                .addValue("plos_tradeid", d.getPlosTradeid())
                .addValue("plos_prod_impact", d.getPlosProdImpact())
                .addValue("plos_prod_imp_qty", d.getPlosProdImpQty())
                .addValue("plos_lossdescription", d.getPlosLossdescription())
                .addValue("plos_pldetailsid", d.getPlosPldetailsid())
                .addValue("plos_equipment", d.getPlosEquipment())
                .addValue("plos_detectedby", d.getPlosDetectedby())
                .addValue("plos_tempfield4", d.getPlosTempfield4())
                .addValue("plos_tempfield5", d.getPlosTempfield5())
                .addValue("plos_active", d.getPlosActive())
                .addValue("plos_createdby", d.getPlosCreatedby())
                .addValue("plos_createdon", d.getPlosCreatedon())
                .addValue("plos_modifiedon", d.getPlosModifiedon());
    }

    public record ShiftSegment(String shiftId, BigDecimal calendarTime) {
    }

    public String findDetailIdByLossCaptureId(String lossCaptureId) {
        List<String> ids = jdbcTemplate.query(
                "SELECT plos_pldetailsid FROM pcs_tl_losscapture WHERE plos_keyid = :id LIMIT 1",
                new MapSqlParameterSource("id", lossCaptureId),
                (rs, rowNum) -> rs.getString(1)
        );
        return ids.isEmpty() ? null : ids.get(0);
    }

    public String findDetailTableByDetailId(String detailId) {
        if (detailId == null || detailId.isBlank()) {
            return null;
        }

        List<String> tables = listDetailTables();

        for (String table : tables) {
            if (!isSafeIdentifier(table)) {
                continue;
            }
            List<Integer> matches = jdbcTemplate.query(
                    "SELECT 1 FROM " + table + " WHERE pldetailsid = :id LIMIT 1",
                    new MapSqlParameterSource("id", detailId),
                    (rs, rowNum) -> 1
            );
            if (!matches.isEmpty()) {
                return table;
            }
        }
        return null;
    }

    public String findDetailTableByMasterId(String masterId) {
        if (masterId == null || masterId.isBlank()) {
            return null;
        }

        List<String> tables = listDetailTables();
        for (String table : tables) {
            if (!isSafeIdentifier(table)) {
                continue;
            }
            List<Integer> matches = jdbcTemplate.query(
                    "SELECT 1 FROM " + table + " WHERE plmasterid = :id LIMIT 1",
                    new MapSqlParameterSource("id", masterId),
                    (rs, rowNum) -> 1
            );
            if (!matches.isEmpty()) {
                return table;
            }
        }
        return null;
    }

    public String findExistingDetailIdByMasterId(String tableName, String masterId) {
        if (!isSafeIdentifier(tableName) || masterId == null || masterId.isBlank()) {
            return null;
        }

        List<String> ids = jdbcTemplate.query(
                """
                SELECT pldetailsid
                FROM %s
                WHERE plmasterid = :masterId
                ORDER BY createdon ASC NULLS LAST, pldetailsid ASC
                LIMIT 1
                """.formatted(tableName),
                new MapSqlParameterSource("masterId", masterId),
                (rs, rowNum) -> rs.getString(1)
        );
        return ids.isEmpty() ? null : ids.get(0);
    }

    public String findExistingMasterId(LocalDateTime entryDate, String shiftId, String flid) {
        List<String> ids = jdbcTemplate.query(
                """
                SELECT prlm_keyid FROM pcs_tl_mst
                WHERE prlm_entrydate::date = :entryDate::date
                  AND prlm_shiftid = :shiftId
                  AND prlm_flid = :flid
                LIMIT 1
                """,
                new MapSqlParameterSource()
                        .addValue("entryDate", entryDate)
                        .addValue("shiftId", shiftId)
                        .addValue("flid", flid),
                (rs, rowNum) -> rs.getString(1)
        );
        return ids.isEmpty() ? null : ids.get(0);
    }

    private List<String> listDetailTables() {
        return jdbcTemplate.query(
                """
                SELECT DISTINCT table_name
                FROM information_schema.columns
                WHERE table_schema = current_schema()
                  AND column_name = 'pldetailsid'
                  AND table_name LIKE 'pcs_tl_%'
                  AND table_name IN (
                      SELECT table_name
                      FROM information_schema.tables
                      WHERE table_schema = current_schema()
                        AND table_type = 'BASE TABLE'
                  )
                ORDER BY table_name
                """,
                Map.of(),
                (rs, rowNum) -> rs.getString(1)
        );
    }

    private boolean isSafeIdentifier(String table) {
        return table != null && table.matches("^[a-zA-Z_][a-zA-Z0-9_]*$");
    }

    private static class ShiftSegmentRowMapper implements RowMapper<ShiftSegment> {
        @Override
        public ShiftSegment mapRow(ResultSet rs, int rowNum) throws SQLException {
            String shiftId = rs.getString(1);
            BigDecimal cal = rs.getBigDecimal(2);
            return new ShiftSegment(shiftId, cal);
        }
    }
}
