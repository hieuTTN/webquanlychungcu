package com.web.service;

import com.web.entity.VehicleFee;
import com.web.repository.VehicleFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleFeeService {

    @Autowired
    private VehicleFeeRepository vehicleFeeRepository;
}
