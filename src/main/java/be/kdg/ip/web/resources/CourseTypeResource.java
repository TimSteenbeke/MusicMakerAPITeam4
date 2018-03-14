package be.kdg.ip.web.resources;

public class CourseTypeResource {
    private String courseTypeDescription;
    private int price;

    public String getCourseTypeDescription() {
        return courseTypeDescription;
    }

    public void setCourseTypeDescription(String courseTypeDescription) {
        this.courseTypeDescription = courseTypeDescription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
