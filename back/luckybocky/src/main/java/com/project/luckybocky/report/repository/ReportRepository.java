package com.project.luckybocky.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.luckybocky.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
