package com.orientdb.manager;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class FriendManager extends Manager {

    public FriendManager(ODatabaseSession session) {
        super(session);
    }

    @Override
    protected void createSchema() {
        if (this.session.getClass("FriendOf") == null) {
            this.session.createEdgeClass("FriendOf");
        }
    }

    public void findFriendsOfFriends() {
        String query = "SELECT out('FriendOf') FROM Person";
        System.out.println("\nSelects all relationships: \"" + query + "\"");
        OResultSet rs = this.session.query(query);

        rs.stream().forEach(e -> System.out.println("Result: " + e.toString().replace("\n", " ")));
        rs.close();
    }

    public void findFriendsOfFriendsExpandOut() {
        friendOfFriends("SELECT expand(out('FriendOf')) FROM Person WHERE name = ?",
                "Get the friends from Bob");
    }

    public void findFriendsOfFriendsExpandIn() {
        friendOfFriends("SELECT expand(in('FriendOf')) FROM Person WHERE name = ?",
                "Get the persons that have Bob as friend");
    }

    public void findFriendsOfFriendsExpandBoth() {
        friendOfFriends("SELECT expand(both('FriendOf')) FROM Person WHERE name = ?",
                "Get the persons that have Bob as friend and the friends from Bob");
    }

    public void findFriendsOfFriendsExpandOutOut() {
        friendOfFriends("SELECT expand(out('FriendOf').out('FriendOf')) FROM Person WHERE name = ?",
                "Get the friends of friends from Bob");
    }

    public void findFriendsOfDepthFour() {
        friendOfFriends("TRAVERSE out('FriendOf') FROM (SELECT FROM Person WHERE name = ?) WHILE $depth < 4",
                "Get the friends of friends of friends from Bob");
    }

    public void getFriendsWithAliceBob() {
        System.out.println("\n");

        String query =
                " MATCH                                           " +
                "   {class:Person, as:a, where: (name = :name1)}, " +
                "   {class:Person, as:b, where: (name = :name2)}, " +
                "   {as:a} -FriendOf-> {as:x} -FriendOf-> {as:b}  " +
                " RETURN x.name as friend                         ";

        System.out.println("Get the friend of Niklas and Fynn: \"" +
                query.replace("name1", "Niklas").replace("name2", "Fynn") + "\"");

        Map<String, Object> params = new HashMap<>();
        params.put("name1", "Niklas");
        params.put("name2", "Fynn");

        OResultSet rs = this.session.query(query, params);

        while (rs.hasNext()) {
            OResult item = rs.next();
            System.out.println("Friend: " + item.getProperty("name"));
        }

        rs.close();
    }

    private void friendOfFriends(String query, String message) {
        System.out.println("\n" + message + ": \"" + query.replace("?", "'Bob'") + "\"");
        OResultSet rs = this.session.query(query, "Bob");

        rs.stream()
                .sorted(Comparator.comparing(s -> s.getProperty("name")))
                .forEach(x -> System.out.println("Friend: " + x.getProperty("name")));
        rs.close();
    }
}
