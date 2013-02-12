package ro.ieugen.fileserver;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.StringWriter;

public class MustacheTemplate {

    private final Mustache mustache;

    public MustacheTemplate(String template) {
        MustacheFactory mf = new DefaultMustacheFactory();
        this.mustache = mf.compile(template);
    }

    public Mustache getMustache() {
        return mustache;
    }

    public String render(Object... propertiesObject) throws IOException {
        StringWriter writer = new StringWriter();
        mustache.execute(writer, propertiesObject).flush();
        return writer.toString();
    }

}
