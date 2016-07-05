package com.ellenBusy.controller;

import com.ellenBusy.aspect.LogAspect;
import com.ellenBusy.service.TouTiaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BusyEllen on 16/7/2.
 */
@Controller
public class IndexController {
    @Autowired
    private TouTiaoService touTiaoService;//被Autowired修饰的成员变量,不用再new出来即可使用
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpSession session) {
        logger.info("log index");
        return  "Hello Ellen!" + session.getAttribute("msg") + "<br>" +touTiaoService.say();
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String request(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int usrId,
                          @RequestParam(value = "key", defaultValue = "1") int key,
                          @RequestParam(value = "value", defaultValue = "value") String value) {
        return String.format("{%s},{%d},{%d},{%s}",groupId, usrId, key, value);

    }

    @RequestMapping(value = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames =  request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }

        for(Cookie cookie : request.getCookies()) {

            sb.append("Cookie:");
            sb.append(cookie.getName() );
            sb.append(cookie.getValue()+ "<br>");
        }

        sb.append("getMethod:" + request.getMethod() + "<br>");
        sb.append("getPathInfo:" + request.getPathInfo() + "<br>");
        sb.append("getQueryString:" + request.getQueryString() + "<br>");
        sb.append("getRequestURI:" + request.getRequestURI() + "<br>");
        sb.append("getId:" + session.getId());

        return sb.toString();

    }

    @RequestMapping("/response")
    @ResponseBody
    public String response(@CookieValue( name = "elleBusy" ,defaultValue ="yes" ) String ellenBusy,
                           @RequestParam(name = "name", defaultValue = "name") String name,
                           @RequestParam(name = "value", defaultValue = "this is value") String value,
                           HttpServletResponse response) {
        response.addCookie(new Cookie(name, value));//Cookie下发
        response.addHeader(name, value);//header设置
        //ellenBusy = "hehe";
        return "elleBusy from Cookie:" + ellenBusy;//页面内容返回

    }

    @RequestMapping("/vm")
    public String news (Model model) {
        model.addAttribute("value", "this is value");
        model.addAttribute("key", "this is key");
       // model.addAttribute("user", new User("czx", 24));//这个user能用IOC吗?
        model.addAttribute("bodyContent", "this is a body");
        model.addAttribute("hello", "this is a hello");
        String[] allColors = new String[] {"red", "black", "blue" };
        model.addAttribute("allColors", allColors);
        Map<String, String> map = new HashMap<String, String>();
        for(int i=0; i<7; i++) {
            map.put(String.valueOf(i), String.valueOf(i*i));
        }
        model.addAttribute("map", map);
        return "news";//这个news就是模板的文件名

    }

    @RequestMapping("/redirect/{code}")
    //跳转的时候不能加@ResponseBody

    public RedirectView redirect(@PathVariable(value = "code") int code,//返回值是RedireView类型
                           HttpSession session) {
        RedirectView red = new RedirectView("/", true);
        if (code == 301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        session.setAttribute("msg", "this message is from redirect");
        return red;
        //session.setAttribute("msg", "This message is from redirect");//可以往session里写点属性

        //return "redirect:/";//这个方法都是302跳转

    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key", required = true) String key) {
        if ("admin".equals(key)) {
            return "hello admin";
        }
        throw new IllegalArgumentException("Key 错误");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error:" + e.getMessage();
    }

}
