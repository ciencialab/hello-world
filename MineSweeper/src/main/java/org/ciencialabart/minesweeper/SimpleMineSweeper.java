package org.ciencialabart.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple implementation of the MineSweeper interface
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
class SimpleMineSweeper implements MineSweeper {
    private final Direction[] directions;
    private List<String> fieldLines;
    private List<List<Character>> hintFieldMatrix;
    
    /**
     * Enum of 8 World directions
     */
    enum Direction {
        N (0, -1),
        NE (1, -1),
        E (1, 0),
        SE (1, 1),
        S (0, 1),
        SW (-1, 1),
        W (-1, 0),
        NW (-1, -1);
        public final int xShift;
        public final int yShift;
        
        /**
         * Creates specific direction enum
         * 
         * @param xShift horizontal offset of specific direction
         * @param yShift vertical offset of specific direction
         */
        Direction (int xShift, int yShift) {
            this.xShift = xShift;
            this.yShift = yShift;
        }
    }
    
    /**
     * Initialises array of 8 World directions
     */
    public SimpleMineSweeper() {
        directions = Direction.values();
    }
    
    /**
     * Initialises the mine-field as specified in MineSweeper interface
     * 
     * @param mineField string containing the mines
     * @throws IllegalArgumentException if mineField is not properly formatted
     */
    @Override
    public void setMineField(String mineField) throws IllegalArgumentException {
        // checking for characters validity
        if (((!mineField.matches("[\\.*\n]+")                                   // it discards any not mentioned characters
                || !mineField.contains("*"))                                    // as it's "string containing the mines"
                || mineField.charAt(0) == '\n')                                 // as "Lines are separated by '\n'" and not surrounded
                || mineField.charAt(mineField.length() - 1) == '\n') {
            throw new IllegalArgumentException();                               // if something is wrong: throwing Exception
        }
        
        fieldLines = Arrays.asList(mineField.split("\n"));                      // splitting the mineField into lines
        if (fieldLines.stream()
                .mapToInt(line -> line.length())                                // checking if their lenghts
                    .distinct()                                                 // differ
                    .count() > 1) {                                             // and if so
            fieldLines = null;                                                  // small rollback
            throw new IllegalArgumentException();                               // and throwing Exception
        }
        // note: fieldLines is now set and valid
    }
    
    /**
     * Produces a hint-field as specified in MineSweeper interface
     * 
     * @return a string representation of the hint-field
     * @throws IllegalStateException if the mine-field has not been initialised
     */
    @Override
    public String getHintField() throws IllegalStateException {
        int xPosition;                                                          // current x mine scanning position (on hint-field with frame)
        int yPosition;                                                          // current y mine scanning position (on hint-field with frame)
        char currentFieldValue;                                                 // current mine value for above coordinates
        int xNeighbourPosition;                                                 // for current mine: its neighbour x scanning position (on hint-field with frame)
        int yNeighbourPosition;                                                 // for current mine: its neighbour y scanning position (on hint-field with frame)
        char currentNeighbourFieldValue;                                        // current mine neighbour value for above coordinates
        int xSize;                                                              // x dimension of original (not framed) mine-field
        int ySize;                                                              // y dimension of original (not framed) mine-field
        List<Character> frame;                                                  // horizontal frame element
        StringBuilder hintField;                                                       // almost final hintField
        
        if (fieldLines == null) {                                               // if the mine-field has not been initialised
            throw new IllegalStateException();                                  // throws Exception
        }
        
        // creating hint-field based on mine-field
        xSize = fieldLines.get(0).length();                                     // x dimension of original hint-field
        ySize = fieldLines.size();                                              // y dimension of original hint-field
        frame = new ArrayList(Collections.nCopies(xSize + 2, '0'));             // creating horizontal frame element (2 squares wider than mine-field) filled with zeroes
        hintFieldMatrix = fieldLines.stream()                                   // start of creating hint-field based on mine-field
                .collect(() -> new ArrayList(Collections.singletonList(frame)), // applying top frame
                        (list, line) -> list                                    // joining subsequent mine-field lines
                            .add(String                                         // placed in frame made of zeroes
                                .format("%s", "0" + line.replace('.', '0') + "0")   // with . content replaced with zeroes as well
                                .chars()
                                .mapToObj(code -> (char) code)
                                .collect(Collectors.toList())
                            ),
                        (list1, list2) -> list1.addAll(list2));
        hintFieldMatrix.add(frame);                                             // applying bottom frame - now framed hint-field is ready to scan
        
        // scanning and updating the framed hint-field
        yPosition = ySize;                                                      // initial y mine scanning position set to bottom of frame content
        while (yPosition > 0) {                                                 // while verticaly inside the frame
            xPosition = xSize;                                                  // initial x mine scanning position set to right side of frame content
            while (xPosition > 0) {                                             // while horizontally inside the frame
                currentFieldValue = hintFieldMatrix                             // getting current mine value
                        .get(yPosition)
                        .get(xPosition);
                if (currentFieldValue == '*') {                                 // if on mine
                    for (Direction direction : directions) {                    // then for every of 8 directions
                        xNeighbourPosition = xPosition + direction.xShift;      // determine ...
                        yNeighbourPosition = yPosition + direction.yShift;      // ... neighbour position
                        currentNeighbourFieldValue = hintFieldMatrix            // get neighbour value
                                .get(yNeighbourPosition)
                                .get(xNeighbourPosition);
                        if (currentNeighbourFieldValue != '*') {                // and if it's not a mine
                            currentNeighbourFieldValue++;                       // increment it
                            hintFieldMatrix                                     // and update the hint-field
                                    .get(yNeighbourPosition)
                                    .set(xNeighbourPosition, currentNeighbourFieldValue);
                        }
                    }
                }
                xPosition--;                                                    // update of current x mine scanning position
            }
            yPosition--;                                                        // update of current y mine scanning position
        }
        
        // removing spare frames
        hintFieldMatrix.remove(ySize + 1);
        hintFieldMatrix.remove(0);
        hintFieldMatrix.forEach(row -> row.remove(xSize + 1));
        hintFieldMatrix.forEach(row -> row.remove(0));
        
        // moving the ready hint-field from list of lists to string
        hintField = hintFieldMatrix.stream()
                .map(row -> row.stream()                                        // for every row
                        .reduce(new StringBuilder(),                            // appending given row Characters to StringBuilder
                                (line, field) -> line.append(field),
                                (line1, line2) -> line1.append(line2))
                            .append('\n'))                                      // \n terminated
                .reduce(new StringBuilder(),                                    // merging resulting row StringBuilders
                        (partField, line) -> partField.append(line),
                        (partField1, partField2) -> partField1.append(partField2));
        return hintField.substring(0, hintField.length() - 1);                  // returning final hint-field without terminal \n
    }
    
    /**
     * The main method with example negative and positive usage scenarios
     * <p>
     * Feel free to replace it with your content
     * 
     * @param args are ignored
     */
    public static void main(String[] args) {
        String mineField;
        SimpleMineSweeper simpleMineSweeper = new SimpleMineSweeper();
        
        // Negative scenarios
        System.out.println("Negative examples:");
        System.out.println("------------------");
        System.out.println();
        
        // Throwing IllegalStateException
        System.out.println("First calling method getHintField():");
        try {
            simpleMineSweeper.getHintField();
        } catch (IllegalStateException ex) {
            System.out.println("Caught: " + ex);
        }
        System.out.println();
        
        // Throwing IllegalArgumentException
        System.out.println("Calling method setMineField() with argument:");
        mineField = "*...\n..*\n....";
        System.out.println(mineField);
        try {
            simpleMineSweeper.setMineField(mineField);
        } catch (IllegalArgumentException ex) {
            System.out.println("Caught: " + ex);
        }
        System.out.println();
        System.out.println();
        
        
        // Positive scenario
        System.out.println("Positive example:");
        System.out.println("-----------------");
        System.out.println();
        System.out.println("Calling method setMineField() with argument:");
        mineField = "*...\n..*.\n....";
        System.out.println(mineField);
        simpleMineSweeper.setMineField(mineField);
        System.out.println("Calling method getHintField() obtaining as a result:");
        System.out.println(simpleMineSweeper.getHintField());
        System.out.println();
    }
    
}
