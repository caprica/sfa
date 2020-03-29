import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.RouteMatcher;
import org.springframework.util.SimpleRouteMatcher;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// seems like "/" is not allowed in Spring RequestMapping regex according to random internet comment

public class TheTest {

    private static final RouteMatcher pathPatternRouteMatcher = new PathPatternRouteMatcher();
    private static final RouteMatcher antRouteMatcher = new SimpleRouteMatcher(new AntPathMatcher());

    private static final String ANT1 = "/{x:[\\w\\-]+}";

    private static final String ANT2  = "/{p:^(?!api$).*$}/**/{q:[\\w\\-]+}";
//    private static final String PATH1 = "/{p:^(?!api$).*$}/**/{q:[\\w\\-]+}";
//    private static final String PATH1 = "/api/{path:.+}";
    private static final String PATH1 = "/^(?!api)/.*{path:.+}";

//    private static final String ANTX = "{a:/api/.*?}";

    // original was this
    // {path:(?:(?!api|.).)*}/**

    // this works because the "." is used in place of the "/" which is seemingly not allowed in spring regex here
    // but it means apix api- and so on would not be handled
    private static final String ANTX = "/{path:(?:(?!api).)*}/**";

    // Match:
    //  /a
    //  /a/b
    //  /a/b/c
    //
    // No Match:
    //  /api/a
    //  /api/a/b
    //  /api/a/b/c

    @Test
    public void x() {
        String pattern1 = "^(/api/.+)$";

        assertTrue("/api/a".matches(pattern1));
        assertTrue("/api/a/b/c".matches(pattern1));

        String pattern2 = "^(?!(/api/.+)$).*$";
//        String pattern2 = "(?!(/api/.+)$).*";

        assertTrue("/a".matches(pattern2));
        assertTrue("/a/b/c".matches(pattern2));
        assertTrue("/api".matches(pattern2));
    }

    @Test
    public void antRoutesMatchedCorrectly() {
        assertTrue(testAntRoute(ANT1, "/a"));
        assertTrue(testAntRoute(ANT1, "/b"));
        assertTrue(testAntRoute(ANT1, "/api"));
//        assertTrue(testAntRoute(ANT2, "/a?x=1&y=2"));

        assertTrue(testAntRoute(ANT2, "/a/b"));
        assertTrue(testAntRoute(ANT2, "/a/b/c/d/e"));
//        assertTrue(testAntRoute(ANT2, "/a/b?x=1&y=2"));

        assertFalse(testAntRoute(ANT2, "/api/a"));
        assertFalse(testAntRoute(ANT2, "/api/a/b"));

        //        assertFalse(testAntRoute(ANT2, "/api/a/b/c/d/e"));
    }

    @Test
    public void antRoutesMatchedCorrectlyNew() {
//        assertTrue(testAntRoute(ANTX, "/a"));
//        assertTrue(testAntRoute(ANTX, "/b"));
//        assertTrue(testAntRoute(ANTX, "/api"));
////        assertTrue(testAntRoute(ANTX, "/a?x=1&y=2"));
//
//        assertTrue(testAntRoute(ANTX, "/a/b"));
//        assertTrue(testAntRoute(ANTX, "/a/b/c/d/e"));
////        assertTrue(testAntRoute(ANTX, "/a/b?x=1&y=2"));

//        assertTrue(testAntRoute(ANTX, "/api/a"));
//        assertTrue(testPathRoute(ANTX, "/api/a/b"));

        assertTrue(testAntRoute(ANTX, "/bcd"));
        assertTrue(testAntRoute(ANTX, "/bcd/efg"));
        assertTrue(testAntRoute(ANTX, "/bcd/efg/hij"));

        assertFalse(testAntRoute(ANTX, "/api/bcd"));
        assertFalse(testAntRoute(ANTX, "/api/bcd/efg"));
        assertFalse(testAntRoute(ANTX, "/api/bcd/efg/hij"));

        assertTrue(testAntRoute(ANTX, "/apix/bcd"));
        assertTrue(testAntRoute(ANTX, "/apix/bcd/efg"));
        assertTrue(testAntRoute(ANTX, "/apix/bcd/efg/hij"));


        //              assertFalse(testAntRoute(ANT2, "/apix/a"));
//              assertTrue(testAntRoute(ANTX, "/api/"));
    }

    //    @Test
    public void pathPatternRoutesMatchedCorrectly() {
//        assertTrue(testPathRoute(PATH1, "/a"));
//        assertTrue(testPathRoute(PATH1, "/b"));
//        assertTrue(testPathRoute(PATH1, "/api"));
//        assertTrue(testAntRoute(PATH1, "/a?x=1&y=2"));

//        assertTrue(testPathRoute(PATH1, "/a/b"));
//        assertTrue(testPathRoute(PATH1, "/a/b/c/d/e"));
//        assertTrue(testAntRoute(PATH1, "/a/b?x=1&y=2"));

        assertTrue(testPathRoute(PATH1, "/api/a/b"));
//        assertTrue(testPathRoute(PATH1, "/api/a"));
//        assertFalse(testAntRoute(PATH1, "/api/a/b/c/d/e"));
    }

    private static boolean testAntRoute(String pattern, String route) {
        RouteMatcher.Route r = antRouteMatcher.parseRoute(route);
        System.out.println("ROUTE: " + r);
        boolean match = antRouteMatcher.match(pattern, r);
        System.out.println(antRouteMatcher.matchAndExtract(pattern, r));
        return match;
    }

    private static boolean testPathRoute(String pattern, String route) {
        return pathPatternRouteMatcher.match(pattern, pathPatternRouteMatcher.parseRoute(route));
    }
}
