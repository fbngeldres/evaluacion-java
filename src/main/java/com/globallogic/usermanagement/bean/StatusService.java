package com.globallogic.usermanagement.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusService {

    private StatusServiceEnum statusServiceEnum;

    private Object message;

}
