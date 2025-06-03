package com.example.doanltweb.controller;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.service.ProductService;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ProductPaginationServlet", value = "/ProductPaginationServlet")
public class ProductPaginationServlet extends HttpServlet {
    private static final int PAGE_SIZE = 12;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int page = 1;
        String sort = request.getParameter("sort");
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }
        int offset = (page - 1) * PAGE_SIZE;

        ProductDao dao = new ProductDao();
        List<Product> products = dao.getProductsByPageAndSort(offset, PAGE_SIZE, sort);
        int totalProducts = dao.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        Map<String, Object> result = new HashMap<>();
        result.put("products", products);
        result.put("totalPages", totalPages);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(result));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}