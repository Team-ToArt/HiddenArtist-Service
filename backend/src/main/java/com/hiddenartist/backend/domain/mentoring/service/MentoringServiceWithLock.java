package com.hiddenartist.backend.domain.mentoring.service;

import com.hiddenartist.backend.domain.mentoring.persistence.LockApplicationTime;
import com.hiddenartist.backend.global.exception.type.MentoringException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.redis.LockApplicationTimeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentoringServiceWithLock {

  private final LockApplicationTimeClient lockApplicationTimeClient;

  public void reservationApplicationTime(LockApplicationTime lockApplicationTime) {
    String key = LockApplicationTimeClient.generateKey(lockApplicationTime.getTokenValue(),
        lockApplicationTime.getApplicationTime());
    LockApplicationTime reservedApplicationTime = lockApplicationTimeClient.findBy(key);
    if (isNotReservationTimeOwner(reservedApplicationTime.getEmail(), lockApplicationTime.getEmail())) {
      throw new MentoringException(ServiceErrorCode.TIME_ALREADY_LOCK);
    }
    lockApplicationTimeClient.save(lockApplicationTime);
  }

  private boolean isNotReservationTimeOwner(String reservedEmail, String targetEmail) {
    return !reservedEmail.equals(targetEmail);
  }

}