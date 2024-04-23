package com.community.api.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {


    // 정상 처리 0XXXXXXX
    // DATA 관련 010001XX
    DATA_SEARCH_SUCCESSFULLY(HttpStatus.OK, "00001100", "정상 데이터 입니다."),
    DATA_NORMAL_PROCESSING(HttpStatus.OK, "00001101", "정상 처리 되었습니다."),

    // USER 관련 010002XX
    USER_JOIN_SUCCESSFULLY(HttpStatus.OK, "00001001", "회원가입이 완료되었습니다."),
    USER_NOT_DUPLICATE_ID(HttpStatus.OK, "00001002", "중복된 아이디가 아닙니다."),
    USER_NOT_DUPLICATE_NICKNAME(HttpStatus.OK, "00001003", "중복된 닉네임이 아닙니다."),


    // 비정상 처리 91XXXXXX
    AUTH_PERMISSION_DENY(HttpStatus.OK, "91009991", "접근 권한이 없습니다."),

    // DATA 관련 910001XX
    DATA_UPDATE_WRONG(HttpStatus.OK, "91000101", "데이터 수정에 실패하였습니다."),
    DATA_NOT_DUPLICATE(HttpStatus.OK, "91000102", "중복된 DATA 입니다."),
    DATA_ALREADY_PROCESSED(HttpStatus.OK, "91000103", "이미 처리된 DATA 입니다."),


    // USER 관련 910002XX
    USER_NO_ID_SEARCHED(HttpStatus.OK, "91000201", "검색된 아이디가 없습니다."),
    USER_NOT_USE_ACCOUNT(HttpStatus.OK, "91000202", "비활성화 계정 입니다."),
    USER_JOIN_EMPTY_ACCOUNT(HttpStatus.OK, "91000203", "아이디를 입력하여 주시기 바랍니다."),
    USER_JOIN_EMPTY_PWD(HttpStatus.OK, "91000204", "비밀번호를 입력하여 주시기 바랍니다."),
    USER_JOIN_RESUBSCRIPTION_IS_NOT_VALID(HttpStatus.OK, "91000214", "탈퇴 후 재가입 유효기간이 아닙니다."),
    USER_JOIN_DUPLICATE_ACCOUNT(HttpStatus.OK, "91000215", "중복된 계정 입니다."),
    USER_JOIN_DUPLICATE_EMAIL(HttpStatus.OK, "91000216", "중복된 이메일 입니다."),
    USER_JOIN_EMAIL_NOT_AVAILABLE(HttpStatus.OK, "91000217", "사용할 수 없는 이메일 입니다."),
    USER_JOIN_DUPLICATE_PHONENUMBER(HttpStatus.OK, "91000218", "중복된 핸드폰 번호 입니다."),
    USER_JOIN_PHONENUMBER_NOT_AVAILABLE(HttpStatus.OK, "91000219", "사용할 수 없는 핸드폰 번호 입니다."),
    USER_INFORMATION_IS_NOT_RIGHT(HttpStatus.OK, "91000221", "로그인정보가 옳바르지 않습니다."),
    USER_TOKEN_ID_IS_NOT_RIGHT(HttpStatus.OK, "91000222", "토큰 ID가 옳바르지 않습니다."),
    USER_NO_SEARCHED(HttpStatus.OK, "91000223", "검색된 고객이 없습니다."),
    USER_JOIN_DUPLICATE_NICKNAME(HttpStatus.OK, "91000224", "중복된 닉네임 입니다."),
    USER_JOIN_IS_NULL(HttpStatus.OK, "91000225", "닉네임을 입력하여 주시기 바랍니다."),
    USER_PHONENUM_NO_SEARCHED(HttpStatus.OK, "91000226", "가입되지 않은 휴대폰 번호입니다."),
    USER_PHONENUM_EMAIL_NOT_MATCH(HttpStatus.OK, "91000227", "이메일과 폰번호가 일치하지 않습니다."),
    USER_ROLE_NOT_SHOP(HttpStatus.OK, "91000228", "상점 아이디 권한이 없습니다."),

    // 이벤트, 쿠폰 관련 910003XX
    EVENT_DUPLICATE(HttpStatus.OK, "91000301", "중복된 이벤트 참여입니다."),
    SHOPCOUPON_NOT_FOUND(HttpStatus.OK, "91000301", "존재 하지 않는 쿠폰 ID 입니다."),


    // Point 관련 910004XX
    LACK_OF_POINTS(HttpStatus.OK, "91000401", "Point 부족 합니다."),
    PURCHASE_THE_PREVIOUS_PRODUCT_LACK_OF_POINTS(HttpStatus.OK, "91000402", "이전 상품권 구매로 인한 현재 Point 부족 합니다."),
    POINT_GIFT_USER_NOT_QUALIFIED(HttpStatus.OK, "91000403", "포인트 받을 권한이 없는 고객입니다."),

    // 상점 관련 910005XX
    SHOP_NOT_FOUND(HttpStatus.OK, "91000501", "존재 하지 않는 상점 입니다."),

    // 주문 관련 910006XX
    ORDERS_PRICE_NOTCORRECT(HttpStatus.OK, "91000601", "주문 신청한 가격이 올바르지 않습니다"),
    ORDERS_NOT_FOUND(HttpStatus.OK, "91000602", "존재 하지 않는 주문 ID 입니다."),
    ORDERS_CANNOT_CANCLE(HttpStatus.OK, "91000603", "이미 진행중인 주문 입니다."),

    // 상품 관련 910007XX
    PRODUCT_NOT_FOUND(HttpStatus.OK, "91000701", "존재 하지 않는 상품 ID 입니다."),
    PRODUCT_NOT_CORRECT_SHOP(HttpStatus.OK, "91000702", "해당 상점의 상품이 아닙니다."),

    // 상품옵션 관련 910008XX
    PRODUCTOPTION_NOT_FOUND(HttpStatus.OK, "91000801", "존재 하지 않는 상품옵션 ID 입니다."),
    PRODUCTOPTION_NOT_CORRECT_PRODUCT(HttpStatus.OK, "91000802", "해당 상품의 상품옵션이 아닙니다"),


    // 상점 쿠폰 관련 910009XX
    COUPON_NOTOVER_100(HttpStatus.OK, "91000901", "할인율은 100%를 넘어 설정할수 없습니다"),
    COUPON_NOT_FOUND(HttpStatus.OK, "91000902", "존재하지 않는 쿠폰 입니다."),
    COUPON_NOT_OPEN(HttpStatus.OK, "92000903", "쿠폰의 유효기간을 확인해 주세요."),
    COUPON_NOT_USABLE(HttpStatus.OK, "92000904", "해당 상점에서 사용할수 없는 쿠폰입니다."),

    // 상점 리뷰 관련 910010XX
    REVIEW_ONLY_ORDER_USER(HttpStatus.OK, "91001001", "리뷰는 해당 가게에서 주문한 회원만 작성 가능합니다."),
    REVIEW_NOT_FOUND(HttpStatus.OK, "91001001", "존재 하지 않는 리뷰 ID 입니다."),
    REVIEW_NOT_CORRECT_WRITER(HttpStatus.OK, "91001001", "리뷰 작성자가 아닙니다."),

    // 장바구니 리뷰 관련 910011XX
    BASKET_ONLY_ONEITEM(HttpStatus.OK, "91001101", "장바구니는 1개의 아이템만 가능합니다."),
    BASKET_ONLY_ONESHOP(HttpStatus.OK, "91001102", "장바구니는 1개의 상점 상품만 가능합니다."),

    ;



    private final HttpStatus status;
    private final String statusCode;
    private final String statusMessage;

    ResultCode(HttpStatus status, String statusCode, String statusMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }


}
