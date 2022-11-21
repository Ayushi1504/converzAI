package org.example.Repository;


import org.example.DataModel.Customer;
import org.springframework.data.repository.CrudRepository;
public interface CustomerRepository extends CrudRepository<Customer, String>{
}
