package org.zerock.w1.calc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="calcController", urlPatterns="/calc/makeResult")
public class CalcController extends HttpServlet {
    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");

        System.out.printf(" num1: %s", num1);
        System.out.printf(" num2: %s", num2);

        //post 방식으로 처리하고 jsp를 이용해서 결과를 보여주는 방식으로 이용하면 같은 페이지를 다시 호출 할 수 있기에 처리가 끝난후 다른 경로로 이동해야 한다.
        resp.sendRedirect("/index");
    }
}
