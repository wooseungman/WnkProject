package wnk.com.biz.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.wnk.framework.core.common.Constants;
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
		
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		switch(Integer.parseInt(user.getMember_class_code())){
			case 10 : 
				role.setName(Constants.MEMBER_LEVEL);
				break;
			case 30 : 
				role.setName(Constants.COMPANY_LEVEL);
				break;
			case 50 : 
				role.setName(Constants.MANAGE_LEVEL);
				break;
			case 99 : 
				role.setName(Constants.ADMIN_LEVAL);
				break;
			default : 
				role.setName(Constants.MEMBER_LEVEL);
				break;
		}
		roles.add(role);
		user.setAuthorities(roles);
		
		return user;
	}
}
