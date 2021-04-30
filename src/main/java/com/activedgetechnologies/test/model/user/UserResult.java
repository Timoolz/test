package com.activedgetechnologies.test.model.user;


import com.activedgetechnologies.test.model.TokenInfo;
import com.activedgetechnologies.test.model.entity.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private TokenInfo tokenInfo;
    private User user;
}
