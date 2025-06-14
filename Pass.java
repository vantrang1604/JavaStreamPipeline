import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;

public class Pass {
    public static void main(String[] args) throws Exception {
        // Open the file for reading as a Stream
        Stream.of(1)
        .forEach(number -> {
            try {
                Stream<String> lines = Files.lines(Paths.get("data/pass.txt"));
                long count = Arrays.stream(lines.collect(Collectors.joining("\n")).split("\n{2}")).map(r -> {
                    Map<String, String> obj = new HashMap<>();
                    Arrays.stream(r.replace("\n", " ")
                            .split(" "))
                            .forEach(str -> {
                                String[] parts = str.split(":");
                                obj.put(parts[0], parts[1]);
                            });
                    return obj;
                })
                // Only get those that does not has missing fields.
                .filter(obj -> obj.get("born") != null && obj.get("issued") != null
                        && obj.get("expires") != null
                        &&
                        obj.get("height") != null && obj.get("hair") != null && obj.get("eyes") != null
                        && obj.get("usmca") != null)

                // Get license that is at least 21 years old
                .filter(obj -> 2024 - Integer.parseInt(obj.get("born")) >= 21)

                // Filter out licenses issued more than 10 years ago or in the future
                .filter(obj -> {
                    return Integer.parseInt(obj.get("issued")) <= 2024
                            && 2024 - Integer.parseInt(obj.get("issued")) <= 10;
                })

                // Filter out licenses that have expired or have an expiry date more than 10
                // years into the future
                .filter(obj -> {
                    return Integer.parseInt(obj.get("expires")) >= 2024
                            && Integer.parseInt(obj.get("expires")) - 2024 <= 10;
                })

                // Filter out licenses with inappropriate height
                .filter(obj -> {
                    String height = obj.get("height");
                    if (height.endsWith("cm")) {
                        int heightInCm = Integer.parseInt(height.replace("cm", ""));
                        return heightInCm >= 150 && heightInCm <= 193;
                    } else if (height.endsWith("in")) {
                        int heightInInches = Integer.parseInt(height.replace("in", ""));
                        return heightInInches >= 59 && heightInInches <= 76;
                    }
                    return false;
                })

                // Validate hair color
                .filter(obj -> Pattern.matches("#[0-9a-fA-F]{6}", obj.get("hair")))

                // Validate eye color
                .filter(obj -> List.of("amber", "blue", "brown", "gray", "green", "hazel", "other")
                        .contains(obj.get("eyes")))

                // Validate the license ID format
                .filter(obj -> Pattern.matches("\\d{9}", obj.get("usmca")))
                // Print each valid record separated by a dashed line
                .peek(obj -> {
                    System.out.println("-".repeat(132));
                    System.out.println(obj);
                })
                .count();
                System.out.println("=".repeat(132));
                System.out.println("Valid records: " + count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

