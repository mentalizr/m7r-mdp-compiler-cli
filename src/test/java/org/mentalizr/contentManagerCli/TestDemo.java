package org.mentalizr.contentManagerCli;

import org.junit.jupiter.api.Test;
import org.mentalizr.contentManagerCli.console.OSDetector;

import static org.junit.jupiter.api.Assertions.*;

public class TestDemo {

    @Test
    public void test() {
        System.out.println("Test1");
        System.out.println(System.getProperty("os.name").toLowerCase());

        System.out.println("Running on: " + OSDetector.getOS());

        String property = System.getProperty("not.existing");
        assertNull(property);
    }

}
