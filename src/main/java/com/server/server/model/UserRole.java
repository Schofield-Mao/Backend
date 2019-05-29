package com.server.server.model;

import com.server.server.model.meta.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRole {
    long userId;
    Role role;
}
