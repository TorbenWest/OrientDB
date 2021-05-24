package com.orientdb.manager;

import com.orientdb.GsonConverter;
import com.orientdb.entities.Address;
import com.orientdb.entities.Person;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

public class PersonManager extends Manager {

    public PersonManager(ODatabaseSession session) {
        super(session);
    }

    @Override
    protected void createSchema() {
        if (this.session.getClass("Person") == null) {
            this.session.createVertexClass("Person");
        }

        // You could define the properties likes this. Otherwise you will create a schemaless vertex.
//        if (person.getProperty("name") == null) {
//            person.createProperty("name", OType.STRING);
//            person.createProperty("address", OType.STRING);
//
////                                 index name                index type                 property name
//            person.createIndex("Person_name_index", OClass.INDEX_TYPE.NOTUNIQUE, "name");
//        }
    }

    public void createPerson() {
        System.out.println("Creating persons...");
        System.out.println("\nCREATE VERTEX Person SET \n" +
                "name = 'Tom',\n" +
                "address = { \n" +
                "\tcity: {\n" +
                "    \tname: 'London', \n" +
                "        code: 'DH 0139' \n" +
                "    },\n" +
                "    street: {\n" +
                "        name: 'Too St.', \n" +
                "        number: '2', \n" +
                "        extension: 'a'\n" +
                "    }, \n" +
                "    country: 'England' \n" +
                "}\n");

        OVertex alice = createPerson("Alice", "Foo St.", "London", "DE 982", "England");
        OVertex bob = createPerson("Bob", "Boo St.", "Dortmund", "68463", "Germany");
        OVertex jim = createPerson("Jim", "Joo St.", "Essen", "98443", "Germany");
        OVertex jonathan = createPerson("Jonathan", "Jona St.", "Moenchengladbach", "93905", "Germany");
        OVertex joshua = createPerson("Joshua", "Jos St.", "Krefeld", "95340", "Germany");
        OVertex torben = createPerson("Torben", "Tor St.", "Viersen", "94541", "Germany");
        OVertex niklas = createPerson("Niklas", "Nik St.", "Moenchengladbach", "92801", "Germany");
        OVertex fynn = createPerson("Fynn", "Fyn St.", "Waltrop", "92708", "Germany");
        OVertex dorothee = createPerson("Dorothee", "Dor St.", "Krefeld", "92114", "Germany");
        OVertex cora = createPerson("Cora", "Cor St.", "Duesseldorf", "98443", "Germany");
        OVertex david = createPerson("David", "Dav St.", "Venlo", "DG 4584", "Netherlands");
        OVertex laura = createPerson("Laura", "Lau St.", "Kleve", "87494", "Germany");
        OVertex paul = createPerson("Paul", "Pau St.", "Kaldenkirchen", "14864", "Germany");
        OVertex peter = createPerson("Peter", "Pet St.", "Kaldenkirchen", "87416", "Germany");

        System.out.println("Creating relationships...");
        System.out.println("\nCREATE EDGE FriendOf FROM #10:3 TO #11:4\n");

        addFriend(alice, niklas);
        addFriend(alice, paul);
        addFriend(alice, peter);
        addFriend(alice, bob);
        addFriend(bob, jim);
        addFriend(bob, peter);
        addFriend(bob, fynn);
        addFriend(bob, torben);
        addFriend(jim, niklas);
        addFriend(jim, david);
        addFriend(jim, laura);
        addFriend(jonathan, niklas);
        addFriend(jonathan, fynn);
        addFriend(jonathan, dorothee);
        addFriend(jonathan, alice);
        addFriend(torben, paul);
        addFriend(torben, peter);
        addFriend(torben, fynn);
        addFriend(niklas, torben);
        addFriend(niklas, paul);
        addFriend(niklas, cora);
        addFriend(niklas, david);
        addFriend(fynn, niklas);
        addFriend(fynn, dorothee);
        addFriend(dorothee, niklas);
        addFriend(dorothee, cora);
        addFriend(dorothee, laura);
        addFriend(dorothee, jim);
        addFriend(cora, fynn);
        addFriend(cora, david);
        addFriend(cora, laura);
        addFriend(cora, jim);
        addFriend(cora, paul);
        addFriend(david, jonathan);
        addFriend(david, fynn);
        addFriend(david, bob);
        addFriend(david, peter);
        addFriend(laura, niklas);
        addFriend(laura, david);
        addFriend(laura, torben);
        addFriend(paul, jonathan);
        addFriend(paul, fynn);
        addFriend(paul, david);
        addFriend(peter, niklas);
        addFriend(peter, laura);
        addFriend(peter, dorothee);
    }

    public void selectFromCity() {
        System.out.println("\n");

        String query = "SELECT FROM Person WHERE address.city.name = ?";
        System.out.println("Select all persons which living in Krefeld: \"" + query.replace("?", "'Krefeld'") + "\"");
        OResultSet rs = this.session.query(query, "Krefeld");

        rs.stream().forEach(x -> System.out.println("Result: " + x.getProperty("name")));
        rs.close();
    }

    private void addFriend(OVertex first, OVertex second) {
        System.out.println(first.getProperty("name") + " is a friend of " + second.getProperty("name"));
        OEdge edge2 = first.addEdge(second, "FriendOf");
        edge2.save();
    }

    private OVertex createPerson(String name, String street, String city, String code, String country) {
        OVertex result = this.session.newVertex("Person");

        String serialize = GsonConverter.serialize(new Person(name, city, code, street, country));
        System.out.println("Creating new vertex: " + serialize);

        result.fromJSON(serialize);
        result.save();
        return result;
    }

}
