package com.labscale.weabiz.user.entities;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USERS")
/*
@NamedQueries({
	@NamedQuery(name = "Singer.findAllWithName",
		query = "select distinct s from Singer s " +
			"left join fetch s.albums a " +
			"left join fetch s.instruments i"),
	@NamedQuery(name = "Singer.findById",
		query = "select distinct s from Singer s " +
			"left join fetch s.albums a " +
			"left join fetch s.instruments i " +
			"where s.id = :id")
})
*/
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"roles", "todos", "schedules"})
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USERID")
    private Long userId;

    @NonNull
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @NonNull
    @Column(name = "USERNAME", nullable = false)
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "DEPTID")
    private int deptId;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "JOINED")
    private LocalDate joined;

    @Column(name = "RESIGNED")
    private LocalDate resigned;

    @Column(name = "PROFILE_IMAGE")
    private String profileImage;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserRole> roles;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTodo> todos;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSchedule> schedules;
}
