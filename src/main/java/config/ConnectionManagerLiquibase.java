package config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;

public final class ConnectionManagerLiquibase {

    private ConnectionManagerLiquibase() {

    }

    public static Database getConnection(Connection connection) throws DatabaseException {

        Database database =  DatabaseFactory.getInstance().
                findCorrectDatabaseImplementation(new JdbcConnection(connection));
        database.setLiquibaseSchemaName("migration");
        return database;


    }
    public static void executeLiquibase(String path, Database database) throws LiquibaseException{
        Liquibase liquibase = new Liquibase(path, new ClassLoaderResourceAccessor(),database);
        liquibase.update();
        System.out.println("Миграции успешно выполнены!");
    }
}
