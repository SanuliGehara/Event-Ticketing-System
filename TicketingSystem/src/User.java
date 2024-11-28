public abstract class User implements Runnable{
    private final String username;
    private final String password;
    private final UserType userType;

    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }
}
