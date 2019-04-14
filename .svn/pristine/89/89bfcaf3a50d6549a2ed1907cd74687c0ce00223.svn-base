package com.xiu.jd.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.jd.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {  

	private final static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
    public boolean isLogin(String username, String password) { 
    	
    	logger.debug("username: {}, password: {}.", username, password);
    	
        if("admin".equals(username) && "admin".equals(password))  
            return true;  
        else   
            return false;
    }
}
