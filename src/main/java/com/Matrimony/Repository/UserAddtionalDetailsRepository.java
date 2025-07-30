package com.Matrimony.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Matrimony.Entities.User;
import com.Matrimony.Entities.UserAddtionalDetails;

@Repository
public interface UserAddtionalDetailsRepository extends JpaRepository<UserAddtionalDetails, Long> {

	UserAddtionalDetails findByUser(User user);
}
