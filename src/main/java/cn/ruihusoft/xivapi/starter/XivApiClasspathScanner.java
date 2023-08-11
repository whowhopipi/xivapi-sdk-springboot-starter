package cn.ruihusoft.xivapi.starter;

import cn.ruihusoft.xivapi.starter.core.XivApiFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Set;

public class XivApiClasspathScanner extends ClassPathBeanDefinitionScanner {

    public XivApiClasspathScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilters() {
        addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> holders = super.doScan(basePackages);

        for (BeanDefinitionHolder holder : holders) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

            //拿到接口的全路径名称
            String beanClassName = definition.getBeanClassName();

            try {
                definition.getPropertyValues().add("interfaceClass", Class.forName(beanClassName));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //设置Calss 即代理工厂
            definition.setBeanClass(XivApiFactory.class);
            //按 照查找Bean的Class的类型
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        }

        return holders;
    }

}
