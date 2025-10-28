package com.junsu.cyr.service.apply;

import com.junsu.cyr.domain.applys.Apply;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.apply.ApplyConditionRequest;
import com.junsu.cyr.model.apply.ApplyResponse;
import com.junsu.cyr.model.apply.ApplyUploadRequest;
import com.junsu.cyr.repository.ApplyRepository;
import com.junsu.cyr.response.exception.code.ApplyExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
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
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final UserService userService;

    public Apply getApplyByApplyId(Long applyId) {
        return applyRepository.findById(applyId)
                .orElseThrow(() -> new BaseException(ApplyExceptionCode.NOT_FOUND_APPLY));
    }

    public ApplyResponse getApply(Long applyId, Integer userId) {
        User user = userService.getUserById(userId);
        Apply apply = getApplyByApplyId(applyId);

        if(!userService.isLeastManager(user) && userId != apply.getUser().getUserId()) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        return new ApplyResponse(apply);
    }

    public Page<ApplyResponse> getApplyList(ApplyConditionRequest condition, Integer userId) {
        User user = userService.getUserById(userId);

        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        Page<Apply> applies;
        if(condition.getConfirm() == null) {
            applies = applyRepository.findAllBy(pageable);
        } else {
            applies = applyRepository.findAllByConfirm(condition.getConfirm(), pageable);
        }

        return applies.map(apply -> new ApplyResponse(
                apply.getApplyId(),
                apply.getTitle(),
                apply.getPreferenceRole(),
                apply.getConfirm(),
                apply.getConfirmedAt(),
                apply.getUser().getUserId(),
                apply.getUser().getNickname()));
    }

    @Transactional
    public ApplyResponse uploadApply(ApplyUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(userService.isLeastManager(user)) {
            throw new BaseException(ApplyExceptionCode.ALREADY_MANAGER_OR_ADMIN);
        }

        isValidUploadData(request);

        Apply apply = Apply.builder()
                .user(user)
                .title(request.getTitle())
                .motive(request.getMotive())
                .primaryTime(request.getPrimaryTime())
                .weeklyHour(request.getWeeklyHour())
                .preferenceRole(request.getPreferenceRole())
                .preferenceMethod(request.getPreferenceMethod())
                .contact(request.getContact())
                .confirm(Boolean.FALSE)
                .build();

        applyRepository.save(apply);

        return new ApplyResponse(apply);
    }

    @Transactional
    public void confirmApply(Long applyId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        Apply apply = getApplyByApplyId(applyId);

        if(apply.getConfirm()) {
            throw new BaseException(ApplyExceptionCode.ALREADY_CONFIRMED_APPLY);
        }

        apply.confirm(user);
    }

    private void isValidUploadData(ApplyUploadRequest request) {
        if(request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new BaseException(ApplyExceptionCode.INVALID_REQUEST_VALUE);
        }
        if(request.getMotive() == null || request.getMotive().isEmpty()) {
            throw new BaseException(ApplyExceptionCode.INVALID_REQUEST_VALUE);
        }
        if(request.getPrimaryTime() == null || request.getPrimaryTime().isEmpty()) {
            throw new BaseException(ApplyExceptionCode.INVALID_REQUEST_VALUE);
        }
        if(request.getWeeklyHour() == null || request.getWeeklyHour().isEmpty()) {
            throw new BaseException(ApplyExceptionCode.INVALID_REQUEST_VALUE);
        }
        if(request.getContact() == null || request.getContact().isEmpty()) {
            throw new BaseException(ApplyExceptionCode.INVALID_REQUEST_VALUE);
        }
    }
}
