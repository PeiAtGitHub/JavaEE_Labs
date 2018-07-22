package javaeetutorial.interceptor.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import lombok.Getter;

@Stateless
@Named
public class HelloBean {

    @Getter
    protected String name;

    @Interceptors(HelloInterceptor.class)
    public void setName(String name) {
        this.name = name;
    }

}
