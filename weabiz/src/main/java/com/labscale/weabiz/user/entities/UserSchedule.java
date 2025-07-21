package com.labscale.weabiz.user.entities;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USERSCHEDULE")
@Getter
@Setter
@ToString(exclude = "user")
public class UserSchedule {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SCHEDULEID")
	private int scheduleId;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "CONTENT", nullable = false)
	private String content;

	@Column(name = "STARTDATE")
	private LocalDateTime startdate;

	@Column(name = "ENDDATE")
	private LocalDateTime enddate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERID", nullable = false) // 외래키
	private User user;
}
