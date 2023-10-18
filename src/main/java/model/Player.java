package model;

import lombok.Builder;
import lombok.Data;

public class Player {
    private long id;
    private String login;
    private String password;

    public Player(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { // проверка на идентичность
            return true;
        }
        if (!(obj instanceof Player)) { // проверка типа объекта
            return false;
        }
        Player other = (Player) obj; // приведение объекта к типу Player
        return this.id ==(other.id) && this.login.equals(other.login)&&this.password.equals(other.password); // сравнение содержимого
    }
}
