package org.framework.integration.common.core.exceptions;

/**
 * Created  on 2022/3/10 10:10:27
 *
 * @author wmz
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
