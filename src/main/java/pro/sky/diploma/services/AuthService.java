package pro.sky.diploma.services;

import pro.sky.diploma.dto.RegisterReq;
import pro.sky.diploma.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterReq registerReq, Role role);
}
