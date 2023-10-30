package service;

import model.Player;

public interface AuthorizationService {

    Player logIn(String name,String password);
    Player playerRegister(String name, String password);

    boolean logout(Player player);



}
