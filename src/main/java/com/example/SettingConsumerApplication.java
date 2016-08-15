package com.example;

import com.example.dao.UserDao;
import com.example.domain.UserSettings;
import com.example.settings.SettingsProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@Controller
public class SettingConsumerApplication {


	private static final Logger LOG = LoggerFactory.getLogger(SettingConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SettingConsumerApplication.class, args);
	}


	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SettingsProvider settingsProvider;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, Principal principal, HttpServletResponse response) {

		String login = principal.getName();

		model.addAttribute("userName", login);
		response.addCookie(new Cookie("userName", login));


		Date dateEdit = settingsProvider.getDateEditByLogin(login);

		LOG.info("{} user setting date edit {}", login, dateEdit);

		return "index";
	}


	@ResponseBody
	@RequestMapping(value = "/updateDateEdit", method = RequestMethod.GET)
	public String updateDateEdit(Principal principal) {

		UserSettings userSettings = settingsProvider.getUserSettingsByLogin(principal.getName());

		userSettings.setDateEdit(new Date());

		settingsProvider.save(userSettings);

		return "ok";
	}





}
