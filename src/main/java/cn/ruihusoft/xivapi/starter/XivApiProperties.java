package cn.ruihusoft.xivapi.starter;

import lombok.Data;

@Data
public class XivApiProperties {

    private boolean enabled = true;

    private boolean ssl = false;

    private String url = "https://xivapi.com/";

}
