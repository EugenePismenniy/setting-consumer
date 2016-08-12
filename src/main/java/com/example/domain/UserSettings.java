package com.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author it100985pev on 12.08.16 12:20.
 */
@Entity
@Table(name = "USER_SETTINGS")
@NoArgsConstructor
@ToString
public class UserSettings {

	@Id
	@Column(name = "LOGIN")
	private String login;

	@Column(name = "VALUE")
	@Getter
	@Setter
	private String value;

	@Column(name = "DATE_EDIT")
	@Getter
	@Setter
	private Date dateEdit;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
