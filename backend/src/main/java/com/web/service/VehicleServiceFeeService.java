package com.web.service;

import com.web.entity.VehicleServiceFee;
import com.web.repository.VehicleServiceFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceFeeService {

    @Autowired
    private VehicleServiceFeeRepository vehicleServiceFeeRepository;

}
