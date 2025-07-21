package com.labscale.weabiz.user.entities;

import static jakarta.persistence.GenerationType.IDENTITY;

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
@Table(name = "USERTODO")
@Getter
@Setter
@ToString(exclude="user")
public class UserTodo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TODOID")
    private int todoId;    

    @Column(name = "CONTENT", nullable = false)
    private String content;
    
    @Column(name = "COMPLETED", nullable = false)
    private boolean completed; // 기본값 false
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID", nullable = false)  // 외래키
    private User user;
}