package org.example.Controller;

import org.example.DataModel.Booking;
import org.example.DataModel.Customer;
import org.example.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomer(),HttpStatus.ACCEPTED);
    }

    @GetMapping("get/{customerId}")
    public ResponseEntity<Optional<Customer>> getCustomerInfo(@PathVariable(value = "customerId")  String customerId) {
        return new ResponseEntity<>(customerService.getCustomerInfo(customerId),HttpStatus.CREATED);
    }

    @PostMapping("create/customer")
    public ResponseEntity<Optional<Customer>> createCustomer( @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer),HttpStatus.CREATED);
    }


    @PostMapping("getBookings/customer/{customerId}")
    public ResponseEntity<List<Booking>> getCustomerBookings(@PathVariable(value = "customerId")  String customerId) {
        return new ResponseEntity<>(customerService.getCustomerBooking(customerId), HttpStatus.ACCEPTED);
    }



}
