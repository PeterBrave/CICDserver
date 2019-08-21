package org.citrix.enums;

import lombok.Getter;

/**
 * @author kavin
 * @date 2019-08-19 20:53
 */
@Getter
public enum ResultEnum {

    INSERT_DB_ERROR(1, "insert database error"),
    GET_DATA_ERROR(2, "get data error"),
    DELETE_DATA_ERROR(3, "delete data error"),
    UPDATE_DATA_ERROR(4, "update data error"),

    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
