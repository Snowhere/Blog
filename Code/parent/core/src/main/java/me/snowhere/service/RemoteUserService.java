package me.snowhere.service;

import me.snowhere.dmo.User;

import java.util.List;

public interface RemoteUserService {
    List<User> getRemoteUser();
}
