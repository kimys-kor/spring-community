package com.community.api.service;

import com.community.api.model.User;
import com.community.api.model.base.UserGrade;
import com.community.api.model.dto.UserDetailDto;
import com.community.api.model.dto.UserDto;
import com.community.api.model.dto.UserReadDto;
import com.community.api.repository.UserCustomRepository;
import com.community.api.common.exception.inteface.CustomException;
import com.community.api.common.exception.AuthenticationErrorCode;
import com.community.api.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    @PersistenceContext
    EntityManager em;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;


    public User findByUsername(String username) {
        User byId = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        return byId;
    }

    public void updateLastLogin(Long userId, LocalDateTime time) {
        userRepository.updateLastLogin(userId, time);
    }

    // 관리자 페이지 유저 리스트
    public Map<String, Object> findAll(Pageable pageable) {
        Page<UserReadDto> pageObject = userCustomRepository.findAll(pageable);

        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("users",pageObject.getContent());
        result.put("totalItem", pageObject.getTotalElements());

        return result;
    }

    // 관리자 페이지 유저 상세
    public Map<String,Object> findById(Long userId) {
        UserDetailDto userDetailDto = userCustomRepository.findById(userId);


        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("userDetail", userDetailDto);
        return result;
    }

    public void join(UserDto userDto) throws CustomException{
        String encPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encPassword);
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getNickname(), userDto.getPhoneNum());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new CustomException(AuthenticationErrorCode.USER_ALREADY_EXIST);
        }
    }

    

    public List countAllByCreatedDtBetween() {
        LocalDate today = LocalDate.now();

        List data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate day = today.minusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
            String formattedDay = day.format(formatter);

            LocalDateTime start = day.atStartOfDay();
            LocalDateTime end = day.atTime(LocalTime.MAX);
            Integer count = userRepository.countAllByCreatedDtBetween(start, end);

            Map<String, Object> map = new HashMap<>();
            map.put("x", formattedDay);
            map.put("y", count);
            data.add(map);
        }

        return data;
    }

    // 유저 비밀번호 업데이트
    @Transactional
    public String updatePassword(Long userId) {
        Random random = new Random();
        int temp = 0;
        String tempNum = "";
        int size    = 6;
        String resultNum = "";

        for (int i=0; i<size; i++) {
            temp = random.nextInt(9);
            tempNum =  Integer.toString(temp);
            resultNum += tempNum;
        }
        String encPassword = passwordEncoder.encode(resultNum);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        user.setPassword(encPassword);
        em.flush();
        em.clear();
        return resultNum;
    }

    // 유저 포인트 추가
    @Transactional
    public void addPoint(Long userId, Integer point) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        user.setPoint(user.getPoint()+point);
        em.flush();
        em.clear();
    }

    // 유저 정보 수정
    @Transactional
    public void updateInfo(Long userId, String userNickname, String userGrade) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        UserGrade grade = UserGrade.of(userGrade);

        user.setNickname(userNickname);
        user.setGrade(grade);
        em.flush();
        em.clear();

    }
}
