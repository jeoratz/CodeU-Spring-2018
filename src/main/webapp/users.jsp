<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<!DOCTYPE html>
<html>
<head>
  <title>Users</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <a href="/following">Following</a>
    <% } else{ %>
        <a href="/login">Login</a>
        <a href="/register">Register</a>
     <% } %>
    <% if(request.getSession().getAttribute("user") != null){ %>
            <a href="/user/<%= request.getSession().getAttribute("user") %>">Profile</a>
            <a href="/messages.jsp">Messages</a>
    <% } %>

    <a href="/about.jsp">About</a>
    <a href="/users.jsp">Users</a>
    <a href="/activityfeed">Activity Feed</a>
  </nav>

    <h1>Users</h1>

    <%
    List<User> users = (List<User>) UserStore.getInstance().getAllUsers();
    if(users == null || users.isEmpty()){
    %>
      <p>No users yet!</p>
    <%
    }
    else{
    %>
      <ul class="mdl-list">
    <%
      for(User user : users){
          %>  <li><a href="/user/<%= user.getName() %>">
              <%= user.getName() %></a></li>
          <%
      }
    %>
      </ul>
    <%
    }
    %>
    <hr/>
  </div>
</body>
</html>
