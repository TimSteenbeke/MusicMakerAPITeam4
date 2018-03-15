package be.kdg.ip.web.resources;

public class CourseTypeResource {
    private String description;
    private int price;
    private int courseTypeId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseTypeId(int courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public int getCourseTypeId(){return courseTypeId;}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
