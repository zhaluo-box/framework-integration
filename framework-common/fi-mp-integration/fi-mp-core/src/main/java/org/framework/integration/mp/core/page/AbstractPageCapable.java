package org.framework.integration.mp.core.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created  on 2022/8/18 16:16:18
 *
 * @author zl
 */
@Data
public abstract class AbstractPageCapable {

    /**
     * 分页默认从第一页开始
     */
    private int pageNum = 1;

    /**
     * 默认分页大小20 但是最好有前端传递, 这里的分页大小只是做一个冗余的健壮性处理
     */
    private int pageSize = 20;

    /**
     * 分页顺序
     * createTime::Desc
     */
    private List<String> orderBy = new ArrayList<>();

    public <T> Page<T> buildPage() {
        Page<T> page = new Page<>(pageNum, pageSize);
        page.setOptimizeCountSql(false);
        page.setOptimizeJoinOfCountSql(false);
        if (orderBy.size() > 0) {
            page.addOrder(convertOrder());
        }
        return page;
    }

    /**
     * 将前端传递的排序字符转为Mybatis排序字段
     */
    private List<OrderItem> convertOrder() {
        List<OrderItem> orderItems = new ArrayList<>();
        if (orderBy.size() == 0) {
            return orderItems;
        }

        orderBy.stream().map(order -> order.split("::")).peek(orderChar -> {
            if (orderChar.length <= 1) {
                throw new RuntimeException("排序字段异常" + Arrays.toString(orderChar));
            }
        }).forEach(orderChar -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(orderChar[0]);
            orderItem.setAsc("ASC".equalsIgnoreCase(orderChar[1]));
            orderItems.add(orderItem);
        });

        return orderItems;
    }

}
