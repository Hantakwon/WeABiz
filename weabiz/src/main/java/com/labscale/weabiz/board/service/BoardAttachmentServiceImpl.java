package com.labscale.weabiz.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.labscale.weabiz.board.entities.BoardAttachment;
import com.labscale.weabiz.board.repository.BoardAttachmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardAttachmentServiceImpl implements BoardAttachmentService {

    private final BoardAttachmentRepository attachmentRepository;

    @Override
    public void saveAttachment(BoardAttachment attachment) {
        attachmentRepository.save(attachment);
    }

    @Override
    public List<BoardAttachment> getAttachmentsByBoardId(int boardId) {
        return attachmentRepository.findByBoard_BoardId(boardId);
    }

    @Override
    public void deleteAttachment(int attachmentId) {
        attachmentRepository.deleteById(attachmentId);
    }

	@Override
	public BoardAttachment findById(int attachmentId) {
		return attachmentRepository.findById(attachmentId).get();
	}
}