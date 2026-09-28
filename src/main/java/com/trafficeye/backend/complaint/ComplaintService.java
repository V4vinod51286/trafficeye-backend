package com.trafficeye.backend.complaint;

import com.trafficeye.backend.complaint.dto.ComplaintResponse;
import com.trafficeye.backend.complaint.dto.CreateComplaintRequest;
import com.trafficeye.backend.user.User;
import com.trafficeye.backend.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.trafficeye.backend.complaint.ComplaintStatus;

import java.util.List;
import java.util.UUID;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintService(
            ComplaintRepository complaintRepository,
            UserRepository userRepository) {

        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ComplaintResponse createComplaint(
            CreateComplaintRequest request) {

        // Temporary development user.
        // This will be replaced by the authenticated user
        // once OTP authentication is implemented.
        User user = userRepository
                .findByMobile("9999999999")
                .orElseThrow(() ->
                        new RuntimeException(
                                "Development user not found"));

        Complaint complaint = new Complaint();

        complaint.setUser(user);
        complaint.setViolationType(request.getViolationType());
        complaint.setVehicleNumber(request.getVehicleNumber());
        complaint.setIncidentTime(request.getIncidentTime());
        complaint.setLatitude(request.getLatitude());
        complaint.setLongitude(request.getLongitude());
        complaint.setAddress(request.getAddress());
        complaint.setDescription(request.getDescription());

        // Status is controlled by the backend.
        complaint.setStatus(ComplaintStatus.SUBMITTED);

        Complaint savedComplaint =
                complaintRepository.save(complaint);

        return mapToResponse(savedComplaint);
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getComplaints() {

        return complaintRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ComplaintResponse getComplaint(UUID id) {

        Complaint complaint = complaintRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Complaint not found: " + id));

        return mapToResponse(complaint);
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getMyComplaints() {

        User user = userRepository
                .findByMobile("9999999999")
                .orElseThrow(() ->
                        new RuntimeException(
                                "Development user not found"));

        return complaintRepository
                .findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ComplaintResponse mapToResponse(
            Complaint complaint) {

        ComplaintResponse response =
                new ComplaintResponse();

        response.setId(complaint.getId());
        response.setViolationType(
                complaint.getViolationType());
        response.setVehicleNumber(
                complaint.getVehicleNumber());
        response.setIncidentTime(
                complaint.getIncidentTime());
        response.setLatitude(
                complaint.getLatitude());
        response.setLongitude(
                complaint.getLongitude());
        response.setAddress(
                complaint.getAddress());
        response.setDescription(
                complaint.getDescription());
        response.setStatus(
                complaint.getStatus());
        response.setCreatedAt(
                complaint.getCreatedAt());

        return response;
    }
}