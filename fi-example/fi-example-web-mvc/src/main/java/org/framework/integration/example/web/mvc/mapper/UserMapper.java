package org.framework.integration.example.web.mvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.framework.integration.example.web.mvc.common.entity.User;

import java.util.List;

/**
 * Created  on 2022/2/10 22:22:11
 *
 * @author zl
 */

public interface UserMapper extends BaseMapper<User> {

    /**
     * mybatis 注解不支持 in List, 可以使用String 字符串使用
     */
    @Update("delete from user where id in (#{ids})")
    void deleteByIds(List<Long> ids);

    /**
     * 参数为字符串ids= "1,2,3"  因为id 在数据库为Long  所以会报 Data truncation: Truncated incorrect DOUBLE value: '6,7,8'
     * 是数据类型不匹配造成的
     */
    @Update("delete from user where id in (#{ids})")
    void deleteByIdsStr(String ids);
}
