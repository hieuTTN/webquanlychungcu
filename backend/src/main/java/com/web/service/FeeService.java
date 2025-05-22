package com.web.service;

import com.web.dto.request.FeeUpdate;
import com.web.dto.response.Fee;
import com.web.entity.*;
import com.web.repository.*;
import com.web.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FeeService {

    @Autowired
    private VehicleFeeRepository vehicleFeeRepository;

    @Autowired
    private VehicleServiceFeeRepository vehicleServiceFeeRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ServiceFeeRepository serviceFeeRepository;

    @Autowired
    private UtilityBillRepository utilityBillRepository;

    @Autowired
    private MailService mailService;

    public List<Fee> getFeeByTime(Integer month, Integer year){
        List<Fee> list = new ArrayList<>();
        List<Apartment> apartments = apartmentRepository.canHoDaBan();
        for (Apartment a : apartments){
            ServiceFee serviceFeeEx = serviceFeeRepository.findByThangNamAndCanHo(month, year, a.getId());
            UtilityBill utilityBillEx = utilityBillRepository.findByThangNamAndCanHo(month, year, a.getId());
            VehicleFee vehicleFeeEx = vehicleFeeRepository.findByThangNamAndCanHo(month, year, a.getId());
            if(serviceFeeEx != null && utilityBillEx != null && vehicleFeeEx != null){
                Fee fee = new Fee();
                fee.setMonth(month);
                fee.setYear(year);
                fee.setServiceFee(serviceFeeEx);
                fee.setApartment(a);
                fee.setVehicleFee(vehicleFeeEx);
                fee.setUtilityBill(utilityBillEx);
                list.add(fee);
            }
        }
        return list;
    }


    public void taoDongPhi(Integer month, Integer year) {
        List<Apartment> apartments = apartmentRepository.canHoDaBan();
        for (Apartment a : apartments){
            ServiceFee serviceFeeEx = serviceFeeRepository.findByThangNamAndCanHo(month, year, a.getId());
            UtilityBill utilityBillEx = utilityBillRepository.findByThangNamAndCanHo(month, year, a.getId());
            VehicleFee vehicleFeeEx = vehicleFeeRepository.findByThangNamAndCanHo(month, year, a.getId());
            if(serviceFeeEx == null) createServiceFee(a, month, year);
            if(vehicleFeeEx == null) createVehicleFee(a, month, year);
            if(utilityBillEx == null) createDNFee(a, month, year);
            if(serviceFeeEx != null && serviceFeeEx.getPaidStatus() == false){
                sendMailFee(a, serviceFeeEx.getFee(), month, year, 1);
            }
            if(vehicleFeeEx != null && vehicleFeeEx.getPaidStatus() == false){
                sendMailFee(a, vehicleFeeEx.getFee(), month, year, 2);
            }
            if(utilityBillEx != null && utilityBillEx.getPaidStatus() == false && utilityBillEx.getFee() != null){
                sendMailFee(a, utilityBillEx.getFee(), month, year, 3);
            }
        }
    }

    public void createServiceFee(Apartment a, Integer month, Integer year){
        ServiceFee serviceFee = new ServiceFee();
        serviceFee.setFee(a.getAcreage() * 13000D);
        serviceFee.setApartment(a);
        serviceFee.setCreatedDate(LocalDateTime.now());
        serviceFee.setMonth(month);
        serviceFee.setYear(year);
        serviceFee.setPaidStatus(false);
        serviceFeeRepository.save(serviceFee);
    }

    public void createVehicleFee(Apartment a, Integer month, Integer year){
        VehicleFee vehicleFee = new VehicleFee();
        Double fee = 0D;
        VehicleServiceFee phiOTo = vehicleServiceFeeRepository.findById(1L).get();
        VehicleServiceFee phiXeMay = vehicleServiceFeeRepository.findById(2L).get();
        VehicleServiceFee phiXeDap = vehicleServiceFeeRepository.findById(3L).get();
        for(Vehicle v : a.getVehicles()){
            if(v.getVehicleType() == 2){
                fee += phiOTo.getFee();
            }
            if(v.getVehicleType() == 1){
                fee += phiXeMay.getFee();
            }
            if(v.getVehicleType() == 0){
                fee += phiXeDap.getFee();
            }
        }
        vehicleFee.setFee(fee);
        vehicleFee.setApartment(a);
        vehicleFee.setCreatedDate(LocalDateTime.now());
        vehicleFee.setMonth(month);
        vehicleFee.setYear(year);
        vehicleFee.setPaidStatus(false);
        vehicleFeeRepository.save(vehicleFee);
    }

    public void createDNFee(Apartment a, Integer month, Integer year){
        Integer preMonth = month - 1;
        Integer preYear = year;
        if(preMonth == 0){
            preMonth = 12;
            preYear = year - 1;
        }
        UtilityBill ex = utilityBillRepository.findByThangNamAndCanHo(preMonth, preYear, a.getId());
        UtilityBill utilityBill = new UtilityBill();
        utilityBill.setApartment(a);
        utilityBill.setCreatedDate(LocalDateTime.now());
        utilityBill.setMonth(month);
        utilityBill.setYear(year);
        utilityBill.setPaidStatus(false);
        if(ex != null){
            utilityBill.setElectricityIndexPreMonth(ex.getElectricityIndex());
            utilityBill.setWaterIndexPreMonth(ex.getWaterIndex());
        }
        utilityBillRepository.save(utilityBill);
    }


    public void sendMailFee(Apartment a, Double fee, Integer month, Integer year, Integer loaiPhi){
        if(loaiPhi == 1){
            for (Resident r : a.getResidents()){
                mailService.sendEmail(r.getEmail(), "Thông báo đóng phí căn hộ "+a.getName(),
                        "Phí căn hộ tháng "+month+" năm "+year+" của bạn là "+ formatToVND(fee), false, true);
            }
        }
        if(loaiPhi == 2){
            for (Resident r : a.getResidents()){
                mailService.sendEmail(r.getEmail(), "Thông báo đóng phí gửi xe ",
                        "Phí gửi xe của căn hộ "+ a.getName()+" tháng "+month+" năm "+year+" của bạn là "+ formatToVND(fee), false, true);
            }
        }
        if(loaiPhi == 3){
            for (Resident r : a.getResidents()){
                mailService.sendEmail(r.getEmail(), "Thông báo đóng phí điện nước ",
                        "Phí điện nước của căn hộ "+ a.getName()+" tháng "+month+" năm "+year+" của bạn là "+ formatToVND(fee), false, true);
            }
        }
    }

    public Fee getByApartmentId(Long id, Integer month, Integer year){
        Apartment a = apartmentRepository.findById(id).get();
        ServiceFee serviceFeeEx = serviceFeeRepository.findByThangNamAndCanHo(month, year, a.getId());
        UtilityBill utilityBillEx = utilityBillRepository.findByThangNamAndCanHo(month, year, a.getId());
        VehicleFee vehicleFeeEx = vehicleFeeRepository.findByThangNamAndCanHo(month, year, a.getId());
        Fee fee = new Fee();
        if(serviceFeeEx != null && utilityBillEx != null && vehicleFeeEx != null){
            fee.setServiceFee(serviceFeeEx);
            fee.setMonth(month);
            fee.setYear(year);
            fee.setApartment(a);
            fee.setVehicleFee(vehicleFeeEx);
            fee.setUtilityBill(utilityBillEx);
        }
        return fee;
    }


    public void capNhatThongTin(FeeUpdate feeUpdate) {
        Apartment a = apartmentRepository.findById(feeUpdate.getId()).get();
        ServiceFee serviceFeeEx = serviceFeeRepository.findByThangNamAndCanHo(feeUpdate.getMonth(), feeUpdate.getYear(), a.getId());
        UtilityBill utilityBillEx = utilityBillRepository.findByThangNamAndCanHo(feeUpdate.getMonth(), feeUpdate.getYear(), a.getId());
        VehicleFee vehicleFeeEx = vehicleFeeRepository.findByThangNamAndCanHo(feeUpdate.getMonth(), feeUpdate.getYear(), a.getId());

        vehicleFeeEx.setPaidStatus(feeUpdate.getCheckPhiGuiXe());
        vehicleFeeRepository.save(vehicleFeeEx);

        serviceFeeEx.setPaidStatus(feeUpdate.getCheckPhiCanHo());
        serviceFeeRepository.save(serviceFeeEx);

        if(feeUpdate.getChiSoDien() != null && feeUpdate.getSoDien() != null){
            try {
                utilityBillEx.setElectricityIndex(feeUpdate.getChiSoDien());
                utilityBillEx.setNumElectricity(feeUpdate.getSoDien());
            }catch (Exception e) {}
        }
        if(feeUpdate.getChiSoNuoc() != null && feeUpdate.getSoNuoc() != null){
            try {
                utilityBillEx.setWaterIndex(feeUpdate.getChiSoNuoc());
                utilityBillEx.setNumWater(feeUpdate.getSoNuoc());
            }catch (Exception e) {}
        }
        saveHDDienNuoc(utilityBillEx);
    }

    public void saveHDDienNuoc(UtilityBill utilityBill){
        Double fee = 0D;
        if(utilityBill.getNumWater() != null){
            fee += calculateWaterBill(utilityBill.getNumWater().intValue());
        }
        if(utilityBill.getNumElectricity() != null){
            fee += calculateElectricityBill(utilityBill.getNumElectricity().intValue());
        }
        if(fee != 0D){
            utilityBill.setFee(fee);
        }
        utilityBillRepository.save(utilityBill);
    }

    public double calculateWaterBill(int numWater) {
        double totalCost = 0;

        // Giá nước theo từng bậc
        int tier1Limit = 10;
        int tier2Limit = 20;
        int tier3Limit = 30;

        double tier1Rate = 8500;
        double tier2Rate = 9900;
        double tier3Rate = 16000;
        double tier4Rate = 27000;

        if (numWater > tier3Limit) {
            totalCost += (numWater - tier3Limit) * tier4Rate;
            numWater = tier3Limit;
        }
        if (numWater > tier2Limit) {
            totalCost += (numWater - tier2Limit) * tier3Rate;
            numWater = tier2Limit;
        }
        if (numWater > tier1Limit) {
            totalCost += (numWater - tier1Limit) * tier2Rate;
            numWater = tier1Limit;
        }
        if (numWater > 0) {
            totalCost += numWater * tier1Rate;
        }

        return totalCost;
    }

    public static double calculateElectricityBill(int numKWh) {
        double totalCost = 0;

        // Giá điện theo từng bậc
        int tier1Limit = 50;
        int tier2Limit = 100;
        int tier3Limit = 200;
        int tier4Limit = 300;
        int tier5Limit = 400;

        double tier1Rate = 1806;
        double tier2Rate = 1866;
        double tier3Rate = 2167;
        double tier4Rate = 2729;
        double tier5Rate = 3050;
        double tier6Rate = 3151;

        if (numKWh > tier5Limit) {
            totalCost += (numKWh - tier5Limit) * tier6Rate;
            numKWh = tier5Limit;
        }
        if (numKWh > tier4Limit) {
            totalCost += (numKWh - tier4Limit) * tier5Rate;
            numKWh = tier4Limit;
        }
        if (numKWh > tier3Limit) {
            totalCost += (numKWh - tier3Limit) * tier4Rate;
            numKWh = tier3Limit;
        }
        if (numKWh > tier2Limit) {
            totalCost += (numKWh - tier2Limit) * tier3Rate;
            numKWh = tier2Limit;
        }
        if (numKWh > tier1Limit) {
            totalCost += (numKWh - tier1Limit) * tier2Rate;
            numKWh = tier1Limit;
        }
        if (numKWh > 0) {
            totalCost += numKWh * tier1Rate;
        }

        return totalCost;
    }


    public static String formatToVND(Double amount) {
        Locale vietnamLocale = new Locale("vi", "VN"); // Định dạng theo Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);
        return currencyFormatter.format(amount);
    }
}
