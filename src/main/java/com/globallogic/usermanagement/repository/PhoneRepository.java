package com.globallogic.usermanagement.repository;

import com.globallogic.usermanagement.repository.entity.Phone;
import com.globallogic.usermanagement.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, UUID> {

}
