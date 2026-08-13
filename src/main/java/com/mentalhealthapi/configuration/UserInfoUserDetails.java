package com.mentalhealthapi.configuration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mentalhealthapi.model.Member;

public class UserInfoUserDetails implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String password;
    
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(Member memberInfo) {
        name=memberInfo.getMemberid();
        password=memberInfo.getPassword();
        authorities= Arrays.stream(memberInfo.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
