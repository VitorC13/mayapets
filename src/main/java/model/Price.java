package model;

import java.sql.Timestamp;

public class Price {
    private Long id;
    private Double price;
    private Double priceVar;
    private Double priceAtc;
    private Product product;
    private Collection collection;
    private Timestamp dateCreated;
    private Timestamp dateUpdate;
    private boolean active;
    private boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceVar() {
        return priceVar;
    }

    public void setPriceVar(Double priceVar) {
        this.priceVar = priceVar;
    }

    public Double getPriceAtc() {
        return priceAtc;
    }

    public void setPriceAtc(Double priceAtc) {
        this.priceAtc = priceAtc;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
