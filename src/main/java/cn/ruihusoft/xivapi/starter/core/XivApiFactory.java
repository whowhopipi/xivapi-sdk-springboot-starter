package cn.ruihusoft.xivapi.starter.core;

import cn.ruihusoft.xivapi.starter.XivApiProperties;
import feign.Feign;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

@AllArgsConstructor
public class XivApiFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceClass;
    private Feign.Builder builder;
    private XivApiProperties properties;

    @Override
    public T getObject() throws Exception {
        return builder.target(interfaceClass, properties.getUrl());
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }
}
