package com.junsu.cyr.service.service;

import com.junsu.cyr.domain.cheers.Cheer;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.CheerRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.CheerExceptionCode;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheerService {

    private final CheerRepository cheerRepository;
    private final UserService userService;

    public Long getTotalCheer() {
        Long totalCheer = cheerRepository.sumCheer();
        return totalCheer;
    }

    @Transactional
    public void updateCheer(Integer userId) {
        User user = userService.getUserById(userId);

        Cheer cheer = getCheerOrCreateCheer(user);
        if(isValidCheerRequest(cheer)){
            cheer.updateCheer();
        } else {
            throw new BaseException(CheerExceptionCode.INVALID_REQUEST_PERIOD);
        }
    }

    public Cheer getCheerOrCreateCheer(User user) {
        Optional<Cheer> cheer = cheerRepository.findCheerByUser_UserId(user.getUserId());

        if(cheer.isPresent()) {
            return cheer.get();
        } else {
            Cheer createCheer = Cheer.builder()
                    .user(user)
                    .sum(0L)
                    .updatedAt(LocalDateTime.now())
                    .build();
            return cheerRepository.save(createCheer);
        }
    }

    public Boolean isValidCheerRequest(Cheer cheer) {
        LocalDateTime oneMinuteBefore = LocalDateTime.now().minusMinutes(1);
        return cheer.getUpdatedAt().isBefore(oneMinuteBefore);
    }
}
