package ru.javawebinar.topjava;

public class Profiles {
    public static final String
            POSTGRES = "db-postgres",
            HSQLDB = "db-hsqldb",
            HEROKU = "db-heroku",

    JDBC = "repo-jdbc",
            JPA = "repo-jpa",
            DATAJPA = "repo-datajpa";

    public static final String DB_IMPLEMENTATION = DATAJPA;

    public static String getActiveDbProfile() {
        try {
            Class.forName("org.postgresql.Driver");
            return Profiles.POSTGRES;
        } catch (ClassNotFoundException ex) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                return Profiles.HSQLDB;
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not resolve DB profile");
            }
        }
    }
}
