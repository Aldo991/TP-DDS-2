package ar.edu.utn.frba.dds.spark.template.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.GuavaTemplateCache;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.io.RuntimeIOException;
import spark.ModelAndView;
import spark.TemplateEngine;

public class HandlebarsTemplateEngine extends TemplateEngine {

  protected Handlebars handlebars;

  /**
   * Constructs a handlebars template engine
   */
  public HandlebarsTemplateEngine() {
    this("/templates");
  }

  /**
   * Constructs a handlebars template engine
   *
   * @param resourceRoot the resource root
   */
  public HandlebarsTemplateEngine(String resourceRoot) {
    TemplateLoader templateLoader = new ClassPathTemplateLoader();
    templateLoader.setPrefix(resourceRoot);
    templateLoader.setSuffix(null);

    handlebars = new Handlebars(templateLoader);

    // Set Guava cache.
    Cache<TemplateSource, Template> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES)
        .maximumSize(1000).build();

    handlebars = handlebars.with(new GuavaTemplateCache(cache));
  }

  @Override
  public String render(ModelAndView modelAndView) {
    String viewName = modelAndView.getViewName();
    try {
      Template template = handlebars.compile(viewName);

      return template.apply(modelAndView.getModel());
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeIOException(e);
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw e;
    }
  }
}
