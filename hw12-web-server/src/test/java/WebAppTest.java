import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw12.WebApp;
import ru.otus.javadeveloper.hw12.dao.executor.DbHibernateExecutorHibernateImpl;
import ru.otus.javadeveloper.hw12.dao.model.User;
import ru.otus.javadeveloper.hw12.service.DBService;
import ru.otus.javadeveloper.hw12.service.DbHibernateServiceImpl;
import utils.DefaultBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.*;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class WebAppTest {
    private final static int PORT = 8081;
    private static final DBService<User> dbService = new DbHibernateServiceImpl<>(User.class, new DbHibernateExecutorHibernateImpl<>());
    private final static User defaultTestUser = DefaultBuilder.createDefaultTestUser();
    private static final String LOGIN = "ivan";
    private static final String PASSWORD = "pass";
    private static Server server;

    @AfterEach
    public void stopServer() throws Exception {
        server.stop();
    }

    @BeforeEach
    public void startServer() throws Exception {
        WebApp appl = new WebApp();
        server = appl.createServer(PORT);
        server.start();
    }

    private URL makeUrl(String part) throws MalformedURLException {
        return new URL("http://localhost:" + PORT + part);
    }

    @Test
    public void authentificatedAdminShouldGetUserList() throws IOException {
        dbService.save(defaultTestUser);
        HttpURLConnection connection = (HttpURLConnection) makeUrl("/user").openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization",
                getBase64HeaderAuthValue());
        assertEquals(HttpStatus.OK_200, connection.getResponseCode());

        String responseBody = getServerResponse(connection);
        final Type RESULT_TYPE = new TypeToken<List<User>>() {
        }.getType();
        assertEquals(Collections.singletonList(defaultTestUser), new Gson().fromJson(responseBody, RESULT_TYPE));
    }

    @Test
    public void authentificatedAdminShouldCreateNewUser() throws IOException {
        User defaultTestUser = DefaultBuilder.createDefaultTestUser();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String data = gson.toJson(defaultTestUser, User.class);
        HttpURLConnection connection = (HttpURLConnection) makeUrl("/user").openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization",
                getBase64HeaderAuthValue());

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());
        outputStream.close();
        assertEquals(HttpStatus.OK_200, connection.getResponseCode());

        String responseBody = getServerResponse(connection);
        User actual = gson.fromJson(responseBody, User.class);
        assertEquals(DefaultBuilder.createDefaultTestUser(), actual);
        assertEquals(WebAppTest.defaultTestUser, dbService.get(actual.getId()).get());
    }

    private String getServerResponse(HttpURLConnection connection) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    private String getBase64HeaderAuthValue() {
        return "Basic " + new String(Base64.getEncoder().encode((LOGIN + ":" + PASSWORD).getBytes()));
    }

    @Test
    public void userWithAdminLoginAndPasswordReceiveSuccessResponse() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) makeUrl("/user").openConnection();
        connection.setRequestMethod("GET");
        connection.setAuthenticator(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(LOGIN, PASSWORD.toCharArray());
            }
        });

        assertEquals(HttpStatus.OK_200, connection.getResponseCode());
    }
}