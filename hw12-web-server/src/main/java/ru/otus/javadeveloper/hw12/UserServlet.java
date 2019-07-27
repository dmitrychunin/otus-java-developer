package ru.otus.javadeveloper.hw12;

import com.google.gson.Gson;
import ru.otus.javadeveloper.hw12.dao.executor.DbHibernateExecutorHibernateImpl;
import ru.otus.javadeveloper.hw12.dao.model.User;
import ru.otus.javadeveloper.hw12.service.DBService;
import ru.otus.javadeveloper.hw12.service.DbHibernateServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class UserServlet extends HttpServlet {
    private final DBService<User> dbService = new DbHibernateServiceImpl<>(User.class, new DbHibernateExecutorHibernateImpl<>());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        List<User> all = dbService.getAll();
        all.stream().flatMap(user -> user.getPhoneDataSets().stream()).forEach(phoneDataSet -> phoneDataSet.setUser(null));
        String resultAsString = gson.toJson(all);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resultAsString);
        printWriter.flush();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String collect = request.getReader().lines().collect(Collectors.joining());
        Gson gson = new Gson();
        User user = gson.fromJson(collect, User.class);
        user.getPhoneDataSets().stream().forEach(phoneDataSet -> phoneDataSet.setUser(user));
        User saved = dbService.save(user);
        saved.getPhoneDataSets().stream().forEach(phoneDataSet -> phoneDataSet.setUser(null));

        String resultAsString = gson.toJson(saved);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resultAsString);
        printWriter.flush();
    }
}
