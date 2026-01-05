package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.CategoryCategoryDTOValueTestComparatorTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        LongIdValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class
})
public class CategoryCategoryDTOValueTestComparatorDefaultTestConfiguration
        extends CategoryCategoryDTOValueTestComparatorTestConfigurationTemplate {

}
