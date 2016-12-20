package co.wnk.framework.core.security.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import co.wnk.framework.core.security.vo.User;
import wnk.com.biz.common.service.testDao;
import wnk.com.biz.sample.controller.WNKSampleController;
import co.wnk.framework.core.Exception.WnkException;
import co.wnk.framework.core.common.util.WnkEncryptionUtil;
import co.wnk.framework.core.common.util.WnkSpringBeanUtil;
import co.wnk.framework.core.security.service.UserService;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	@Autowired testDao userService;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = null;
		
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("input username : " + username);
        System.out.println("input password : " + password);
        
        Collection<? extends GrantedAuthority> authorities;

        try {

        	user = userService.loadUserByUsername(username);
            String inputPasswd = WnkEncryptionUtil.enCryptionAes128(password).trim();
                        
            if(user == null)
            	throw new UsernameNotFoundException("system.notFindUser");
            if (!inputPasswd.equals(user.getPasswd().trim())){
            	System.out.println("inputPasswd || user.getPasswd() : " + inputPasswd + " || " + user.getPasswd());
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

}
