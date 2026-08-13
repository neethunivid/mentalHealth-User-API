package com.mentalhealthapi.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.repository.MemberRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userInfo = memberRepository.findByMemberidAndStatus(username,1);
       
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}