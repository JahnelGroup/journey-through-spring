---
title:  "Validation"
permalink: /basics/validation
---

Before using a piece of a data you typically want to [validate](https://en.wikipedia.org/wiki/Data_validation){:target="_blank"} it first. Validation takes many forms and really depends on where the data is coming from and where the validation check is being done. If you're passing data from one class to another via a function call then maybe some light validations are warranted (i.e., null checks) but if you're receiving data from an external source you typically need to scrutinize it further.

Spring provides a light specification around validating Beans with a lot of the implementation provided by Hibernate.

<i class='far fa-bookmark'></i> Read: [**spring.io:** Validation](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#validation){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**hibernate.org:** Hibernate Validators](http://hibernate.org/validator/){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**tutorialspoint.com:** Spring MVC - Hibernate Validator Example](https://www.tutorialspoint.com/springmvc/springmvc_hibernate_validator.htm){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**mkyong.com:** Combine Spring validator and Hibernate validator](https://www.mkyong.com/spring-mvc/combine-spring-validator-and-hibernate-validator/){:target="_blank"}
* **Exercise:** [**github.com:** Journey Through Spring: Basic Web Validations](https://github.com/JahnelGroup/journey-through-spring/tree/master/src/basic-web-validations){:target="_blank"}

## The Trash/Error Bag Pattern
Although not an official *design pattern* Spring's validation framework follows a technique called an **error bag**. The idea is named well because you basically create a "trash bag" object that you pass around to interested parties that will put their "trash" or errors into the bag. Afterwards you inspect the trash bag for errors and handle them accordingly. 

* The trash bag is an instance of [Errors](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/validation/Errors.html){:target="_blank"}.
* The interested parties your implementations of [Validators](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/validation/Validator.html){:target="_blank"}.

## The Validator Interface
The whole point of the Validator interface is to validate an object. Here is an example of a simple Person POJO.

```java
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Person {
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
}
```

> I used a library here called [Lombok](https://projectlombok.org/){:target="_blank"} that helps generate boiler plate code like getters, setters, hashcode, equals and toString. 

We can create a Validator to validate a Person given whatever business requirements we have. For the purpose of brevity I'm only going to show one validation here. You can view the entire [UserValidator.java](https://github.com/JahnelGroup/journey-through-spring/blob/master/src/basic-web-validations/src/main/java/com/example/basicwebvalidations/by_interface/UserValidator.java){:target="_blank"} in the Journey Through Spring exercise. 

```java
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(person.getAge() != null && person.getAge() < 21){
           errors.rejectValue(
              // the first parameter is the name of the field we are validating, reflection 
              // will be used to get it's value.
              "age", 
              
              // the second parameter is an arbitrary errorcode that I came up with to 
              // represent a failure in this validation. 
              "underage", 
              
              // the third parameter is the default human-readble text that represents this
              // type of failure when the errorcode lookup cannot be resolved.
              "You are not old enough."
           );
        }
    }

}
```

> Error codes are discussed below and they are typically used to lookup the human-readable message text from a [MessageSource](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/MessageSource.html){:target="_blank"}.

## Running the Validator

To run the validator you need an implemention of [Errors](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/validation/Errors.html){:target="_blank"}. There are a few implementations and we'll use [BeanPropertyBindingResult](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/validation/BeanPropertyBindingResult.html){:target="_blank"} because thats what Spring MVC would normally provide you. 

```java
Errors result = new BeanPropertyBindingResult(person, "person");
userValidator.validate(person, result);
if( result.hasErrors() ){
    result.getAllErrors().stream().forEach(System.out::println);
}
```

After running a Validator the Errors bag can be inspected for results. You can even pass the Errors bag to multiple validators.

```java
Errors result = new BeanPropertyBindingResult(person, "person");
personNameValidator.validate(person, result);
personAgeValidator.validate(person, result);
// etc

// now you have a collection of errors from all the validators
if( result.hasErrors() ){
    result.getAllErrors().stream().forEach(System.out::println);
}
```

Think about separating validators as it may or may not be a good idea depending on what you're trying to accomplish.

## Validation with Annotations (JSR-303)

It is also possible to define validations as annotations directly on the properties of a Bean. This can be convenient for simple validations like null checks, lengths, or even more complicated custom validations. 

```java
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class Pet {

    @NotNull
    @Size(min = 2, max = 25)
    private String name;

    @NotNull
    private String type;

    @NotNull
    @Max(30)
    private Integer age;

    @NotNull
    private Integer weight;

}
```

Now you just need to apply the [@Valid](https://docs.oracle.com/javaee/7/api/javax/validation/Valid.html){:target="_blank"} annotation on your payload in a Spring Controller to have Spring run the JSR-303 annotation validations.

```java
@PostMapping("/pets")
public Pet addPet(@RequestBody @Valid Pet pet){
    // code
}
```

We haven't defined anyway to inspect the Errors so Spring will return a payload that looks like this by default if a validation fails. 

```json
{
    "error": "Bad Request",
    "errors": [
        {
            "arguments": [
                {
                    "arguments": null,
                    "code": "type",
                    "codes": [
                        "pet.type",
                        "type"
                    ],
                    "defaultMessage": "type"
                }
            ],
            "bindingFailure": false,
            "code": "NotNull",
            "codes": [
                "NotNull.pet.type",
                "NotNull.type",
                "NotNull.java.lang.String",
                "NotNull"
            ],
            "defaultMessage": "must not be null",
            "field": "type",
            "objectName": "pet",
            "rejectedValue": null
        }
    ]
}
```

If you want to do something with the Errors then have Spring inject a [BindingResult](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/validation/BindingResult.html){:target="_blank"} object for you (it extends Errors). Spring will handle the binding in the same way you did it before manually.

```java
@PostMapping("/pets")
public Pet addPet(@RequestBody @Valid Pet pet, BindingResult result){
    if( result.hasErrors() ){
        result.getAllErrors().stream().forEach(System.out::println);
    }
    // code
}
```

<i class='far fa-bookmark'></i> Read: [**jcp.org:** JSR 303: Bean Validation](https://jcp.org/en/jsr/detail?id=303){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**wikipedia.org:** Bean Validation](https://en.wikipedia.org/wiki/Bean_Validation){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**developer.ucsd.edu:** Java Validation API (JSR-303)](https://developer.ucsd.edu/develop/data-layer/jpa/java-validation-api.html){:target="_blank"}

### Custom JSR-303 Annotation Validator

To make a custom JSR-303 validation you need two things - an annotation and a corresponding [ConstraintValidator](https://docs.oracle.com/javaee/7/api/javax/validation/ConstraintValidator.html){:target="_blank"}.

Here is an example of a custom annotation called **@PetId** that we'll use to validate that a Pet ID takes the format of 5 digits followed by a dash then two letters (i.e., 12345-AZ).

```java
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PetIdNumberValidator.class)
public @interface PetId {
    String message() default "{com.example.PetId.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

Here is the corresponding [ConstraintValidator](https://docs.oracle.com/javaee/7/api/javax/validation/ConstraintValidator.html){:target="_blank"}.

```java
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PetIdNumberValidator implements
        ConstraintValidator<PetId, String> {

    @Override
    public void initialize(PetId petId) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("[0-9]{5}-[A-Z]{2}")
                && (value.length() > 3) && (value.length() < 14);
    }

}
```

> **Important**: Spring will initialize ConstraintValidator's as a **Singletons** so only one instance will ever be created for each type. **Do not** store state anywhere here.

<i class='far fa-bookmark'></i> Read: [**baeldung.com:** Spring MVC Custom Validation](http://www.baeldung.com/spring-mvc-custom-validator){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**dolszewski.com:** Custom validation annotation in Spring](http://dolszewski.com/spring/custom-validation-annotation-in-spring/){:target="_blank"}

## ErrorCode, MessageSource and ResourceBundle
Externalization of error messages is just as important as application properties. The process by which you do this is slightly different, instead of *properties* you have *messages* and instead of calling them *property files* they are *resource bundles*. This is an important step to making your system maintainable and support localization. To accomplish this you need a file somewhere in your classpath that is formatted as a key/value pair and loaded into the system as a ResourceBundle.

The entire process of mapping codes to message text is called message interpolation.

<i class='far fa-bookmark'></i> Read: [**spring.io:** Internationalization using MessageSource](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#context-functionality-messagesource){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**baeldung.com:** Spring Boot Internationalization](http://www.baeldung.com/spring-boot-internationalization){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**wikipedia.org:** Java Resource Bundle](https://en.wikipedia.org/wiki/Java_resource_bundle){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**oracle.com:** ResourceBundle](https://docs.oracle.com/javase/7/docs/api/java/util/ResourceBundle.html){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**oracle.com:** ResourceBundle Concept](https://docs.oracle.com/javase/tutorial/i18n/resbundle/concept.html){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**jboss.org:** Message Interpolation](http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/#chapter-message-interpolation){:target="_blank"}

### Hibernate Validation.properties
Hibernate validations will by default source their *defaultMessage* from a file on the classpath provided by Hibernate Validator called **Validation.properties**.

### messages.properties convention

There are several ways to load a bundle and Spring provides a very convenient property called *spring.messages.basename* where you can set the location of the files. Spring Boot is smart enough to assume convention of course so by default it will look for **messages.properties** and load that as a resource bundle for you. Here is an example of a simple resource bundle:

> Bundles support Locale change with the format messages_XX.properties, where XX is the locale code.

**src/main/resources/messages.properties**
```properties
# Override default Spring Validation Field Error Messages
field.required=Required field
field.max_length=Field cannot be greater than {0} characters
field.min_length=Field cannot be less than {0} characters

underage=You are only {0}, you must be at least 21!
```

**This is currently broken in Spring.**

## API Error Framework
If you're building a more sophisticated API then you would want more control over how the error responses are generated. This means mapping every type of exception into your own *ApiError* facade. 

<i class='far fa-bookmark'></i> Read: [**baeldung.com:** Global Error Handler in Spring REST API](http://www.baeldung.com/global-error-handler-in-a-spring-rest-api){:target="_blank"}
