package com.collecting.collecting_data_news.api.member.repository;



import com.collecting.collecting_data_news.domain.member.entity.Member;
import com.collecting.collecting_data_news.domain.member.enums.SnsType;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findBySnsIdAndSnsType(String snsId, SnsType snsType);
}