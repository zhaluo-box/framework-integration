package org.framework.integration.fi.mg.common.enums;

/**
 * 基于自定义header 识别是外部调用还是内部调用
 * header_name = M_INVOKE_WAY
 *
 * @author zl
 * Created  on 2023/5/23 15:15:03
 */
public enum InvokeWay {

    /**
     * 内部调用
     */
    OUTER("外部调用"),

    /**
     * 外部调用
     */
    INNER("内部调用");

    String getDesc() {
        return desc;
    }

    final String desc;

    InvokeWay(String desc) {
        this.desc = desc;
    }
}
