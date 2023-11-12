package com.texoit.challenge.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@SuppressWarnings({ "serial"})
@Entity
@Table(name="studio")
public class Studio implements Serializable {
	 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name", length=75, unique=true)
	private String name;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER )
	@JoinTable(
			  name = "moviestudio", 
			  joinColumns =@JoinColumn(name = "idstudio"),  
			  inverseJoinColumns = @JoinColumn(name = "idmovie"))
	private List<Movie> movies;
	

	public Studio() {
		super();
	}
	public Studio(String name) {
		super();
		this.name=name;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
	
	
}
