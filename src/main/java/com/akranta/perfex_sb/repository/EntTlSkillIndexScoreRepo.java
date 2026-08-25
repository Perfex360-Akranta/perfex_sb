package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class EntTlSkillIndexScoreRepo {

        private static final Logger logger = LoggerFactory.getLogger(EntTlSkillIndexScoreRepo.class);

        @PersistenceContext
        private EntityManager entityManager;

        public void callSkillScoreCountProcedure(
                        String siamKeyid,
                        String flid,
                        LocalDateTime reviewDate,
                        // String uniqueposid,
                        String createdBy) {

                Query query = entityManager.createNativeQuery(
                                "CALL skilindex_creation_insert(:siamKeyid,:reviewDate, :flid, :createdBy)");
                query.setParameter("siamKeyid", siamKeyid);
                query.setParameter("flid", flid);
                query.setParameter("reviewDate", reviewDate);
                // query.setParameter("uniqueposid", uniqueposid);
                query.setParameter("createdBy", createdBy);
                query.executeUpdate();
        }

        public void callMultipleSkillScoreCountProcedure(
                        String siamKeyid,
                        String flid,
                        LocalDateTime reviewDate,
                        // String uniqueposid,
                        String createdBy) {

                Query query = entityManager.createNativeQuery(
                                "CALL sp_multiple_skill_idx_insert(:siamKeyid,:flid,:reviewDate,:createdBy)");
                query.setParameter("siamKeyid", siamKeyid);
                query.setParameter("flid", flid);
                query.setParameter("reviewDate", reviewDate);
                // query.setParameter("uniqueposid", uniqueposid);
                query.setParameter("createdBy", createdBy);
                query.executeUpdate();
        }
}