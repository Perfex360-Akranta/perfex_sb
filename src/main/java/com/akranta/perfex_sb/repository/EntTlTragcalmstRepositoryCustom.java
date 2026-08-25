package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

public interface EntTlTragcalmstRepositoryCustom {

    int countGridCalendar(String etcmKeyid, String filterCond);

    List<Object[]> findGridCalendar(String etcmKeyid, String filterCond, Integer fromRow, Integer toRow);

    int countGridCalendarModify(String condSql, Map<String,Object> params);
    List<Object[]> findGridCalendarModify(String condSql, Map<String,Object> params, Integer fromRow, Integer pageSize);

    int countGridCalendarView(String condSql, Map<String,Object> params);
    List<Object[]> findGridCalendarView(String condSql, Map<String,Object> params, Integer fromRow, Integer pageSize);

}
