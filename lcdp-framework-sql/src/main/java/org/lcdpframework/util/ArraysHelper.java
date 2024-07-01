package org.lcdpframework.util;

import org.lcdpframework.constant.ConcatConst;

import java.util.Collection;

/**
 * 集合操作相关的工具类.
 * @author blinkfox on 2017-04-01.
 */
public final class ArraysHelper {

    /**
     * 私有构造方法.
     */
    private ArraysHelper() {
        super();
    }

    /**
     * 判断数组是否不为空.
     * @param array 数组
     * @return 布尔值
     */
    public static boolean isNotEmpty(Object[] array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断集合是否为空.
     * @param collections 集合
     * @return 布尔值
     */
    public static boolean isEmpty(Collection<?> collections) {
        return collections == null || collections.isEmpty();
    }

    /**
     * 将对象转成数组，如果对象类型是数组或集合，则直接转换，否则构造成数组.
     * @param obj 对象
     * @param objType 对象类型
     * @return 数组
     */
    public static Object[] toArray(Object obj, int objType) {
        Object[] values;
        switch (objType) {
            case ConcatConst.OBJTYPE_ARRAY:
                values = (Object[]) obj;
                break;
            case ConcatConst.OBJTYPE_COLLECTION:
                values = ((Collection<?>) obj).toArray();
                break;
            default:
                values = new Object[]{obj};
        }
        return values;
    }
}