package org.example.DataModel;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "booking",schema="cnv")
public class Booking implements Serializable {

    @Id
    @Column(name =  "booking_id")
    private String bookingId;

    @Column(name =  "customer_id")
    private String customerId;

    @Column(name =  "service_operator_id")
    private String serviceOperatorId;

    @Column(name =  "booked_start_time")
    private long bookedSlotStartTime;

    @Column(name =  "booked_end_time")
    private long bookedSlotEndTime;

    @Column(name = "booking_status")
    private String bookingStatus; // SCHEDULED // CANCELLED //  COMPLETED // RESCHEDULED

}
