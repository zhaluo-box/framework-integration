package org.framework.integration.utils.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

/**
 * Created  on 2022/7/28 16:16:31
 *
 * @author zl
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssertUtil extends Assert {

    public static <X extends Throwable> void hasTextTrow(String text, Supplier<? extends X> exceptionSupplier) throws X {
        if (!StringUtils.hasText(text)) {
            throw exceptionSupplier.get();
        }
    }
}
