package cn.ruihusoft.xivapi.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(XivApiRegistar.class)
@Documented
public @interface XivApiScanner {
}
