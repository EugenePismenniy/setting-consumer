package com.example.domain;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;

/**
 * @author it100985pev on 12.08.16 12:20.
 */
@Entity
@Table(name = "USER_SETTINGS")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserSettings {

	@Id
	@Column(name = "LOGIN")
	private String login;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "VERSION")
	@Version
	private int version;
}
