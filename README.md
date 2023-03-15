도서명 - 자바 웹 개발 워크북  
IDE - IntelliJ IDEA 2022.3.3 (Ultimate Edition)  
자바 버전 - JDK 11  
웹 서버 - 톰캣 9.0.73  

1. 웹 기본 동작 방식  
   (1)Request(요청) / Response(응답)
    * GET 방식 : 주소창에 직접 원하는 데이터를 적거나 링크를 클릭해서 호출
    * POST 방식 : 주소와 데이터를 따로 보내는 방식
   
   (2) Response(응답)에서 정적, 동적 데이터에 따라 다르게 처리
    * 정적(static)데이터 : 항상 동일하게 고정된 데이터를 전송하는 방식
    * 동적(dynamic)데이터 : 매번 다른 데이터를 구성해서 전송하는 방식
    * 정적 데이터를 보내는 역활만 수행하는 서버는 웹서버라 칭함
    * 동적 데이터를 보내는 역할을 수행하는 서버는 웹 애플리케이션 서버(WAS)라고 칭함
 
   (3)서블릿/JSP  
     * 서블릿(Servlet) : 서버에서 동적으로 요청과 응답을 처리할 수 있는 API들을 정의
     * JSP : 서블릿과 같은 원리지만 HTML을 쉽게 이용할 수 있는 방식으로 코드 작성 가능
     * 주로 서블릿으로는 코드를 이용한 처리, JSP로는 화면 개발
     * 객체를 생성하거나 호출하는 주체는 사용자가 아닌 서블릿 컨테이너가 담당
     * 서블릿 클래스에서 생성하는 객체의 관리가 서븦핏 컨테이너에 의해 관리
     * 서블릿/JSP의 코드 개발은 기본적인 자바 API, 서블릿 API도 같이 사용
     * JSP는 HTML 내에 자바 코드를 추가하는 방식
     * 서블릿은 자바 코드 안에 HTML 코드를 추가하는 방식

   (4)쿼리 스트링과 파라미터  
      * 주소창 뒤에 '?'로 시작하는 내용물을 쿼리 스트링이라고 함
      * 쿼리 스트링은 '키=값'의 형태로 데이터 전달
      * 여러개의 데이터가 필요한 경우 '&'를 이용하여 연결
      * '키=값'의 형태를 '파라미터 이름과 값' 이라고 함
      * 파라미터는 모두 문자열로 처리되어 결과 데이터를 처리하기 위해서는 형변환을 해야 한다.
      * 위의 이유로 JSP에서는 스트링이나 파라미터를 처리 하지 않는다.
      * JSP는 입력 화면을 구성하거나 처리 결과를 보여주는 용로도만 사용한다.
      * 브라우저는 직접 JSP 경로를 호출하지 않고 서블릿 경로를 통해서 JSP를 보는 방식으로 사용

   (5)MVC 방식
      * Request -> Controller <- Model
      *               |
      * Response <- View
      * WEB-INF 경로 하다는 브라우저에서 직접 접근이 불가능한 경로로 jsp로 직접 호출이 불가능 함
      * doGet() 으로 GET 방식으로 들어오는 요청 처리
      * doPost() 으로 POST 방식으로 들어오는 요청 처리
```java
@WebServlet(name="calcController", urlPatterns="/calc/makeResult")
public class CalcController extends HttpServlet {
    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");

        System.out.printf(" num1: %s", num1);
        System.out.printf(" num2: %s", num2);
        
        //post 방식으로 처리하고 jsp를 이용해서 결과를 보여주는 방식으로 이용하면 
        //같은 페이지를 다시 호출 할 수 있기에 처리가 끝난 후 다른 경로로 이동해야 한다.
        resp.sendRedirect("/index");
    }
}
```

2. HttpServlet 의 라이플사이클
   * 브라우저가 톰갯에 서블릿이 처리해야 할 특정한 경로를 호출
   * 서블릿 클래스를 로딩하고 객채 생성. init()를 실행해서 객체가 동작 전 수행해야 할 일을 처리
   * 생성된 서블릿 객체는 GET/POST 정보와 함께 전달되는 파라미터를 HttpServletRequest 라는 타입의 파라미터로 전달
   * 응답을 처리하는데 필요한 기능들은 HttpServletResponse 라는 타입의 객체로 전달
   * 서블릿 내부에서는 GET/POST 에 맞게 doGet()/doPost() 등의 메소드를 실행
   * 이 후 동일한 주소의 호출이 있을 때 서블릿은 동일한 객체 하나만을 이용해서 처리
   * 톰캣이 종료될 때는 서블릿의 destroy() 라는 메소드를 실행
```java
@WebServlet(name="sampleServlet", urlPatterns = "/sample")
public class SampleServlet extends HttpServlet {
    @Override //같은 주소면 동일한 객체로 전달
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        System.out.println("doGet...." + this);
    } 
    @Override //톰캣(웹서버) 종료 후 수행할 일을 지정
    public void destroy(){ 
        System.out.println("destory................");
    }
    @Override //객체 동작 전 수행할 일을 지정
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init(ServletConfig)........");
    }
}
```
3. HttpServletRequest 의 주요 기능(주로 읽기 기능)
   * getParameter() : 키를 이용해서 값을 얻는 역할(항상 String 형, null 체크 필수)
   * getParameterValues() : 동일한 이름의 파라미터가 여러개 일 경우 사용(Sting[] 타입으로 반환)
   * setAttribute() : JSP 로 전달할 데이터를 추가할 때 사용(키와 값 형대로 데이터 저장)
   * RequestDispatcher()  : 현재 요청을 다른 서버의 자원에게 전달하는 용도로 사용

4. HttpServletResponse 의 주요 기능(주로 쓰기 기능)
   * setContentType() : 응답 데이터의 종류를 지정
   * setHeader() : 특정 이름의 HTTP 헤더 지정
   * setStatus() : 응답 상태 코드 지정
   * getWriter() : PrintWriter 를 이용해서 응답 메시지 작성
   * addCookie() : 응답 시에 쿠기 추가
   * sendRedirect() : 브라우저에 이동을 지시  
   
3. Todo 웹 어플리케이션 구조
   * 목록(GET) : 조회 화면, 등록/수정/삭제 후에도 결과 확인 화면
   * 등록(GET) : 등록을 위해서는 GET 방식으로 이동하기에 등록 화면도 작성
   * 등록(POST) : 등록 화면에서 입력한 내용들은 POST 방식으로 전송된다.
   * 컨트롤러에서 처리 후 Redirect 방식으로 다시 목록 화면으로 이동한다.
   * 조회(GET) : 조회 화면은 GET 방식으로 동작한다. Todo 글 번호를 선택하면 동작한다.
   * 수정/삭제(GET) : 조회 화면에서 수정/삭제를 선택했을 때 GET 방식으로 이동한다.
   * 수정 (POST) : 수정은 POST 방식으로 전송하고 컨트롤러에서 수정 뒤 목록 화면으로 이동한다.
   * 삭제 (POST) : 삭제도 POST 방식으로 전송하고 컨트롤러에서 삭제 후 목록 화면으로 이동한다.