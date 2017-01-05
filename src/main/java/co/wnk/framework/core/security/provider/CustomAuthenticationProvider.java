package co.wnk.framework.core.security.provider;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import co.wnk.framework.core.security.vo.User;
import co.wnk.framework.core.common.util.WnkEncryptionUtil;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	private UserDetailsService userService;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = null;
		
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        Collection<? extends GrantedAuthority> authorities;

        try {
        	user = (User) userService.loadUserByUsername(username);
            String inputPasswd = WnkEncryptionUtil.enCryptionAes128(password).trim();
                        
            if(user == null)
            	throw new UsernameNotFoundException("system.notFindUser");
            if (!inputPasswd.equals(user.getPasswd().trim())){
            	throw new BadCredentialsException("system.notFindUser");
            }
            	
            authorities = user.getAuthorities();
        } catch(UsernameNotFoundException e) {
        	System.out.println(e.toString());
            throw new UsernameNotFoundException(e.getMessage());
        } catch(BadCredentialsException e) {
        	System.out.println(e.toString());
            throw new BadCredentialsException(e.getMessage());
        } catch(Exception e) {
        	System.out.println(e.toString());
            throw new RuntimeException(e.getMessage());
        }

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	public boolean supports(Class<?> authentication) {
		return true;
	}

	public void setUserService(UserDetailsService userService) {
		this.userService = userService;
	}
}
