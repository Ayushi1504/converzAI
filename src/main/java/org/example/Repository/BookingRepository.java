package org.example.Repository;

import org.example.DataModel.Booking;
import org.example.DataModel.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, String> {

    @Query("select b from Booking where customerId =: customerId")
    public List<Booking> getAllBookingsByCustomerId(String customerId);

    @Query("select b from Booking where serviceOperatorId =: serviceOperatorId")
    public List<Booking> getAllBookingsByServiceOperatorId(String serviceOperatorId);

}
