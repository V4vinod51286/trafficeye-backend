package com.trafficeye.backend.complaint;

import com.trafficeye.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ComplaintRepository
        extends JpaRepository<Complaint, UUID> {

    List<Complaint> findByUser(User user);
}