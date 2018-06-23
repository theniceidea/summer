package org.summerframework.springproxyvertx.base;


import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfigurationValueConverter {

    public static boolean isSupport(Class<?> fromType, Class<?> toType) {
        if (!isBasicType(fromType) || !isBasicType(toType)) {
            return false;
        }
        return true;
    }

    public static Object fromString(String value, Class<?> toType) {
        if (toType == null) {
            return null;
        }

        if (StringUtils.isEmpty(value) && !isPrimitive(toType)) {
            return null;
        }

        if (isPrimitive(toType)) {
            return getPrimitiveValue(value, toType);
        }

        return getValue(value, toType);
    }

    private static Object getValue(String value, Class<?> toType) {
        try {
            if (isString(toType)) {
                return value;
            }
            else if (isBoolean(toType)) {
                return Boolean.valueOf(value);
            }
            else if (isDate(toType)) {
                if (value.length() == 10) {
                    value = value + "T00:00:00+0000";
                }
                else if (value.length() == 19) {
                    value = value + "+0000";
                }
                return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(value);
            }
            else if (isNumber(toType)) {
                if (Integer.class == toType) {
                    return Integer.parseInt(value);
                }
                else if (Long.class == toType) {
                    return Long.parseLong(value);
                }
                else if (Double.class == toType) {
                    return Double.parseDouble(value);
                }
                else if (Float.class == toType) {
                    return Float.parseFloat(value);
                }
                else if (BigInteger.class == toType) {
                    return new BigInteger(value);
                }
                else if (BigDecimal.class == toType) {
                    return new BigDecimal(value);
                }
            }
            else if (isClassType(toType)){
                return Class.forName(value);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static Object getPrimitiveValue(String value, Class<?> toType) {
        boolean isNullValue = StringUtils.isEmpty(value);
        if (boolean.class == toType) {
            return isNullValue ? false : Boolean.valueOf(value).booleanValue();
        }
        else if (int.class == toType) {
            return isNullValue ? 0 : Long.valueOf(value).intValue();
        }
        else if (long.class == toType) {
            return isNullValue ? 0 : Long.valueOf(value).longValue();
        }
        else if (double.class == toType) {
            return isNullValue ? 0 : Double.valueOf(value).doubleValue();
        }
        else if (float.class == toType) {
            return isNullValue ? 0 : Double.valueOf(value).floatValue();
        }
        return null;
    }

    private static boolean isBasicType(Class<?> type) {
        return isString(type) || isDate(type) || isNumber(type) || isBoolean(type)
            || isPrimitive(type) || isClassType(type);
    }

    private static boolean isClassType(Class<?> type) {
        return "java.lang.Class".equals(type.getName());
    }

    private static boolean isPrimitive(Class<?> type) {
        return type != null && type.isPrimitive();
    }

    private static boolean isBoolean(Class<?> type) {
        return Boolean.class.isAssignableFrom(type);
    }

    private static boolean isNumber(Class<?> type) {
        return Number.class.isAssignableFrom(type);
    }

    private static boolean isDate(Class<?> type) {
        return Date.class.isAssignableFrom(type);
    }

    private static boolean isString(Class<?> type) {
        return String.class.isAssignableFrom(type);
    }

}
