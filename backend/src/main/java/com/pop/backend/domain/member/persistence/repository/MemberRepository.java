package com.pop.backend.domain.member.persistence.repository;

import com.pop.backend.domain.member.persistence.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, QMemberRepository {

  Optional<Member> findByEmail(String email);

}