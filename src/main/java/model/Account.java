package model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Account {
    private Player player;
    private double balance;
}
