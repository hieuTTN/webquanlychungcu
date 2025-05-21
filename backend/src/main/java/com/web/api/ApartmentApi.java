package com.web.api;

import com.web.entity.Apartment;
import com.web.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartment")
@CrossOrigin
public class ApartmentApi {

    @Autowired
    private ApartmentService apartmentService;


    @PostMapping("/admin/create")
    public ResponseEntity<?> save(@RequestBody Apartment apartment){
        Apartment result = apartmentService.save(apartment);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        apartmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/findAll")
    public ResponseEntity<?> findAll(@RequestParam(required = false) Integer floor,Pageable pageable){
        Page<Apartment> result = apartmentService.findAllPage(floor,pageable);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/admin/allTang")
    public ResponseEntity<?> allTang(){
        List<Integer> result = apartmentService.allTang();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/all/findById")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        Apartment result = apartmentService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/user/my")
    public ResponseEntity<?> myApp(){
        Apartment result = apartmentService.myApp();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
