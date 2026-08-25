package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.DcmTlDocumentManager;

@Repository
public interface DcmTlDocumentManagerRepository extends JpaRepository<DcmTlDocumentManager,String>
{




}
