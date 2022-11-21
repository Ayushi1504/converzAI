package org.example.DataModel;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "service_operator",schema="cnv")
public class ServiceOperator implements Serializable {

    @Id
    @Column(name =  "operator_id")
    private String operatorId;

    @Column(name =  "operator_name")
    private String operatorName;

    @Column(name =  "operator_rating")
    private String operatorRating;

}
