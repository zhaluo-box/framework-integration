package org.framework.integration.example.web.mvc.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.example.web.mvc.common.entity.User;
import org.framework.integration.example.web.mvc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created  on 2022/3/16 09:9:20
 *
 * @author wmz
 */
@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insert(User user) {
        userMapper.insert(user);
        log.info(user.getId().toString());
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        userMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(String ids) {
        userMapper.deleteByIdsStr(ids);
    }

}
