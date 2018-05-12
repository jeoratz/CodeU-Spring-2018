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
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
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
    <% if(request.getSession().getAttribute("user") != null) { %>
      <h1>Your Private Messages</h1>

      <%
      List<Conversation> conversations = (List<Conversation>) ConversationStore.getInstance().getAllConversations();
      if(conversations == null || conversations.isEmpty()){
      %>
        <p>No private messages yet!</p>
      <%
      }
      else{
      %>
        <ul class="mdl-list">
      <%
        String username = (String) request.getSession().getAttribute("user");
        for(Conversation conversation : conversations){
            if(conversation.getUser1().equals(username) || conversation.getUser2().equals(username)) {
              %>  <li><a href="/privatechat/<%= conversation.getTitle() %>">
                  <%= conversation.getTitle() %></a></li>
              <%
            }
        }
      }
    } else { %>
      <h3>  Login to see private messages</h3>
    <% }
    %>
      </ul>
    <hr/>
  </div>
</body>
</html>
