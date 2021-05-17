package com.bci.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "phones")
public class Phones {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_phone")
	private Integer id;

	@Column(name = "city_code")
	private Integer cityCode;

	@Column(name = "country_code")
	private Integer countryCode;

	@Column(name = "number")
	private Integer number;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private Users user;

}
