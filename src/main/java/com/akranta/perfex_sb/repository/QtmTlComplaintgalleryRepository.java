package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.QtmTlComplaintgallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QtmTlComplaintgalleryRepository extends JpaRepository<QtmTlComplaintgallery, String> {
    
    @Query(value = "SELECT * FROM qtm_tl_complaintgallery WHERE cmga_keyid = :keyid", 
           nativeQuery = true)
    QtmTlComplaintgallery findByKeyid(@Param("keyid") String keyid);
     @Modifying
    @Query(value = "DELETE FROM qtm_tl_complaintgallery WHERE cmga_keyid = :keyid", 
           nativeQuery = true)
    int deleteComplaintGallery(@Param("keyid") String keyid);
}