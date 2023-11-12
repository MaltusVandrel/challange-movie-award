package com.texoit.challenge.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="movie")
public class Movie implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="title", length=75, unique=true)
	private String title;
	
	@Column(name="year", nullable=false)
	private Integer year;
	
	@Column(name="winner", nullable=false)
	private Boolean winner;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "movies",cascade=CascadeType.ALL )
	private Set<Producer> producers;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "movies",cascade=CascadeType.ALL )
	private Set<Studio> studios;
	
	public Movie() {
		super();
	}

	public Movie(String title, Integer year, Boolean winner) {
		super();
		this.title = title;
		this.year = year;
		this.winner = winner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	public Set<Producer> getProducers() {
		return producers;
	}

	public void setProducers(Set<Producer> producers) {
		this.producers = producers;
	}

	public Set<Studio> getStudios() {
		return studios;
	}

	public void setStudios(Set<Studio> studios) {
		this.studios = studios;
	}
	
	
}
