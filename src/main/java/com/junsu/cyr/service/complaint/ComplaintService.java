package com.junsu.cyr.service.complaint;

import com.junsu.cyr.domain.complaints.Complaint;
import com.junsu.cyr.domain.complaints.ComplaintCategory;
import com.junsu.cyr.domain.complaints.Status;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.complaint.ComplaintConditionRequest;
import com.junsu.cyr.model.complaint.ComplaintListResponse;
import com.junsu.cyr.model.complaint.ComplaintRequest;
import com.junsu.cyr.model.complaint.ComplaintResponse;
import com.junsu.cyr.repository.ComplaintCategoryRepository;
import com.junsu.cyr.repository.ComplaintRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ComplaintExceptionCode;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final S3Service s3Service;
    private final UserService userService;
    private final ComplaintRepository complaintRepository;
    private final ComplaintCategoryRepository complaintCategoryRepository;

    public Complaint getComplaintByComplaintId(Long complaintId) {
        return complaintRepository.findById(complaintId)
                .orElseThrow(() -> new BaseException(ComplaintExceptionCode.NOT_FOUND_COMPLAINT));
    }

    public ComplaintResponse getComplaint(Long complaintId, User user) {
        Complaint complaint = getComplaintByComplaintId(complaintId);

        if(!complaint.getUser().equals(user) && !userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        return new ComplaintResponse(complaint);
    }

    public Page<ComplaintListResponse> getComplaintList(ComplaintConditionRequest condition) {
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        Page<Complaint> complaints;
        if(condition.getStatus() == null) {
            complaints = complaintRepository.findAll(pageable);
        } else {
            complaints = complaintRepository.findAllByStatus(condition.getStatus(), pageable);
        }

        return complaints.map(ComplaintListResponse::new);
    }

    @Transactional
    public void acceptComplaint(Long complaintId, String message, User user) {
        Complaint complaint = getComplaintByComplaintId(complaintId);

        if(!complaint.getUser().equals(user) && !userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        if(complaint.getStatus() != Status.WAIT) {
            throw new BaseException(ComplaintExceptionCode.ALREADY_PROCESSED_COMPLAINT);
        }

        if(message == null || message.isEmpty()) {
            throw new BaseException(ComplaintExceptionCode.TOO_SHORT_MESSAGE_LENGTH);
        }

        complaint.processing(user, Status.ACCEPT, message);
    }

    @Transactional
    public void rejectComplaint(Long complaintId, String message, User user) {
        Complaint complaint = getComplaintByComplaintId(complaintId);

        if(!complaint.getUser().equals(user) && !userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        if(complaint.getStatus() != Status.WAIT) {
            throw new BaseException(ComplaintExceptionCode.ALREADY_PROCESSED_COMPLAINT);
        }
        System.out.println(message);
        if(message == null || message.isEmpty()) {
            throw new BaseException(ComplaintExceptionCode.TOO_SHORT_MESSAGE_LENGTH);
        }

        complaint.processing(user, Status.REJECT, message);
    }

    @Transactional
    public void uploadComplaint(ComplaintRequest request, User user) {
        ComplaintCategory complaintCategory = complaintCategoryRepository.findByName(request.getCategoryName());

        String uploadUrl = null;
        try {
            if(request.getFile() != null) {
                uploadUrl = s3Service.uploadFile(request.getFile(), Type.COMPLAINT);
            }
        } catch (Exception e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        Complaint complaint = Complaint.builder()
                .user(user)
                .complaintCategory(complaintCategory)
                .captureUrl(uploadUrl)
                .title(request.getTitle())
                .reason(request.getReason())
                .status(Status.WAIT)
                .build();

        complaintRepository.save(complaint);
    }
}
