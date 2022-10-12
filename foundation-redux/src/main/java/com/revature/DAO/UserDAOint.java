package com.revature.DAO;

//import java.util.List;
import com.revature.models.User; //create me!

public interface UserDAOint {
    //interfaces help with abstraction, and here we are storing the method signatures implmented in our userDAO proper.
    User createUser(String first_name, String last_name, String username, String email, String password, int role_num);
    User getByUsername(String username);
}
