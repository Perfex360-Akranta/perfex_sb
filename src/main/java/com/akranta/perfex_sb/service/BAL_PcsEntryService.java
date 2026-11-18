package com.akranta.perfex_sb.service;

import java.util.List;

import com.akranta.perfex_sb.dto.BalPcsEntrySaveDto;
import com.akranta.perfex_sb.model.BAL_PcsEntryProductiondata;

public interface BAL_PcsEntryService {

        BAL_PcsEntryProductiondata createNew(BalPcsEntrySaveDto request);

        List<String[]> getLastShiftProduct(String factId, String cellId, String entryDate);

        List<String[]> getShiftStarEndTime(String shiftId);

        List<String[]> getHolidayDates(String factId, String entryDate);

        List<String[]> getAllTimeforHour(String cellId, String shiftId, String entryDate,
                        String hourId, String shiftWise);

        List<String[]> getCycletime(String productId);

        public int getShiftTotaltime(String startdate, String enddate, String shiftId);

}
