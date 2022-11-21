package org.example.DataModel;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table(name = "customer",schema="cnv")
public class Customer implements Serializable {

    @Id
    @Column(name =  "customer_id")
    private String customerId;

    @Column(name =  "customer_name")
    private String customerName;

    // More Customer Related Info;

}
