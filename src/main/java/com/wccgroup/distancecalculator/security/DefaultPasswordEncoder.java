package com.wccgroup.distancecalculator.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordEncoder extends BCryptPasswordEncoder {

}
