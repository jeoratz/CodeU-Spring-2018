<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Event" %>
<%@ page import="codeu.model.data.NewConversationEvent" %>
<%@ page import="codeu.model.data.LoginLogoutEvent" %>
<%@ page import="codeu.model.data.NewUserEvent" %>
<%@ page import="codeu.model.data.NewMessageEvent" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.EventStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>


<%
List <Event> events = (List<Event>) request.getAttribute("events");
%>

<!DOCTYPE html>
<html>
<head>
	<title>Activity Feed</title>
	<link rel="stylesheet" href="/css/main.css">
	<style>
		#activityfeed {
      background-color: white;
			height: 500px;
			overflow-y:scroll;
		}
	</style>
  <script>
    // scroll the chat div to the bottom
    function scrollEvents() {
      var eventDiv = document.getElementById('event');
      eventDiv.scrollTop = eventtDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollEvents()">
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
  	<div id="container">
  		<h1>Activity Feed</h1>
      <hr/>
  		<div id="activityfeed">
  			<ul>
  				<%
  				for (Event event : events) {
  					if(event.getEventType().equals("conversation-event")){
  					  NewConversationEvent conversationEvent = (NewConversationEvent) event;
  					  if (!conversationEvent.isPrivate()){
  					    String message = event.toString();
                %>
                    <li>
                     <strong><%= message%> </strong>
                    </li>
                <%
  				    }
  				  }
            else if(event.getEventType().equals("message-event")){
              NewMessageEvent messageEvent = (NewMessageEvent) event;
              if (!messageEvent.isPrivate()){
                String message = event.toString();
                %>
                    <li>
                     <strong><%= message%> </strong>
                    </li>
                <%
              }
            }
  				  else {
  				    String message = event.toString();
              %>
                <li>
                <strong><%= message%> </strong>
                </li>
               <%
  				  }
  			  }
  			%>
  		</ul>
  	</div>
    <form action="/privateactivityfeed" method="POST">
     <button type="submit">Private Activity Feed</button>
   </form>
  </div>
</body>
</html>




