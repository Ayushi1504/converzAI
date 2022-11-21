package org.example.Service;

import org.example.Configuration.BookingStatus;
import org.example.DataModel.Booking;
import org.example.DataModel.ServiceOperator;
import org.example.Repository.BookingRepository;
import org.example.Repository.ServiceOperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class ServiceOperatorService {

    private Logger logger =  Logger.getLogger(ServiceOperatorService.class.toString());

    @Autowired
    private ServiceOperatorRepository serviceOperatorRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<ServiceOperator> getAllServiceOperators(){
        List<ServiceOperator> serviceOperatorsList =  new LinkedList<>();
        serviceOperatorRepository.findAll().forEach(serviceOperatorsList::add);
        return serviceOperatorsList;
    }

    public Optional<ServiceOperator> getServiceOperator(String operatorId){
        return serviceOperatorRepository.findById(operatorId);
    }

    public Optional<ServiceOperator> createServiceOperator(ServiceOperator operator){
        operator.setOperatorId(UUID.randomUUID().toString());
        return Optional.of(serviceOperatorRepository.save(operator));
    }

    public List<Booking> getServiceOperatorBooking(String operatorId){
        if( serviceOperatorRepository.existsById(operatorId)){
            // Booking repo get list
            logger.info("Retrieving Bookings for ServiceOperator with ID : "+operatorId);
            return bookingRepository.getAllBookingsByServiceOperatorId(operatorId);

        }
        else logger.warning("Invalid serviceOperatorId : "+operatorId);
        return null;
    }

    public List<Long[]> getServiceOperatorOpenSlots(String operatorId) {
        logger.info("Retrieving Bookings for ServiceOperator with ID : " + operatorId);
        List<Booking> bookedSlots = getServiceOperatorBooking(operatorId);

        // only SCHEDULED or RESCHEDULED (i.e. upcoming Appointments are in future)
        // so after taking current timestamp , we fill filter out the appointments for the upcoming day

        List<Booking> scheduledSlots = new ArrayList<>();

        for (Booking booking : bookedSlots) {
            if (booking.getBookingStatus().equalsIgnoreCase(BookingStatus.RESCHEDULED.toString())
                    || booking.getBookingStatus().equalsIgnoreCase(BookingStatus.SCHEDULED.toString())) {
                scheduledSlots.add(booking);
            }
        }

        Collections.sort(scheduledSlots, Comparator.comparing(Booking::getBookedSlotStartTime));

        // we have a list of upcoming slots
        // we will retrieve free slots for upcoming day(+1) from current time

        long currentTimestamp = System.currentTimeMillis();

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,1);

        long currentTimeStampNextDay = cal.getTime().getTime();

        List<Long[]> myFreeSlots = new ArrayList<>();

        for(Booking upcomingSlots : scheduledSlots){

            if(myFreeSlots.isEmpty()){
                if(upcomingSlots.getBookedSlotStartTime()>=currentTimestamp+3600){
                    myFreeSlots.add(new Long[]{currentTimestamp,currentTimestamp+3600});
                }
                else currentTimestamp = upcomingSlots.getBookedSlotEndTime();
            }
            else{
                Long[] lastFreeSlot = myFreeSlots.get(myFreeSlots.size()-1);
                if(upcomingSlots.getBookedSlotStartTime()>=lastFreeSlot[1]+3600) {
                    myFreeSlots.remove(myFreeSlots.get(myFreeSlots.size() - 1));
                    // current Time + one hour is free
                    myFreeSlots.add(new Long[]{lastFreeSlot[0], lastFreeSlot[1] + 3600});

                }
            }

        }

        // we parsed through the upcoming scheduled list
        // But we need to obtain free slots till next day

        // Adding a comparison
        if(!myFreeSlots.isEmpty()){
            Long[] lastFreeSlot = myFreeSlots.get(myFreeSlots.size()-1);
            if(lastFreeSlot[1]<currentTimeStampNextDay){
                myFreeSlots.remove(myFreeSlots.get(myFreeSlots.size() - 1));
                // current Time + one hour is free
                myFreeSlots.add(new Long[]{lastFreeSlot[0], currentTimeStampNextDay});
            }
        }

        return myFreeSlots;


    }


}
