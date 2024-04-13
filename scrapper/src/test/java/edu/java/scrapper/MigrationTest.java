package edu.java.scrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MigrationTest extends IntegrationEnvironment {
    @Test
    @SneakyThrows
    void linkTableCreateTest() {
        List<String> columns = List.of("id", "link", "updated_at", "checked_at", "status");
        String sqlQuery =
            "SELECT column_name FROM information_schema.columns WHERE table_name = 'link';";
        Connection connection = POSTGRES.createConnection("");
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        ResultSet result = statement.executeQuery();
        List<String> values = new ArrayList<>();
        while (result.next()) {
            values.add(result.getString("column_name"));
        }
        assertThat(values).containsExactlyInAnyOrderElementsOf(columns);
    }

    @Test
    @SneakyThrows
    void chatTableCreateTest() {
        List<String> columns = List.of("id", "status");
        String sqlQuery =
            "SELECT column_name FROM information_schema.columns WHERE table_name = 'chat';";
        Connection connection = POSTGRES.createConnection("");
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        ResultSet result = statement.executeQuery();
        List<String> values = new ArrayList<>();
        while (result.next()) {
            values.add(result.getString("column_name"));
        }
        assertThat(values).containsExactlyInAnyOrderElementsOf(columns);
    }
}
