package org.citrix.exception;

import org.citrix.enums.ResultEnum;

/**
 * @author kavin
 * @date 2019-08-19 20:49
 */
public class CICDException extends RuntimeException {

    private Integer code;

    public CICDException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public CICDException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
