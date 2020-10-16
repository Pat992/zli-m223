package ch.zli.m223.api19a.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zli.m223.api19a.crm.model.impl.CustomerImpl;

public interface CustomerRepository extends JpaRepository<CustomerImpl, Long>  {

}
