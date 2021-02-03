package ${packagePrefix}.common.convert;

import org.mapstruct.*;

import java.util.List;
import java.util.stream.Stream;


@MapperConfig
public interface BaseMapping<S ,T> {

    /**
     * 映射同名属性
     * @param var
     * @return
     */
//    @Mapping(target = "",dateFormat = "yyyy-MM-dd HH:mm:ss")
    T sourceToTarget(S var);

    /**
     * 反向映射，映射同名属性
     * @param var
     * @return
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
    S targetToSource(T var);

    /**
     * 映射同名属性，集合形式
      * @param var
     * @return
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<T> sourceToTarget(List<S> var);

    /**
     * 反向映射，同名属性，集合形式
     * @param var
     * @return
     */
    @InheritConfiguration(name = "targetToSource")
    List<S> targetToSource(List<T> var);

    /**
     * 映射同名属性，集合流形式
     */
    List<T> sourceToTarget(Stream<S> stream);

    /**
     * 反向，映射同名属性，集合流形式
     */
    List<S> targetToSource(Stream<T> stream);
}
