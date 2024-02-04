package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.service.ImageCleanupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageCleanupServiceImpl implements ImageCleanupService {

    @Override
    @Scheduled(fixedRate = 2000L, initialDelay = 1000L) // Check daily
    public void checkAndDeleteUnusedImages() {

    }
}
