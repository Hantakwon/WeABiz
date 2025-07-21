package com.labscale.weabiz.board.entities;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BOARD")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"attachments"})
public class Board {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "BOARDID")
    private int boardId;

    @NonNull
    @Column(name = "TITLE", nullable = false)
    private String title;

    @NonNull
    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;

    // 작성자
    @Column(name = "WRITER", nullable = false)
    private String writer;

    // 작성일
    @Column(name = "REGDATE", nullable = false)
    private LocalDateTime regdate = LocalDateTime.now();

    // 공지사항 여부
    @Column(name = "ISNOTICE", nullable = false)
    private boolean isNotice = false;
    
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardAttachment> attachments = new ArrayList<>();
}
