/**
 * Created by Vison on 15/4/11.
 */
import api.PostController;
import model.*;
import static spark.Spark.*;

public class app {
    public static void main(String[] args) {
        System.out.println("Hello Spark!");
        port(9090); // Spark will run on port 9090
        staticFileLocation("/public");
        accessControlInit();



        // API services
        new PostController(new PostService(), "/api/post");

        // Html services
        //new HtmlController("");
    }

    // As title
    private static void accessControlInit() {
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });
    }
}
