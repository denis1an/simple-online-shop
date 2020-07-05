<%--
  Created by IntelliJ IDEA.
  User: denisandreev
  Date: 07.06.2020
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>Online shop</title>
    <!-- CSS only -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <!-- JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
  </head>
  <body>

  <nav class="navbar navbar-light bg-light justify-content-between">
    <a class="navbar-brand">Shop Online</a>
    <a href="${pageContext.request.contextPath}/card" class="btn btn-secondary" role="button">Shopping card</a>
  </nav>

  <div class="container mx-auto my-2 my-sm-3 my-lg-4 p-3">
    <div class="row">
      <div class="col"></div>
      <div class="col-10">
        <div class="card-columns">
          <c:forEach items="${products}" var="product">
            <div class="card" style="width: 18rem;">
              <img class="card-img-top" src="<c:url value="img/${product.getImg()}"></c:url>"/>
              <div class="card-body">
                <h5 class="card-title">
                  <p>${product.getName()}</p>
                  <p>${product.getPrice()}$</p>
                </h5>
                <p class="card-text">${product.getInfo()}
                  <a href="http://localhost:8080/dracaena/product?productId=${product.getId()}" class="text-black">More ...</a>
                </p>
                <form method="post" action="${pageContext.request.contextPath}/products">
                  <input type="hidden" name="productId" value="${product.getId()}">
                  <input type="submit" class="btn btn-success" name="submit" value="Add to Cart">
                </form>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
      <div class="col"></div>
    </div>
  </div>
  </body>
</html>
