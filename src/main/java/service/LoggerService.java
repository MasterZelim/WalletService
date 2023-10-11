package service;

import model.Action;
public class LoggerService {
    public void info(Action action){
        System.out.println(action.name() + " прошло успешно!");
    }
    public void info(){
        System.out.println("Операция успешна");
    }
    public void infoError(Action action){
        System.out.println("Операция: " + action + " неудачна!");
    }
}
