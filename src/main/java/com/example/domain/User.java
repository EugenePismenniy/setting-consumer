package com.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author it100985pev on 12.08.16 11:54.
 */
@Entity
@Table(name = "USER")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
	@Id
	@Column(name = "LOGIN")
	private String login;
	@Column(name = "PASSWORD")
	private String password;
}
