package org.example.DTO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


@Data
public class RescheduleBooking implements Serializable {

    private String bookingId;

    private String customerId;

    private String serviceOperatorId;

    private long bookedSlotStartTime;

    private long bookedSlotEndTime;

    private String newBookingScheduledTime;

    private String newBookingServiceOperatorId;

}
