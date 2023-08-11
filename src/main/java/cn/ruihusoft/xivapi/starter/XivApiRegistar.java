package cn.ruihusoft.xivapi.starter;

import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

public class XivApiRegistar implements BeanDefinitionRegistryPostProcessor ,ResourceLoaderAware{

    @Setter
    private String basePackage = "cn.ruihusoft.xviapi.api";

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        XivApiClasspathScanner scanner = new XivApiClasspathScanner(beanDefinitionRegistry);

        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }

        scanner.registerFilters();

        scanner.scan(basePackage);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // do nothing
    }
}
