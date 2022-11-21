package org.example.Service;

import org.example.DataModel.Booking;
import org.example.DataModel.Customer;
import org.example.Repository.BookingRepository;
import org.example.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class CustomerService {
    private Logger logger =  Logger.getLogger(CustomerService.class.toString());
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public List<Customer> getAllCustomer(){
        List<Customer> customerList =  new LinkedList<>();
        customerRepository.findAll().forEach(customerList::add);
        return customerList;
    }

    public Optional<Customer> getCustomerInfo(String customerId){
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> createCustomer(Customer customer){
        customer.setCustomerId(UUID.randomUUID().toString());
        return Optional.of(customerRepository.save(customer));
    }

    public List<Booking> getCustomerBooking(String customerId){
        if( customerRepository.existsById(customerId)){
            // Booking repo get list
            List<Booking> bookings = bookingRepository.getAllBookingsByCustomerId(customerId);
            return bookings;
        }
        else logger.warning("Invalid customerId : "+customerId);
        return null;
    }


}
