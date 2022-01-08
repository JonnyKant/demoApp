package com.modeling.demo.repos;

import com.modeling.demo.domains.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepos extends CrudRepository<User,Long> {
    com.modeling.demo.domains.User findByEmail(String email);
}
