package model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@Builder
@EqualsAndHashCode
public class Audit {

    private Long id;
    private Long playerID;
    private Action action;
    private Timestamp timestamp;


    public Audit(Long playerID, Action action){

        this.playerID = playerID;
        this.action = action;
        this.timestamp = new Timestamp(System.currentTimeMillis());

    }

    public Audit(Long id, Long playerID, Action action, Timestamp timestamp){

        this.id = id;
        this.playerID = playerID;
        this.action = action;
        this.timestamp = timestamp;
    }


}
