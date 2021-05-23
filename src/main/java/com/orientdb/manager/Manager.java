package com.orientdb.manager;

import com.orientechnologies.orient.core.db.ODatabaseSession;

public abstract class Manager {

    protected final ODatabaseSession session;

    public Manager(ODatabaseSession session) {
        this.session = session;
        this.createSchema();
    }

    protected abstract void createSchema();

}
