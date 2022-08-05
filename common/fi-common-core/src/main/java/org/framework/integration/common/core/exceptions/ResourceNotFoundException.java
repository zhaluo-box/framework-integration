package org.framework.integration.common.core.exceptions;

/**
 * Created  on 2022/3/10 10:10:27
 *
 * @author wmz
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
