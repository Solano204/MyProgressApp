package com.example.myprogress.app.SqlTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.myprogress.app.HandleProcedures.ProcedureRepository;
import com.example.myprogress.app.HandleProcedures.ProcedureRepositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Here I specify that any data base will be
                                                                            // replaced by temporary database in the
                                                                            // testing
@ActiveProfiles("test")
public class TestProcedure {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProcedureRepository procedureRepository = new ProcedureRepositoryImpl();

    private final String idUser = "Carlos";
    private final String password = "123456";
    private final String emailUser = "carlosjosue@gmail.com";
    private final String typeAuthentication = "App";
    private final String newidUser = "Carlos";
    private final String newpassword = "123456";
    private final String newemailUser = "carlosjosue@gmail.com";
    private final String newtypeAuthentication = "App";

    @BeforeEach
    public void setUp() {
        addUser(idUser, password, emailUser, typeAuthentication);
    }

    @Test
    public void savedUser() {
        boolean saved = addUser(newidUser, newpassword, newemailUser, newtypeAuthentication);
        assertTrue(saved);
    }

    public boolean addUser(String idUser, String password, String emailUser, String typeAuthentication) {
        String nameProcedure = "add_user_app";
        try {
            jdbcTemplate.execute("DROP ALIAS IF EXISTS add_user_app;");
        } catch (Exception e) {
            // Handle exception if needed
        }

        jdbcTemplate.execute(
                "CREATE ALIAS add_user_app FOR \"com.example.myprogress.app.TestMySql.prodedures.addUserAppProcedure\";");

                try {
                    jdbcTemplate.execute((Connection con) -> {
                        try (CallableStatement stmt = con.prepareCall("{call add_user_app(?, ?, ?, ?)}")) {
                            stmt.setString(1, idUser);
                            if (typeAuthentication.equals("App")) {
                                stmt.setString(2, password);
                            }
                            stmt.setString(3, emailUser);
                            stmt.setString(4, typeAuthentication);
                            stmt.execute();
                            return true;
                        }
                    });
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

    }

}