package com.co.parrolabs.repositories;

import com.co.parrolabs.repositories.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE (:name IS NULL OR c.name = :name) AND (:phone IS NULL OR c.phone = :phone) AND (:email IS NULL OR c.email = :email)")
    List<Customer> searchCustomers(@Param("name") String name,
                                   @Param("phone") String phone,
                                   @Param("email") String email);
}