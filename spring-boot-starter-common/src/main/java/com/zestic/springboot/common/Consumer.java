package com.zestic.springboot.common;

import com.zestic.common.entity.Message;

public interface Consumer {

    Message receive();
}
