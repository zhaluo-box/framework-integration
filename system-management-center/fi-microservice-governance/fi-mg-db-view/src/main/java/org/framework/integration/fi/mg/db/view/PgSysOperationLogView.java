package org.framework.integration.fi.mg.db.view;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDayQpsDTO;
import org.framework.integration.fi.mg.common.dto.SysOperationLogTopQpsDTO;
import org.framework.integration.fi.mg.common.vo.SysOperationLogPageVO;
import org.framework.integration.fi.mg.common.vo.SysOperationLogTopQpsVO;
import org.framework.integration.fi.mg.db.view.entity.SysOperationLog;
import org.framework.integration.fi.mg.db.view.mapper.SysOperationMapper;
import org.framework.integration.fi.mg.db.view.service.DBSysOperationLogService;
import org.framework.integration.mp.core.page.BasePageQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created  on 2023/5/30 11:11:53
 *
 * @author zl
 */
@Component
public class PgSysOperationLogView extends ServiceImpl<SysOperationMapper, SysOperationLog> implements DBSysOperationLogService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysOperationLogDTO sysOperationLogDTO) {
        SysOperationLog sysOperationLog = new SysOperationLog();
        BeanUtils.copyProperties(sysOperationLogDTO, sysOperationLog);
        save(sysOperationLog);
    }

    @Override
    public IPage<SysOperationLogDTO> page(BasePageQuery<SysOperationLogPageVO> pageQuery) {
        SysOperationLogPageVO query = pageQuery.getQuery();
        Page<SysOperationLog> page = pageQuery.buildPage();
        LambdaQueryWrapper<SysOperationLog> condition = new LambdaQueryWrapper<SysOperationLog>()
                        // formatter
                        .eq(Objects.nonNull(query.getBusinessCode()), SysOperationLogDTO::getBusinessCode, query.getBusinessCode())
                        .eq(StringUtils.hasText(query.getName()), SysOperationLogDTO::getName, query.getName())
                        .eq(StringUtils.hasText(query.getGroupName()), SysOperationLogDTO::getGroupName, query.getGroupName())
                        .eq(StringUtils.hasText(query.getSystemName()), SysOperationLogDTO::getSystemName, query.getSystemName())
                        .eq(StringUtils.hasText(query.getOperatorId()), SysOperationLogDTO::getOperatorId, query.getOperatorId())
                        .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()), SysOperationLogDTO::getStartTime,
                                 query.getStartTime(), query.getEndTime())
                        .eq(StringUtils.hasText(query.getModuleName()), SysOperationLogDTO::getModuleName, query.getModuleName());

        Page<SysOperationLog> pageResult = page(page, condition);
        return pageResult.convert(p -> p);
    }

    @Override
    public List<SysOperationLogTopQpsDTO> topQps(SysOperationLogTopQpsVO sysOperationLogTopQpsVO) {
        return null;
    }

    @Override
    public List<SysOperationLogDayQpsDTO> dayQps() {
        return null;
    }
}
