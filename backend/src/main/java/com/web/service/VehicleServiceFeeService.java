package com.web.service;

import com.web.entity.VehicleServiceFee;
import com.web.repository.VehicleServiceFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceFeeService {

    @Autowired
    private VehicleServiceFeeRepository vehicleServiceFeeRepository;

    public List<VehicleServiceFee> findAll(){
        return vehicleServiceFeeRepository.findAll();
    }

    public VehicleServiceFee update(VehicleServiceFee vehicleServiceFee) {
        return vehicleServiceFeeRepository.save(vehicleServiceFee);
    }
}
