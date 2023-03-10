package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

/**
 * Model class for the abstract Museum User, the code was partially generated by Umple.
 * Extends to Manager, Visitor, and Employee.
 *
 * @author Siger
 * @author Kevin
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class MuseumUser {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // MuseumUser Attributes
  private String email;
  private String name;
  private String password;
  private long museumUserId;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public MuseumUser() {
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  @Column(unique = true, nullable = false)
  public String getEmail() {
    return email;
  }

  @Column(nullable = false)
  public String getName() {
    return name;
  }

  @Column(nullable = false)
  public String getPassword() {
    return password;
  }

  @Id
  @GeneratedValue()
  public long getMuseumUserId() {
    return museumUserId;
  }

  public boolean setEmail(String aEmail) {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName) {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword) {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setMuseumUserId(long aMuseumUserId) {
    boolean wasSet = false;
    museumUserId = aMuseumUserId;
    wasSet = true;
    return wasSet;
  }
}
