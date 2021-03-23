package org.mentalizr.contentManagerCli.pathChecks;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

class PathChecksTest {

    @Test
    public void test() {

        PathChecks<IllegalArgumentException> pathChecks = new PathChecks<>();
        PathChecksExceptionFactory<IllegalArgumentException> exceptionFactory = new IllegalArgumentExceptionFactory();

        try {
            pathChecks.assertExistingDir(Paths.get("/home/dummy"), exceptionFactory);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testShort() {
        try {
            new PathChecks<IllegalArgumentException>().assertExistingDir(Paths.get("/home/dummy"), new IllegalArgumentExceptionFactory());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInnerClass() {
        try {
            new PathChecks<IllegalArgumentException>().assertExistingDir(Paths.get("/home/dummy"), new PathChecksExceptionFactory<>() {
                @Override
                public IllegalArgumentException createException(String message) {
                    return new IllegalArgumentException(message);
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLambda() {
        try {
            new PathChecks<IllegalArgumentException>().assertExistingDir(Paths.get("/home/dummy"),
                    (path) -> new IllegalArgumentException()
                );
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
