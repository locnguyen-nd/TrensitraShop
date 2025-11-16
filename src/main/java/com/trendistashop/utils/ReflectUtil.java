package com.trendistashop.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class ReflectUtil {
    private ReflectUtil() {}

    public static void copyNonNull(Object src, Object target, boolean ignoreBlankString, Set<String> ignore) {
        Class<?> srcClass = src.getClass();
        Class<?> tgtClass = target.getClass();

        for (Field f : srcClass.getDeclaredFields()) {
            String name = f.getName();
            if (ignore != null && ignore.contains(name)) continue;

            try {
                String getterName = (f.getType() == boolean.class || f.getType() == Boolean.class) ? "is" + capitalize(name) : "get" + capitalize(name);
                Method getter = srcClass.getMethod(getterName);
                Object value = getter.invoke(src);

                if (value == null) continue;
                if (ignoreBlankString && value instanceof String s && s.isBlank()) continue;

                String setterName = "set" + capitalize(name);
                Method setter = findSetter(tgtClass, setterName, f.getType());
                if (setter != null) {
                    setter.invoke(target, value);
                }
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                throw new RuntimeException("Failed to copy field: " + name, e);
            }
        }
    }

    private static Method findSetter(Class<?> clazz, String setterName, Class<?> paramType) {
        try { return clazz.getMethod(setterName, paramType); }
        catch (NoSuchMethodException e) { return null; }
    }

    private static String capitalize(String s) {
        return (s == null || s.isEmpty()) ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
