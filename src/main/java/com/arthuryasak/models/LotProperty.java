package ru.yasak.springcourse.models;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "lots_properties")
public class LotProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Integer propertyId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // (optional = false, mappedBy = "property", fetch = FetchType.EAGER) // was LAZY
    @JoinColumn(name = "lot_id")
    private Lot lot;

    private Integer weight;

    private Integer size;

    private String description;

    private byte[] photo;

    public LotProperty() {

    }
    public LotProperty(Lot lot) {
        this.lot = lot;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotProperty that = (LotProperty) o;
        return Objects.equals(getPropertyId(), that.getPropertyId()) && Objects.equals(getLot(), that.getLot()) && Objects.equals(getWeight(), that.getWeight()) && Objects.equals(getSize(), that.getSize()) && Objects.equals(getDescription(), that.getDescription()) && Arrays.equals(getPhoto(), that.getPhoto());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getPropertyId(), getWeight(), getSize(), getDescription());
        result = 31 * result + Arrays.hashCode(getPhoto());
        return result;
    }
}
