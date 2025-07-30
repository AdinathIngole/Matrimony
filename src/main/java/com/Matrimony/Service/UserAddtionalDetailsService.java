package com.Matrimony.Service;

import com.Matrimony.Entities.User;
import com.Matrimony.Entities.UserAddtionalDetails;

public interface UserAddtionalDetailsService {

	 public void saveAdditionalDetails(User user, UserAddtionalDetails userDetails);
}
