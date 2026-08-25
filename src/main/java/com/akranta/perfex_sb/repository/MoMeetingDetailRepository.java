package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akranta.perfex_sb.model.GenTlMomdtl;

public interface MoMeetingDetailRepository extends JpaRepository<GenTlMomdtl, String> {
    List<GenTlMomdtl> findByMomskeyid(String momskeyid);

}
