package com.library.spring;

import java.util.regex.Pattern;

public class Constant {
    public static Pattern PATTERN_FOR_N = Pattern.compile("^([A-Z])?(a-z){3,125}$");
}
