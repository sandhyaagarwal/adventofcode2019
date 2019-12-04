package com.adventofcode2019;

import java.io.*;

public class Day1Part2 {
    public static void main(String[] args) {
        double totalFuel = 0;

        try {
            File inputFile = FileUtils.getFileFromResources("inputs/day1.txt");
            final BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            try (reader) {
                for (String line; (line = reader.readLine()) != null;) {
                    // Process line
                    double mass = Double.valueOf(line.trim());
                    do {
                        int fuel = calculateFuel(mass);
                        if (fuel != 0) {
                            totalFuel += fuel;
                        }
                        mass = fuel;
                    } while (mass != 0);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Total Fuel : " + totalFuel);

    }

    private static int calculateFuel(double mass) {
        int fuel = (int) Math.floor(mass / 3) - 2;
        System.out.println("Fuel for mass = " + mass + " is " + fuel);
        if (fuel < 0) {
            fuel = 0;
        }
        return fuel;
    }

}