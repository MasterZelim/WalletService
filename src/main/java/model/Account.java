package model;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Account {
    private long id;
    private Player player;
    private double balance;

    public Account(long id, Player player, double balance) {
        this.id = id;
        this.player = player;
        this.balance = balance;
    }

    public Account(Player player, double balance) {
        this.player = player;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}