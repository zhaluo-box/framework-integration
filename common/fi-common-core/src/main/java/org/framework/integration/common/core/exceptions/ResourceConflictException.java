package org.framework.integration.common.core.exceptions;

/**
 * Created  on 2022/3/10 10:10:27
 *
 * @author wmz
 */
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
