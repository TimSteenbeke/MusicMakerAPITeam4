package be.kdg.ip.config.orika;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Integrates Orika Framework with Spring's Framework. During application startup Orika wil be initialized all
 * Spring Components of Orika's Convertor and Mapper type will be registered in Orika's Mapperfactory.
 */
@Component
public class OrikaBeanMapper extends ConfigurableMapper {
    private MapperFactory mapperFactory;
    private ApplicationContext applicationContext;

    @Autowired
    public OrikaBeanMapper(ApplicationContext applicationContext) {
        // false = delay init  of ConfigurableMapper
        // because registering of convertors needs be done before init.
        super(false);
        this.applicationContext = applicationContext;
        // init ConfigurableMapper
        this.init();
    }

    @Override
    protected void configure(MapperFactory factory) {
        this.mapperFactory = factory;
        // next line because of
        // http://stackoverflow.com/questions/29867292/orika-not-able-to-map-when-the-application-is-running-on-jetty
        // this.mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(DateTime.class));
        addCustomMapperAndConvertors();
    }

    @Override
    protected void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
        // customize the factoryBuilder as needed
        factoryBuilder.mapNulls(false).build();

    }

    private void addCustomMapperAndConvertors() {
        final Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
        converters.values().forEach(this::addConverter);

        final Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
        mappers.values().forEach(this::addMapper);
    }

    private void addConverter(final Converter<?, ?> converter) {
        mapperFactory.getConverterFactory().registerConverter(converter);
    }

    private void addMapper(final Mapper<?, ?> mapper) {
        mapperFactory.classMap(mapper.getAType(), mapper.getBType())
                .byDefault()
                .mapNulls(false)
                .mapNullsInReverse(false)
                .customize((Mapper) mapper)
                .register();
    }
}
