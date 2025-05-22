package com.web.api;

import com.web.entity.Vehicle;
import com.web.entity.VehicleServiceFee;
import com.web.service.VehicleServiceFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/VehicleServiceFee")
@CrossOrigin
public class VehicleServiceFeeApi {

    @Autowired
    private VehicleServiceFeeService vehicleServiceFeeService;

    @GetMapping("/all/findAll")
    public ResponseEntity<?> findAll(){
        List<VehicleServiceFee> result = vehicleServiceFeeService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
