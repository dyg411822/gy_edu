package com.scb.common.util;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by
 */
@Deprecated
public class ObjectUtil {


    private static Set<String> basic = new HashSet<String>();

    static {
        basic.add("ver");
        basic.add("ret");
        basic.add("errmsg");
        basic.add("errcode");
        basic.add("data");
    }

    /**
     * Object 2 ByteArray
     *
     * @param obj
     * @return
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
        } finally {
            try {
                if (oos != null)
                    oos.close();
            } catch (IOException e) {
            }
            try {
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
            }
        }
        return bytes;
    }

 /*   public static <T> byte[] object2ByteArrayByKryo(T t) {
        Kryo kryo = new Kryo();
        Output output = new Output(1);
        kryo.writeObjectOrNull(output, t, t.getClass());
        return output.getBuffer();
    }*/


    /**
     * ByteArray to Object
     *
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException e) {
            }
            try {
                if (bis != null)
                    bis.close();
            } catch (IOException e) {
            }
        }

        return obj;
    }

    /**
     * 将对象转换为xml
     *
     * @param object
     * @return
     */
    public static String objectToXMLString(Object object) {
        Map<String, Object> map = objectToMap(object);
        return mapToXMLString(map);
    }

    /**
     * 将Map转换为xml
     *
     * @param map
     * @return
     */
    public static String mapToXMLString(Map<String, Object> map) {
        StringBuffer buffer = new StringBuffer("<xml>");
        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i].toString();
//            Object value = map.get(key);
//            if (!StringUtils.isEmpty(value.toString())) {
//                buffer.append("<" + key + ">" + value + "</" + key + ">");
//            }
        }
        buffer.append("</xml>");
        return buffer.toString();
    }


    /**
     * 将对象转换为map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> objectToMap(Object object) {
        if (object == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                field.setAccessible(true);
                obj = field.get(object);
                if (obj != null) {
                    map.put(field.getName(), obj.toString());
                }
                obj = field.get(object);
                if (!isJavaBasicObject(field.getType()) && !field.getType().isPrimitive() && obj != null) {
                    obj = objectToMapString(field.get(object));
                    if (obj != null) {
                        map.put(field.getName(), objectToMapString(field.get(object)));
                    } else {
                        map.put(field.getName(), "");
                    }
                } else {
                    if (obj != null) {
                        map.put(field.getName(), obj.toString());
                    } else {
                        map.put(field.getName(), "");
                    }
                }
                field.setAccessible(false);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 将对象转换为map
     *
     * @param object
     * @return
     */
    public static Map<String, String> objectToMapString(Object object) {
        if (object == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                field.setAccessible(true);
                obj = field.get(object);
                if (obj != null) {
                    if (obj instanceof Date) {
                        map.put(field.getName(), ((Date) obj).getTime() + "");
                    } else {
                        map.put(field.getName(), obj.toString());

                    }
                }
                field.setAccessible(false);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 将map转化为对象
     * 如果对象中嵌套对象  不要使用这种方法！！！！
     *
     * @param map
     * @param beanClass
     * @return
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws IllegalAccessException, InstantiationException {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }

    /**
     * xml to map xml <node><key label="key1">value1</key><key
     * label="key2">value2</key>......</node>
     *
     * @param xml
     * @return
     */
    public static Map<String, String> convertXmlToMap(String xml) {
        if (xml == null) {
            return null;
        }
        try {
            Map<String, String> map = new HashMap<String, String>();
            Document document = DocumentHelper.parseText(xml);
            Element nodeElement = document.getRootElement();
            List node = nodeElement.elements();
            for (Iterator it = node.iterator(); it.hasNext(); ) {
                Element elm = (Element) it.next();
                if (elm.attributeValue("label") == null) {
                    map.put(elm.getQualifiedName(), elm.getText());
                } else {
                    map.put(elm.attributeValue("label"), elm.getText());
                }
            }
            return map;
        } catch (Exception e) {
            //  logger.error(e.toString());
        }
        return null;
    }

    public static String getFullStackTraceToString(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.toString();
    }

    public static String getFullStackTraceToString(Throwable exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.toString();
    }

    private static boolean isJavaBasicObject(Object object) {
        return object == Integer.class || object == String.class ||
            object == Double.class || object == Float.class || object == Long.class
            || object == Boolean.class || object == Date.class;
    }


    public static void main11(String[] args) {
        String xml = "<xml>\n" +
            "   <return_code><![CDATA[SUCCESS]]></return_code>\n" +
            "   <return_msg><![CDATA[OK]]></return_msg>\n" +
            "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
            "   <mch_id><![CDATA[10000100]]></mch_id>\n" +
            "   <nonce_str><![CDATA[NfsMFbUFpdbEhPXP]]></nonce_str>\n" +
            "   <sign><![CDATA[B7274EB9F8925EB93100DD2085FA56C0]]></sign>\n" +
            "   <result_code><![CDATA[SUCCESS]]></result_code>\n" +
            "   <transaction_id><![CDATA[1008450740201411110005820873]]></transaction_id>\n" +
            "   <out_trade_no><![CDATA[1415757673]]></out_trade_no>\n" +
            "   <out_refund_no><![CDATA[1415701182]]></out_refund_no>\n" +
            "   <refund_id><![CDATA[2008450740201411110000174436]]></refund_id>\n" +
            "   <refund_channel><![CDATA[]]></refund_channel>\n" +
            "   <refund_fee>1</refund_fee>\n" +
            "   <coupon_refund_fee>0</coupon_refund_fee>\n" +
            "</xml>";

        Map<String, String> map = convertXmlToMap(xml);

        System.out.println(map.get("return_code"));
        System.out.println(map.get("appid"));
        System.out.println(map.get("mch_id"));
        System.out.println(map.get("sign"));
        System.out.println(map.get("transaction_id"));

    }

    //获取json字符串红得指定key的值
    //2015-11-30
    //kdq
//    public static int getIntJsonValue(String json, String key) {
//        if ((json == null) || (key == null) || json.equals("") || key.equals("")) {
//            return 0;
//        } else {
//            try {
//                Map map = JSONObject.parseObject(json);
//                if (map != null) {
//                    return (Integer) map.get(key);
//                } else {
//                    return 0;
//                }
//            } catch (Exception e) {
//                return -1;
//            }
//        }
//    }

    //获取json字符串红得指定key的值
    //2015-11-30
    //kdq
//    public static String getStrJsonValue(String json, String key) {
//        if ((json == null) || (key == null) || json.equals("") || key.equals("")) {
//            return "";
//        } else {
//            try {
//                Map map = JSONObject.parseObject(json);
//                if (map != null) {
//                    return map.get(key) + "";
//                } else {
//                    return "";
//                }
//            } catch (Exception e) {
//                return "";
//            }
//        }
//    }

    //2015-11-30
    //kdq
    //从json字符串的数据中查找key的值，然后改变key的值
//    public static String getChangeJsonValue(String json, HashMap mapadd) {
//        Map map = JSONObject.parseObject(json);
//        Iterator iter = mapadd.entrySet().iterator();
//        while (iter.hasNext()) {
//            Map.Entry entry = (Map.Entry) iter.next();
//            Object key = entry.getKey();
//            Object val = entry.getValue();
//
//            map.remove(key);
//            if (key != null && !key.equals("")) {
//                map.put(key, val);
//            }
//        }
//        return JSONObject.toJSONString(map);
//    }


    /**
     * @param t        需要过滤字段的对象
     * @param includes 包含的属性, 如果excludes同时不为空, excludes不会生效
     * @param excludes 当includes为空时excludes生效
     * @param <T>
     * @return JSONString
     * @author songzj
     */
/*    public static <T> T excludeFieldToJson(T t, String[] includes, String[] excludes) {
        Gson gson = getGson(includes, excludes);
        return (T) gson.fromJson(gson.toJsonTree(t), t.getClass());
    }

    public static String object2JsonString(Object o) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(o);
    }

    public static Gson getGson(final String[] includes, final String[] excludes) {
        ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {

                if (basic.contains(fieldAttributes.getName())) {
                    return false;
                }

                if (includes != null && includes.length > 0) {
                    List<String> includeList = Arrays.asList(includes);
                    if (!includeList.contains(fieldAttributes.getName())) {
                        logger.debug("include 过滤该属性 === > " + "\t" + fieldAttributes.getName() + "\t\t" + Arrays.toString(includes));
                        return true;
                    }
                } else if (excludes != null && excludes.length > 0) {
                    List<String> excludeList = Arrays.asList(excludes);
                    if (excludeList.contains(fieldAttributes.getName())) { //不包含该属性
                        logger.debug("exclude 过滤该属性 === > " + "\t" + fieldAttributes.getName() + "\t\t" + Arrays.toString(excludes));
                        return true;
                    }
                }
                logger.debug("包含该属性 ===> " + fieldAttributes.getName() + "\t\t");
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        };

        return new GsonBuilder().addSerializationExclusionStrategy(exclusionStrategy).create();
    }*/
    public static boolean isObjectEmpty(Object... object) {
        if (object == null) {
            return true;
        }

        Object[] os = object.clone();
        for (Object o : os) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotObjectEmpty(Object... object) {
        if (object == null) {
            return false;
        }

        Object[] os = object.clone();
        for (Object o : os) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

//    public static void main(String[] args) {
        //System.err.println(getStrJsonValue("{\"state\": \"SUCCESS\",\"title\": \"1446891316306beij.jpg\",\"original\": \"446891316306beij.jpg\",\"type\": \".jpg\",\"url\": \"http://quncao.b0.upaiyun.com/1446891316306beij.jpg\",\"size\": \"73602\"}", "url"));

//        String ss = "http://quncao.b0.upaiyun.com/1446891316306beij.jpg";
//        System.out.println(ss.split("com/")[1]);


//        System.err.println("==" + getStrJsonValue("{\"contactName\":false,\"contactMobile\":false,\"idCard\":false,\"gender\":false,\"canReplace\":false,\"maxReplace\":0,\"isPrivate\":0,\"canUseCredit\":1,\"medalNum\":3,\"limitMedalNum\":3}", "medalNum"));
//        Long l = 0L;
//        System.out.println(l.equals(0L));
//    }


}
