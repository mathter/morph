package io.github.mathter.morph.data;

import io.github.mathter.morph.path.Path;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JPathMapTest {
    @Test
    public void testCreate() {
        final Path title = Path.of("title");
        final String titleValue = RandomStringUtils.insecure().nextAlphabetic(10);

        final Path authors = Path.of("authors");
        final Path authorName = Path.of("a/name");
        final Path authorLastName = Path.of("a/lastName");

        final String authorName0 = RandomStringUtils.insecure().nextAlphabetic(10);
        final String authorLastName0 = RandomStringUtils.insecure().nextAlphabetic(10);

        final String authorName1 = RandomStringUtils.insecure().nextAlphabetic(10);
        final String authorLastName1 = RandomStringUtils.insecure().nextAlphabetic(10);

        final Path isbn = Path.of("isbn");
        final String isbnValue = RandomStringUtils.insecure().nextAlphabetic(10);

        final Path year = Path.of("year");
        final int yearValue = 2024;

        final JPathMap bookPathMap = PathMap.jempty();

        bookPathMap.put(title, titleValue);

        final PathMap author0PathMap = PathMap.empty();
        author0PathMap.put(authorName, authorName0);
        author0PathMap.put(authorLastName, authorLastName0);
        bookPathMap.put(authors, author0PathMap);

        final PathMap author1PathMap = PathMap.empty();
        author1PathMap.put(authorName, authorName1);
        author1PathMap.put(authorLastName, authorLastName1);
        bookPathMap.put(authors, author1PathMap);

        bookPathMap.put(isbn, isbnValue);
        bookPathMap.put(year, yearValue);

        Assertions.assertEquals(Opt.of(titleValue), bookPathMap.get(title));

        final Opt<?> authorsResultOpt = bookPathMap.get(authors);
        Assertions.assertNotNull(authorsResultOpt);
        Assertions.assertTrue(authorsResultOpt.isPresent());
        Assertions.assertTrue(authorsResultOpt.get() instanceof List);

        final List<PathMap> authorsResult = (List<PathMap>) authorsResultOpt.get();
        Assertions.assertEquals(2, authorsResult.size());

        final PathMap author0Result = authorsResult.get(0);
        Assertions.assertNotNull(author0Result);
        Assertions.assertEquals(Opt.of(authorName0), author0Result.get(authorName));
        Assertions.assertEquals(Opt.of(authorLastName0), author0Result.get(authorLastName));

        final PathMap author1Result = authorsResult.get(1);
        Assertions.assertNotNull(author1Result);
        Assertions.assertEquals(Opt.of(authorName1), author1Result.get(authorName));
        Assertions.assertEquals(Opt.of(authorLastName1), author1Result.get(authorLastName));

        Assertions.assertEquals(Opt.of(isbnValue), bookPathMap.get(isbn));
        Assertions.assertEquals(Opt.of(yearValue), bookPathMap.get(year));
    }
}
