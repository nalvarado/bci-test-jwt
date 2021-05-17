package com.bci.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bci.test.model.Phones;

@Repository
public interface PhonesRepository extends JpaRepository<Phones, Integer> {

}
