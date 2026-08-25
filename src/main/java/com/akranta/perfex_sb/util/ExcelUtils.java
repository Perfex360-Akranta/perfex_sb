package com.akranta.perfex_sb.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.akranta.perfex_sb.dto.ExcelExportRequest;
import com.akranta.perfex_sb.dto.MetaConfigDto;
import com.akranta.perfex_sb.repository.DbFunctionTempleteRepository;

@Component
public class ExcelUtils {

    @Autowired
    private DbFunctionTempleteRepository fnRepository;

    private static final String REPORT_FORMAT_EXL_2007 = "xlsx";

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private static final Set<String> EXCLUDED_FIELDS = Set.of("rn", "dataorder", "slno");

    public void exportToResponse(ExcelExportRequest req,
            OutputStream outputStream) throws Exception {

        logger.info("Condition Param************ {}", req.getVconditionparam());
        logger.info("Common Param ************ {}", req.getVcommonparam());
        //req.setFunctionName("ABN_FN_ABNJHREPORT");
        logger.info(" Function Name ************ {}", req.getFunctionName());
        // logger.info(" ColModel Name ************ {}", req.getColModel());
        logger.info(" Title Name ************{}", req.getTitle());
        logger.info(" Format Name ************{}", req.getFormat());
        logger.info(" File Name ************{}", req.getFileName());

        logger.info("Meta Config = {}", req.getMetaConfig());

if (req.getMetaConfig() != null) {
    logger.info("Model Row Index = {}",
            req.getMetaConfig().getModelRowIndex());

    logger.info("Parent Header Row Index = {}",
            req.getMetaConfig().getParentHeaderRowIndex());

    logger.info("Child Header Row Index = {}",
            req.getMetaConfig().getChildHeaderRowIndex());

    logger.info("Order Row Index = {}",
            req.getMetaConfig().getOrderRowIndex());

    logger.info("Data Start Index = {}",
            req.getMetaConfig().getDataStartIndex());
}
        // req.setTitle(req.getFileName());
        // 1. Build params for callFunction
        Map<String, Object> params = new HashMap<>();
        params.put("vconditionparam", req.getVconditionparam());
        params.put("vcommonparam", req.getVcommonparam());

        logger.info("Request title  = {}", req.getTitle());
        logger.info("Request format = {}", req.getFormat());
        logger.info("Request file   = {}", req.getFileName());

        // 2. Call DB function — returns totalcnt + List<Map<String,Object>>
        Map<String, Object> dbResult = fnRepository.callFunction(req.getFunctionName(), params);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> rows = (List<Map<String, Object>>) dbResult.get("cur");

        // logger.info("data rows {}", rows);

        // List<Map<String, Object>> dataRows = rows.stream()
        // .filter(row -> "4".equals(String.valueOf(row.get("dataorder"))))
        // .collect(java.util.stream.Collectors.toList());

        // logger.info("data rows {}", dataRows);
        // 3. Parse colModel JSON
        // JSONObject colModelJson = JSONObject.fromObject(req.getColModel());
        // colModelJson.put("title", req.getTitle());

        // 4. Build workbook
        Workbook wb = buildWorkbook(rows, req.getFormat(), req.getTitle(),req.getMetaConfig());

        // 5. Write to response stream
        wb.write(outputStream);
        if (wb instanceof SXSSFWorkbook) {
            ((SXSSFWorkbook) wb).dispose();
        }
    }

    // -----------------------------------------------------------------------
    // Core workbook builder — generic, reusable for any module
    // -----------------------------------------------------------------------
    private Workbook buildWorkbook(List<Map<String, Object>> rows,
            String format, String title ,MetaConfigDto configDto) throws Exception {

        // logger.info("Rows: {}", rows);

        boolean isXlsx = REPORT_FORMAT_EXL_2007.equalsIgnoreCase(format);
        String extension = isXlsx ? "xlsx" : "xls";
        

        Workbook wb;

        if (isXlsx) {
            wb = new SXSSFWorkbook(200);
        } else {
            wb = new HSSFWorkbook();
        }
        // inp.close();

        Sheet sheet = wb.createSheet("Report"); // ← creates the sheet
        if (sheet instanceof SXSSFSheet sxssfSheet) {
    sxssfSheet.trackAllColumnsForAutoSizing();
}
        sheet.setDisplayGridlines(false);

    // 3. CREATE REUSABLE STYLES HERE
    CellStyle parentHeaderStyle =
            createParentHeaderStyle(wb);        
        // Parse column definitions
        // JSONArray colModelArr = colModelJson.getJSONArray("colModel");

        // Write header row
        // String title = colModelJson.optString("title", "Report");

        // Write column headers + collect visible columns
        List<String> visibleCols = new ArrayList<>();
        //Row headerRow = sheet.createRow(2); // row 0=title, 1=blank, 2=headers
        Row parentHeaderExcelRow = sheet.createRow(2);
        Row childHeaderExcelRow = sheet.createRow(3);
        Cell snHeader = childHeaderExcelRow.createCell(0);
        snHeader.setCellValue("Sl.No");
                snHeader.setCellStyle(getHeaderStyle(wb));
        int headerColIdx = 1; // col 0 = S.No
        // Cell snHeader = headerRow.createCell(0);
        // snHeader.setCellValue("S.No");
        // snHeader.setCellStyle(getHeaderStyle(wb));

        // for (int i = 0; i < colModelArr.size(); i++) {
        // JSONObject col = colModelArr.getJSONObject(i);
        // if (col.optBoolean("hidden", false))
        // continue;
        // Cell hCell = headerRow.createCell(headerColIdx++);
        // hCell.setCellValue(col.optString("label", col.optString("name", "")));
        // hCell.setCellStyle(getHeaderStyle(wb));
        // visibleCols.add(col);
        // }
        // Map<String, Object> metaRow = rows.get(0);

        // Map<String, Object> header = rows.get(1);

        // Map<String, Object> orderRow = rows.get(2);
        for (int i = 0; i < rows.size(); i++) {

    Map<String, Object> currentRow = rows.get(i);

    logger.info(
            "ROW INDEX = {}, DATAORDER = {}, CODE = {}",
            i,
            currentRow.get("dataorder"),
            currentRow.get("code")
    );
}

        Map<String, Object> metaRow = rows.get(configDto.getModelRowIndex());

        Map<String, Object> parentHeaderRow = rows.get(configDto.getParentHeaderRowIndex());

        Map<String, Object> orderRow = rows.get(configDto.getOrderRowIndex());

        Map<String, Object> childHeaderRow = null;

        if (configDto.getChildHeaderRowIndex() != null) 
            {
                childHeaderRow =rows.get(configDto.getChildHeaderRowIndex());
            }

        // logger.info("Header*******: {}", header);

        // logger.info("orderRow*******: {}", orderRow);

        logger.info("metaRow = {}", metaRow);
        logger.info("parentHeaderRow = {}", parentHeaderRow);
        logger.info("childHeaderRow = {}", childHeaderRow);
        logger.info("orderRow = {}", orderRow);

        //logger.info("metaRow = {}", rows.get(0));
        //logger.info("header = {}", rows.get(1));
        //logger.info("order = {}", rows.get(2));

        List<Map.Entry<String, Object>> orderedColumns = new ArrayList<>(orderRow.entrySet());

        orderedColumns.sort((e1, e2) -> Integer.compare(
                Integer.parseInt(e1.getValue().toString()),
                Integer.parseInt(e2.getValue().toString())));

        for (Map.Entry<String, Object> entry : orderedColumns) {

            // JSONObject col = colModelArr.getJSONObject(i);

            String fieldName = entry.getKey();
            if (EXCLUDED_FIELDS.contains(fieldName.toLowerCase())) {
                continue;
            }
            // Object headerVal = header.get(fieldName);
            // String headerText = headerVal == null ? null : headerVal.toString();
            // if (headerText == null) {
            //     continue;
            // }
            Object parentHeaderVal = parentHeaderRow.get(fieldName);
            String parentHeaderText = parentHeaderVal == null ? "": parentHeaderVal.toString().trim();


            String childHeaderText = "";

            if (childHeaderRow != null) {

                Object childHeaderVal = childHeaderRow.get(fieldName);

                childHeaderText =  childHeaderVal == null ? "" : childHeaderVal.toString().trim();
            }


            String headerText =!childHeaderText.isEmpty()? childHeaderText: !parentHeaderText.isEmpty()? parentHeaderText: fieldName;
            // Cell hCell = headerRow.createCell(headerColIdx);
            // hCell.setCellValue(headerText);
            // hCell.setCellStyle(getHeaderStyle(wb));
            Cell parentCell = parentHeaderExcelRow.createCell(headerColIdx);
            parentCell.setCellValue(parentHeaderText);
           // parentCell.setCellStyle(getHeaderStyle(wb));
           // REUSE THE STYLE
        parentCell.setCellStyle(parentHeaderStyle);

            Cell childCell = childHeaderExcelRow.createCell(headerColIdx);
            childCell.setCellValue(
                    !childHeaderText.isEmpty()
                    ? childHeaderText
                    : parentHeaderText
                );
            childCell.setCellStyle(getHeaderStyle(wb));


            Object meta = metaRow.get(fieldName);
            if (meta == null)
                meta = metaRow.get(fieldName.toLowerCase());
            if (meta == null)
                meta = metaRow.get(fieldName.toUpperCase());

            if (meta != null) {
                int width = extractColumnWidth(meta.toString());
                sheet.setColumnWidth(headerColIdx, width * 40);
            }
            if (meta != null && isHidden(meta.toString())) {
                sheet.setColumnHidden(headerColIdx, true);
            }
            String align = "left";

            if (meta != null) {
                align = extractAlignment(meta.toString());
            }

            visibleCols.add(fieldName);
            headerColIdx++;
        }
        if (childHeaderRow != null) {

    int startCol = 1;

    while (startCol < headerColIdx) {

        Cell startCell = parentHeaderExcelRow.getCell(startCol);

        String currentParent =
                startCell == null
                        ? ""
                        : startCell.getStringCellValue();


        int endCol = startCol;


        while (endCol + 1 < headerColIdx) {

            Cell nextCell =
                    parentHeaderExcelRow.getCell(endCol + 1);

            String nextParent =
                    nextCell == null
                            ? ""
                            : nextCell.getStringCellValue();


            if (!currentParent.equals(nextParent)) {
                break;
            }

            endCol++;
        }


        if (endCol > startCol) {

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            2,
                            2,
                            startCol,
                            endCol
                    )
            );
        }


        startCol = endCol + 1;
    }
}
        writeTitle(wb, sheet, title, visibleCols.size() + 1);
        // Write data rows
        CellStyle leftStyle = getDataStyle(wb, HorizontalAlignment.LEFT);
        CellStyle rightStyle = getDataStyle(wb, HorizontalAlignment.RIGHT);
        CellStyle centerStyle = getDataStyle(wb, HorizontalAlignment.CENTER);

        int rowIdx = configDto.getDataStartIndex(); // data starts at row 3
        int slNo = 1;

        //List<Map<String, Object>> dataRowsResult = rows.subList(3, rows.size());
        List<Map<String, Object>> dataRowsResult =
        rows.subList(
                configDto.getDataStartIndex(),
                rows.size()
        );

        logger.info("Total rows = {}", rows.size());
        logger.info("Filtered data rows = {}", dataRowsResult.size());
        for (Map<String, Object> dataRow : dataRowsResult) {
            Row row = sheet.createRow(rowIdx++);

            // S.No cell
            Cell snCell = row.createCell(0);
            snCell.setCellValue(slNo++);
            snCell.setCellStyle(rightStyle);

            // Data cells
            int colIdx = 1;
            for (String fieldName : visibleCols) {

                Object val = dataRow.get(fieldName);
                if (val == null)
                    val = dataRow.get(fieldName.toLowerCase());
                if (val == null)
                    val = dataRow.get(fieldName.toUpperCase());

                String cellValue = val == null ? "" : val.toString();

                // Get alignment for this column
                Object meta = metaRow.get(fieldName);

                if (meta == null)
                    meta = metaRow.get(fieldName.toLowerCase());

                if (meta == null)
                    meta = metaRow.get(fieldName.toUpperCase());

                String align = "left";

                if (meta != null) {
                    align = extractAlignment(meta.toString());
                }

                Cell cell = row.createCell(colIdx++);

                boolean isNumeric = cellValue.matches("^-?\\d+(\\.\\d+)?$");

                if (isNumeric && !cellValue.isEmpty()) {
                    cell.setCellValue(Double.parseDouble(cellValue));
                    cell.setCellStyle(rightStyle);
                } else {
                    cell.setCellValue(cellValue);
                    cell.setCellStyle(
                            "right".equals(align) ? rightStyle
                                    : "center".equals(align) ? centerStyle
                                            : leftStyle);
                }
            }
        }

        // Auto-size columns
        // for (int i = 0; i <= visibleCols.size(); i++) {
        // sheet.autoSizeColumn(i);
        // }
for (int colIndex = 0; colIndex < headerColIdx; colIndex++) {
    sheet.autoSizeColumn(colIndex);
}
        return wb;
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------
    // private void writeTitle(Workbook wb, Sheet sheet, String title) {
    // Row titleRow = sheet.createRow(0);
    // Cell titleCell = titleRow.createCell(0);
    // titleCell.setCellValue(title);

    // CellStyle titleStyle = wb.createCellStyle();
    // Font font = wb.createFont();
    // font.setBold(true);
    // font.setFontHeightInPoints((short) 14);
    // titleStyle.setFont(font);
    // titleStyle.setAlignment(HorizontalAlignment.CENTER);
    // titleCell.setCellStyle(titleStyle);
    // }

    private void writeTitle(Workbook wb, Sheet sheet, String title, int totalColumns) {

        logger.info("Title for Report ***************** {}", title);
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(30); // Increase row height

        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);

        // Merge title across all columns
        sheet.addMergedRegion(new CellRangeAddress(
                0, // First row
                0, // Last row
                0, // First column
                totalColumns - 1 // Last column
        ));

        CellStyle titleStyle = wb.createCellStyle();

        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16); // Bigger font
        titleStyle.setFont(font);

        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        titleCell.setCellStyle(titleStyle);
    }

    private CellStyle getHeaderStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle getDataStyle(Workbook wb, HorizontalAlignment align) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(align);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        return style;
    }

    private int extractColumnWidth(String meta) {
        if (meta == null || meta.isEmpty()) {
            return 120; // default width
        }

        String[] parts = meta.split("#");

        for (String part : parts) {
            if (part.startsWith("WI=")) {
                try {
                    return Integer.parseInt(part.substring(3));
                } catch (NumberFormatException e) {
                    return 120;
                }
            }
        }

        return 120;
    }

    private boolean isHidden(String meta) {

        if (meta == null || meta.isEmpty()) {
            return false;
        }

        String[] parts = meta.split("#");

        for (String part : parts) {
            if ("HD=T".equalsIgnoreCase(part)) {
                return true;
            }
        }

        return false;
    }

    private String extractAlignment(String meta) {

        if (meta == null) {
            return "left";
        }

        for (String part : meta.split("#")) {
            if (part.startsWith("AL=")) {
                String align = part.substring(3);

                if ("L".equalsIgnoreCase(align))
                    return "left";
                if ("C".equalsIgnoreCase(align))
                    return "center";
                if ("R".equalsIgnoreCase(align))
                    return "right";
            }
        }

        return "left";
    }

    private CellStyle createParentHeaderStyle(Workbook wb) {

    CellStyle style = wb.createCellStyle();

    style.setFillForegroundColor(
            IndexedColors.LIGHT_YELLOW.getIndex()
    );

    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    Font font = wb.createFont();
    font.setBold(true);

    style.setFont(font);

    style.setAlignment(HorizontalAlignment.CENTER);
    style.setVerticalAlignment(VerticalAlignment.CENTER);

    style.setBorderTop(BorderStyle.THIN);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);

    return style;
}
}