package wnk.com.biz.sample.service;

import java.util.Collection;

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
import co.wnk.framework.core.common.util.WnkSpringBeanUtil;
import co.wnk.framework.core.security.service.UserService;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired testDao userService;

    //@Autowired private PasswordEncoder passwordEncoder;

    //@Autowired private SaltSource saltSource;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        
        
        System.out.println("input username : " + username);
        System.out.println("input password : " + password);
        
        User user;
        Collection<? extends GrantedAuthority> authorities;

        //try {

            user = userService.loadUserByUsername(username);
            //String hashedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
            //System.out.println("username : " + username + " / password : " + password + " / hash password : " + hashedPassword);
            //System.out.println("username : " + user.getUsername() + " / password : " + user.getPassword());
            
            System.out.println("login info : " + username + "||" + password);
            System.out.println("user info : " + user.getId() + "||" + user.getPasswd());
            
            authorities = user.getAuthorities();
            
            /*String hashedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));

            System.out.println("username : " + username + " / password : " + password + " / hash password : " + hashedPassword);
            System.out.println("username : " + user.getUsername() + " / password : " + user.getPassword());
			*/
            
            if (true) throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");

            //authorities = user.getAuthorities();
        /*} catch(UsernameNotFoundException e) {
        	System.out.println(e.toString());
            throw new UsernameNotFoundException(e.getMessage());
        } catch(BadCredentialsException e) {
        	System.out.println(e.toString());
            throw new BadCredentialsException(e.getMessage());
        } catch(Exception e) {
        	System.out.println(e.toString());
            throw new RuntimeException(e.getMessage());
        }*/

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	public boolean supports(Class<?> authentication) {
		return true;
	}

}
