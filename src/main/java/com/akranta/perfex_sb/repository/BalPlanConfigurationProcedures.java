package com.akranta.perfex_sb.repository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository 
public class BalPlanConfigurationProcedures {

    @PersistenceContext
        private EntityManager entityManager;

     public void generateCalendar(
                        String year,
                        String factoryId,
                        String sectionId,
                        String cellId,
                        String machineId,
                        String assemblyId,
                        String frequency,
                        String fromMonth) {

                Query query = entityManager.createNativeQuery(
                                "CALL PLM_PR_ANNUALCALGEN_NOOUT(" +
                                                ":year, :factoryId, :sectionId, :cellId, :machineId, " +
                                                ":assemblyId, :frequency, :type, :param1, :param2, :fromMonth)");

                query.setParameter("year", year);
                query.setParameter("factoryId", factoryId);
                query.setParameter("sectionId", sectionId);
                query.setParameter("cellId", cellId);
                query.setParameter("machineId", machineId);
                query.setParameter("assemblyId", assemblyId);
                query.setParameter("frequency", frequency);
                query.setParameter("type", "MCH");
                query.setParameter("param1", "{}");
                query.setParameter("param2", "{}");
                query.setParameter("fromMonth", fromMonth);

                query.executeUpdate();
        }
    
}