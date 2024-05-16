package com.community.api.service;

import com.community.api.common.exception.AuthenticationErrorCode;
import com.community.api.common.exception.MessageErrorCode;
import com.community.api.model.Message;
import com.community.api.model.User;
import com.community.api.model.dto.MessageDto;
import com.community.api.model.dto.ReadMessageListDto;
import com.community.api.repository.MessageCustomRepository;
import com.community.api.repository.MessageRepository;
import com.community.api.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    @PersistenceContext
    EntityManager em;
    private final MessageRepository messageRepository;
    private final MessageCustomRepository messageCustomRepository;
    private final UserRepository userRepository;

    public Message sendMessage(String sender, MessageDto messageDto) {
        if (sender.equals(messageDto.receiver())) {
            throw MessageErrorCode.MESSAGE_CANNOT_SEND_TO_SELF.defaultException();
        }
        userRepository.findByUsername(messageDto.receiver()).orElseThrow(AuthenticationErrorCode.USER_NOT_EXIST::defaultException);


        Message message = Message.builder()
                .title(messageDto.title())
                .content(messageDto.content())
                .receiver(messageDto.receiver())
                .sender(sender)
                .isChecked(true)
                .build();

        messageRepository.save(message);
        return message;
    }

    public Page<ReadMessageListDto> getMessageList(String username, Pageable pageable) {
        return messageCustomRepository.getList(username, pageable);
    }

    @Transactional
    public Message getMessageContent(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(MessageErrorCode.No_EXIST_MESSAGE::defaultException);

        message.setChecked(true);
        em.flush();
        em.clear();
        return message;
    }

    public void deleteMessage(List<Long> idList) {
        for (Long id : idList) {
            messageRepository.deleteById(id);
        }
    }

}
