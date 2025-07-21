package com.labscale.weabiz.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labscale.weabiz.board.entities.BoardAttachment;

public interface BoardAttachmentRepository extends JpaRepository<BoardAttachment, Integer> {
    List<BoardAttachment> findByBoard_BoardId(int boardId);
}
