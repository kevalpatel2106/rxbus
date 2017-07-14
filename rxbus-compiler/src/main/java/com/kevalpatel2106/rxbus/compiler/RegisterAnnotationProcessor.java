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

package com.kevalpatel2106.rxbus.compiler;

import com.kevalpatel2106.rxbus.annotations.RegisterEvent;

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
    public static int COUNT = 1;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        Collection<? extends Element> annotationElements = roundEnvironment.getElementsAnnotatedWith(RegisterEvent.class);
        System.out.println("Types 1 annotation found in " + annotationElements.size());
        for (Element elem : roundEnvironment.getElementsAnnotatedWith(RegisterEvent.class)) {
            RegisterEvent annotation = elem.getAnnotation(RegisterEvent.class);
            write("com.kevalpatel2106.rxbussample",
                    elem.getSimpleName().toString(),
                    RegisterEvent.class.getSimpleName() + "_" + COUNT,
                    annotation.EVENT_TAG());
        }
        return false;

    }

    private void write(String packageName,
                       String methodName,
                       String className,
                       String[] tags) {
        if (packageName == null) return;

        StringBuilder builder = new StringBuilder();

        //Package statement
        builder.append("package " + packageName + ";\n\n");

        //Write imports
        builder.append("import com.kevalpatel2106.rxbus.Event;\n" +
                "import com.kevalpatel2106.rxbus.RxBus;\n" +
                "import io.reactivex.disposables.Disposable;\n" +
                "import io.reactivex.functions.Consumer;\n\n");

        //Start the class
        builder.append("public class " + className + " {\n\n");

        //Global variable
        builder.append("public Disposable mDisposable;\n\n");

        //Begin: Constructor
        builder.append("\tpublic " + className + "() {\n")
                .append("\t\tString[] tags = new String[" + tags.length + "];\n");

        //Load all the tags in array
        for (int i = 0; i < tags.length; i++)
            builder.append("\t\ttags[" + i + "] = \"" + tags[i] + "\";\n\n");

        //Getting disposable from RxBus
        builder.append("\t\tRxBus.getDefault().register(tags)\n")
                .append("\t\t\t.doOnSubscribe(new Consumer<Disposable>() {\n")
                .append("\t\t\t\t@Override\n")
                .append("\t\t\t\tpublic void accept(Disposable disposable) throws Exception {\n")
                .append("\t\t\t\t\tmDisposable = disposable;\n")
                .append("\t\t\t\t}\n")
                .append("\t\t\t});\n");


        builder.append("\t}\n");
        //End: Constructor

        //Get tag methods
        builder.append("\n")
                .append("\tpublic static String getTag(){\n")
                .append("\t\treturn \"" + tags[0] + "\";\n")
                .append("\t}\n");

        //End of class
        builder.append("}");

        System.out.println(builder.toString());


//                .subscribe(new Consumer<Event>() {
//                    @Override
//                    public void accept(@NonNull Event event) throws Exception {
//                        Point point = (Point) event.getObject();
//                        textView.setText(String.format(Locale.getDefault(),
//                                "Fragment One clicked at (%d,%d).", point.x, point.y));
//                    }
//                });

        try {
            JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(packageName + "." + className);
            // Write contents in builder into file
            Writer writer = javaFileObject.openWriter();
            writer.write(builder.toString());
            writer.close();

            COUNT++;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
