package com.exam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.model.ScanData;

public interface IScanDataDao extends JpaRepository<ScanData, Integer> {

}