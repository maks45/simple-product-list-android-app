package com.maks.durov.productslist.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.maks.durov.productslist.room.LocalDateTimeConverter;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    private String imagePath;
    @TypeConverters({LocalDateTimeConverter.class})
    private LocalDateTime dateTime;
    private Boolean isDone;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(title, product.title) &&
                Objects.equals(imagePath, product.imagePath) &&
                Objects.equals(isDone, product.isDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, imagePath, isDone);
    }
}
