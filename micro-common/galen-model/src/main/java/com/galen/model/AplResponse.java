package com.galen.model;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhaoxi
 * @date 2019/1/28 11:05
 * 自定义响应数据的结构
 */
public class AplResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public AplResponse() {
        this.put("status", 200);
        this.put("msg", "OK");
    }

    public AplResponse(Integer status, String msg, Object bean) {
        this.put("status", status);
        this.put("msg", msg);
        this.put("bean", bean);
    }

    public AplResponse(long total, Object bean) {
        this.put("status", 200);
        this.put("msg", "OK");
        this.put("total", total);
        this.put("bean", bean);
    }

    public static AplResponse build(Integer status, String msg, Object bean) {
        return new AplResponse(status, msg, bean);
    }

    public static AplResponse build(Integer status, String msg) {
        return new AplResponse(status, msg, null);
    }

    public Integer getStatus() {
        return (Integer) this.get("status");
    }

    public void setStatus(Integer status) {
        this.put("status", status);
    }

    public String getMsg() {
        return (String) this.get("msg");
    }

    public void setMsg(String msg) {
        this.put("msg", msg);
    }

    public Object getBean() {
        return this.get("bean");
    }

    public void setBean(Object bean) {
        this.put("bean", bean);
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param clazz    TaotaoResult中的object类型
     * @return
     */
    public static AplResponse formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, AplResponse.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("bean");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static AplResponse format(String json) {
        try {
            return MAPPER.readValue(json, AplResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static AplResponse formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("bean");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}

