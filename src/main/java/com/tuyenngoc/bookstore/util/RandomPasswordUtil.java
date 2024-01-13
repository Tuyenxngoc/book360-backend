package com.tuyenngoc.bookstore.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomPasswordUtil {

    public static String random() {
        return RandomStringUtils.random(6, true, true);
    }
}
