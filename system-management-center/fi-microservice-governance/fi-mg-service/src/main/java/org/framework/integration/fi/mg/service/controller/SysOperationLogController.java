package org.framework.integration.fi.mg.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;
import org.framework.integration.fi.mg.common.service.SysOperationLogService;
import org.framework.integration.fi.mg.common.vo.SysOperationLogPageVO;
import org.framework.integration.mp.core.page.BasePageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2023/5/25 10:10:57
 *
 * @author zl
 */

@RestController
@RequestMapping("sysOperationLog")
public class SysOperationLogController {

    @Autowired
    private SysOperationLogService sysOperationLogService;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody SysOperationLogDTO sysOperationLogDTO) {
        sysOperationLogService.save(sysOperationLogDTO);
        return ResponseEntity.ok();
    }

    @PostMapping("actions/pages")
    public ResponseEntity<IPage<SysOperationLogDTO>> page(@RequestBody BasePageQuery<SysOperationLogPageVO> pageQuery) {
        return ResponseEntity.ok(sysOperationLogService.page(pageQuery));
    }

}
