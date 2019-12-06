package com.adventofcode2019;

import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

public class Day3Part2 {
    public static void main(String[] args) {
        List<List<Point>> wires = new ArrayList<List<Point>>();
        List<String> lines = new ArrayList<String>();
        Point refPoint = new Point(1, 1);
        try {
            File inputFile = FileUtils.getFileFromResources("inputs/day3.txt");
            final BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            try (reader) {
                for (String line; (line = reader.readLine()) != null;) {
                    lines.add(line);
                }

                for (String line : lines) {
                    List<Point> wire = new ArrayList<Point>(List.of(refPoint));
                    Point nextPoint = refPoint;

                    String[] lineValues = line.split(",");
                    for (String lineValue : lineValues) {
                        lineValue = lineValue.trim();
                        Point p = null;
                        if (lineValue.startsWith("R")) {
                            p = new Point(nextPoint.x + Integer.valueOf(lineValue.substring(1)), nextPoint.y);
                        } else if (lineValue.startsWith("L")) {
                            p = new Point(nextPoint.x - Integer.valueOf(lineValue.substring(1)), nextPoint.y);
                        } else if (lineValue.startsWith("U")) {
                            p = new Point(nextPoint.x, nextPoint.y + Integer.valueOf(lineValue.substring(1)));
                        } else if (lineValue.startsWith("D")) {
                            p = new Point(nextPoint.x, nextPoint.y - Integer.valueOf(lineValue.substring(1)));
                        }

                        wire.add(p);
                        nextPoint = p;
                    }
                    // System.out.println("Wire : " + wire);
                    wires.add(wire);
                }

            }
            assert wires.size() == 2;
            List<Point> wire1 = wires.get(0);
            List<Point> wire2 = wires.get(1);

            System.out.println("wire 1 points : " + wire1);
            System.out.println("wire 2 points : " + wire2);

            Set<Point> intersectionPoints = new HashSet<Point>();

            for (int i = 0; i < wire1.size() && (i + 1) < wire1.size(); i++) {
                Point l1p1 = wire1.get(i);
                Point l1p2 = wire1.get(i + 1);
                boolean l1Vertical = false;
                boolean l2Vertical = false;
                float m1 = 0;
                float b1 = 0;

                if (l1p2.x != l1p1.x) {
                    l1Vertical = false;
                } else {
                    l1Vertical = true;
                }

                for (int j = 0; j < wire2.size() && (j + 1) < wire2.size(); j++) {
                    Point l2p1 = wire2.get(j);
                    Point l2p2 = wire2.get(j + 1);
                    float m2 = 0;
                    float b2 = 0;

                    if (l2p2.x != l2p1.x) {
                        l2Vertical = false;
                    } else {
                        l2Vertical = true;
                    }

                    if (l1Vertical && l2Vertical) {
                        if (l1p1.x == l2p1.x && l1p2.x == l2p2.x) { // overlapping vertical lines
                            if (l2p1.y < Math.max(l1p1.y, l1p2.y)) {
                                intersectionPoints.add(new Point(l1p1.x, l2p1.y));
                            } else if (l1p1.y < Math.max(l2p1.y, l2p2.y)) {
                                intersectionPoints.add(new Point(l1p1.x, l1p1.y));
                            }
                            if (l2p2.y < Math.max(l1p1.y, l1p2.y)) {
                                intersectionPoints.add(new Point(l1p1.x, l2p2.y));
                            } else if (l1p2.y < Math.max(l2p1.y, l2p2.y)) {
                                intersectionPoints.add(new Point(l1p1.x, l1p2.y));
                            }
                        }
                    } else if (!l1Vertical && !l2Vertical) { // both are horizontal
                        if (l1p1.y == l2p1.y && l1p2.y == l2p2.y) { // overlapping horizontal lines
                            if (l2p1.x < Math.max(l1p1.x, l1p2.x)) {
                                intersectionPoints.add(new Point(l2p1.x, l1p1.y));
                            } else if (l1p1.x < Math.max(l2p1.x, l2p2.x)) {
                                intersectionPoints.add(new Point(l1p1.x, l1p1.y));
                            }
                            if (l2p2.x < Math.max(l1p1.x, l1p2.x)) {
                                intersectionPoints.add(new Point(l2p2.x, l1p1.y));
                            } else if (l1p2.x < Math.max(l2p1.x, l2p2.x)) {
                                intersectionPoints.add(new Point(l1p2.x, l1p1.y));
                            }
                        }
                    } else if ((!l1Vertical && l2Vertical) || (l1Vertical && !l2Vertical)) {
                        Optional<Point> intersectionPoint = Optional.empty();
                        if (l1Vertical && !l2Vertical) {
                            int y = l2p1.y;
                            int x = l1p1.x;
                            if (((x >= l2p1.x && x <= l2p2.x) || (x >= l2p2.x && x <= l2p1.x))
                                    && ((y >= l1p1.y && y <= l1p2.y) || (y >= l1p2.y && y <= l1p1.y))) {
                                Point p = new Point();
                                p.x = l1p1.x;
                                p.y = l2p1.y;
                                intersectionPoint = Optional.of(p);
                            }
                        } else if (l2Vertical && !l1Vertical) {
                            int x = l2p1.x;
                            int y = l1p2.y;
                            if (((x >= l1p1.x && x <= l1p2.x) || (x >= l1p2.x && x <= l1p1.x))
                                    && ((y >= l2p1.y && y <= l2p2.y) || (y >= l2p2.y && y <= l2p1.y))) {
                                Point p = new Point();
                                p.x = l2p1.x;
                                p.y = l1p2.y;
                                intersectionPoint = Optional.of(p);
                            }
                        }
                        if (!intersectionPoint.isEmpty()) {
                            intersectionPoints.add(intersectionPoint.get());
                        }
                    } else {
                        m1 = l1p2.y - l1p1.y / l1p2.x - l1p1.x;
                        b1 = l1p1.y - m1 * l1p1.x;

                        m2 = l2p2.y - l2p1.y / l2p2.x - l2p1.x;
                        b2 = l2p1.y - m2 * l2p1.x;

                        Optional<Point> intersectionPoint = calculateIntersectionPoint(m1, b1, m2, b2);
                        if (!intersectionPoint.isEmpty()) {
                            intersectionPoints.add(intersectionPoint.get());
                        }
                    }
                }
            }

            intersectionPoints.remove(refPoint);
            int minDistance = 0;

            for (Point intersectionPoint : intersectionPoints) {
                int wire1StepsToIntersectionPoint = 0;
                int wire2StepsToIntersectionPoint = 0;

                wire1StepsToIntersectionPoint += calculateStepsToIntersectionPoint(wire1, intersectionPoint);
                wire2StepsToIntersectionPoint += calculateStepsToIntersectionPoint(wire2, intersectionPoint);
                int totalDistance = wire1StepsToIntersectionPoint + wire2StepsToIntersectionPoint;

                if (minDistance == 0 || totalDistance < minDistance) {
                    minDistance = totalDistance;
                }
            }

            System.out.println("Minimum total distance : " + minDistance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int calculateStepsToIntersectionPoint(List<Point> wire, Point intersectionPoint) {
        int distance = 0;
        for (int i = 0; i < wire.size() && i + 1 < wire.size(); i++) {
            Point p1 = wire.get(i);
            Point p2 = wire.get(i + 1);
            int x = 0;
            int y = 0;
            boolean reachedIntersection = false;

            if (intersectionPoint.x >= p1.x && intersectionPoint.x <= p2.x && intersectionPoint.y <= p1.y
                    && intersectionPoint.y >= p2.y) {
                x = Math.abs(p1.x - intersectionPoint.x);
                y = Math.abs(p1.y - intersectionPoint.y);
                reachedIntersection = true;
            } else if (intersectionPoint.x >= p2.x && intersectionPoint.x <= p1.x && intersectionPoint.y <= p2.y
                    && intersectionPoint.y >= p1.y) {
                x = Math.abs(p1.x - intersectionPoint.x);
                y = Math.abs(p1.y - intersectionPoint.y);
                reachedIntersection = true;
            } else {
                x = Math.abs(p1.x - p2.x);
                y = Math.abs(p1.y - p2.y);
                reachedIntersection = false;
            }

            if (x != 0) {
                distance += x;
            } else if (y != 0) {
                distance += y;
            }

            if (reachedIntersection) {
                break;
            }
        }

        System.out.println("Distance : " + distance);
        return distance;
    }

    private static Optional<Point> calculateIntersectionPoint(double m1, double b1, double m2, double b2) {

        if (m1 == m2) {
            return Optional.empty();
        }

        double x = (b2 - b1) / (m1 - m2);
        double y = m1 * x + b1;

        Point point = new Point();
        point.setLocation(x, y);
        return Optional.of(point);
    }

}