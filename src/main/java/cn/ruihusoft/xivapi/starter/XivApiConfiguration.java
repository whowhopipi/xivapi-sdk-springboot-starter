package cn.ruihusoft.xivapi.starter;

import cn.ruihusoft.xivapi.starter.core.SSLSocketClient;
import cn.ruihusoft.xviapi.api.GameDataApi;
import cn.ruihusoft.xviapi.api.GameDataDetailApi;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@EnableAutoConfiguration
@Configuration
@ConditionalOnProperty(prefix = "cn.ruihusoft.xivapi", name = "enabled", havingValue = "true", matchIfMissing = false)
public class XivApiConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "cn.ruihusoft.xivapi")
    public XivApiProperties properties() {
        return new XivApiProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public Encoder encoder() {
        return new JacksonEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public Decoder decoder() {
        return new JacksonDecoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public feign.okhttp.OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (!properties().isSsl()) {
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager());
            builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        }

        return new feign.okhttp.OkHttpClient(builder.build());
    }

    @Bean
    @ConditionalOnMissingBean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .client(okHttpClient())
                .encoder(encoder())
                .decoder(decoder());
    }

    //    @Bean
    public XivApiRegistar xivApiRegistar() {
        return new XivApiRegistar();
    }

    @Bean
    @ConditionalOnMissingBean
    public GameDataApi gameDataApi() {
        return feignBuilder().target(GameDataApi.class, properties().getUrl());
    }

    @Bean
    @ConditionalOnMissingBean
    public GameDataDetailApi gameDataDetailApi() {
        return feignBuilder().target(GameDataDetailApi.class, properties().getUrl());
    }
}
