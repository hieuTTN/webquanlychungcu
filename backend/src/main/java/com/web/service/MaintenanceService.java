package com.web.service;

import com.web.entity.Apartment;
import com.web.entity.Maintenance;
import com.web.exception.MessageException;
import com.web.repository.ApartmentRepository;
import com.web.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    public Maintenance save(Maintenance maintenance){
        maintenanceRepository.save(maintenance);
        return maintenance;
    }

    public Page<Maintenance> findAllPage(Pageable pageable){
        return maintenanceRepository.findAll(pageable);
    }

    public void delete(Long id){
        maintenanceRepository.deleteById(id);
    }

    public Maintenance findById(Long id){
        return maintenanceRepository.findById(id).orElse(null);
    }
}
