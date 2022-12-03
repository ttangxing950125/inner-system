package com.deloitte.data.platform.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author fangliu
 */
@Data
@Accessors(chain = true)
public class JwtUser {

    private boolean valid;
    private Long userId;
    private String role;

    public JwtUser() {
        this.valid = false;
    }
}
