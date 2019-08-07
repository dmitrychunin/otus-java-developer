package ru.otus.javadeveloper.hw12;

import com.google.gson.Gson;
import ru.otus.javadeveloper.hw12.service.DBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class EntityServlet<T> extends HttpServlet {
    private final Gson gson;
    private final DBService dbService;
    private final Class<T> clazz;

    public EntityServlet(Gson gson, Class<T> clazz, DBService dbService) {
        this.gson = gson;
        this.clazz = clazz;
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<T> all = (List<T>) dbService.getAll(clazz);
//        all.stream().flatMap(user -> user.getPhoneDataSets().stream()).forEach(phoneDataSet -> phoneDataSet.setUser(null));
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
        T user = gson.fromJson(collect, clazz);
//        user.getPhoneDataSets().stream().forEach(phoneDataSet -> phoneDataSet.setUser(user));
        T saved = (T) dbService.save(user);
//        saved.getPhoneDataSets().stream().forEach(phoneDataSet -> phoneDataSet.setUser(null));

        String resultAsString = gson.toJson(saved);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resultAsString);
        printWriter.flush();
    }
}
