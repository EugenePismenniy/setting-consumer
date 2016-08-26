package com.example.settings;

import com.example.dao.UserSettingsDou;
import com.example.domain.UserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author it100985pev on 15.08.16 14:03.
 */
@Service("settingsProviderDefaultImpl")
public class SettingsProviderDefaultImpl implements SettingsProvider {

	private static final Logger LOG = LoggerFactory.getLogger(SettingsProviderDefaultImpl.class);

	@Autowired
	private UserSettingsDou userSettingsDou;

	@Cacheable(cacheNames = "settingsVersion")
	@Override
	public int getSettingsVersionByLogin(String login) {
		LOG.info("get version without cache: login = '{}'", login);
		return userSettingsDou.getSettingsVersionByLogin(login);
	}

	@CacheEvict(cacheNames = "settingsVersion", key = "#userSettings.login", beforeInvocation = true)
	@CachePut(cacheNames = "settingsVersion", key = "#userSettings.login")
	@Override
	public int save(UserSettings userSettings) {
		LOG.info("save new user settings = {} and get version", userSettings);
		UserSettings us = userSettingsDou.saveAndFlush(userSettings);
		return us.getVersion();
	}

	@CacheEvict(cacheNames = "settingsVersion", key = "#login")
	@Override
	public UserSettings getUserSettingsByLogin(String login) {
		LOG.info("get user settings by login = {}", login);
		return userSettingsDou.findOne(login);
	}
}
