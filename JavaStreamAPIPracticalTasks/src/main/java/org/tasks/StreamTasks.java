package org.tasks;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;

public class StreamTasks {

    // Necessary value record class, assuming this is defined already somewhere in the project
    record Prop(UUID id, String name, int value) {}

    // Stream Generation (5 points)
    public static Stream<Prop> generate(int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> new Prop(UUID.randomUUID(), "name" + i, i));
    }

    // Flattening (5 points)
    public static Stream<UUID> toIds(List<List<Prop>> nestedProps) {
        return nestedProps.stream()
                .flatMap(Collection::stream)
                .map(Prop::id);
    }

    // Counts (5 points)
    public static long countEvenNumbers(Stream<Integer> numbers) {
        return numbers.filter(i -> i % 2 == 0)
                .count();
    }

    // Shortening Operations (7 points)
    public static Stream<Character> firstWord(Stream<Character> characters) {
        StringBuilder sb = new StringBuilder();
        Iterator<Character> iterator = characters.iterator();
        while (iterator.hasNext()) {
            char c = iterator.next();
            if (Character.isWhitespace(c)) {
                break;
            }
            sb.append(c);
        }
        return sb.toString().chars().mapToObj(c -> (char) c);
    }

    // Indirect Mapping (7 points)
    public static Stream<String> nonNullProps(Stream<Prop> props) {
        return props.flatMap(prop -> Stream.of(
                Optional.ofNullable(prop.id()).map(UUID::toString),
                Optional.ofNullable(prop.name()),
                Optional.of(Integer.toString(prop.value()))
        ).flatMap(Optional::stream));
    }

    // Sorting (7 points)
    public static List<Prop> sortProps(Stream<Prop> props) {
        return props.sorted(Comparator.comparingInt(Prop::value)
                        .thenComparing(Prop::name, Comparator.nullsFirst(String::compareTo)))
                .collect(Collectors.toList());
    }

    // Filter by Property (7 points)
    public static List<Prop> filterNotNullName(Stream<Prop> props) {
        return props.filter(prop -> prop.name() != null)
                .collect(Collectors.toList());
    }

    // Stateful filter (10 points)
    public static List<Prop> filterDuplicateIds(Stream<Prop> props) {
        Set<UUID> seen = new HashSet<>();
        return props.filter(prop -> seen.add(prop.id()))
                .collect(Collectors.toList());
    }

    // Aggregation (8 points)
    public static Optional<String> getNameOfHighestValue(Stream<Prop> props) {
        return props.max(Comparator.comparingInt(Prop::value))
                .map(Prop::name);
    }

    // Combining Collectors (7 points)
    public static Map<String, List<Prop>> detectNameConflicts(Stream<Prop> props) {
        return props.collect(Collectors.groupingBy(Prop::name)).entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // Stateful Collectors (15 points)
    public static Map<String, Integer> sumValuesByName(Stream<Prop> props) {
        return props.collect(Collectors.groupingBy(Prop::name, Collectors.summingInt(Prop::value)));
    }

    // Collector Chaining (7 points)
    public static Map<String, Integer> sumOfOddAndEven(Stream<Integer> numbers) {
        return numbers.collect(Collectors.partitioningBy(i -> i % 2 == 0, Collectors.summingInt(i -> i)))
                .entrySet().stream().collect(Collectors.toMap(
                        entry -> entry.getKey() ? "even" : "odd", Map.Entry::getValue));
    }

    // Custom Aggregation (10 points)
    public static Map<String, Optional<String>> highestLowestValueNames(Stream<Prop> props) {
        Map<String, Optional<Prop>> maxProp = props.collect(Collectors.groupingBy(Prop::name,
                Collectors.maxBy(Comparator.comparingInt(Prop::value))));
        Map<String, Optional<String>> resultMap = new HashMap<>();
        maxProp.forEach((k, v) -> resultMap.put(k, v.map(Prop::name)));
        return resultMap;
    }

    // Functional Transformation (Optional)
    public static <T> Function<T, T> fold(Stream<Function<T, T>> functions) {
        return functions.reduce(Function.identity(), Function::andThen);
    }

    // Advanced Collectors (Optional)
    public static <T, RT, AT, RF, AF, R> Collector<T, ?, R> partitioningCollector(
            Predicate<? super T> predicate,
            Collector<? super T, AT, RT> collTrue,
            Collector<? super T, AF, RF> collFalse,
            BiFunction<RT, RF, R> constructor) {

        return Collector.of(
                () -> new Tuple<>(collTrue.supplier().get(), collFalse.supplier().get()),
                (tuple, item) -> {
                    if (predicate.test(item)) {
                        collTrue.accumulator().accept(tuple.left, item);
                    } else {
                        collFalse.accumulator().accept(tuple.right, item);
                    }
                },
                (tuple1, tuple2) -> new Tuple<>(
                        collTrue.combiner().apply(tuple1.left, tuple2.left),
                        collFalse.combiner().apply(tuple1.right, tuple2.right)),
                tuple -> constructor.apply(collTrue.finisher().apply(tuple.left), collFalse.finisher().apply(tuple.right))
        );

    }

    public static class Tuple<L, R> {
        public final L left;
        public final R right;

        public Tuple(L left, R right) {
            this.left = left;
            this.right = right;
        }
    }

    public static Map<String, Object> sumAndNullCount(Stream<Integer> numbers) {
        Collector<Integer, ?, Map<String, Object>> collector = partitioningCollector(
                Objects::nonNull,
                Collectors.summingInt(i -> i),
                Collectors.counting(),
                (sum, count) -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("sum", sum);
                    result.put("nullCount", count);
                    return result;
                });

        return numbers.collect(collector);
    }

    public static void main(String[] args) {
        // Sample test cases to verify the methods

        // Generate test
        System.out.println("Stream Generation");
        Stream<Prop> generatedProps = generate(10);
        generatedProps.forEach(System.out::println);
        System.out.println();

        // Flatten test
        System.out.println("Flattening");
        List<List<Prop>> nestedLists = List.of(
                List.of(new Prop(UUID.randomUUID(), "name1", 1), new Prop(UUID.randomUUID(), "name2", 2)),
                List.of(new Prop(UUID.randomUUID(), "name3", 3), new Prop(UUID.randomUUID(), "name4", 4))
        );
        Stream<UUID> ids = toIds(nestedLists);
        ids.forEach(System.out::println);
        System.out.println();

        // Count test
        System.out.println("Counts");
        Stream<Integer> integers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println(countEvenNumbers(integers));  // Should print 4
        System.out.println();

        // Shorten test
        System.out.println("Shortening Operations");
        Stream<Character> characters = Stream.of('t', 'e', 's', 't', ' ', 'w', 'o', 'r', 'd');
        firstWord(characters).forEach(System.out::print);  // Should print "test"
        System.out.println();

        // Indirect mapping test
        System.out.println("\nIndirect Mapping");
        Stream<String> nonEmpty = nonNullProps(generatedProps = generate(5));
        nonEmpty.forEach(System.out::print);  // Should print non-null properties
        System.out.println();

        // Sorting test
        System.out.println("\nSorting");
        List<Prop> sortedProps = sortProps(generate(5));
        sortedProps.forEach(System.out::println);
        System.out.println();

        // Filter by Property test
        System.out.println("Filter by Property");
        List<Prop> nonNullNameProps = filterNotNullName(generate(5));
        nonNullNameProps.forEach(System.out::println);
        System.out.println();

        // Stateful filter test
        System.out.println("Stateful filter");
        List<Prop> uniqueProps = filterDuplicateIds(generate(10));
        uniqueProps.forEach(System.out::println);
        System.out.println();

        // Aggregation test
        System.out.println("Aggregation");
        Optional<String> highestValueName = getNameOfHighestValue(generate(10));
        highestValueName.ifPresent(System.out::println);
        System.out.println();

        // Combining Collectors test
        System.out.println("Combining Collectors");
        List<Prop> props = List.of(
                new Prop(UUID.randomUUID(), "name1", 1),
                new Prop(UUID.randomUUID(), "name2", 2),
                new Prop(UUID.randomUUID(), "name1", 3),
                new Prop(UUID.randomUUID(), "name3", 4)
        );
        Map<String, List<Prop>> nameConflicts = detectNameConflicts(props.stream());
        nameConflicts.forEach((name, propList) -> {
            System.out.println(name + ": " + propList);
        });
        System.out.println();

        // Stateful Collectors test
        System.out.println("Stateful Collectors");
        Map<String, Integer> sumValuesMap = sumValuesByName(props.stream());
        sumValuesMap.forEach((name, sum) -> {
            System.out.println(name + ": " + sum);
        });
        System.out.println();

        // Collector Chaining test
        System.out.println("Collector Chaining");
        Stream<Integer> numberStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Map<String, Integer> sums = sumOfOddAndEven(numberStream);
        sums.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });
        System.out.println();

        // Custom Aggregation test
        System.out.println("Custom Aggregation");
        Map<String, Optional<String>> highestAndLowestValueNames = highestLowestValueNames(props.stream());
        highestAndLowestValueNames.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });
        System.out.println();

        // Functional Transformation test
        System.out.println("Functional Transformation");
        Function<String, String> f = fold(Stream.of(String::trim, s -> s.replace('b', 'c')));
        System.out.println(f.apply("  bat"));  // Prints "cat"
        System.out.println();

        // Advanced Collectors test
        System.out.println("Advanced Collectors");
        Stream<Integer> numbers = Stream.of(1, 2, 3, null, 4, 5, null);
        Map<String, Object> result = sumAndNullCount(numbers);
        System.out.println(result);  // Prints {sum=15, nullCount=2}
    }
}