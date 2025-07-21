package com.labscale.weabiz.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.labscale.weabiz.ai.APIResponseDO;
import com.labscale.weabiz.ai.WebClientAPI;
import com.labscale.weabiz.board.entities.Board;
import com.labscale.weabiz.board.entities.BoardAttachment;
import com.labscale.weabiz.board.service.BoardAttachmentService;
import com.labscale.weabiz.board.service.BoardService;
import com.labscale.weabiz.user.entities.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final BoardAttachmentService boardAttachmentService;
	private final WebClientAPI webClientAPI;

	@GetMapping
	public String boardListPage(Model model) {
		List<Board> boardList = boardService.findAll();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

		// 공지사항만 필터링
		List<Map<String, Object>> formattedBoardList = boardList.stream().filter(board -> !board.isNotice())
				.map(board -> {
					Map<String, Object> map = new HashMap<>();
					map.put("boardId", board.getBoardId());
					map.put("title", board.getTitle());
					map.put("writer", board.getWriter());
					map.put("regdate", board.getRegdate().format(formatter));
					map.put("isNotice", board.isNotice());
					return map;
				}).collect(Collectors.toList());

		model.addAttribute("boardList", formattedBoardList);
		model.addAttribute("boardType", "일반 게시판");
		return "board";
	}

	@GetMapping("/notice")
	public String boardNoticeListPage(Model model) {
		List<Board> boardList = boardService.findAll();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

		// 공지사항만 필터링
		List<Map<String, Object>> formattedBoardList = boardList.stream().filter(board -> board.isNotice()) // isNotice
																											// == true만
				.map(board -> {
					Map<String, Object> map = new HashMap<>();
					map.put("boardId", board.getBoardId());
					map.put("title", board.getTitle());
					map.put("writer", board.getWriter());
					map.put("regdate", board.getRegdate().format(formatter));
					map.put("isNotice", board.isNotice());
					return map;
				}).collect(Collectors.toList());

		model.addAttribute("boardList", formattedBoardList);
		model.addAttribute("boardType", "공지사항");
		return "board";
	}

	@GetMapping("/insert")
	public String boardInsertForm() {
		return "boardInsert";
	}

	@PostMapping("/insert")
	public String boardInsert(Board board, @RequestParam(value = "files", required = false) MultipartFile[] files,
			@RequestParam(value = "isNotice", required = false) String isNotice, HttpSession session) {

		User user = (User) session.getAttribute("user");

		// 작성자 설정
		board.setWriter(user.getUserName());

		// ✅ ADMIN만 isNotice 가능 (단순히 값이 넘어오면 true 처리)
		if (isNotice != null) {
			board.setNotice(true);
		} else {
			board.setNotice(false);
		}

		System.out.println(board);
		
		// 게시글 저장
		boardService.save(board);

		// ✅ 첨부파일 처리
		String uploadDir = "C:/upload/";
		File dir = new File(uploadDir);
		if (!dir.exists())
			dir.mkdirs();

		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					try {
						String originalFilename = file.getOriginalFilename();
						String savedPath = uploadDir + System.currentTimeMillis() + "_" + originalFilename;

						file.transferTo(new File(savedPath));

						BoardAttachment attachment = new BoardAttachment();
						attachment.setBoard(board); // 관계 설정
						attachment.setFileName(originalFilename);
						attachment.setFilePath(savedPath);
						attachment.setFileSize(file.getSize());

						boardAttachmentService.saveAttachment(attachment);

					} catch (IOException e) {
						e.printStackTrace();
						// 첨부파일 저장 실패 시, 게시글은 유지
					}
				}
			}
		}

		return "redirect:/board";
	}

	@GetMapping("/{id}")
	public String boardDetail(@PathVariable("id") int id, Model model) {
		Board board = boardService.findById(id);
		List<BoardAttachment> attachments = boardAttachmentService.getAttachmentsByBoardId(id); // 첨부파일 조회

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

		// Board를 Map으로 가공
		Map<String, Object> boardMap = new HashMap<>();
		boardMap.put("boardId", board.getBoardId());
		boardMap.put("title", board.getTitle());
		boardMap.put("content", board.getContent());
		boardMap.put("writer", board.getWriter());
		boardMap.put("regdate", board.getRegdate().format(formatter)); // 포맷
		boardMap.put("isNotice", board.isNotice());

		model.addAttribute("board", boardMap);
		model.addAttribute("attachments", attachments); // 첨부파일 모델에 추가

		System.out.println("첨부파일" + attachments);

		return "boardDetail";
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
		BoardAttachment attachment = boardAttachmentService.findById(id);
		if (attachment == null) {
			return ResponseEntity.notFound().build();
		}

		try {
			File file = new File(attachment.getFilePath());
			if (!file.exists()) {
				return ResponseEntity.notFound().build();
			}

			// 파일 리소스 생성
			Resource resource = new InputStreamResource(new FileInputStream(file));

			// 헤더 설정 (파일 이름 인코딩 처리)
			String encodedFileName = URLEncoder.encode(attachment.getFileName(), "UTF-8").replaceAll("\\+", "%20");

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(file.length()).body(resource);

		} catch (IOException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	// 게시글 수정 페이지 이동
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model, HttpSession session) {
		Board board = boardService.findById(id);

		model.addAttribute("board", board);
		return "boardUpdate"; // 수정 페이지 JSP
	}

	// 게시글 수정 처리
	@PostMapping("/update")
	public String updateBoard(@RequestParam("boardId") int boardId, @RequestParam("title") String title,
			@RequestParam("content") String content, HttpSession session) {
		Board board = boardService.findById(boardId);

		board.setTitle(title);
		board.setContent(content);
		boardService.save(board);

		return "redirect:/board/" + boardId;
	}

	// 게시글 삭제 처리
	@GetMapping("/delete/{id}")
	public String deleteBoard(@PathVariable("id") int id, HttpSession session) {
		Board board = boardService.findById(id);

		boardService.deleteById(id); // 첨부파일도 같이 삭제되도록 구현되었다고 가정
		return "redirect:/board";
	}

	@GetMapping("/summarize/{id}")
	public String summarizeBoard(@PathVariable("id") int id, Model model) {
		// 게시글 조회
		Board board = boardService.findById(id);

		// HTML 태그 제거
		String cleanText = Jsoup.parse(board.getContent()).text();

		// 프롬프트 구성
		String prompt = cleanText;

		// LLM 호출
		APIResponseDO responseDO = new APIResponseDO();
		responseDO.setAnswer(webClientAPI.ask(prompt));

		// 모델 전달
		model.addAttribute("board", board);
		model.addAttribute("responseDO", responseDO);

		return "boardDetail";
	}
}
