package com.example.basicbeans;

import com.example.basicbeans.hello.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * DemoApplication is a @Component (drill down in the @SpringBootApplication to find it) therefore
 * an instance of this class is created when the IoC Container is started.
 */
@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

	public static void main(String[] args) {

		/**
		 * This will start the IoC Container and create a Bean of type ApplicationContext along with a
		 * few others that you'll notice get printed out when this application runs.
		 *
		 * It will also scan and detect the PersonConfig configuration file to create the Beans
		 * defined within that class as well.
		 */
		SpringApplication.run(DemoApplication.class, args);

	}

    /**
     * Spring will automatically Dependency Inject dependencies properties marked
     * with @Autowired. It is discouraged and you should use constructor injection
     * but here it is for demonstration purposes.
     */
	@Autowired
	private ApplicationContext appContext;

    /**
     * You shouldn't do anything in public static void main other than start the
     * Spring container. Spring provides an interface called ApplicationRunner
     * that will provide you the same experience but now you have access to your
     * beans. For example appContext is null during the execution time of your
     * main method but it is available here.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Beans:");
        Arrays.stream(appContext.getBeanDefinitionNames()).forEach(System.out::println);

        System.out.println("===================");
		System.out.println("Demonstrate how you can use the applicationContext directly to retrieve beans also.");
		System.out.println("HelloService: " + appContext.getBean("helloService", HelloService.class).sayHello("Steven"));
    }
}
