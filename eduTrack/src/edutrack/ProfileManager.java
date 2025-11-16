/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrack;

/**
 *
 * @author evan2
 */

//Manages user profiles and login sessions.

public class ProfileManager {
    private User[] userList = new User[100];
    private int userCount = 0;
    private User currentUser;

    public User createProfile(String name, String email, String password, String role) {
        // Anonymous subclass of User
        User newUser = new User(name, email, password) {};
        userList[userCount++] = newUser;
        return newUser;
    }

    public boolean login(String email, String password) {
        for (int i = 0; i < userCount; i++) {
            if (userList[i].getEmail().equals(email) && userList[i].getPassword().equals(password)) {
                currentUser = userList[i];
                currentUser.login();
                return true;
            }
        }
        return false;
    }

    public void logout() {
        if (currentUser != null) {
            currentUser.logout();
            currentUser = null;
        }
    }

    public User getUserByEmail(String email) {
        for (int i = 0; i < userCount; i++) {
            if (userList[i].getEmail().equals(email)) {
                return userList[i];
            }
        }
        return null;
    }
}

