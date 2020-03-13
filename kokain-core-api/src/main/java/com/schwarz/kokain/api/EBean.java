package com.schwarz.kokain.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface EBean {

    enum Scope {

        /**
         * A new instance of the bean is created each time it is needed.
         */
        Default, //

        //TODO scope implementation
//        /**
//         * A new instance of the bean is created the first time it is needed in an
//         * Activity scope, it is then retained and the same instance is always returned
//         * from within the same Activity. Different Activities hold different copies of
//         * the bean.
//         */
//        Activity,

        /**
         * A new instance of the bean is created the first time it is needed, it is then
         * retained and the same instance is always returned.
         */
        Singleton, //
    }

    /**
     * The scope of the enhanced bean.
     *
     * @return the scope of the bean.
     */
    Scope scope() default Scope.Default;
}
