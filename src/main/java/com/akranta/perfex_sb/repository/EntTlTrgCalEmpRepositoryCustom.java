package com.akranta.perfex_sb.repository;

import java.util.List;

public interface EntTlTrgCalEmpRepositoryCustom {

    int countUniqueEmployeePopup(String etcmKeyid,
                                 String refdocid,
                                 String flid,
                                 String factoryId,
                                 boolean uniquePos,
                                 String empType,
                                 String empGender,
                                 String roleLevel,
                                 String filterCond);

    List<Object[]> findUniqueEmployeePopup(String etcmKeyid,
                                           String refdocid,
                                           String flid,
                                           String factoryId,
                                           boolean uniquePos,
                                           String empType,
                                           String empGender,
                                           String roleLevel,
                                           String filterCond,
                                           Integer fromRow,
                                           Integer toRow);
}
