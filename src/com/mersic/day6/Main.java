package com.mersic.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        Map<String, List<Moon>> map = new HashMap<>();
        Map<String, Moon> moonMap = new HashMap<>();

        String inputString = null;
        while ((inputString = br.readLine()) != null) {
            String[] s = inputString.split("\\)");
            String planet = s[0];
            Moon moon = new Moon(s[1], s[0]);
            moonMap.put(moon.moon, moon);
            if (!map.containsKey(planet)) {
                List<Moon> moons = new ArrayList<>();
                moons.add(moon);
                map.put(planet, moons);
            } else {
                List<Moon> moons = map.get(planet);
                moons.add(moon);
            }
        }

        int totalOrbits = 0;
        List<String> next_planets = new ArrayList<String>();
        next_planets.add("COM");
        int depth = 0;
        while (next_planets.size() > 0) {
            List<String> current_planets = next_planets;
            totalOrbits += (depth * next_planets.size());
            depth++;
            next_planets = new ArrayList<String>();
            for (String current_planet : current_planets) {
                List<Moon> current_moons = map.get(current_planet);
                if (current_moons == null) {
                    continue;
                }
                for (Moon moon : current_moons) {
                    moon.depth = depth;
                    next_planets.add(moon.moon);
                }
            }
        }
        System.out.println("total_orbits: " + totalOrbits);
//        for (List<Moon> mList : map.values()) {
//            for (Moon m : mList) {
//                System.out.println("Moon: " + m);
//            }
//        }
        List<String> you_parents = new LinkedList<String>();
        Moon
        parent_moon = moonMap.get("YOU");
        while ((parent_moon = moonMap.get(parent_moon.parent)) != null) {
            you_parents.add(parent_moon.moon);
        }
        parent_moon = moonMap.get("SAN");
        List<String> san_parents = new LinkedList<String>();
        while ((parent_moon = moonMap.get(parent_moon.parent)) != null) {
            san_parents.add(parent_moon.moon);
        }

        int you_parent_index = 0;
        for (String you_parent : you_parents) {
            int san_you_index = san_parents.indexOf(you_parent);
            if (san_you_index != -1) {
                System.out.println("san_you_index: " + san_you_index);
                System.out.println("you_parent_index: " + you_parent_index);
                System.out.println("total transfers: "+ (san_you_index + you_parent_index));
                break;
            }
            you_parent_index++;
        }
    }
}