package org.framework.integration.fi.mg.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDayQpsDTO;
import org.framework.integration.fi.mg.common.dto.SysOperationLogTopQpsDTO;
import org.framework.integration.fi.mg.common.vo.SysOperationLogPageVO;
import org.framework.integration.fi.mg.common.vo.SysOperationLogTopQpsVO;
import org.framework.integration.mp.core.page.BasePageQuery;

import java.util.List;

/**
 * Created  on 2023/5/25 11:11:40
 *
 * @author zl
 */
public interface SysOperationLogService extends SysOperationLogSaveService {

    /**
     * 如何抽象成分页数据也是一个问题
     */
    IPage<SysOperationLogDTO> page(BasePageQuery<SysOperationLogPageVO> pageQuery);

    /**
     * @return top qps , 默认前20
     */
    List<SysOperationLogTopQpsDTO> topQps(SysOperationLogTopQpsVO sysOperationLogTopQpsVO);

    /**
     * @return 单个API name  24 小时QPS 统计
     */
    List<SysOperationLogDayQpsDTO> dayQps();
}
