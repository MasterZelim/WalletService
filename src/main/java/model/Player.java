package model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Player {
    private Long id;
    private String name;
    private String password;

    public Player(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
