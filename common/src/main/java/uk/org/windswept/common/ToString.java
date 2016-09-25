package uk.org.windswept.common;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by steveatkinson on 25/09/2016.
 */
public class ToString<T>
{
    T object;

    public ToString (T object)
    {
        this.object = object;
    }

    Set<Field> visitedFields = new HashSet<Field>();

    @Override
    public String toString ()
    {
        return toStringBuilder().toString();
    }

    ToStringBuilder toStringBuilder()
    {
        return new ReflectionToStringBuilder(object, ToStringStyle.SHORT_PREFIX_STYLE)
        {
            @Override
            protected boolean accept (Field field)
            {
                if (visitedFields.contains(field))
                {
                    return false;
                }
                visitedFields.add(field);
                return super.accept(field);
            }
        }
                .setExcludeFieldNames("size", "hashCode", "iterator");
    }

    private ToStringStyle recursiveToStringStyle ()
    {
        RecursiveToStringStyle toStringStyle = new RecursiveToStringStyle(){
            @Override
            protected boolean accept (Class<?> clazz)
            {
                return clazz.getPackage().getName().startsWith("org.windswept")
                        || clazz.getPackage().getName().startsWith("org.apache.ftpserver") ;
            }
        };
        return toStringStyle;
    }

}
