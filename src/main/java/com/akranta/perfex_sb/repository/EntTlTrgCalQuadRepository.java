package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.akranta.perfex_sb.model.EntTlTrgCalQuad;

@Repository
public interface EntTlTrgCalQuadRepository extends JpaRepository<EntTlTrgCalQuad, String> {
    
    // @Query(value = "SELECT etcq_currentlevel FROM ent_tl_trgcalquad WHERE etcq_keyid IN (:keyIds) LIMIT 1", 
    //        nativeQuery = true)
    // Integer getCurrentLevel(@Param("keyIds") String keyIds);

      @Query(value = "SELECT etcq_currentlevel FROM ent_tl_trgcalquad WHERE etcq_keyid IN :keyIds LIMIT 1", 
           nativeQuery = true)
    Integer getCurrentLevel(@Param("keyIds") List<String> keyIds);
    
    @Modifying
    @Query(value = "UPDATE ent_tl_trgcalquad SET " +
                   "etcq_currentlevel = 3, " +
                   "etcq_l3pass = 'P', " +
                   "etcq_l3date = CURRENT_TIMESTAMP, " +
                   "etcq_currentleveldate = CURRENT_TIMESTAMP, " +
                   "etcq_l3_updby = :userid " +
                   "WHERE etcq_keyid IN (:keyIds)", 
           nativeQuery = true)
    //int updateToLevel3(@Param("keyIds") String keyIds, @Param("userid") String userid);
    int updateToLevel3(@Param("keyIds") List<String> keyIds, @Param("userid") String userid);
    
    @Modifying
    @Query(value = "UPDATE ent_tl_trgcalquad SET " +
                   "etcq_currentlevel = 4, " +
                   "etcq_l4pass = 'P', " +
                   "etcq_l4date = CURRENT_TIMESTAMP, " +
                   "etcq_currentleveldate = CURRENT_TIMESTAMP, " +
                   "etcq_l4_updby = :userid " +
                   "WHERE etcq_keyid IN (:keyIds)", 
           nativeQuery = true)
    int updateToLevel4(@Param("keyIds") List<String> keyIds, @Param("userid") String userid);


    //vignesh

    @Query("""
            select q from EntTlTrgCalQuad q
             where q.empm_keyid   = :empKeyid
               and q.empm_topicid = :topicId
               and q.l1trgcalid   = :trgCalId
            """)
    Optional<EntTlTrgCalQuad> findFirst(@Param("empKeyid") String empKeyid,
                                        @Param("topicId") String topicId,
                                        @Param("trgCalId") String trgCalId);

}