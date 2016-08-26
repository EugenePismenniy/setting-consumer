package com.example.settings;

import com.example.domain.UserSettings;

/**
 * @author it100985pev on 15.08.16 12:02.
 */
public interface SettingsProvider {

	/**
	 * @return version settings
	 * */
	int getSettingsVersionByLogin(String login);

	/**
	 * @return version settings
	 * */
	int save(UserSettings userSettings);

	UserSettings getUserSettingsByLogin(String login);
}
