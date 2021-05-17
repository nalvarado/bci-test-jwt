package com.bci.test.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity(name = "users")
public class Users {

	@Id
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_user")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "create", nullable = false)
	private Date create;

	@Column(name = "modified", nullable = false)
	private Date modified;

	@Column(name = "last_login", nullable = false)
	private Date lastLogin;

	@Column(name = "activate", nullable = false)
	private Boolean activate;
	
	@Column(name = "token", length = 500)
	private String token;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user")
	private Set<Phones> phones = new HashSet<Phones>();

}
