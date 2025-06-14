import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Distinction {
    public static void main(String[] args) throws IOException {
        // Stream of lines from the file
        Stream.of(1)
        .forEach(number -> {
            try{
                Stream<String> lines = Files.lines(Paths.get("data/distinction.txt"));
                // Set of possible seat IDs
                ArrayList<String> seatIds = new ArrayList<>();
                AtomicInteger max = new AtomicInteger(0);
                int coachMissing = 0;
                int deckMissing = 0;
                int rowMissing = 0;
                int columnMissing = 0;
                int seatIDMissing = 0;
                AtomicReference<String> missingTicket = new AtomicReference<>("No missing ticket found");
                ArrayList<String> unavailableSeats = new ArrayList<>();
                // Get all the UNAVAILABLE seats
                IntStream.range(0, 8).forEach(i -> {
                    String coach1 = String.format("%3s", Integer.toBinaryString(i)).replace(' ', '0')
                            .replace("0", "L").replace("1", "R");
                    IntStream.range(0, 2).forEach(j -> {
                        String deck1 = j == 0 ? "D" : "U";
                            if(deck1 == "D"){
                                IntStream.range(0, 16).forEach(k -> {
                                    if(k == 0||k==15){
                                    String row1 = String.format("%4s", Integer.toBinaryString(k)).replace(' ', '0')
                                            .replace("0", "F").replace("1", "B");
                                            IntStream.range(0, 4).forEach(l -> {
                                                String column1 = String.format("%2s", Integer.toBinaryString(l)).replace(' ', '0')
                                                        .replace("0", "L").replace("1", "R");
                                                        unavailableSeats.add(coach1 + deck1 + row1 + column1);
                                            }
                                            );
                                    }
                                    if(k == 14){
                                        String row2 = String.format("%4s", Integer.toBinaryString(k)).replace(' ', '0')
                                        .replace("0", "F").replace("1", "B");
                                        IntStream.range(0, 4).forEach(l -> {
                                            if(l == 1 || l ==3){
                                            String column2 = String.format("%2s", Integer.toBinaryString(l)).replace(' ', '0')
                                                    .replace("0", "L").replace("1", "R");
                                                    unavailableSeats.add(coach1 + deck1 + row2 + column2);
                                            }
                                        }
                                    );
                                }
                                });
                            }
                            if(deck1 == "U"){
                                IntStream.range(0, 16).forEach(k -> {
                                    if(k == 14){
                                        String row2 = String.format("%4s", Integer.toBinaryString(k)).replace(' ', '0')
                                        .replace("0", "F").replace("1", "B");
                                        IntStream.range(0, 4).forEach(l -> {
                                            if(l == 2 || l ==3){
                                            String column2 = String.format("%2s", Integer.toBinaryString(l)).replace(' ', '0')
                                                    .replace("0", "L").replace("1", "R");
                                                    unavailableSeats.add(coach1 + deck1 + row2 + column2);
                                            }
                                        }
                                    );
                                }
                                    if(k==15){
                                    String row1 = String.format("%4s", Integer.toBinaryString(k)).replace(' ', '0')
                                            .replace("0", "F").replace("1", "B");
                                            IntStream.range(0, 4).forEach(l -> {
                                                if(l==3){
                                                String column1 = String.format("%2s", Integer.toBinaryString(l)).replace(' ', '0')
                                                        .replace("0", "L").replace("1", "R");
                                                        unavailableSeats.add(coach1 + deck1 + row1 + column1);
                                                }
                                            }
                                            );
                                    }
                                });
                            }
                        });
                    });
        // Generate all possible seat IDs (include the missing one)
                IntStream.range(0, 8).forEach(i -> {
                    String coach = String.format("%3s", Integer.toBinaryString(i)).replace(' ', '0')
                            .replace("0", "L").replace("1", "R");
                    IntStream.range(0, 2).forEach(j -> {
                        String deck = j == 0 ? "D" : "U";
                        IntStream.range(0, 16).forEach(k -> {
                            String row = String.format("%4s", Integer.toBinaryString(k)).replace(' ', '0')
                                    .replace("0", "F").replace("1", "B");
                            IntStream.range(0, 4).forEach(l -> {
                                String column = String.format("%2s", Integer.toBinaryString(l)).replace(' ', '0')
                                        .replace("0", "L").replace("1", "R");
                                if(!unavailableSeats.contains(coach + deck + row + column)){
                                seatIds.add(coach + deck + row + column);
                                }
                            });
                        });
                    });
                });
                lines.forEach(ticket -> {
                    String trimmedTicket = ticket.trim();
                    seatIds.remove(trimmedTicket);

                    String binaryTicket = trimmedTicket.replaceAll("L", "0").replaceAll("R", "1")
                            .replaceAll("D", "0").replaceAll("U", "1")
                            .replaceAll("F", "0").replaceAll("B", "1");

                    int coach = Integer.parseInt(binaryTicket.substring(0, 3), 2);
                    int deck = Integer.parseInt(binaryTicket.substring(3, 4));
                    int row = Integer.parseInt(binaryTicket.substring(4, 8), 2);
                    int column = Integer.parseInt(binaryTicket.substring(8, 10), 2);
                    int seatID = coach * 128 + deck * 64 + row * 4 + column;

                    if (seatID > max.get()) {
                        max.set(seatID);
                    }
                    if(seatIds.size()==1){
                    missingTicket.set(seatIds.get(0));
                    }
                });
                int highestSeatId = max.get();
                String yourSeat = missingTicket.get().replaceAll("L", "0").replaceAll("R", "1")
                .replaceAll("D", "0").replaceAll("U", "1")
                .replaceAll("F", "0").replaceAll("B", "1");
                
                coachMissing = (Integer.parseInt(yourSeat.substring(0, 3), 2));
                deckMissing = (Integer.parseInt(yourSeat.substring(3, 4)));
                rowMissing = (Integer.parseInt(yourSeat.substring(4, 8), 2));
                columnMissing = (Integer.parseInt(yourSeat.substring(8, 10), 2));
                seatIDMissing = (coachMissing * 128 + deckMissing * 64 + rowMissing * 4 + columnMissing);
                
                System.out.println("-".repeat(132));
                System.out.printf("%s: coach %d, deck %d, row %d, column %d, seat ID %d.%n",
                missingTicket, coachMissing, deckMissing, rowMissing, columnMissing, seatIDMissing);
                System.out.println("=".repeat(132));
                System.out.println("Highest Seat ID: " + highestSeatId);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }
} 
