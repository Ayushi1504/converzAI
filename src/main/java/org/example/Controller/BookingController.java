package org.example.Controller;

import org.example.DTO.RescheduleBooking;
import org.example.DataModel.Booking;
import org.example.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings/")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("createBooking}")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return new ResponseEntity<>(bookingService.createBooking(booking), HttpStatus.CREATED);
    }

    @PostMapping("cancelBooking/customer/{customerId}/bookingId/{bookingId}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable(value = "customerId")  String customerId,@PathVariable(value = "bookingId")  String bookingId) {
        return new ResponseEntity<>(bookingService.cancelBooking(customerId,bookingId), HttpStatus.ACCEPTED);
    }


    @PostMapping("reschedule")
    public ResponseEntity<Booking> rescheduleBooking(@RequestBody RescheduleBooking booking) {
        return new ResponseEntity<>(bookingService.rescheduleBooking(booking),HttpStatus.ACCEPTED);
    }
}
