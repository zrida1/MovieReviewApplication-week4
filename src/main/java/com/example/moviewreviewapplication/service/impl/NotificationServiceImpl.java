package com.example.moviewreviewapplication.service.impl;

import com.example.moviewreviewapplication.service.NotificationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Async
    @Override
    public void sendReviewNotification(String email, String movieTitle) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                "Notification sent to " + email +
                        " for movie: " + movieTitle
        );
    }
}