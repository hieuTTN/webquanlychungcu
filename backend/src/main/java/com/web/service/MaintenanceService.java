package com.web.service;

import com.web.entity.Apartment;
import com.web.entity.Maintenance;
import com.web.exception.MessageException;
import com.web.repository.ApartmentRepository;
import com.web.repository.MaintenanceRepository;
import com.web.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private UserUtils userUtils;

    public Maintenance save(Maintenance maintenance){
        if(maintenance.getId() == null){
            maintenance.setCreatedBy(userUtils.getUserWithAuthority());
            maintenance.setCreatedDate(LocalDateTime.now());
        }
        else{
            Maintenance ex = maintenanceRepository.findById(maintenance.getId()).get();
            maintenance.setCreatedDate(ex.getCreatedDate());
            maintenance.setCreatedBy(ex.getCreatedBy());
        }
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
