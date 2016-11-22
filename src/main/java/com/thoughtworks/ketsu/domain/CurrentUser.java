package com.thoughtworks.ketsu.domain;

import java.util.Optional;

/**
 * Created by zyongliu on 22/11/16.
 */
public interface CurrentUser {
    Optional<User> getCurrentUser();
}
