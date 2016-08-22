package com.example.dao;

import com.example.domain.UserSettings;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @author it100985pev on 12.08.16 13:48.
 */
public interface UserSettingsDou extends JpaRepository<UserSettings, String> {

	@Query("select u.version from UserSettings u where u.login = ?1")
	int getSettingsVersionByLogin(String login);

	UserSettings saveAndFlush(UserSettings u);
}
