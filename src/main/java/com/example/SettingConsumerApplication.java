package com.example;

import com.example.dao.UserDao;
import com.example.domain.UserSettings;
import com.example.settings.SettingsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.Principal;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@Controller
public class SettingConsumerApplication {


	private static final Logger LOG = LoggerFactory.getLogger(SettingConsumerApplication.class);

	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
		SpringApplication.run(SettingConsumerApplication.class, args);
	}

	@Autowired
	private UserDao userDao;

	@Autowired
	private SettingsProvider settingsProvider;

	@RequestMapping(method = RequestMethod.GET)

	public String index(Model model, Principal principal, HttpServletResponse response) {

		String login = principal.getName();

		model.addAttribute("userName", login);
		response.addCookie(new Cookie("userName", login));


		int settingsVersion = settingsProvider.getSettingsVersionByLogin(login);

		LOG.info("{} user setting version {}", login, settingsVersion);

		return "index";
	}


	@ResponseBody
	@RequestMapping(value = "/updateDateEdit", method = RequestMethod.GET)
	public String updateDateEdit(Principal principal) {

		UserSettings userSettings = settingsProvider.getUserSettingsByLogin(principal.getName());

		userSettings.setValue("hello");

		settingsProvider.save(userSettings);

		return "ok";
	}





}
