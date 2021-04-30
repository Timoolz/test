package com.activedgetechnologies.test.dao;



import com.activedgetechnologies.test.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends BaseRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "select * from user where deleted = false order by date_created desc", nativeQuery = true)
    Page<User> getAllUsers(Pageable pageable);

    @Query(value = "select * from user where first_name like :name or last_name like :name and deleted = false order by date_created desc", nativeQuery = true)
    Page<User> getAllUsersSearch(String name, Pageable pageable);


}
