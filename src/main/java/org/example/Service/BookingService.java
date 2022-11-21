package org.example.Service;

import org.example.Configuration.BookingStatus;
import org.example.DTO.RescheduleBooking;
import org.example.DataModel.Booking;
import org.example.DataModel.Customer;
import org.example.DataModel.ServiceOperator;
import org.example.Repository.BookingRepository;
import org.example.Repository.CustomerRepository;
import org.example.Repository.ServiceOperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class BookingService {

    private Logger logger =  Logger.getLogger(BookingService.class.toString());

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ServiceOperatorRepository serviceOperatorRepository;

    @Autowired
    ServiceOperatorService serviceOperatorService;

    public Booking createBooking(Booking booking){

        if(booking.getCustomerId()!=null && customerRepository.existsById(booking.getCustomerId())){

            if( booking.getServiceOperatorId()!=null && serviceOperatorRepository.existsById(booking.getServiceOperatorId())){


                    //  check if any operator is free at mentioned Time
                    // FCFS method for now
                    // else throw error

                    Boolean slotFree = false;

                    List<ServiceOperator> serviceOperatorList = serviceOperatorService.getAllServiceOperators();

                    for( ServiceOperator operator : serviceOperatorList ){

                        if(slotFree) break;

                        List<Long[]> freeSlots = serviceOperatorService.getServiceOperatorOpenSlots(operator.getOperatorId());

                        for(Long[] slots : freeSlots){
                            if(booking.getBookedSlotStartTime()>= slots[0]
                                    && booking.getBookedSlotStartTime()+3600<=slots[1]) {
                                slotFree = true;
                                booking.setServiceOperatorId(operator.getOperatorId());
                                break;
                            }
                        }

                    }

                    if(slotFree){
                        booking.setBookedSlotEndTime(booking.getBookedSlotStartTime()+3600);
                    }
                    else {
                        logger.warning("No Free Slots at Mentioned Time");
                        return null;
                    }


            }
            else{

                Boolean slotFree = false;

                List<Long[]> freeSlots = serviceOperatorService.getServiceOperatorOpenSlots(booking.getServiceOperatorId());

                for(Long[] slots : freeSlots){
                    if(booking.getBookedSlotStartTime()>= slots[0]
                            && booking.getBookedSlotStartTime()+3600<=slots[1]) {
                        slotFree = true;
                        break;
                    }
                }

                if(slotFree){
                    booking.setBookedSlotEndTime(booking.getBookedSlotStartTime()+3600);
                }
                else {
                    logger.warning("No Free Slots at Mentioned Time");
                    return null;
                }

            }

            booking.setBookingId(UUID.randomUUID().toString());
            booking.setBookingStatus(BookingStatus.SCHEDULED.toString());
            bookingRepository.save(booking);
            return booking;
        }
        else logger.warning("Customer Id does not exist");
        return null;
    }

    public Booking cancelBooking(String customerId,String bookingId){
        if(customerRepository.existsById(customerId)){
            Booking toBeCancelled = new Booking();
            toBeCancelled.setBookingId(bookingId);
            updateBookingStatus(toBeCancelled,BookingStatus.CANCELLED.toString());
            return toBeCancelled;
        }
        logger.warning("Customer Id does not exist");
        return  null;
    }

    public Booking rescheduleBooking(RescheduleBooking rescheduleBooking){

        if(rescheduleBooking.getCustomerId()!=null
                && customerRepository.existsById(rescheduleBooking.getCustomerId())
                && rescheduleBooking.getBookingId()!=null
                && bookingRepository.existsById(rescheduleBooking.getBookingId())){

            // Mark Old Booking as CANCELLED
            // Create new Booking

            Booking old = new Booking();
            old.setBookingId(rescheduleBooking.getBookingId());
            updateBookingStatus(old,BookingStatus.CANCELLED.toString());

            logger.info("Booking with ID : "+rescheduleBooking.getBookingId()+ " CANCELLED");

            logger.info("Creating new Booking at Requested Time");

            Booking newBooking = new Booking();
            newBooking.setCustomerId(rescheduleBooking.getCustomerId());
            newBooking.setBookedSlotStartTime(rescheduleBooking.getBookedSlotStartTime());
            createBooking(newBooking);
        }
        else logger.warning("Customer Id does not exist");
        return null;
    }

    public List<Booking> getAllCustomerBookings(String customerId){
        bookingRepository.getAllBookingsByCustomerId(customerId);
        return null;
    }

    public Boolean updateBookingStatus(Booking booking,String status){
        if(bookingRepository.existsById(booking.getBookingId()) && ( booking.getBookingStatus().equalsIgnoreCase(BookingStatus.CANCELLED.toString())
                || booking.getBookingStatus().equalsIgnoreCase(BookingStatus.RESCHEDULED.toString())
                || booking.getBookingStatus().equalsIgnoreCase(BookingStatus.COMPLETED.toString())
                || booking.getBookingStatus().equalsIgnoreCase(BookingStatus.SCHEDULED.toString())
        )){
            booking.setBookingStatus(status);
            bookingRepository.save(booking);

            return true;
        }

        return  false;

    }



}
