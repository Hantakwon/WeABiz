package com.labscale.weabiz.board.service;

import java.util.List;

import com.labscale.weabiz.board.entities.Board;

public interface BoardService {
    List<Board> findAll();
    Board findById(int id);
    void save(Board board);
	void deleteById(int id);
	Board findLatestNotice();
}