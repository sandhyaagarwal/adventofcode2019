package com.adventofcode2019;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Day2Part1 {
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

                // Resetting to "1202 Program Alarm" state
                inputs.set(1, 12);
                inputs.set(2, 2);
                System.out.println("Inputs : " + inputs);
                executeIntCodeProgram(inputs);
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
            System.out.println("input sublist :" + inputs.subList(index, index + 4));
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
                System.out.println("input sublist after processing:" + inputs.subList(index, index + 4));
                index = index + 4;
            } else {
                break;
            }

        }

        System.out.println("Inputs after running the IntCodeProgram = " + inputs);
    }

}