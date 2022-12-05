package org.framework.integration.common.core.exceptions;

/**
 * Created  on 2022/12/2 14:14:47
 *
 * @author zl
 */
public class AuthorizationException extends RuntimeException {

    public AuthorizationException(String message) {
        super(message);
    }
}
