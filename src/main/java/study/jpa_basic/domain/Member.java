package study.jpa_basic.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Member")
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "MEMBER_ID")
  private Long id;

  @Column(name = "USERNAME")
  private String username;

  @Embedded private Period workPeriod;

  @Embedded private Address homeAddress;

  @ElementCollection
  @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
  @Column(name = "FOOD_NAME")
  private Set<String> favoriteFood = new HashSet<>();

//  @OrderColumn(name = "address_history_order")
//  @ElementCollection
//  @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//  private List<Address> addressHistory = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "MEMBER_ID")
  private List<AddressEntity> addressHistory = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Period getWorkPeriod() {
    return workPeriod;
  }

  public void setWorkPeriod(Period workPeriod) {
    this.workPeriod = workPeriod;
  }

  public Address getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
  }

  public Set<String> getFavoriteFood() {
    return favoriteFood;
  }

  public void setFavoriteFood(Set<String> favoriteFood) {
    this.favoriteFood = favoriteFood;
  }

  public List<AddressEntity> getAddressHistory() {
    return addressHistory;
  }

  public void setAddressHistory(List<AddressEntity> addressHistory) {
    this.addressHistory = addressHistory;
  }
}
