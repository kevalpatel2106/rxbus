/*
 *  Copyright 2017 Keval Patel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance wit
 *  the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 *   the specific language governing permissions and limitations under the License.
 */

package com.kevalpatel2106.rxbus.annotations;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.kevalpatel2106.rxbus.annotations.RegisterEvent")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RegisterAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        Collection<? extends Element> annotationElements = roundEnvironment.getElementsAnnotatedWith(RegisterEvent.class);
        System.out.println("Types 1 annotation found in " + annotationElements.size());
        for (Element elem : roundEnvironment.getElementsAnnotatedWith(RegisterEvent.class)) {
            RegisterEvent complexity = elem.getAnnotation(RegisterEvent.class);

            write("com.kevalpatel2106.rxbussample", elem.getSimpleName().toString(), complexity.EVENT_TAG());
        }
        return false;

    }

    private void write(String packageName, String name, String[] tags) {
        if (packageName == null) return;

        StringBuilder builder = new StringBuilder()
                .append("package " + packageName + ";\n")
                .append("public class " + name + "_ {\n\n")
                .append("\tpublic static String getTag(){\n")
                .append("\t\treturn \"" + tags[0] + "\";")
                .append("\t}\n")
                .append("}");

        try {
            JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(packageName + "." + name + "_");
            // Write contents in builder into file
            Writer writer = javaFileObject.openWriter();
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
