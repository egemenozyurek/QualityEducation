/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.userauthorization;

/**
 *
 * @author evan02
 */
public class ProfileManager {
    private User[] userList = new User[100];
    private int userCount = 0;
    private User currentUser;
    
    public User createProfile(String name, String email, String password, String role){
        //checking for duplicate email
        if (getUserByEmail(email) != null) {
            return null;
        }
        if (userCount >= userList.length) {
            System.out.println("User list is full!");
            return null;
        }
        User newUser;
        if ("Teacher".equalsIgnoreCase(role)) {
            newUser = new Teacher(name, email, password, "General");
        } 
        else {
            newUser = new Student(name, email, password);
        }
        userList[userCount++] = newUser;
        return newUser;
    }
    
    //returning the logged-in user or null if invalid credentials found
    public User login(String email, String password) {
        for (int i = 0; i < userCount; i++) {
            User u = userList[i];

            if (u.getEmail().equalsIgnoreCase(email)
                    && u.getPassword().equals(password)) {

                currentUser = u;
                currentUser.login();
                return currentUser;
            }
        }
        return null;
    }
    public void logout() {
        if (currentUser != null) {
            currentUser.logout();
            currentUser = null;
        }
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public User getUserByEmail(String email) {
        for (int i = 0; i < userCount; i++) {
            if (userList[i].getEmail().equalsIgnoreCase(email)) {
                return userList[i];
            }
        }
        return null;
    }
    public int getUserCount() {
        return userCount;
    }  
}
