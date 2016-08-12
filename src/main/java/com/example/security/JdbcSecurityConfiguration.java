package com.example.security;

import com.example.dao.UserDao;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author it100985pev on 12.08.16 14:30.
 */
@Configuration
@EnableGlobalAuthentication
public class JdbcSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {


	@Bean
	public UserDetailsService userDetailsService(final UserDao userDao) {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

				User user = userDao.findOne(login);

				if (user == null) {
					throw new UsernameNotFoundException("User '" + login + "' not found!");
				}


				UserDetails userDetails = new org.springframework.security.core.userdetails.User(
						user.getLogin(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER")
				);

				return userDetails;
			}
		};
	}


	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
}
