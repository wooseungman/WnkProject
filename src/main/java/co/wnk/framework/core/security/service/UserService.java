package co.wnk.framework.core.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.wnk.framework.core.common.util.WnkSpringBeanUtil;
import co.wnk.framework.core.security.vo.User;
import wnk.com.biz.sample.dao.WNKSampleDao;

@Service
public class UserService implements UserDetailsService {
	
	//private JdbcTemplate jdbcTemplate;
	
	//private DataSource dataSource;
	 
	 
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("11111111111111111111111111111111111111111111111111 : ");

		//WnkSpringBeanUtil.getStringBean("securityDao");
		//Map<String, Object> params = new HashMap<String, Object>();
		
		//Map<String, Object> paramMap = new HashMap<String, Object>();
		//paramMap.put("username", username);
		
		//String sql = "SELECT * FROM GBL_MEMBER WHERE ID = ?";
		
		//Map<String,Object> www = (Map<String,Object>)jdbcTemplate.queryForMap("sample.selectMemberInfo",paramMap);
		//dao.selectOne("sample.selectMemberInfo", paramMap);
		User user = new User();
		System.out.println("22222222222222222222222222222222222222222222222222");
		return user;
	}
	
	public User loadUserByUserId(String userId) throws UsernameNotFoundException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		//dao.selectOne("sample.selectMemberInfo", paramMap);
		User user = new User();
		return user;
	}
}
