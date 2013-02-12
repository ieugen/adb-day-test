package ro.ieugen.fileserver;

import com.google.common.collect.ImmutableList;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class MustacheTemplateTest {

    private static final String title = UUID.randomUUID().toString();
    private static final String SIMPLE_OBJECT_TEMPLATE = "simple-object.template";
    private static final String ARRAY_TEMPLATE = "array.template";

    private List<Item> items = ImmutableList.of(new Item(1), new Item(2), new Item(3));

    @Test
    public void testSimpleObjectRender() throws Exception {
        MustacheTemplate template = new MustacheTemplate(SIMPLE_OBJECT_TEMPLATE);
        String result = template.render(this);
        assertThat(result).contains(title)
                .contains(String.format("<h1>%s</h1>", title));
    }

    @Test
    public void testArrayTemplateRender() throws Exception {
        MustacheTemplate template = new MustacheTemplate(ARRAY_TEMPLATE);
        String result = template.render(this);
        assertThat(result).contains("Item has id: 1").contains("Item has id: 3");
    }

    public String getTitle() {
        return title;
    }

    public List<Item> getItems() {
        return items;
    }

    static class Item {
        private final int id;

        Item(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
