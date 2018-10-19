package com.xtp.library.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射帮助类
 */
public class ReflectUtil {
    /**
     * 利用反射获取指定对象的指定属性
     *
     * @param obj       目标对象
     * @param fieldName 目标属性
     * @return 目标属性的值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                result = field.get(obj);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 利用反射获取指定对象里面的指定属性
     *
     * @param obj       目标对象
     * @param fieldName 目标属性
     * @return 目标字段
     */
    private static Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz
                .getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                //ignore exception
            }
        }
        return field;
    }

    /**
     * 利用反射设置指定对象的指定属性为指定的值
     *
     * @param obj        目标对象
     * @param fieldName  目标属性
     * @param fieldValue 目标值
     */
    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
        Field field = getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据指定的对象、方法、参数反射调用，并返回调用结果
     *
     * @param method
     * @param target
     * @param args
     * @return
     */
    public static Object invoke(Method method, Object target, Object[] args) {
        if (method == null) {
            return null;
        }
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return method.invoke(target, args);
        } catch (InvocationTargetException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 根据class类型、methodName方法名称，返回Method对象。
     * 注意：这里不检查参数类型，所以自定义的java类应该避免使用重载方法
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static Method findMethod(Class<?> clazz, String methodName) {
        Method[] candidates = clazz.getDeclaredMethods();
        for (int i = 0; i < candidates.length; i++) {
            Method candidate = candidates[i];
            if (candidate.getName().equals(methodName)) {
                return candidate;
            }
        }
        if (clazz.getSuperclass() != null) {
            return findMethod(clazz.getSuperclass(), methodName);
        }
        return null;
    }
}