package com.labscale.weabiz.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.labscale.weabiz.board.entities.Board;
import com.labscale.weabiz.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;

	@Override
	public List<Board> findAll() {
		return boardRepository.findAll();
	}

	@Override
	public Board findById(int id) {
		return boardRepository.findById(id).get();
	}

	@Override
	public void save(Board board) {
		boardRepository.save(board);
	}

	@Override
	public void deleteById(int id) {
		boardRepository.deleteById(id);
	}

	@Override
	public Board findLatestNotice() {
		return boardRepository.findTopByIsNoticeTrueOrderByRegdateDesc();
	}

}
