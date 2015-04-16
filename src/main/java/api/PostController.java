package api;

import com.google.gson.Gson;
import model.PostService;
import org.bson.Document;
import org.json.simple.*;
import java.util.*;
import static spark.Spark.*;

/**
 * Created by Vison on 15/4/11.
 */

public class PostController {
    public PostController(final PostService postService, String router) {

        // create
        post(router + "", (req, res) -> {
            String jsonString = req.body();
            JSONObject jsonObj = (JSONObject)JSONValue.parse(jsonString);

            Document doc = new Document("title", jsonObj.get("title"))
                    .append("content", jsonObj.get("content"))
                    .append("date", jsonObj.get("date"));
            postService.addPost(doc);
            return true;
        });
        // read
        get(router + "/:id", (req, res) -> {
            String id = req.params(":id");
            Document doc = postService.getPost(id);

            return doc.toJson();
        });

        // get all post
        get(router + "", (req, res) -> {
            ArrayList<Document> docs = postService.getAllPosts();
            String jsonString = new Gson().toJson(docs);
            return jsonString;
        });

        // update
        put(router + "/:id", (req, res) -> {
            String id = req.params(":id");
            String jsonString = req.body();
            JSONObject jsonObj = (JSONObject)JSONValue.parse(jsonString);
            Document doc = new Document("title", req.params("title"))
                    .append("content", req.params("content"))
                    .append("date", req.params("date"));
            postService.updatePost(id, doc);
            return 0;
        });

        // delete
        delete(router + "/:id", (req, res) -> {
            String id = req.params(":id");
            postService.deletePost(id);
            return 0;
        });


        //////

        get("/users/:name", (request, response) -> "Selected user: " + request.params(":name"));

        get("/news/:section", (request, response) -> {
            response.type("text/xml");
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><news>" + request.params("section") + "</news>";
        });

        get("/protected", (request, response) -> {
            halt(403, "I don't think so!!!");
            return null;
        });

        get("/redirect", (request, response) -> {
            response.redirect("/news/world");
            return null;
        });
    };

}
