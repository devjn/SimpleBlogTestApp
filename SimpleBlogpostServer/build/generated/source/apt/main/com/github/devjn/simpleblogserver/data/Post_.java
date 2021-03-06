
package com.github.devjn.simpleblogserver.data;

import com.github.devjn.simpleblogserver.data.PostCursor.Factory;
import io.objectbox.EntityInfo;
import io.objectbox.annotation.apihint.Internal;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;


// THIS CODE IS GENERATED BY ObjectBox, DO NOT EDIT.

/**
 * Properties for entity "Post". Can be used for QueryBuilder and for referencing DB names.
 */
public final class Post_ implements EntityInfo<Post> {

    // Leading underscores for static constants to avoid naming conflicts with property names

    public static final String __ENTITY_NAME = "Post";

    public static final int __ENTITY_ID = 1;

    public static final Class<Post> __ENTITY_CLASS = Post.class;

    public static final String __DB_NAME = "Post";

    public static final CursorFactory<Post> __CURSOR_FACTORY = new Factory();

    @Internal
    static final PostIdGetter __ID_GETTER = new PostIdGetter();

    public final static Post_ __INSTANCE = new Post_();

    public final static io.objectbox.Property<Post> id =
        new io.objectbox.Property<>(__INSTANCE, 0, 1, long.class, "id", true, "id");

    public final static io.objectbox.Property<Post> title =
        new io.objectbox.Property<>(__INSTANCE, 1, 2, String.class, "title");

    public final static io.objectbox.Property<Post> description =
        new io.objectbox.Property<>(__INSTANCE, 2, 3, String.class, "description");

    public final static io.objectbox.Property<Post> creationDate =
        new io.objectbox.Property<>(__INSTANCE, 3, 4, java.util.Date.class, "creationDate");

    @SuppressWarnings("unchecked")
    public final static io.objectbox.Property<Post>[] __ALL_PROPERTIES = new io.objectbox.Property[]{
        id,
        title,
        description,
        creationDate
    };

    public final static io.objectbox.Property<Post> __ID_PROPERTY = id;

    @Override
    public String getEntityName() {
        return __ENTITY_NAME;
    }

    @Override
    public int getEntityId() {
        return __ENTITY_ID;
    }

    @Override
    public Class<Post> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override
    public String getDbName() {
        return __DB_NAME;
    }

    @Override
    public io.objectbox.Property<Post>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override
    public io.objectbox.Property<Post> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override
    public IdGetter<Post> getIdGetter() {
        return __ID_GETTER;
    }

    @Override
    public CursorFactory<Post> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    @Internal
    static final class PostIdGetter implements IdGetter<Post> {
        @Override
        public long getId(Post object) {
            return object.id;
        }
    }

}
