package com.web.api;

import com.web.entity.VehicleServiceFee;
import com.web.service.VehicleServiceFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/VehicleServiceFee")
@CrossOrigin
public class VehicleServiceFeeApi {

    @Autowired
    private VehicleServiceFeeService vehicleServiceFeeService;
}
