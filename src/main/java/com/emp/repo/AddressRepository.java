package com.emp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.entity.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
