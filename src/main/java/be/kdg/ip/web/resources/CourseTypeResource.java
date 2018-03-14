package be.kdg.ip.web.resources;

public class CourseTypeResource {
    private String courseTypeDescription;
    private int price;
    private int courseTypeId;

    public String getCourseTypeDescription() {
        return courseTypeDescription;
    }

    public void setCourseTypeDescription(String courseTypeDescription) {
        this.courseTypeDescription = courseTypeDescription;
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
