package codeu.controller;

import codeu.model.store.basic.UserStore;
import codeu.model.data.User;
import codeu.model.data.Profile;
import codeu.model.data.Message;
import codeu.model.data.Event;
import codeu.model.data.Conversation;
import codeu.model.data.NewConversationEvent;
import codeu.model.store.basic.ProfileStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.EventStore;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.time.Instant;
import java.util.UUID;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/** Servlet class responsible for user registration. */
public class ProfileServlet extends HttpServlet {
  /** Store class that gives access to Profiles. */
  private ProfileStore profileStore;

  private MessageStore messageStore;
  private UserStore userStore;
  private ConversationStore conversationStore;
  private EventStore eventStore;

  /**
   * Set up state for handling profile creation-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setProfileStore(ProfileStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setEventStore(EventStore.getInstance());
  }

  /**
   * Sets the ProfileStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setProfileStore(ProfileStore profileStore) {
    this.profileStore = profileStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the EventStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setEventStore(EventStore eventStore) {
    this.eventStore = eventStore;
  }


  /** doGet function will eventually output HTML directly from servlet */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    String username = requestUrl.substring("/user/".length());
    User user = userStore.getUser(username);

    Profile profile = profileStore.getProfile(username);
    if (profile == null) {
      // couldn't find profile, redirect to homepage
      System.out.println("Profile was null: " + username);
      response.sendRedirect("/index.jsp");
      return;
    }
    List<Message> messages = messageStore.getUserMessages(user.getId());

    request.setAttribute("profileUser", user);
    request.setAttribute("username", username);
    request.setAttribute("profile", profile);
    request.setAttribute("about", profile.getAbout());
    request.setAttribute("messages", messages);
    request.setAttribute("photo", profile.getPictureURL());

    request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String requestUrl = request.getRequestURI();
    String username = requestUrl.substring("/user/".length());
    User user = userStore.getUser(username);

    Profile profile = profileStore.getProfile(username);
    if (profile == null) {
      // couldn't find profile, redirect to homepage
      response.sendRedirect("/index.jsp");
      return;
    }
    if (request.getParameter("update") != null) {
      String newAbout = request.getParameter("newAbout");
      profile.setAbout(newAbout);
      profileStore.addProfile(profile);
    }

    if (request.getParameter("photo") != null) {
      String newPhoto = request.getParameter("photo");
      profile.setPhoto(newPhoto);
      profileStore.addProfile(profile);
    }

    if(request.getParameter("message") != null) {
      String otherUsername = username;
      User otherUser = user;
      username = (String) request.getSession().getAttribute("user");
      user = (User) userStore.getUser(username);

      if (username == null) {
        // user is not logged in, don't let them create a private conversation
        request.setAttribute("error", "Please log in to send a private message");
        response.sendRedirect("/user/" + otherUsername);
        return;
      }

      if (user == null) {
        // user was not found, don't let them create a private conversation
        request.setAttribute("error", "Please log in to send a private message");
        System.out.println("User not found: " + username);
        response.sendRedirect("/user/" + otherUsername);
        return;
      }

      if(username.equals(otherUsername)) {
        request.setAttribute("error", "You cannot message yourself!");
        response.sendRedirect("/user/" + otherUsername);
        return;
      }

      String conversationTitle = username + otherUsername;
      String altTitle = otherUsername + username;

      if (conversationStore.isTitleTaken(conversationTitle)) {
        // conversation title is already taken, just go into that conversation instead of creating a
        // new one
        response.sendRedirect("/privatechat/" + conversationTitle);
        return;
      }

      if (conversationStore.isTitleTaken(altTitle)) {
        // conversation title is already taken, just go into that conversation instead of creating a
        // new one
        response.sendRedirect("/privatechat/" + altTitle);
        return;
      }

      Conversation conversation =
          new Conversation(UUID.randomUUID(), user.getId(), conversationTitle, Instant.now(), username, otherUsername);

      conversationStore.addConversation(conversation);
      Event event = new NewConversationEvent(Instant.now(), "conversation-event", conversationTitle, "placeholder-link", true, username, otherUsername);
      eventStore.addEvent(event);
      response.sendRedirect("/privatechat/" + conversationTitle);
    }

//    response.sendRedirect("/user/" + username);
  }
}
