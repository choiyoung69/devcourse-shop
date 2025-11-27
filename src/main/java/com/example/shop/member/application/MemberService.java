package com.example.shop.member.application;

import com.example.shop.member.application.dto.MemberCommand;
import com.example.shop.member.application.dto.MemberInfo;
import com.example.shop.member.domain.Member;
import com.example.shop.member.domain.MemberRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<MemberInfo> findALl(Pageable pageable) {
        Page<Member> pages = memberRepository.findAll(pageable);

        return pages.stream()
                .map(MemberInfo::from)
                .toList();
    }

    public MemberInfo create(MemberCommand memberCommand) {
        Member member = Member.create(memberCommand.email(),
                memberCommand.name(),
                memberCommand.password(),
                memberCommand.phone(),
                memberCommand.saltKey(),
                memberCommand.flag());
        return MemberInfo.from(memberRepository.save(member));
    }

    public MemberInfo update(MemberCommand command, String id) {
        UUID uuid = UUID.fromString(id);

        Member member = memberRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));

        member.updateInformation(
                command.email(),
                command.name(),
                command.password(),
                command.phone(),
                command.saltKey(),
                command.flag()
        );

        Member savedMember = memberRepository.save(member);
        return MemberInfo.from(savedMember);
    }

    public void deleteById(String id) {
        UUID uuid = UUID.fromString(id);
        memberRepository.deleteById(uuid);
    }
}
