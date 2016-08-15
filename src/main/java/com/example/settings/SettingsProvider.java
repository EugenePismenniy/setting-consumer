package com.example.settings;

import com.example.domain.UserSettings;

import java.util.Date;

/**
 * @author it100985pev on 15.08.16 12:02.
 */
public interface SettingsProvider {

	Date getDateEditByLogin(String login);

	UserSettings save(UserSettings userSettings);

	UserSettings getUserSettingsByLogin(String login);
}
