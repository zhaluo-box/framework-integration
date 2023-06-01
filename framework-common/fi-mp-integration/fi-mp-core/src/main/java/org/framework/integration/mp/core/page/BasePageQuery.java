package org.framework.integration.mp.core.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 适用于 复杂入参分页请求，以VO的形式传递
 * 推荐在POST 请求的时候使用当前类
 * Created  on 2022/3/11 10:10:23
 *
 * @author zl
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BasePageQuery<VO> extends AbstractPageCapable {

    /**
     * 对于GET请求查询，默认VO类型采用String
     * 对于很复杂的POST请求查询， VO类型采用自定已的查询VO
     */
    private VO query;

}
