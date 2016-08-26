package com.example.controller;

import com.example.domain.UserSettings;
import com.example.settings.SettingsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author it100985pev on 25.08.16 11:22.
 */
@Controller
public class SettingsController {

	private static final Logger LOG = LoggerFactory.getLogger(SettingsController.class);

	@Value("${default-login}")
	private String defaultLogin;

	@Autowired
	private SettingsProvider settingsProvider;

	@Autowired
	private CookieExpert cookieExpert;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String index(Model model,
						HttpServletRequest request,
						HttpServletResponse response) {

		String login = defaultLogin;

		UserSettings userSettings = cookieExpert.extractUserSettings(login, request);

		if (userSettings == null) {
			LOG.info("User Settings got from Data Base");
			userSettings = settingsProvider.getUserSettingsByLogin(login);
			cookieExpert.saveUserSettings(userSettings, response);
		} else {
			int dbSettingsVersion = settingsProvider.getSettingsVersionByLogin(login);
			int cookieSettingsVersion = userSettings.getVersion();

			if (dbSettingsVersion != cookieSettingsVersion) {
				LOG.info("User Settings in the cookie and DB are different. Version in cookie = {}, Version in DB = {}. Get settings from DB"
						, cookieSettingsVersion, dbSettingsVersion);
				userSettings = settingsProvider.getUserSettingsByLogin(login);
				cookieExpert.saveUserSettings(userSettings, response);
			}
		}

		model.addAttribute("settings", userSettings);
		return "index";
	}


	@RequestMapping(method = RequestMethod.POST, value = "/")
	public String updateSettings(@RequestParam("settingsValue") String settingsValue
			, Model model
			, HttpServletResponse response) {

		String login = defaultLogin;

		UserSettings settings = settingsProvider.getUserSettingsByLogin(login);
		settings.setValue(settingsValue);

		settingsProvider.save(settings);
		cookieExpert.saveUserSettings(settings, response);

		model.addAttribute("settings", settings);

		return "index";
	}

}
