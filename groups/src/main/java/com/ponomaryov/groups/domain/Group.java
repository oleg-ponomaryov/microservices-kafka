package com.ponomaryov.groups.domain;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ponomaryov.json.JsonDateSerializer;

import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Table(name = "groups")
@EqualsAndHashCode(of = { "id" })
public class Group implements Serializable {

	private static final long serialVersionUID = -8567109877530491869L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false)
	@Getter
	private String id;

    @Column(name = "name", nullable = false)
	@Getter
	@Setter
    private String name;
    
    @Column(name = "description", nullable = true)
	@Getter
	@Setter
    private String description;
    
    @Column(name = "created", nullable = false, updatable = false)
	@Getter
	@Setter
	@JsonSerialize(using=JsonDateSerializer.class)
    private Date created;

	@Column(name = "updated")
	@Getter
	@Setter
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date updated;
	
		@PrePersist
		public void onSave() {
			if (this.created==null) {
				this.created = new Date();
			}
		}

		@PreUpdate
		public void onUpdate() {
			this.updated = new Date();
		}
}
