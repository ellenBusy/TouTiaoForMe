package com.ellenBusy.service;

import org.springframework.stereotype.Service;

/**
 * Created by BusyEllen on 16/7/2.
 */
@Service//被Service注解修饰的类,在启动后会自动生成该类的一个对象
public class TouTiaoService {
    public String say() {
        return "this is from TouTiaoService";
    }
}
