package com.lunarshade.vkapp.security;

import com.lunarshade.vkapp.entity.AppUser;
import com.lunarshade.vkapp.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class VkMiniappUserDetailService implements UserDetailsService {

    private final UserService userService;

    public VkMiniappUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        AppUser user = userService.getByProviderId(id);

        if (user == null) throw new UsernameNotFoundException("User not found");

        return User.builder()
                .username(id)
                .password("")
                .disabled(false)
                .credentialsExpired(false)
                .accountExpired(false)
                .accountLocked(false)
                .authorities(getAuthoritisList(user))
                .build();
    }

    private Collection<SimpleGrantedAuthority> getAuthoritisList (AppUser user) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        });

        return  authorities;
    }
}
