package com.adventofcode2019;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Day2Part2 {
    public static void main(String[] args) {
        List<Integer> inputs = new ArrayList<Integer>();
        List<String> lines = new ArrayList<String>();
        try {
            File inputFile = FileUtils.getFileFromResources("inputs/day2.txt");
            final BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            try (reader) {
                for (String line; (line = reader.readLine()) != null;) {
                    lines.add(line);
                }

                for (String line : lines) {
                    String[] lineValues = line.split(",");
                    for (String lineValue : lineValues) {
                        inputs.add(Integer.valueOf(lineValue.trim()));
                    }
                }

                System.out.println("Inputs length : " + inputs.size());
                List<Integer> inputsCopy = new ArrayList<Integer>();
                inputsCopy.addAll(inputs);

                // Resetting to "1202 Program Alarm" state
                for (int i = 0; i <= 99; i++) {
                    for (int j = 0; j <= 99; j++) {
                        inputs.set(1, i);
                        inputs.set(2, j);
                        System.out.println("Inputs before executing the program : " + inputs);
                        executeIntCodeProgram(inputs);
                        System.out.println("Inputs after executing the program : " + inputs);

                        if (inputs.get(0) == 19690720) {
                            System.out.println("The two numbers are : " + i + "," + j);
                            System.out.println("The two numbers are : " + inputsCopy.get(i) + "," + inputsCopy.get(j));

                            System.exit(0);
                        } else {
                            inputs.clear();
                            inputs.addAll(inputsCopy);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeIntCodeProgram(List<Integer> inputs) {
        int index = 0;
        int inputsLen = inputs.size();
        while (index + 3 < inputsLen) {
            int opCode = inputs.get(index);
            int operand1Index = inputs.get(index + 1);
            int operand2Index = inputs.get(index + 2);
            int operand1 = inputs.get(operand1Index);
            int operand2 = inputs.get(operand2Index);

            Integer result = null;
            switch (opCode) {
            case 1:
                result = operand1 + operand2;
                break;
            case 2:
                result = operand1 * operand2;
                break;
            case 99:
                break;
            }
            if (result != null) {
                inputs.set(inputs.get(index + 3), result);
                index = index + 4;
            } else {
                break;
            }

        }

    }

}