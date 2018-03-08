package be.kdg.ip.domain;
import javax.persistence.*;

@Entity
@Table(name="CourseType")
public class CourseType {
    @Id
    @Column
    @GeneratedValue
    private int courseTypeId;
    @Column
    private String description;
    @Column
    private int price;

    public int getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(int courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
