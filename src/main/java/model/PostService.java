package model;
import java.util.*;
import com.mongodb.*;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by Vison on 15/4/11.
 */
public class PostService {
    private MongoCollection<Document> postCollection;

    // test dataset
    private LinkedList<Post> posts;

    public PostService() {

        // To  connect to a single MongoDB server
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        // To access a database
        MongoDatabase database = mongoClient.getDatabase("mydb");

        postCollection = database.getCollection("post");

        //addPost(new Post("title", "content1", new Date()));
        //getAllPosts();
        //getPost("552b71fbabc27a0c3362084f");
    }

    public void addPost(Document post) {
        // mongodb: insert
        postCollection.insertOne(post);
    }

    public Document getPost(String _id) {
        // mongodb: query
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(_id));  // need to be an object
        Document post = postCollection.find(query).first();
        if (post == null)
            return null;
        else
            return post;

        /*Post post = new Post(
                postDoc.getObjectId("_id").toString(),
                postDoc.getString("title"),
                postDoc.getString("content"),
                postDoc.getDate("date"));
        */

    };

    public ArrayList<Document> getAllPosts() {
        MongoCursor<Document> cursor = postCollection.find().iterator();
        ArrayList<Document> docs = new ArrayList<Document>();
        int i = 0;
        try {
            while (cursor.hasNext()) {
                docs.add(cursor.next());

                // set id to string
                docs.get(i).put("_id", docs.get(i++).get("_id").toString());
            }
        } finally {
            cursor.close();
        }
        return docs;
    };

    public void updatePost(String _id, Document post) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(_id));
        postCollection.updateOne(query, post);
    };

    public void deletePost(String _id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(_id));  // need to be an object
        postCollection.deleteOne(query);
    };


}
