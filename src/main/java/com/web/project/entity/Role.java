package com.web.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "roles")
@Table(name = "roles")
public class Role {
	
	public Role() {
		super();
	}
	
	public Role(Integer id) {
		this.id = id;
	}

	public Role(Integer id, String name, String mota) {
		super();
		this.id = id;
		this.name = name;
		this.mota = mota;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	@Column(name = "ten" , nullable = false , unique = true )
	private String name ;
	@Column(name = "mo_Ta", length = 40)
	private String mota ;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMota() {
		return mota;
	}
	public void setMota(String mota) {
		this.mota = mota;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
