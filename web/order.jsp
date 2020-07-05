<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: denisandreev
  Date: 01.07.2020
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Card</title>

    <!-- CSS only -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <!-- JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</head>
<body>
<div class="container mx-auto my-2 my-sm-3 my-lg-4 p-3">
    <div class="row">
        <div class="col"></div>
        <div class="col-10">
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Back</a>
            <c:choose>
                <c:when test="${order.getProductList().size()== 0}">
                    <h4>Your cart is empty</h4>
                </c:when>
                <c:otherwise>
                    <h4> You have ${order.getProductList().size()} in your order. </h4>
                    <h4>Total price: ${order.getTotalPrice()} $ </h4>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <td>Product</td>
                            <td>Name</td>
                            <td>Info</td>
                            <td>Price</td>
                            <td></td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${order.getProductList()}" var="product">
                            <tr>
                                <td>
                                    <img class="img-fluid" src="<c:url value="img/${product.getImg()}"></c:url>" />
                                </td>
                                <td>${product.getName()}</td>
                                <td>${product.getInfo()}</td>
                                <td>${product.getPrice()}$</td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/card">
                                        <input type="hidden" name="orderId" value="${product.getOrderId()}">
                                        <input type="submit" class="btn btn-danger" name="submit" value="Delete">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="float-right">
                        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter">
                            Next
                        </button>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLongTitle">CHECKOUT</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form method="post" action="${pageContext.request.contextPath}/card">
                                        <div class="form-group">
                                            <label for="inputName">Name</label>
                                            <input type="text" class="form-control" id="inputName" aria-describedby="emailHelp" placeholder="Enter email" name="name">
                                            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                                        </div>
                                        <div class="form-group">
                                            <label for="inputPassword">Phone number</label>
                                            <input type="tel" class="form-control" id="inputPassword" placeholder="Enter phone number" name="phoneNumber">
                                        </div>
                                        <div class="form-group">
                                            <label for="inputAddress">Address</label>
                                            <input type="text" class="form-control" id="inputAddress" placeholder="Enter address" name="address">
                                        </div>
                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-success" name="submit" value="checkout" >Submit</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </c:otherwise>
            </c:choose>

        </div>
        <div class="col"></div>
    </div>
</div>
</body>
</html>
