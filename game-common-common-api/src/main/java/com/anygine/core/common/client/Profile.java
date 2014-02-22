package com.anygine.core.common.client;

import playn.core.Image;

import com.anygine.core.common.client.annotation.Field;
import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.api.JsonWritableFactory;
import com.google.inject.Inject;

@Storable
public class Profile { 

  public static enum Type {
    AUTHENTICATED, DEV, ANONYMOUS, ADD_PROFILE;
  }

  private static final String UNKNOWN_PROFILE_IMAGE_PATH = "Icons/UnknownMale.png";
  
  @Inject
  private static JsonWritableFactory jsonWritableFactory;
  
  @Inject
  private JsonWritableFactory factory;
  
  @Field(name="type")
  protected final Type type;
  @Field(name="username", unique=true)
  protected final String username;
  protected final String password;
  @Field(name="email", unique=true)
  protected final String email;
  @Field(name="autoLogin")
  protected final boolean autoLogin;

  @Field(name="imageWithPath")
  protected ImageWithPath imageWithPath;

  public Profile(
//      long id, int version,
      Type type, String username, String password, 
      String email, boolean autoLogin, 
      @FieldRef(field="imageWithPath", attribute="path") String imagePath) {
//    super(id, version);
    this.type = type;
    this.username = username;
    this.password = password;
    this.email = email;
    this.autoLogin = autoLogin;
    if (imagePath == null || imagePath.equals("")) {
      imagePath = UNKNOWN_PROFILE_IMAGE_PATH;
    }
//    Image image = PlayNInjectorManager.getInjector().getAssetManager().getImage(imagePath);
    this.imageWithPath = jsonWritableFactory.newInstance(new ImageWithPath(imagePath));
  }

  public String getEmail() {
    return email;
  }

  public boolean isAutoLogin() {
    return autoLogin;
  }

  public Type getType() {
    return type;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Image getImage() {
    return imageWithPath.getImage();
  }

  // TODO: Try to enforce default access
  public ImageWithPath getImageWithPath() {
    return imageWithPath;
  }
  
  public String getImagePath() {
    return imageWithPath.getPath();
  }

/*  
  // TODO: Remove when verified that write(EntityWriter) works
  @Override 
  protected void _writeJson(Writer writer) {
    super._writeJson(writer);
    writeEnum(type, writer, "type");
    writeString(username, writer, "username");
    writeString(email, writer, "email");
    writer.value("autoLogin", autoLogin);
    write(imageWithPath, writer, "image");    
  }

  // TODO: Code generate
  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writeEnum(type, writer, "type");
    writeString(username, writer, "username");
    writeString(email, writer, "email");
    writer.value("autoLogin", autoLogin);
    write(imageWithPath, writer, "image");
  }

  // TODO: Code generate
  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    update(ImageWithPath.class, imageWithPath, jsonObj, "image");
  }

  // JDK compiler requires usage Class.isAssignableFrom() + Class.cast()
  // TODO: Code-generate
  // TODO: Possibly split into non type parameterized methods
  // TODO: Fix password storage and handling
  @Override
  public <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    if (attribute.getClass().isAssignableFrom(Long.class) 
        && Long.class.cast(attribute) == MetaModel.getProfile().id) {
      return (int) (id - (Long.class.cast(value)));
    }
    if (attribute.getClass().isAssignableFrom(String.class) 
        && String.class.cast(attribute) == MetaModel.getProfile().email) {
      return email.compareTo(String.class.cast(value));
    }
    if (attribute.getClass().isAssignableFrom(String.class) 
        && String.class.cast(attribute) == MetaModel.getProfile().username) {
      return username.compareTo(String.class.cast(value));
    }
    if (attribute.getClass().isAssignableFrom(String.class) 
        && String.class.cast(attribute) == MetaModel.getProfile().password) {
      return 0;
      // TODO
      // return 0; 
    }
    return 1;
  }

  // TODO: Code-generate
  // TODO: Later on, use secondary indexes (maps) for performance
  @Override
  protected <E extends Entity> void checkUniqueConstraints(
      SortedMap<Long, E> typedEntities) throws UniqueConstraintViolationException {
    for (E entity : typedEntities.values()) {
      if (email.equals(((Profile) entity).getEmail())) {
        throw new UniqueConstraintViolationException(
            "Email " + email + " already exists");
      }
      if (username.equals(((Profile) entity).getUsername())) {
        throw new UniqueConstraintViolationException(
            "Username " + username + " already exists");
      }
      // Any other...
    }
  }

 
  // TODO: Code generate
  @Override
  public Profile entityCopy(Json.Object updateSpec) {
    return null;
    //    return new Profile(updateSpec);
  }
  
  */
}
