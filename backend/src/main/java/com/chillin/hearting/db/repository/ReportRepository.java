package com.chillin.hearting.db.repository;

import com.chillin.hearting.db.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
