package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.akranta.perfex_sb.model.Qtmtlcapa;

@Repository
public interface QtmtlcapaRepository extends JpaRepository<Qtmtlcapa, String> {
}
