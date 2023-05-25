package org.framework.integration.fi.mg.client.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.framework.integration.fi.mg.common.dto.SysOperationLogOriginalDTO;
import org.springframework.core.NamedThreadLocal;

/**
 * Created  on 2023/5/23 16:16:10
 *
 * @author zl
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogContextHolder {

    private static final ThreadLocal<SysOperationLogOriginalDTO> THREAD_LOCAL = new NamedThreadLocal<>("operation-log-context");

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static SysOperationLogOriginalDTO getLocalMap() {
        SysOperationLogOriginalDTO dto = THREAD_LOCAL.get();
        if (dto == null) {
            dto = new SysOperationLogOriginalDTO();
            THREAD_LOCAL.set(dto);
        }
        return dto;
    }

}
