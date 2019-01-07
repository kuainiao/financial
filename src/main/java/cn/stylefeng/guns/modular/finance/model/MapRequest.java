package cn.stylefeng.guns.modular.finance.model;

import java.util.Map;

public class MapRequest {
    private Map<String,String> data;
    public Map<String,String> getMap(){
        return data;
    }
    public void setMap(Map<String,String> data) {
        this.data = data;
    }
}
