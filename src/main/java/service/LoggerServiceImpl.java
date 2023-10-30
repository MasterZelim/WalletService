package service;

import model.Action;
public class LoggerServiceImpl implements LoggerService{

    @Override
    public void info(Action action){
        System.out.println("Действие: " + action.name() + " прошло успешно!");
    }
    @Override
    public void info(){
        System.out.println("Операция прошла успешно");
    }

    @Override
    public void infoError(Action action){
        System.out.println("Операция: " + action.name() + " прошла неудачно!");
    }
}
