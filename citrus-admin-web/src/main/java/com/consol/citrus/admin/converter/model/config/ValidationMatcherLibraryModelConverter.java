/*
 * Copyright 2006-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.admin.converter.model.config;

import com.consol.citrus.admin.converter.model.AbstractModelConverter;
import com.consol.citrus.model.config.core.ValidationMatcherLibraryModel;
import com.consol.citrus.validation.matcher.ValidationMatcher;
import com.consol.citrus.validation.matcher.ValidationMatcherLibrary;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Christoph Deppisch
 */
@Component
public class ValidationMatcherLibraryModelConverter extends AbstractModelConverter<ValidationMatcherLibraryModel, ValidationMatcherLibrary> {
    /**
     * Default constructor.
     */
    public ValidationMatcherLibraryModelConverter() {
        super(ValidationMatcherLibraryModel.class, ValidationMatcherLibrary.class);

        addDecorator(new MethodCallDecorator("setMatchers", "setMembers") {
            @Override
            public Object decorateArgument(Object arg) {
                getAdditionalImports().add(AbstractMap.class);
                getAdditionalImports().add(Collectors.class);
                getAdditionalImports().add(Stream.class);
                getAdditionalImports().add(ValidationMatcher.class);

                List<ValidationMatcherLibraryModel.Matcher> matchers = (List<ValidationMatcherLibraryModel.Matcher>) arg;
                StringBuilder codeBuilder = new StringBuilder();

                codeBuilder.append("Stream.of(");
                matchers.forEach(matcher -> codeBuilder.append(String.format("%n\t\t\t\tnew AbstractMap.SimpleEntry<String, ValidationMatcher>(\"%s\", new %s()),", matcher.getName(), matcher.getClazz())));
                codeBuilder.deleteCharAt(codeBuilder.length() - 1);
                codeBuilder.append(String.format(")%n\t\t\t.collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue))"));

                return codeBuilder.toString();
            }

            @Override
            public boolean allowMethodCall(Object arg) {
                return ((List<ValidationMatcherLibraryModel.Matcher>) arg).size() > 0;
            }
        });
    }

    @Override
    public ValidationMatcherLibraryModel convert(String id, ValidationMatcherLibrary model) {
        ValidationMatcherLibraryModel converted = convert(model);
        converted.setId(id);

        model.getMembers().forEach((key, value) -> {
            ValidationMatcherLibraryModel.Matcher matcher = new ValidationMatcherLibraryModel.Matcher();

            matcher.setName(key);
            matcher.setClazz(value.getClass().getName());

            converted.getMatchers().add(matcher);
        });

        return converted;
    }

    @Override
    public String getJavaConfig(ValidationMatcherLibraryModel model) {
        return getJavaConfig(model, model.getId());
    }
}
