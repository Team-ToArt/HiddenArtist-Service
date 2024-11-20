package com.hiddenartist.backend.global.redis;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiddenartist.backend.domain.mentoring.service.MentoringService;
import com.hiddenartist.backend.global.config.AbstractIntegrationTest;
import com.hiddenartist.backend.global.exception.type.MentoringException;
import com.hiddenartist.backend.global.exception.type.RedisLockException;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisDistributeLockTest extends AbstractIntegrationTest {

  @Autowired
  private MentoringService mentoringService;

  @Test
  @DisplayName("Redis 분산 락으로 100개의 멘토링 신청 중 하나만 성공")
  void applicationTimeLockTest() {
    //given
    ExecutorService executor = Executors.newFixedThreadPool(100);

    String token = EntityToken.MENTORING.identifyToken("1");
    LocalDateTime applicationTime = LocalDateTime.of(2024, 10, 24, 15, 30);

    AtomicInteger successCount = new AtomicInteger();
    AtomicInteger exceptionCount = new AtomicInteger();

    List<CompletableFuture<Void>> requests = IntStream.range(0, 100).mapToObj(i ->
                                                          CompletableFuture.runAsync(() -> {
                                                            String email = "test" + i + "@test.com";
                                                            try {
                                                              mentoringService.reservationApplicationTime(token, applicationTime, email);
                                                              successCount.getAndIncrement();
                                                            } catch (MentoringException | RedisLockException e) {
                                                              exceptionCount.getAndIncrement();
                                                            }
                                                          }, executor))
                                                      .toList();
    //when
    CompletableFuture.allOf(requests.toArray(CompletableFuture[]::new)).join();

    //then
    assertThat(successCount.get()).isEqualTo(1);
    assertThat(exceptionCount.get()).isEqualTo(99);
  }

}