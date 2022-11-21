package org.example.Controller;

import org.example.DataModel.Booking;
import org.example.DataModel.ServiceOperator;
import org.example.Service.ServiceOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/serviceOperator/")
public class ServiceOperatorController {

    @Autowired
    ServiceOperatorService serviceOperatorService;


    @GetMapping("all")
    public ResponseEntity<List<ServiceOperator>> getAllServiceOperator() {
        return new ResponseEntity<>(serviceOperatorService.getAllServiceOperators(), HttpStatus.CREATED);
    }

    @GetMapping("get/{operatorId}")
    public ResponseEntity<Optional<ServiceOperator>> getServiceOperatorInfo(@PathVariable(value = "operatorId")  String operatorId) {
        return new ResponseEntity<>(serviceOperatorService.getServiceOperator(operatorId), HttpStatus.ACCEPTED);
    }

    @PostMapping("getBookings/serviceOperator/{operatorId}")
    public ResponseEntity<List<Booking>> getServiceOperatorBookings(@PathVariable(value = "operatorId")  String operatorId) {
        return new ResponseEntity<>(serviceOperatorService.getServiceOperatorBooking(operatorId),HttpStatus.ACCEPTED);
    }


    @PostMapping("getOpenBookings/serviceOperator/{operatorId}")
    public ResponseEntity<List<Long[]>> getOpenSlots(@PathVariable(value = "operatorId")  String operatorId) {
        return new ResponseEntity<>(serviceOperatorService.getServiceOperatorOpenSlots(operatorId),HttpStatus.ACCEPTED);

    }

}
