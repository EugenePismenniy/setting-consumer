package com.example.settings;

import com.example.dao.UserSettingsDou;
import com.example.domain.UserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author it100985pev on 15.08.16 14:03.
 */
@Repository("settingsProviderDefaultImpl")
public class SettingsProviderDefaultImpl implements SettingsProvider {

	private static final Logger LOG = LoggerFactory.getLogger(SettingsProviderDefaultImpl.class);

	@Autowired
	private UserSettingsDou userSettingsDou;

	@Cacheable(cacheNames = "dateEdit", key = "#login")
	@Override
	public Date getDateEditByLogin(String login) {
		LOG.info("get dateEdit without cache: login = '{}'", login);
		return userSettingsDou.findDateEditByLogin(login);
	}

	@CacheEvict(cacheNames = "dateEdit", key = "#userSettings.login")
	@Override
	public UserSettings save(UserSettings userSettings) {
		LOG.info("save new user settings = {}", userSettings);
		return userSettingsDou.saveAndFlush(userSettings);
	}

	@Override
	public UserSettings getUserSettingsByLogin(String login) {
		return userSettingsDou.findOne(login);
	}
}
