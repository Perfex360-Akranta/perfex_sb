package com.akranta.perfex_sb.repository;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akranta.perfex_sb.model.GenTlMomattendance;

public interface MoMeetingAttendanceRepository extends JpaRepository<GenTlMomattendance, String> {
    // List<GenTlMomattendance> findByMoms_keyid(String moms_keyid);

}
