package com.emp.entity;

import java.time.LocalDate;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Employee_details")
public class Employee {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfJoin;
    private String department;
    private double salary;
    private boolean experience;
    
    @ManyToOne
    @JoinColumn(name = "permanent_address_id", referencedColumnName = "id")
    private Address permanentAddress;

    @ManyToOne
    @JoinColumn(name = "present_address_id", referencedColumnName = "id")
    private Address presentAddress;
    
    @ManyToOne
    @JoinColumn(name = "education1_id", referencedColumnName = "id")
    private Education education1;
    

    private String contactNo;
    private String email;
    private boolean activeStatus;
    
    

}
