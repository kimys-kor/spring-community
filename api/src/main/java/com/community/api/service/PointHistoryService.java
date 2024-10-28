package com.community.api.service;

import com.community.api.model.PointHistory;
import com.community.api.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PointHistoryService {

    @Value("${key.signupPoint}")
    private String signupPoint;

    @Value("${key.loginPoint}")
    private String loginPoint;

    @Value("${key.savePostPoint}")
    private String savePostPoint;

    @Value("${key.saveCommentPoint}")
    private String saveCommentPoint;

    private final PointHistoryRepository pointHistoryRepository;

    public PointHistory save(Long userId, String nickname, String actionType, Long postId) {
        int point = getActionPoint(actionType);

        String pointContent = String.format("[%s]님 %s + %d포인트", nickname, getActionDescription(actionType), point);

        PointHistory pointHistory = PointHistory.builder()
                .userId(userId)
                .pointContent(pointContent)
                .point(point)
                .postId(postId)
                .build();

        return pointHistoryRepository.save(pointHistory);
    }

    private String getActionDescription(String actionType) {
        switch (actionType) {
            case "signup":
                return "회원 가입 축하";
            case "login":
                return "첫 로그인";
            case "savePost":
                return "게시글 작성";
            case "saveComment":
                return "댓글 작성";
            default:
                return "포인트 획득";
        }
    }

    private int getActionPoint(String actionType) {
        if ("signup".equals(actionType)) {
            return Integer.parseInt(signupPoint);
        } else if ("login".equals(actionType)) {
            return Integer.parseInt(loginPoint);
        } else if ("savePost".equals(actionType)) {
            return Integer.parseInt(savePostPoint);
        } else if ("saveComment".equals(actionType)) {
            return Integer.parseInt(saveCommentPoint);
        } else {
            return 0;
        }
    }
}