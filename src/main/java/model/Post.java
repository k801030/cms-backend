package model;

import org.bson.types.ObjectId;

import java.util.*;
/**
 * Created by Vison on 15/4/11.
 */
public class Post {
    public String _id;
    public String title;
    public String content;
    public Date date;

    public Post(String _id, String title, String content, Date date){
        this._id = _id;
        this.title = title;
        this.content = content;
        this.date = date;
    };

    public Post(String title, String content, Date date){
        this.title = title;
        this.content = content;
        this.date = date;
    };
}
