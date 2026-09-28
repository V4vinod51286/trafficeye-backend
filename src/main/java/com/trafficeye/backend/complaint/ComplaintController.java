package com.trafficeye.backend.complaint;

import com.trafficeye.backend.complaint.dto.ComplaintResponse;
import com.trafficeye.backend.complaint.dto.CreateComplaintRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(
            ComplaintService complaintService) {

        this.complaintService = complaintService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComplaintResponse createComplaint(
            @Valid @RequestBody CreateComplaintRequest request) {

        return complaintService.createComplaint(request);
    }

    @GetMapping
    public List<ComplaintResponse> getComplaints() {

        return complaintService.getComplaints();
    }

   @GetMapping("/me")
    public List<ComplaintResponse> getMyComplaints() {

        return complaintService.getMyComplaints();
    }
    
    @GetMapping("/{id}")
    public ComplaintResponse getComplaint(
            @PathVariable UUID id) {

        return complaintService.getComplaint(id);
    }

}