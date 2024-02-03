package com.collecting.collecting_data_news.api.member.repository;

import com.collecting.collecting_data_news.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {


    Optional<Member> findByOauthTypeAndOauthId(String oAuthType, String oAuthId);

    Optional<Member> findByOauthId(String oAuthId);
}
