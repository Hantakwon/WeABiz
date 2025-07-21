package com.labscale.weabiz.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.labscale.weabiz.entities.Schedule;
import com.labscale.weabiz.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 📄 스케줄 메인 화면
    @GetMapping
    public String schedulePage(Model model) {
        List<Schedule> schedules = scheduleService.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Map<String, Object>> scheduleList = schedules.stream().map(s -> {
            Map<String, Object> map = new HashMap<>();
            map.put("scheduleId", s.getId());
            map.put("title", s.getTitle());
            map.put("content", s.getContent());
            map.put("startdate", s.getStartdate().format(formatter));
            map.put("enddate", s.getEnddate().format(formatter));
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("scheduleList", scheduleList);
        return "schedule";
    }

    // ➕ 일정 추가
    @PostMapping("/insert")
    public String insertSchedule(@RequestParam String title,
                                 @RequestParam String content,
                                 @RequestParam String startdate,
                                 @RequestParam String enddate) {
        Schedule schedule = new Schedule();
        schedule.setTitle(title);
        schedule.setContent(content);
        schedule.setStartdate(LocalDate.parse(startdate).atStartOfDay());
        schedule.setEnddate(LocalDate.parse(enddate).atStartOfDay());

        scheduleService.save(schedule);
        return "redirect:/schedule";
    }

    // 📝 일정 수정
    @PostMapping("/update/{id}")
    public String updateSchedule(@PathVariable("id") Integer id,
                                 @RequestParam String title,
                                 @RequestParam String content,
                                 @RequestParam String startdate,
                                 @RequestParam String enddate) {
        Schedule schedule = scheduleService.findById(id);
        if (schedule != null) {
            schedule.setTitle(title);
            schedule.setContent(content);
            schedule.setStartdate(LocalDate.parse(startdate).atStartOfDay());
            schedule.setEnddate(LocalDate.parse(enddate).atStartOfDay());
            scheduleService.save(schedule);
        }
        return "redirect:/schedule";
    }

    // 🗑️ 일정 삭제
    @PostMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable("id") Integer id) {
        scheduleService.deleteById(id);
        return "redirect:/schedule";
    }

    // 📅 FullCalendar 이벤트용 JSON
    @GetMapping("/events")
    @ResponseBody
    public List<Map<String, Object>> getEvents() {
        return scheduleService.findAll().stream().map(s -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", s.getId());
            map.put("title", s.getTitle());
            map.put("start", s.getStartdate().toString());
            map.put("end", s.getEnddate().toString());
            map.put("content", s.getContent());
            return map;
        }).collect(Collectors.toList());
    }
}
