package model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Account {
    private Long id;
    private Player player;
    private float balance;

    public Account(Long id, Player player, Float balance) {
        this.id = id;
        this.player = player;
        this.balance = balance;
    }

    public Account(Player player, Float balance) {
        this.player = player;
        this.balance = balance;
    }

}
