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

package rxbus;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

/**
 * Created by Keval on 07-Jul-17.
 */

@SupportedAnnotationTypes("rxbus.RegisterEvent")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RegisterEventProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Collection<? extends Element> annotationElements = roundEnvironment.getElementsAnnotatedWith(RegisterEvent.class);
        List<TypeElement> typeElements = ElementFilter.typesIn(annotationElements);

        System.out.println("Types " + typeElements.size());
        String packageName = null;
        String[] names = null;
        for (TypeElement type : typeElements) {
            PackageElement packageElement = (PackageElement) type.getEnclosingElement();
            packageName = packageElement.getQualifiedName().toString();
            System.out.println("Package " + packageName);
            names = type.getAnnotation(RegisterEvent.class).EVENT_TAG();
        }

        if (packageName == null) return false;

        StringBuilder builder = new StringBuilder()
                .append("package " + packageName + ";")
                .append("public class RegisterEvent1 {\n\n")
                .append("\tpublic static String getTag(){\n")
                .append("\t\treturn " + names[0] + ";")
                .append("\t}")
                .append("}");


        try {
            JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(packageName + ".RegisterEvent1");
            // Write contents in builder into file
            Writer writer = javaFileObject.openWriter();
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
