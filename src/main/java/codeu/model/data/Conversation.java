  // Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import codeu.model.data.User;

/**
 * Class representing a conversation, which can be thought of as a chat room. Conversations are
 * created by a User and contain Messages.
 */
public class Conversation {
  public final UUID id;
  public final UUID owner;
  public final Instant creation;
  public final String title;
  public List<User> isVisibleTo = new ArrayList<User>();
  public final String user1;
  public final String user2;

  /**
   * Constructs a new Conversation.
   *
   * @param id the ID of this Conversation
   * @param owner the ID of the User who created this Conversation
   * @param title the title of this Conversation
   * @param creation the creation time of this Conversation
   */
  public Conversation(UUID id, UUID owner, String title, Instant creation) {
    this.id = id;
    this.owner = owner;
    this.creation = creation;
    this.title = title;
    this.user1 = "";
    this.user2 = "";
  }
  public Conversation(UUID id, UUID owner, String title, Instant creation, List<User> isVisibleTo) {
    this.id = id;
    this.owner = owner;
    this.creation = creation;
    this.title = title;
    this.isVisibleTo = isVisibleTo;
    this.user1 = "";
    this.user2 = "";
  }
  public Conversation(UUID id, UUID owner, String title, Instant creation, String user1, String user2) {
    this.id = id;
    this.owner = owner;
    this.creation = creation;
    this.title = title;
    this.user1 = user1;
    this.user2 = user2;
  }


  /** Returns the ID of this Conversation. */
  public UUID getId() {
    return id;
  }

  /** Returns the ID of the User who created this Conversation. */
  public UUID getOwnerId() {
    return owner;
  }

  /** Returns the title of this Conversation. */
  public String getTitle() {
    return title;
  }

  /** Returns the creation time of this Conversation. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Returns the List of users following the conversation. */
  public List<User> getFollowedUsers(){
    return isVisibleTo;
  }

  /** Returns the title of this Conversation. */
  public String getUser1() {
    return user1;
  }

  /** Returns the title of this Conversation. */
  public String getUser2() {
    return user2;
  }
}
