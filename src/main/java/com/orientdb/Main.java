package com.orientdb;

import com.orientdb.manager.FriendManager;
import com.orientdb.manager.PersonManager;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;

public class Main {

    public static void main(String[] args) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        ODatabaseSession db = orient.open("test", "test", "password");

        PersonManager personManager = new PersonManager(db);
        FriendManager manager = new FriendManager(db);

        personManager.createPerson();
        personManager.selectFromCity();

        manager.findFriendsOfFriends();
        manager.findFriendsOfFriendsExpandOut();
        manager.findFriendsOfFriendsExpandIn();
        manager.findFriendsOfFriendsExpandBoth();
        manager.findFriendsOfFriendsExpandOutOut();
        manager.findFriendsOfDepthFour();
        manager.getFriendsWithAliceBob();

        db.close();
        orient.close();
    }

}
