package com.example.controller;

import com.example.domain.UserSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * @author it100985pev on 25.08.16 16:43.
 */
@Service
public class CookieExpert {


	private static final Logger LOG = LoggerFactory.getLogger(CookieExpert.class);

	private final ObjectMapper objectMapper;

	private static final String COOKIE_NAME_PATTERN = "setting-consumer%s";

	@Autowired
	private CookieExpert(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}


	public void saveUserSettings(UserSettings userSettings, HttpServletResponse response) {


		String cookieValue;
		try {
			cookieValue = objectMapper.writeValueAsString(userSettings);
		} catch (Exception e) {
			LOG.error("Error write user settings object as string: {}", ExceptionUtils.getMessage(e));
			return;
		}

		try {
			cookieValue = URLEncoder.encode(cookieValue, "utf8");
		} catch (Exception e) {
			LOG.error("Error encode cookie value as utf8 string: {}, value = '{}'"
					, ExceptionUtils.getMessage(e), cookieValue);
			return;
		}


		String cookieName = getCookieName(userSettings.getLogin());

		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(2));
		response.addCookie(cookie);
	}


	public String getCookieName(String login) {
		return String.format(COOKIE_NAME_PATTERN, login);
	}


	public UserSettings extractUserSettings(String login, HttpServletRequest request) {

		String cookieName = getCookieName(login);

		String cookieValue = findCookieValueByName(cookieName, request);

		UserSettings userSettings = null;

		if (cookieValue != null) {
			try {
				userSettings =  objectMapper.readValue(cookieValue, UserSettings.class);
			} catch (Exception e) {
				LOG.error("Error parse json '{}' settings from cookie: {}",
						cookieValue, ExceptionUtils.getMessage(e));
			}
		}

		return userSettings;
	}


	public String findCookieValueByName(String name, HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();

		Cookie cookie = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (name.equals(c.getName())) {
					cookie = c;
					break;
				}
			}
		}

		if (cookie != null) {

			String value = cookie.getValue();
			try {
				value = URLDecoder.decode(cookie.getValue(), "utf8");
			} catch (Exception e) {
				LOG.error("Error decode cookie value as utf8 string: {}, value = '{}'",
						ExceptionUtils.getMessage(e), value);
				return null;
			}


			LOG.info("Found cookie: name = '{}', value = '{}', version = '{}', domain = '{}', path = '{}', secure = '{}', comment = '{}', maxAge = '{}'"
					, cookie.getName()
					, value
					, cookie.getVersion()
					, cookie.getDomain()
					, cookie.getPath()
					, cookie.getSecure()
					, cookie.getComment()
					, cookie.getMaxAge()
			);

			return value;

		} else {
			LOG.info("Cookie by name = '{}' not found!", name);
			return null;
		}
	}
}
