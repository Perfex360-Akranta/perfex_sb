package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.OtherLossEntryDto;
import com.akranta.perfex_sb.dto.OtherLossUploadRequest;
import com.akranta.perfex_sb.repository.OtherLossRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.OtherLossService;
import com.akranta.perfex_sb.util.FlexibleDateParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OtherLossServiceImpl implements OtherLossService {

    private static final String SEQ_IDENTIFIER = "PCS_TL_OTHERLOSSENTRY";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "OLS";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private final OtherLossRepository repository;
    private final DbActionTemplate dbActionTemplate;

    public OtherLossServiceImpl(OtherLossRepository repository, DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public Map<String, Object> saveOrUpdate(OtherLossEntryDto dto) {
        if (!isValidKey(dto.getOlseKeyid())) {
            dto.setOlseKeyid(generateKey());
            int cnt = repository.insert(applyDefaults(dto));
            if (cnt <= 0) throw new IllegalStateException("No rows inserted");
        } else {
            int cnt = repository.update(applyDefaults(dto));
            if (cnt <= 0) throw new IllegalStateException("No rows updated");
        }
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("msg", "Saved successfully");
        resp.put("keyId", dto.getOlseKeyid());
        return resp;
    }

    @Override
    @Transactional
    public void updateLossValues(List<OtherLossEntryDto> items) {
        if (items == null) return;
        for (OtherLossEntryDto d : items) {
            if (isValidKey(d.getOlseKeyid()) && d.getOlseLossvalue() != null) {
                repository.updateLossValue(d.getOlseKeyid(), d.getOlseLossvalue());
            }
        }
    }

    @Override
    @Transactional
    public void delete(String keyId) {
        if (!isValidKey(keyId)) throw new IllegalArgumentException("keyId is required");
        repository.delete(keyId);
    }

    @Override
    @Transactional
    public Map<String, Object> uploadExcel(OtherLossUploadRequest req) {
        if (req.getFile() == null || req.getFile().isEmpty()) {
            throw new IllegalArgumentException("file is required");
        }
        List<OtherLossEntryDto> toInsert = parseExcel(req.getFile(), req);
        for (OtherLossEntryDto dto : toInsert) {
            repository.insert(dto);
        }
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("msg", "Data Uploaded successfully");
        resp.put("rowsInserted", toInsert.size());
        return resp;
    }

    private List<OtherLossEntryDto> parseExcel(MultipartFile file, OtherLossUploadRequest req) {
        List<OtherLossEntryDto> list = new ArrayList<>();
        try (InputStream is = file.getInputStream(); Workbook wb = WorkbookFactory.create(is)) {
            Sheet sheet = wb.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum() + 1; // skip header
            int lastRow = sheet.getLastRowNum();
            for (int r = firstRow; r <= lastRow; r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Cell dateCell = row.getCell(0);
                Cell valueCell = row.getCell(1);
                Object dateVal = getCellValue(dateCell);
                Object lossVal = getCellValue(valueCell);
                LocalDateTime lossDate = FlexibleDateParser.parseCellValue(dateVal);

// Vignesh 16-May-2026
                // Excel numeric date is reduced by 1 day in FlexibleDateParser.
                // So adjust only Excel numeric date cells here.
                // Do not change FlexibleDateParser because it is used in other places.
                if (lossDate != null
                        && dateCell != null
                        && dateCell.getCellType() == CellType.NUMERIC
                        && DateUtil.isCellDateFormatted(dateCell)) {
                    lossDate = lossDate.plusDays(1);
                }

                BigDecimal lossValue = null;
                if (lossVal != null) {
                    try {
                        lossValue = new BigDecimal(lossVal.toString().trim());
                    } catch (NumberFormatException ignore) {
                    }
                }
                if (lossDate == null || lossValue == null) continue; // skip invalid rows
                OtherLossEntryDto dto = new OtherLossEntryDto();
                dto.setOlseKeyid(generateKey());
                dto.setOlseFlid(req.getFlid());
                dto.setOlseElementid(req.getElementId());
                dto.setOlseDate(LocalDateTime.now());
                dto.setOlseLossid(req.getLossId());
                dto.setOlseLossdate(lossDate);
                dto.setOlseLossvalue(lossValue);
                dto.setOlseTempfield1("-");
                dto.setOlseTempfield2("-");
                dto.setOlseTempfield3("-");
                dto.setOlseTempfield4("-");
                dto.setOlseTempfield5("-");
                dto.setOlseTempfield6("-");
                dto.setOlseTempfield7("-");
                dto.setOlseTempfield8("-");
                dto.setOlseActive("Y");
                dto.setOlseCreatedby(req.getCreatedBy());
                dto.setOlseCreatedon(LocalDateTime.now());
                dto.setOlseModifiedon(LocalDateTime.now());
                list.add(dto);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse Excel: " + e.getMessage(), e);
        }
        return list;
    }

    private Object getCellValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC: return cell.getNumericCellValue();
            case BOOLEAN: return cell.getBooleanCellValue();
            default: return null;
        }
    }

    private OtherLossEntryDto applyDefaults(OtherLossEntryDto d) {
        LocalDateTime now = LocalDateTime.now();
        if (d.getOlseActive() == null) d.setOlseActive("Y");
        if (d.getOlseTempfield1() == null) d.setOlseTempfield1("-");
        if (d.getOlseTempfield2() == null) d.setOlseTempfield2("-");
        if (d.getOlseTempfield3() == null) d.setOlseTempfield3("-");
        if (d.getOlseTempfield4() == null) d.setOlseTempfield4("-");
        if (d.getOlseTempfield5() == null) d.setOlseTempfield5("-");
        if (d.getOlseTempfield6() == null) d.setOlseTempfield6("-");
        if (d.getOlseTempfield7() == null) d.setOlseTempfield7("-");
        if (d.getOlseTempfield8() == null) d.setOlseTempfield8("-");
        if (d.getOlseCreatedon() == null) d.setOlseCreatedon(now);
        if (d.getOlseModifiedon() == null) d.setOlseModifiedon(now);
        if (d.getOlseDate() == null) d.setOlseDate(now);
        return d;
    }

    private boolean isValidKey(String key) {
        return key != null && !key.trim().isEmpty() && !"{}".equals(key) && !"null".equalsIgnoreCase(key);
    }

    private String generateKey() {
        try {
            return dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate sequence: " + e.getMessage(), e);
        }
    }
}
