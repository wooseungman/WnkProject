package wnk.com.biz.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.wnk.framework.core.security.vo.Role;
import co.wnk.framework.core.security.vo.User;
import wnk.com.biz.sample.dao.WNKSampleDao;

@Service
public class SecurityUserService implements UserDetailsService {

	@Autowired WNKSampleDao dao;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("user_id", username);
		User user = (User) dao.selectOne("sample.selectMemberInfo", m);
		
		if(user == null)
			throw new UsernameNotFoundException("system.notFindUser");
		
		Role role = new Role();
		role.setName(user.getMember_role());
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setAuthorities(roles);
		
		return user;
	}
}
