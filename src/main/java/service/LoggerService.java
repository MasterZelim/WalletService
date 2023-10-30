package service;

import model.Action;

public interface LoggerService {

    void info(Action action);
    void info();
    void infoError(Action action);
}
