package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlOtgRouter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenTlOtgRouterRepository
        extends JpaRepository<GenTlOtgRouter, String> {

    boolean existsByOtgcKeyid(String otgcKeyid);
}