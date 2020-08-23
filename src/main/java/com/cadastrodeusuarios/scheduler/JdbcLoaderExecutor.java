package com.cadastrodeusuarios.scheduler;

import com.cadastrodeusuarios.entity.User;
import com.cadastrodeusuarios.scheduler.validator.*;
import com.cadastrodeusuarios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class JdbcLoaderExecutor extends LoaderExecutor {

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;
    @Autowired
    private UserService userService;

    @Override
    public void execute(String intputPath, String outputPath, Set<String> readFiles) {

        long initialTime = System.currentTimeMillis();

        File dir = new File(intputPath);
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {

            for (File child : directoryListing) {

                if (readFiles.contains(child.getName())) {
                    continue;
                }
                readFile(intputPath, outputPath, readFiles, child.getName());
            }
        }
        System.out.println("JPA implementation executed in " + (System.currentTimeMillis() - initialTime) + " ms");
    }

    private void readFile(String inputPath, String outputPath, Set<String> readFiles, String name) {

        File inputF = new File(inputPath.concat("/").concat(name));
        File outputF = new File(outputPath.concat("/").concat(name));

        PrintWriter writer = null;
        InputStream inputFS = null;
        try {

            if(outputF.createNewFile()) {
                writer = new PrintWriter(outputF);
                inputFS = new FileInputStream(inputF);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

                int currentLine = 1;
                String line;
                br.readLine();
                List<User> users = new LinkedList<>();
                while ((line = br.readLine()) != null) {

                    currentLine++;
                    if (validate(currentLine, line, writer)) {
                        users.add(mapToItem(line));
                    }
                }
                batchInsert(users);
            }
        } catch (IOException e) {
            System.out.println("Error creating output file: " + e.getMessage());
        } finally {

            if (writer != null) writer.close();
            if (inputFS != null) {
                try {
                    inputFS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        readFiles.add(name);
    }

    private void batchInsert(List<User> users) {

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {

            connection = getDatabaseConnection();
            connection.setAutoCommit(true);

            String compiledQuery = "INSERT INTO USER(COMPANY_ID, EMAIL, BIRTHDATE)" +
                    " VALUES" + "(?, ?, ?)";
            preparedStatement = connection.prepareStatement(compiledQuery);

            for (User user : users) {

                processUser(preparedStatement, user);
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error while executing batch insert: " + e.getMessage());
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean validate(int currentLine, String line, PrintWriter writer) {

        CsvFileLineValidator validator = new CsvFileLineHasThreeFields();
        validator.linkWith(new CsvFileLineHasCompanyId())
                .linkWith(new CsvFileLineCompanyIsAllowed())
                .linkWith(new CsvFileLineHasValidBirthdate())
                .linkWith(new CsvFileLineHasValidEmail());

        List<String> errors = new LinkedList<>();
        validator.check(errors, line);
        if (errors.isEmpty()) {

            return true;
        } else {

            String outputError = errors.stream()
                    .reduce((value, aggregator) -> value + ", " + aggregator).get();
            writer.println("Error at line " + currentLine + ": " + outputError);
            return false;
        }
    }

    private void processUser(PreparedStatement preparedStatement, User user) {

        try {

            preparedStatement.setInt(1, user.getCompanyId());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setDate(3, java.sql.Date.valueOf(user.getBirthdate()));
            preparedStatement.addBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User mapToItem (String line) {

        String[] p = line.split(";");
        User user = new User();
        user.setCompanyId(Integer.parseInt(p[0]));
        user.setEmail(p[1]);
        user.setBirthdate(LocalDate.parse(p[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return user;
    }

    private Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
    }
}
