package com.labscale.weabiz.board.service;

import java.util.List;

import com.labscale.weabiz.board.entities.BoardAttachment;

public interface BoardAttachmentService{
    void saveAttachment(BoardAttachment attachment);
    BoardAttachment findById(int attachmentId);
    List<BoardAttachment> getAttachmentsByBoardId(int boardId);
    void deleteAttachment(int attachmentId);
}
