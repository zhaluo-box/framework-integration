/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.framework.integration.mp.core.mateInject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.framework.integration.mp.core.entity.SecurityInfo;
import org.framework.integration.mp.core.service.SecurityInfoProvider;

import java.util.Date;
import java.util.Objects;

/**
 * 自定义sql字段填充器,本类默认在default-config.properties中配置
 * <p>
 * 若实际项目中，字段名称不一样，可以新建一个此类，在yml配置中覆盖mybatis-plus.global-config.metaObject-handler配置即可
 * <p>
 * 注意默认获取的userId为空
 */
@Slf4j
public class DefaultMateObjectHandler implements MetaObjectHandler {

    public DefaultMateObjectHandler(SecurityInfoProvider securityInfoProvider) {
        this.securityInfoProvider = securityInfoProvider;
    }

    private final SecurityInfoProvider securityInfoProvider;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("Common core MetaObject handler");

        try {
            setFieldValByName(getCreateTimeFieldName(), new Date(), metaObject);
            setFieldValByName(getUpdateTimeFieldName(), new Date(), metaObject);
        } catch (ReflectionException e) {
            //没有此字段，则不处理
        }

        SecurityInfo securityInfo = securityInfoProvider.getSecurityInfo();

        if (log.isDebugEnabled()) {
            log.debug(" -******- Login info {} : ", securityInfo);
        }

        if (Objects.isNull(securityInfo)) {
            return;
        }

        try {
            setFieldValByName(getCreateUserFieldName(), securityInfo.getUserName(), metaObject);
            setFieldValByName(getUpdateUserFieldName(), securityInfo.getUserName(), metaObject);
        } catch (ReflectionException e) {
            //没有此字段，则不处理
        }

        try {
            setFieldValByName(getCreateIdFieldName(), securityInfo.getUserId(), metaObject);
            setFieldValByName(getUpdateIdFieldName(), securityInfo.getUserId(), metaObject);
        } catch (ReflectionException e) {
            //没有此字段，则不处理
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        try {
            setFieldValByName(getUpdateTimeFieldName(), new Date(), metaObject);
        } catch (ReflectionException e) {
            //没有此字段，则不处理
            logDebugRecord(e);
        }
        SecurityInfo securityInfo = securityInfoProvider.getSecurityInfo();
        if (Objects.isNull(securityInfo)) {
            return;
        }
        try {
            setFieldValByName(getUpdateUserFieldName(), securityInfo.getUserName(), metaObject);
        } catch (ReflectionException e) {
            //没有此字段，则不处理
            logDebugRecord(e);
        }

        try {
            setFieldValByName(getUpdateIdFieldName(), securityInfo.getUserId(), metaObject);
        } catch (ReflectionException e) {
            //没有此字段，则不处理
            logDebugRecord(e);
        }
    }

    /**
     * default 默认打印Debug 日志
     */
    private void logDebugRecord(Throwable throwable) {
        if (log.isDebugEnabled()) {
            log.debug(throwable.getMessage());
        }
    }

    /**
     * 获取创建时间字段的名称（非数据库中字段名称）
     */
    protected String getCreateTimeFieldName() {
        return "createTime";
    }

    /**
     * 获取创建用户字段的名称（非数据库中字段名称）
     */
    protected String getCreateUserFieldName() {
        return "createUser";
    }

    /**
     * 获取创建用户字段的名称（非数据库中字段名称）
     */
    protected String getCreateIdFieldName() {
        return "createId";
    }

    /**
     * 获取更新时间字段的名称（非数据库中字段名称）
     */
    protected String getUpdateTimeFieldName() {
        return "updateTime";
    }

    /**
     * 获取更新用户字段的名称（非数据库中字段名称）
     */
    protected String getUpdateUserFieldName() {
        return "updateUser";
    }

    /**
     * 获取更新用户字段的名称（非数据库中字段名称）
     */
    protected String getUpdateIdFieldName() {
        return "updateId";
    }

}
