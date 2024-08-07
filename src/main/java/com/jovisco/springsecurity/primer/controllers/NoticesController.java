package com.jovisco.springsecurity.primer.controllers;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.entities.Notice;
import com.jovisco.springsecurity.primer.repositories.NoticeRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@RestController
public class NoticesController {

  private final NoticeRepository noticeRepository;

  @GetMapping("/notices")
  public ResponseEntity<List<Notice>> getNotices() {
    List<Notice> notices = noticeRepository.findAllActiveNotices();
    return (notices != null)
        ? ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
            .body(notices)
        : null;
  }
}