package com.airline.locationservice;

import com.intuit.karate.junit5.Karate;
// import org.junit.jupiter.api.Test;
// import org.junit.BeforeClass;
// import org.junit.runner.RunWith;

public class KarateRunner {

    @Karate.Test
    public Karate testAll() {
        System.out.println( "Run Karate Integration Tests");
        return new Karate().relativeTo(getClass());
    }
    
    // @Karate.Test
    // Karate testSample() {
    //     System.out.println( "Run sample Test");
    //     return new Karate().feature("AirportCodeIata").relativeTo(getClass());
    // }
    
    // @Karate.Test
    // Karate testTags() {
    //     return new Karate().feature("tags").tags("@second").relativeTo(getClass());
    // }

    // @Karate.Test
    // Karate testFullPath() {
    //     return new Karate()
    //             .feature("classpath:karate/tags.feature")
    //             .tags("@first");
    // }

}