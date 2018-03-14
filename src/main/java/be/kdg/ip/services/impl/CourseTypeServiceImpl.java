package be.kdg.ip.services.impl;

import be.kdg.ip.domain.CourseType;
import be.kdg.ip.repositories.api.CourseTypeRepository;
import be.kdg.ip.services.api.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("courseTypeService")
@Transactional
public class CourseTypeServiceImpl implements CourseTypeService{
    @Autowired
    private CourseTypeRepository repository;

    @Override
    public CourseType addCourseType(CourseType courseType) {
        return repository.save(courseType);
    }

    @Override
    public CourseType getCourseType(int courseTypeId) {
        return repository.findOne(courseTypeId);
    }

    @Override
    public CourseType updateCourseType(CourseType courseType) {
        return repository.save(courseType);
    }

    @Override
    public void removeCourseType(int courseTypeId) {
        repository.delete(courseTypeId);
    }

    @Override
    public List<CourseType> getAllCourseTypes() {
        return repository.findAll();
    }
}
